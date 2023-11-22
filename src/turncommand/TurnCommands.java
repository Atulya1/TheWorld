package turncommand;

import java.io.IOException;
import player.GameOverException;
import world.World;

/**
 * Turn Commands Interface represents a turn.
 */
public interface TurnCommands {
  /**
   * Starting point for the controller.
   *
   * @param m the model to use
   * @throws IllegalArgumentException if an invalid model is provided
   */
  void go(World m) throws IllegalArgumentException, IOException, GameOverException;

  /**
   * Starting point for the controller for automatic moves.
   *
   * @param world the model to use
   * @throws IllegalArgumentException if an invalid model is provided
   */
  void goAutomatic(World world) throws IllegalArgumentException, IOException, GameOverException;
}
