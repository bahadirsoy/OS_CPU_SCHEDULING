//Author: Bahadır Ustabaşı 20180808059

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class BAHADIR_USTABASI_20180808059 {
    public static void main(String[] args) {

        //Variables
        ArrayList<Tuple> tuples = new ArrayList<Tuple>();
        int rowCounter = 0;
        int[] returns;
        int[] returnsDone;
        int time = 0;
        int lineCounter = 0;

        //Reading and parsing txt file
        try {
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String[] arr = myReader.nextLine().replace("(", "").replace(")", "").split(":|;|,");
                //printArr(arr);


                for (int i = 1; i < arr.length; i += 2) {
                    tuples.add(new Tuple(Integer.parseInt(arr[i]), Integer.parseInt(arr[i + 1]), rowCounter));
                }

                rowCounter++;
            }

            myReader.close();

            //init returns array
            returns = new int[rowCounter];
            returnsDone = new int[rowCounter];


            while (hasNotDoneTuple(tuples)) {
                if (!hasPossibleTuple(returns, returnsDone, time)) {
                    time = returns[lineCounter];
                }

                int index = findFirstNotDoneTuple(tuples, lineCounter);

                if (index == -1) {

                    lineCounter = increaseVariable(lineCounter, rowCounter);

                } else {
                    if (returns[lineCounter] > time) {

                        lineCounter = increaseVariable(lineCounter, rowCounter);

                    } else {
                        Tuple t = tuples.get(index);
                        returns[lineCounter] = t.first + t.second + time;
                        time += t.first;

                        if (t.second == -1) {
                            returns[lineCounter]++;
                            returnsDone[lineCounter] = 1;
                        }

                        t.isDone = true;
                        //System.out.println(time - t.first + " " + t.line + " " + t.first + "," + t.second + " " + returns[lineCounter]);
                        lineCounter = increaseVariable(lineCounter, rowCounter);

                    }
                }

            }

            float averageATT = calculateAverageATT(returns);
            System.out.println("ATT: " + averageATT);

            System.out.println("AWT: " + calculateAverageAWT(tuples, rowCounter, averageATT));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static float calculateAverageAWT(ArrayList<Tuple> arrList, int lineCount, float averageATT) {
        float result = 0;

        for (int i = 0; i < lineCount; i++) {
            result += calculateBT(arrList, i);
        }

        return averageATT - (result / 3);
    }

    public static float calculateAverageATT(int[] returns) {
        float result = 0;

        for (int i : returns) {
            result += i;
        }

        return result / returns.length;
    }

    public static int calculateBT(ArrayList<Tuple> arrList, int line) {
        int result = 0;

        for (Tuple t : arrList) {
            if (t.line == line) {

                result += t.first;
                if (t.second != -1) {
                    result += t.second;
                }

            }
        }

        return result;
    }

    public static boolean hasPossibleTuple(int[] returns, int[] returnsDone, int time) {
        for (int i = 0; i < returns.length; i++) {
            if (returns[i] <= time && returnsDone[i] == 0) {
                return true;
            }
        }
        return false;
    }

    public static int increaseVariable(int value, int limit) {
        value++;

        if (value >= limit) {
            return 0;
        } else {
            return value;
        }
    }

    public static boolean hasNotDoneTuple(ArrayList<Tuple> arrList) {
        for (Tuple t : arrList) {
            if (!t.isDone) {
                return true;
            }
        }
        return false;
    }

    public static int findFirstNotDoneTuple(ArrayList<Tuple> arrList, int line) {
        for (Tuple t : arrList) {
            if (!t.isDone && t.line == line) {
                return arrList.indexOf(t);
            }
        }
        return -1;
    }

}

class Tuple {

    public int first;
    public int second;
    public int line;
    public boolean isDone;

    public Tuple(int first, int second, int line) {
        this.first = first;
        this.second = second;
        this.line = line;
        this.isDone = false;
    }

}
