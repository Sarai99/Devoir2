import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
    void registerClient(String clientName,ChatClient client) throws RemoteException;
    void broadcastMessage(String sender,String message) throws RemoteException;
}

