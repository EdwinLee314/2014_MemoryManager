import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientThread extends Thread {
	private int _id;
	Message message;
	public ClientThread(String name, int id,Message message){
		super(name);
		_id = id;
		this.message = message;
	}
	
	public void run(){
		try {
			sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(message.end() == false){
			Page t =message.get(_id);			
			if(t != null){
				System.out.println("Client "+ _id + "get Page" + t);
				writeToLog(t);
			}
		}
		System.out.println("Finish Client " + _id);
	}
	
	public void writeToLog(Page t){
		String filename = "client_log_"+ Integer.toString(_id);
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