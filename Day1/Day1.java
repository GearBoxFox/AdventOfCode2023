package Day1;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Day1 {

    static ArrayList<String> dictionary = new ArrayList<>(
            Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));
    static String startChars =
            "otfsen";

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

        for(String line : input) {
            Matcher matcher = pattern.matcher(line);
            try {
                matcher.find();

                int firstDig = Integer.parseInt(matcher.group(1));
                int secondDig = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : firstDig;

                int finalVal = (firstDig * 10) + secondDig;

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
            String firstMatch = "";
            int firstIndex = Integer.MAX_VALUE;

            int index = 1;
            String output = line.toLowerCase(Locale.ROOT);

            // funky precheck for edge cases
            output = precheck(output);

            // replace text with ints
//            for (String key : dictionary) {
//                output.replaceAll(key, String.valueOf(index));
//                index++;
//            }
            // end text to int

//            System.out.println(output);

            // get first and last int
            Matcher matcher = pattern.matcher(output);
            try {
                matcher.find();

                int firstDig = Integer.parseInt(matcher.group(1));
                int secondDig = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : firstDig;

                int finalVal = (firstDig * 10) + secondDig;

                sum += finalVal;

            } catch (IllegalStateException ignored) {
            }
        }

        return sum;
    }

    static String precheck(String in) {
        String buff = "";
        for (char c : in.toCharArray()) {
            if (startChars.indexOf(c) != -1 || !buff.isEmpty()) {
                buff = buff.concat(String.valueOf(c));
            }

            if (dictionary.contains(buff)) {
                in = in.replaceAll(buff, String.valueOf(dictionary.indexOf(buff) + 1));
                buff = "";
                continue;
            }

            boolean hasWord = false;
            for (String word : dictionary) {
                if (word.contains(buff)) {hasWord = true; break;}
            }

            if (!hasWord) buff = String.valueOf(buff.charAt(buff.length() - 1));
        }
        return in;
    }
}
