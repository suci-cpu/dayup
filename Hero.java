package down.swing_draw_.tank_all_.tank04;

import java.util.Vector;

/**
 * @author 王靖
 */
public class Hero extends Tank {
    private Vector<Shot> vector = new Vector<>();
    private boolean isLive = true;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Vector<Shot> getVector() {
        return vector;
    }

    public void setVector(Vector<Shot> vector) {
        this.vector = vector;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    private Shot shot = null;

    public Shot getShot() {
        return shot;
    }

    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotEnemyTank() {
        if(vector.size() > 5){
            return;
        }
        switch (getDirect()) {
            case 0: {
                shot = new Shot(getX() + 30, getY() - 15, 0);
                vector.add(shot);
                break;
            }
            case 1: {
                shot = new Shot(getX() - 15, getY() + 30, 1);
                vector.add(shot);
                break;
            }
            case 2: {
                shot = new Shot(getX() + 30, getY() + 75, 2);
                vector.add(shot);
                break;
            }
            case 3: {
                shot = new Shot(getX() + 75, getY() + 30, 3);
                vector.add(shot);
                break;
            }
        }
        new Thread(shot).start();
    }

}
