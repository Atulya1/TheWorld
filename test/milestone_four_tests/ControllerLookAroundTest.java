package milestone_four_tests;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import the_world_view.ViewInterface;
import the_world_view_controller.Controller;
import the_world_view_controller.ControllerInterface;
import world.World;
import world.WorldModel;

/**
 * Testing Controller for Look Around Operation.
 */
public class ControllerLookAroundTest {

  private World model;
  private ViewInterface view;
  private ControllerInterface controller;
  private StringBuilder log = new StringBuilder();

  /**
   * Set method to initialize model, view, and controller object for running the tests.
   */
  @Before
  public void setUpWorld() {
    String inputFile = "res/mansion.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      model = new WorldModel();
      model = model.parseFile(reader);
      assertNotNull(model);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    view = new TheWorldMockView(log) {
      @Override
      public void showDialog(String str) {
        log.append(str);
      }
    };
    controller = new Controller(view, model);
    controller.addPlayer("Player 1", "5", "Armory", "Human");
    controller.addPlayer("Player 2", "5", "Billiard Room",
            "Human");
    controller.setTurns("20");
  }

  /**
   * Testing look around using dialog.
   */
  @Test
  public void testingLookAround() {
    controller.lookAroundAction();
    controller.moveAction();
    controller.lookAroundAction();
    controller.pickAction();
    controller.lookAroundAction();
    controller.quitAction();
    controller.lookAroundAction();
    controller.attackAction();
    controller.lookAroundAction();
  }
}
