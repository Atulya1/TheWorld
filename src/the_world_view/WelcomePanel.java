package the_world_view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import the_world_view_controller.ControllerInterface;

/**
 * Welcome-Panel welcomes the players. It shows the credits and oprion to start the game.
 */
public class WelcomePanel extends JPanel implements PanelInterface {
  private ImageIcon backgroundImage;
  private final ControllerInterface controller;

  /**
   * constructor for the welcome panel.
   *
   * @param cardLayout  card layout.
   * @param parentPanel parent panel.
   * @param frame       frame.
   */
  public WelcomePanel(final CardLayout cardLayout, final JPanel parentPanel,
                      JFrame frame, ControllerInterface controller) {
    this.controller = controller;
    initComponents(cardLayout, parentPanel, frame);
  }

  /**
   * method to set background image.
   *
   * @param imagePath path of the image.
   */
  private void setBackgroundImage(String imagePath) {
    backgroundImage = new ImageIcon(imagePath);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (backgroundImage != null) {
      g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
  }

  /**
   * initialize the pannel.
   *
   * @param cardLayout  card layout.
   * @param parentPanel parent panel.
   * @param frame       frame.
   */
  private void initComponents(final CardLayout cardLayout,
                              final JPanel parentPanel, final JFrame frame) {
    setLayout(new BorderLayout());

    JButton startButton = new JButton("Start");
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showPopupMenu(startButton, cardLayout, parentPanel, frame);
      }
    });

    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(startButton);
    buttonPanel.add(quitButton);

    add(buttonPanel, BorderLayout.SOUTH);
    setBackgroundImage("src/the_world_view/image2.png"); // Set the path to your image file
  }

  /**
   * shows option to start the game with current specification or new specification.
   *
   * @param component   Component.
   * @param cardLayout  card layout.
   * @param parentPanel parent panel.
   * @param frame       frame.
   */
  private void showPopupMenu(Component component, final CardLayout cardLayout,
                             final JPanel parentPanel, final JFrame frame) {
    JPopupMenu popupMenu = new JPopupMenu();

    JMenuItem newGameCurrentWorld = new JMenuItem("Start New Game (Current World)");
    newGameCurrentWorld.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Starting new game "
                + "with current world specification");
        cardLayout.show(parentPanel, "Game Setup");
        frame.setTitle("Game Setup");
      }
    });

    JMenuItem newGameNewWorld = new JMenuItem("Start New Game (New World)");
    newGameNewWorld.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Starting new game "
                + "with new world specification");
        controller.restartGame();
        cardLayout.show(parentPanel, "Game Setup");
        frame.setTitle("Game Setup");
      }
    });

    popupMenu.add(newGameCurrentWorld);
    popupMenu.add(newGameNewWorld);
    popupMenu.show(component, 0, component.getHeight());
  }

  @Override
  public void refresh() {
  }
}
