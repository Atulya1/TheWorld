package world;

import java.util.Objects;

/**
 * WorldDescription has the description of the room.
 * It has rows and columns and the name of the world.
 */
public class WorldDescription {
  private final int row;
  private final int column;
  private final String worldName;

  /**
   * constructor for WorldDescription class.
   *
   * @param row       information in int.
   * @param column    information in int.
   * @param worldName name of the world.
   * @throws IllegalArgumentException if rows and columns exceed 100.
   */
  public WorldDescription(int row, int column, String worldName) throws IllegalArgumentException {
    if (row > 100 || column > 100) {
      throw new IllegalArgumentException("rows and columns can't be greater than 100");
    }
    this.row = row;
    this.column = column;
    this.worldName = worldName;
  }

  /**
   * get the row of the grid.
   *
   * @return row of the grid.
   */
  public int getRow() {
    return row;
  }

  /**
   * get the column of the grid.
   *
   * @return column of the grid.
   */
  public int getColumn() {
    return column;
  }

  /**
   * get the name of the world.
   *
   * @return name of the world.
   */
  public String getWorldName() {
    return worldName;
  }

  /**
   * overridden toString() method.
   * @return String.
   */
  @Override
  public String toString() {
    return "WorldDescription{"
            + "row=" + row
            + ", column=" + column
            + ", worldName='" + worldName + '\''
            + '}';
  }

  /**
   * overridden equals method.
   * @param o WorldDescription object.
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
    WorldDescription that = (WorldDescription) o;
    return row == that.row && column == that.column
            && Objects.equals(worldName, that.worldName);
  }

  /**
   * overridden hashcode method.
   * @return int.
   */
  @Override
  public int hashCode() {
    return Objects.hash(row, column, worldName);
  }
}
