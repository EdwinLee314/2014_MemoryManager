public class Order {
	private int clientId;
	private int requestId;
	private String request;
	private int pageId;
	private String contents;
	
	public Order(int cId, int rId, String request, int pId, String content){
		this.clientId = cId;
		this.requestId = rId;
		this.request = request;
		this.pageId = pId;
		this.contents = content;
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
		return "Client Id" + clientId +"Request id: " + requestId + " Request: " + request + 
				" Page Id: " + pageId + " Contents : " + contents;
	}
	
}