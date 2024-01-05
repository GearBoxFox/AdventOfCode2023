package Day5;

import util.Util;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) {
        ArrayList<String> input = Util.getPuzzleInput("./Day5/input.txt");

        // test should be 35
//        System.out.println(task1(input));
        System.out.println(task2(input));
    }

    public static int task1(ArrayList<String> input) {
        ArrayList<Long> seeds = Arrays.stream(input.get(0).replace("seeds:", "").trim().split(" ")).map(Long::parseLong).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Long> seedSwap = new ArrayList<>();

        input.forEach(line -> {
            // check for blank lines and for new map
            if (line.isBlank() || line.isEmpty() || line.startsWith("seeds:")) return;
            if (line.contains(":")) {
                seeds.addAll(seedSwap);
                seedSwap.clear();
                return;
            }

            // parse map rules for line
            Long[] mapVals = Arrays.stream(line.split(" ")).map(Long::parseLong).toArray(Long[]::new);

            // update seeds to new map rules using swap list
            ArrayList<Long> removeVals = new ArrayList<>();
            for (Long seed : seeds) {
                if (mapVals[1] < seed  && seed < (mapVals[1] + mapVals[2])) {
                    seedSwap.add(mapVals[0] + (seed - mapVals[1]));
                    removeVals.add(seed);
                }
            }

            // remove old values from seed list to not get double values
            for (Long val : removeVals) {
                seeds.remove(val);
            }
        });

        seeds.addAll(seedSwap);

        return Math.toIntExact(seeds.stream().sorted().toArray(Long[]::new)[0]);
    }

    public static int task2(ArrayList<String> input) {
        // to avoid maxing out memory, going backwards in the maps and check if it's a valid seed
        Long[] seedsRanges = Arrays.stream(input.get(0).replace("seeds:", "").trim().split(" ")).map(Long::parseLong).toArray(Long[]::new);
        ArrayList<ArrayList<String>> maps = new ArrayList<>(Collections.nCopies(1, new ArrayList<>()));

        int index = 0;
        for (int i = 2; i < input.size(); i++) {
            if (maps.size() - 1 < index) {
                maps.add(new ArrayList<String>());
            }

            if (input.get(i).isEmpty() || input.get(i).isBlank()) {
                index++;
                continue;
            }

            maps.get(index).add(input.get(i));
        }

        {
            ArrayList<ArrayList<String>> mapsRev = new ArrayList<>();
            for (int i = 0; i < maps.size(); i++) {
                mapsRev.add(maps.get(maps.size() - (i + 1)));
            }
            maps = mapsRev;
        }

        long i = 0;
        AtomicLong guess = new AtomicLong();
        boolean hasMatch = false;
        while(!hasMatch) {
            guess.set(i);
//            System.out.print("\n" + guess.get());

            maps.forEach(map -> {
                AtomicBoolean mapFound = new AtomicBoolean(false);
                map.forEach(line -> {
                    if (line.contains(":")) return;

                    // parse map rules for line
                    Long[] mapVals = Arrays.stream(line.split(" ")).map(Long::parseLong).toArray(Long[]::new);

                    // see if current seed guess has that value
                    if (mapVals[0] <= guess.get() && guess.get() <= (mapVals[0] + mapVals[2]) && !mapFound.get()) {
                        guess.set(mapVals[1] + (guess.get() - mapVals[0]));
                        mapFound.set(true);
                    }

                });
            });

            for (int y = 0; y < (seedsRanges.length / 2) + 1; y += 2) {
                if (guess.get() > seedsRanges[y] && guess.get() < seedsRanges[y] + seedsRanges[y + 1]) {
                    hasMatch = true;
                }
            }

            i++;
//            System.out.println("i: " + (i - 1));
        }

        return (int) i - 1;
    }
}
