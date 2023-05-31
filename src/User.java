import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class User implements Runnable {
    private Semaphore semaphore;
    private MyMap map;
    private int symbol;
    private int stepCoordinate;
    private volatile int steps;
    private int possibleMoves;


    public User(MyMap m, Semaphore s) {
        possibleMoves = 0;
        steps = 0;
        stepCoordinate = 0;
        map = m;
        semaphore = s;
        symbol = 1;
    }


    public void setUserStepCoordinate(int coordinate) {
        stepCoordinate = coordinate;
    }


    public void FirstMove() {
        map.changeSymbol(0, 1);
        map.ShowMyMap();
        steps++;
    }


    public void Move() {
        if (map.userPossibleSteps().contains(stepCoordinate)) {
            if (map.coordinateSymbol(stepCoordinate) == 0) {
                map.changeSymbol(stepCoordinate, 1);
                map.ShowMyMap();
            }
            if (map.coordinateSymbol(stepCoordinate) == 2) {
                map.changeSymbol(stepCoordinate, 3);
                map.ShowMyMap();
            }

            steps++;
            map.laps++;

            System.out.println(map.userPossibleSteps() + ":User possible steps");
            System.out.println(map.mpcPossibleSteps() + ":MPC possible steps");
        }
    }


    @Override
    public void run() {
        try {
            semaphore.acquire();
            FirstMove();
            while (steps != 3) {
                Thread.onSpinWait();
            }
            steps = 0;
            semaphore.release();
            Thread.sleep(1000);
            while (true) {
                semaphore.acquire();
                while (steps != 3) {
                    Thread.onSpinWait();
                }
                steps = 0;
                semaphore.release();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
