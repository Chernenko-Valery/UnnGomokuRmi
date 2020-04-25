import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GomokuServer extends Remote {

    public boolean bindAsWhitePlayer() throws RemoteException, NotBoundException, MalformedURLException;
    public boolean bindAsBlackPlayer() throws RemoteException, NotBoundException, MalformedURLException;
    public boolean clickFromPlayer(int aX, int aY, int aType) throws RemoteException;

}
