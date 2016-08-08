public class Order {
	private int clientId;
	private String request;
	private int pageId;
	private String contents;
	
	public Order(int cId, String request, int pId, String content){
		this.clientId = cId;
		this.request = request;
		this.pageId = pId;
		this.contents = content;
	}
	public int getClientId(){
		return clientId;
	}
	public int getPageId(){
		return pageId;
	}
	public String getContent(){
		return contents;
	}
	public String getRequest(){
		return request;
	}
	public String toString(){
		return "Client id: " + clientId + " Request: " + request + " Page Id: " + pageId + " Contents : " + contents + "\n";
	}
	
}
