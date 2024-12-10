import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    private Map<String, ChatClient> clients;

    public ChatServerImpl() throws RemoteException {
        clients = new HashMap<>();
    }

    @Override
    public void registerClient(String clientName, ChatClient client) throws RemoteException {
        clients.put(clientName, client);
        System.out.println("Client registered: " + clientName);
    }

    @Override
    public void broadcastMessage(String sender, String message) throws RemoteException {
   
    	
        for (String clientName : clients.keySet()) {
        if (!clientName.equals(sender)) {
           
           ChatClient client = clients.get(clientName);
           client.receiveMessage(message);
        }
        else
        {
        
  
        }
       }
    }
    
    public static void main(String[] args) {
    try {
        ChatServerImpl server = new ChatServerImpl();
        String serverURL = "rmi://localhost/ChatServer";
        Naming.rebind(serverURL, server);
        System.out.println("Chat server started.");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}

