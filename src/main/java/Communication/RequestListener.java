package Communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Michał on 2016-03-21.
 */
public class RequestListener {
    public static void main(String[] args){

        int portNumber = Integer.parseInt(args[0]);

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while(true){
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
            }


        } catch(Exception ex){

        }
    }
}
