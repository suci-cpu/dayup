package down.swing_draw_.tank_all_.tank04;

import java.util.Vector;

/**
 * @author 王靖
 */
public class EnemyTank extends Tank implements Runnable {
    private int sleepTime = 5;
    private Shot shot = null;
    private Vector<Shot> vector = new Vector<>();
    Vector<EnemyTank> enemyTanks = new Vector<>();

    public Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public boolean isShootable() {
        return shootable;
    }

    public void setShootable(boolean shootable) {
        this.shootable = shootable;
    }

    private boolean shootable = true;
    private boolean isLive = true;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Shot getShot() {
        return shot;
    }

    public void setShot(Shot shot) {
        this.shot = shot;
    }

    public Vector<Shot> getVector() {
        return vector;
    }

    public void setVector(Vector<Shot> vector) {
        this.vector = vector;
    }

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true) {
            if (!isLive) {
                break;
            }
            MyPanel mp;
            switch (getDirect()) {
                case 0: {
                    for (int i = 0; i < (int) (Math.random() * 500); i++) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (getY() > getSpeed() && !isTouched()) {
                            moveUP();
                        }
                    }
                    break;
                }
                case 1: {
                    for (int i = 0; i < (int) (Math.random() * 500); i++) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (getX() > getSpeed() && !isTouched()) {
                            moveLeft();
                        }
                    }
                    break;
                }
                case 2: {
                    for (int i = 0; i < (int) (Math.random() * 500); i++) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (getY() + 100 < 750 - getSpeed() && !isTouched()) {
                            moveDown();
                        }
                    }
                    break;
                }
                case 3: {
                    for (int i = 0; i < (int) (Math.random() * 500); i++) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (getX() + 75 < 1000 - getSpeed() && !isTouched()) {
                            moveRight();
                        }
                    }
                    break;
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setDirect((int) (Math.random() * 4));
            shotTank();
        }
    }

    public void shotTank() {
        if (vector.size() > 1) {
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

    public boolean isTouched() {

        switch (this.getDirect()) {
            case 0: {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        if (this.getX() >= enemyTank.getX() &&
                                this.getX() <= enemyTank.getX() + 60 &&
                                this.getY() >= enemyTank.getY() &&
                                this.getY() <= enemyTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() + 60 >= enemyTank.getX() &&
                                this.getX() + 60 <= enemyTank.getX() + 60 &&
                                this.getY() >= enemyTank.getY() &&
                                this.getY() <= enemyTank.getY() + 60) {
                            return true;
                        }
                    }
                }
                break;
            }
            case 1: {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        if (this.getX() >= enemyTank.getX() &&
                                this.getX() <= enemyTank.getX() + 60 &&
                                this.getY() >= enemyTank.getY() &&
                                this.getY() <= enemyTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX()>= enemyTank.getX() &&
                                this.getX()<= enemyTank.getX() + 60 &&
                                this.getY() + 60 >= enemyTank.getY() &&
                                this.getY() + 60 <= enemyTank.getY() + 60) {
                            return true;
                        }
                    }
                }
                break;
            }
            case 2: {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        if (this.getX() >= enemyTank.getX() &&
                                this.getX() <= enemyTank.getX() + 60 &&
                                this.getY() + 60 >= enemyTank.getY() &&
                                this.getY() + 60 <= enemyTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() + 60 >= enemyTank.getX() &&
                                this.getX() + 60 <= enemyTank.getX() + 60 &&
                                this.getY() + 60 >= enemyTank.getY() &&
                                this.getY() + 60 <= enemyTank.getY() + 60) {
                            return true;
                        }
                    }
                }
                break;
            }
            case 3: {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this) {
                        if (this.getX() + 60 >= enemyTank.getX() &&
                                this.getX() + 60 <= enemyTank.getX() + 60 &&
                                this.getY() >= enemyTank.getY() &&
                                this.getY() <= enemyTank.getY() + 60) {
                            return true;
                        }

                        if (this.getX() + 60 >= enemyTank.getX() &&
                                this.getX() + 60 <= enemyTank.getX() + 60 &&
                                this.getY() + 60 >= enemyTank.getY() &&
                                this.getY() + 60 <= enemyTank.getY() + 60) {
                            return true;
                        }
                    }
                }
                break;
            }
        }
        return false;
    }
}
