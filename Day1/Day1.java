package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Day1 {
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

        System.out.println(day1(inputList));
    }

    public static int day1(ArrayList<String> input) {
        Pattern pattern = Pattern.compile("\\D"); // any non-digit character
        int solution = 0;
        int index = 0;

        for(String line : input) {
            Matcher matcher = pattern.matcher(line);
            String output = matcher.replaceAll(""); // strip non-digit characters

//            if (output.isEmpty()) {
//                continue;
//            }

            int firstDig = Character.getNumericValue(output.charAt(0));
            int secondDig = Character.getNumericValue(output.charAt(output.length() - 1));

            int finalVal = (firstDig * 10) + secondDig;

            index++;

            System.out.print(index + ":  ");
            System.out.println(finalVal);

            solution += finalVal;
        }
        return solution;
    }
}
