import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Message {
	private Order o;
	private Page t;
	private int cId = -1;
	private int sId = -1;
	private int endClient = 0;
	private int clientNo;
	private int serverNo;
	private int dealNo =1; //process order in ascend
	Semaphore servers[];
	Semaphore received = new Semaphore(0);
	Lock lock = new ReentrantLock();
	Condition cReady  = lock.newCondition(); 
	public Message(int sNo,int cNo){
		this.clientNo = cNo;	
		this.serverNo = sNo;	
		this.servers = new Semaphore[sNo];
		for(int i = 0; i< sNo;i++){
			servers[i] = new Semaphore(0);
		}
	}
	
	//client send order
	public void sendOrder(Order order) throws InterruptedException{	
			lock.lock();
			try{
				while(dealNo != order.getRequestId()){
					cReady.await();
				}
				System.out.println("Client" +order.getClientId() + " Request No" + order.getRequestId());
				o = order;	
				sId = order.getServerId();
				System.out.println("Ask Server" + sId);
				servers[sId].release();
			}finally{			
				lock.unlock();
			}
	}
	//server getOrder
	public Order getOrder(int serverId) throws InterruptedException{
			servers[serverId].acquire();
			//System.out.println("server" + sId + "get order");
		
			return o;		
	}
	//server send page
	public void send(int cId,Page t){			
			this.t = t;
			this.cId = cId;
			o = null;
			received.release();
			//System.out.println("server processed client" + cId);
	}
	//client received page
	public void received() throws InterruptedException{
		lock.lock();
		try{	
			received.acquire();
			dealNo++; //process next order
			if(t !=null){
				writeToLog(t,cId);
			}
			t = null;
			cId = -1;
			sId = -1;
			cReady.signalAll();
		}finally{
			//System.out.println("finished one turn");
			lock.unlock();
		}
			
	}
	
	public void end(){
		endClient++;
		if(clientNo == endClient){
			for(int i = 0; i< serverNo; i++){
				servers[i].release();
			}
		}
	}
	
	public boolean notFinish(){
		if(clientNo == endClient){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void writeToLog(Page t, int id){
		String filename = "client_log_"+ Integer.toString(id);
		try{
			FileWriter fw=new FileWriter(filename,true);  
		    PrintWriter pw=new PrintWriter(fw); 
		    pw.println(t.getId() + " " + t.getContent());
			fw.close();
		} catch (IOException e) {  
		    e.printStackTrace();  
		} 	
	}
}