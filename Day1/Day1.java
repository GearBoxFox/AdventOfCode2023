package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Day1 {

    static String[] dictionary =
            {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "zero"};

    public static void main(String[] args) {
        ArrayList<String> inputList = new ArrayList<>();

        try {
            File input = new File("./Day1/input.txt");
            Scanner scanner = new Scanner(input);
            while (scanner.hasNext()) {
                inputList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        } // end try

        System.out.println("Task 1: " + task1(inputList));
        System.out.println("Task 2: " + task2(inputList));
    }

    public static int task1(ArrayList<String> input) {
        Pattern pattern = Pattern.compile("^\\D*(\\d)(\\D*(\\d)\\D*)*"); // any non-digit character
        int solution = 0;
        int index = 0;

        for(String line : input) {
            Matcher matcher = pattern.matcher(line);
            try {
                matcher.find();

                int firstDig = Integer.parseInt(matcher.group(1));
                int secondDig = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : firstDig;

                int finalVal = (firstDig * 10) + secondDig;

                index++;

                solution += finalVal;

            } catch(IllegalStateException ignored) {
            }
        }
        return solution;
    }

    public static int task2(ArrayList<String> input) {
        int sum = 0;
        Pattern pattern = Pattern.compile("^\\D*(\\d)(\\D*(\\d)\\D*)*");

        for(String line : input) {
            int index = 1;
            String output = line;
            for (String key : dictionary) {
                output = output.replaceAll(key, String.valueOf(index));
                index++;


                Matcher matcher = pattern.matcher(line);
                try {
                    matcher.find();

                    int firstDig = Integer.parseInt(matcher.group(1));
                    int secondDig = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : firstDig;

                    int finalVal = (firstDig * 10) + secondDig;

                    index++;

                    sum += finalVal;

                } catch (IllegalStateException ignored) {
                }
            }
        }

        return sum;
    }
}
