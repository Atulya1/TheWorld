package milestone_four_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import player.GameOverException;
import room.Room;
import the_world_view.ViewInterface;
import the_world_view_controller.Controller;
import the_world_view_controller.ControllerInterface;
import weapon.Weapon;
import world.World;
import world.WorldModel;

/**
 * A Junit test class for testing Attack on the target character.
 */

public class AttackPlayerTestView {
  StringBuilder log = new StringBuilder();
  private ControllerInterface controller;
  private ViewInterface view;
  private World model;

  /**
   * Set method to initialize WorldModel object for running the tests.
   */
  @Before
  public void setUpWorld() {
    String inputFile = "res/mansion.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      model = new WorldModel();
      model = model.parseFile(reader);
      assertNotNull(model);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    view = new TheWorldMockView(log);
    controller = new Controller(view, model);
    controller.addPlayer("Atul", "5", "Armory", "Human");
    controller.addPlayer("Salli", "5", "Armory", "Human");
    controller.setTurns("10");
  }

  /**
   * Testing attack by Poking.
   *
   * @throws GameOverException when game gets over.
   */
  @Test
  public void testingAttackByPoke() throws GameOverException {
    //Player 1 moving to Dining Hall(Turn 1)
    model.movePlayer("Dining Hall");
    model.nextT();
    //Player 2 doing nothing(Turn 2)
    model.nextT();
    //Player 1 moving to Drawing Room(Turn 3)
    model.movePlayer("Drawing Room");
    model.nextT();
    //Player 2 moving to Billiard Room(Turn 3)
    model.movePlayer("Billiard Room");
    assertEquals("Salli", controller.getTurn().getName());
    model.nextT();
    assertEquals("Atul", controller.getTurn().getName());
    assertEquals(6, controller.getTargetCharacterHealth());
    assertEquals("[]", controller.getTurn().getWeapons().toString());
    model.decreaseHealth("Poke");
    assertEquals(5, controller.getTargetCharacterHealth());
  }

  /**
   * testing attack by item.
   *
   * @throws GameOverException when game gets over.
   */
  @Test
  public void testingAttackByItem() throws GameOverException {
    //Picking Revolver from the armory
    Room r = model.getTurn().getCurrentRoom();
    String weap = "Revolver";
    r.removeWeapon(weap);
    model.addWeaponToPlayer(model.getTurn(), weap);
    //Player 1 moving to Dining Hall(Turn 1)
    model.movePlayer("Dining Hall");
    model.nextT();
    //Player 2 doing nothing(Turn 2)
    model.nextT();
    //Player 1 moving to Drawing Room(Turn 3)
    model.movePlayer("Drawing Room");
    model.nextT();
    //Player 2 moving to Billiard Room(Turn 3)
    model.movePlayer("Billiard Room");
    model.nextT();
    assertEquals("Atul", controller.getTurn().getName());
    assertEquals(6, controller.getTargetCharacterHealth());
    assertEquals("[WeaponType{roomIndex=0, weaponName='Revolver', damageValue=3}]",
            controller.getTurn().getWeapons().toString());
    model.decreaseHealth("Revolver");
    assertEquals(3, controller.getTargetCharacterHealth());
  }

  /**
   * Testing attack with a weapon that is unavailable.
   */
  @Test
  public void testAttackWithUnavailableWeapon() throws GameOverException {
    //Picking Revolver from the armory
    Room r = model.getTurn().getCurrentRoom();
    String weap = "Revolver";
    r.removeWeapon(weap);
    model.addWeaponToPlayer(model.getTurn(), weap);
    //Player 1 moving to Dining Hall(Turn 1)
    model.movePlayer("Dining Hall");
    model.nextT();
    //Player 2 doing nothing(Turn 2)
    model.nextT();
    //Player 1 moving to Drawing Room(Turn 3)
    model.movePlayer("Drawing Room");
    model.nextT();
    //Player 2 moving to Billiard Room(Turn 3)
    model.movePlayer("Billiard Room");
    model.nextT();

    assertEquals("Atul", controller.getTurn().getName());
    assertEquals(6, controller.getTargetCharacterHealth());
    assertEquals("[WeaponType{roomIndex=0, weaponName='Revolver', damageValue=3}]",
            controller.getTurn().getWeapons().toString());
    model.decreaseHealth("Pan");
    assertEquals(6, controller.getTargetCharacterHealth());
  }

  /**
   * testing removing item after attack.
   *
   * @throws GameOverException when game gets over.
   */
  @Test
  public void testingRemovingItemAfterAttack() throws GameOverException {
    //Picking Revolver from the armory
    Room r = model.getTurn().getCurrentRoom();
    String weap = "Revolver";
    r.removeWeapon(weap);
    model.addWeaponToPlayer(model.getTurn(), weap);
    //Player 1 moving to Dining Hall(Turn 1)
    model.movePlayer("Dining Hall");
    model.nextT();
    //Player 2 doing nothing(Turn 2)
    model.nextT();
    //Player 1 moving to Drawing Room(Turn 3)
    model.movePlayer("Drawing Room");
    model.nextT();
    //Player 2 moving to Billiard Room(Turn 3)
    model.movePlayer("Billiard Room");
    model.nextT();
    assertEquals("Atul", controller.getTurn().getName());
    assertEquals(6, controller.getTargetCharacterHealth());
    assertEquals("[WeaponType{roomIndex=0, weaponName='Revolver', damageValue=3}]",
            controller.getTurn().getWeapons().toString());
    model.decreaseHealth("Revolver");
    assertEquals(3, controller.getTargetCharacterHealth());
    assertEquals("[]",
            controller.getTurn().getWeapons().toString());
  }

  /**
   * testing attack on Target character in Different Space.
   *
   * @throws GameOverException when game gets over.
   */
  @Test
  public void testingTargetDifferentSpace() throws GameOverException {
    //Player 1 moving to Dining Hall(Turn 1)
    model.movePlayer("Dining Hall");
    model.nextTurn();
    //Player 2 doing nothing(Turn 2)
    model.nextTurn();
    //Player 1 moving to Drawing Room(Turn 3)
    model.movePlayer("Drawing Room");
    model.nextTurn();
    //Player 2 moving to Billiard Room(Turn 3)
    //world.movePlayer("Billiard Room");
    //world.nextTurn();
    StringBuilder sc = new StringBuilder();

    Room currentRoom = model.getTurn().getCurrentRoom();
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
    if (currentRoom.getName().equals(model.getTargetCharacterLocation())) {
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
          for (Weapon weapon : model.getTurn().getWeapons()) {
            sc.append(weapon.getWeaponName())
                    .append("-> Damage Value : ").append(Integer.toString(weapon
                            .getDamageValue())).append("\n");
          }
          sc.append("Poke").append("-> Damage Value : ").append("1").append("\n");
          String weapon = "Revolver";
          try {
            model.decreaseHealth(weapon);
            sc.append("Attack successful with weapon : ").append(weapon).append("\n");
          } catch (GameOverException e) {
            sc.append("Game over").append("\n");
            sc.append("Winner : ").append(model.getTurn().getName());
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
    for (Weapon selectedWeapon : model.getTurn().getWeapons()) {
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

    Room currentRoom = model.getTurn().getCurrentRoom();
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
    if (currentRoom.getName().equals(model.getTargetCharacterLocation())) {
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
          for (Weapon weapon : model.getTurn().getWeapons()) {
            sc.append(weapon.getWeaponName())
                    .append("-> Damage Value : ").append(Integer.toString(weapon
                            .getDamageValue())).append("\n");
          }
          sc.append("Poke").append("-> Damage Value : ").append("1").append("\n");
          String weapon = "Revolver";
          try {
            model.decreaseHealth(weapon);
            sc.append("Attack successful with weapon : ").append(weapon).append("\n");
          } catch (GameOverException e) {
            sc.append("Game over").append("\n");
            sc.append("Winner : ").append(model.getTurn().getName());
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
    for (Weapon selectedWeapon : model.getTurn().getWeapons()) {
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