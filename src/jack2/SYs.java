package jack2;

import javax.swing.*;

public class SYs extends JFrame {
    Mypanel mp = null;
    public static void main(String[] args) {
        SYs sYs = new SYs();

    }
    public SYs() {
        mp = new Mypanel();
        this.add(mp);
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


}
