package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Util {
    public static ArrayList<String> getPuzzleInput(String path) {
        ArrayList<String> inputList = new ArrayList<>();

        try {
            File input = new File(path);
            Scanner scanner = new Scanner(input);
            while (scanner.hasNext()) {
                inputList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        } // end try

        return inputList;
    }
}
