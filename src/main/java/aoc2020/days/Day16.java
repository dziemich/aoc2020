package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 {

  static class Range {
    int lower;
    int upper;

    public Range() {}

    public Range(int lower, int upper) {
      this.lower = lower;
      this.upper = upper;
    }

    public boolean belongsIn(int candidate) {
      return candidate >= lower && candidate <= upper;
    }
  }

  public static int solve1(List<String> input, int ruleLen, int ticketStart) {
    Pattern pattern = Pattern.compile("(\\d+)-(\\d+) or (\\d+)-(\\d+)");
    List<Range> ranges = new ArrayList<>();

    for (int i = 0; i < ruleLen; i++) {
      String line = input.get(i);
      Matcher matcher = pattern.matcher(line);
      if (!matcher.find()) continue;

      Range r1 = new Range(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
      Range r2 = new Range(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));

      ranges = addRange(ranges, r1);
      ranges = addRange(ranges, r2);
    }

    ranges = relaxRanges(ranges);

    // 1-3 5-11 13-44 45-50
    int result = 0;

    for (int i = ticketStart; i < input.size(); i++) {

      for (String s : input.get(i).split(",")) {
        int num = Integer.parseInt(s);
        if (ranges.stream().noneMatch(r -> r.belongsIn(num))) {
          result += num;
        }
      }
    }
    return result;
  }

  static List<Range> addRange(List<Range> ranges, Range r) {
    for (int j = 0; j < ranges.size(); j++) {
      Range cr = ranges.get(j);
      // crl rl ru cru
      if (cr.lower <= r.lower && cr.upper >= r.upper) return ranges;
      // rl crl cru ru
      else if (cr.lower >= r.lower && cr.upper <= r.upper) {
        cr.upper = r.upper;
        cr.lower = r.lower;
        return ranges;
        // crl rl cru ru
      } else if (cr.lower <= r.lower && cr.upper >= r.lower) {
        cr.upper = r.upper;
        return ranges;
      }
      // rl crl ru cru
      else if (cr.lower >= r.lower && cr.lower <= r.upper) {
        cr.lower = r.lower;
        return ranges;
      }
    }
    ranges.add(new Range(r.lower, r.upper));
    return ranges;
  }

  public static List<Range> relaxRanges(List<Range> ranges) {

    List<Range> copy = new ArrayList<>(ranges);

    for (int i = 0; i < ranges.size(); i++) {
      Range r1 = ranges.get(i);
      for (int j = i + 1; j < ranges.size(); j++) {
        Range r2 = ranges.get(j);
        if (r1.lower <= r2.lower && r1.upper >= r2.upper) {
          copy.remove(j);
        }
      }
    }
    return copy;
  }

  public static long solve2(List<String> input, int ruleLen, int ticketStart, int[] ticket) {
    List<String> validTickets = getValidTickets(input, ruleLen, ticketStart);
    Map<Integer, List<Range>> rules = new HashMap<>();

    Pattern pattern = Pattern.compile("(\\d+)-(\\d+) or (\\d+)-(\\d+)");
    List<Range> ranges = new ArrayList<>();

    for (int i = 0; i < ruleLen; i++) {
      String line = input.get(i);
      Matcher matcher = pattern.matcher(line);
      if (!matcher.find()) continue;

      Range r1 = new Range(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
      Range r2 = new Range(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
      rules.put(i, List.of(r1, r2));
    }

    // column to rule index
    HashMap<Integer, Integer> mappings = new HashMap<>();

    List<List<Integer>> parsedInput =
        validTickets.stream()
            .map(
                s ->
                    Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList()))
            .collect(Collectors.toList());

    while (rules.size() > 0) {
      System.out.println("rs: " + rules.size());
      for (int i = 0; i < ruleLen; i++) {
        if (mappings.containsKey(i)) continue;
        Set<Integer> candidates = new HashSet<>(rules.keySet());
        Set<Map.Entry<Integer, List<Range>>> ruleRanges = rules.entrySet();

        for (int j = 0; j < validTickets.size(); j++) {
          if (candidates.size() == 1) {
            break;
          }
          int num = parsedInput.get(j).get(i);
          for (var entry : new HashSet<>(ruleRanges)) {
            if (entry.getValue().stream().noneMatch(r -> r.belongsIn(num))) {
              candidates.remove(entry.getKey());
            }
          }
        }
        if (candidates.size() == 1) {
          Integer ruleIndex = candidates.iterator().next();
          mappings.put(i, ruleIndex);
          rules.remove(ruleIndex);
        }
      }
    }

    return mappings.entrySet().stream()
        .filter(e -> e.getValue() <= 5)
        .mapToLong(e -> ticket[e.getKey()])
        .reduce((i1, i2) -> i1 * i2)
        .getAsLong();
  }

  public static List<String> getValidTickets(List<String> input, int ruleLen, int ticketStart) {
    Pattern pattern = Pattern.compile("(\\d+)-(\\d+) or (\\d+)-(\\d+)");
    List<Range> ranges = new ArrayList<>();

    for (int i = 0; i < ruleLen; i++) {
      String line = input.get(i);
      Matcher matcher = pattern.matcher(line);
      if (!matcher.find()) continue;

      Range r1 = new Range(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
      Range r2 = new Range(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));

      ranges = addRange(ranges, r1);
      ranges = addRange(ranges, r2);
    }

    ranges = relaxRanges(ranges);

    List<String> validTickets = new ArrayList<>();

    for (int i = ticketStart; i < input.size(); i++) {
      boolean valid = true;
      String ticket = input.get(i);
      for (String s : ticket.split(",")) {
        int num = Integer.parseInt(s);
        if (ranges.stream().noneMatch(r -> r.belongsIn(num))) {
          valid = false;
        }
      }
      if (valid) {
        validTickets.add(ticket);
      }
    }
    return validTickets;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day16.txt");
    int result1 = Day16.solve1(input, 20, 25);
    long result2 =
        Day16.solve2(
            input,
            20,
            25,
            new int[] {
              163, 73, 67, 113, 79, 101, 109, 149, 53, 61, 97, 89, 103, 59, 71, 83, 151, 127, 157,
              107
            });
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
