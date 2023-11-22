package world;

import gamesetup.CreatePlayer;
import gamesetup.GameSetup;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import player.GameOverException;
import player.PlayerType;
import turncommand.Attack;
import turncommand.Exit;
import turncommand.LookAround;
import turncommand.Move;
import turncommand.MovePet;
import turncommand.Pick;
import turncommand.ShowT;
import turncommand.TurnCommands;

/**
 * This class is an implementation of WorldController interface.
 * It takes input from the user and performs operation in the game.
 */
public class WorldConsoleController implements WorldController {
  private final Appendable out;
  private final Scanner scan;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public WorldConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);
  }

  @Override
  public void playGame(World world) throws IOException {
    try {
      out.append("Press q or quit to quit the game.\n");
      out.append("Press 1 to add a player: \n");
      while (!world.isGameOver()) {
        String s = scan.nextLine();
        try {
          switch (s) {
            case "q":
              return;
            case "quite":
              return;
            case "1":
              GameSetup g = new CreatePlayer(out, scan);
              g.setup(world);
              out.append("Press 1 to add another player:").append("\n");
              out.append("Press 2 to continue with the game").append("\n");
              break;
            case "2":
              playTheGame(world);
              break;
            default:
              out.append(String.format("Unknown command %s", s));
              break;
          }
        } catch (InputMismatchException ime) {
          out.append("Bad length to ").append(s).append("\n");
        } catch (GameOverException e) {
          out.append("Game is over\n");
          out.append("Player Details----\n");
          out.append(world.getTurn().playerDescription()).append("\n");
          out.append("Space Details----\n");
          out.append(world.displayRoomInformation());
        }
      }
      if (world.isGameOver()) {
        out.append("Game is over\n");
        out.append("Player Details----\n");
        out.append(world.getTurn().playerDescription()).append("\n");
        out.append("Space Details----\n");
        out.append(world.displayRoomInformation());
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private void playTheGame(World world) throws IOException, GameOverException {
    out.append("Enter maximum number of turns allowed : \n");
    String turns = scan.nextLine();
    int turn = Integer.parseInt(turns);
    while (!world.isGameOver() && turn > 0) {
      out.append(world.getTurn().getName()).append("'s turn.\n");
      out.append("Current Location : ").append(world.getTurn()
              .getCurrentRoom().getName()).append("\n");
      if (world.getTurn().getPlayerType() == PlayerType.HUMAN) {
        String s = "";
        boolean moved = false;
        while (!("7").equals(s) && !moved) {
          out.append("Press the following to perform action : ").append("\n");
          out.append("Press 1 move player : \n");
          out.append("Press 2 pick weapon : \n");
          out.append("Press 3 look around : \n");
          out.append("Press 4 to see Target Character's information : \n");
          out.append("Press 5 to Attack : \n");
          out.append("Press 6 to Move Pet : \n");
          out.append("Press 7 exit : \n");
          s = scan.nextLine();
          TurnCommands tc = null;
          switch (s) {
            case "q":
              return;
            case "quite":
              return;
            case "1":
              moved = true;
              tc = new Move(out, scan);
              break;
            case "2":
              tc = new Pick(out, scan);
              break;
            case "3":
              tc = new LookAround(out, scan);
              break;
            case "4":
              tc = new ShowT(out, scan);
              break;
            case "5":
              tc = new Attack(out, scan);
              break;
            case "6":
              tc = new MovePet(out, scan);
              break;
            case "7":
              out.append("Moving to other player's turn \n");
              break;
            default:
              out.append(String.format("Unknown command %s", s));
              break;
          }
          if (tc != null) {
            try {
              tc.go(world);
            } catch (GameOverException e) {
              out.append("Game over!!");
              out.append(world.getCurrentWorldStat(world).toString()).append("\n");
            }
          }
        }
      } else {
        out.append(world.getTurn().getName()).append(" is a Computer Player.").append("\n");
        out.append("Running the game automatically....").append("\n");
        Map<String, TurnCommands> map = new HashMap<>();
        map.put("move", new Move(out, scan));
        map.put("lookAround", new LookAround(out, scan));
        map.put("showT", new ShowT(out, scan));
        map.put("pick", new Pick(out, scan));
        map.put("attack", new Attack(out, scan));
        map.put("movePet", new MovePet(out, scan));
        map.put("exit", new Exit(out, scan));
        List<String> commands = new ArrayList<>();
        commands.add("move");
        commands.add("lookAround");
        commands.add("showT");
        commands.add("pick");
        commands.add("attack");
        commands.add("movePet");
        commands.add("exit");
        String c = "";
        boolean moved = false;
        while (!("exit").equals(c) && !moved) {
          c = commands.get(new Random().nextInt(commands.size()));
          out.append(c).append(" command selected\n");
          TurnCommands tc = map.get(c);
          tc.goAutomatic(world);
          if (("move").equals(c)) {
            moved = true;
          }
        }
      }
      world.nextTurn();
      turn = turn - 1;
    }
    if (world.isGameOver()) {
      out.append("Game over!!!\n");
      out.append(world.toString()).append("\n");
    }
    if (turn == 0) {
      out.append("Turns exhausted. Game Over.\n");
      out.append(world.toString()).append("\n");
    }
  }
}
