package milestone_four_tests;

import the_world_view.PanelInterface;
import the_world_view_controller.ControllerInterface;

/**
 * Mock class for Welcome Panel.
 */
public class WelcomeMockPanel implements PanelInterface {
  private final StringBuilder log;
  private final ControllerInterface controller;

  /**
   * constructor.
   * @param log log.
   * @param controller controller.
   * @param startOption start option.
   */
  public WelcomeMockPanel(StringBuilder log, ControllerInterface controller,
                          String startOption) {
    this.log = log;
    this.controller = controller;
    initComponents(startOption);
  }

  @Override
  public void refresh() {

  }

  /**
   * initialize components.
   * @param startOption start options.
   */
  private void initComponents(String startOption) {
    showPopupMenu(startOption);
  }

  /**
   * show Popup Menu.
   * @param startOption start Option.
   */
  private void showPopupMenu(String startOption) {
    if ("current".equals(startOption)) {
      log.append("current specification");
    } else {
      controller.restartGame();
      log.append("new specification");
    }
  }
}
