import java.rmi.RemoteException;

public class GomokuClientImp
        implements GomokuClient{

    protected static final String localhost = "127.0.0.1";
    protected static final String RMI_HOSTNAME = "java.rmi.server.hostname";
    protected static final String SERVICE_PATH = "rmi://localhost/GomokuServerImp";

    private GomokuGameField mGameField = null;
    private Field mField = null;
    protected GomokuServer mServer = null;
    private int mType = Cell.EMPTY;
    protected boolean mTurn = false;
    private boolean mWin = false;
    private boolean mGameOver = false;

    public GomokuClientImp() throws RemoteException {
        super();
        mField = new Field(Field.DEFAULT_COUNT);
    }

    public void setGameField(GomokuGameField aGameField) {
        mGameField = aGameField;
    }

    @Override
    public boolean click(int x, int y) throws RemoteException {
        System.out.println("Click on " + x + " " + y);
        if(!mTurn) return false;
        boolean isOkay = mServer.clickFromPlayer(x, y, mType);
        if(isOkay) {
            System.out.println("Click okay!");
            mTurn = false;
        }
        return isOkay;
    }

    @Override
    public void startPlay(boolean aTurn) throws RemoteException {
        mTurn = aTurn;
        System.out.println("Start Play!");
        MainForm form = new MainForm(new GomokuGameField(this));
        form.setVisible(true);
    }

    @Override
    public boolean updateCell(int aX, int aY, int aType) throws RemoteException {
        boolean isOkay = mField.setCellType(aX, aY, aType);
        mGameField.repaint();
        return isOkay;
    }

    @Override
    public void setTurn(boolean aTurn) throws RemoteException {
        mTurn = aTurn;
    }

    @Override
    public Field getField() {
        return mField;
    }

    public boolean isTurn() { return mTurn; }

    public boolean isWin() { return mWin; }

    public boolean isGameOver() { return mGameOver; }

    @Override
    public int getType() throws RemoteException {
        return mType;
    }

    @Override
    public void setWin(boolean aWin) throws RemoteException {
        mWin = aWin;
    }

    @Override
    public void stopGame() throws RemoteException {
        mGameOver = true;
        System.out.println("Game Over!");
    }
}
