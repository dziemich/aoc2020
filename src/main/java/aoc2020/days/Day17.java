package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.*;

public class Day17 {

  static class Coord {
    int x;
    int y;
    int z;
    int w;

    public Coord(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    public Coord(int x, int y, int z, int w) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.w = w;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Coord coord = (Coord) o;
      return x == coord.x && y == coord.y && z == coord.z && w == coord.w;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y, z, w);
    }
  }

  public static long solve1(List<String> input, int cycles) {

    Set<Coord> actives = new HashSet<>();

    for (int i = 0; i < input.size(); i++) {
      String line = input.get(i);
      for (int j = 0; j < line.length(); j++) {
        if (line.charAt(j) == '#') actives.add(new Coord(j, i, 0));
      }
    }

    int xmin = -1;
    int ymin = -1;
    int zmin = -1;
    int xmax = input.size();
    int ymax = input.get(0).length();
    int zmax = 1;

    for (int cycle = 0; cycle < cycles; cycle++) {

      Set<Coord> copy = new HashSet<>(actives);

      for (int x = xmin; x <= xmax; x++) {
        for (int y = ymin; y <= ymax; y++) {
          for (int z = zmin; z <= zmax; z++) {
            int activeAround = 0;
            for (int i = -1; i <= 1; i++) {
              for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                  if (i == 0 && j == 0 && k == 0) continue;
                  if (actives.contains(new Coord(x + i, y + j, z + k))) {
                    activeAround++;
                  }
                }
              }
            }
            Coord checked = new Coord(x, y, z);
            if (actives.contains(checked) && !(activeAround == 2 || activeAround == 3)) {
              copy.remove(checked);
            }
            if (!actives.contains(checked) && activeAround == 3) {
              copy.add(checked);
            }
          }
        }
      }
      xmin--;
      ymin--;
      zmin--;
      xmax++;
      ymax++;
      zmax++;

      actives = copy;
    }
    return actives.size();
  }

  public static long solve2(List<String> input, int cycles) {

    Set<Coord> actives = new HashSet<>();

    for (int i = 0; i < input.size(); i++) {
      String line = input.get(i);
      for (int j = 0; j < line.length(); j++) {
        if (line.charAt(j) == '#') actives.add(new Coord(j, i, 0, 0));
      }
    }

    int xmin = -1;
    int ymin = -1;
    int zmin = -1;
    int wmin = -1;

    int xmax = input.size();
    int ymax = input.get(0).length();
    int zmax = 1;
    int wmax = 1;

    for (int cycle = 0; cycle < cycles; cycle++) {

      Set<Coord> copy = new HashSet<>(actives);

      for (int x = xmin; x <= xmax; x++) {
        for (int y = ymin; y <= ymax; y++) {
          for (int z = zmin; z <= zmax; z++) {
            for (int w = wmin; w <= wmax; w++) {
              int activeAround = 0;
              for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                  for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                      if (i == 0 && j == 0 && k == 0 && l == 0) continue;
                      if (actives.contains(new Coord(x + i, y + j, z + k, w + l))) {
                        activeAround++;
                      }
                    }
                  }
                }
              }
              Coord checked = new Coord(x, y, z, w);
              if (actives.contains(checked) && !(activeAround == 2 || activeAround == 3)) {
                copy.remove(checked);
              }
              if (!actives.contains(checked) && activeAround == 3) {
                copy.add(checked);
              }
            }
          }
        }
      }

      xmin--;
      ymin--;
      zmin--;
      wmin--;

      xmax++;
      ymax++;
      zmax++;
      wmax++;

      actives = copy;
    }
    return actives.size();
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day17.txt");
    long result1 = Day17.solve1(input, 6);
    long result2 = Day17.solve2(input, 6);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
