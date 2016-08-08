import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Message {
	private Page t;
	private int cId = -1;
	private boolean sented = false;
	Lock lock = new ReentrantLock();
	Condition notRead  = lock.newCondition(); 
	Condition notWrite  = lock.newCondition(); 
	public Message(){ 	
		
	}
	
	//server send message
	public void send(int clientId,Page tPage){
		lock.lock();
		try{			
			//copy message
			cId = clientId;
			t = tPage;
			//System.out.println("Server send to Client: "+ clientId+ " data :" + t);	
			//finish write notify clients
			notWrite.signalAll();	
			//wait until client write to log
			while(t != null){
				try {				
					System.out.println("Server is waiting Client " + cId);
					notRead.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}finally{
			lock.unlock();
		}
	}
	//client get message
	public Page  get(int id){		
		lock.lock();
		try{		
				while(cId != id){				
					try {
						//System.out.println("Client " + id + " is waiting");
						notWrite.await();
						//System.out.println("Client " + id + " is awake");
						if(sented == true && cId == -1){
							return null;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}		
			Page g = t;
			//empty the message
			t = null;
			cId = -1;
			//finish read signal server	
			
			return g;
		
		}finally{
			notRead.signal();
			lock.unlock();
		}
	}
	
	//server send no more message
	public void finish(){		
		sented = true;
	}
	
	//client end receive message 
	public boolean end(){
		return sented;
	}
}