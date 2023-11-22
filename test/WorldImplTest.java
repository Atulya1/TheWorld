
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import helper.ImageCompare;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;
import room.Room;
import world.WorldModel;

/**
 * Represents a class to test World Implementation.
 */
public class WorldImplTest {
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

  /**
   * Testing illegal world description.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWorldDescription() {
    String inputFile = "res/mansionError.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
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

  /**
   * Testing file parsing.
   */
  @Test
  public void testParsing() {
    String inputFile = "res/mansion.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      WorldModel world2 = new WorldModel();
      world2 = new WorldModel();
      world2 = world2.parseFile(reader);
      assertNotNull(world2);
      System.out.println(world2);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Testing world details parsing errors.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testParsingErrors() {
    String inputFile = "res/mansionError3.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
      WorldModel world2 = new WorldModel();
      world2 = new WorldModel();
      world2 = world2.parseFile(reader);
      assertNotNull(world2);
      System.out.println(world2);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Testing invalid room and weapons while parsing.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRoomAndWeapon() {
    String inputFile = "res/mansionError2.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
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

  /**
   * Testing invalid world description.
   */
  @Test
  public void testInValidWorldDescription2() {
    assertEquals(36, world.getworldDescription().getRow());
    assertEquals(30, world.getworldDescription().getColumn());
    assertNotEquals("Doctor Atul's Mansion", world.getworldDescription().getWorldName());
  }

  /**
   * Testing invalid room description.
   */
  @Test
  public void testInValidRoomDescription() {
    assertNotEquals(23, world.getRooms().get(0).getUpperLeftCorner().getRow());
    assertEquals(19, world.getRooms().get(0).getUpperLeftCorner().getColumn());
    assertEquals(23, world.getRooms().get(0).getLowerRightCorner().getRow());
    assertEquals(26, world.getRooms().get(0).getLowerRightCorner().getColumn());
    assertEquals("Armory", world.getRooms().get(0).getName());
  }

  /**
   * Testing invalid weapon description.
   */
  @Test
  public void testInValidWeaponDescription() {
    assertNotEquals(9, world.getWeapons().get(0).getRoomIndex());
    assertEquals(3, world.getWeapons().get(0).getDamageValue());
    assertEquals("Crepe Pan", world.getWeapons().get(0).getWeaponName());
  }

  /**
   * Testing room having zero neighbors.
   */
  @Test(expected = NullPointerException.class)
  public void zeroNeighborTest() {
    List<Room> room = world.getRooms();
    Room noNeighborRoom = null;
    for (Room r : room) {
      if (r.getNeighbor().size() == 0) {
        noNeighborRoom = r;
      }
    }
    System.out.println(noNeighborRoom.getName());
  }

  /**
   * Testing room having one neighbor.
   */
  @Test
  public void oneNeighborTest() {
    String room = world.getRooms().get(6).getName();
    List<String> neighbor = new ArrayList<>();
    for (Room r : world.getRooms().get(6).getNeighbor()) {
      neighbor.add(r.getName());
    }
    assertEquals("Green House", room);
    assertEquals("[Hedge Maze]", neighbor.toString());
  }

  /**
   * Testing description of room having one neighbor.
   */
  @Test
  public void oneNeighborDescTest() {
    Room room = world.getRooms().get(6);
    String roomInformation = "Name : Green House\n"
            + "Neighbor ---\n"
            + "Hedge Maze\n"
            + "Weapons --- \n"
            + "Trowel\n"
            + "Damage value of Trowel is 2\n"
            + "Pinking Shears\n"
            + "Damage value of Pinking Shears is 2\n"
            + "Players --- \n";
    assertEquals(roomInformation, room.displaySingleRoomInformation(room, world));
  }

  /**
   * Testing multiple neighbors.
   */
  @Test
  public void multipleNeighborTest() {
    String room = world.getRooms().get(0).getName();
    List<String> neighbor = new ArrayList<>();
    for (Room r : world.getRooms().get(0).getNeighbor()) {
      neighbor.add(r.getName());
    }
    assertEquals("Armory", room);
    assertEquals("[Billiard Room, Dining Hall, Drawing Room]", neighbor.toString());
  }

  /**
   * testing one item description.
   */
  @Test
  public void oneItemDescTest() {
    List<Room> room = world.getRooms();
    Room oneWeapon = null;
    for (Room r : room) {
      if (r.getWeapons().size() == 1) {
        oneWeapon = r;
      }
    }
    String oneWeaponRoomDesc = "Name : Servants' Quarters\n"
            + "Neighbor ---\n"
            + "Lancaster Room\n"
            + "Lilac Room\n"
            + "Parlor\n"
            + "Weapons --- \n"
            + "Broom Stick\n"
            + "Damage value of Broom Stick is 2\n"
            + "Players --- \n";
    assertEquals(oneWeaponRoomDesc, oneWeapon.displaySingleRoomInformation(oneWeapon, world));
  }

  /**
   * Testing no item description.
   */
  @Test
  public void noItemDescTest() {
    List<Room> room = world.getRooms();
    Room zeroWeapon = null;
    for (Room r : room) {
      if (r.getWeapons().size() == 0) {
        zeroWeapon = r;
      }
    }
    String zeroWeaponRoomDesc = "Name : Winter Garden\n"
            + "Neighbor ---\n"
            + "Carriage House\n"
            + "Piazza\n"
            + "Weapons --- \n"
            + "Winter Garden has no weapons.\n"
            + "Players --- \n";
    assertEquals(zeroWeaponRoomDesc, zeroWeapon.displaySingleRoomInformation(zeroWeapon, world));
  }

  /**
   * Target PlayerImpl Starting Position Test.
   */
  @Test
  public void targetCharacterStartingPositionTest() {
    assertEquals(0, world.getCurrentRoomIndex());
  }

  /**
   * verifying correctness of the world image.
   *
   * @throws IOException IOException.
   */
  @Test
  public void verifyImageWorld() throws IOException {
    BufferedImage img = ImageIO.read(new File("res/world_map.png"));
    BufferedImage img2 = ImageIO.read(new File("res/world_map_test.png"));
    ImageCompare imageCompare = new ImageCompare();
    assertEquals(true, imageCompare.bufferedImagesEqual(img, img2));
  }

  @Test
  public void testMoveTargetCharacter() {
    for (int i = 0; i < world.getNoOfRooms(); i++) {
      System.out.println(world.getTargetCharacterLocation());
      world.moveTcharacter();
    }
    System.out.println(world.getTargetCharacterLocation());
    world.moveTcharacter();
    System.out.println(world.getTargetCharacterLocation());
  }
}
