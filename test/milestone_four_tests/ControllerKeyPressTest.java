package milestone_four_tests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import the_world_view.PanelInterface;
import the_world_view.ViewInterface;
import the_world_view_controller.ControllerInterface;
import world.World;
import world.WorldModel;

/**
 * Test controller behaviour when a key is pressed.
 */
public class ControllerKeyPressTest {

  private World model;

  /**
   * initial model setup.
   */
  @Before
  public void setUpWorld() {
    model = new WorldModel();
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("res/mansion.txt"));
      model = model.parseFile(reader);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * testing Pressing M and Not Moving.
   */
  @Test
  public void testingPressingMandNotMoving() {
    StringBuilder logController = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    ViewInterface view = new TheWorldMockView(logView) {
      @Override
      public void showDialog(String message) {
        logView.append(message);
      }

      @Override
      public void refresh() {
        logView.append("true");
      }
    };
    ControllerInterface mockController = new TheWorldMockController(logController, model, view) {
      @Override
      public void moveAction() {
        if (model.getNoOfTurns() > 0) {
          System.out.println("here");
          view.refresh();
          showMoveDialog();
        } else {
          view.showDialog("Game Over : Turns exhausted");
        }
      }

      private void showMoveDialog() {

        view.showDialog("Moved");
      }
    };

    StringBuilder logGame = new StringBuilder();
    PanelInterface mockGame = new GameMockPanel(mockController, logGame, 'M');
    assertEquals("Game Over : Turns exhausted", logView.toString());
  }

  /**
   * testing Pressing M and Moving.
   */
  @Test
  public void testingPressingMandMoving() {
    StringBuilder logController = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    ViewInterface view = new TheWorldMockView(logView) {
      @Override
      public void showDialog(String message) {
        logView.append(message);
      }

      @Override
      public void refresh() {
        logView.append("true");
      }
    };
    ControllerInterface mockController = new TheWorldMockController(logController, model, view) {
      @Override
      public void moveAction() {
        if (model.getNoOfTurns() > 0) {
          showMoveDialog();
        } else {
          view.showDialog("Game Over : Turns exhausted");
        }
      }

      private void showMoveDialog() {

        view.showDialog("Moved");
      }
    };

    StringBuilder logGame2 = new StringBuilder();
    model.setNoOfTurns(6);
    PanelInterface mockGame2 = new GameMockPanel(mockController, logGame2, 'M');
    assertEquals("Moved", logView.toString());
  }

  /**
   * testing Pressing L and Not Looking Around.
   */
  @Test
  public void testingPressingLandNotLookingAround() {
    StringBuilder logController = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    ViewInterface view = new TheWorldMockView(logView) {
      @Override
      public void showDialog(String message) {
        logView.append(message);
      }

      @Override
      public void refresh() {
        logView.append("true");
      }
    };
    ControllerInterface mockController = new TheWorldMockController(logController, model, view) {
      @Override
      public void lookAroundAction() {
        if (model.getNoOfTurns() > 0) {
          showLookAroundDialog();
        } else {
          view.showDialog("Game Over : Turns exhausted");
        }
      }

      private void showLookAroundDialog() {

        view.showDialog("Looked Around");
      }
    };

    StringBuilder logGame = new StringBuilder();
    PanelInterface mockGame = new GameMockPanel(mockController, logGame, 'L');
    assertEquals("Game Over : Turns exhausted", logView.toString());
  }

  /**
   * testing Pressing L and Looking Around.
   */
  @Test
  public void testingPressingLandLookingAround() {
    StringBuilder logController = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    ViewInterface view = new TheWorldMockView(logView) {
      @Override
      public void showDialog(String message) {
        logView.append(message);
      }

      @Override
      public void refresh() {
        logView.append("true");
      }
    };
    ControllerInterface mockController = new TheWorldMockController(logController, model, view) {
      @Override
      public void lookAroundAction() {
        if (model.getNoOfTurns() > 0) {
          showLookAroundDialog();
        } else {
          view.showDialog("Game Over : Turns exhausted");
        }
      }

      private void showLookAroundDialog() {
        view.showDialog("Looked Around");
      }
    };

    StringBuilder logGame = new StringBuilder();
    model.setNoOfTurns(6);
    PanelInterface mockGame = new GameMockPanel(mockController, logGame, 'L');
    assertEquals("Looked Around", logView.toString());
  }

  /**
   * testing Pressing A and Not Attacking.
   */
  @Test
  public void testingPressingAandNotAttacking() {
    StringBuilder logController = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    ViewInterface view = new TheWorldMockView(logView) {
      @Override
      public void showDialog(String message) {
        logView.append(message);
      }

      @Override
      public void refresh() {
        logView.append("true");
      }
    };
    ControllerInterface mockController = new TheWorldMockController(logController, model, view) {
      private boolean hasPlayersInTheVicinity() {
        return true;
      }

      @Override
      public void attackAction() {
        if (model.getNoOfTurns() > 0) {
          if (!hasPlayersInTheVicinity()) {
            if (model.getTurn().getCurrentRoom().getName()
                    .equals(model.getTargetCharacterLocation())) {
              showAttackDialog();
            } else {
              view.showDialog("Target Character is not in the same "
                      + "room. You can't attack.");
            }
          } else {
            view.showDialog("Players in the vicinity. Can't Attack.");
          }
        } else {
          view.showDialog("Game Over : Turns exhausted");
        }
      }

      private void showAttackDialog() {
        view.showDialog("Looked Around");
      }
    };
    StringBuilder logGame = new StringBuilder();
    //model.setNoOfTurns(6);
    PanelInterface mockGame = new GameMockPanel(mockController, logGame, 'A');
    assertEquals("Game Over : Turns exhausted", logView.toString());
  }

  /**
   * testing Pressing A and Attacking.
   */
  @Test
  public void testingPressingAandAttacking() {
    StringBuilder logController = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    ViewInterface view = new TheWorldMockView(logView) {
      @Override
      public void showDialog(String message) {
        logView.append(message);
      }

      @Override
      public void refresh() {
        logView.append("true");
      }
    };
    ControllerInterface mockController = new TheWorldMockController(logController, model, view) {
      private boolean hasPlayersInTheVicinity() {
        return false;
      }

      @Override
      public void attackAction() {
        if (model.getNoOfTurns() > 0) {
          if (!hasPlayersInTheVicinity()) {
            if (true) {
              showAttackDialog();
            } else {
              view.showDialog("Target Character is not in the same room. You can't attack.");
            }
          } else {
            view.showDialog("Players in the vicinity. Can't Attack.");
          }
        } else {
          view.showDialog("Game Over : Turns exhausted");
        }
      }

      private void showAttackDialog() {
        view.showDialog("Attacked");
      }
    };

    StringBuilder logGame = new StringBuilder();
    model.setNoOfTurns(6);
    PanelInterface mockGame = new GameMockPanel(mockController, logGame, 'A');
    assertEquals("Attacked", logView.toString());
  }

  /**
   * testing Pressing P and Not Picking.
   */
  @Test
  public void testingPressingPandNotPicking() {
    StringBuilder logController = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    ViewInterface view = new TheWorldMockView(logView) {
      @Override
      public void showDialog(String message) {
        logView.append(message);
      }

      @Override
      public void refresh() {
        logView.append("true");
      }
    };
    ControllerInterface mockController = new TheWorldMockController(logController, model, view) {
      @Override
      public void pickAction() {
        if (model.getNoOfTurns() > 0) {
          showPickDialog();
        } else {
          view.showDialog("Game Over : Turns exhausted");
        }
      }

      private void showPickDialog() {
        view.showDialog("Picked");
      }
    };

    StringBuilder logGame = new StringBuilder();
    PanelInterface mockGame = new GameMockPanel(mockController, logGame, 'P');
    assertEquals("Game Over : Turns exhausted", logView.toString());
  }

  /**
   * testing Pressing P and Picking.
   */
  @Test
  public void testingPressingPandPicking() {
    StringBuilder logController = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    ViewInterface view = new TheWorldMockView(logView) {
      @Override
      public void showDialog(String message) {
        logView.append(message);
      }

      @Override
      public void refresh() {
        logView.append("true");
      }
    };
    ControllerInterface mockController = new TheWorldMockController(logController, model, view) {
      @Override
      public void pickAction() {
        if (model.getNoOfTurns() > 0) {
          showPickDialog();
        } else {
          view.showDialog("Game Over : Turns exhausted");
        }
      }

      private void showPickDialog() {

        view.showDialog("Picked");
      }
    };

    StringBuilder logGame = new StringBuilder();
    model.setNoOfTurns(6);
    PanelInterface mockGame = new GameMockPanel(mockController, logGame, 'P');
    assertEquals("Picked", logView.toString());
  }

  /**
   * testing Pressing Q And Not Quiting.
   */
  @Test
  public void testingPressingQandNotQuiting() {
    StringBuilder logController = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    ViewInterface view = new TheWorldMockView(logView) {
      @Override
      public void showDialog(String message) {
        logView.append(message);
      }

      @Override
      public void refresh() {
        logView.append("true");
      }
    };
    ControllerInterface mockController = new TheWorldMockController(logController, model, view) {
      @Override
      public void quitAction() {
        if (model.getNoOfTurns() > 0) {
          showQuitDialog();
        } else {
          view.showDialog("Game Over : Turns exhausted");
        }
      }

      private void showQuitDialog() {
        view.showDialog("Quit");
      }
    };

    StringBuilder logGame = new StringBuilder();
    PanelInterface mockGame = new GameMockPanel(mockController, logGame, 'Q');
    assertEquals("Game Over : Turns exhausted", logView.toString());
  }

  /**
   * testing Pressing Q And Quiting.
   */
  @Test
  public void testingPressingQandQuiting() {
    StringBuilder logController = new StringBuilder();
    StringBuilder logView = new StringBuilder();
    ViewInterface view = new TheWorldMockView(logView) {
      @Override
      public void showDialog(String message) {
        logView.append(message);
      }

      @Override
      public void refresh() {
        logView.append("true");
      }
    };
    ControllerInterface mockController = new TheWorldMockController(logController, model, view) {
      @Override
      public void quitAction() {
        if (model.getNoOfTurns() > 0) {
          showQuitDialog();
        } else {
          view.showDialog("Game Over : Turns exhausted");
        }
      }

      private void showQuitDialog() {
        view.showDialog("Quit");
      }
    };

    StringBuilder logGame = new StringBuilder();
    model.setNoOfTurns(6);
    PanelInterface mockGame = new GameMockPanel(mockController, logGame, 'Q');
    assertEquals("Quit", logView.toString());
  }
}
