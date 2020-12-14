package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {
  public static long solve1(List<String> input) {

    String mask = input.get(0).substring(7);
    HashMap<Integer, Long> memory = new HashMap<>();

    Pattern memPattern = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");

    for (int i = 1; i < input.size(); i++) {

      String line = input.get(i);
      if (line.startsWith("mask")) {
        mask = line.substring(7);
        continue;
      }

      Matcher matcher = memPattern.matcher(line);
      if (!matcher.find()) continue;
      int address = Integer.parseInt(matcher.group(1));
      int value = Integer.parseInt(matcher.group(2));

      String valueBinary = Integer.toBinaryString(value);
      StringBuilder maskedValue = new StringBuilder();

      for (int j = 0; j < Math.min(valueBinary.length(), mask.length()); j++) {
        char maskChar = mask.charAt(mask.length() - j - 1);
        char valueChar = valueBinary.charAt(valueBinary.length() - j - 1);
        if (maskChar == 'X') maskedValue.insert(0, valueChar);
        else maskedValue.insert(0, maskChar);
      }
      for (int j = 0; j < mask.length() - valueBinary.length(); j++) {
        maskedValue.insert(j, mask.charAt(j) == '1' ? '1' : '0');
      }
      long val = Long.parseLong(maskedValue.toString(), 2);
      memory.put(address, val);
    }
    return memory.values().stream().reduce(Long::sum).orElse(-1L);
  }

  private static class AdressGen {
    static Set<Long> addresses = new HashSet<>();

    public static List<Long> generate(String addressBinary, String mask) {
      addresses = new HashSet<>();
      helper(addressBinary, mask, "");
      return new ArrayList<>(addresses);
    }

    static void helper(String addressBinary, String mask, String current) {
      if (current.length() == mask.length()) {
        addresses.add(Long.parseLong(current, 2));
        return;
      }
      int currLen = current.length();
      int maskIndex = mask.length() - currLen - 1;
      int addressIndex = addressBinary.length() - currLen - 1;

      if (maskIndex >= 0 && addressIndex >= 0) {
        char ac = addressBinary.charAt(addressIndex);
        char mc = mask.charAt(maskIndex);

        if (mc == '0') {
          helper(addressBinary, mask, ac + current);
        } else if (mc == '1') {
          helper(addressBinary, mask, "1" + current);
        } else {
          helper(addressBinary, mask, "0" + current);
          helper(addressBinary, mask, "1" + current);
        }
        return;
      }

      char mc = mask.charAt(maskIndex);

      if (mc == 'X') {
        helper(addressBinary, mask, "0" + current);
        helper(addressBinary, mask, "1" + current);
      } else {
        helper(addressBinary, mask, mc + current);
      }
    }
  }

  public static long solve2(List<String> input) {

    String mask = input.get(0).substring(7);
    HashMap<Long, Long> memory = new HashMap<>();

    Pattern memPattern = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");

    for (int i = 1; i < input.size(); i++) {

      String line = input.get(i);
      if (line.startsWith("mask")) {
        mask = line.substring(7);
        continue;
      }

      Matcher matcher = memPattern.matcher(line);
      if (!matcher.find()) continue;
      int address = Integer.parseInt(matcher.group(1));
      long value = Integer.parseInt(matcher.group(2));

      String addressBinary = Integer.toBinaryString(address);

      List<Long> addresses = AdressGen.generate(addressBinary, mask);

      for (long computedAddress : addresses) {
        memory.put(computedAddress, value);
      }
    }
    return memory.values().stream().reduce(Long::sum).orElse(-1L);
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day14.txt");
    long result1 = Day14.solve1(input);
    long result2 = Day14.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
