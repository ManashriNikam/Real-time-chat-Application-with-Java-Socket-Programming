package ChatApp.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class ChatServer {
    public static Set<ClientHandler> clients=Collections.synchronizedSet(new HashSet<>());
    public static Map<String,ChatRoom> rooms=new HashMap<>();
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket=new ServerSocket(1234);
        System.out.println("Chat server started on port 1234");
        while (true) {
            Socket socket=serverSocket.accept();
            ClientHandler client=new ClientHandler(socket);
            clients.add(client);
            client.start(); 
            
        }}
    }


