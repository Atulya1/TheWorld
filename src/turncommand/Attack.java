package turncommand;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import player.GameOverException;
import room.Room;
import weapon.Weapon;
import world.World;

/**
 * Look around during a turn.
 */
public class Attack implements TurnCommands {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param scan the source to read from
   * @param out  the target to print to
   */
  public Attack(Appendable out, Scanner scan) {
    if (scan == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = scan;
  }

  /**
   * Overridden method to see target character and the pet info.
   *
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException              IOException.
   */
  @Override
  public void go(World world) throws IllegalArgumentException, IOException,
          GameOverException {
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
      out.append("Target Character is in the same room.").append("\n");
      if (hasPlayersInTheVicinity) {
        out.append("There are other players in the vicinity. "
                + "You can't attack.").append("\n");
      } else {
        out.append("Do you want to attack? Press 1 or 2.").append("\n");
        out.append("1. Yes").append("\n");
        out.append("2. No").append("\n");
        String action = scan.nextLine();
        if (("1").equals(action)) {
          out.append("Choose weapon to attack").append("\n");
          out.append("=======================").append("\n");
          for (Weapon weapon : world.getTurn().getWeapons()) {
            out.append(weapon.getWeaponName())
              .append("-> Damage Value : ").append(Integer.toString(weapon
                            .getDamageValue())).append("\n");
          }
          out.append("Poke").append("-> Damage Value : ").append("1").append("\n");
          String weapon = scan.nextLine();
          boolean present = false;
          for (Weapon selectedWeapon : world.getTurn().getWeapons()) {
            if (selectedWeapon.getWeaponName().equals(weapon)) {
              present = true;
              break;
            }
          }
          try {
            if (!present) {
              out.append("Weapon not present in the inventory").append(weapon).append("\n");
              throw new IllegalArgumentException("Weapon not present in the inventory");
            }
            world.decreaseHealth(weapon);
            out.append("Attack successful with weapon : ").append(weapon).append("\n");
          } catch (GameOverException e) {
            out.append("Game over").append("\n");
            out.append("Winner : ").append(world.getTurn().getName());
            return;
          } catch (IllegalArgumentException e) {
            out.append(e.getMessage()).append("\n");
          }
        }
      }
    } else {
      out.append("Target Character is not in the same room. "
              + "You can't attack.").append("\n");
    }
  }

  /**
   * Overridden method to see target character and the pet info.
   *
   * @param world the model to use.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws IOException              IOException.
   */
  @Override
  public void goAutomatic(World world) throws IllegalArgumentException,
          IOException, GameOverException {
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
      out.append("Target Character is in the same room.").append("\n");
      if (hasPlayersInTheVicinity) {
        out.append("There are other players in the vicinity. "
                + "You can't attack.").append("\n");
      } else {
        out.append("Attacking....").append("\n");
        out.append("=======================").append("\n");
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
          out.append(w.getWeaponName())
                  .append("-> Damage Value : ").append(Integer.toString(w
                          .getDamageValue())).append("\n");
        } else {
          weaponName = "Poke";
          out.append("No weapons available. Poking in the eye").append("\n");
          out.append("Poke").append("-> Damage Value : ").append("1").append("\n");
        }
        world.decreaseHealth(weaponName);
        world.nextT();
        out.append("Attack successful.").append("\n");
      }
    } else {
      out.append("Target Character is not in the same room. "
              + "You can't attack.").append("\n");
      world.nextT();
    }
  }
}
