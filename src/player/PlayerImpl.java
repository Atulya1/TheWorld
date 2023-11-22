package player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import room.Room;
import weapon.Weapon;

/**
 * PlayerImpl class defines the players and it's attributes.
 */
public class PlayerImpl implements Player {
  private final String name;
  private Room currentRoom;
  private List<Weapon> weapons;
  private final int maxWeaponnLimit;
  private int currentWeaponLimit;

  private PlayerType playerType;

  /**
   * Constructor for PlayerImpl class.
   *
   * @param name Name of the character.
   * @param maxWeaponnLimit max no. of weapons.
   * @param enteredRoom entered room.
   * @param playerType playerType.
   * @throws IllegalArgumentException if health is greater than 100.
   */
  public PlayerImpl(String name, Room enteredRoom, int maxWeaponnLimit, PlayerType playerType)
          throws IllegalArgumentException {
    this.currentRoom = enteredRoom;
    this.name = name;
    this.maxWeaponnLimit = maxWeaponnLimit;
    this.weapons = new ArrayList<>();
    this.currentWeaponLimit = maxWeaponnLimit;
    this.playerType = playerType;
  }

  /**
   * get name of the character.
   * @return name of the character in String.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * set current room of the player.
   * @param room to be added.
   */
  @Override
  public void setCurrentRoom(Room room) {
    this.currentRoom = room;
  }

  /**
   * get current room of the player.
   * @return Room object.
   */
  @Override
  public Room getCurrentRoom() {
    return currentRoom;
  }

  /**
   * add weapon that a player has.
   * @param weapon weapon.
   */
  @Override
  public void addWeapon(Weapon weapon) {
    weapons.add(weapon);
  }

  /**
   * list of weapons that a player has.
   * @return list of weapons.
   */
  @Override
  public List<Weapon> getWeapons() {
    return weapons;
  }

  /**
   * return weapon limit.
   * @return maxWeaponnLimit.
   */
  @Override
  public int getWeaponLimit() {
    return maxWeaponnLimit;
  }

  /**
   *  return current Weapon Limit.
   * @return currentWeaponLimit
   */
  @Override
  public int getCurrentWeaponLimit() {
    return currentWeaponLimit;
  }

  /**
   * current player description.
   * @return String.
   */
  @Override
  public String playerDescription() {
    return "Player{"
            + "name='" + name + '\''
            + ", currentRoom=" + currentRoom.getName()
            + ", weapons=" + weapons.toString()
            + ", player type=" + playerType.toString()
            + '}';
  }

  /**
   * reduces the weapon count when player holds a weapon.
   */
  @Override
  public void reduceWeaponCount() {
    currentWeaponLimit = currentWeaponLimit - 1;
  }

  /**
   * Override toString() Method.
   * @return String
   */
  @Override
  public String toString() {
    return "PlayerImpl{"
            + "name='" + name
            + '\''
            + ", currentRoom=" + currentRoom.getName()
            + ", player type=" + playerType.toString()
            + '}';
  }

  /**
   * Override equals method.
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlayerImpl character = (PlayerImpl) o;
    return Objects.equals(name, character.name);
  }

  /**
   * Overriden hasCode method.
   * @return integer
   */
  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  /**
   * The type of player.
   * @return the type of Player.
   */
  @Override
  public PlayerType getPlayerType() {
    return this.playerType;
  }

  /**
   * removing the weapons from the list.
   * @param weapon weapon.
   */
  @Override
  public void removeWeapon(Weapon weapon) {
    weapons.remove(weapon);
  }
}
