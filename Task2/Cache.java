import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Cache {
	private ArrayList<Page> caches = new ArrayList<Page>();
	private Disk store;
	
	public Cache(){
		store = new Disk("storeFile");
	}
	
	public void initialCache(int pNo,ArrayList<Page> pList){
		//put all pages into disk
		store.initialStoreFile(pList);
		//put first pNo pages into cache
		for(int i = 0; i< pNo; i++){
			Page temp = pList.get(i);
			caches.add(temp);
		}
	}
	
	public Page readPage(int id){
		Page temp = null;
		for(int i = 0; i <caches.size();i++){
			if(caches.get(i).getId() == id){
				temp = caches.remove(i);
				//put the read page to the last index
				caches.add(temp);
				break;
			}
		}
		//can not find it in cache
		if(temp == null){
			//find it in store file
			temp = store.readFromFile(id);
			//remove the LRU
			Page t = caches.remove(0);
			writeToLog(t);
			//put the page to the last of cache
			caches.add(temp);
		}
		return temp;
	}
	
	public void writePage(Page p){
		Page temp = null;
		for(int i = 0; i <caches.size();i++){
			if(caches.get(i).getId() == p.getId()){
				temp = caches.remove(i);
				//put the write page to the last index
				caches.add(p);
				//flush to the store file
				store.writeToFile(temp, p);
				break;
			}
		}
		//can not find it in cache
		if(temp == null){
			//remove the LRU
			Page t = caches.remove(0);
			writeToLog(t);
			//find it in store file
			temp = store.readFromFile(p.getId());
			//put the write page to the last index
			caches.add(p);
			//flush to the store file
			store.writeToFile(temp, p);
		}
	}
	
	public void writeToLog(Page p){
			String fileName = "server_log.dat";
			try{
				FileWriter fw=new FileWriter(fileName,true);  
		        PrintWriter pw=new PrintWriter(fw); 
		        pw.println(p);
				fw.close();
			} catch (IOException e) {  
	            e.printStackTrace();  
	        } 
	}
}