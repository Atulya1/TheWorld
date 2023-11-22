package world;

import java.io.IOException;

/**
 * Interface for World game controller.
 */
public interface WorldController {

  /**
   * play game using console.
   * @param world world object.
   * @throws IOException exception.
   */
  void playGame(World world) throws IOException;
}
