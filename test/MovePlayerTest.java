import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import player.GameOverException;
import player.Player;
import player.PlayerImpl;
import player.PlayerType;
import room.Room;
import world.World;
import world.WorldModel;

/**
 * Test class to test moving Player in the world.
 */
public class MovePlayerTest {
  private World world;
  private Appendable out;
  private Scanner scan;

  /**
   * Set method to initialize WorldModel object for running the tests.
   */
  @Before
  public void setUpWorld() {
    String inputFile = "res/mansion.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      world = new WorldModel();
      world = world.parseFile(reader);
      assertNotNull(world);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    Readable input = new InputStreamReader(System.in);
    scan = new Scanner(input);
    out = System.out;
    String playerName = "Atul";
    List<Room> rooms = world.getRooms();
    String roomNumber = "1";
    String weaponLimit = "2";
    PlayerType pt = PlayerType.HUMAN;
    Room roomToEnter = rooms.get(Integer.parseInt(roomNumber) - 1);
    Player player1 = new PlayerImpl(playerName, roomToEnter, Integer.parseInt(weaponLimit), pt);
    world.addPlayer(player1);
    roomToEnter.addPlayer(player1);
    String playerName2 = "Computer";
    List<Room> rooms2 = world.getRooms();
    String roomNumber2 = "1";
    String weaponLimit2 = "2";
    PlayerType pt2 = PlayerType.COMPUTER;
    Room roomToEnter2 = rooms2.get(Integer.parseInt(roomNumber2) - 1);
    Player player2 = new PlayerImpl(playerName2, roomToEnter2, Integer.parseInt(weaponLimit2), pt2);
    world.addPlayer(player2);
    roomToEnter.addPlayer(player2);
  }

  /**
   * Test to move player in the world.
   * @throws GameOverException GameOverException.
   * @throws IOException IOException.
   */
  @Test
  public void moveTest() throws GameOverException, IOException {
    StringBuilder sc = new StringBuilder();
    sc.append("Current Location : \n");
    sc.append("================").append("\n");
    sc.append(world.getTurn().getCurrentRoom().getName()).append("\n");
    List<Room> neighbour = world.getTurn().getCurrentRoom().getNeighbor();
    sc.append("Neighbors of the room").append("\n");
    sc.append("================").append("\n");
    for (Room r : neighbour) {
      if (!r.hasPet()) {
        sc.append(r.getName()).append("\n");
        sc.append("================").append("\n");
        sc.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    sc.append("Choose room to move :\n");
    sc.append("================").append("\n");
    String roomToMove = "Billiard Room";
    world.movePlayer(roomToMove);
    sc.append("Player moved to the desired destination").append("\n");

    String actual = sc.toString();
    String expected = "Current Location : \n"
            + "================\n"
            + "Armory\n"
            + "Neighbors of the room\n"
            + "================\n"
            + "Billiard Room\n"
            + "================\n"
            + "Name : Billiard Room\n"
            + "Neighbor ---\n"
            + "Armory\n"
            + "Dining Hall\n"
            + "Trophy Room\n"
            + "Weapons --- \n"
            + "Billiard Cue\n"
            + "Damage value of Billiard Cue is 2\n"
            + "Players --- \n"
            + "\n"
            + "Dining Hall\n"
            + "================\n"
            + "Name : Dining Hall\n"
            + "Neighbor ---\n"
            + "Armory\n"
            + "Billiard Room\n"
            + "Drawing Room\n"
            + "Kitchen\n"
            + "Parlor\n"
            + "Tennessee Room\n"
            + "Trophy Room\n"
            + "Wine Cellar\n"
            + "Weapons --- \n"
            + "Dining Hall has no weapons.\n"
            + "Players --- \n"
            + "\n"
            + "Drawing Room\n"
            + "================\n"
            + "Name : Drawing Room\n"
            + "Neighbor ---\n"
            + "Armory\n"
            + "Dining Hall\n"
            + "Foyer\n"
            + "Wine Cellar\n"
            + "Weapons --- \n"
            + "Letter Opener\n"
            + "Damage value of Letter Opener is 2\n"
            + "Players --- \n"
            + "\n"
            + "Choose room to move :\n"
            + "================\n"
            + "Player moved to the desired destination\n";

    assertEquals(expected, actual);
  }
}
