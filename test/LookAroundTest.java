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
import weapon.Weapon;
import world.World;
import world.WorldModel;

/**
 * A Junit test class to test Look Around.
 */

public class LookAroundTest {
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
    PlayerType pt2 = PlayerType.HUMAN;
    Room roomToEnter2 = rooms2.get(Integer.parseInt(roomNumber2) - 1);
    Player player2 = new PlayerImpl(playerName2, roomToEnter2, Integer.parseInt(weaponLimit2), pt2);
    world.addPlayer(player2);
    roomToEnter.addPlayer(player2);
  }

  /**
   * testing Look Around for Multiple Players in the Same Space.
   * @throws GameOverException GameOverException.
   * @throws IOException IOException.
   */
  @Test
  public void testingLookAroundMultiplePlayersSameSpace() throws GameOverException, IOException {
    StringBuilder sc = new StringBuilder();
    List<Room> neighbour = world.getTurn().getCurrentRoom().getNeighbor();
    Room currentRoom = world.getTurn().getCurrentRoom();
    sc.append("Current location").append("\n");
    sc.append("================").append("\n");
    sc.append(currentRoom.getName()).append("\n");
    sc.append("Room occupants").append("\n");
    sc.append("================").append("\n");
    if (currentRoom.hasPet()) {
      sc.append("The Pet").append("\n");
    }
    if (currentRoom.hasTargetCharacter()) {
      sc.append(world.getTargetCharacter()).append("\n");
    }
    if (currentRoom.getPlayers().size() > 0) {
      for (Player p : currentRoom.getPlayers()) {
        sc.append(p.playerDescription()).append("\n");
      }
    }
    sc.append("Items in the Room").append("\n");
    sc.append("================").append("\n");
    if (currentRoom.getWeapons().size() > 0) {
      for (Weapon p : currentRoom.getWeapons()) {
        sc.append(p.weaponInfo()).append("\n");
      }
    }
    sc.append("Neighbors of the room").append("\n");
    sc.append("================").append("\n");
    for (Room r : neighbour) {
      if (!r.hasPet()) {
        sc.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    String actual = sc.toString();
    String expected = "Current location\n"
            + "================\n"
            + "Armory\n"
            + "Room occupants\n"
            + "================\n"
            + "The Pet\n"
            + "[Target Character Name : Doctor Lucky, Current Health : 50]\n"
            + "Player{name='Atul', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Player{name='Computer', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Items in the Room\n"
            + "================\n"
            + "weaponName='Revolver', damageValue=3\n"
            + "Neighbors of the room\n"
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
            + "\n";

    assertEquals(expected, actual);
  }

  /**
   * Player 2 is in the room "Armory".
   * Billiard Room is not visible as the Pet is in that room.
   */
  @Test
  public void testingLookAroundSinglePlayerSameSpace() {
    StringBuilder sc = new StringBuilder();
    List<Room> neighbour = world.getTurn().getCurrentRoom().getNeighbor();
    Room currentRoom = world.getTurn().getCurrentRoom();
    sc.append("Current location").append("\n");
    sc.append("================").append("\n");
    sc.append(currentRoom.getName()).append("\n");
    sc.append("Room occupants").append("\n");
    sc.append("================").append("\n");
    if (currentRoom.hasPet()) {
      sc.append("The Pet").append("\n");
    }
    if (currentRoom.hasTargetCharacter()) {
      sc.append(world.getTargetCharacter()).append("\n");
    }
    if (currentRoom.getPlayers().size() > 0) {
      for (Player p : currentRoom.getPlayers()) {
        sc.append(p.playerDescription()).append("\n");
      }
    }
    sc.append("Items in the Room").append("\n");
    sc.append("================").append("\n");
    if (currentRoom.getWeapons().size() > 0) {
      for (Weapon p : currentRoom.getWeapons()) {
        sc.append(p.weaponInfo()).append("\n");
      }
    }
    sc.append("Neighbors of the room").append("\n");
    sc.append("================").append("\n");
    for (Room r : neighbour) {
      if (!r.hasPet()) {
        sc.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    String actual = sc.toString();
    String expected = "Current location\n"
            + "================\n"
            + "Armory\n"
            + "Room occupants\n"
            + "================\n"
            + "The Pet\n"
            + "[Target Character Name : Doctor Lucky, Current Health : 50]\n"
            + "Player{name='Atul', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Player{name='Computer', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Items in the Room\n"
            + "================\n"
            + "weaponName='Revolver', damageValue=3\n"
            + "Neighbors of the room\n"
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
            + "\n";

    assertEquals(expected, actual);
    world.movePlayer("Billiard Room");
    world.nextTurn();

    StringBuilder s = new StringBuilder();
    List<Room> neighbour1 = world.getTurn().getCurrentRoom().getNeighbor();
    Room currentRoom1 = world.getTurn().getCurrentRoom();
    s.append("Current location").append("\n");
    s.append("================").append("\n");
    s.append(currentRoom1.getName()).append("\n");
    s.append("Room occupants").append("\n");
    s.append("================").append("\n");
    if (currentRoom1.hasPet()) {
      s.append("The Pet").append("\n");
    }
    if (currentRoom1.hasTargetCharacter()) {
      s.append(world.getTargetCharacter()).append("\n");
    }
    if (currentRoom1.getPlayers().size() > 0) {
      for (Player p : currentRoom.getPlayers()) {
        s.append(p.playerDescription()).append("\n");
      }
    }
    s.append("Items in the Room").append("\n");
    s.append("================").append("\n");
    if (currentRoom1.getWeapons().size() > 0) {
      for (Weapon p : currentRoom1.getWeapons()) {
        s.append(p.weaponInfo()).append("\n");
      }
    }
    s.append("Neighbors of the room").append("\n");
    s.append("================").append("\n");
    for (Room r : neighbour1) {
      if (!r.hasPet()) {
        s.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    String actual1 = s.toString();
    String expected1 = "Current location\n"
            + "================\n"
            + "Armory\n"
            + "Room occupants\n"
            + "================\n"
            + "Player{name='Computer', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Items in the Room\n"
            + "================\n"
            + "weaponName='Revolver', damageValue=3\n"
            + "Neighbors of the room\n"
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
            + "\n";
    assertEquals(expected1, actual1);
  }

  /**
   * Player 2 is in the room "Armory".
   * Billiard Room is not visible as the Pet is in that room.
   */
  @Test
  public void testingLookAroundNoNeighbor() {
    StringBuilder sc = new StringBuilder();
    List<Room> neighbour = world.getTurn().getCurrentRoom().getNeighbor();
    Room currentRoom = world.getTurn().getCurrentRoom();
    sc.append("Current location").append("\n");
    sc.append("================").append("\n");
    sc.append(currentRoom.getName()).append("\n");
    sc.append("Room occupants").append("\n");
    sc.append("================").append("\n");
    if (currentRoom.hasPet()) {
      sc.append("The Pet").append("\n");
    }
    if (currentRoom.hasTargetCharacter()) {
      sc.append(world.getTargetCharacter()).append("\n");
    }
    if (currentRoom.getPlayers().size() > 0) {
      for (Player p : currentRoom.getPlayers()) {
        sc.append(p.playerDescription()).append("\n");
      }
    }
    sc.append("Items in the Room").append("\n");
    sc.append("================").append("\n");
    if (currentRoom.getWeapons().size() > 0) {
      for (Weapon p : currentRoom.getWeapons()) {
        sc.append(p.weaponInfo()).append("\n");
      }
    }
    sc.append("Neighbors of the room").append("\n");
    sc.append("================").append("\n");
    for (Room r : neighbour) {
      if (!r.hasPet()) {
        sc.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    String actual = sc.toString();
    String expected = "Current location\n"
            + "================\n"
            + "Armory\n"
            + "Room occupants\n"
            + "================\n"
            + "The Pet\n"
            + "[Target Character Name : Doctor Lucky, Current Health : 50]\n"
            + "Player{name='Atul', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Player{name='Computer', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Items in the Room\n"
            + "================\n"
            + "weaponName='Revolver', damageValue=3\n"
            + "Neighbors of the room\n"
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
            + "\n";

    assertEquals(expected, actual);
    world.movePlayer("Billiard Room");
    world.nextTurn();

    StringBuilder s = new StringBuilder();
    List<Room> neighbour1 = world.getTurn().getCurrentRoom().getNeighbor();
    Room currentRoom1 = world.getTurn().getCurrentRoom();
    s.append("Current location").append("\n");
    s.append("================").append("\n");
    s.append(currentRoom1.getName()).append("\n");
    s.append("Room occupants").append("\n");
    s.append("================").append("\n");
    if (currentRoom1.hasPet()) {
      s.append("The Pet").append("\n");
    }
    if (currentRoom1.hasTargetCharacter()) {
      s.append(world.getTargetCharacter()).append("\n");
    }
    if (currentRoom1.getPlayers().size() > 0) {
      for (Player p : currentRoom.getPlayers()) {
        s.append(p.playerDescription()).append("\n");
      }
    }
    s.append("Items in the Room").append("\n");
    s.append("================").append("\n");
    if (currentRoom1.getWeapons().size() > 0) {
      for (Weapon p : currentRoom1.getWeapons()) {
        s.append(p.weaponInfo()).append("\n");
      }
    }
    s.append("Neighbors of the room").append("\n");
    s.append("================").append("\n");
    for (Room r : neighbour1) {
      if (!r.hasPet()) {
        s.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    String actual1 = s.toString();
    String expected1 = "Current location\n"
            + "================\n"
            + "Armory\n"
            + "Room occupants\n"
            + "================\n"
            + "Player{name='Computer', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Items in the Room\n"
            + "================\n"
            + "weaponName='Revolver', damageValue=3\n"
            + "Neighbors of the room\n"
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
            + "\n";
    assertEquals(expected1, actual1);
  }

  /**
   * Player 2 is in the room "Armory".
   * Player 1 is in "Dining Hall".
   * Billiard Room is not visible as the Pet is in that room.
   */
  @Test
  public void testingLookAroundWithNeighbor() {
    StringBuilder sc = new StringBuilder();
    List<Room> neighbour = world.getTurn().getCurrentRoom().getNeighbor();
    Room currentRoom = world.getTurn().getCurrentRoom();
    sc.append("Current location").append("\n");
    sc.append("================").append("\n");
    sc.append(currentRoom.getName()).append("\n");
    sc.append("Room occupants").append("\n");
    sc.append("================").append("\n");
    if (currentRoom.hasPet()) {
      sc.append("The Pet").append("\n");
    }
    if (currentRoom.hasTargetCharacter()) {
      sc.append(world.getTargetCharacter()).append("\n");
    }
    if (currentRoom.getPlayers().size() > 0) {
      for (Player p : currentRoom.getPlayers()) {
        sc.append(p.playerDescription()).append("\n");
      }
    }
    sc.append("Items in the Room").append("\n");
    sc.append("================").append("\n");
    if (currentRoom.getWeapons().size() > 0) {
      for (Weapon p : currentRoom.getWeapons()) {
        sc.append(p.weaponInfo()).append("\n");
      }
    }
    sc.append("Neighbors of the room").append("\n");
    sc.append("================").append("\n");
    for (Room r : neighbour) {
      if (!r.hasPet()) {
        sc.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    String actual = sc.toString();
    String expected = "Current location\n"
            + "================\n"
            + "Armory\n"
            + "Room occupants\n"
            + "================\n"
            + "The Pet\n"
            + "[Target Character Name : Doctor Lucky, Current Health : 50]\n"
            + "Player{name='Atul', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Player{name='Computer', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Items in the Room\n"
            + "================\n"
            + "weaponName='Revolver', damageValue=3\n"
            + "Neighbors of the room\n"
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
            + "\n";

    assertEquals(expected, actual);
    world.movePlayer("Dining Hall");
    world.nextTurn();

    StringBuilder s = new StringBuilder();
    List<Room> neighbour1 = world.getTurn().getCurrentRoom().getNeighbor();
    Room currentRoom1 = world.getTurn().getCurrentRoom();
    s.append("Current location").append("\n");
    s.append("================").append("\n");
    s.append(currentRoom1.getName()).append("\n");
    s.append("Room occupants").append("\n");
    s.append("================").append("\n");
    if (currentRoom1.hasPet()) {
      s.append("The Pet").append("\n");
    }
    if (currentRoom1.hasTargetCharacter()) {
      s.append(world.getTargetCharacter()).append("\n");
    }
    if (currentRoom1.getPlayers().size() > 0) {
      for (Player p : currentRoom.getPlayers()) {
        s.append(p.playerDescription()).append("\n");
      }
    }
    s.append("Items in the Room").append("\n");
    s.append("================").append("\n");
    if (currentRoom1.getWeapons().size() > 0) {
      for (Weapon p : currentRoom1.getWeapons()) {
        s.append(p.weaponInfo()).append("\n");
      }
    }
    s.append("Neighbors of the room").append("\n");
    s.append("================").append("\n");
    for (Room r : neighbour1) {
      if (!r.hasPet()) {
        s.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    String actual1 = s.toString();
    String expected1 = "Current location\n"
            + "================\n"
            + "Armory\n"
            + "Room occupants\n"
            + "================\n"
            + "Player{name='Computer', currentRoom=Armory, weapons=[], player type=HUMAN}\n"
            + "Items in the Room\n"
            + "================\n"
            + "weaponName='Revolver', damageValue=3\n"
            + "Neighbors of the room\n"
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
            + "Atul\n"
            + "\n"
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
            + "\n";
    assertEquals(expected1, actual1);
  }
}