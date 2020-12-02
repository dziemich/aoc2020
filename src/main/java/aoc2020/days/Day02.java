package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 {

  // PART 1
  // TIME: O(n*m) n - input count, m - line length
  // SPACE: O(1)

  public static int solve1(List<String> input) {

    String regexString = "(\\d+)-(\\d+) ([a-z]): ([a-z]+)";
    Pattern regex = Pattern.compile(regexString);

    int result = 0;

    for (String line : input) {

      Matcher matcher = regex.matcher(line);
      if (isValid1(matcher)) result++;
    }

    return result;
  }

  private static boolean isValid1(Matcher matcher) {

    if (!matcher.find()) return false;

    int lowerBound = Integer.parseInt(matcher.group(1));
    int upperBound = Integer.parseInt(matcher.group(2));

    char searchedFor = matcher.group(3).charAt(0);

    int occurenceCounter = 0;

    for (char c : matcher.group(4).toCharArray()) {
      if (c == searchedFor) occurenceCounter++;
    }

    return occurenceCounter >= lowerBound && occurenceCounter <= upperBound;
  }

  // PART 2
  // TIME: O(n)
  // SPACE: O(1)

  public static int solve2(List<String> input) {

    String regexString = "(\\d+)-(\\d+) ([a-z]): ([a-z]+)";
    Pattern regex = Pattern.compile(regexString);

    int result = 0;

    for (String line : input) {

      Matcher matcher = regex.matcher(line);
      if (isValid2(matcher)) result++;
    }

    return result;
  }

  private static boolean isValid2(Matcher matcher) {

    if (!matcher.find()) return false;

    String password = matcher.group(4);

    char leftChar = password.charAt(Integer.parseInt(matcher.group(1)) - 1);
    char rightChar = password.charAt(Integer.parseInt(matcher.group(2)) - 1);

    char searchedFor = matcher.group(3).charAt(0);

    return leftChar == searchedFor ^ rightChar == searchedFor;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day02.txt");
    System.out.println("PART 1: " +  Day02.solve1(input));
    System.out.println("PART 2: " +  Day02.solve2(input));
  }
}
