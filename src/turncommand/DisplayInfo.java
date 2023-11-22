package turncommand;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import player.GameOverException;
import player.Player;
import room.Room;
import world.World;

/**
 * Displays information about the player during turn.
 */
public class DisplayInfo implements TurnCommands {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param scan  the source to read from
   * @param out the target to print to
   */
  public DisplayInfo(Appendable out, Scanner scan) {
    if (scan == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = scan;
  }

  /**
   * Overridden method to display information about a Human player.
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException IOException.
   */
  @Override
  public void go(World world) throws IllegalArgumentException, IOException {
    out.append("Select Player : \n");
    List<Player> players = world.getPlayer();
    Player turn = world.getTurn();
    List<Room> neighbors = turn.getCurrentRoom().getNeighbor();
    for (Room r : neighbors) {
      for (Player p : r.getPlayers()) {
        out.append(p.getName()).append("\n");
      }
    }
    String player = scan.nextLine();
    for (Player p : players) {
      if (p.getName().equals(player)) {
        out.append(p.playerDescription()).append("\n");
      }
    }
  }

  /**
   * Overridden method to display information about a Computer player.
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException IOException.
   * @throws GameOverException GameOverException.
   */
  @Override
  public void goAutomatic(World world) throws IllegalArgumentException, IOException,
          GameOverException {
    out.append("Select Player from the neighborhood : \n");
    List<Player> players = world.getPlayer();
    for (Player p : players) {
      out.append(p.getName()).append("\n");
    }
    Player player = players.get(new Random().nextInt(players.size()));
    for (Player p : players) {
      if (p.getName().equals(player.getName())) {
        out.append(p.playerDescription()).append("\n");
      }
    }
  }
}
