import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;


public class ClientThread extends Thread {
	private int _id;
	private LinkedList<Order> oList = new LinkedList<Order>();
	Message message;
	public ClientThread(String name, int id,Message message){
		super(name);
		_id = id;
		this.message=message;
	}
	
	public void run(){
		readRequest();
		try {
			//sleep(10);
			while(oList.isEmpty() == false){		
				Order t = oList.pollFirst();						
				//send request
				message.sendOrder(t);			
				//get result 
				message.received();						
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//notify server no more request
		message.end();
		System.out.println("Finish Client " + _id);
	}
	
	private void readRequest(){
		//read file
		File file = new File("client_requests_" + _id+".dat");
		BufferedReader inputStream = null;
		try{
			if(file.exists()){
				inputStream = new BufferedReader(new FileReader(file),40*1024*1024);
				String line = "";	
				//read request
					while((line =inputStream.readLine()) != null) {							
						String data[] = line.split(" ");
						int rId = Integer.parseInt(data[0]);
						int pId = Integer.parseInt(data[2]);
						if(data[1].equals("read")){
							Order tOrder = new Order(_id,rId,data[1],pId, "");
							oList.add(tOrder);
						}
						else{
							Order tOrder = new Order(_id,rId,data[1],pId, data[3]);
							oList.add(tOrder);
						}
					}	
				inputStream.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}					
	}
	
	
}