package room;

import java.util.Objects;
import world.WorldDescription;

/**
 * Coordinate class defines rows and columns for the rooms.
 */
public class Coordinate {
  private final int row;
  private final int column;

  /**
   * constructor for Coordinate class.
   * @param row information in int.
   * @param column information in int.
   * @param worldDescription type WorldDescription.
   * @throws IllegalArgumentException for invalid rows and columns.
   */
  public Coordinate(int row, int column, WorldDescription worldDescription)
          throws IllegalArgumentException {
    if (row > worldDescription.getRow() || column > worldDescription.getColumn()) {
      throw new IllegalArgumentException("columns can't be greater than "
             + "the maximum allowed worldDescription");
    }
    this.row = row;
    this.column = column;
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
   * overridden toString method.
   * @return String.
   */
  @Override
  public String toString() {
    return "Coordinate{"
            + "row=" + row
            + ", column=" + column
            + '}';
  }

  /**
   * overridden equals method.
   * @param o Coordinate object.
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
    Coordinate that = (Coordinate) o;
    return row == that.row && column == that.column;
  }

  /**
   * overridden hashcode method.
   * @return int.
   */
  @Override
  public int hashCode() {
    return Objects.hash(row, column);
  }
}
