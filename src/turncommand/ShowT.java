package turncommand;

import java.io.IOException;
import java.util.Scanner;
import player.GameOverException;
import world.World;

/**
 * Look around during a turn.
 */
public class ShowT implements TurnCommands {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param scan  the source to read from
   * @param out the target to print to
   */
  public ShowT(Appendable out, Scanner scan) {
    if (scan == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = scan;
  }

  /**
   * Overridden method to see target character and the pet info.
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException IOException.
   */
  @Override
  public void go(World world) throws IllegalArgumentException, IOException,
          GameOverException {
    out.append("Target Character is in the room : ").append(world
            .getTargetCharacterLocation()).append("\n");
    out.append("Target Character information : ").append(world
            .getTargetCharacter()).append("\n");
    out.append("Target Character's pet is in the room : ").append(world
            .getTargetCharacterPetLocation()).append("\n");
    out.append("Target Character information : ").append(world
            .getTargetCharacterPet()).append("\n");
  }

  /**
   * Overridden method to see target character and the pet info.
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException IOException.
   */
  @Override
  public void goAutomatic(World world) throws IllegalArgumentException,
          IOException, GameOverException {
    out.append("Target Character is in the room : ").append(world
            .getTargetCharacterLocation()).append("\n");
    out.append("Target Character information : ").append(world
            .getTargetCharacter()).append("\n");
    out.append("Target Character's pet is in the room : ").append(world
            .getTargetCharacterPetLocation()).append("\n");
    out.append("Target Character information : ").append(world
            .getTargetCharacterPet()).append("\n");
  }
}
