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
	private int endClient = 0;
	private int clientNo;
	private int dealNo =1; //process order in ascend
	Semaphore server = new Semaphore(0);
	Semaphore received = new Semaphore(0);
	Lock lock = new ReentrantLock();
	Condition notReady  = lock.newCondition(); 
	public Message(int cNo){
		this.clientNo = cNo;	
	}
	
	//client send order
	public void sendOrder(Order order) throws InterruptedException{
			System.out.println(order);			
			lock.lock();
			try{
				while(dealNo != order.getRequestId()){
					notReady.await();
				}
				//System.out.println("Client" +order.getClientId() + " Request No" + order.getRequestId());
				o = order;
				server.release();
			}finally{
				lock.unlock();
			}
	}
	//server getOrder
	public Order getOrder() throws InterruptedException{
			server.acquire();
			//System.out.println("server received");
			return o;	
	}
	//server send page
	public void send(int cId,Page t){			
			this.t = t;
			this.cId = cId;
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
			notReady.signalAll();
		}finally{
			System.out.println("finished one turn");
			lock.unlock();
		}
			
	}
	
	public void end(){
		endClient++;
		if(clientNo == endClient){
			server.release();
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
