package Day1;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class Day1 {

    static ArrayList<String> dictionary = new ArrayList<>(
            Arrays.asList("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
    static int[] map = {1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    static String startChars =
            "otfsen";

    public static void main(String[] args) {
        ArrayList<String> inputList = new ArrayList<>();

        try {
            File input = new File("./Day1/test.txt");
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

    public static int task2o(ArrayList<String> input) {
        int sum = 0;

        for(String line : input) {
            // funky precheck for edge cases
            line = line.toLowerCase();
            sum += precheck(line);
        }

        return sum;
    }

    static int precheck(String in) {
        String buff = "";
        int first = 0, last = 0;
        for (char c : in.toCharArray()) {
            if (Character.isDigit(c)) {
                if (first == 0) {first = Integer.parseInt(String.valueOf(c));} else {last = Integer.parseInt(String.valueOf(c));}
                continue;
            }

            if (startChars.indexOf(c) != -1 || !buff.isEmpty()) {
                buff = buff.concat(String.valueOf(c));
            }

            if (dictionary.contains(buff)) {
                if (first == 0) {first = dictionary.indexOf(buff) + 1;} else {last = dictionary.indexOf(buff) + 1;}
                buff = "";//String.valueOf(buff.charAt(buff.length() - 1));
                continue;
            }

            boolean hasWord = false;
            for (String word : dictionary) {
                if (word.startsWith(buff)) {hasWord = true; break;}
            }

            if (!hasWord) buff = String.valueOf(buff.charAt(buff.length() - 1));
        }

        if (last == 0) {last = first;}

        System.out.println(first*10 + ", " + last);

        return (first * 10) + last;
    }

    static int task2(ArrayList<String> lines) {
        int sum = 0;
        for (String line : lines) {
            ArrayList<Integer> indexes = new ArrayList<>();

            for (String word : dictionary) {
                int prevIndex = line.indexOf(word);
                while (prevIndex != -1) {
                    indexes.add(prevIndex);
                    prevIndex = line.indexOf(word, prevIndex + 1);
                }

            }

            List<Integer> indexArr = indexes.stream().sorted().collect(Collectors.toList());

//            System.out.println(indexArr.get(0) + " " + indexArr.get(indexArr.size() - 1));

            int first = 0, last = 0;

            if (Character.isDigit(line.charAt(indexArr.get(0)))) {
                first = Integer.parseInt(String.valueOf(line.charAt(indexArr.get(0)))); // goofy ah long line
            } else {
                for (int i = 0; i < dictionary.size(); i++) {
                    int x = line.indexOf(dictionary.get(i));

                    if (x == -1) continue;

                    if (x == indexArr.get(0)) {
                        first = map[dictionary.indexOf(dictionary.get(i))];
                        break;
                    }
                }
            }

            if (Character.isDigit(line.charAt(indexArr.get(indexArr.size() - 1)))) {
                last = Integer.parseInt(String.valueOf(line.charAt(indexArr.get(indexArr.size() - 1)))); // goofy ah long line v2 electric boogaloo
            }else {
                for (int i = 0; i < dictionary.size(); i++) {
                    int x = line.indexOf(dictionary.get(i));

                    if (x == -1) continue;

                    if (x == indexArr.get(indexArr.size() - 1)) {
                        last = map[dictionary.indexOf(dictionary.get(i))];
                        break;
                    }
                }
            }

            sum += (first * 10) + last;
            System.out.println(first + " " + last);
        }
        return sum;
    }

}
