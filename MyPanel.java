package down.swing_draw_.tank_all_.tank04;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Vector;

/**
 * @author 王靖
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    private boolean 无敌 = true;
    static Hero hero = null;
    static Vector<EnemyTank> enemyTanks = new Vector<>();
    static int enemyTankSize = 3;

    public static Hero getHero() {
        return hero;
    }

    public static void setHero(Hero hero) {
        MyPanel.hero = hero;
    }

    public MyPanel() {

        hero = new Hero(20, 620);
        try {
            Recorder.getGrade();
        } catch (IOException e) {
            e.printStackTrace();
        }
        hero.setSpeed(10);

        for (int i = 0; i < enemyTankSize; i++) {
            EnemyTank enemyTank = new EnemyTank((i + 1) * 100, 0);
            enemyTank.setDirect(2);
            enemyTank.setSpeed(2);
            new Thread(enemyTank).start();
            Shot shot = new Shot(enemyTank.getX() + 30, enemyTank.getY() + 60, enemyTank.getDirect());
            enemyTank.getVector().add(shot);
            new Thread(shot).start();
            enemyTanks.add(enemyTank);
        }
        for (int i = 0; i < enemyTankSize; i++) {
            enemyTanks.get(i).setEnemyTanks(enemyTanks);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        showInfo(g);
        drawTank(1020,60,g,0,1);
        g.setColor(Color.black);
        g.fillRect(0, 0, 1000, 750);
        if (hero.isLive()) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
            Vector<Shot> vector = hero.getVector();
            for (int i = 0; i < vector.size(); i++) {
                Shot shot = vector.get(i);
                if (shot != null && shot.isLive()) {
                    drawBullet(shot, g);
                } else {
                    hero.getVector().remove(shot);
                }
            }
        }
        if (!hero.isLive()) {
            g.setColor(Color.YELLOW);
            g.fillOval(hero.getX() - 20, hero.getY() - 20, 100, 100);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hero.setLive(true);
            hero.setX(20);
            hero.setY(620);
        }

        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive()) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
            } else {
                g.setColor(Color.cyan);
                g.fillOval(enemyTank.getX(), enemyTank.getY(), 60, 60);
                enemyTanks.remove(enemyTank);
            }
            Vector<Shot> vector2 = enemyTank.getVector();

            for (int j = 0; j < vector2.size(); j++) {
                Shot shot = vector2.get(j);
                if (shot.isLive()) {
                    hitTank(shot, hero);
                    g.fillOval(shot.getX() - 4, shot.getY() - 4, 8, 8);
                } else {
                    enemyTank.getVector().remove(shot);
                }
            }

        }


    }

    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0: {
                g.setColor(Color.yellow);
                break;
            }
            case 1: {
                g.setColor(Color.cyan);
                break;
            }
        }
        switch (direct) {
            case 0: {//上
                g.fill3DRect(x, y, 10, 60, false);//左轮子
                g.fill3DRect(x + 50, y, 10, 60, false);//右轮子
                g.fill3DRect(x + 10, y + 10, 40, 40, false);//盖子
                g.fillOval(x + 10, y + 10, 40, 40);//小盖子
                g.drawLine(x + 30, y + 30, x + 30, y - 15);//炮筒

                break;
            }
            case 1: {//左
                g.fill3DRect(x, y, 60, 10, false);//左轮子
                g.fill3DRect(x, y + 50, 60, 10, false);//右轮子
                g.fill3DRect(x + 10, y + 10, 40, 40, false);//盖子
                g.fillOval(x + 10, y + 10, 40, 40);//小盖子
                g.drawLine(x + 30, y + 30, x - 15, y + 30);//炮筒

                break;
            }
            case 2: {//下
                g.fill3DRect(x, y, 10, 60, false);//左轮子
                g.fill3DRect(x + 50, y, 10, 60, false);//右轮子
                g.fill3DRect(x + 10, y + 10, 40, 40, false);//盖子
                g.fillOval(x + 10, y + 10, 40, 40);//小盖子
                g.drawLine(x + 30, y + 30, x + 30, y + 75);//炮筒

                break;
            }
            case 3: {//右
                g.fill3DRect(x, y, 60, 10, false);//左轮子
                g.fill3DRect(x, y + 50, 60, 10, false);//右轮子
                g.fill3DRect(x + 10, y + 10, 40, 40, false);//盖子
                g.fillOval(x + 10, y + 10, 40, 40);//小盖子
                g.drawLine(x + 30, y + 30, x + 75, y + 30);//炮筒

                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            if (hero.getY() > hero.getSpeed()) {
                hero.moveUP();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(1);
            if (hero.getX() > hero.getSpeed()) {
                hero.moveLeft();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            if (hero.getY() + 100 < 735 - hero.getSpeed()) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(3);
            if (hero.getX() + 75 < 1000 - hero.getSpeed()) {
                hero.moveRight();
            }
        }


        if (e.getKeyCode() == KeyEvent.VK_J) {
            hero.shotEnemyTank();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void drawBullet(Shot bullet, Graphics g) {
        if (bullet == null || !bullet.isLive()) {
            return;
        }
        g.setColor(Color.YELLOW);
        g.fillOval(bullet.getX() - 4, bullet.getY() - 4, 8, 8);
        //    this.repaint();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hitEnemyTank();
            this.repaint();
        }
    }

    public void hitTank(Shot shot, Tank tank) {
        if (shot.getX() >= tank.getX() && shot.getX() <= tank.getX() + 60 &&
                shot.getY() >= tank.getY() && shot.getY() <= tank.getY() + 60) {
            shot.setLive(false);
            if (tank instanceof EnemyTank) {
                ((EnemyTank) tank).setLive(false);
                Recorder.addCount();
            }
            if (tank instanceof Hero) {
                if (无敌) {
                    ((Hero) tank).setLive(true);
                } else {
                    ((Hero) tank).setLive(false);
                }


            }
        }

    }

    public void hitEnemyTank() {
        Vector<Shot> vector = hero.getVector();
        for (int i = 0; i < vector.size(); i++) {
            Shot shot = vector.get(i);
            if (shot.isLive()) {
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }

        }
    }

    public void drawEnemyBullet() {

    }

    public static boolean area(int direct) {
        boolean flags = true;
        switch (direct) {
            case 0: {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    for (int j = 0; j < enemyTanks.size(); j++) {
                        EnemyTank enemyTank2 = enemyTanks.get(j);
                        if (enemyTank.getX() == enemyTank2.getX() && enemyTank.getY() == enemyTank2.getY()) {
                            continue;
                        }
                        if (enemyTank.getX() >= enemyTank2.getX() &&
                                enemyTank.getX() <= enemyTank2.getX() + 60 &&
                                enemyTank.getY() >= enemyTank2.getY() &&
                                enemyTank.getY() >= enemyTank2.getY() + 60) {
                            flags = true;
                        }
                    }
                }
                flags = false;
                break;
            }
            case 1: {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    for (int j = 0; j < enemyTanks.size(); j++) {
                        EnemyTank enemyTank2 = enemyTanks.get(j);
                        if (enemyTank.getX() == enemyTank2.getX() && enemyTank.getY() == enemyTank2.getY()) {
                            continue;
                        }
                        if (enemyTank.getX() - enemyTank2.getX() > 60 ||
                                Math.abs(enemyTank.getY() - enemyTank2.getY()) > 60) {
                            flags = true;
                        }
                    }
                }
                flags = false;
                break;
            }
            case 2: {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    for (int j = 0; j < enemyTanks.size(); j++) {
                        EnemyTank enemyTank2 = enemyTanks.get(j);
                        if (enemyTank.getX() == enemyTank2.getX() && enemyTank.getY() == enemyTank2.getY()) {
                            continue;
                        }
                        if (Math.abs(enemyTank.getX() - enemyTank2.getX()) > 60 ||
                                enemyTank2.getY() - enemyTank.getY() > 60) {
                            flags = true;
                        }
                    }
                }
                flags = false;
                break;
            }
            case 3: {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    for (int j = 0; j < enemyTanks.size(); j++) {
                        EnemyTank enemyTank2 = enemyTanks.get(j);
                        if (enemyTank.getX() == enemyTank2.getX() && enemyTank.getY() == enemyTank2.getY()) {
                            continue;
                        }
                        if (enemyTank.getX() - enemyTank2.getX() > 60 ||
                                Math.abs(enemyTank2.getY() - enemyTank.getY()) > 60) {
                            flags = true;
                        }
                    }
                }
                flags = false;
                break;
            }
        }
        return flags;
    }

    public static boolean isMovable() {
        int area = 150;
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTanks.size(); j++) {
                EnemyTank enemyTank2 = enemyTanks.get(j);
                if (i == j) {
                    continue;
                }
                if (Math.abs(enemyTank.getX() - enemyTank2.getX()) > area ||
                        Math.abs(enemyTank.getY() - enemyTank2.getY()) > area) {
                    return true;
                }
            }
        }
        return false;
    }

    public void showInfo(Graphics g){
        g.setColor(Color.black);
        Font font = new Font("宋体",Font.BOLD,25);
        g.setFont(font);
        g.drawString("您累计击毁地方坦克",1020,30);
        g.setColor(Color.black);
        g.drawString(Recorder.getWastedEnemyCount() + "",1120,100);
    }
}
