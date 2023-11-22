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
import player.Player;
import player.PlayerImpl;
import player.PlayerType;
import room.Room;
import weapon.Weapon;
import world.World;
import world.WorldModel;

/**
 * Test class to test moving pet in the world.
 */
public class MovePetTest {
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
   * pet Start Location Test.
   */
  @Test
  public void petStartLocationTest() {
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

  /**
   * testing pet movement by player.
   * Pet is moved to Foyer[5] by the player.
   */
  @Test
  public void movePlayerPetTest() throws IOException {
    StringBuilder sc = new StringBuilder();
    Room turn = world.getTurn().getCurrentRoom();
    if (turn.hasPet()) {
      sc.append("Pet is in the same space. Do you want to move the pet?\n");
      sc.append("Yes\n");
      sc.append("No\n");
      if ("Yes".equals("Yes")) {
        int i = 0;
        sc.append("Select room number to move the pet :\n");
        for (Room room : world.getRooms()) {
          sc.append(Integer.toString(i)).append(". ").append(room.getName()).append("\n");
          i++;
        }
        String roomNumber = "5";
        if (Integer.parseInt(roomNumber) > world.getRooms().size()
                || Integer.parseInt(roomNumber) < 0) {
          throw new IllegalArgumentException("Wrong input\n");
        } else {
          world.movePet(Integer.parseInt(roomNumber));
          world.hasPetMoved(true);
          sc.append("Pet has been moved.\n");
        }
      }
    } else {
      sc.append("Pet is not in the same space. You can't move the pet.\n");
    }
    String actual = sc.toString();
    String expected = "Pet is in the same space. Do you want to move the pet?\n"
            + "Yes\n"
            + "No\n"
            + "Select room number to move the pet :\n"
            + "0. Armory\n"
            + "1. Billiard Room\n"
            + "2. Carriage House\n"
            + "3. Dining Hall\n"
            + "4. Drawing Room\n"
            + "5. Foyer\n"
            + "6. Green House\n"
            + "7. Hedge Maze\n"
            + "8. Kitchen\n"
            + "9. Lancaster Room\n"
            + "10. Library\n"
            + "11. Lilac Room\n"
            + "12. Master Suite\n"
            + "13. Nursery\n"
            + "14. Parlor\n"
            + "15. Piazza\n"
            + "16. Servants' Quarters\n"
            + "17. Tennessee Room\n"
            + "18. Trophy Room\n"
            + "19. Wine Cellar\n"
            + "20. Winter Garden\n"
            + "Pet has been moved.\n";
    assertEquals(actual, expected);
    StringBuilder s = new StringBuilder();
    s.append("Target Character is in the room : ").append(world
            .getTargetCharacterLocation()).append("\n");
    s.append("Target Character information : ").append(world
            .getTargetCharacter()).append("\n");
    s.append("Target Character's pet is in the room : ").append(world
            .getTargetCharacterPetLocation()).append("\n");
    s.append("Target Character information : ").append(world
            .getTargetCharacterPet()).append("\n");
    String expected1 = "Target Character is in the room : Armory\n"
            + "Target Character information : [Target Character Name : "
            + "Doctor Lucky, Current Health : 50]\n"
            + "Target Character's pet is in the room : Foyer\n"
            + "Target Character information : {Pet name is :Fortune the Cat}\n";
    String actual1 = s.toString();
    assertEquals(actual1, expected1);
  }

  /**
   * Testing dfs starting over.
   * Dfs order[0 1 3 4 5 15 7 6 20 2 19 8 14 16 9 11 12 10 13 18 17].
   * Moving the pet to Tennessee Room[17].
   * In the next turn, pet will automatically move to Armory[0].
   * XOR
   * player | dfs
   * 0    |  0 | 0  will never happen (dfs will always try to move the pet).
   * 0    |  1 | 1
   * 1    |  0 | 1  will never happen (dfs will always try to move the pet).
   * 1    |  1 | 0
   */
  @Test
  public void movePetDfsTest() {
    StringBuilder sc = new StringBuilder();
    Room turn = world.getTurn().getCurrentRoom();
    if (turn.hasPet()) {
      sc.append("Pet is in the same space. Do you want to move the pet?\n");
      sc.append("Yes\n");
      sc.append("No\n");
      if ("Yes".equals("Yes")) {
        int i = 0;
        sc.append("Select room number to move the pet :\n");
        for (Room room : world.getRooms()) {
          sc.append(Integer.toString(i)).append(". ").append(
                  room.getName()).append("\n");
          i++;
        }
        String roomNumber = "17";
        if (Integer.parseInt(roomNumber) > world.getRooms().size()
                || Integer.parseInt(roomNumber) < 0) {
          throw new IllegalArgumentException("Wrong input\n");
        } else {
          world.movePet(Integer.parseInt(roomNumber));
          world.hasPetMoved(true);
          sc.append("Pet has been moved.\n");
        }
      }
    } else {
      sc.append("Pet is not in the same space. You can't move the pet.\n");
    }
    String actual = sc.toString();
    String expected = "Pet is in the same space. Do you want to move the pet?\n"
            + "Yes\n"
            + "No\n"
            + "Select room number to move the pet :\n"
            + "0. Armory\n"
            + "1. Billiard Room\n"
            + "2. Carriage House\n"
            + "3. Dining Hall\n"
            + "4. Drawing Room\n"
            + "5. Foyer\n"
            + "6. Green House\n"
            + "7. Hedge Maze\n"
            + "8. Kitchen\n"
            + "9. Lancaster Room\n"
            + "10. Library\n"
            + "11. Lilac Room\n"
            + "12. Master Suite\n"
            + "13. Nursery\n"
            + "14. Parlor\n"
            + "15. Piazza\n"
            + "16. Servants' Quarters\n"
            + "17. Tennessee Room\n"
            + "18. Trophy Room\n"
            + "19. Wine Cellar\n"
            + "20. Winter Garden\n"
            + "Pet has been moved.\n";
    assertEquals(actual, expected);
    StringBuilder s = new StringBuilder();
    s.append("Target Character is in the room : ").append(world
            .getTargetCharacterLocation()).append("\n");
    s.append("Target Character information : ").append(world
            .getTargetCharacter()).append("\n");
    s.append("Target Character's pet is in the room : ").append(world
            .getTargetCharacterPetLocation()).append("\n");
    s.append("Target Character information : ").append(world
            .getTargetCharacterPet()).append("\n");
    String expected1 = "Target Character is in the room : Armory\n"
            + "Target Character information : [Target Character Name : "
            + "Doctor Lucky, Current Health : 50]\n"
            + "Target Character's pet is in the room : Tennessee Room\n"
            + "Target Character information : {Pet name is :Fortune the Cat}\n";
    String actual1 = s.toString();
    assertEquals(actual1, expected1);
    world.movePlayer("Billiard Room");
    world.nextTurn();
    world.movePlayer("Billiard Room");
    world.nextTurn();
    StringBuilder s1 = new StringBuilder();
    s1.append("Target Character is in the room : ").append(world
            .getTargetCharacterLocation()).append("\n");
    s1.append("Target Character information : ").append(world
            .getTargetCharacter()).append("\n");
    s1.append("Target Character's pet is in the room : ").append(world
            .getTargetCharacterPetLocation()).append("\n");
    s1.append("Target Character information : ").append(world
            .getTargetCharacterPet()).append("\n");
    String expected2 = "Target Character is in the room : Carriage House\n"
            + "Target Character information : [Target Character Name : "
            + "Doctor Lucky, Current Health : 50]\n"
            + "Target Character's pet is in the room : Armory\n"
            + "Target Character information : {Pet name is :Fortune the Cat}\n";
    String actual2 = s1.toString();
    assertEquals(actual2, expected2);
  }

  /**
   * Player 2 is in the room "Armory".
   * Billiard Room is not visible as the Pet is in that room.
   */
  @Test
  public void testingNeighborNoVisibilityWithPet() {
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
   * testing pet movement by player.
   * Pet is moved to Foyer[5] by the player.
   */
  @Test
  public void testingNeighborVisibilityWithPet() throws IOException {
    StringBuilder sc = new StringBuilder();
    Room turn = world.getTurn().getCurrentRoom();
    if (turn.hasPet()) {
      sc.append("Pet is in the same space. Do you want to move the pet?\n");
      sc.append("Yes\n");
      sc.append("No\n");
      if ("Yes".equals("Yes")) {
        int i = 0;
        sc.append("Select room number to move the pet :\n");
        for (Room room : world.getRooms()) {
          sc.append(Integer.toString(i)).append(". ").append(room.getName()).append("\n");
          i++;
        }
        String roomNumber = "5";
        if (Integer.parseInt(roomNumber) > world.getRooms().size()
                || Integer.parseInt(roomNumber) < 0) {
          throw new IllegalArgumentException("Wrong input\n");
        } else {
          world.movePet(Integer.parseInt(roomNumber));
          world.hasPetMoved(true);
          sc.append("Pet has been moved.\n");
        }
      }
    } else {
      sc.append("Pet is not in the same space. You can't move the pet.\n");
    }
    String actual = sc.toString();
    String expected = "Pet is in the same space. Do you want to move the pet?\n"
            + "Yes\n"
            + "No\n"
            + "Select room number to move the pet :\n"
            + "0. Armory\n"
            + "1. Billiard Room\n"
            + "2. Carriage House\n"
            + "3. Dining Hall\n"
            + "4. Drawing Room\n"
            + "5. Foyer\n"
            + "6. Green House\n"
            + "7. Hedge Maze\n"
            + "8. Kitchen\n"
            + "9. Lancaster Room\n"
            + "10. Library\n"
            + "11. Lilac Room\n"
            + "12. Master Suite\n"
            + "13. Nursery\n"
            + "14. Parlor\n"
            + "15. Piazza\n"
            + "16. Servants' Quarters\n"
            + "17. Tennessee Room\n"
            + "18. Trophy Room\n"
            + "19. Wine Cellar\n"
            + "20. Winter Garden\n"
            + "Pet has been moved.\n";
    assertEquals(actual, expected);
    StringBuilder s = new StringBuilder();
    s.append("Target Character is in the room : ").append(world
            .getTargetCharacterLocation()).append("\n");
    s.append("Target Character information : ").append(world
            .getTargetCharacter()).append("\n");
    s.append("Target Character's pet is in the room : ").append(world
            .getTargetCharacterPetLocation()).append("\n");
    s.append("Target Character information : ").append(world
            .getTargetCharacterPet()).append("\n");
    String expected1 = "Target Character is in the room : Armory\n"
            + "Target Character information : [Target Character Name : "
            + "Doctor Lucky, Current Health : 50]\n"
            + "Target Character's pet is in the room : Foyer\n"
            + "Target Character information : {Pet name is :Fortune the Cat}\n";
    String actual1 = s.toString();
    assertEquals(actual1, expected1);

    world.nextTurn();
    StringBuilder ss = new StringBuilder();
    List<Room> neighbour1 = world.getTurn().getCurrentRoom().getNeighbor();
    Room currentRoom1 = world.getTurn().getCurrentRoom();
    ss.append("Current location").append("\n");
    ss.append("================").append("\n");
    ss.append(currentRoom1.getName()).append("\n");
    ss.append("Room occupants").append("\n");
    ss.append("================").append("\n");
    if (currentRoom1.hasPet()) {
      ss.append("The Pet").append("\n");
    }
    if (currentRoom1.hasTargetCharacter()) {
      ss.append(world.getTargetCharacter()).append("\n");
    }
    if (currentRoom1.getPlayers().size() > 0) {
      for (Player p : currentRoom1.getPlayers()) {
        ss.append(p.playerDescription()).append("\n");
      }
    }
    ss.append("Items in the Room").append("\n");
    ss.append("================").append("\n");
    if (currentRoom1.getWeapons().size() > 0) {
      for (Weapon p : currentRoom1.getWeapons()) {
        ss.append(p.weaponInfo()).append("\n");
      }
    }
    ss.append("Neighbors of the room").append("\n");
    ss.append("================").append("\n");
    for (Room r : neighbour1) {
      if (!r.hasPet()) {
        ss.append(r.displaySingleRoomInformation(r, world)).append("\n");
      }
    }
    String actual12 = ss.toString();
    String expected12 = "Current location\n"
            + "================\n"
            + "Armory\n"
            + "Room occupants\n"
            + "================\n"
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
            + "[Target Character Name : Doctor Lucky, Current Health : 50]\n"
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
    assertEquals(expected12, actual12);
  }
}
