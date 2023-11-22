package turncommand;

import java.io.IOException;
import java.util.Scanner;
import player.GameOverException;
import world.World;

/**
 * Exit the player after a turn.
 */
public class Exit implements TurnCommands {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param scan  the source to read from
   * @param out the target to print to
   */
  public Exit(Appendable out, Scanner scan) {
    if (scan == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = scan;
  }

  /**
   * Overridden method to exit turn for a Human player.
   * @param m the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException IOException.
   */
  @Override
  public void go(World m) throws IllegalArgumentException, IOException, GameOverException {
    out.append("Exiting.....\n");
  }

  /**
   * Overridden method to exit turn for a Computer player.
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException IOException.
   */
  @Override
  public void goAutomatic(World world) throws IllegalArgumentException,
          IOException, GameOverException {
    world.nextT();
  }
}
