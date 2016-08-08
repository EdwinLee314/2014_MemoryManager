public class ClientThread extends Thread {
	private int _id;
	Message message;
	public ClientThread(String name, int id,Message message){
		super(name);
		_id = id;
		this.message = message;
	}
	
	public void run(){
	
		while(message.end() == false){
			//System.out.println("Before Client id is" + _id);
			message.get(_id);
			//System.out.println("After Client id is" + _id);
		}
		System.out.println("Finish Client " + _id);
	}
	
}