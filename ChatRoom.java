package ChatApp.server;
import java.util.*;

public class ChatRoom {
    private String roomName;
    private Set<ClientHandler> members=new HashSet<>();
    public ChatRoom(String roomName){
        this.roomName=roomName;
    }
    public synchronized void join(ClientHandler client){
        members.add(client);
        broadcast(client.getUsername()+" joined the room");
    }
    public synchronized void leave(ClientHandler client){
        members.remove(client);
        broadcast(client.getUsername()+" left the room");
    }
    public synchronized void broadcast(String message){
        for(ClientHandler c: members){
            c.sendMessage(message);
        }
    }
}
