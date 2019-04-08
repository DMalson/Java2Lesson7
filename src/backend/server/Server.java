package backend.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    static final int PORT = 8666;
    List<ClientHandler> clients = new ArrayList<>();


    public Server() {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server is launched");

            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("Server finished his work");
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void sendMsgToAllClients(String msg) {
        for (ClientHandler client : clients) {
            client.sendMsg(msg);
        }
    }

    public void sendMsgToAClient(String clientName, String msg){
        for (ClientHandler client : clients) {
            if (clientName.equalsIgnoreCase(client.getClientName())) {
                client.sendMsg(msg);
                break;
            }
        }
    }

    public void removeClientFromServer(ClientHandler client) {
        clients.remove(client);
    }
}
