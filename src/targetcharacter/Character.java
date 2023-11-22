package targetcharacter;

import player.GameOverException;

/**
 * represents the Target character class.
 */
public interface Character {

  /**
   * get Character Name.
   * @return name.
   */
  String getCharacterName();

  /**
   * get health.
   * @return health.
   */
  int getHealth();

  /**
   * get character Description.
   * @return description.
   */
  String characterDescription();

  /**
   * descrease health.
   * @param decreaseHealth damage value.
   * @throws GameOverException when game gets over.
   */
  void decreaseHealth(int decreaseHealth) throws GameOverException;
}
