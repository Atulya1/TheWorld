package milestone_four_tests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import the_world_view.PanelInterface;
import the_world_view.View;
import the_world_view.ViewInterface;
import the_world_view_controller.Controller;
import the_world_view_controller.ControllerInterface;
import world.World;
import world.WorldModel;

/**
 * Testing controller for setup panel operations.
 */
public class ControllerSetupPanelTest {

  private ControllerInterface controller;
  private ViewInterface view;

  /**
   * setting up model, view and controller.
   */
  @Before
  public void setUpWorld() {
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
    this.view = view;
    this.controller = controller;
  }

  /**
   * test Starting Game Without Adding Players.
   */
  @Test
  public void testStartingGameWithoutAddingPlayers() {
    StringBuilder logSetupPanel = new StringBuilder();
    controller.setTurns("6");
    PanelInterface mockSetup = new SetupMockPanel(controller, logSetupPanel);
    assertEquals("Players not added", logSetupPanel.toString());
  }

  /**
   * test Starting Game Without Adding Turn.
   */
  @Test
  public void testStartingGameWithoutAddingTurn() {
    StringBuilder logSetupPanel = new StringBuilder();
    controller.addPlayer("Atul", "3", "Piazza", "Human");
    PanelInterface mockSetup = new SetupMockPanel(controller, logSetupPanel);
    assertEquals("Turns not added", logSetupPanel.toString());
  }

  /**
   * test Starting Game After Adding Turn And Players.
   */
  @Test
  public void testStartingGameAfterAddingTurnAndPlayers() {
    StringBuilder logSetupPanel = new StringBuilder();
    controller.addPlayer("Atul", "3", "Piazza", "Human");
    controller.setTurns("6");
    PanelInterface mockSetup = new SetupMockPanel(controller, logSetupPanel);
    assertEquals("Game started", logSetupPanel.toString());
  }

  /**
   * test Adding Players.
   */
  @Test
  public void testAddingPlayers() {
    StringBuilder logSetupPanel = new StringBuilder();
    controller.addPlayer("Atul", "3", "Piazza", "Human");
    controller.setTurns("6");
    PanelInterface mockSetup = new SetupMockPanel(controller, logSetupPanel);
    assertEquals("Game started", logSetupPanel.toString());
    assertEquals(1, controller.getPlayers().size());
  }

  /**
   * test Adding Turn.
   */
  @Test
  public void testAddingTurn() {
    StringBuilder logSetupPanel = new StringBuilder();
    controller.addPlayer("Atul", "3", "Piazza", "Human");
    controller.setTurns("6");
    PanelInterface mockSetup = new SetupMockPanel(controller, logSetupPanel);
    assertEquals("Game started", logSetupPanel.toString());
    assertEquals(6, controller.getNoOfTurns());
  }
}
