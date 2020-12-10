package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {

  public static int solve1(List<String> input) {

    List<Integer> intInput =
        input.stream()
            .map(Integer::parseInt)
            .sorted(Integer::compareTo)
            .collect(Collectors.toList());

    int diff1 = 0;
    int diff3 = 0;

    int current = 0;

    for (int joltage : intInput) {
      if (joltage - current == 1) diff1++;
      if (joltage - current == 3) diff3++;
      current = joltage;
    }
    return diff1 * (diff3 + 1);
  }

  public static long solve2(List<String> input) {
    List<Integer> intInput =
        input.stream()
            .map(Integer::parseInt)
            .sorted(Integer::compareTo)
            .collect(Collectors.toList());

    int target = intInput.get(intInput.size() - 1);
    HashSet<Integer> joltages = new HashSet<>(intInput);

    long[] dp = new long[target + 1];

    dp[target] = 1;

    for (int i = target - 1; i >= 0; i--) {
      for (int j = 1; j <= 3; j++) {
        if (i + j < dp.length && joltages.contains(i + j)) dp[i] += dp[i + j];
      }
    }
    return dp[0];
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day10.txt");
    int result1 = Day10.solve1(input);
    long result2 = Day10.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
