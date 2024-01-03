package Day4;

import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 {
    public static void main(String[] args) {
        ArrayList<String> input = Util.getPuzzleInput("./Day4/input.txt");

        // test should be 13 points
//        System.out.println(task1(input));
        System.out.println(task2(input));
    }

    public static int task1(ArrayList<String> input) {
        AtomicInteger sum = new AtomicInteger();
        Pattern regex = Pattern.compile("^.*:(.*)[|](.*)");
        AtomicInteger game = new AtomicInteger(1);
        input.forEach((line) -> {
            Matcher matcher = regex.matcher(line);
            if (!matcher.find()) System.exit(-1);

            String[] numbers = matcher.group(2).trim().split(" ");
            List<String> winningNumbers = Arrays.stream(matcher.group(1).trim().split(" ")).filter((String s) -> !s.isEmpty()).toList();
            List<String> winners = Arrays.stream(numbers).filter(winningNumbers::contains).filter((String s) -> !s.isEmpty()).toList();

            int initial = 0;
            if (winners.size() != 0) {
                initial = 1;
                for (int i = 1; i < winners.size(); i++) {
                    initial *= 2;
                }
            }

//            System.out.println("Game " + game.get() + " has\n" + winners.size() + " winning numbers\n" + "(" + winners + ")\n" + "for a score of " + initial + "\n");

            game.getAndIncrement();
            sum.addAndGet(initial);
        });
        return sum.get();
    }

    public static int task2(ArrayList<String> input) {
        AtomicInteger sum = new AtomicInteger();
        AtomicInteger game = new AtomicInteger();
        Pattern regex = Pattern.compile("^.*:(.*)[|](.*)");

        List<Integer> instances = new ArrayList<>(Collections.nCopies(input.size(), 1));

        input.forEach((line) -> {
            Matcher matcher = regex.matcher(line);
            if (!matcher.find()) System.exit(-1);

            String[] numbers = matcher.group(2).trim().split(" ");
            List<String> winningNumbers = Arrays.stream(matcher.group(1).trim().split(" ")).filter((String s) -> !s.isEmpty()).toList();
            List<String> winners = Arrays.stream(numbers).filter(winningNumbers::contains).filter((String s) -> !s.isEmpty()).toList();

            if (winners.size() != 0) {
                for (int i = 0; i < instances.get(game.get()); i++) {
                    for (int y = Math.min(game.get() + 1, instances.size() - 1); y <= game.get() + winners.size(); y++) {
                        instances.set(Math.min(y, instances.size() - 1), instances.get(Math.min(y, instances.size() - 1)) + 1);
                    }
                }
            }

            game.incrementAndGet();
        });

        return instances.stream().mapToInt(Integer::intValue).sum();
    }
}
