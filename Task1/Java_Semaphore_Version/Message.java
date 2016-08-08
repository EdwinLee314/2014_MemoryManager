import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

public class Message {
	private Page t;
	private boolean sented = false;
	Semaphore munex = new Semaphore(0);
	Semaphore clients[];
	
	public Message(int cNo){
		this.clients = new Semaphore[cNo];
		for(int i = 0; i< cNo;i++){
			clients[i] = new Semaphore(0);
		}
	}
	
	//server send message
	public void send(int clientId,Page tPage){
			
			t = tPage;
			System.out.println("Server send to Client: "+ clientId+ " data :" + t);
			clients[clientId].release();
			//wait until client write to log
				try {
					munex.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

	}
	
	//server send no more message
	public void finish(){
		sented = true;
		for(int i = 0; i< clients.length;i++){
			clients[i].release();
		}
	}
	
	//client get message
	public void get(int id){
		try {
			clients[id].acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		if(t != null){
			System.out.println("Client " + id + " got data: "+ t);
			
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
		t = null;
		munex.release();
	}
	
	//client end receive message 
	public boolean end(){
		return sented;
	}
}
