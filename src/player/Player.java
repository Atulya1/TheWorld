package player;

import java.util.List;
import room.Room;
import weapon.Weapon;

/**
 * Interface for player type.
 */
public interface Player {

  /**
   * get name of player.
   * @return name.
   */
  String getName();

  /**
   * set current room.
   * @param room to be added.
   */
  void setCurrentRoom(Room room);

  /**
   * get current room.
   * @return returns Room object.
   */
  Room getCurrentRoom();

  /**
   * add weapon.
   * @param weapon weapon.
   */
  void addWeapon(Weapon weapon);

  /**
   * get all weapons.
   * @return list of weapons.
   */
  List<Weapon> getWeapons();

  /**
   * get weapons limit.
   * @return int.
   */
  int getWeaponLimit();

  /**
   * get player description.
   * @return string.
   */
  String playerDescription();

  /**
   * reduce weapon count.
   */
  void reduceWeaponCount();

  /**
   * get current weapon holding limit.
   * @return int.
   */
  int getCurrentWeaponLimit();

  /**
   * returns player type.
   * @return player type.
   */
  PlayerType getPlayerType();

  /**
   * removing the weapons from the list.
   * @param weapon weapon.
   */
  void removeWeapon(Weapon weapon);
}
