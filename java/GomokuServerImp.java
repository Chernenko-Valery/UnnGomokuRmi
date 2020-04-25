import com.sun.media.jfxmediaimpl.HostUtils;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GomokuServerImp implements GomokuServer {

    public static final String TAG = "GomokuServerImp";

    private static final String localhost = "127.0.0.1";
    private static final int port = 8136;

    private static final int WIN_COUNT = 5;

    private Field mField;
    private GomokuClient mWhitePlayer = null;
    private GomokuClient mBlackPlayer = null;

    public GomokuServerImp(){
        mField = new Field(Field.DEFAULT_COUNT);
    }

    @Override
    public boolean bindAsWhitePlayer() throws RemoteException, NotBoundException, MalformedURLException {
        if (mWhitePlayer!= null) return false;
        Registry registry = LocateRegistry.getRegistry(localhost, 8137);
        mWhitePlayer = (GomokuClient) registry.lookup(WhiteGomokuClientImp.TAG);
        System.out.println("White player is connected!");
        if (mBlackPlayer != null) {
            mWhitePlayer.startPlay(true);
            mBlackPlayer.startPlay(false);
        }

        return true;
    }

    @Override
    public boolean bindAsBlackPlayer() throws RemoteException, NotBoundException, MalformedURLException {
        if (mBlackPlayer != null) return false;
        Registry registry = LocateRegistry.getRegistry(localhost, 8138);
        mBlackPlayer = (GomokuClient) registry.lookup(BlackGomokuClientImp.TAG);
        System.out.println("Black player is connected!");
        if (mBlackPlayer != null) {
            mWhitePlayer.startPlay(true);
            mBlackPlayer.startPlay(false);
        }
        return true;
    }

    @Override
    public boolean clickFromPlayer(int aX, int aY, int aType) throws RemoteException {
        boolean isOkay = mField.setCellType(aX, aY, aType);
        System.out.println("Click From Player");
        if (isOkay) {
            if(aType == Cell.WHITE) {
                mBlackPlayer.setTurn(true);
                if (checkWin(aX, aY, aType)) {
                    System.out.println("White Win!");
                    mWhitePlayer.setWin(true);
                    mBlackPlayer.setWin(false);
                    mWhitePlayer.stopGame();
                    mBlackPlayer.stopGame();
                }
                mWhitePlayer.updateCell(aX, aY, aType);
                mBlackPlayer.updateCell(aX, aY, aType);
            } else if (aType == Cell.BLACK) {
                mWhitePlayer.setTurn(true);
                if (checkWin(aX, aY, aType)) {
                    System.out.println("Black Win!");
                    mWhitePlayer.setWin(false);
                    mBlackPlayer.setWin(true);
                    mWhitePlayer.stopGame();
                    mBlackPlayer.stopGame();
                }
                mWhitePlayer.updateCell(aX, aY, aType);
                mBlackPlayer.updateCell(aX, aY, aType);
            }
        }
        return isOkay;
    }

    private boolean checkWin(int aX, int aY, int aType) {
        //Check diagonal
        if (checkLeftDiagonal(aX, aY, aType)) return true;
        if (checkRightDiagonal(aX, aY, aType)) return true;
        //Check line
        if (checkXLine(aX, aY, aType)) return true;
        if (checkYLine(aX, aY, aType)) return true;
        System.out.println("Not Win!");
        return false;
    }

    private boolean checkLeftDiagonal(int aX, int aY, int aType) {
        if(mField.getCellType(aX, aY) != aType) {
            return false;
        }
        int count = 1;
        int tmpX = aX - 1;
        int tmpY = aY - 1;
        while(mField.getCellType(tmpX, tmpY) == aType) {
            count++;
            tmpX--;
            tmpY--;
        }
        tmpX = aX + 1;
        tmpY = aY + 1;
        while(mField.getCellType(tmpX, tmpY) == aType) {
            count++;
            tmpX++;
            tmpY++;
        }
        return count == WIN_COUNT;
    }

    private boolean checkRightDiagonal(int aX, int aY, int aType) {
        if(mField.getCellType(aX, aY) != aType) {
            return false;
        }
        int count = 1;
        int tmpX = aX + 1;
        int tmpY = aY - 1;
        while(mField.getCellType(tmpX, tmpY) == aType) {
            count++;
            tmpX++;
            tmpY--;
        }
        tmpX = aX - 1;
        tmpY = aY + 1;
        while(mField.getCellType(tmpX, tmpY) == aType) {
            count++;
            tmpX--;
            tmpY++;
        }
        return count == WIN_COUNT;
    }

    private boolean checkXLine(int aX, int aY, int aType) {
        if(mField.getCellType(aX, aY) != aType) {
            return false;
        }
        int count = 1;
        int tmpX = aX - 1;
        while (mField.getCellType(tmpX, aY) == aType) {
            count++;
            tmpX--;
        }
        tmpX = aX + 1;
        while (mField.getCellType(tmpX, aY) == aType) {
            count++;
            tmpX++;
        }
        return count == WIN_COUNT;
     }

    private boolean checkYLine(int aX, int aY, int aType) {
        if(mField.getCellType(aX, aY) != aType) {
            return false;
        }
        int count = 1;
        int tmpY = aY - 1;
        while (mField.getCellType(aX, tmpY) == aType) {
            count++;
            tmpY--;
        }
        tmpY = aY + 1;
        while (mField.getCellType(aX, tmpY) == aType) {
            count++;
            tmpY++;
        }
        return count == WIN_COUNT;
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        //Создание удаленного RMI Объекта.
        GomokuServerImp server = new GomokuServerImp();
        GomokuServer stub = (GomokuServer) UnicastRemoteObject.exportObject(server, port);
        System.out.println("Initializing " + TAG);

        //Регистрация удаленного RMI Объекта в реестре
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind(TAG, stub);
        System.out.println("Starting " + TAG);
    }
}
