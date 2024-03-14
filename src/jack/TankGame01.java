package jack;

import javax.swing.*;
//创建mp并在构造器初始化
public class TankGame01 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {

        TankGame01 tankGame01 = new TankGame01();

    }
//初始化mp，把mp加入到JFrame，设置JFrame大小，键盘监听mp，可视化
    public TankGame01() {
        mp = new MyPanel();
        this.add(mp);
        this.setSize(1000, 750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        new Thread(mp).start();


    }
}
