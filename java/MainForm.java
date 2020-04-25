import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {

    private static final int DEFAULT_WIDTH = Field.DEFAULT_COUNT * GomokuGameField.CELL_SIZE + 7;
    private static final int DEFAULT_HEIGHT = DEFAULT_WIDTH + 23;
    private static final int DEFAULT_MARGIN = 50;

    public MainForm(GomokuGameField aGameField)
    {
        /* Form's settings */
        setTitle("Gomoku Game");
        setBounds(DEFAULT_MARGIN, DEFAULT_MARGIN, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(aGameField, BorderLayout.CENTER);

        setVisible(true);
    }
}
