package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day04 {

  // PART 1
  // TIME O(n)
  // SPACE O(1)

  static List<String> required = List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

  public static int solve1(List<String> input) {
    int valid = 0;
    List<String> passport = new ArrayList<>();
    for (String line : input) {
      if (line.isEmpty()) {
        if (verify(passport)) valid++;
        passport = new ArrayList<>();
      } else {
        String[] entries = line.split(" ");
        for (String entry : entries) {
          passport.add(entry.split(":")[0]);
        }
      }
    }
    if (verify(passport)) valid++;
    return valid;
  }

  static boolean verify(List<String> passport) {
    for (String req : required) {
      if (!passport.contains(req)) {
        return false;
      }
    }
    return true;
  }


  // PART 2
  // TIME O(n)
  // SPACE O(1)

  public static int solve2(List<String> input) {
    HashMap<String, String> passport = new HashMap<>();
    int valid = 0;
    for (String line : input) {
      if (line.isEmpty()) {
        if (verify2(passport)) valid++;
        passport = new HashMap<>();
      } else {
        String[] entries = line.split(" ");
        for (String entry : entries) {
          String[] data = entry.split(":");
          passport.put(data[0], data[1]);
        }
      }
    }
    if (verify2(passport)) valid++;
    return valid;
  }

  static boolean verify2(HashMap<String, String> passport) {
    return verifyByr(passport)
        && verifyIyr(passport)
        && verifyEyr(passport)
        && verifyHgt(passport)
        && verifyHcl(passport)
        && verifyEcl(passport)
        && verifyPid(passport);
  }

  static boolean verifyByr(HashMap<String, String> passport) {
    if (!passport.containsKey("byr")) return false;
    String value = passport.get("byr");
    int intValue = Integer.parseInt(value);
    return value.length() == 4 && intValue >= 1920 && intValue <= 2002;
  }

  static boolean verifyIyr(HashMap<String, String> passport) {
    if (!passport.containsKey("iyr")) return false;
    String value = passport.get("iyr");
    int intValue = Integer.parseInt(value);
    return value.length() == 4 && intValue >= 2010 && intValue <= 2020;
  }

  static boolean verifyEyr(HashMap<String, String> passport) {
    if (!passport.containsKey("eyr")) return false;
    String value = passport.get("eyr");
    int intValue = Integer.parseInt(value);
    return value.length() == 4 && intValue >= 2020 && intValue <= 2030;
  }

  static boolean verifyHgt(HashMap<String, String> passport) {
    if (!passport.containsKey("hgt")) return false;
    String value = passport.get("hgt");
    String unit = value.substring(Math.max(0, value.length() - 2));
    int intValue = Integer.parseInt(value.substring(0, Math.max(0, value.length() - 2)));
    return (unit.equals("cm") && intValue >= 150 && intValue <= 193)
        || (unit.equals("in") && intValue >= 59 && intValue <= 76);
  }

  static boolean verifyHcl(HashMap<String, String> passport) {
    if (!passport.containsKey("hcl")) return false;
    String value = passport.get("hcl");
    if (value.charAt(0) != '#') return false;
    if (value.length() != 7) return false;
    List<Character> valid =
        List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');
    for (int i = 1; i < 6; i++) {
      if (!valid.contains(value.charAt(i))) return false;
    }
    return true;
  }

  static boolean verifyEcl(HashMap<String, String> passport) {
    if (!passport.containsKey("ecl")) return false;
    String value = passport.get("ecl");
    List<String> valid = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    return valid.contains(value);
  }

  static boolean verifyPid(HashMap<String, String> passport) {
    if (!passport.containsKey("pid")) return false;
    String value = passport.get("pid");
    if (value.length() != 9) return false;
    for (char c : value.toCharArray()) {
      if (!Character.isDigit(c)) return false;
    }
    return true;
  }

  public static void main(String[] args) {
    int result1 = Day04.solve1(FileReader.read("day04.txt"));
    int result2 = Day04.solve2(FileReader.read("day04.txt"));
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
