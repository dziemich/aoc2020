package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.List;

public class Day12 {

  // PART 1
  // TIME: O(n)
  // SPACE: O(1)

  public static int solve1(List<String> input) {
    int hor = 0;
    int vert = 0;

    String directions = "NESW";
    char currentDir = 'E';

    for (String line : input) {

      String action = line.substring(0, 1);
      int value = Integer.parseInt(line.substring(1));

      switch (action) {
        case "N":
          vert += value;
          break;
        case "S":
          vert -= value;
          break;
        case "E":
          hor += value;
          break;
        case "W":
          hor -= value;
          break;
        case "L":
          int lIndex = directions.indexOf(currentDir);
          lIndex = (4 + lIndex - value / 90) % 4;
          currentDir = directions.charAt(lIndex);
          break;
        case "R":
          int rIndex = directions.indexOf(currentDir);
          rIndex = (rIndex + value / 90) % 4;
          currentDir = directions.charAt(rIndex);
          break;
        case "F":
          switch (currentDir) {
            case 'N':
              vert += value;
              break;
            case 'S':
              vert -= value;
              break;
            case 'E':
              hor += value;
              break;
            case 'W':
              hor -= value;
              break;
          }
      }
    }
    return Math.abs(hor) + Math.abs(vert);
  }

  // PART 1
  // TIME: O(n)
  // SPACE: O(1)

  public static int solve2(List<String> input) {
    int hor = 0;
    int vert = 0;

    int waypointHor = 10;
    int waypointVert = 1;

    for (String line : input) {

      String action = line.substring(0, 1);
      int value = Integer.parseInt(line.substring(1));

      switch (action) {
        case "N":
          waypointVert += value;
          break;
        case "S":
          waypointVert -= value;
          break;
        case "E":
          waypointHor += value;
          break;
        case "W":
          waypointHor -= value;
          break;
        case "L":
          while (value > 0) {
            int tmpHor = waypointHor;
            waypointHor = waypointVert;
            waypointVert = tmpHor;
            waypointHor *= -1;
            value -= 90;
          }
          break;
        case "R":
          while (value > 0) {
            int tmpHor = waypointHor;
            waypointHor = waypointVert;
            waypointVert = tmpHor;
            waypointVert *= -1;
            value -= 90;
          }
          break;
        case "F":
          hor += waypointHor * value;
          vert += waypointVert * value;
      }
    }
    return Math.abs(hor) + Math.abs(vert);
  }

  public static void main(String[] args) {

    List<String> input = FileReader.read("day12.txt");
    int result1 = Day12.solve1(input);
    int result2 = Day12.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
