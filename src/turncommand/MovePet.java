package turncommand;

import java.io.IOException;
import java.util.Scanner;
import player.GameOverException;
import room.Room;
import world.World;

/**
 * Pick a weapon during turn.
 */
public class MovePet implements TurnCommands {
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param scan  the source to read from
   * @param out the target to print to
   */
  public MovePet(Appendable out, Scanner scan) {
    if (scan == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = scan;
  }

  /**
   * Overridden method to move for a Human player.
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException IOException.
   */
  @Override
  public void go(World world) throws IllegalArgumentException, IOException {
    Room turn = world.getTurn().getCurrentRoom();
    if (turn.hasPet()) {
      out.append("Pet is in the same space. Do you want to move the pet?\n");
      out.append("Yes\n");
      out.append("No\n");
      if (scan.nextLine().equals("Yes")) {
        int i = 0;
        out.append("Select room number to move the pet :\n");
        for (Room room : world.getRooms()) {
          out.append(Integer.toString(i)).append(". ").append(room.getName()).append("\n");
          i++;
        }
        String roomNumber = scan.nextLine();
        if (Integer.parseInt(roomNumber) > world.getRooms().size()
                || Integer.parseInt(roomNumber) < 0) {
          throw new IllegalArgumentException("Wrong input\n");
        } else {
          world.movePet(Integer.parseInt(roomNumber));
          world.hasPetMoved(true);
          out.append("Pet has been moved.\n");
        }
      }
    } else {
      out.append("Pet is not in the same space. You can't move the pet.\n");
    }
  }

  /**
   * Overridden method to move for a Computer player.
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException IOException.
   */
  @Override
  public void goAutomatic(World world) throws IllegalArgumentException,
          IOException, GameOverException {
  }
}
