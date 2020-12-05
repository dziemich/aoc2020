package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.ArrayList;
import java.util.List;

public class Day05 {

  // PART 1
  // TIME O(n)
  // SPACE O(1)

  public static int solve1(List<String> input) {
    int maxId = -1;

    for (String line : input) {
      int rowLeft = 0;
      int rowRight = 127;

      for (int i = 0; i < 7; i++) {
        int mid = (rowLeft + rowRight) / 2;
        if (line.charAt(i) == 'B') {
          rowLeft = mid + 1;
        } else {
          rowRight = mid;
        }
      }

      int colLeft = 0;
      int colRight = 7;

      for (int i = 7; i < 10; i++) {
        int mid = (colLeft + colRight) / 2;
        if (line.charAt(i) == 'R') {
          colLeft = mid + 1;
        } else {
          colRight = mid;
        }
      }
      maxId = Math.max(maxId, rowLeft * 8 + colLeft);
    }
    return maxId;
  }

  // PART 2
  // TIME O(n * log n)
  // SPACE O(n)

  public static int solve2(List<String> input) {
    List<Integer> seatIds = new ArrayList<>();

    for (String line : input) {
      int rowLeft = 0;
      int rowRight = 127;

      for (int i = 0; i < 7; i++) {
        int mid = (rowLeft + rowRight) / 2;
        if (line.charAt(i) == 'B') {
          rowLeft = mid + 1;
        } else {
          rowRight = mid;
        }
      }

      int colLeft = 0;
      int colRight = 7;

      for (int i = 7; i < 10; i++) {
        int mid = (colLeft + colRight) / 2;
        if (line.charAt(i) == 'R') {
          colLeft = mid + 1;
        } else {
          colRight = mid;
        }
      }
      seatIds.add(rowLeft * 8 + colLeft);
    }
    seatIds.sort(Integer::compareTo);

    for (int i = 1; i < seatIds.size() - 1; i++) {
      int current = seatIds.get(i);
      if (seatIds.get(i + 1) != current + 1) {
        return current + 1;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day05.txt");
    int result1 = Day05.solve1(input);
    int result2 = Day05.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
