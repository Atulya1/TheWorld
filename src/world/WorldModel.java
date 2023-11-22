package world;

import helper.Dfs;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import player.GameOverException;
import player.Player;
import player.PlayerImpl;
import room.Coordinate;
import room.Room;
import room.RoomType;
import targetcharacter.Character;
import targetcharacter.TargetCharacter;
import targetcharcterpet.Pet;
import targetcharcterpet.PetClass;
import weapon.Weapon;
import weapon.WeaponType;

/**
 * WorldModel is the implementation of the interface of the world.
 * It implements graphical representation, movement of the character,
 * and neighbors of spaces and their respective weapons.
 */
public class WorldModel implements World {

  private WorldDescription worldDescription;
  private List<Player> players;
  private int noOfRooms;
  private List<Room> rooms;
  private int noOfWeapons;
  private List<Weapon> weapons;
  private int currentRoomIndex;
  private boolean isGameOver;
  private Player currentTurn = null;
  private Character targetCharacter;
  private Pet targetCharacterPet;
  private List<Integer> dfs;
  private int currentIndexPetDfs = 0;
  private int currentIndexTargetCharacter = 0;
  private boolean hasPetMoved = false;

  private int turns = 0;

  /**
   * Constructor for WorldModel class.
   */
  public WorldModel() {
    rooms = new ArrayList<>();
    weapons = new ArrayList<>();
    currentRoomIndex = 0;
    players = new ArrayList<>();
    isGameOver = false;
  }

  /**
   * Description of the world.
   *
   * @return WorldDescription object.
   */
  private void setWorldDescription(WorldDescription wd) {
    this.worldDescription = wd;
  }


  /**
   * Stores character information.
   * stores PlayerImpl object.
   */
  private void setPlayer(PlayerImpl c) {
    this.players.add(c);
  }

  /**
   * stores number of rooms in the world.
   *
   * @param r in int
   */
  private void setNoOfRooms(int r) {
    this.noOfRooms = r;
  }

  /**
   * stores number of weapons in the world.
   *
   * @param w in int
   */
  private void setNoOfWeapons(int w) {
    this.noOfWeapons = w;
  }

  /**
   * returns the World Description.
   *
   * @return WorldDescription object.
   */
  @Override
  public WorldDescription getworldDescription() {
    return worldDescription;
  }

  /**
   * stores the list of Room objects.
   *
   * @param rooms List of Room object.
   */
  private void setRooms(List<Room> rooms) {
    this.rooms = rooms;
  }

  /**
   * stores the list of Weapon Objects.
   *
   * @param weapons List of Weapon Object.
   */
  private void setWeapons(List<Weapon> weapons) {
    this.weapons = weapons;
  }

  /**
   * PlayerImpl information.
   *
   * @return PlayerImpl object.
   */
  @Override
  public List<Player> getPlayer() {
    return players;
  }

  /**
   * get List of rooms.
   *
   * @return List of rooms.
   */
  @Override
  public List<Room> getRooms() {
    return rooms;
  }

  /**
   * get List of Weapons.
   *
   * @return list of weapons.
   */
  @Override
  public List<Weapon> getWeapons() {
    return weapons;
  }

  /**
   * returns no. of rooms.
   *
   * @return integer.
   */

  @Override
  public int getNoOfRooms() {
    return noOfRooms;
  }

  /**
   * returns the no. of weapons.
   *
   * @return integer.
   */

  @Override
  public int getNoOfWeapons() {
    return noOfWeapons;
  }

  /**
   * Checks if two rooms are neighbours.
   *
   * @param room1 : currentRoom whose neighbours are to be found.
   * @param room2 : Other rooms that will be compared with the input room.
   * @return : True or False value based on if two rooms are neighbours.
   */
  private boolean isNeighbor(Room room1, Room room2) {
    Coordinate upperLeft1 = room1.getUpperLeftCorner();
    Coordinate lowerRight1 = room1.getLowerRightCorner();
    Coordinate upperLeft2 = room2.getUpperLeftCorner();
    Coordinate lowerRight2 = room2.getLowerRightCorner();

    boolean shareTopWall = lowerRight1.getRow() == upperLeft2.getRow() - 1
            && upperLeft1.getColumn() <= lowerRight2.getColumn()
            && lowerRight1.getColumn() >= upperLeft2.getColumn();

    boolean shareBottomWall = upperLeft1.getRow() == lowerRight2.getRow() + 1
            && upperLeft1.getColumn() <= lowerRight2.getColumn()
            && lowerRight1.getColumn() >= upperLeft2.getColumn();

    boolean shareLeftWall = lowerRight1.getColumn() == upperLeft2.getColumn() - 1
            && upperLeft1.getRow() <= lowerRight2.getRow()
            && lowerRight1.getRow() >= upperLeft2.getRow();

    boolean shareRightWall = upperLeft1.getColumn() == lowerRight2.getColumn() + 1
            && upperLeft1.getRow() <= lowerRight2.getRow()
            && lowerRight1.getRow() >= upperLeft2.getRow();

    return shareTopWall || shareBottomWall || shareLeftWall || shareRightWall;
  }

  /**
   * Gets all the neighbours of a room.
   *
   * @param room : Room object whose neighbours are to be found.
   * @return : List of neighbours room objects.
   */
  private List<Room> findNeighbor(Room room) {
    List<Room> neighbor = new ArrayList<>();

    for (Room otherRoom : rooms) {
      if (otherRoom != room && isNeighbor(room, otherRoom)) {
        neighbor.add(otherRoom);
      }
    }
    return neighbor;
  }

  /**
   * overridden toString() method.
   *
   * @return String.
   */
  @Override
  public String toString() {
    return "WorldModel{"
            + "worldDescription=" + worldDescription
            + ", targetCharacter=" + this.getTargetCharacter()
            + ", location=" + this.getTargetCharacterLocation()
            + ", targetCharacter's pet=" + this.getTargetCharacterPet()
            + ", location=" + this.getTargetCharacterPetLocation()
            + ", noOfRooms=" + noOfRooms
            + ", rooms=" + rooms.toString()
            + ", noOfWeapons=" + noOfWeapons
            + ", weapons=" + weapons.toString()
            + '}';
  }

  /**
   * Parses the text file having information about the game.
   *
   * @param reader Readable interface implementation Object.
   * @return WorldModel object.
   * @throws IOException IOException.
   * @throws IllegalArgumentException IllegalArgumentException.
   * @throws FileNotFoundException FileNotFoundException.
   */
  @Override
  public WorldModel parseFile(BufferedReader reader) throws IOException, IllegalArgumentException,
          FileNotFoundException {
    try {
      String line = reader.readLine();

      // World description
      String worldDescription = line;
      String[] worldInfo = worldDescription.split(" ");
      int rows = Integer.parseInt(worldInfo[0]);
      int columns = Integer.parseInt(worldInfo[1]);
      String worldName = "";
      for (int i = 2; i < worldInfo.length; i++) {
        worldName = worldName + worldInfo[i] + " ";
      }
      worldName = worldName.substring(0, worldName.length() - 1);
      WorldDescription worldDesc = new WorldDescription(rows, columns, worldName);

      // Target character info
      String charInfo = reader.readLine();
      String[] characterInfo = charInfo.split(" ");
      String name = "";
      for (int i = 1; i < characterInfo.length; i++) {
        name = name + characterInfo[i] + " ";
      }
      name = name.substring(0, name.length() - 1);

      //Pet
      String pet = reader.readLine();

      // no of rooms
      int noOfRooms = Integer.parseInt(reader.readLine());
      List<Room> rooms = new ArrayList<>();

      // room info
      for (int i = 0; i < noOfRooms; i++) {
        String roomInfo = reader.readLine();
        ;
        String[] roomInformation = roomInfo.split(" ");
        int ulRow = Integer.parseInt(roomInformation[0]);
        int ulColumn = Integer.parseInt(roomInformation[1]);
        int lrRow = Integer.parseInt(roomInformation[2]);
        int lrColumn = Integer.parseInt(roomInformation[3]);
        String roomName = "";

        Coordinate upperLeftCoordinate = new Coordinate(ulRow, ulColumn, worldDesc);
        Coordinate lowerRightCoordinate = new Coordinate(lrRow, lrColumn, worldDesc);
        for (int j = 4; j < roomInformation.length; j++) {
          roomName = roomName + roomInformation[j] + " ";
        }
        roomName = roomName.substring(0, roomName.length() - 1);
        Room room = new RoomType(upperLeftCoordinate, lowerRightCoordinate, roomName, i);
        rooms.add(room);
      }
      rooms.get(0).updateTargetCharacterPresence(true);
      rooms.get(0).updatePetPresence(true);

      //no of weapons
      int noOfWeapons = Integer.parseInt(reader.readLine());
      List<Weapon> weapons = new ArrayList<>();

      //weapon info
      for (int i = 0; i < noOfWeapons; i++) {
        String weaponInfo = reader.readLine();
        String[] weaponInformation = weaponInfo.split(" ");
        int roomIndex = Integer.parseInt(weaponInformation[0]);
        int damageValue = Integer.parseInt(weaponInformation[1]);
        String weaponName = "";
        for (int j = 2; j < weaponInformation.length; j++) {
          weaponName = weaponName + weaponInformation[j] + " ";
        }
        weaponName = weaponName.substring(0, weaponName.length() - 1);
        Weapon weapon = new WeaponType(roomIndex, damageValue, weaponName);
        weapons.add(weapon);
      }

      if (noOfWeapons < 20 || noOfRooms < 20 || noOfRooms < noOfWeapons) {
        throw new IllegalArgumentException("World should consist of at least 20 rooms "
                + "and at least 20 weapons. Weapons shouldn't be more than rooms.");
      }
      int health = Integer.parseInt(characterInfo[0]);
      TargetCharacter targetCharacter = new TargetCharacter(name, health);
      Pet petName = new PetClass(pet);
      WorldModel world = new WorldModel();
      world.setWorldDescription(worldDesc);
      world.setNoOfRooms(noOfRooms);
      world.setNoOfWeapons(noOfWeapons);
      world.setRooms(rooms);
      world.addTargetCharacter(targetCharacter);
      world.addTargetCharacterPet(petName);
      world.setWeapons(weapons);
      for (Room room : rooms) {
        room.addNeighbor(world.findNeighbor(room));
      }
      world.getNeighbours();
      for (Room room : rooms) {
        room.getNeighbor().toString();
      }
      for (Weapon weapon : weapons) {
        Room room = rooms.get(weapon.getRoomIndex());
        room.addWeapons(weapon);
      }
      for (Room room : rooms) {
        room.getWeapons().toString();
      }
      reader.close();
      return world;
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Creates BufferedImage of the created world.
   *
   * @return : BufferedImage.
   */
  @Override
  public BufferedImage createWorldGraphicalImage(World world) {
    BufferedImage worldMapImage = createWorldGraphicalImageHelper(world);
    try {
      File outputFile = new File("res/world_map.png");
      ImageIO.write(worldMapImage, "png", outputFile);
      System.out.println("World map image saved successfully!");
    } catch (IOException e) {
      System.out.println("Error saving the world map image: " + e.getMessage());
    }
    return worldMapImage;
  }

  /**
   * Helper method to create Image.
   *
   * @param worldMap of type WorldModel.
   * @return BufferedImage.
   */
  private BufferedImage createWorldGraphicalImageHelper(World worldMap) {
    int rows = worldMap.getworldDescription().getRow();
    int columns = worldMap.getworldDescription().getColumn();
    int cellSize1 = 40;
    int cellSize = 30;

    int width = columns * cellSize1;
    int height = rows * cellSize1;

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = image.createGraphics();


    graphics.setColor(Color.cyan);
    graphics.fillRect(4, 4, width, height);


    Font font = new Font("Arial", Font.PLAIN, 3);
    FontRenderContext frc = graphics.getFontRenderContext();
    for (Room room : worldMap.getRooms()) {
      Coordinate upperLeft = room.getUpperLeftCorner();
      Coordinate lowerRight = room.getLowerRightCorner();
      int x1 = upperLeft.getColumn() * cellSize;
      int y1 = upperLeft.getRow() * cellSize;
      int x2 = lowerRight.getColumn() * cellSize + cellSize;
      int y2 = lowerRight.getRow() * cellSize + cellSize;

      graphics.setColor(Color.BLACK);
      graphics.drawRect(x1, y1, x2 - x1, y2 - y1);

      String name = room.getName();
      int textX = (x1 + x2) / 2;
      int textY = (y1 + y2) / 2;
      graphics.drawString(name, textX, textY);
    }

    graphics.dispose();
    return image;
  }

  /**
   * Gets the value of current room index value.
   *
   * @return : current room index value.
   */
  @Override
  public int getCurrentRoomIndex() {
    return currentRoomIndex;
  }

  /**
   * Move the targetCharacter to next roomIndex.
   */
  private void moveTargetCharacter() throws GameOverException {
    List<Room> rooms = getRooms();
    System.out.println(String.format("Target character moved to the space index: %d."
                    + "Name of the space is : %s",
            currentRoomIndex, rooms.get(currentRoomIndex).getName()));
    if (currentRoomIndex < getNoOfRooms() - 1) {
      currentRoomIndex++;
    } else if (currentRoomIndex == getNoOfRooms() - 1) {
      currentRoomIndex = 0;
    }
  }

  /**
   * Move the targetCharacter to next destinationRoomIndex.
   *
   * @param roomName : The last room that the target character wants to go to.
   */
  @Override
  public void moveTargetCharacter(String roomName) throws
          IllegalArgumentException, GameOverException {
    int destinationRoomIndex = 0;
    for (Room r : getRooms()) {
      if (r.getName().equals(roomName)) {
        destinationRoomIndex = getRooms().indexOf(r);
      }
    }
    if (destinationRoomIndex < 0 || destinationRoomIndex >= getNoOfRooms()) {
      throw new IllegalArgumentException("Invalid room index.");
    } else {
      while (getCurrentRoomIndex() != destinationRoomIndex) {
        moveTargetCharacter();
      }
      getTurn().setCurrentRoom(rooms.get(destinationRoomIndex));
      System.out.println("Target character moved to the destination.");
    }
  }

  /**
   * Displays the room information along with all the weapons in them.
   */
  @Override
  public String displayRoomInformation() {
    String str = "";
    for (Room room : getRooms()) {
      str = str.concat("Name : " + room.getName()).concat("\n");
      str = str.concat("Neighbor ---\n");
      List<Room> neighbours = room.getNeighbor();
      if (neighbours.size() > 0) {
        for (Room neighbour : neighbours) {
          str = str.concat(neighbour.getName()).concat("\n");
        }
      } else {
        str = str.concat(room.getName()).concat(" has no neighbours.\n");
      }
      str = str.concat("Weapons --- \n");
      List<Weapon> weapons = room.getWeapons();
      if (weapons.size() > 0) {
        for (Weapon weapon : weapons) {
          str = str.concat(weapon.getWeaponName()).concat("\n");
          str = str.concat("Damage value of ").concat(weapon.getWeaponName())
                  .concat(" is ").concat("" + weapon.getDamageValue())
                  .concat("\n");
        }
      } else {
        str = str.concat(room.getName()).concat(" has no weapons.").concat("\n");
      }
    }
    return str;
  }

  /**
   * add player.
   * @param player object.
   */
  @Override
  public void addPlayer(Player player) {
    players.add(player);
  }

  private void addTargetCharacter(Character targetCharacter) {
    this.targetCharacter = targetCharacter;
  }

  private void addTargetCharacterPet(Pet targetCharacterPet) {
    this.targetCharacterPet = targetCharacterPet;
  }

  /**
   * remove player.
   * @param player player object.
   */
  @Override
  public void removePlayer(String player) {
  }

  /**
   * display player info.
   * @param player object.
   * @return String.
   */
  @Override
  public String displayPlayerInfo(Player player) {
    return null;
  }

  /**
   * returns currentWorld status.
   * @param world object.
   * @return string.
   */
  @Override
  public String getCurrentWorldStat(World world) {
    return null;
  }

  /**
   * get winner.
   * @return string.
   */
  @Override
  public String getWinner() {
    return null;
  }

  /**
   * isGameOver.
   * @return boolean.
   */
  @Override
  public boolean isGameOver() {
    return isGameOver;
  }

  /**
   * returns turn of the player.
   * @return Player obejct.
   */
  @Override
  public Player getTurn() {
    if (currentTurn == null) {
      return players.get(0);
    } else {
      return currentTurn;
    }
  }

  /**
   * overridden equals method.
   * @param o object of weapon type.
   * @return boolean.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WorldModel world = (WorldModel) o;
    return noOfRooms == world.noOfRooms && noOfWeapons == world.noOfWeapons
            && currentRoomIndex == world.currentRoomIndex && Objects.equals(worldDescription,
            world.worldDescription) && Objects.equals(players, world.players)
            && Objects.equals(rooms, world.rooms) && Objects.equals(weapons, world.weapons);
  }

  /**
   * overridden hashcode method.
   * @return int.
   */
  @Override
  public int hashCode() {
    return Objects.hash(worldDescription, players, noOfRooms, rooms, noOfWeapons,
            weapons, currentRoomIndex);
  }

  /**
   * player Name exists.
   * @param name String.
   * @return boolean.
   */
  @Override
  public boolean playerNameExists(String name) {
    for (Player p : players) {
      if (p.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * display single room information.
   * @param roomName room name in string.
   * @return String.
   */
  @Override
  public String displaySingleRoomInfomation(String roomName, World w) {
    Room r = null;
    for (Room room : rooms) {
      if (room.getName().equals(roomName)) {
        r = room;
      }
    }
    if (r == null) {
      return "No room available with name :".concat(roomName);
    }
    return r.displaySingleRoomInformation(r, w);
  }

  /**
   * add weapon to the player.
   * @param player current player object.
   * @param weapon weapon to add.
   */
  public void addWeaponToPlayer(Player player, String weapon) {
    for (Weapon w : weapons) {
      if (w.getWeaponName().equals(weapon)) {
        player.addWeapon(w);
        player.reduceWeaponCount();
      }
    }
  }

  /**
   * changes the turn of the player.
   */
  @Override
  public void nextTurn() {
    Player p = getTurn();
    int index = players.indexOf(p);
    if (index == players.size() - 1) {
      p = players.get(0);
    } else {
      p = players.get(index + 1);
    }
    currentTurn = p;
    this.moveTcharacter();
    if (!this.hasPetMoved) {
      this.wanderingPet();
    }
    this.hasPetMoved = false;
    turns = turns - 1;
  }

  @Override
  public void nextT() throws GameOverException {
    Player p = getTurn();
    int index = players.indexOf(p);
    if (index == players.size() - 1) {
      p = players.get(0);
    } else {
      p = players.get(index + 1);
    }
    currentTurn = p;
    this.moveTcharacter();
    if (!this.hasPetMoved) {
      this.wanderingPet();
    }
    this.hasPetMoved = false;
    turns = turns - 1;
    if (turns < 0) {
      throw new GameOverException("Game Over : Turns exhausted");
    }
  }


  /**
   * get target character's description.
   * @return String.
   */
  @Override
  public String getTargetCharacter() {

    return this.targetCharacter.characterDescription();
  }

  /**
   * get target character's pet description.
   * @return String.
   */
  @Override
  public String getTargetCharacterPet() {
    return this.targetCharacterPet.getPetDescription();
  }

  /**
   * move pet.
   */
  @Override
  public void movePet(int roomNumber) {
    Room room = this.getTcharacterPetLocation();
    room.updatePetPresence(false);
    Room roomToMove = this.getRooms().get(roomNumber);
    roomToMove.updatePetPresence(true);
  }

  /**
   * wandering pet.
   */
  public void wanderingPet() {
    Room room = this.getTcharacterPetLocation();
    room.updatePetPresence(false);
    int index = 0;
    int i = 0;
    for (Room r : getRooms()) {
      if (r.getName().equals(room.getName())) {
        index = i;
        break;
      }
      i++;
    }
    int currentDdsIndex = dfs.indexOf(index);
    Room roomToMove = null;
    if (currentDdsIndex + 1 == dfs.size()) {
      currentDdsIndex = 0;
      roomToMove = getRooms().get(currentDdsIndex);
    } else {
      roomToMove = getRooms().get(currentDdsIndex + 1);
    }
    roomToMove.updatePetPresence(true);
  }

  /**
   * move target character.
   */
  @Override
  public void moveTcharacter() {
    rooms.get(currentIndexTargetCharacter).updateTargetCharacterPresence(false);
    if (currentIndexTargetCharacter == noOfRooms - 1) {
      currentIndexTargetCharacter = 0;
      rooms.get(currentIndexTargetCharacter).updateTargetCharacterPresence(true);
    } else {
      rooms.get(currentIndexTargetCharacter + 1).updateTargetCharacterPresence(true);
      currentIndexTargetCharacter++;
    }
  }

  /**
   * move player.
   * @param roomName name of the room.
   */
  @Override
  public void movePlayer(String roomName) {
    int destinationRoomIndex = 0;
    Room destinationRoom = null;
    for (Room r : getRooms()) {
      if (r.getName().equals(roomName)) {
        destinationRoomIndex = getRooms().indexOf(r);
        destinationRoom = r;
      }
    }
    if (destinationRoomIndex < 0 || destinationRoomIndex >= getNoOfRooms()) {
      throw new IllegalArgumentException("Invalid room index.");
    } else {
      Room currentRoom = getTurn().getCurrentRoom();
      currentRoom.removePlayer(getTurn().getName());
      destinationRoom.addPlayer(getTurn());
    }
    getTurn().setCurrentRoom(rooms.get(destinationRoomIndex));
  }

  /**
   * get Target Character's Location.
   * @return room name.
   */
  @Override
  public String getTargetCharacterLocation() {
    return getTcharacterLocation().getName();
  }

  /**
   * get Target Character's Location.
   * @return Room.
   */
  private Room getTcharacterLocation() {
    Room targetCharacterLocation = null;
    for (Room room : rooms) {
      if (room.hasTargetCharacter()) {
        targetCharacterLocation = room;
      }
    }
    return targetCharacterLocation;
  }

  /**
   * get Target Character's Pet Location.
   * @return room name.
   */
  @Override
  public String getTargetCharacterPetLocation() {
    return getTcharacterPetLocation().getName();
  }

  /**
   * get Target Character's Pet Location.
   * @return Room.
   */
  private Room getTcharacterPetLocation() {
    Room targetCharacterLocationPet = null;
    for (Room room : rooms) {
      if (room.hasPet()) {
        targetCharacterLocationPet = room;
      }
    }
    return targetCharacterLocationPet;
  }

  /**
   * Get the dfs of all the neighbours.
   * Used to move the pet.
   */
  @Override
  public void getNeighbours() {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    for (Room room : rooms) {
      List<Integer> l = new ArrayList<>();
      List<Room> r = room.getNeighbor();
      for (Room n : r) {
        l.add(n.getIndex());
      }
      graph.put(room.getIndex(), l);
    }
    Dfs ob = new Dfs();
    this.dfs = ob.performDfs(graph);
  }

  /**
   * helper method to decrease the health when moving.
   *
   * @param weapon name of the weapon.
   */
  @Override
  public void decreaseHealth(String weapon) throws IllegalArgumentException, GameOverException {
    int damageValue = 0;
    if ("Poke".equals(weapon)) {
      damageValue = 1;
    }
    List<Weapon> weaponsList = getWeapons();
    for (Weapon w : weaponsList) {
      if (w.getWeaponName().equals(weapon)) {
        damageValue = w.getDamageValue();
        weapons.remove(w);
        getTurn().removeWeapon(w);
        break;
      }
    }
    try {
      targetCharacter.decreaseHealth(damageValue);
    } catch (GameOverException e) {
      throw new GameOverException("Game over. Winner is " + this.getTurn().getName());
    }
  }

  /**
   * check if the pet has been moved.
   */
  @Override
  public boolean hasPetMoved(boolean petMoved) {

    return hasPetMoved = petMoved;
  }

  @Override
  public void setNoOfTurns(int turns) {
    this.turns = turns;
  }

  @Override
  public int getNoOfTurns() {
    return turns;
  }

  @Override
  public int getCurrentIndexTargetCharacter() {
    return currentIndexTargetCharacter;
  }

  @Override
  public int getHealth() {
    return targetCharacter.getHealth();
  }
}
