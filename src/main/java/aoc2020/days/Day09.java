package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.*;
import java.util.stream.Collectors;

public class Day09 {

  // PART 1
  // TIME O(n)
  // SPACE O(n)

  public static long solve1(List<String> input, int preambleLength) {
    List<Long> intInput = input.stream().map(Long::parseLong).collect(Collectors.toList());

    Queue<Set<Long>> combinations = createCombinations(0, preambleLength, intInput);

    for (int i = preambleLength; i < intInput.size(); i++) {
      Long num = intInput.get(i);
      boolean found = false;
      for (Set<Long> comb : combinations) {
        if (comb.contains(num)) {
          found = true;
          break;
        }
      }
      if (!found) return num;
      combinations = createCombinations(i - preambleLength + 1, preambleLength, intInput);
    }
    return -1;
  }

  private static Queue<Set<Long>> createCombinations(
      int startIndex, int preambleLength, List<Long> intInput) {
    Queue<Set<Long>> combinations = new LinkedList<>();

    for (int i = startIndex; i < startIndex + preambleLength; i++) {
      Set<Long> comb = new HashSet<>();
      for (int j = startIndex; j < startIndex + preambleLength; j++) {
        if (i == j) continue;
        comb.add(intInput.get(i) + intInput.get(j));
      }
      combinations.offer(comb);
    }
    return combinations;
  }

  // PART 2
  // TIME O(n)
  // SPACE O(n)

  public static Long solve2(List<String> input, Long target) {
    List<Long> intInput = input.stream().map(Long::parseLong).collect(Collectors.toList());
    int start = 0;
    int end = 0;

    Long sum = intInput.get(0);

    while (end < intInput.size()) {
      if (sum.equals(target)) {
        List<Long> window = intInput.subList(start, end + 1);
        return window.stream().max(Comparator.comparingLong(o -> o)).get()
            + window.stream().min(Comparator.comparingLong(o -> o)).get();
      } else if (sum > target) {
        sum -= intInput.get(start++);
      } else {
        sum += intInput.get(++end);
      }
    }
    return 0L;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day09.txt");
    int preamble = 25;
    long result1 = Day09.solve1(input, preamble);
    long result2 = Day09.solve2(input, result1);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
