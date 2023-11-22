package milestone_four_tests;

import java.util.List;
import player.Player;
import room.Room;
import the_world_view.ViewInterface;
import the_world_view_controller.ControllerInterface;
import world.World;

/**
 * Mock class for Controller.
 */
public class TheWorldMockController implements ControllerInterface {
  private final StringBuilder log;
  private World model;
  private final ViewInterface view;

  /**
   * Constructor.
   * @param log log.
   * @param model model.
   * @param view view.
   */
  public TheWorldMockController(StringBuilder log, World model, ViewInterface view) {
    this.log = log;
    this.model = model;
    this.view = view;
  }

  @Override
  public void playGame() {

  }

  @Override
  public String[] createRoomString() {
    return new String[0];
  }

  @Override
  public void addPlayer(String name, String weapon, String space, String playerType) {

  }

  @Override
  public void setTurns(String turns) {

  }

  @Override
  public void moveAction() {

  }

  @Override
  public void pickAction() {

  }

  @Override
  public void attackAction() {

  }

  @Override
  public void lookAroundAction() {

  }

  @Override
  public void quitAction() {

  }

  @Override
  public Room getTargetCharacterCurrentRoom() {
    return null;
  }

  @Override
  public List<Player> getPlayers() {
    return null;
  }

  @Override
  public Player getTurn() {
    return null;
  }

  @Override
  public int getTargetCharacterHealth() {
    return 0;
  }

  @Override
  public int getNoOfTurns() {
    return 0;
  }

  @Override
  public List<Room> getRooms() {
    return null;
  }

  @Override
  public void showErrorDialog(String error) {

  }

  @Override
  public void restartGame() {

  }
}
