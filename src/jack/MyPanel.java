package jack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

@SuppressWarnings({"all"})
class MyPanel extends JPanel implements KeyListener, Runnable {
    Hero hero = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 3;
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    //同理，在构造器初始化Tank
    public MyPanel() {
        //创建我的坦克
        hero = new Hero(300, 300);
        hero.setSpeed(10);

        //敌人坦克有多个，可以便利
        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = new EnemyTank((100 * (i + 1)), 0);
            enemyTank.setDirect(2);
            enemyTank.setSpeed(2);
            new Thread(enemyTank).start();

            Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
            //把shot放在集合
            enemyTank.shots.add(shot);
            new Thread(shot).start();
            enemyTanks.add(enemyTank);
        }
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("./20190215210241592.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("./20190215210304713.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("./20190215210323305.png"));
    }

    //打开Jframe时，自动启用paint方法
    public void paint(Graphics g) {
        super.paint(g);
        //游戏界面
        g.fillRect(0, 0, 1000, 750);
        //画我的坦克
        if(hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
        }

//        drawTank(hero.getX() + 100, hero.getY(), g, 1, 0);
//        drawTank(hero.getX() + 200, hero.getY(), g, 2, 0);
//        drawTank(hero.getX() + 300, hero.getY(), g, 3, 0);
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive == true) {
                g.drawOval(shot.x, shot.y, 3, 3);
            } else {
                hero.shots.remove(shot);
            }
        }
        //画敌人坦克

        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.fillOval(shot.x, shot.y, 10, 10);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
        //画我的炮弹
        if (hero.shot != null && hero.shot.isLive == true) {
//            g.fill3DRect(hero.shot.x, hero.shot.y, 1, 1, false);
            g.fillOval(hero.shot.x, hero.shot.y, 3, 3);
        }
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);

            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);

            }
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

    }

    public void hitTank(Shot s, Tank enemyTank) {
        switch (enemyTank.getDirect()) {
            case 0:
              if(s.x >enemyTank.getX()  && s.x < enemyTank.getX() + 40 && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                }
                break;
            case 1:
                if (s.x >= enemyTank.getX() && s.x <= enemyTank.getX() + 40 && s.y >= enemyTank.getY() && s.y <= enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;

                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 2:
                if (s.x >= enemyTank.getX() && s.x <= enemyTank.getX() + 60 && s.y >= enemyTank.getY() && s.y <= enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    enemyTankSize--;
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
                case 3:
                    if(s.x >enemyTank.getX()  && s.x < enemyTank.getX() + 40 && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                        s.isLive = false;
                        enemyTank.isLive = false;
                    }
                    break;

        }
    }

    //画敌人坦克
    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
                break;

        }
        switch (direct) {
            case 0: // Up
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1: // Left
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            case 2: // Down
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3: // Rightw
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
        }


    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println((char) e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            if (hero.getY() > 0) {
                hero.moveUp();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_A) {

            hero.setDirect(1);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            if ((hero.getY() + 90) < 750)

                hero.moveDown();

        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(3);
            if ((hero.getX() + 60) < 1000) {
                hero.moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            /* if(hero.shot == null || !hero.shot.isLive) {*/
            hero.shotEnemyTank();
            /*    }*/
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void hitHero() {
        for(int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for(int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if(hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            for (int j = 0; j < hero.shots.size(); j++) {
                Shot shot = hero.shots.get(j);
                if (shot != null && shot.isLive) {
                    for (int i = 0; i < enemyTanks.size(); i++) {
                        EnemyTank enemyTank = enemyTanks.get(i);

                        hitTank(shot, enemyTank);
                    }
                }
            }
          hitHero();


            this.repaint();

    }
}
}



