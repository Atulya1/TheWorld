package world;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import player.GameOverException;
import player.Player;
import room.Room;
import weapon.Weapon;

/**
 * Interface world has all the implementation of the board game "The World".
 */
public interface World {

  /**
   * adds player to the game.
   * @param player player.
   */
  void addPlayer(Player player);

  /**
   * Description of the world.
   * @return WorldDescription object.
   */
  WorldDescription getworldDescription();

  /**
   * PlayerImpl information.
   * @return PlayerImpl object.
   */
  List<Player> getPlayer();

  /**
   * list of spaces.
   * @return List of Room object.
   */
  List<Room> getRooms();

  /**
   * list of weapons.
   * @return List of Weapon Object.
   */
  List<Weapon> getWeapons();

  /**
   * Number of rooms in the world.
   * @return no of rooms.
   */
  int getNoOfRooms();

  /**
   * Number of weapons in the world.
   * @return no of weapons.
   */
  int getNoOfWeapons();

  /**
   * Parses the text file having information about the game.
   * @param reader Readable interface implementation Object.
   * @return WorldModel object.
   * @throws IOException ioexception.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws FileNotFoundException FileNotFoundException.
   */
  WorldModel parseFile(BufferedReader reader) throws IOException,
          IllegalArgumentException, FileNotFoundException;

  /**
   * Generates the graphical representation of the world.
   * @param world world.
   * @return BufferedImage of the world
   */
  BufferedImage createWorldGraphicalImage(World world);

  /**
   * Moves the target character inside the game.
   * @param roomName description.
   * @throws GameOverException GameOverException.
   */
  void moveTargetCharacter(String roomName) throws GameOverException;

  /**
   * returns the current room index.
   * @return integer
   */
  int getCurrentRoomIndex();

  /**
   * Displays information about all the rooms in the world game.
   * @return String.
   */
  String displayRoomInformation();

  /**
   * remove player.
   * @param player player object.
   */
  void removePlayer(String player);

  /**
   * display player info.
   * @param player object.
   * @return String.
   */
  String displayPlayerInfo(Player player);

  /**
   * returns currentWorld status.
   * @param world object.
   * @return string.
   */
  String getCurrentWorldStat(World world);

  /**
   * get winner.
   * @return string.
   */
  String getWinner();

  /**
   * isGameOver.
   * @return boolean.
   */
  boolean isGameOver();

  /**
   * returns turn of the player.
   * @return Player obejct.
   */
  Player getTurn();

  /**
   * player Name exists.
   * @param name String.
   * @return boolean.
   */
  boolean playerNameExists(String name);

  /**
   * display single room information.
   * @param roomName room name in string.
   * @return String.
   */
  String displaySingleRoomInfomation(String roomName, World w);

  /**
   * add weapon to the player.
   * @param turn current player object.
   * @param weapon weapon to add.
   */
  void addWeaponToPlayer(Player turn, String weapon);

  /**
   * changes the turn of the player.
   */
  void nextTurn();

  /**
   * change the turn of the player (view).
   * @throws GameOverException GameOverException.
   */
  void nextT() throws GameOverException;

  /**
   * get Target Character's description.
   * @return String.
   */
  String getTargetCharacter();

  /**
   * get Target Character's Pet description.
   * @return String.
   */
  String getTargetCharacterPet();

  /**
   * get Target Character's Pet Location.
   * @return Room.
   */
  String getTargetCharacterPetLocation();

  /**
   * get Target Character's Location.
   * @return room name.
   */
  String getTargetCharacterLocation();

  /**
   * Get the dfs of all the neighbours.
   * Used to move the pet.
   */
  void getNeighbours();

  /**
   * move pet.
   */
  void movePet(int roomNumber);

  /**
   * wandering pet.
   */
  void wanderingPet();

  /**
   * move Target character.
   */
  void moveTcharacter();

  /**
   * move player.
   * @param roomName room name.
   */
  void movePlayer(String roomName);

  /**
   * method to decrease the health when moving.
   *
   * @param weapon of type Room Object.
   * @return Integer.
   */
  void decreaseHealth(String weapon) throws IllegalArgumentException, GameOverException;

  /**
   * Check if pet present.
   * @param hasMoved boolean.
   * @return boolean.
   */
  boolean hasPetMoved(boolean hasMoved);

  /**
   * No of turns allowed.
   */
  void setNoOfTurns(int turns);

  /**
   * current turns left.
   * @return integer.
   */
  int getNoOfTurns();

  /**
   * return current index of the target character.
   */
  int getCurrentIndexTargetCharacter();

  /**
   * returns the current health of the target Character.
   * @return health.
   */
  int getHealth();
}