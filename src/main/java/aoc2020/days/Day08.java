package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.HashSet;
import java.util.List;

public class Day08 {

  // PART 1
  // TIME O(n)
  // SPACE O(n)

  public static int solve1(List<String> input) {
    HashSet<Integer> visitedIndices = new HashSet<>();
    int acc = 0;
    for (int i = 0; i < input.size(); i++) {
      if (visitedIndices.contains(i)) {
        return acc;
      }
      visitedIndices.add(i);
      String line = input.get(i);
      String operation = line.substring(0, 3);

      if (operation.equals("nop")) continue;

      if (operation.equals("acc") || operation.equals("jmp")) {
        char op = line.charAt(4);
        int num = Integer.parseInt(line.substring(5));
        if (op == '+') {
          if (operation.equals("acc")) {
            acc += num;
          } else {
            i += num - 1;
          }
        } else {
          if (operation.equals("acc")) {
            acc -= num;
          } else {
            i = i - num - 1;
          }
        }
      }
    }
    return 0;
  }



  // PART 2
  // TIME O(n^2)
  // SPACE O(n)
  static int result = 0;

  public static int solve2(List<String> input) {

    for (int i = 0; i < input.size(); i++) {
      String line = input.get(i);
      String operation = line.substring(0, 3);
      if (operation.equals("acc")) continue;

      if (operation.equals("nop")) {
        input.set(i, "jmp" + line.substring(3));
      } else {
        input.set(i, "nop" + line.substring(3));
      }
      solve2Helper(input);
      input.set(i, line);
    }
    return result;
  }

  public static void solve2Helper(List<String> input) {
    HashSet<Integer> visitedIndices = new HashSet<>();
    int acc = 0;
    for (int i = 0; i < input.size(); i++) {
      if (visitedIndices.contains(i)) {
        return;
      }
      visitedIndices.add(i);
      String line = input.get(i);
      String operation = line.substring(0, 3);

      if (operation.equals("nop")) continue;

      if (operation.equals("acc") || operation.equals("jmp")) {
        char op = line.charAt(4);
        int num = Integer.parseInt(line.substring(5));
        if (op == '+') {
          if (operation.equals("acc")) {
            acc += num;
          } else {
            i += num - 1;
          }
        } else {
          if (operation.equals("acc")) {
            acc -= num;
          } else {
            i = i - num - 1;
          }
        }
      }
    }
    result = acc;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day08.txt");
    int result1 = Day08.solve1(input);
    int result2 = Day08.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
