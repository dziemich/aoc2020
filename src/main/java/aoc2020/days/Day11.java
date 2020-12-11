package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day11 {
  public static final char HASH = '#';
  public static final char DOT = '.';
  public static final char L = 'L';

  // PART 1
  // TIME O(m*n) - m -> line length, n -> line count
  // SPACE O(m*n) -> m -> line length, n -> line count

  public static int solve1(List<String> input) {
    List<String> copy = new ArrayList<>(input);
    boolean hasChanged = false;
    int hashCount = 0;

    do {
      hasChanged = false;
      ArrayList<String> iterator = new ArrayList<>(copy);
      for (int lineIndex = 0; lineIndex < iterator.size(); lineIndex++) {
        char[] line = iterator.get(lineIndex).toCharArray();

        for (int i = 0; i < line.length; i++) {
          char current = line[i];
          if (current == DOT) continue;
          HashMap<Character, Integer> counts = new HashMap<>();

          for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
              if (k == 0 && j == 0) continue;
              if (!(i + j < 0 || i + j >= line.length)
                  && !(lineIndex + k < 0 || lineIndex + k >= iterator.size())) {
                char c = iterator.get(lineIndex + k).charAt(i + j);
                int count = counts.getOrDefault(c, 0);
                counts.put(c, count + 1);
              }
            }
          }

          if (current == L && counts.getOrDefault(HASH, 0) == 0) {
            line[i] = HASH;
            hashCount++;
            hasChanged = true;
          } else if (current == HASH && counts.getOrDefault(HASH, 0) >= 4) {
            line[i] = L;
            hashCount--;
            hasChanged = true;
          }
        }
        copy.set(lineIndex, new String(line));
      }
    } while (hasChanged);

    return hashCount;
  }

  // PART 2
  // TIME O(m*n) - m -> line length, n -> line count
  // SPACE O(m*n) -> m -> line length, n -> line count
  public static int solve2(List<String> input) {
    List<String> copy = new ArrayList<>(input);
    boolean hasChanged = false;
    int hashCount = 0;

    do {
      hasChanged = false;
      ArrayList<String> iterator = new ArrayList<>(copy);
      for (int lineIndex = 0; lineIndex < iterator.size(); lineIndex++) {
        char[] line = iterator.get(lineIndex).toCharArray();

        for (int i = 0; i < line.length; i++) {
          char current = line[i];
          if (current == DOT) continue;
          HashMap<Character, Integer> counts = new HashMap<>();

          // diagonals
          int it = 1;
          while (i - it >= 0 && lineIndex - it >= 0) {
            char c = iterator.get(lineIndex - it).charAt(i - it);
            if (c != DOT) {
              int count = counts.getOrDefault(c, 0);
              counts.put(c, count + 1);
              break;
            }
            it++;
          }

          it = 1;
          while (i + it < line.length && lineIndex - it >= 0) {
            char c = iterator.get(lineIndex - it).charAt(i + it);
            if (c != DOT) {
              int count = counts.getOrDefault(c, 0);
              counts.put(c, count + 1);
              break;
            }
            it++;
          }

          it = 1;
          while (i - it >= 0 && lineIndex + it < iterator.size()) {
            char c = iterator.get(lineIndex + it).charAt(i - it);
            if (c != DOT) {
              int count = counts.getOrDefault(c, 0);
              counts.put(c, count + 1);
              break;
            }
            it++;
          }

          it = 1;
          while (i + it < line.length && lineIndex + it < iterator.size()) {
            char c = iterator.get(lineIndex + it).charAt(i + it);
            if (c != DOT) {
              int count = counts.getOrDefault(c, 0);
              counts.put(c, count + 1);
              break;
            }
            it++;
          }

          // straight
          it = 1;
          while (i + it < line.length) {
            char c = iterator.get(lineIndex).charAt(i + it);
            if (c != DOT) {
              int count = counts.getOrDefault(c, 0);
              counts.put(c, count + 1);
              break;
            }
            it++;
          }

          it = 1;
          while (i - it >= 0) {
            char c = iterator.get(lineIndex).charAt(i - it);
            if (c != DOT) {
              int count = counts.getOrDefault(c, 0);
              counts.put(c, count + 1);
              break;
            }
            it++;
          }

          it = 1;
          while (lineIndex + it < iterator.size()) {
            char c = iterator.get(lineIndex + it).charAt(i);
            if (c != DOT) {
              int count = counts.getOrDefault(c, 0);
              counts.put(c, count + 1);
              break;
            }
            it++;
          }

          it = 1;
          while (lineIndex - it >= 0) {
            char c = iterator.get(lineIndex - it).charAt(i);
            if (c != DOT) {
              int count = counts.getOrDefault(c, 0);
              counts.put(c, count + 1);
              break;
            }
            it++;
          }

          if (current == L && counts.getOrDefault(HASH, 0) == 0) {
            line[i] = HASH;
            hashCount++;
            hasChanged = true;
          } else if (current == HASH && counts.getOrDefault(HASH, 0) >= 5) {
            line[i] = L;
            hashCount--;
            hasChanged = true;
          }
        }
        copy.set(lineIndex, new String(line));
      }
    } while (hasChanged);

    return hashCount;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day11.txt");
    int result1 = Day11.solve1(input);
    int result2 = Day11.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
