import java.awt.*;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        boolean gameOn = true;
        Semaphore sem = new Semaphore(2);
        sem.acquire();
        MyMap map = new MyMap();
        User user = new User(map, sem);
        MyFrame frame = new MyFrame(user, map);
        PC mpc = new PC(map, sem, frame);

        Thread u = new Thread(user);
        Thread m = new Thread(mpc);
        u.start();
        Thread.sleep(1000);
        m.start();
        map.winnerCheck();

        while (gameOn) {
            frame.setTitle("Virus war");
            if (map.laps >= 100) {
                map.winnerCheck();
                if (map.haveWinner){
                    frame.setTitle(map.winnerCheck());
                    u.stop();
                    m.stop();
                    frame.disableButtons();
                    gameOn = false;
                }
            }
        }
    }
}