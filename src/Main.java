import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Container contentPane;
        Solitaire.frame.setSize(Solitaire.TABLE_WIDTH, Solitaire.TABLE_HEIGHT);
        Solitaire.table.setLayout(null);
        Solitaire.table.setBackground(new Color(0, 180, 0));
        contentPane = Solitaire.frame.getContentPane();
        contentPane.add(Solitaire.table);
        Solitaire.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Solitaire.playNewGame();
        Solitaire.table.addMouseListener(new CardMovement());
        Solitaire.table.addMouseMotionListener(new CardMovement());
        Solitaire.frame.setVisible(true);
    }
}