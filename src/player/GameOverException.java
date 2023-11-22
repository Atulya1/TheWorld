package player;

/**
 * custom exception for when the game gets over.
 */
public class GameOverException extends Exception {

  /**
   * GameOverException method.
   * @param s string.
   */
  public GameOverException(String s) {
    super(s);
  }
}
