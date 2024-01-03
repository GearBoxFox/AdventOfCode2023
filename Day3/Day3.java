package Day3;

import util.Util;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static void main(String[] args) {
        ArrayList<String> input = Util.getPuzzleInput("./Day3/input.txt");

//        System.out.println(task1(input));
        System.out.println(task2(input));
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
        AtomicInteger inputIndex = new AtomicInteger();
        AtomicInteger sum = new AtomicInteger();
        AtomicInteger lineIndex = new AtomicInteger();
        input.forEach((line) -> {
            lineIndex.incrementAndGet();
                int index = 0;

//                System.out.println("Current Line: " + lineIndex.get());

                while((index = line.indexOf('*', index)) != -1) { // find all '*' per line
                    int first = 0, second = 0;

//                    System.out.println("'*' found at index " + index);

                    for (int i = -1; i < 2; i++) {
                        // loop through 3x3 grid around each '*'
                        String curLine = input.get(Math.min(Math.max(inputIndex.get() + i, 0), input.size() - 1));
                        int min = Math.min(index + 2, curLine.length() - 1);
                        String sub = curLine.substring(Math.max(index - 1, 0), min);

                        int lastNum = 0;
                        for (int subIn = 0; subIn < 3; subIn++) {
                            if (Character.isDigit(sub.charAt(subIn))) {
//                            System.out.println("Found number");
                                int finalNum = 0;

                                //find full number

                                // find start
                                int cIndex = Math.max(index - 1, 0) + subIn;
                                boolean lastWasDig = false;
                                while (!lastWasDig && !Character.isDigit(curLine.charAt(cIndex)) || Character.isDigit(curLine.charAt(cIndex))) {
                                    if (cIndex == 0) break;
                                    lastWasDig = Character.isDigit(curLine.charAt(cIndex));
                                    cIndex--;
                                }

                                // redo some clamps on indexes
                                cIndex = cIndex == 0 ? cIndex : cIndex + 1;
                                if (cIndex == 0 && lastWasDig && !Character.isDigit(curLine.charAt(cIndex))) cIndex++;
                                cIndex = Math.min(cIndex, curLine.length() - 1);


                                while (Character.isDigit((curLine.charAt(cIndex)))) {
                                    char c = curLine.charAt(cIndex++);
                                    finalNum = (finalNum * 10) + Integer.parseInt(String.valueOf(c));
                                    if (cIndex == curLine.length()) break;
                                }

//                            System.out.println("Final found number is: " + finalNum);

                                if (lastNum == finalNum) {
                                    break;
                                } if (first == 0) {
                                    first = finalNum;
                                } else if (second == 0) {
                                    second = finalNum;
//                                System.out.println("Found pair " + first + ", " + second + " at line " + lineIndex.get() + " index " + index + " ratio is " + first * second);
                                } else if (finalNum != 0) {
                                    System.out.println("Found more than two at line " + lineIndex.get() + " index " + index);
                                }


                                lastNum = finalNum;
                            }
                        }
                    }

                    if (first == 0) {
                        System.out.println("Found '*' with no numbers at line " + lineIndex.get() + ", index " + index);
                    } else if (second == 0) {
                        System.out.println("Found '*' with only one value (" + first + ") at line " + lineIndex.get() + ", index " + index);
                    } else {
                        sum.addAndGet(first * second);
                    }

                    index++;
                }
                inputIndex.getAndIncrement();
        });

        return sum.get();
    }
}
