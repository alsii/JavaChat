package server;
import java.io.*;
import java.net.*;

public class User implements Runnable {
	private String name = "Undefined User";
	private Socket socket = null;
	public  BufferedReader in = null;
	public 	PrintWriter out = null;
	
	//private Server server = null;
	private Thread userThread = null;
	
	
	public User (Socket socket/*, Server server*/, String name)throws IOException{
		this.socket = socket;
		//this.server = server;
		this.name = name;
		userThread = new Thread(this, "User");
		userThread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			System.out.println("making new streams");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			System.out.println("success");
		}catch(IOException e){
		}
		String input;
		while (true){
			try{
				System.out.println("making new user");
				out.println("Enter your nickname:");
				this.name = in.readLine();
				out.println("Accepted! You are sucessfuly logged in! Type your messegas!");
				System.out.println("success");
				break;
			}catch(IOException e){
				out.println("I/O Error. Try again!");
			}
		}
		
		try {
			while ((input = in.readLine())!=null){
				if (input.equalsIgnoreCase("exit")) break;
				out.print(name + ": "+ input);
				System.out.println("User " + name + " said:"+input);
				break;
			}
		} catch (IOException e) {
		}
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
		}
		
		System.out.println("User "+ name + " disconnected.");
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Socket getSocket(){
		return socket;
	}
	
}
