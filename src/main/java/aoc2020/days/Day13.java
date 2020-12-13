package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.List;

public class Day13 {

  // PART 1
  // TIME: O(n)
  // SPACE: O(1)

  public static long solve1(List<String> input) {

    long start = Long.parseLong(input.get(0));

    long shortest = Integer.MAX_VALUE;
    long busId = -1;

    String[] entries = input.get(1).split(",");

    for (String entry : entries) {
      if (entry.equals("x")) continue;

      long id = Long.parseLong(entry);
      long time = id - (start % id);
      if (time <= shortest) {
        busId = id;
        shortest = time;
      }
    }
    return busId * shortest;
  }

  // PART 2
  // TIME: O(n)
  // SPACE: O(1)

  public static long solve2(List<String> input) {
    String[] entries = input.get(1).split(",");
    long accumulator = Long.parseLong(entries[0]);
    long addition = accumulator;

    for (int i = 1; i < entries.length; i++) {
      if (entries[i].equals("x")) continue;
      long busTime = Long.parseLong(entries[i]);
      while ((accumulator + i) % busTime != 0) accumulator += addition;
      addition *= busTime;
    }
    return accumulator;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day13aux.txt");
    long result1 = Day13.solve1(input);
    long result2 = Day13.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
