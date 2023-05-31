import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class PC implements Runnable {
    private MyMap map;
    private MyFrame frame;
    Semaphore semaphore;
    private final int symbol;

    public PC(MyMap m, Semaphore s, MyFrame f) {
        map = m;
        frame = f;
        this.semaphore = s;
        this.symbol = 2;
    }

    public void FirstMove() {
        map.changeSymbol(99, symbol);
        frame.getButton(99).setText("o");
        map.ShowMyMap();
    }

    public int enterStep() {
        ArrayList<Integer> possibleSteps = map.mpcPossibleSteps();
        if (possibleSteps.size() > 0) {
            for (int i = 0; i < possibleSteps.size(); i++) {
                if (map.coordinateSymbol(possibleSteps.get(i)) == 1) {
                    return possibleSteps.get(i);
                }
            }

            Random random = new Random();
            int place = random.nextInt(possibleSteps.size());
            return possibleSteps.get(place);
        }
        return -1;
    }

    public void Move() {
        int coordinate = enterStep();
        if (coordinate >= 0) {
            if (map.coordinateSymbol(coordinate) == 1) {
                map.changeSymbol(coordinate, 4);
                frame.getButton(coordinate).setBackground(Color.blue);
                frame.getButton(coordinate).setEnabled(false);
            }
            if (map.coordinateSymbol(coordinate) == 0) {
                map.changeSymbol(coordinate, 2);
                frame.getButton(coordinate).setText("o");
            }
            map.ShowMyMap();

            System.out.println(map.userPossibleSteps() + ":User possible steps");
            System.out.println(map.mpcPossibleSteps() + ":MPC possible steps");
        }
    }


    @Override
    public void run() {
        try {
            semaphore.acquire();
            FirstMove();
            Move();
            Move();
            semaphore.release();
            Thread.sleep(1000);
            while (true) {
                semaphore.acquire();
                for (int i = 0; i < 3; i++) {
                    Move();
                    Thread.sleep(200);
                }
                semaphore.release();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
