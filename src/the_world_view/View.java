package the_world_view;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import the_world_view_controller.ControllerInterface;

/**
 * View class for the World game. Implements the view interface.
 */
public class View implements ViewInterface {

  private JFrame frame;
  private JPanel parentPanel;
  private PanelInterface welcomePanel;
  private PanelInterface setupPanel;
  private PanelInterface gamePanel;
  private ControllerInterface controller;

  @Override
  public void setupView(ControllerInterface controller) {
    this.controller = controller;
    frame = new JFrame("The World");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 1000);
    parentPanel = new JPanel(new CardLayout());
    welcomePanel = new WelcomePanel((CardLayout) parentPanel.getLayout(),
            parentPanel, frame, controller);
    SwingUtilities.invokeLater(() -> {
      parentPanel.add((JPanel) welcomePanel, "introduction");
      setupPanel = new SetupPanel((CardLayout) parentPanel.getLayout(),
              parentPanel, frame, controller);
      parentPanel.add((JPanel) setupPanel, "Game Setup");
      gamePanel = new GamePanel((CardLayout) parentPanel.getLayout(),
              parentPanel, frame, controller);
      parentPanel.add((JPanel) gamePanel, "The World");

      CardLayout cardLayout = (CardLayout) parentPanel.getLayout();
      cardLayout.show(parentPanel, "introduction");
      JScrollPane scrollPane = new JScrollPane(parentPanel);
      frame.add(scrollPane);
      frame.setVisible(true);
    });
  }

  @Override
  public void refresh() {
    gamePanel.refresh();
  }

  @Override
  public void showGameOverDialog(String message) {
    JOptionPane.showMessageDialog((JPanel) gamePanel, message,
            "Game Over", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void showDialog(String message) {
    JOptionPane.showMessageDialog((JPanel) gamePanel, message,
            "Dialog", JOptionPane.INFORMATION_MESSAGE);
  }
}