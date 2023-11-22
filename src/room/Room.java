package room;

import java.util.List;
import player.Player;
import weapon.Weapon;
import world.World;

/**
 * Room interface declares methods needs as spaces in the grid of the board game.
 */
public interface Room {

  /**
   * coordinates of the upper left corner of the room.
   *
   * @return the upper right left coordinates of the room.
   */
  public Coordinate getUpperLeftCorner();

  /**
   * coordinates of the lower right corner of the room.
   *
   * @return the lower right corner coordinates of the room.
   */
  public Coordinate getLowerRightCorner();

  /**
   * set all neighbors of the room.
   * @param rooms list of rooms.
   */
  public void addNeighbor(List<Room> rooms);

  /**
   * get all neighbors of the room.
   *
   * @return the list of neighbor of the room.
   */
  public List<Room> getNeighbor();

  /**
   * get the name of the room.
   *
   * @return the name of the room.
   */
  String getName();

  /**
   * get the weapons inside the room.
   *
   * @return room index.
   */
  List<Weapon> getWeapons();

  /**
   * set the weapons inside the room.
   * @param weapons weapon
   */
  void addWeapons(Weapon weapons);

  /**
   * Displays information about a single room in the world game.
   * @param room room.
   * @return String.
   */
  String displaySingleRoomInformation(Room room, World w);

  /**
   * Add player to the room.
   * @param player player.
   */
  void addPlayer(Player player);

  /**
   * Remove player from the room.
   * @param player player.
   */
  void removePlayer(String player);

  /**
   * remove weapon from the room.
   * @param weapon weapon.
   */
  void removeWeapon(String weapon);

  /**
   * has pet or not.
   * @return boolean.
   */
  boolean hasPet();

  /**
   * has target character or not.
   * @return boolean.
   */
  boolean hasTargetCharacter();

  /**
   * update Target Character's Presence in the room.
   * @param t boolean.
   */
  void updateTargetCharacterPresence(boolean t);

  /**
   * update Pet Presence in the room.
   * @param t boolean.
   */
  void updatePetPresence(boolean t);

  /**
   * get room index.
   * @return int.
   */
  int getIndex();

  /**
   * get list of players in the room.
   * @return List of players.
   */
  List<Player> getPlayers();
}
