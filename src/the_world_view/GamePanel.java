package the_world_view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import player.Player;
import room.Coordinate;
import room.Room;
import the_world_view_controller.ControllerInterface;
import weapon.Weapon;

/**
 * Panel to play the game on. It implements Panel interface and JPanel class.
 */
public class GamePanel extends JPanel implements PanelInterface {

  private static final int temp_size = 22;
  private final ControllerInterface controller;
  private final TheWorldPanel gamePanel;

  /**
   * Constructor to set up the game panel.
   * @param cardLayout card layout.
   * @param parentPanel parent panel.
   * @param frame frame.
   * @param controller controller.
   */
  public GamePanel(final CardLayout cardLayout, final JPanel parentPanel,
                       final JFrame frame, ControllerInterface controller) {
    this.controller = controller;
    this.gamePanel = new TheWorldPanel();
    initComponents(cardLayout, parentPanel, frame);
    this.bindKeys();
  }

  /**
   * helper method to initialize the panel.
   * @param cardLayout card layout.
   * @param parentPanel parent panel.
   * @param frame frame.
   */
  private void initComponents(final CardLayout cardLayout,
                              final JPanel parentPanel, final JFrame frame) {
    setLayout(new BorderLayout());
    gamePanel.repaint();
    gamePanel.revalidate();
    add(gamePanel, BorderLayout.CENTER);
    JButton quitButton = new JButton("Quit");
    quitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(parentPanel, "introduction");
        frame.setTitle("Introduction Panel");
      }
    });
    add(quitButton, BorderLayout.SOUTH);
  }

  @Override
  public void refresh() {
    gamePanel.repaint();
  }

  /**
   * helper method to add key press functionality to the panel.
   */
  private void bindKeys() {
    InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap actionMap = getActionMap();

    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "showMoveDialog");
    actionMap.put("showMoveDialog", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.moveAction();
      }
    });

    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "showAttackDialog");
    actionMap.put("showAttackDialog", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.attackAction();
      }
    });

    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0), "showLookAroundDialog");
    actionMap.put("showLookAroundDialog", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.lookAroundAction();
      }
    });

    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "showPickDialog");
    actionMap.put("showPickDialog", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.pickAction();
      }
    });
    inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), "showQuitDialog");
    actionMap.put("showQuitDialog", new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.quitAction();
      }
    });
  }

  /**
   * The inner class representing the game board panel for the World game.
   * This panel is responsible for rendering the game board.
   */
  private class TheWorldPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      // place target character on the board
      Room r = controller.getTargetCharacterCurrentRoom();
      Coordinate ul = r.getUpperLeftCorner();
      Coordinate lr = r.getLowerRightCorner();
      int x1 = ul.getColumn() * temp_size + 5;
      int y1 = ul.getRow() * temp_size + 5;
      int x2 = lr.getColumn() * temp_size + temp_size + 5;
      int y2 = lr.getRow() * temp_size + temp_size + 5;
      int textX = ((x1 + x2 - 80)) / 2;
      int textY = ((y1 + y2)) / 2;
      ImageIcon icon = new ImageIcon("src/the_world_view/target.png");
      Image resizedImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
      ImageIcon resizedIcon = new ImageIcon(resizedImage);
      g.drawImage(resizedIcon.getImage(), textX, textY, null);
      int x = 15;

      //place players on the board
      for (Player player : controller.getPlayers()) {
        Room room = player.getCurrentRoom();
        Coordinate ulp = room.getUpperLeftCorner();
        Coordinate lrp = room.getLowerRightCorner();
        int x1p = ulp.getColumn() * temp_size + 5;
        int y1p = ulp.getRow() * temp_size + 5;
        int x2p = lrp.getColumn() * temp_size + temp_size + 5;
        int y2p = lrp.getRow() * temp_size + temp_size + 5;
        int textXp = (((x1p + x2p - 80)) / 2);
        int textYp = (y1p) + x;
        g.setColor(Color.RED);
        g.drawString(player.getName(), textXp, textYp);
        x = x + 15;
      }

      //draw info board
      int x1Info = 740;
      int y1Info = 300;
      int x2Info = 970;
      int y2Info = 800;
      drawInformationBoard(g, x1Info, y1Info, x2Info, y2Info);

      //draw instructions board
      int x1Ins = 740;
      int y1Ins = 20;
      int x2Ins = 970;
      int y2Ins = 250;
      drawInstructionBoard(g, x1Ins, y1Ins, x2Ins, y2Ins);

      //draw spaces
      for (Room room : controller.getRooms()) {
        Coordinate upperLeft = room.getUpperLeftCorner();
        Coordinate lowerRight = room.getLowerRightCorner();
        int x11 = upperLeft.getColumn() * temp_size + 5;
        int y11 = upperLeft.getRow() * temp_size + 5;
        int x22 = lowerRight.getColumn() * temp_size + temp_size + 5;
        int y22 = lowerRight.getRow() * temp_size + temp_size + 5;
        g.setColor(Color.BLACK);
        g.drawRect(x11, y11, x22 - x11, y22 - y11);
        String name = room.getName();
        int tx = ((x11 + x22 - 80)) / 2;
        int ty = ((y11 + y22)) / 2;
        g.drawString(name, tx, ty);
      }
    }

    /**
     * This method draws instructions on the game panel.
     * @param g Graphics
     * @param x111 int
     * @param y111 int
     * @param x222 int
     * @param y222 int
     */
    private void drawInstructionBoard(Graphics g, int x111, int y111, int x222, int y222) {
      g.setColor(Color.BLACK);
      g.drawRect(x111, y111, x222 - x111, y222 - y111);

      String n = "Instructions";
      int tx11 = ((x111 + x222 - 80)) / 2;
      int ty11 = (y111 + 20);
      g.drawString(n, tx11, ty11);

      String move = "Press M to move";
      int moveX1 = ((x111 + x222 - 200)) / 2;
      int moveY1 = ((y111 + 70));
      g.setColor(Color.BLACK);
      g.drawString(move, moveX1, moveY1);

      String pick = "Press P to pick";
      int pickX1 = ((x111 + x222 - 200)) / 2;
      int pickY1 = ((y111 + 90));
      g.setColor(Color.BLACK);
      g.drawString(pick, pickX1, pickY1);


      String attack = "Press A to Attack";
      int attackX1 = ((x111 + x222 - 200)) / 2;
      int attackY1 = ((y111 + 110));
      g.setColor(Color.BLACK);
      g.drawString(attack, attackX1, attackY1);

      String lookAround = "Press L to look Around";
      int lookAroundX1 = ((x111 + x222 - 200)) / 2;
      int lookAroundY1 = ((y111 + 130));
      g.setColor(Color.BLACK);
      g.drawString(lookAround, lookAroundX1, lookAroundY1);

      String nextTurn = "Press Q to Quit Turn";
      int nextTurnX1 = ((x111 + x222 - 200)) / 2;
      int nextTurnY1 = ((y111 + 150));
      g.setColor(Color.BLACK);
      g.drawString(nextTurn, nextTurnX1, nextTurnY1);
    }

    /**
     * This method draws information on the game panel.
     * @param g Graphics.
     * @param x1info int
     * @param y1info int
     * @param x2info int
     * @param y2info int
     */
    private void drawInformationBoard(Graphics g, int x1info, int y1info, int x2info, int y2info) {
      g.setColor(Color.BLACK);
      g.drawRect(x1info, y1info, x2info - x1info, y2info - y1info);

      String info = "Game Status";
      int tx1info = ((x1info + x2info - 80)) / 2;
      int ty1info = (y1info + 20);
      g.drawString(info, tx1info, ty1info);

      String turn = "Turn - " + controller.getTurn().getName();
      int turnX1 = ((x1info + x2info - 200)) / 2;
      int turnY1 = ((y1info + 70));
      g.setColor(Color.RED);
      g.drawString(turn, turnX1, turnY1);

      String location = "Current Location : " + controller.getTurn().getCurrentRoom().getName();
      int locationX1 = ((x1info + x2info - 200)) / 2;
      int locationY1 = ((y1info + 90));
      g.setColor(Color.RED);
      g.drawString(location, locationX1, locationY1);

      String health = "Target Character's Health : " + controller.getTargetCharacterHealth();
      int healthX1 = ((x1info + x2info - 200)) / 2;
      int healthY1 = ((y1info + 110));
      g.setColor(Color.RED);
      g.drawString(health, healthX1, healthY1);

      String turns = "Turns left : " + controller.getNoOfTurns();
      int turnsX1 = ((x1info + x2info - 200)) / 2;
      int turnsY1 = ((y1info + 130));
      g.setColor(Color.RED);
      g.drawString(turns, turnsX1, turnsY1);

      String weaponsLimit = "Weapons Limit : " + controller.getTurn().getCurrentWeaponLimit();
      int wlX1 = ((x1info + x2info - 200)) / 2;
      int wlY1 = ((y1info + 150));
      g.setColor(Color.BLACK);
      g.drawString(weaponsLimit, wlX1, wlY1);

      String weapons = "Weapons :: ";
      int wx1 = ((x1info + x2info - 200)) / 2;
      int wy1 = ((y1info + 170));
      g.setColor(Color.BLACK);
      g.drawString(weapons, wx1, wy1);
      int i = 20;
      for (Weapon weapon : controller.getTurn().getWeapons()) {
        int wwX1 = ((x1info + x2info - 200)) / 2;
        int wwY1 = ((y1info + 170 + i));
        g.setColor(Color.RED);
        g.drawString(weapon.getWeaponName() + " " + weapon.getDamageValue(), wwX1, wwY1);
        i = i + 20;
      }

      String play = "Players :: ";
      int px1 = ((x1info + x2info - 200)) / 2;
      int py1 = ((y1info + 270));
      g.setColor(Color.RED);
      g.drawString(play, px1, py1);
      int p = 20;
      JButton button1 = null;
      for (Player player : controller.getPlayers()) {
        int ppX1 = ((x1info + x2info - 200)) / 2;
        int ppY1 = ((y1info + 270 + p));
        g.setColor(Color.BLACK);
        g.drawString(player.getName(), ppX1, ppY1);
        p = p + 20;
      }
    }
  }
}
