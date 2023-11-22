package the_world_view_controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import the_world_view.View;
import the_world_view.ViewInterface;
import world.World;
import world.WorldModel;

/**
 * Driver class for The World game.
 */
public class Main {

  /**
   * Main method to start the game.
   * @param args argument.
   */
  public static void main(String[] args) {
    World model = new WorldModel();
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("res/mansion.txt"));
      model = model.parseFile(reader);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    ViewInterface view = new View();
    ControllerInterface controller = new Controller(view, model);
    controller.playGame();
  }
}
