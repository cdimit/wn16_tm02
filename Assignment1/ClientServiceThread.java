import java.net.*;
import java.io.*;
import java.util.*;

public class ClientServiceThread extends Thread {

	Socket clientSocket;
    boolean isThreadRunning = true; 

    public ClientServiceThread() 
    { 
	super(); 
    } 
    
    ClientServiceThread(Socket s) 
    { 
	clientSocket = s; 
    } 

    public void run() 
    {
	
	try (
	     BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	     PrintWriter toClient = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));                   
	     
	     ) {	

	    while(isThreadRunning) 
		{
		    String inputLine = fromClient.readLine();
		    String[] inputArray =  inputLine.split(",");
		    System.out.println(inputLine);
		    if(!inputArray[0].equals("FINISH")) {
			
			toClient.println("WELCOME "+ inputArray[3] );
			Random rand = new Random();
			int payload_size = (rand.nextInt(1700)+300);
	       
			for(int i=0;i<payload_size;i++)
			    {
				for(int j=0;j<64;j++)
				    {					
					toClient.println("aaaaaaaa");
				    }
			    }
			toClient.println("BYE");
			toClient.flush();
			
		    }
		    else {
			isThreadRunning = false;
		    }
		    
		    
		}

	} catch (Exception e) {
	    e.printStackTrace();
	}
	finally
	    {
		try {
		    clientSocket.close();
		}
		catch (IOException ioe) {
 		    ioe.printStackTrace();
		}
	    }
	
    }

}
