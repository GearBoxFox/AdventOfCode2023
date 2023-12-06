package Day3;

import util.Util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Day3 {
    public static void main(String[] args) {
        ArrayList<String> input = Util.getPuzzleInput("./Day3/input.txt");

    }

    public static int task1(ArrayList<String> input) {
        int sum = 0;

        input.forEach((line) -> {
            int x = 0, y = 0;

            for(int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (!Character.isDigit(c) && c != '.') {
                    x = line.indexOf(c);
                    y = i;


                }
            }
        });

        return sum;
    }
}
