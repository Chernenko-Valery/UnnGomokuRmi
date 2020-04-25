import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GomokuClient extends Remote {

    public Field getField() throws RemoteException;

    public int getType() throws RemoteException;

    public void setTurn(boolean aTurn) throws RemoteException;

    public void startPlay(boolean aTurn) throws RemoteException;

    public boolean click(int x, int y) throws RemoteException;

    public boolean updateCell(int aX, int aY, int aType) throws RemoteException;

    public void setWin(boolean aWin) throws RemoteException;

    public void stopGame() throws RemoteException;
}
