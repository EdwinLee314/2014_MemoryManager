public class Page {
	private int id;
	private String content;
	
	public Page(int id, String content){
		this.id = id;
		this.content = content;
	}
	
	public int getId(){
		return id;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public String getContent(){
		return content;
	}
	
	public String toString(){
		return  id + " " + content + "\n";
	}
	
}
