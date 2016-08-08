public class Q1S {

	public static void main(String[] args) {
		String clientNo = args[0];
		int cNo = Integer.parseInt(clientNo);
		Message message = new Message(cNo);
		
		ServerThread s = new ServerThread("server 1", 1,message);
		s.start();
		
		ClientThread[] cArray = new ClientThread[cNo];
		for(int i = 0; i < cNo; i++){
			cArray[i] = new ClientThread("thread " + Integer.toString(i), i,message);
			cArray[i].start();
		}
	
	}

}
