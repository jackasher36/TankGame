package jack2;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;

public class Mypanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Image image = Toolkit.getDefaultToolkit().getImage(Mypanel.class.getResource("20190215210241592.png"));

        g.drawImage(image, 100, 100, this);
    }
}
