package gamesetup;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import player.Player;
import player.PlayerImpl;
import player.PlayerType;
import room.Room;
import world.World;

/**
 * Implementation of GameSetup interface.
 */
public class CreatePlayer implements GameSetup {
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param scan  the source to read from
   * @param out the target to print to
   */
  public CreatePlayer(Appendable out, Scanner scan) {
    if (scan == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = scan;
  }

  @Override
  public void setup(World world) throws IllegalArgumentException, IOException {
    if (world == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    out.append("Player Type :").append("\n");
    out.append("1 : Human Player").append("\n");
    out.append("2 : Computer Player :").append("\n");
    String p = scan.nextLine();
    PlayerType pt = null;
    if (("1").equals(p)) {
      pt = PlayerType.HUMAN;
    } else {
      pt = PlayerType.COMPUTER;
    }
    out.append("Add character Name:").append("\n");
    String playerName = "";
    if (scan.hasNextLine()) {
      playerName = scan.nextLine();
      while (world.playerNameExists(playerName)) {
        out.append("Player name already exists. Try Another name : ").append("\n");
        playerName = scan.nextLine();
      }
    }
    out.append("Add player to the room: ").append("\n");
    List<Room> rooms = world.getRooms();
    int i = 1;
    for (Room room : rooms) {
      out.append(String.valueOf(i)).append(" : ").append(room.getName()).append("\n");
      i++;
    }
    String roomNumber = "";
    if (scan.hasNextLine()) {
      roomNumber = scan.nextLine();
    }
    out.append("Enter maximum weapon limit:\n");
    String weaponLimit = "";
    if (scan.hasNextLine()) {
      weaponLimit = scan.nextLine();
    }
    Room roomToEnter = rooms.get(Integer.parseInt(roomNumber) - 1);
    Player player = new PlayerImpl(playerName, roomToEnter, Integer.parseInt(weaponLimit), pt);
    world.addPlayer(player);
    roomToEnter.addPlayer(player);
    out.append(world.displaySingleRoomInfomation(roomToEnter.getName(), world)).append("\n");
    out.append(world.toString()).append("\n");
  }
}
