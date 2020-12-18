package aoc2020.days;

import aoc2020.utils.FileReader;

import java.util.List;
import java.util.Stack;

public class Day18 {

  public static long solve1(List<String> input) {
    long sum = 0;
    for (String eq : input) {
      long result = calculate1(eq.replaceAll("\\s", "").trim());
      sum += result;
    }
    return sum;
  }

  public static long solve2(List<String> input) {
    long sum = 0;
    for (String eq : input) {
      long result = calculate2(eq.replaceAll("\\s", "").trim(), 0).num;
      sum += result;
    }
    return sum;
  }

  private static long calculate1(String equation) {
    Stack<Character> operators = new Stack<>();
    Stack<Long> longs = new Stack<>();

    for (char c : equation.toCharArray()) {
      if (Character.isDigit(c)) {
        if (longs.isEmpty()) {
          longs.push((long) (c - '0'));
        } else {
          if (operators.peek() == '+') {
            operators.pop();
            longs.push(longs.pop() + c - '0');
          } else if (operators.peek() == '*') {
            operators.pop();
            longs.push(longs.pop() * (c - '0'));
          } else {
            longs.push((long) c - '0');
          }
        }
      } else {
        if (c == ')') {
          operators.pop();
          if (!operators.isEmpty() && longs.size() > 1) {
            if (operators.peek() == '+') {
              operators.pop();
              longs.push(longs.pop() + longs.pop());
            } else if (operators.peek() == '*') {
              operators.pop();
              longs.push(longs.pop() * longs.pop());
            }
          }
        } else {
          operators.push(c);
        }
      }
    }
    return longs.pop();
  }
  ;

  static class Pair {
    int index;
    long num;

    public Pair(int index, long num) {
      this.index = index;
      this.num = num;
    }
  }

  static Pair calculate2(String equation, int index) {

    Stack<Long> longs = new Stack<>();
    Stack<Character> operators = new Stack<>();

    for (int i = index; i < equation.length(); i++) {
      char c = equation.charAt(i);
      if (Character.isDigit(c)) {
        if (!operators.isEmpty() && operators.peek() == '+') {
          longs.push(longs.pop() + ((long) c - '0'));
        } else {
          longs.push((long) c - '0');
        }
      } else {
        if (c == '(') {
          Pair p = calculate2(equation, i + 1);
          i = p.index;
          if (!operators.isEmpty() && operators.peek() == '+') {
            longs.push(longs.pop() + p.num);
          } else {
            longs.push(p.num);
          }
        } else if (c == ')') {
          Long num = longs.stream().reduce((l1, l2) -> l1 * l2).get();
          return new Pair(i, num);
        } else {
          operators.push(c);
        }
      }
    }
    return new Pair(-1, longs.stream().reduce((l1, l2) -> l1 * l2).get());
  }

  public static void main(String[] args) {
    List<String> input = FileReader.read("day18.txt");
    long result1 = Day18.solve1(input);
    long result2 = Day18.solve2(input);
    System.out.println("PART 1: " + result1);
    System.out.println("PART 2: " + result2);
  }
}
