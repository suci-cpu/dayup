package down.swing_draw_.tank_all_.tank04;

import java.io.*;

/**
 * @author 王靖
 */
public class Recorder {
    private static int wastedEnemyCount;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "D:\\java\\PROJECT\\study_way\\out\\file\\grade.txt";



    public static int getWastedEnemyCount() {
        return wastedEnemyCount;
    }

    public static void setWastedEnemyCount(int wastedEnemyCount) {
        Recorder.wastedEnemyCount = wastedEnemyCount;
    }

    public static void getGrade() throws IOException {
        br = new BufferedReader(new FileReader(recordFile));
        wastedEnemyCount = br.read();
        br.close();

    }
    public static void saveGrade() throws IOException {
        bw = new BufferedWriter(new FileWriter(recordFile));
        bw.write(wastedEnemyCount);
        bw.close();
    }
    public static void addCount(){
        wastedEnemyCount++;
    }
}
