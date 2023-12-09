package Day3;

import util.Util;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) {
        ArrayList<String> input = Util.getPuzzleInput("./Day3/input.txt");

        System.out.println(task1(input));
    }

    // test should be 4361, everything but 114 and 58
    public static int task1(ArrayList<String> input) {
        AtomicInteger sum = new AtomicInteger();
        Pattern pattern = Pattern.compile("\\d+");

        AtomicInteger index = new AtomicInteger();
        input.forEach((line) -> {
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                boolean matched = false;
                int len = matcher.end() - matcher.start();

                for (int y = -1; y < 2; y++) {
                    for (int x = -1; x < len + 1; x++) {
                        char c = input.get(Math.max(Math.min(index.get() + y, input.size() - 1), 0)).charAt(Math.min(Math.max(matcher.start() + x, 0), input.get(Math.max(Math.min(index.get() + y, input.size() - 1), 0)).length() - 1));

                        if (!Character.isDigit(c) && c != '.') {
                            sum.addAndGet(Integer.parseInt(line.substring(matcher.start(), matcher.end())));
//                            System.out.println(line.substring(matcher.start(), matcher.end()));
                            matched = true;
                            break;
                        }
                    }
                    if (matched) break;
                }
            }
            index.getAndIncrement();
        });

        return sum.get();
    }

    // test should be 467835
    public static int task2(ArrayList<String> input) {
        return 0;
    }
}