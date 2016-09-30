import java.net.*;
import java.io.*;

public class Server {


	public static void main(String[] args)  throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: java Server <Port Number> <Repetition Number>");
            System.exit(1);
        }
	
        Integer portNumber = Integer.parseInt(args[0]);
        Integer repetition = Integer.parseInt(args[1]);
        
    	ServerSocket   serverSocket = new ServerSocket(portNumber);

    	System.out.println("Server Start");
    	
    	while(true) {
    		
    		Socket clientSocket = serverSocket.accept();
    	    
    	    ClientServiceThread clientThread = new ClientServiceThread(clientSocket);
    	    clientThread.start();
    		
    	    
    		
    	}
    	
    	
    	
    	
    	
	}

}
