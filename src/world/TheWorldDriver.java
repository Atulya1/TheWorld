package world;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Driver class for WorldModel. It has a test fun for the game "The World".
 * It also generates the graphical representation of the "The world".
 */
public class TheWorldDriver {

  /**
   * Main method.
   * @param args args.
   */
  public static void main(String[] args) {

    try {
      Scanner scan = new Scanner(System.in);
      Appendable out = System.out;
      out.append("Enter file name with path").append("\n");
      String fileName = scan.nextLine();
      World world = new WorldModel();
      BufferedReader reader;
      try {
        reader = new BufferedReader(new FileReader(fileName));
        world = world.parseFile(reader);
      } catch (IOException e) {
        out.append(e.getMessage());
      }
      out.append(world.toString());
      out.append("World name: ").append(world.getworldDescription().getWorldName()).append("\n");
      out.append("Spaces: ").append(String.valueOf(world.getRooms().size())).append("\n");
      out.append("Items: ").append(String.valueOf(world.getWeapons().size())).append("\n");
      out.append("Rooms and neigbours").append("\n");
      Readable input = new InputStreamReader(System.in);
      new WorldConsoleController(input, out).playGame(world);
      scan.close();
      //System.out.println(world.getNeighbours());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}