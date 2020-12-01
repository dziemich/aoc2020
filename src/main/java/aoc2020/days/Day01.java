package aoc2020.days;

import aoc2020.utils.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day01 {

  // PART 1
  // TIME: O(n)
  // SPACE: O(n)

  public static int solve1(List<String> input, int target) {

    Set<Integer> set = new HashSet<>();

    for (String n : input) {
      int num = Integer.parseInt(n);

      if (set.contains(target - num)) {
        return num * (target - num);
      }
      set.add(num);
    }
    return 0;
  }

  // PART 2
  // TIME: O(n^2)
  // SPACE: O(1)

  public static int solve2(List<String> input, int target) {

    List<Integer> nums =
        input.stream()
            .map(Integer::parseInt)
            .sorted(Integer::compareTo)
            .collect(Collectors.toList());

    for (int i = 0; i < nums.size(); i++) {
      int left = i + 1;
      int right = nums.size() - 1;

      while (left < right) {
        Integer numAtI = nums.get(i);
        Integer numAtLeft = nums.get(left);
        Integer numAtRight = nums.get(right);
        int sum = numAtI + numAtLeft + numAtRight;

        if (sum == target) return numAtI * numAtLeft * numAtRight;
        else if (sum < target) {
          left++;
        } else {
          right--;
        }
      }
    }
    return 0;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day01.txt");
    int result1 = Day01.solve1(input, 2020);
    System.out.println("PART 1: " + result1);

    int result2 = Day01.solve2(input, 2020);
    System.out.println("PART 2: " + result2);
  }
}
