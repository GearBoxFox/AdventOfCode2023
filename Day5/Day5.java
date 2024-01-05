package Day5;

import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Day5 {

    public static void main(String[] args) {
        ArrayList<String> input = Util.getPuzzleInput("./Day5/input.txt");

        // test should be 35
        System.out.println(task1(input));
        System.out.println(task2(input));
    }

    public static int task1(ArrayList<String> input) {
        AtomicReference<List<Long>> seeds = new AtomicReference<>(Arrays.stream(input.get(0).replace("seeds:", "").trim().split(" ")).map(Long::parseLong).toList());
        long lowestLocation = Long.MAX_VALUE;
        HashMap<Long, Long> currentMap = new HashMap<>();

        input.forEach(line -> {
            if (line.isBlank() || line.isEmpty() || line.startsWith("seeds:")) return;
            if (line.contains(":")) {
                if (currentMap.isEmpty()) return;

                List<Long> newseeds = new ArrayList<>(List.of());

                for (Long seed : seeds.get()) {
                    newseeds.add(currentMap.getOrDefault(seed, seed));
                }

                seeds.set(newseeds);

                currentMap.clear();
                return;
            }

            String[] mapVals = line.trim().split(" ");

            for (int i = 0; i < Long.parseLong(mapVals[2]); i++) {
                currentMap.put(Long.parseLong(mapVals[1]) + i, Long.parseLong(mapVals[0]) + i);
            }
        });

        return Math.toIntExact(seeds.get().stream().sorted().toList().get(0));
    }

    public static int task2(ArrayList<String> input) {
        return 0;
    }
}
