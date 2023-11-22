import static org.junit.Assert.assertNotNull;

import gamesetup.CreatePlayer;
import gamesetup.GameSetup;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import player.GameOverException;
import turncommand.DisplayInfo;
import turncommand.TurnCommands;
import world.WorldModel;

/**
 * Test file to test creating players.
 */
public class CreatePlayerTest {

  Appendable out = System.out;
  Scanner scan = new Scanner(System.in);
  private GameSetup gs;
  private WorldModel world;

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
  }

  /**
   * Set method to initialize WorldModel object for running the tests.
   */
  @Before
  public void setUpCreatePlayer() throws GameOverException, IOException {
    gs = new CreatePlayer(out, scan);
  }

  /**
   * Testing IOException.
   */
  @Test(expected = IOException.class)
  public void testingIoException() throws GameOverException, IOException {
    gs.setup(world);
  }

  /**
   * Testing GameOverException.
   */
  @Test(expected = GameOverException.class)
  public void testingGameOverException() throws GameOverException, IOException {
    gs.setup(world);
  }
}
