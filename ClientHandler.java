package ChatApp.server;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends Thread{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;
    private ChatRoom currentRoom;
    
    public ClientHandler(Socket socket){
        this.socket=socket;
    }
    public String getUsername(){
        return username;
    }
    public void sendMessage(String msg){
        out.println(msg);
    }
    public void run(){
        try {
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(),true);
            out.println("Enter username : ");
            username=in.readLine();
            out.println("Welcome "+username);
            out.println("Commands: /join room| /msg user message | /exit");
            String message;
            while((message=in.readLine())!=null){
                if(message.startsWith("/join")){
                    String roomName=message.split(" ")[1];
                    currentRoom=ChatServer.rooms.computeIfAbsent(roomName,ChatRoom::new);
                    currentRoom.join(this);
                }
                else if(message.startsWith("/msg")){
                    String[] parts=message.split(" ",3);
                    privateMessage(parts[1],parts[2]);
                }
                else if(message.equals("/exit")){
                    break;
                }
                else if(currentRoom != null){
                    String time=new SimpleDateFormat("HH:mm").format(new Date());
                    currentRoom.broadcast("["+ time +"]"+ username+":"+message);
                }
            }

            
        
        }catch(Exception e){
            System.err.println("Client dissconnected");
        }
    }
    private void privateMessage(String target,String msg){
        for(ClientHandler c:ChatServer.clients){
            if(c.getUsername().equals(target)){
                c.sendMessage("Private from "+username +": "+msg);
            }
        }
    }
}
