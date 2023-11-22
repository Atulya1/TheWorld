package turncommand;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import player.GameOverException;
import player.Player;
import room.Room;
import weapon.Weapon;
import world.World;

/**
 * Look around during a turn.
 */
public class LookAround implements TurnCommands {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param scan  the source to read from
   * @param out the target to print to
   */
  public LookAround(Appendable out, Scanner scan) {
    if (scan == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = scan;
  }

  /**
   * Overridden method to Look Around for Human player.
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException IOException.
   */
  @Override
  public void go(World world) throws IllegalArgumentException, IOException, GameOverException {
    List<Room> neighbour = world.getTurn().getCurrentRoom().getNeighbor();
    Room currentRoom = world.getTurn().getCurrentRoom();
    out.append("Current location").append("\n");
    out.append("================").append("\n");
    out.append(currentRoom.getName()).append("\n");
    out.append("Room occupants").append("\n");
    out.append("================").append("\n");
    if (currentRoom.hasPet()) {
      out.append("The Pet").append("\n");
    }
    if (currentRoom.hasTargetCharacter()) {
      out.append(world.getTargetCharacter()).append("\n");
    }
    if (currentRoom.getPlayers().size() > 0) {
      for (Player p : currentRoom.getPlayers()) {
        out.append(p.playerDescription()).append("\n");
      }
    }
    out.append("Items in the Room").append("\n");
    out.append("================").append("\n");
    if (currentRoom.getWeapons().size() > 0) {
      for (Weapon p : currentRoom.getWeapons()) {
        out.append(p.weaponInfo()).append("\n");
      }
    }
    out.append("Neighbors of the room").append("\n");
    out.append("================").append("\n");
    for (Room r : neighbour) {
      if (!r.hasPet()) {
        out.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
  }

  /**
   * Overridden method to Look Around for a computer player.
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException IOException.
   */
  @Override
  public void goAutomatic(World world) throws IllegalArgumentException,
          IOException, GameOverException {
    List<Room> neighbour = world.getTurn().getCurrentRoom().getNeighbor();

    for (Room r : neighbour) {
      out.append(r.displaySingleRoomInformation(r, world)).append("\n");
    }
  }
}
