package the_world_view_controller;

import java.util.List;
import player.Player;
import room.Room;

/**
 * Controller Interface for The World Game. This class has
 * the functions that makes model and view interact.
 */
public interface ControllerInterface {

  /**
   * This method starts the game.
   */
  void playGame();

  /**
   * This method returns all the rooms.
   *
   * @return Array with room names.
   */
  String[] createRoomString();

  /**
   * This method adds the player into the game.
   *
   * @param name       player name.
   * @param weapon     weapon name.
   * @param space      space name.
   * @param playerType player type.
   */
  void addPlayer(String name, String weapon, String space, String playerType);

  /**
   * This method sets the maximum number of turns.
   *
   * @param turns turns.
   */
  void setTurns(String turns);

  /**
   * Method to move the player on key press.
   */
  void moveAction();

  /**
   * Method to pick the weapon on key press.
   */
  void pickAction();

  /**
   * Method to attack on key press.
   */
  void attackAction();

  /**
   * Method to look around on key press.
   */
  void lookAroundAction();

  /**
   * Method to quit turn on key press.
   */
  void quitAction();

  /**
   * returns current Room of the target character.
   *
   * @return Room.
   */
  Room getTargetCharacterCurrentRoom();

  /**
   * This method returns List of Players.
   *
   * @return players.
   */
  List<Player> getPlayers();

  /**
   * this method returns player with current turn.
   *
   * @return Player.
   */
  Player getTurn();

  /**
   * This method returns the current health of the target character.
   *
   * @return integer.
   */
  int getTargetCharacterHealth();

  /**
   * returns the no. of turns left.
   *
   * @return integer.
   */
  int getNoOfTurns();

  /**
   * returns list of Rooms.
   *
   * @return List of rooms.
   */
  List<Room> getRooms();

  /**
   * This method shows error dialog.
   *
   * @param error String.
   */
  void showErrorDialog(String error);

  /**
   * this method resets the game with new specifications.
   */
  void restartGame();
}
