package backend.server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private static int clientsCount = 0;
    private Server server;
    private Socket clientSocket;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private String clientName;

    public ClientHandler(Socket clientSocket, Server server) {
        try {
            clientsCount++;
            this.server = server;
            this.clientSocket = clientSocket;
            this.outMessage = new PrintWriter(clientSocket.getOutputStream());
            this.inMessage = new Scanner(clientSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
    }

    public String getClientName() {
        return clientName;
    }

    private void close() {
        server.removeClientFromServer(this);
        clientsCount--;
        server.sendMsgToAllClients("In our chat client count is " + clientsCount);
    }

    public void sendMsg(String msg) {
        outMessage.println(msg);
        outMessage.flush();
    }

    @Override
    public void run() {

        try {
            server.sendMsgToAllClients("We have a new clients");

            while (true) {
                if (inMessage.hasNext()) {
                    String clientMsg = inMessage.nextLine();
                    System.out.println(clientMsg);

                    if (clientMsg.equalsIgnoreCase("EXIT")) {
                        break;
                    }
                    String[] clientMsgInArray = new String[];
                    clientMsgInArray = clientMsg.split(" ", 3);
                    if (clientMsgInArray[0].equalsIgnoreCase("/w") {
                        server.sendMsgToAClient(clientMsgInArray[1],clientMsgInArray[2]);
                    } else{
                        server.sendMsgToAllClients(clientMsg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close();
        }


    }
}
