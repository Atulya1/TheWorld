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
import weapon.Weapon;
import world.World;
import world.WorldModel;

/**
 * A Junit test class for testing Attack on the target character.
 */

public class AttackPlayerTest {
  private Appendable out;
  private Scanner scan;
  private World world;

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
   * Testing attack by Poking.
   *
   * @throws GameOverException when game gets over.
   */
  @Test
  public void testingAttackByPoke() throws GameOverException {
    //Player 1 moving to Dining Hall(Turn 1)
    world.movePlayer("Dining Hall");
    world.nextTurn();
    //Player 2 doing nothing(Turn 2)
    world.nextTurn();
    //Player 1 moving to Drawing Room(Turn 3)
    world.movePlayer("Drawing Room");
    world.nextTurn();
    //Player 2 moving to Billiard Room(Turn 3)
    world.movePlayer("Billiard Room");
    world.nextTurn();
    StringBuilder sc = new StringBuilder();
    Room currentRoom = world.getTurn().getCurrentRoom();
    List<Room> neighbours = currentRoom.getNeighbor();
    boolean hasPlayersInTheVicinity = false;
    for (Room room : neighbours) {
      if (room.getPlayers().size() > 0) {
        hasPlayersInTheVicinity = true;
      }
    }
    if (currentRoom.getPlayers().size() > 1) {
      hasPlayersInTheVicinity = true;
    }
    if (currentRoom.getName().equals(world.getTargetCharacterLocation())) {
      sc.append("Target Character is in the same room.").append("\n");
      if (hasPlayersInTheVicinity) {
        sc.append("There are other players in the vicinity. "
                + "You can't attack.").append("\n");
      } else {
        sc.append("Attacking....").append("\n");
        sc.append("=======================").append("\n");
        String weaponName = "";
        if (world.getTurn().getWeapons().size() > 0) {
          int attackValue = 0;
          Weapon w = null;
          for (Weapon weapon : world.getTurn().getWeapons()) {
            if (attackValue < weapon.getDamageValue()) {
              attackValue = weapon.getDamageValue();
              w = weapon;
            }
          }
          weaponName = w.getWeaponName();
          sc.append(w.getWeaponName())
                  .append("-> Damage Value : ").append(Integer.toString(w
                          .getDamageValue())).append("\n");
        } else {
          weaponName = "Poke";
          sc.append("No weapons available. Poking in the eye").append("\n");
          sc.append("Poke").append("-> Damage Value : ").append("1").append("\n");
        }
        world.decreaseHealth(weaponName);
        sc.append("Attack successful.").append("\n");
      }
    } else {
      sc.append("Target Character is not in the same room. "
              + "You can't attack.").append("\n");
    }
    String expected = sc.toString();
    String actual = "Target Character is in the same room.\n"
            + "Attacking....\n"
            + "=======================\n"
            + "No weapons available. Poking in the eye\n"
            + "Poke-> Damage Value : 1\n"
            + "Attack successful.\n";
    assertEquals(expected, actual);
    StringBuilder s = new StringBuilder();
    s.append("Target Character is in the room : ").append(world
            .getTargetCharacterLocation()).append("\n");
    s.append("Target Character information : ").append(world
            .getTargetCharacter()).append("\n");
    s.append("Target Character's pet is in the room : ").append(world
            .getTargetCharacterPetLocation()).append("\n");
    s.append("Target Character information : ").append(world
            .getTargetCharacterPet()).append("\n");
    String expect = "Target Character is in the room : Drawing Room\n"
            + "Target Character information : [Target Character Name : "
            + "Doctor Lucky, Current Health : 49]\n"
            + "Target Character's pet is in the room : Trophy Room\n"
            + "Target Character information : {Pet name is :Fortune the Cat}\n";
    String ac = s.toString();
    assertEquals(ac, expect);
  }

  /**
   * testing attack by item.
   *
   * @throws GameOverException when game gets over.
   */
  @Test
  public void testingAttackByItem() throws GameOverException {
    //Picking Revolver from the armory
    Room r = world.getTurn().getCurrentRoom();
    String weap = "Revolver";
    r.removeWeapon(weap);
    world.addWeaponToPlayer(world.getTurn(), weap);
    //Player 1 moving to Dining Hall(Turn 1)
    world.movePlayer("Dining Hall");
    world.nextTurn();
    //Player 2 doing nothing(Turn 2)
    world.nextTurn();
    //Player 1 moving to Drawing Room(Turn 3)
    world.movePlayer("Drawing Room");
    world.nextTurn();
    //Player 2 moving to Billiard Room(Turn 3)
    world.movePlayer("Billiard Room");
    world.nextTurn();
    StringBuilder sc = new StringBuilder();

    Room currentRoom = world.getTurn().getCurrentRoom();
    List<Room> neighbours = currentRoom.getNeighbor();
    boolean hasPlayersInTheVicinity = false;
    for (Room room : neighbours) {
      if (room.getPlayers().size() > 0) {
        hasPlayersInTheVicinity = true;
      }
    }
    if (currentRoom.getPlayers().size() > 1) {
      hasPlayersInTheVicinity = true;
    }
    if (currentRoom.getName().equals(world.getTargetCharacterLocation())) {
      sc.append("Target Character is in the same room.").append("\n");
      if (hasPlayersInTheVicinity) {
        sc.append("There are other players in the vicinity. "
                + "You can't attack.").append("\n");
      } else {
        sc.append("Do you want to attack? Press 1 or 2.").append("\n");
        sc.append("1. Yes").append("\n");
        sc.append("2. No").append("\n");
        String action = "1";
        if ("1".equals(action)) {
          sc.append("Choose weapon to attack").append("\n");
          sc.append("=======================").append("\n");
          for (Weapon weapon : world.getTurn().getWeapons()) {
            sc.append(weapon.getWeaponName())
                    .append("-> Damage Value : ").append(Integer.toString(weapon
                            .getDamageValue())).append("\n");
          }
          sc.append("Poke").append("-> Damage Value : ").append("1").append("\n");
          String weapon = "Revolver";
          try {
            world.decreaseHealth(weapon);
            sc.append("Attack successful with weapon : ").append(weapon).append("\n");
          } catch (GameOverException e) {
            sc.append("Game over").append("\n");
            sc.append("Winner : ").append(world.getTurn().getName());
            return;
          } catch (IllegalArgumentException e) {
            sc.append("Wrong weapon name").append("\n");
          }
        }
      }
    } else {
      sc.append("Target Character is not in the same room. "
              + "You can't attack.").append("\n");
    }
    String actual = sc.toString();
    String expected = "Target Character is in the same room.\n"
            + "Do you want to attack? Press 1 or 2.\n"
            + "1. Yes\n"
            + "2. No\n"
            + "Choose weapon to attack\n"
            + "=======================\n"
            + "Revolver-> Damage Value : 3\n"
            + "Poke-> Damage Value : 1\n"
            + "Attack successful with weapon : Revolver\n";
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
    String expect = "Target Character is in the room : Drawing Room\n"
            + "Target Character information : [Target Character Name : "
            + "Doctor Lucky, Current Health : 47]\n"
            + "Target Character's pet is in the room : Trophy Room\n"
            + "Target Character information : {Pet name is :Fortune the Cat}\n";
    String ac = s.toString();
    assertEquals(ac, expect);
  }

  /**
   * Testing attack with a weapon that is unavailable.
   */
  @Test
  public void testAttackWithUnavailableWeapon() {
    //Picking Revolver from the armory
    Room r = world.getTurn().getCurrentRoom();
    String weap = "Revolver";
    r.removeWeapon(weap);
    world.addWeaponToPlayer(world.getTurn(), weap);
    //Player 1 moving to Dining Hall(Turn 1)
    world.movePlayer("Dining Hall");
    world.nextTurn();
    //Player 2 doing nothing(Turn 2)
    world.nextTurn();
    //Player 1 moving to Drawing Room(Turn 3)
    world.movePlayer("Drawing Room");
    world.nextTurn();
    //Player 2 moving to Billiard Room(Turn 3)
    world.movePlayer("Billiard Room");
    world.nextTurn();
    StringBuilder sc = new StringBuilder();

    Room currentRoom = world.getTurn().getCurrentRoom();
    List<Room> neighbours = currentRoom.getNeighbor();
    boolean hasPlayersInTheVicinity = false;
    for (Room room : neighbours) {
      if (room.getPlayers().size() > 0) {
        hasPlayersInTheVicinity = true;
      }
    }
    if (currentRoom.getPlayers().size() > 1) {
      hasPlayersInTheVicinity = true;
    }
    if (currentRoom.getName().equals(world.getTargetCharacterLocation())) {
      sc.append("Target Character is in the same room.").append("\n");
      if (hasPlayersInTheVicinity) {
        sc.append("There are other players in the vicinity. "
                + "You can't attack.").append("\n");
      } else {
        sc.append("Do you want to attack? Press 1 or 2.").append("\n");
        sc.append("1. Yes").append("\n");
        sc.append("2. No").append("\n");
        String action = "1";
        if ("1".equals(action)) {
          sc.append("Choose weapon to attack").append("\n");
          sc.append("=======================").append("\n");
          for (Weapon weapon : world.getTurn().getWeapons()) {
            sc.append(weapon.getWeaponName())
                    .append("-> Damage Value : ").append(Integer.toString(weapon
                            .getDamageValue())).append("\n");
          }
          sc.append("Poke").append("-> Damage Value : ").append("1").append("\n");
          String weapon = "Trowel";
          boolean present = false;
          for (Weapon selectedWeapon : world.getTurn().getWeapons()) {
            if (selectedWeapon.getWeaponName().equals(weapon)) {
              present = true;
              break;
            }
          }
          try {
            if (!present) {
              sc.append("Weapon not present in the inventory : ").append(weapon).append("\n");
              throw new IllegalArgumentException("Weapon not present in the inventory");
            }
            world.decreaseHealth(weapon);
            sc.append("Attack successful with weapon : ").append(weapon).append("\n");
          } catch (GameOverException e) {
            sc.append("Game over").append("\n");
            sc.append("Winner : ").append(world.getTurn().getName());
            return;
          } catch (IllegalArgumentException e) {
            sc.append(e.getMessage()).append("\n");
          }
        }
      }
    } else {
      sc.append("Target Character is not in the same room. "
              + "You can't attack.").append("\n");
    }
    String actual = sc.toString();
    String expected = "Target Character is in the same room.\n"
            + "Do you want to attack? Press 1 or 2.\n"
            + "1. Yes\n"
            + "2. No\n"
            + "Choose weapon to attack\n"
            + "=======================\n"
            + "Revolver-> Damage Value : 3\n"
            + "Poke-> Damage Value : 1\n"
            + "Weapon not present in the inventory : Trowel\n"
            + "Weapon not present in the inventory\n";
    assertEquals(actual, expected);
  }

  /**
   * testing removing item after attack.
   *
   * @throws GameOverException when game gets over.
   */
  @Test
  public void testingRemovingItemAfterAttack() throws GameOverException {
    //Picking Revolver from the armory
    Room r = world.getTurn().getCurrentRoom();
    String weap = "Revolver";
    r.removeWeapon(weap);
    world.addWeaponToPlayer(world.getTurn(), weap);
    //Player 1 moving to Dining Hall(Turn 1)
    world.movePlayer("Dining Hall");
    world.nextTurn();
    //Player 2 doing nothing(Turn 2)
    world.nextTurn();
    //Player 1 moving to Drawing Room(Turn 3)
    world.movePlayer("Drawing Room");
    world.nextTurn();
    //Player 2 moving to Billiard Room(Turn 3)
    world.movePlayer("Billiard Room");
    world.nextTurn();
    StringBuilder sc = new StringBuilder();

    Room currentRoom = world.getTurn().getCurrentRoom();
    List<Room> neighbours = currentRoom.getNeighbor();
    boolean hasPlayersInTheVicinity = false;
    for (Room room : neighbours) {
      if (room.getPlayers().size() > 0) {
        hasPlayersInTheVicinity = true;
      }
    }
    if (currentRoom.getPlayers().size() > 1) {
      hasPlayersInTheVicinity = true;
    }
    if (currentRoom.getName().equals(world.getTargetCharacterLocation())) {
      sc.append("Target Character is in the same room.").append("\n");
      if (hasPlayersInTheVicinity) {
        sc.append("There are other players in the vicinity. "
                + "You can't attack.").append("\n");
      } else {
        sc.append("Do you want to attack? Press 1 or 2.").append("\n");
        sc.append("1. Yes").append("\n");
        sc.append("2. No").append("\n");
        String action = "1";
        if (("1").equals(action)) {
          sc.append("Choose weapon to attack").append("\n");
          sc.append("=======================").append("\n");
          for (Weapon weapon : world.getTurn().getWeapons()) {
            sc.append(weapon.getWeaponName())
                    .append("-> Damage Value : ").append(Integer.toString(weapon
                            .getDamageValue())).append("\n");
          }
          sc.append("Poke").append("-> Damage Value : ").append("1").append("\n");
          String weapon = "Revolver";
          try {
            world.decreaseHealth(weapon);
            sc.append("Attack successful with weapon : ").append(weapon).append("\n");
          } catch (GameOverException e) {
            sc.append("Game over").append("\n");
            sc.append("Winner : ").append(world.getTurn().getName());
            return;
          } catch (IllegalArgumentException e) {
            sc.append("Wrong weapon name").append("\n");
          }
        }
      }
    } else {
      sc.append("Target Character is not in the same room. "
              + "You can't attack.").append("\n");
    }
    String actual = sc.toString();
    String weapon = "Trowel";
    Weapon wea = null;
    for (Weapon selectedWeapon : world.getTurn().getWeapons()) {
      if (selectedWeapon.getWeaponName().equals(weapon)) {
        wea = selectedWeapon;
        break;
      }
    }
    String expected = "Target Character is in the same room.\n"
            + "Do you want to attack? Press 1 or 2.\n"
            + "1. Yes\n"
            + "2. No\n"
            + "Choose weapon to attack\n"
            + "=======================\n"
            + "Revolver-> Damage Value : 3\n"
            + "Poke-> Damage Value : 1\n"
            + "Attack successful with weapon : Revolver\n";
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
    String expect = "Target Character is in the room : Drawing Room\n"
            + "Target Character information : [Target Character Name : "
            + "Doctor Lucky, Current Health : 47]\n"
            + "Target Character's pet is in the room : Trophy Room\n"
            + "Target Character information : {Pet name is :Fortune the Cat}\n";
    String ac = s.toString();
    assertEquals(ac, expect);

    StringBuilder sb = new StringBuilder();
    List<Weapon> weaponList1 = world.getWeapons();
    assertFalse(weaponList1.contains(wea));
  }

  /**
   * testing attack on Target character in Different Space.
   *
   * @throws GameOverException when game gets over.
   */
  @Test
  public void testingTargetDifferentSpace() throws GameOverException {
    //Player 1 moving to Dining Hall(Turn 1)
    world.movePlayer("Dining Hall");
    world.nextTurn();
    //Player 2 doing nothing(Turn 2)
    world.nextTurn();
    //Player 1 moving to Drawing Room(Turn 3)
    world.movePlayer("Drawing Room");
    world.nextTurn();
    //Player 2 moving to Billiard Room(Turn 3)
    //world.movePlayer("Billiard Room");
    //world.nextTurn();
    StringBuilder sc = new StringBuilder();

    Room currentRoom = world.getTurn().getCurrentRoom();
    List<Room> neighbours = currentRoom.getNeighbor();
    boolean hasPlayersInTheVicinity = false;
    for (Room room : neighbours) {
      if (room.getPlayers().size() > 0) {
        hasPlayersInTheVicinity = true;
      }
    }
    if (currentRoom.getPlayers().size() > 1) {
      hasPlayersInTheVicinity = true;
    }
    if (currentRoom.getName().equals(world.getTargetCharacterLocation())) {
      sc.append("Target Character is in the same room.").append("\n");
      if (hasPlayersInTheVicinity) {
        sc.append("There are other players in the vicinity. "
                + "You can't attack.").append("\n");
      } else {
        sc.append("Do you want to attack? Press 1 or 2.").append("\n");
        sc.append("1. Yes").append("\n");
        sc.append("2. No").append("\n");
        String action = "1";
        if ("1".equals(action)) {
          sc.append("Choose weapon to attack").append("\n");
          sc.append("=======================").append("\n");
          for (Weapon weapon : world.getTurn().getWeapons()) {
            sc.append(weapon.getWeaponName())
                    .append("-> Damage Value : ").append(Integer.toString(weapon
                            .getDamageValue())).append("\n");
          }
          sc.append("Poke").append("-> Damage Value : ").append("1").append("\n");
          String weapon = "Revolver";
          try {
            world.decreaseHealth(weapon);
            sc.append("Attack successful with weapon : ").append(weapon).append("\n");
          } catch (GameOverException e) {
            sc.append("Game over").append("\n");
            sc.append("Winner : ").append(world.getTurn().getName());
            return;
          } catch (IllegalArgumentException e) {
            sc.append("Wrong weapon name").append("\n");
          }
        }
      }
    } else {
      sc.append("Target Character is not in the same room. "
              + "You can't attack.").append("\n");
    }
    String actual = sc.toString();
    String weapon = "Trowel";
    Weapon wea = null;
    for (Weapon selectedWeapon : world.getTurn().getWeapons()) {
      if (selectedWeapon.getWeaponName().equals(weapon)) {
        wea = selectedWeapon;
        break;
      }
    }
    String expected = "Target Character is not in the same room. You can't attack.\n";
    assertEquals(actual, expected);
  }

  /**
   * testing attack on Target character players in same Space.
   *
   * @throws GameOverException when game gets over.
   */
  @Test
  public void testingAttackPlayersSameSpace() throws GameOverException {

    StringBuilder sc = new StringBuilder();

    Room currentRoom = world.getTurn().getCurrentRoom();
    List<Room> neighbours = currentRoom.getNeighbor();
    boolean hasPlayersInTheVicinity = false;
    for (Room room : neighbours) {
      if (room.getPlayers().size() > 0) {
        hasPlayersInTheVicinity = true;
      }
    }
    if (currentRoom.getPlayers().size() > 1) {
      hasPlayersInTheVicinity = true;
    }
    if (currentRoom.getName().equals(world.getTargetCharacterLocation())) {
      sc.append("Target Character is in the same room.").append("\n");
      if (hasPlayersInTheVicinity) {
        sc.append("There are other players in the vicinity. "
                + "You can't attack.").append("\n");
      } else {
        sc.append("Do you want to attack? Press 1 or 2.").append("\n");
        sc.append("1. Yes").append("\n");
        sc.append("2. No").append("\n");
        String action = "1";
        if ("1".equals(action)) {
          sc.append("Choose weapon to attack").append("\n");
          sc.append("=======================").append("\n");
          for (Weapon weapon : world.getTurn().getWeapons()) {
            sc.append(weapon.getWeaponName())
                    .append("-> Damage Value : ").append(Integer.toString(weapon
                            .getDamageValue())).append("\n");
          }
          sc.append("Poke").append("-> Damage Value : ").append("1").append("\n");
          String weapon = "Revolver";
          try {
            world.decreaseHealth(weapon);
            sc.append("Attack successful with weapon : ").append(weapon).append("\n");
          } catch (GameOverException e) {
            sc.append("Game over").append("\n");
            sc.append("Winner : ").append(world.getTurn().getName());
            return;
          } catch (IllegalArgumentException e) {
            sc.append("Wrong weapon name").append("\n");
          }
        }
      }
    } else {
      sc.append("Target Character is not in the same room. "
              + "You can't attack.").append("\n");
    }
    String actual = sc.toString();
    String weapon = "Trowel";
    Weapon wea = null;
    for (Weapon selectedWeapon : world.getTurn().getWeapons()) {
      if (selectedWeapon.getWeaponName().equals(weapon)) {
        wea = selectedWeapon;
        break;
      }
    }
    String expected = "Target Character is in the same room.\n"
            + "There are other players in the vicinity. You can't attack.\n";
    assertEquals(actual, expected);
  }
}