package weapon;

/**
 * Interface for Weapon. It stores info about a weapon.
 */
public interface Weapon {

  /**
   * return room index.
   * @return room index
   */
  int getRoomIndex();

  /**
   * the damage value of the weapon.
   * @return integer
   */
  int getDamageValue();

  /**
   * returns name of the weapon.
   * @return String
   */
  String getWeaponName();

  /**
   * weapon information.
   * @return String.
   */
  String weaponInfo();
}
