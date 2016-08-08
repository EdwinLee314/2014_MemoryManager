
public class B1 {
	public static void main(String[] args) {
		String serverNo = args[0];
		String clientNo = args[1];
		int sNo = Integer.parseInt(serverNo);
		int cNo = Integer.parseInt(clientNo);
		Message message = new Message( sNo,cNo);
		
		ServerThread[] sArray = new ServerThread[sNo];
		for(int i = 0; i < sNo;i++){
			sArray[i] = new ServerThread("server"+ Integer.toString(i), i,message);
			sArray[i].start();
		}
		
		ClientThread[] cArray = new ClientThread[cNo];
		for(int i = 0; i < cNo; i++){
			cArray[i] = new ClientThread("client " + Integer.toString(i), i,message);
			cArray[i].start();
		}
	
	}
}
