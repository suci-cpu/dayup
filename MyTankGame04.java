package down.swing_draw_.tank_all_.tank04;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * @author 王靖
 * tank上下左右移动
 * 敌人tank
 */
public class MyTankGame04 extends JFrame {
    MyPanel mp = null;

    public static void main(String[] args) {
        MyTankGame04 myTankGame04 = new MyTankGame04();

    }

    public MyTankGame04() {
        mp = new MyPanel();
        new Thread(mp).start();
        this.add(mp);
        this.setSize(1300, 750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("游戏退出");
                try {
                    Recorder.saveGrade();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
}
