package turncommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import player.GameOverException;
import room.Room;
import world.World;

/**
 * Move during a turn.
 */
public class Move implements TurnCommands {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param scan  the source to read from
   * @param out the target to print to
   */
  public Move(Appendable out, Scanner scan) {
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
  public void go(World world) throws IllegalArgumentException, IOException,
          GameOverException {
    out.append("Current Location : \n");
    out.append("================").append("\n");
    out.append(world.getTurn().getCurrentRoom().getName()).append("\n");
    List<Room> neighbour = world.getTurn().getCurrentRoom().getNeighbor();
    out.append("Neighbors of the room").append("\n");
    out.append("================").append("\n");
    for (Room r : neighbour) {
      if (!r.hasPet()) {
        out.append(r.getName()).append("\n");
        out.append("================").append("\n");
        out.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    out.append("Choose room to move :\n");
    out.append("================").append("\n");
    String roomToMove = scan.nextLine();
    world.movePlayer(roomToMove);
    out.append("Player moved to the desired destination").append("\n");
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
    out.append("Current location").append("\n");
    out.append("================").append("\n");
    out.append(world.getTurn().getCurrentRoom().getName()).append("\n");
    List<Room> neighbour = world.getTurn().getCurrentRoom().getNeighbor();
    out.append("Neighbors of the room").append("\n");
    out.append("================").append("\n");
    List<Room> randomRoom = new ArrayList<>();
    for (Room r : neighbour) {
      if (!r.hasPet()) {
        randomRoom.add(r);
        out.append(r.getName()).append("\n");
        out.append("================").append("\n");
        out.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    out.append("Choose room to move :\n");
    out.append("================").append("\n");
    Room roomToMove = randomRoom.get(new Random().nextInt(randomRoom.size()));
    out.append(roomToMove.getName()).append(" selected\n");
    world.movePlayer(roomToMove.getName());
    world.nextT(); //MS4
  }
}
