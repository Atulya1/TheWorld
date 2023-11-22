package gamesetup;

import java.io.IOException;
import world.World;

/**
 * GameSetup Interface.
 */
public interface GameSetup {
  /**
   * Starting point for the controller.
   *
   * @param m the model to use
   * @throws IllegalArgumentException if an invalid model is provided
   */
  void setup(World m) throws IllegalArgumentException, IOException;
}
