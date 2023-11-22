package the_world_view;

import the_world_view_controller.ControllerInterface;

/**
 * This is the interface for the View for the game.
 */
public interface ViewInterface {

  /**
   * This method sets up the view for the game.
   * @param controller controller.
   */
  void setupView(ControllerInterface controller);

  /**
   * this method shows game over dialog.
   * @param message message.
   */
  void showGameOverDialog(String message);

  /**
   * this method shows any dialog.
   * @param turnsExhausted String.
   */
  void showDialog(String turnsExhausted);

  /**
   * this method repaints the panel.
   */
  void refresh();
}
