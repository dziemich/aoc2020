package aoc2020.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

  public static List<String> read(String filename) {
    try {
      return Files.readAllLines(Path.of(ClassLoader.getSystemResource(filename).toURI()));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
