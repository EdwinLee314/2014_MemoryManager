import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class ServerThread extends Thread {
	
	private HashMap<Integer,Page> pList = new HashMap<Integer,Page>();
	private int _id;
	Message message;
	public ServerThread(String name, int id,Message message){
		super(name);
		_id = id;
		this.message=message;
	}
	
	public void run(){
		setUpPage();
		Order t = null;
		System.out.println("finished inisall page");
		while(message.notFinish()){
			
			try {
				t = message.getOrder();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			processOrder(t);
			
		}
		System.out.println("Finish Server " + _id);
	}
	
	public void processOrder(Order tOrder){
		int cId = tOrder.getClientId();
		Page p = null;
		
		if(tOrder.getRequest().equals("read")){
			//read from Page
			int pId = tOrder.getPageId();
			p = pList.get(pId);		
		}
		else if(tOrder.getRequest().equals("write")){
			//write to Page
			int pId = tOrder.getPageId();
			Page tPage = pList.get(pId);
			tPage.setContent(tOrder.getContent());
			p = null;
			pList.remove(pId);
			pList.put(pId, tPage);
		}	
		//send page to client
		message.send(cId,p);
	}
	
	private void setUpPage(){
		String fileName = "init_buffer_pages.dat";
		//read file
			File file = new File(fileName);
			BufferedReader inputStream = null;
			try{
				if(file.exists()){
					inputStream = new BufferedReader(new FileReader(file),40*1024*1024);
					String line = "";	
					//read request
						while((line =inputStream.readLine()) != null) {							
							String data[] = line.split(" ");						
							int pId = Integer.parseInt(data[0]);
							Page tPage = new Page(pId,data[1]);
							pList.put(pId, tPage);
						}
					inputStream.close();
				}else{
					System.out.println("No files");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
	}	
}