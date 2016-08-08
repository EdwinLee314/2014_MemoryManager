import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;

public class ServerThread extends Thread {
	private LinkedList<Order> oList = new LinkedList<Order>();
	private HashMap<Integer,Page> pList = new HashMap<Integer,Page>();
	private int _id;
	Message message;
	
	public ServerThread(String name, int id, Message message){
		super(name);
		_id = id;
		this.message=message;
	}
	
	public void run(){
		setUpPage();
		readRequest();
		//start process order
		while(oList.isEmpty() == false){  //process until no order
			Order t = oList.pollFirst();
			processOrder(t);
		}
		//send no more message
		message.finish();
		message.send(-1, null);
		System.out.println("Finish Server " + _id);
	}
	
	public void processOrder(Order tOrder){
		int cId = -1;
		Page p = null;
		
		if(tOrder.getRequest().equals("read")){
			//read from Page
			int pId = tOrder.getPageId();
			p = pList.get(pId);
			cId = tOrder.getClientId();
			//send to client		
			//System.out.println("To send" + p);
			message.send(cId, p);
		}
		else if(tOrder.getRequest().equals("write")){
			int pId = tOrder.getPageId();
			Page tPage = pList.get(pId);
			tPage.setContent(tOrder.getContent());
			p = tPage;
			pList.remove(pId);
			pList.put(pId, tPage);
		}	
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

	private void readRequest(){
		//read file
				File file = new File("all_requests.dat");
				BufferedReader inputStream = null;
				try{
					if(file.exists()){
						inputStream = new BufferedReader(new FileReader(file),40*1024*1024);
						String line = "";	
						//read request
							while((line =inputStream.readLine()) != null) {							
								String data[] = line.split(" ");
								int cId = Integer.parseInt(data[0]);
								int pId = Integer.parseInt(data[2]);
								if(data[1].equals("read")){
									Order tOrder = new Order(cId,data[1],pId, "");
									oList.add(tOrder);
								}
								else{
									Order tOrder = new Order(cId,data[1],pId, data[3]);
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