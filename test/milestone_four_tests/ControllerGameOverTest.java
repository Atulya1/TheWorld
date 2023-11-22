package milestone_four_tests;

import static org.junit.Assert.assertEquals;
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
 * Test class to test Controller for Game Over.
 */
public class ControllerGameOverTest {

  private World model;
  private ViewInterface view;
  private ControllerInterface controller;
  private StringBuilder log = new StringBuilder();

  /**
   * Set method to initialize model, view and controller object for running the tests.
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
   * Testing Game Over due to turn exhausted.
   */
  @Test
  public void testingGameOverTurnExhausted() {
    assertEquals(5, controller.getNoOfTurns());
    controller.quitAction();
    assertEquals("Refreshed", log.toString());
    assertEquals(4, controller.getNoOfTurns());
    controller.quitAction();
    assertEquals("RefreshedRefreshed", log.toString());
    assertEquals(3, controller.getNoOfTurns());
    controller.quitAction();
    assertEquals("RefreshedRefreshedRefreshed", log.toString());
    assertEquals(2, controller.getNoOfTurns());
    controller.quitAction();
    assertEquals("RefreshedRefreshedRefreshedRefreshed", log.toString());
    assertEquals(1, controller.getNoOfTurns());
    controller.quitAction();
    assertEquals("RefreshedRefreshedRefreshedRefreshedRefreshed", log.toString());
    assertEquals(0, controller.getNoOfTurns());
    controller.quitAction();
    assertEquals("RefreshedRefreshedRefreshedRefreshedRefreshedGame Over :"
            + " Turns exhausted", log.toString());
  }

  /**
   * Testing Game Over when player wins.
   */
  @Test
  public void testingGameOverPlayerWin() {
    controller.moveAction();
    assertEquals("Refreshed", log.toString());
    assertEquals("Drawing Room", controller.getPlayers().get(0).getCurrentRoom().getName());
    controller.pickAction();
    assertEquals("Billiard Cue", controller.getPlayers().get(1)
            .getWeapons().get(0).getWeaponName());
    controller.attackAction();
    assertEquals(2, controller.getTargetCharacterHealth());
    controller.pickAction();
    assertEquals("Letter Opener", controller.getPlayers().get(0)
            .getWeapons().get(0).getWeaponName());
    controller.quitAction();
    controller.moveAction();
    controller.attackAction();
    assertEquals(0, controller.getTargetCharacterHealth());
    assertEquals("RefreshedRefreshedRefreshedRefreshedRefreshedRefreshedGame over. "
            + "Winner is Player 1Refreshed", log.toString());
  }
}
