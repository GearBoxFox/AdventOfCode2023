package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Day2 {

    public static void main(String[] args) {
        ArrayList<String> inputList = new ArrayList<>();

        try {
            File input = new File("./Day2/input.txt");
            Scanner scanner = new Scanner(input);
            while (scanner.hasNext()) {
                inputList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        } // end try

        System.out.println(task2(inputList));

    }

    public static int task1(ArrayList<String> m_input) {
        HashMap<String, Integer> count = new HashMap<>();
        count.put("green", 13);
        count.put("blue", 14);
        count.put("red", 12);

        return m_input.stream().filter(
                (line) -> {
                    AtomicBoolean works = new AtomicBoolean(true);
                    String[] start = line.split(":");
                    String[] games = start[1].split(";");

                    Arrays.stream(games).forEach( (game) -> {
                        Arrays.stream(game.split(",")).forEach( (cube) -> {
                            String[] draw = cube.trim().split(" ");
                            int x = count.get(draw[1]);
                            if (x < Integer.parseInt(draw[0])) {
                                works.set(false);}
;                        });
                            }
                    );
                return works.get();
                }
        ).flatMapToInt((line) -> IntStream.of(
                Integer.parseInt(line.split(":")[0].replaceAll("\\D", ""))))
                .sum();
    }

    public static int task2(ArrayList<String> input) {
        AtomicInteger sum = new AtomicInteger();

        input.forEach(
                        (line) -> {
                            AtomicInteger r = new AtomicInteger();
                            AtomicInteger g = new AtomicInteger();
                            AtomicInteger b = new AtomicInteger();
                            String[] start = line.split(":");
                            String[] games = start[1].split(";");

                            Arrays.stream(games).forEach( (game) -> {
                                        Arrays.stream(game.split(",")).forEach( (cube) -> {
                                            String[] draw = cube.trim().split(" ");

                                            switch(draw[1]) {
                                                case "green":
                                                    g.set(Math.max(Integer.parseInt(draw[0]), g.get()));
                                                    break;
                                                case "blue":
                                                    b.set(Math.max(Integer.parseInt(draw[0]), b.get()));
                                                    break;
                                                case "red":
                                                    r.set(Math.max(Integer.parseInt(draw[0]), r.get()));
                                                    break;
                                                default:
                                                    break;
                                            }

                                        });
                                    }
                            );

//                        System.out.println(r.get() * g.get() * b.get());
                        sum.addAndGet(r.get() * b.get() * g.get());
                        }
                );

        return sum.get();
    }
}
