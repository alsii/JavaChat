package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	
	public static String line = 		"------------------------------------";
	public static String welcomeMsg = 	"#     Welcome to Sieve-Server!     #";
	public static String byeMsg = "Sieve-Server is closed! Good bye!";
	
	ArrayList<User> userList = new ArrayList<User>();
	int port = 555;
	ServerSocket Listener = null;
	Socket clientSocket = null;
	BufferedReader in = null;
	PrintWriter out = null;
	User nUser = null;
	public Server()throws IOException, InterruptedException {
		
		System.out.println(welcomeMsg);
		System.out.println("Server started");
		
		Scanner sc = new Scanner(System.in);
		while (true){
			try {
				System.out.println("Input number of Server's port");
				port = sc.nextInt();
				Listener = new ServerSocket(port);
				ServerInfo();
				break;
			}catch(IOException e) {
				System.out.println("Couldn't listen to port " + port + "! Try again.");
			}
		}
		sc.close();
		
		System.out.println();
		System.out.println("Ready! Wait for user connections.");
		String input;
		while (true){
			try {
				clientSocket = Listener.accept();
				try{
					in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					out = new PrintWriter(clientSocket.getOutputStream());
				}catch(IOException e){
				}
				while (true){
					try{
						//Thread.sleep(100);
						out.println("Enter your nickname:");
						input = in.readLine();
						out.println("Accepted! You are sucessfuly logged in! Type your messegas!");
						break;
					}catch(IOException e){
						out.println("I/O Error. Try again!");
					}
				}
				nUser = new User(clientSocket, input);
				userList.add(nUser);
				System.out.println("Client "+ userList.get(userList.size()-1)
						.getSocket().getInetAddress()+" successfuly connected!");
				in.close();
				out.close();
			}catch(IOException e) {
				//System.out.println("Accept fault! Waiting for another client...");
			}
			
			
		}
	}
	
	private void ServerInfo(){
		System.out.println(line);
		System.out.println("#           Server Info            #");
		System.out.println("IP-address: "+Listener.getInetAddress());
		System.out.println("Port: "+port);
		System.out.println(line);
	}
}
