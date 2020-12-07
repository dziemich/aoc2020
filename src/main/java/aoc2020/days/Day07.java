package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.*;

public class Day07 {

  // PART 1
  // TIME O(n)
  // SPACE O(n)

  public static final String BEGIN_AT = "shinygold";

  public static int solve1(List<String> input) {

    HashMap<String, List<String>> graph = new HashMap<>();

    for (String line : input) {
      if (line.contains("no other bags")) continue;
      String[] split = line.split(" ");

      String target = split[0] + split[1];

      int index = 6;
      while (split.length > index) {
        String key = split[index - 1] + split[index];
        var list = graph.getOrDefault(key, new ArrayList<>());
        list.add(target);
        graph.put(key, list);
        index += 4;
      }
    }

    int counter = 0;

    Queue<String> q = new LinkedList<>();
    q.add(BEGIN_AT);

    Set<String> visited = new HashSet<>();

    while (!q.isEmpty()) {
      String key = q.remove();
      for (String s : graph.getOrDefault(key, new LinkedList<>())) {
        if (!visited.contains(s)) {
          q.offer(s);
          counter++;
          visited.add(s);
        }
      }
    }
    return counter;
  }

  // PART 2
  // TIME O(n)
  // SPACE O(n)

  static class Bag {
    public String color;
    public int count;

    public Bag(String color, int count) {
      this.color = color;
      this.count = count;
    }
  }

  public static int solve2(List<String> input) {
    HashMap<String, List<Bag>> graph = new HashMap<>();

    for (String line : input) {
      String[] split = line.split(" ");
      String parent = split[0] + split[1];

      if (line.contains("no other bags")) {
        graph.put(parent, new ArrayList<>());
        continue;
      }

      List<Bag> bags = new ArrayList<>();
      int index = 6;

      while (split.length > index) {
        String color = split[index - 1] + split[index];
        int count = Integer.parseInt(split[index - 2]);
        bags.add(new Bag(color, count));
        index += 4;
      }
      graph.put(parent, bags);
    }

    return dfs(BEGIN_AT, graph) - 1;
  }

  static int dfs(String color, HashMap<String, List<Bag>> graph) {
    int counter = 1;
    for (Bag b : graph.getOrDefault(color, new ArrayList<>())) {
      counter += b.count * dfs(b.color, graph);
    }
    return counter;
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day07.txt");
    int result1 = Day07.solve1(input);
    int result2 = Day07.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
