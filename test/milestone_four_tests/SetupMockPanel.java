package milestone_four_tests;

import the_world_view.PanelInterface;
import the_world_view_controller.ControllerInterface;

/**
 * Mock class for Setup panel.
 */
public class SetupMockPanel implements PanelInterface {

  private ControllerInterface controller;
  private StringBuilder log;

  /**
   *  constructor.
   * @param controller controller.
   * @param log log.
   */
  public SetupMockPanel(ControllerInterface controller, StringBuilder log) {
    this.controller = controller;
    this.log = log;
    initaialize();
  }

  /**
   * initialization.
   */
  private void initaialize() {
    if (controller.getNoOfTurns() <= 0) {
      log.append("Turns not added");
    } else if (controller.getPlayers().size() <= 0) {
      log.append("Players not added");
    } else {
      log.append("Game started");
    }
  }

  @Override
  public void refresh() {

  }
}
