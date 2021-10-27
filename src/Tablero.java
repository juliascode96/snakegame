import javax.swing.*;
import java.awt.*;

public class Tablero extends JFrame {
    Tablero() {
        this.add(new Panel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }


}
