package ChatApp.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("localhost",1234);
        BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
        new Thread(()->{
            try {
                String msg;
                while((msg=in.readLine())!=null){
                    System.out.println(msg);
                }
            } catch (Exception e) {}
        }).start();
        String input;
        while((input=userInput.readLine())!=null){
            out.println(input);
        }
        
    }
    
}
