import java.util.ArrayList;
import java.util.Arrays;


class MyMap {
    private final int[][] map;
    boolean haveWinner;
    int laps;
    private ArrayList<Integer> userSteps;
    private ArrayList<Integer> mpcSteps;


    public MyMap() {
        this.laps = 0;
        this.haveWinner = false;
        this.map = new int[10][10];
        userSteps = userPossibleSteps();
        mpcSteps = mpcPossibleSteps();
        FillMap();
    }

    public String winnerCheck() {
        int userWin = 1;
        int mpcWin = 1;

        String msg = null;

        if (laps >= 15) {
            for (Integer userStep : userSteps) {
                if (coordinateSymbol(userStep) == 0 || coordinateSymbol(userStep) == 2) {
                    userWin++;
                }
            }

            for (Integer mpcStep : mpcSteps) {
                if (coordinateSymbol(mpcStep) == 0 || coordinateSymbol(mpcStep) == 1) {
                    mpcWin++;
                }
            }

            if (userWin == 1 || mpcWin == 1) {
                this.haveWinner = true;
                if (userWin == 1) {
                    msg = "MPC wins!";
                } else {
                    msg = "User wins!";

                }
            }
        }
        return msg;
    }


    public void FillMap() {
        for (int[] ints : this.map) {
            Arrays.fill(ints, 0);
        }
    }


    public void ShowMyMap() {
        for (int[] ints : this.map) {
            for (int anInt : ints) {
                System.out.print(anInt + "  ");
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }


    public void changeSymbol(int coordinate, int symbol) {
        int l = coordinate / 10;
        int w = coordinate % 10;
        map[l][w] = symbol;
        userSteps = userPossibleSteps();
        mpcSteps = mpcPossibleSteps();
    }


    public int coordinateSymbol(int coordinate) {
        return map[coordinate / 10][coordinate % 10];
    }


    public ArrayList<Integer> userPossibleSteps() {
        ArrayList<Integer> possibleSteps = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            if (coordinateNearSymbol(i, 1)) {
                possibleSteps.add(i);
            }
        }

        int added = 1;
        ArrayList<Integer> coordinatesToAdd;

        while (added != 0) {
            for (int i = 0; i < possibleSteps.size(); i++) {
                if (coordinateSymbol(possibleSteps.get(i)) == 3) {
                    coordinatesToAdd = SymbolsNearCoordinate(possibleSteps.get(i), 0);
                    for (int c : coordinatesToAdd) {
                        if (!possibleSteps.contains(c)) {
                            possibleSteps.add(c);
                            added++;
                        }
                    }

                    coordinatesToAdd = SymbolsNearCoordinate(possibleSteps.get(i), 2);
                    for (int c : coordinatesToAdd) {
                        if (!possibleSteps.contains(c)) {
                            possibleSteps.add(c);
                            added++;
                        }
                    }

                    coordinatesToAdd = SymbolsNearCoordinate(possibleSteps.get(i), 3);
                    for (int c : coordinatesToAdd) {
                        if (!possibleSteps.contains(c)) {
                            possibleSteps.add(c);
                            added++;
                        }
                    }
                }
            }


            if (added == 1) {
                added = 0;
            } else {
                added = 1;
            }
        }
        cleanCoordinates(possibleSteps, 1);
        laps++;
        return possibleSteps;
    }

    public ArrayList<Integer> mpcPossibleSteps() {
        ArrayList<Integer> possibleSteps = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            if (coordinateNearSymbol(i, 2)) {
                possibleSteps.add(i);
            }
        }

        int added = 1;
        ArrayList<Integer> coordinatesToAdd;

        while (added != 0) {
            for (int i = 0; i < possibleSteps.size(); i++) {
                if (coordinateSymbol(possibleSteps.get(i)) == 4) {
                    coordinatesToAdd = SymbolsNearCoordinate(possibleSteps.get(i), 0);
                    for (int c : coordinatesToAdd) {
                        if (!possibleSteps.contains(c)) {
                            possibleSteps.add(c);
                            added++;
                        }
                    }

                    coordinatesToAdd = SymbolsNearCoordinate(possibleSteps.get(i), 1);
                    for (int c : coordinatesToAdd) {
                        if (!possibleSteps.contains(c)) {
                            possibleSteps.add(c);
                            added++;
                        }
                    }

                    coordinatesToAdd = SymbolsNearCoordinate(possibleSteps.get(i), 4);
                    for (int c : coordinatesToAdd) {
                        if (!possibleSteps.contains(c)) {
                            possibleSteps.add(c);
                            added++;
                        }
                    }
                }
            }

            if (added == 1) {
                added = 0;
            } else {
                added = 1;
            }
        }
        possibleSteps = cleanCoordinates(possibleSteps, 2);
        laps++;
        return possibleSteps;
    }

    public ArrayList<Integer> cleanCoordinates(ArrayList<Integer> coordinates, int symbol) {
        if (symbol == 1) {
            for (int i = 0; i < coordinates.size(); i++) {
                if (coordinateSymbol(coordinates.get(i)) == 1 || coordinateSymbol(coordinates.get(i)) == 3 || coordinateSymbol(coordinates.get(i)) == 4) {
                    coordinates.remove(coordinates.get(i));
                }
            }
        }
        if (symbol == 2) {
            for (int i = 0; i < coordinates.size(); i++) {
                if (coordinateSymbol(coordinates.get(i)) == 2 || coordinateSymbol(coordinates.get(i)) == 4 || coordinateSymbol(coordinates.get(i)) == 3) {
                    coordinates.remove(coordinates.get(i));
                }
            }
        }
        return coordinates;
    }


    public boolean coordinateNearSymbol(int coordinate, int symbol) {
        int l = coordinate / 10;
        int w = coordinate % 10;

        //check coordinates not on boards
        if (coordinate < 100 && coordinate >= 0 && map[l][w] != symbol) {

            if (l > 0 && l < 9 && w > 0 && w < 9) {
                if (map[l - 1][w - 1] == symbol) {
                    return true;
                }
                if (map[l - 1][w] == symbol) {
                    return true;
                }
                if (map[l - 1][w + 1] == symbol) {
                    return true;
                }
                if (map[l][w + 1] == symbol) {
                    return true;
                }
                if (map[l + 1][w + 1] == symbol) {
                    return true;
                }
                if (map[l + 1][w] == symbol) {
                    return true;
                }
                if (map[l + 1][w - 1] == symbol) {
                    return true;
                }
                return map[l][w - 1] == symbol;
            }

            //check coordinates on top board
            if (l == 0 && w > 0 && w < 9) {
                if (map[l + 1][w - 1] == symbol) {
                    return true;
                }
                if (map[l + 1][w] == symbol) {
                    return true;
                }
                if (map[l + 1][w + 1] == symbol) {
                    return true;
                }
                if (map[l][w + 1] == symbol) {
                    return true;
                }
                if (map[l][w - 1] == symbol) {
                    return true;
                }
            }

            //check coordinates on bottom board
            if (l == 9 && w > 0 && w < 9) {
                if (map[l - 1][w] == symbol) {
                    return true;
                }
                if (map[l - 1][w - 1] == symbol) {
                    return true;
                }
                if (map[l - 1][w + 1] == symbol) {
                    return true;
                }
                if (map[l][w + 1] == symbol) {
                    return true;
                }
                if (map[l][w - 1] == symbol) {
                    return true;
                }
            }

            //check coordinates on left board
            if (l > 0 && l < 9 && w == 0) {
                if (map[l - 1][w + 1] == symbol) {
                    return true;
                }
                if (map[l][w + 1] == symbol) {
                    return true;
                }
                if (map[l + 1][w + 1] == symbol) {
                    return true;
                }
                if (map[l - 1][w] == symbol) {
                    return true;
                }
                if (map[l + 1][w] == symbol) {
                    return true;
                }
            }

            //check coordinates on right board
            if (l > 0 && l < 9 && w == 9) {
                if (map[l - 1][w - 1] == symbol) {
                    return true;
                }
                if (map[l][w - 1] == symbol) {
                    return true;
                }
                if (map[l + 1][w - 1] == symbol) {
                    return true;
                }
                if (map[l - 1][w] == symbol) {
                    return true;
                }
                if (map[l + 1][w] == symbol) {
                    return true;
                }
            }

            //check coordinates on top left corner
            if (l == 0 && w == 0) {
                if (map[l][w + 1] == symbol) {
                    return true;
                }
                if (map[l + 1][w + 1] == symbol) {
                    return true;
                }
                if (map[l + 1][w] == symbol) {
                    return true;
                }
            }

            //check coordinates on top right corner
            if (l == 0 && w == 9) {
                if (map[l][w - 1] == symbol) {
                    return true;
                }
                if (map[l + 1][w - 1] == symbol) {
                    return true;
                }
                if (map[l + 1][w] == symbol) {
                    return true;
                }
            }

            //check coordinates on bottom lef corner
            if (l == 9 && w == 0) {
                if (map[l - 1][w] == symbol) {
                    return true;
                }
                if (map[l][w + 1] == symbol) {
                    return true;
                }
                if (map[l - 1][w + 1] == symbol) {
                    return true;
                }
            }

            //check coordinates on bottom right corner
            if (l == 9 && w == 9) {
                if (map[l - 1][w] == symbol) {
                    return true;
                }
                if (map[l - 1][w - 1] == symbol) {
                    return true;
                }
                if (map[l][w - 1] == symbol) {
                    return true;
                }
            }
        }
        return false;
    }


    public ArrayList<Integer> SymbolsNearCoordinate(int coordinate, int symbol) {
        ArrayList<Integer> coordinates = new ArrayList<>();

        int l = coordinate / 10;
        int w = coordinate % 10;

        //check coordinates not on boards
        if (coordinate < 100 && coordinate >= 0) {

            if (l > 0 && l < 9 && w > 0 && w < 9) {
                if (map[l - 1][w - 1] == symbol) {
                    coordinates.add((l - 1) * 10 + w - 1);
                }
                if (map[l - 1][w] == symbol) {
                    coordinates.add((l - 1) * 10 + w);
                }
                if (map[l - 1][w + 1] == symbol) {
                    coordinates.add((l - 1) * 10 + w + 1);
                }
                if (map[l][w + 1] == symbol) {
                    coordinates.add(l * 10 + w + 1);
                }
                if (map[l + 1][w + 1] == symbol) {
                    coordinates.add((l + 1) * 10 + w + 1);
                }
                if (map[l + 1][w] == symbol) {
                    coordinates.add((l + 1) * 10 + w);
                }
                if (map[l + 1][w - 1] == symbol) {
                    coordinates.add((l + 1) * 10 + w - 1);
                }
                if (map[l][w - 1] == symbol) {
                    coordinates.add(l * 10 + w - 1);
                }
            }

            //check coordinates on top board
            else if (l == 0 && w > 0 && w < 9) {
                if (map[l + 1][w - 1] == symbol) {
                    coordinates.add((l + 1) * 10 + w - 1);
                }
                if (map[l + 1][w] == symbol) {
                    coordinates.add((l + 1) * 10 + w);
                }
                if (map[l + 1][w + 1] == symbol) {
                    coordinates.add((l + 1) * 10 + w + 1);
                }
                if (map[l][w + 1] == symbol) {
                    coordinates.add(l * 10 + w + 1);
                }
                if (map[l][w - 1] == symbol) {
                    coordinates.add(l * 10 + w - 1);
                }
            }

            //check coordinates on bottom board
            else if (l == 9 && w > 0 && w < 9) {
                if (map[l - 1][w] == symbol) {
                    coordinates.add((l - 1) * 10 + w);
                }
                if (map[l - 1][w - 1] == symbol) {
                    coordinates.add((l - 1) * 10 + w - 1);
                }
                if (map[l - 1][w + 1] == symbol) {
                    coordinates.add((l - 1) * 10 + w + 1);
                }
                if (map[l][w + 1] == symbol) {
                    coordinates.add(l * 10 + w + 1);
                }
                if (map[l][w - 1] == symbol) {
                    coordinates.add(l * 10 + w - 1);
                }
            }

            //check coordinates on left board
            else if (l > 0 && l < 9 && w == 0) {
                if (map[l - 1][w + 1] == symbol) {
                    coordinates.add((l - 1) * 10 + w + 1);
                }
                if (map[l][w + 1] == symbol) {
                    coordinates.add(l * 10 + w + 1);
                }
                if (map[l + 1][w + 1] == symbol) {
                    coordinates.add((l + 1) * 10 + w + 1);
                }
                if (map[l - 1][w] == symbol) {
                    coordinates.add((l - 1) * 10 + w);
                }
                if (map[l + 1][w] == symbol) {
                    coordinates.add((l + 1) * 10 + w);
                }
            }

            //check coordinates on right board
            else if (l > 0 && l < 9 && w == 9) {
                if (map[l - 1][w - 1] == symbol) {
                    coordinates.add((l - 1) * 10 + w - 1);
                }
                if (map[l][w - 1] == symbol) {
                    coordinates.add(l * 10 + w - 1);
                }
                if (map[l + 1][w - 1] == symbol) {
                    coordinates.add((l + 1) * 10 + w - 1);
                }
                if (map[l - 1][w] == symbol) {
                    coordinates.add((l - 1) * 10 + w);
                }
                if (map[l + 1][w] == symbol) {
                    coordinates.add((l + 1) * 10 + w);
                }
            }

            //check coordinates on top left corner
            else if (l == 0 && w == 0) {
                if (map[l][w + 1] == symbol) {
                    coordinates.add(w + 1);
                }
                if (map[l + 1][w + 1] == symbol) {
                    coordinates.add((l + 1) * 10 + w + 1);
                }
                if (map[l + 1][w] == symbol) {
                    coordinates.add((l + 1) * 10 + w);
                }
            }

            //check coordinates on top right corner
            else if (l == 0 && w == 9) {
                if (map[l][w - 1] == symbol) {
                    coordinates.add(l * 10 + w - 1);
                }
                if (map[l + 1][w - 1] == symbol) {
                    coordinates.add((l + 1) * 10 + w - 1);
                }
                if (map[l + 1][w] == symbol) {
                    coordinates.add((l + 1) * 10 + w);
                }
            }

            //check coordinates on bottom lef corner
            else if (l == 9 && w == 0) {
                if (map[l - 1][w] == symbol) {
                    coordinates.add((l - 1) * 10 + w);
                }
                if (map[l][w + 1] == symbol) {
                    coordinates.add(l * 10 + w + 1);
                }
                if (map[l - 1][w + 1] == symbol) {
                    coordinates.add((l - 1) * 10 + w + 1);
                }
            }

            //check coordinates on bottom right corner
            else if (l == 9 && w == 9) {
                if (map[l - 1][w] == symbol) {
                    coordinates.add((l - 1) * 10 + w);
                }
                if (map[l - 1][w - 1] == symbol) {
                    coordinates.add((l - 1) * 10 + w - 1);
                }
                if (map[l][w - 1] == symbol) {
                    coordinates.add(l * 10 + w - 1);
                }
            }
        }
        return coordinates;
    }
}
