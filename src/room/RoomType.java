package room;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import player.Player;
import weapon.Weapon;
import world.World;


/**
 * RoomType class implements the Room interface. It defines the specifics
 * of the spaces in the "The world"
 */
public class RoomType implements Room {

  private final Coordinate upperLeftCorner;
  private final Coordinate lowerRightCorner;
  private final String roomName;
  private List<Room> neighbors;

  private List<Weapon> weapons;

  private List<Player> players;

  private boolean hasPet = false;

  private boolean hasTargetCharacter = false;

  private final int index;

  /**
   * constructor for RoomType class.
   * @param upperLeftCorner has the coordinates of the room's upper left corner.
   * @param lowerRightCorner has the coordinates of the room's lower right corner.
   * @param roomName has the name of the room.
   */
  public RoomType(Coordinate upperLeftCorner, Coordinate lowerRightCorner,
                  String roomName, int index) {
    this.upperLeftCorner = upperLeftCorner;
    this.lowerRightCorner = lowerRightCorner;
    this.roomName = roomName;
    this.neighbors = new ArrayList<>();
    this.weapons = new ArrayList<>();
    this.players = new ArrayList<>();
    this.index = index;
  }

  /**
   * get the getUpperLeftCorner coordinates of the room.
   *
   * @return getUpperLeftCorner coordinates.
   */
  @Override
  public Coordinate getUpperLeftCorner() {
    return this.upperLeftCorner;
  }

  /**
   * get the getLowerRightCorner coordinates of the room.
   *
   * @return getLowerRightCorner coordinates.
   */
  @Override
  public Coordinate getLowerRightCorner() {
    return this.lowerRightCorner;
  }

  /**
   * store neighbors.
   * @param rooms sets the list of rooms that are neighbours.
   */
  @Override
  public void addNeighbor(List<Room> rooms) {
    this.neighbors = rooms;
  }

  /**
   * returns list of neighbors.
   * @return neighbors of a particular room.
   */
  @Override
  public List<Room> getNeighbor() {
    return neighbors;
  }

  /**
   * returns name of the room.
   * @return the name of the room.
   */
  @Override
  public String getName() {
    return this.roomName;
  }

  /**
   * return list of weapons.
   * @return List of Weapons in the room.
   */
  @Override
  public List<Weapon> getWeapons() {
    return this.weapons;
  }

  /**
   * saves weapons in a particular room.
   * @param weapons in a particular room.
   */
  @Override
  public void addWeapons(Weapon weapons) {
    this.weapons.add(weapons);
  }

  /**
   * Displays the room information along with all the weapons in them.
   * @return return a string.
   */
  @Override
  public String displaySingleRoomInformation(Room room, World w) {

    String info = "";
    info = info.concat("Name : ").concat(room.getName()).concat("\n");
    info = info.concat("Neighbor ---\n");
    List<Room> neighbours = room.getNeighbor();
    if (neighbours.size() > 0) {
      for (Room neighbour : neighbours) {
        info = info.concat(neighbour.getName()).concat("\n");
      }
    } else {
      info = info.concat(room.getName()).concat(" has no neigbors.\n");
    }
    info = info.concat("Weapons --- \n");
    List<Weapon> weapons = room.getWeapons();
    if (weapons.size() > 0) {
      for (Weapon weapon : weapons) {
        info = info.concat(weapon.getWeaponName()).concat("\n");
        info = info.concat("Damage value of ").concat(weapon.getWeaponName())
                .concat(" is ").concat("" + weapon.getDamageValue()).concat("\n");
      }
    } else {
      info = info.concat(room.getName()).concat(" has no weapons.\n");
    }
    info = info.concat("Players --- \n");
    if (players.size() > 0) {
      for (Player p : players) {
        info = info.concat(p.getName()).concat("\n");
      }
    }
    if (hasTargetCharacter) {
      info = info.concat(w.getTargetCharacter());
    }
    if (hasPet) {
      info = info.concat(w.getTargetCharacterPet());
    }
    return info;
  }

  /**
   * Overridden toString() Method.
   * @return String
   */
  @Override
  public String toString() {
    return "RoomType{"
            + "upperLeftCorner=" + upperLeftCorner
            + ", lowerRightCorner=" + lowerRightCorner
            + ", roomName='" + roomName
            + '\'' + '}';
  }

  /**
   * overridden equals method.
   * @param o RoomType object.
   * @return boolean.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoomType roomType = (RoomType) o;
    return Objects.equals(upperLeftCorner, roomType.upperLeftCorner)
            && Objects.equals(lowerRightCorner, roomType.lowerRightCorner)
            && Objects.equals(roomName, roomType.roomName)
            && Objects.equals(neighbors, roomType.neighbors)
            && Objects.equals(weapons, roomType.weapons);
  }

  /**
   * overridden hashcode method.
   * @return int.
   */
  @Override
  public int hashCode() {
    return Objects.hash(upperLeftCorner, lowerRightCorner, roomName, neighbors, weapons);
  }

  /**
   * add player to the room.
   * @param player player.
   */
  @Override
  public void addPlayer(Player player) {
    this.players.add(player);
  }

  /**
   * remove weapon from the room.
   * @param weapon weapon.
   */
  public void removeWeapon(String weapon) {
    Weapon ww = null;
    for (Weapon w : weapons) {
      if (w.getWeaponName().equals(weapon)) {
        ww = w;
      }
    }
    weapons.remove(ww);
  }

  /**
   * remove player from the room.
   * @param player player.
   */
  @Override
  public void removePlayer(String player) {
    Player ww = null;
    for (Player p : players) {
      if (p.getName().equals(player)) {
        ww = p;
      }
    }
    players.remove(ww);
  }

  /**
   * has pet or not.
   * @return boolean.
   */
  @Override
  public boolean hasPet() {
    return hasPet;
  }

  /**
   * has Target Character.
   * @return boolean.
   */
  @Override
  public boolean hasTargetCharacter() {
    return hasTargetCharacter;
  }

  /**
   * update Target Character's Presence in the room.
   * @param t boolean.
   */
  @Override
  public void updateTargetCharacterPresence(boolean t) {
    hasTargetCharacter = t;
  }

  /**
   * update Pet Presence in the room.
   * @param t boolean.
   */
  @Override
  public void updatePetPresence(boolean t) {
    hasPet = t;
  }

  /**
   * get index of the room.
   * @return int.
   */
  @Override
  public int getIndex() {
    return index;
  }

  /**
   * get list of players in the room.
   * @return List of players.
   */
  @Override
  public List<Player> getPlayers() {
    return players;
  }
}
