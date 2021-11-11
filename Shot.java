package down.swing_draw_.tank_all_.tank04;

/**
 * @author 王靖
 */
public class Shot implements Runnable {
    private int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    private int y;
    private int direct = 0;
    private int speed = 5;
    private boolean isLive = true;


    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (true) {
            EnemyTank enemyTank;
//            System.out.println(x + " " + y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direct) {
                case 0: {
                    y -= speed;
                    break;
                }
                case 1: {
                    x -= speed;
                    break;
                }
                case 2: {
                    y += speed;
                    break;
                }
                case 3: {
                    x += speed;
                    break;
                }
            }
            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isLive)) {
                isLive = false;
                break;
            }
        }
    }
}
