import java.net.*;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class ClientServiceThread extends Thread {

	Socket clientSocket;
    Long memory = null;
	String cpu_avg = null;
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
		Calendar rightNow = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		long prevMillis = rightNow.getTimeInMillis();
		int allpack = 0;
	    while(isThreadRunning) 
		{
		    String inputLine = fromClient.readLine();
		    String[] inputArray =  inputLine.split(",");
		    //System.out.println(inputLine);
		    
		    if(!inputLine.equals("FINISH")) {
			
			toClient.println("WELCOME "+ inputArray[3] );
			Random rand = new Random();
			int payload_size = (rand.nextInt(1700)+300);
	       		allpack=allpack+payload_size;
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
		    
		    Runtime runtime = Runtime.getRuntime();
			runtime.gc();
			memory = runtime.totalMemory() - runtime.freeMemory();
		    try {
		    	
			    Process p = Runtime.getRuntime().exec("sh cpu_avg.sh");
			    
			    BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			    cpu_avg = stdInput.readLine();
			    		    
			}
			catch(IOException e1) {}
		}
	    long afterMillis = rightNow.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis();
	    long totaltime = (afterMillis-prevMillis)/1000;
	    long throughput= allpack/totaltime;
		System.out.println(throughput+" KB/S");
		System.out.println(cpu_avg);
		System.out.println(memory + " Bytes");

		

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
