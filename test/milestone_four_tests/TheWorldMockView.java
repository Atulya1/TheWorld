package milestone_four_tests;

import the_world_view.ViewInterface;
import the_world_view_controller.ControllerInterface;

/**
 * Mock class for View class.
 */
public class TheWorldMockView implements ViewInterface {

  private final StringBuilder log;
  private ControllerInterface controller;


  /**
   * Constructs The world with the specified StringBuilder log.
   *
   * @param log The StringBuilder object used to store method invocations for testing purposes.
   */
  public TheWorldMockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void setupView(ControllerInterface controller) {
    this.controller = controller;
  }

  @Override
  public void showGameOverDialog(String message) {
    log.append(message);
  }

  @Override
  public void showDialog(String turnsExhausted) {
    log.append(turnsExhausted);
  }

  @Override
  public void refresh() {
    log.append("Refreshed");
  }
}
