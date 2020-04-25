import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class GomokuGameField extends JPanel {

    public static final int CELL_SIZE = 50;
    public static final int FIELD_SIZE = CELL_SIZE * Field.DEFAULT_COUNT;

    private GomokuClientImp mClient;

    public GomokuGameField(GomokuClientImp aClient) {
        setVisible(false);
        mClient = aClient;
        aClient.setGameField(this);
        repaint();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;
                if(mClient.isGameOver()) return;
                try {
                    mClient.click(x, y);
                } catch (RemoteException aE) {
                    System.out.println(aE.getMessage());
                }
            }
        });
        setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Рисуем линии, что представляют из себя сетку.
        for (int i = 0; i <= mClient.getField().getCount(); i++) {
            g.drawLine(0, i * CELL_SIZE, FIELD_SIZE, i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, FIELD_SIZE);
        }
        for (int i = 0; i < mClient.getField().getCount(); i++) {
            for (int j = 0; j < mClient.getField().getCount(); j++) {
                if (mClient.getField().getCellType(i, j) != Cell.EMPTY) {
                    if (mClient.getField().getCellType(i,j) == Cell.WHITE) {
                        g.setColor(Color.WHITE);
                        g.fillOval((i * CELL_SIZE), (j * CELL_SIZE), CELL_SIZE, CELL_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawOval((i * CELL_SIZE), (j * CELL_SIZE), CELL_SIZE, CELL_SIZE);
                    }
                    if (mClient.getField().getCellType(i, j) == Cell.BLACK) {
                        g.setColor(Color.BLACK);
                        g.fillOval((i * CELL_SIZE), (j * CELL_SIZE), CELL_SIZE, CELL_SIZE);
                        g.setColor(Color.WHITE);
                        g.drawOval((i * CELL_SIZE), (j * CELL_SIZE), CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }

        if(mClient.isGameOver()) {
            g.setColor(Color.BLACK);
            g.fillRect(0, FIELD_SIZE / 2, FIELD_SIZE, FIELD_SIZE / 8);
            g.setColor(Color.RED);
            g.setFont(new Font("Tahoma", 10, 40));
            if(mClient.isWin()) {
                g.drawString("You Win!", 0, 19 * FIELD_SIZE / 32);
            } else {
                g.drawString("You Lose!", 0, 19 * FIELD_SIZE / 32);
            }

        }
    }
}
