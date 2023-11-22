import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import world.WorldModel;

/**
 * Test class for display info test.
 */
public class DisplayInfoTest {

  Appendable out = System.out;
  Scanner scan = new Scanner(System.in);
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
   * Testing file parsing.
   */
  @Test(expected = RuntimeException.class)
  public void testIfFileParsing() {
    String inputFileWrong = "res/mansionError.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFileWrong))) {
      WorldModel world2 = new WorldModel();
      world2 = new WorldModel();
      world2 = world2.parseFile(reader);

      assertNotNull(world2);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


}
