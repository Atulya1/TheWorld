package helper;

import java.awt.image.BufferedImage;

/**
 * comapres two images in BufferedImage format.
 */
public class ImageCompare {
  /**
   * compares two png images.
   * @param img1 img1.
   * @param img2 img2.
   * @return boolean.
   */
  public boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
    if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
      for (int x = 0; x < img1.getWidth(); x++) {
        for (int y = 0; y < img1.getHeight(); y++) {
          if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
            return false;
          }
        }
      }
    } else {
      return false;
    }
    return true;
  }
}
