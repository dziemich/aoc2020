package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.List;

public class Day03 {

  // PART 1
  // TIME: O(n)
  // SPACE: O(1)

  public static long solve1(List<String> input, int stepRight, int stepDown) {
    long counter = 0;
    int index = 0;
    for (int i = stepDown; i < input.size(); i += stepDown) {
      String line = input.get(i);
      index = (index + stepRight) % line.length();
      if (line.charAt(index) == '#') counter++;
    }
    return counter;
  }

  // PART 1
  // TIME: O(n)
  // SPACE: O(1)

  public static long solve2(List<String> input) {
    return solve1(input, 1, 1)
        * solve1(input, 3, 1)
        * solve1(input, 5, 1)
        * solve1(input, 7, 1)
        * solve1(input, 1, 2);
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day03.txt");
    long result1 = Day03.solve1(input, 3, 1);
    long result2 = Day03.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
