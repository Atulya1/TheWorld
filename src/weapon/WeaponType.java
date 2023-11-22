package weapon;

import java.util.Objects;

/**
 * Implementation of Weapon Interface. It stores info about a weapon.
 */
public class WeaponType implements Weapon {

  private int roomIndex;
  private final String weaponName;
  private final int damageValue;

  /**
   * Constructor for WeaponType class.
   * @param roomIndex index of the room.
   * @param damageValue Damage value.
   * @param weaponName Name of the weapon.
   */
  public WeaponType(int roomIndex, int damageValue, String weaponName) {
    this.roomIndex = roomIndex;
    this.weaponName = weaponName;
    this.damageValue = damageValue;
  }

  /**
   * return room index.
   * @return room index.
   */
  @Override
  public int getRoomIndex() {
    return roomIndex;
  }

  /**
   * the damage value of the weapon.
   * @return integer
   */
  @Override
  public int getDamageValue() {
    return damageValue;
  }

  /**
   * returns name of the weapon.
   * @return String
   */
  @Override
  public String getWeaponName() {
    return weaponName;
  }

  /**
   * Overridden toString() Method.
   * @return String
   */
  @Override
  public String toString() {
    return "WeaponType{"
            + "roomIndex=" + roomIndex
            + ", weaponName='" + weaponName + '\''
            + ", damageValue=" + damageValue
            + '}';
  }

  /**
   * Weapon Info.
   * @return String
   */
  @Override
  public String weaponInfo() {
    return "weaponName='"
            + weaponName + '\''
            + ", damageValue="
            + damageValue;
  }

  /**
   * overridden equals method.
   * @param o object of weapon type.
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
    WeaponType that = (WeaponType) o;
    return roomIndex == that.roomIndex && damageValue == that.damageValue
            && Objects.equals(weaponName, that.weaponName);
  }

  /**
   * overridden hashcode method.
   * @return int.
   */
  @Override
  public int hashCode() {
    return Objects.hash(roomIndex, weaponName, damageValue);
  }
}
