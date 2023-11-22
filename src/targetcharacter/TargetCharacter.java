package targetcharacter;

import player.GameOverException;

/**
 * implementation of Character interface.
 */
public class TargetCharacter implements Character {

  private final String name;
  private int health;

  /**
   * Constructor for creating character class.
   * @param name name of the character.
   * @param health health.
   */
  public TargetCharacter(String name, int health) {
    this.name = name;
    this.health = health;
  }

  /**
   * name.
   * @return name.
   */
  @Override
  public String getCharacterName() {
    return name;
  }

  /**
   * health.
   * @return health.
   */
  @Override
  public int getHealth() {
    return health;
  }

  /**
   * description.
   * @return description.
   */
  @Override
  public String characterDescription() {
    return "[Target Character Name : " + name + ", Current Health : "
            + "" + health + "]";
  }

  /**
   * decrease in health when character moves.
   * @param decreaseHealth decrease in health when character moves.
   * @throws GameOverException Custom exception when health decreases below zero.
   */
  @Override
  public void decreaseHealth(int decreaseHealth) throws GameOverException {
    health = health - decreaseHealth;
    if (health <= 0) {
      throw new GameOverException("Game Over");
    }
  }
}
