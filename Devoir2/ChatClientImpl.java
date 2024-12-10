import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
    private String name;
    private ChatServer server;
  

    public ChatClientImpl(String name, ChatServer server) throws RemoteException {
        this.name = name;
        this.server = server;
        server.registerClient(this.name,this);
    }

    public void startChat() throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        String message;
        System.out.println("Chat started. Type 'exit' to quit.");

        while (true) {
            message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                break;
            }
            
           server.broadcastMessage(name,name + ": " + message);
       
        }

        System.out.println("Chat ended.");
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    public static void main(String[] args) {
        try {
            String serverURL = "rmi://localhost/ChatServer";
            ChatServer server = (ChatServer) Naming.lookup(serverURL);

            System.out.print("Enter your name: ");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();

            ChatClientImpl client = new ChatClientImpl(name, server);
            client.startChat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

