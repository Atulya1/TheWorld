package milestone_four_tests;

import the_world_view.PanelInterface;
import the_world_view_controller.ControllerInterface;

/**
 * Mock class for GamePanel.
 */
public class GameMockPanel implements PanelInterface {
  private ControllerInterface controller;
  private StringBuilder log;
  private boolean refreshed = false;
  private char c;

  /**
   * constructor.
   * @param controller controller.
   * @param log log.
   * @param c char.
   */
  public GameMockPanel(ControllerInterface controller, StringBuilder log, char c) {
    this.controller = controller;
    this.log = log;
    this.c = c;
    bindKeys();
  }

  @Override
  public void refresh() {
    refreshed = true;
    log.append(refreshed);
  }

  /**
   * key press.
   */
  private void bindKeys() {
    if (c == 'L' || c == 'l') {
      controller.lookAroundAction();
    } else if (c == 'M' || c == 'm') {
      controller.moveAction();
    } else if (c == 'P' || c == 'p') {
      controller.pickAction();
    } else if (c == 'Q' || c == 'q') {
      controller.quitAction();
    } else if (c == 'A' || c == 'a') {
      controller.attackAction();
    }
  }
}
