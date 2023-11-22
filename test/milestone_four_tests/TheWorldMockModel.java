package milestone_four_tests;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import player.GameOverException;
import player.Player;
import room.Room;
import weapon.Weapon;
import world.World;
import world.WorldDescription;
import world.WorldModel;

/**
 * Mock class for Model.
 */
public class TheWorldMockModel implements World {
  private final StringBuilder log;

  /**
   * constructor.
   * @param log log.
   */
  public TheWorldMockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addPlayer(Player player) {
    log.append(player.getName());
  }

  @Override
  public WorldDescription getworldDescription() {
    return null;
  }

  @Override
  public List<Player> getPlayer() {
    return null;
  }

  @Override
  public List<Room> getRooms() {
    return null;
  }

  @Override
  public List<Weapon> getWeapons() {
    return null;
  }

  @Override
  public int getNoOfRooms() {
    return 0;
  }

  @Override
  public int getNoOfWeapons() {
    return 0;
  }

  @Override
  public WorldModel parseFile(BufferedReader reader) throws
          IOException, IllegalArgumentException, FileNotFoundException {
    return null;
  }

  @Override
  public BufferedImage createWorldGraphicalImage(World world) {
    return null;
  }

  @Override
  public void moveTargetCharacter(String roomName) throws GameOverException {
    log.append(roomName);
  }

  @Override
  public int getCurrentRoomIndex() {
    return 0;
  }

  @Override
  public String displayRoomInformation() {
    return null;
  }

  @Override
  public void removePlayer(String player) {
    log.append(player);
  }

  @Override
  public String displayPlayerInfo(Player player) {
    return null;
  }

  @Override
  public String getCurrentWorldStat(World world) {
    return null;
  }

  @Override
  public String getWinner() {
    return null;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public Player getTurn() {
    return null;
  }

  @Override
  public boolean playerNameExists(String name) {
    return false;
  }

  @Override
  public String displaySingleRoomInfomation(String roomName, World w) {
    return null;
  }

  @Override
  public void addWeaponToPlayer(Player turn, String weapon) {
    log.append(turn.getName());
    log.append(weapon);
  }

  @Override
  public void nextTurn() {

  }

  @Override
  public void nextT() throws GameOverException {

  }

  @Override
  public String getTargetCharacter() {
    return null;
  }

  @Override
  public String getTargetCharacterPet() {
    return null;
  }

  @Override
  public String getTargetCharacterPetLocation() {
    return null;
  }

  @Override
  public String getTargetCharacterLocation() {
    return null;
  }

  @Override
  public void getNeighbours() {

  }

  @Override
  public void movePet(int roomNumber) {

  }

  @Override
  public void wanderingPet() {

  }

  @Override
  public void moveTcharacter() {

  }

  @Override
  public void movePlayer(String roomName) {
    log.append(roomName);
  }

  @Override
  public void decreaseHealth(String weapon) throws IllegalArgumentException, GameOverException {
    log.append(weapon);
  }

  @Override
  public boolean hasPetMoved(boolean hasMoved) {
    return false;
  }

  @Override
  public void setNoOfTurns(int turns) {
    log.append(turns);
  }

  @Override
  public int getNoOfTurns() {
    return 0;
  }

  @Override
  public int getCurrentIndexTargetCharacter() {
    return 0;
  }

  @Override
  public int getHealth() {
    return 0;
  }
}
