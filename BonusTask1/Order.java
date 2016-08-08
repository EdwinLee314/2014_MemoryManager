public class Order {
	private int clientId;
	private int requestId;
	private int serverId;
	private String request;
	private int pageId;
	private String contents;
	
	public Order(int cId, int sId, int rId, String request, int pId, String content){
		this.clientId = cId;
		this.serverId = sId;
		this.requestId = rId;
		this.request = request;
		this.pageId = pId;
		this.contents = content;
	}
	
	public int getServerId(){
		return serverId;
	}
	public int getClientId(){
		return clientId;
	}
	public int getRequestId(){
		return requestId;
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
		return "Client Id" + clientId +"Server Id"+ serverId+ "Request id: " + requestId + " Request: " + request + 
				" Page Id: " + pageId + " Contents : " + contents;
	}
	
}