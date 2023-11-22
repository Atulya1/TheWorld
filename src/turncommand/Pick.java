package turncommand;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import player.GameOverException;
import room.Room;
import weapon.Weapon;
import world.World;

/**
 * Pick a weapon during turn.
 */
public class Pick implements TurnCommands {

  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param scan  the source to read from
   * @param out the target to print to
   */
  public Pick(Appendable out, Scanner scan) {
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
  public void go(World world) throws IllegalArgumentException, IOException {
    out.append("Current weapon limit is : ").append(String.valueOf(world.getTurn()
            .getCurrentWeaponLimit())).append("\n");
    if (world.getTurn().getCurrentWeaponLimit() <= 0) {
      out.append("Weapons limit exhausted.");
      world.nextTurn();
      return;
    }
    Room room = world.getTurn().getCurrentRoom();
    if (room == null || room.getWeapons().size() <= 0) {
      out.append("Weapons not available.");
      return;
    }
    out.append("Pick weapon : \n");
    String weapon = "";
    for (Weapon w : room.getWeapons()) {
      out.append(w.toString()).append("\n");
    }
    weapon = scan.nextLine();
    room.removeWeapon(weapon);
    world.addWeaponToPlayer(world.getTurn(), weapon);
    out.append("Weapons removed from the room and added to the the player\n");
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
    out.append("Current weapon limit is : ").append(String.valueOf(world.getTurn()
            .getCurrentWeaponLimit())).append("\n");
    if (world.getTurn().getCurrentWeaponLimit() <= 0) {
      out.append("Weapons limit exhausted.");
      world.nextT();
      return;
    }
    Room room = world.getTurn().getCurrentRoom();
    if (room == null || room.getWeapons().size() <= 0) {
      out.append("Weapons not available.");
      return;
    }
    out.append("Pick weapon : \n");

    for (Weapon w : room.getWeapons()) {
      out.append(w.toString()).append("\n");
    }
    Weapon w = room.getWeapons()
            .get(new Random().nextInt(room.getWeapons().size()));
    out.append(w.getWeaponName()).append(" selected\n");
    room.removeWeapon(w.getWeaponName());
    world.addWeaponToPlayer(world.getTurn(), w.getWeaponName());
    out.append("Weapons removed from ").append(room.getName())
            .append(" and added to the the player\n");
  }
}
