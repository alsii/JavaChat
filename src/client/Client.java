package client;

import java.util.*;
import java.io.*;
import java.net.*;

public class Client implements  Runnable{
	
	public String welcomeMsg = 	"#   Welcome to SieveChat-Client!   #";
	public String connectionClosedMsg = "Connection is closed!";
	public String connectionFailedMsg = "Connection is failed!";
	public String byeMsg = "Client is closed. Good bye!";
	public String line = "------------------------------------";
	
	
	public Socket Server;
	public int serverPort;
	public Scanner sc ;
	public BufferedReader serverIn;
	public BufferedReader userIn;
	public PrintWriter serverOut;
	public IOException IOe;
	public String name, serverIP;
	public Thread ClientThread;
	//private Socket server;
	
	public Client(){
		
		/*
		 * Initialization block
		 */
		
		Server = null;
		serverPort = 0;
		sc = null;
		serverIn = null;
		userIn = null;
		serverOut = null;
		IOe = null;
		name = "";
		serverIP = "";
		ClientThread = new Thread(this, "Client");
		ClientThread.start();
	}
	
	@Override
	public void run() {
		/*
		 * Start block
		 */
		System.out.println(line);
		System.out.println(welcomeMsg);
		System.out.println(line);
		
		sc = new Scanner(System.in);
		
		/*
		 * Connection init block
		 */
		while(true){
			sc = new Scanner(System.in);
			System.out.println("Input number of server's IP:");
			serverIP = sc.nextLine();
			System.out.println("Input number of server's port:");
			serverPort = sc.nextInt();
			int times = 1;
			IOe = null;
			while (times <= 1 ){
				try {					
					System.out.println("Connecting to "+serverIP+/*args[0]+*/":"+serverPort+" ...");
					Server = new Socket("localhost", serverPort);
					serverIn = new BufferedReader(new InputStreamReader(Server.getInputStream()));
					serverOut = new PrintWriter(Server.getOutputStream(), true);
					userIn = new BufferedReader(new InputStreamReader(System.in));
					System.out.println("Connected!");
					break;
				}catch(IOException e){
					System.out.println(connectionFailedMsg);
					System.out.println();
					IOe = e;
					times++;
				}
			}
			if (times < 11 && IOe == null){
				
				break;
			}
		}
		
		
		String userMsg, serverMsg;
		try{
		while (true){
			serverMsg  = serverIn.readLine();
			System.out.println(serverMsg);
			userMsg = userIn.readLine();
			serverOut.println(userMsg);
			if(userMsg.equalsIgnoreCase("close")){
				serverOut.println(userMsg); 
				break;
			}
			//if(userMsg.equalsIgnoreCase("exit")) break;
		}
		
		sc.close();
		serverOut.close();
		userIn.close();
		serverIn.close();
		Server.close();
		System.out.println(connectionClosedMsg);
		System.out.println(byeMsg);
		}catch(IOException e){
			
		}
	}
}
