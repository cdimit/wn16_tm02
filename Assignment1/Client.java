
import java.util.ArrayList;
import java.util.List;
public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 if (args.length != 2) {
	            System.err.println("Usage: java Client <host name> <port number>");
	            System.exit(1);
	        }
		 
		 String serverName = args[0];
		 Integer portNumber = Integer.parseInt(args[1]);
		 
		 Long avgRTT = 0L;
		 int users = 10;
		 List<ClientThread>clientThreads = new ArrayList<ClientThread>();
		 for(int i=0; i<users;i++){
			 clientThreads.add(new ClientThread(i,serverName,portNumber));
			 
		 }
		 for(int i=0;i<users;i++){
			 clientThreads.get(i).start();
		 }
		 
		 for(int i=0;i<users;i++){
			 try {
				clientThreads.get(i).join();
			} catch (InterruptedException e) {}
			 avgRTT=avgRTT+clientThreads.get(i).getAvgRTT();	 
			 
		 }
		 avgRTT=avgRTT/users;
		 System.out.println("Done, and the avgRTT is : "+avgRTT);
		 
		 
		 
		
	}

}
