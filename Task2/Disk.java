import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Disk {
	private String fileName;
	public Disk(String fileName){
		this.fileName = fileName;
	}
	
	public void initialStoreFile(ArrayList<Page> stores){		
			for(Page p: stores){
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
	
	public Page readFromFile(int id){
		Page tPage = null;
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
							if(id == pId){
								tPage = new Page(pId,data[1]);
								break;
							}
						}
					inputStream.close();
				}else{
					System.out.println("No files");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		return tPage;
	}
	
	public void writeToFile(Page oldPage,Page newPage){
		String temp = "";
		String oldP = oldPage.toString();
		String newP = newPage.toString();
		
		try{
			File file = new File(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            StringBuffer buf = new StringBuffer();
            
            // save the pages before modify line
            while( (temp = br.readLine()) != null && !temp.equals(oldP)) {
                buf = buf.append(temp);
                buf = buf.append(System.getProperty("line.separator"));
            }
            //insert page
            buf =  buf.append(newP);
            // save the pages after modify line
            while ((temp = br.readLine()) != null) {
                buf = buf.append(System.getProperty("line.separator"));
                buf = buf.append(temp);
            }
            
            br.close();
            PrintWriter pw = new PrintWriter(new FileOutputStream(file));
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
}
