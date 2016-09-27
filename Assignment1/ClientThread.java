import java.io.*;
import java.net.*;

public class ClientThread extends Thread {

	Integer id;
	Socket ServerSocket;
	String ServerName;
	int ServerPort;
	String handshakeMsg = "HELLO";
	long AvgRtt =0;
	
	public long getAvgRTT(){
		return this.AvgRtt;
	}

	public ClientThread() {
		super();
	}

	ClientThread(Integer id, String host, Integer port) {
		this.id = id;
		this.ServerName = host;
		this.ServerPort = port;
	}

	public void run(){
    	try(
    			Socket socket = new Socket(ServerName,ServerPort);
    			PrintWriter toServer = new PrintWriter(socket.getOutputStream(),true);
    			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			
    			){
    		
    		URL whatismyip = new URL("http://checkip.amazonaws.com");
    	    BufferedReader inFromWhatIsMyIp = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
    	    String myIp = inFromWhatIsMyIp.readLine();
    	    
    	    String message_request = handshakeMsg + ","+ myIp+","+socket.getLocalPort()+","+this.id;
    	    String message_response;
    	    int request_number=300;
    	    for(int i=0;i<request_number;i++){
    			long startRequestTime = System.currentTimeMillis(); 
    	    	toServer.println(message_request);
    	    	System.out.println("Message Send: "+message_request);
    	    	
    	    	while ((message_response = fromServer.readLine()) != null) {
    	    		if(message_response.equals("BYE"))
    	    			break;
    	    	}
    	    	long endRequestTime   = System.currentTimeMillis();//end of Rtt

    			long rtt = endRequestTime - startRequestTime;
    			this.AvgRtt = this.AvgRtt + rtt;
    	    	
    	    }
    	    toServer.println("FINISH");
    	    this.AvgRtt = this.AvgRtt / request_number;
    	    
    	    System.out.println(this.id+" DONE");
    	    
    	    
    		
    		
    	}catch(UnknownHostException e) {
    	    System.err.println("Don't know about host " + ServerName);
    	    System.exit(1);
    	}catch(IOException e) {
    	    System.err.println("Couldn't get I/O for the connection to " +
 			       ServerName);
 	    System.exit(1);
    	}
    }
}
