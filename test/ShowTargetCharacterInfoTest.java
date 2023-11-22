import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
 * Test class to test target character info.
 */
public class ShowTargetCharacterInfoTest {

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
   * target Character Information Test.
   * @throws GameOverException GameOverException.
   * @throws IOException IOException.
   */
  @Test
  public void targetCharacterInfoTest() throws GameOverException, IOException {
    StringBuilder sc = new StringBuilder();
    sc.append("Target Character is in the room : ").append(world
            .getTargetCharacterLocation()).append("\n");
    sc.append("Target Character information : ").append(world
            .getTargetCharacter()).append("\n");
    sc.append("Target Character's pet is in the room : ").append(world
            .getTargetCharacterPetLocation()).append("\n");
    sc.append("Target Character information : ").append(world
            .getTargetCharacterPet()).append("\n");
    String expected = "Target Character is in the room : Armory\n"
            + "Target Character information : [Target Character Name : "
            + "Doctor Lucky, Current Health : 50]\n"
            + "Target Character's pet is in the room : Armory\n"
            + "Target Character information : {Pet name is :Fortune the Cat}\n";
    String actual = sc.toString();
    assertEquals(actual, expected);
  }
}
