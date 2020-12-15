package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.HashMap;
import java.util.List;

public class Day15 {

  public static int solve(List<String> input, long target) {

    int index = 0;
    HashMap<Integer, Integer> memory = new HashMap<>();

    for (String s : input.get(0).split(",")) {
      memory.put(Integer.parseInt(s), index++);
    }

    int next = 0;
    while (index < target - 1) {
      int tmp = next;
      if (memory.containsKey(next)) {
        next = index - memory.get(next);
      } else {
        next = 0;
      }
      memory.put(tmp, index++);
    }
    return next;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day15.txt");
    long result1 = Day15.solve(input, 2020);
    long result2 = Day15.solve(input, 30000000);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
