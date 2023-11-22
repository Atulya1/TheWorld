package milestone_four_tests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;
import the_world_view.PanelInterface;
import the_world_view.ViewInterface;
import the_world_view_controller.Controller;
import the_world_view_controller.ControllerInterface;
import world.World;
import world.WorldModel;

/**
 * Test class to test Controller operation for Welcome Panel.
 */
public class ControllerWelcomePanelTest {

  /**
   * Test for the handleCellClick method when the game is already over.
   * It verifies that the model and view are not updated when the game is over.
   */
  @Test
  public void testSetUpGame() throws IOException {

    StringBuilder logModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    World mockModel = new TheWorldMockModel(logModel) {
      @Override
      public WorldModel parseFile(BufferedReader reader) throws IOException,
              IllegalArgumentException, FileNotFoundException {
        logModel.append(reader.readLine());
        return null;
      }
    };

    ViewInterface mockView = new TheWorldMockView(logView) {
      boolean setupViewCalled = false;
      ControllerInterface controllerInterface;

      @Override
      public void setupView(ControllerInterface controller) {
        this.controllerInterface = controller;
        setupViewCalled = true;
        logView.append(true);
      }
    };
    ControllerInterface controller = new Controller(mockView, mockModel);
    controller.playGame();
    String expected = "true";
    assertEquals(expected, logView.toString());
    mockModel.parseFile(new BufferedReader(new FileReader("res/mansion.txt")));
    assertEquals("36 30 Doctor Lucky's Mansion", logModel.toString());
  }

  /**
   * test Start Game With Current Specification.
   * @throws IOException IOException.
   */
  @Test
  public void testStartGameWithCurrentSpecification() throws IOException {
    StringBuilder logModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    StringBuilder logController = new StringBuilder();
    StringBuilder logWelcomePanel = new StringBuilder();
    World mockModel = new TheWorldMockModel(logModel) {
      @Override
      public WorldModel parseFile(BufferedReader reader) throws
              IOException, IllegalArgumentException, FileNotFoundException {
        logModel.append(reader.readLine());
        return null;
      }
    };
    ViewInterface mockView = new TheWorldMockView(logView) {
      private ControllerInterface controller;

      @Override
      public void setupView(ControllerInterface controller) {
        this.controller = controller;
        PanelInterface mockPanel = new WelcomeMockPanel(logWelcomePanel, controller, "current");
      }
    };
    ControllerInterface mockController = new Controller(mockView, mockModel);
    mockController.playGame();
    mockModel.parseFile(new BufferedReader(new FileReader("res/mansion.txt")));
    assertEquals("36 30 Doctor Lucky's Mansion", logModel.toString());
    assertEquals("current specification", logWelcomePanel.toString());
  }

  /**
   * test Start Game With New Specification.
   * @throws IOException IOException.
   */
  @Test
  public void testStartGameWithNewSpecification() throws IOException {
    StringBuilder logModel = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    StringBuilder logController = new StringBuilder();
    StringBuilder logWelcomePanel = new StringBuilder();

    ViewInterface mockView = new TheWorldMockView(logView) {
      private ControllerInterface controller;

      @Override
      public void setupView(ControllerInterface controller) {
        this.controller = controller;
        PanelInterface mockPanel = new WelcomeMockPanel(logWelcomePanel, controller, "new");
      }
    };
    World model = new WorldModel();
    model = model.parseFile(new BufferedReader(new FileReader("res/mansion.txt")));
    //initial no. of players in the model.
    assertEquals(model.getPlayer().size(), 0);
    ControllerInterface testController = new Controller(mockView, model);
    testController.addPlayer("Atul", "3", "Armory", "Human");
    //player after adding to the model.
    assertEquals(testController.getPlayers().size(), 1);
    assertEquals(model.getPlayer().get(0).playerDescription(), "Player{name='Atul', "
            + "currentRoom=Armory, weapons=[], player type=HUMAN}");
    //restarting the game.
    mockView.setupView(testController);
    assertEquals("new specification", logWelcomePanel.toString());
    assertEquals(testController.getPlayers().size(), 0);
  }
}
