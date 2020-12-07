package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day06 {

  // PART 1
  // TIME O(n)
  // SPACE O(1)

  public static int solve1(List<String> input) {
    HashSet<Character> set = new HashSet<>();
    int counter = 0;

    for (String line : input) {
      if (line.isEmpty()) {
        counter += set.size();
        set = new HashSet<>();
        continue;
      }
      for (char c : line.toCharArray()) {
        set.add(c);
      }
    }
    return counter + set.size();
  }

  public static int solve2(List<String> input) {
    HashMap<Character, Integer> answers = new HashMap<>();
    int groupEntries = 0;
    int counter = 0;

    for (String line : input) {
      if (line.isEmpty()) {
        final int entryCount = groupEntries;
        counter += answers.values().stream().filter(v -> v == entryCount).count();
        groupEntries = 0;
        answers = new HashMap<>();
        continue;
      }
      groupEntries++;
      for (char c : line.toCharArray()) {
        int occurrences = answers.getOrDefault(c, 0);
        answers.put(c, occurrences + 1);
      }
    }
    final int entryCount = groupEntries;
    counter += answers.values().stream().filter(v -> v == entryCount).count();
    return counter;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day06.txt");
    int result1 = Day06.solve1(input);
    int result2 = Day06.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
