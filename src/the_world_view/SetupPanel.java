package the_world_view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import player.Player;
import the_world_view_controller.ControllerInterface;

/**
 * Panel to play the set up the game. It implements Panel interface and JPanel class.
 */
public class SetupPanel extends JPanel implements PanelInterface {

  private final ControllerInterface controller;
  private final TheSetupPanel setupPanel;

  /**
   * Constructor to set up the setup panel.
   *
   * @param cardLayout  card layout.
   * @param parentPanel parent panel.
   * @param frame       frame.
   * @param controller  controller.
   */
  public SetupPanel(final CardLayout cardLayout, final JPanel parentPanel,
                    final JFrame frame, ControllerInterface controller) {
    this.controller = controller;
    this.setupPanel = new TheSetupPanel();
    initComponents(cardLayout, parentPanel, frame);
  }

  /**
   * helper method to initialize the panel.
   *
   * @param cardLayout  card layout.
   * @param parentPanel parent panel.
   * @param frame       frame.
   */
  private void initComponents(final CardLayout cardLayout,
                              final JPanel parentPanel, final JFrame frame) {
    setLayout(new BorderLayout());
    setupPanel.repaint();
    setupPanel.revalidate();
    add(setupPanel, BorderLayout.CENTER);

    JButton beginGame = new JButton("Begin Game");
    beginGame.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (controller.getNoOfTurns() <= 0) {
          controller.showErrorDialog("Turns not added");
        } else if (controller.getPlayers().size() <= 0) {
          controller.showErrorDialog("Players not added");
        } else {
          cardLayout.show(parentPanel, "The World");
          frame.setTitle("The World");
        }
      }
    });

    JButton addPlayerButton = new JButton("Add Player");
    addPlayerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showAddPlayerDialog();
      }
    });

    JButton addTurns = new JButton("Add Turns");
    addTurns.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showAddTurnsDialog();
      }
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(beginGame);
    buttonPanel.add(addPlayerButton);
    buttonPanel.add(addTurns);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  /**
   * helper method to create Add turns dialog.
   */
  private void showAddTurnsDialog() {
    JDialog dialog = new JDialog();
    dialog.setTitle("Number of Turns");
    dialog.setSize(500, 300);
    dialog.setModal(true);

    JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JTextField turns = new JTextField();


    formPanel.add(new JLabel("Number of Turns"));
    formPanel.add(turns);


    JButton addButton = new JButton("Add");
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String turn = turns.getText();

        System.out.println("Turns: " + turn);
        controller.setTurns(turn);
        refresh();
        dialog.dispose(); // Close the dialog after adding
      }
    });

    dialog.add(formPanel, BorderLayout.CENTER);
    dialog.add(addButton, BorderLayout.SOUTH);
    dialog.setVisible(true);
  }

  /**
   * helper method to create Add player dialog.
   */
  private void showAddPlayerDialog() {
    JDialog dialog = new JDialog();
    dialog.setTitle("Add Player");
    dialog.setSize(500, 300);
    dialog.setModal(true);

    JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JTextField nameField = new JTextField();
    JTextField noOfWeapons = new JTextField();

    String[] rooms = controller.createRoomString();

    JComboBox<String> spaceToEnter = new JComboBox<>(rooms);

    JPanel playerType = new JPanel();
    ButtonGroup scoreGroup = new ButtonGroup();
    JRadioButton scoreRadioButton1 = new JRadioButton("Human");
    JRadioButton scoreRadioButton2 = new JRadioButton("Computer");
    scoreGroup.add(scoreRadioButton1);
    scoreGroup.add(scoreRadioButton2);
    playerType.add(scoreRadioButton1);
    playerType.add(scoreRadioButton2);

    formPanel.add(new JLabel("Player Name:"));
    formPanel.add(nameField);

    formPanel.add(new JLabel("Player Type:"));
    formPanel.add(playerType);

    formPanel.add(new JLabel("No of Weapons:"));
    formPanel.add(noOfWeapons);

    formPanel.add(new JLabel("Space to Enter:"));
    formPanel.add(spaceToEnter);

    JButton addButton = new JButton("Add");
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String name = nameField.getText();
        String weapon = noOfWeapons.getText();
        String space = (String) spaceToEnter.getSelectedItem();
        String type = getSelectedType(scoreRadioButton1, scoreRadioButton2);
        controller.addPlayer(name, weapon, space, type);
        refresh();
        dialog.dispose();
      }
    });

    dialog.add(formPanel, BorderLayout.CENTER);
    dialog.add(addButton, BorderLayout.SOUTH);
    dialog.setVisible(true);
  }

  /**
   * returns selected value.
   *
   * @param radioButtons radioButtons.
   * @return selected value.
   */
  private String getSelectedType(JRadioButton... radioButtons) {
    for (JRadioButton radioButton : radioButtons) {
      if (radioButton.isSelected()) {
        return radioButton.getText();
      }
    }
    return "";
  }

  @Override
  public void refresh() {
    System.out.println("inside refresh");
    setupPanel.repaint();
  }

  /**
   * The inner class representing the setup board panel for the World game.
   * This panel is responsible for rendering the setup board.
   */
  private class TheSetupPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      int x1Ins = 740;
      int y1Ins = 20;
      int x2Ins = 970;
      int y2Ins = 250;
      placesPlayersOnBoard(g, x1Ins, y1Ins, x2Ins, y2Ins);
    }

    /**
     * This method places players on the board.
     *
     * @param g      Graphics
     * @param x1Info int
     * @param y1Info int
     * @param x2Info int
     * @param y2Info int
     */
    private void placesPlayersOnBoard(Graphics g, int x1Info,
                                      int y1Info, int x2Info, int y2Info) {
      g.setColor(Color.BLACK);
      if (controller.getPlayers().size() > 0) {
        String play = "Players :: ";
        int px1 = ((x1Info + x2Info - 200)) / 2;
        int py1 = ((y1Info + 20));
        g.setColor(Color.RED);
        g.drawString(play, px1, py1);
        int p = 20;
        for (Player player : controller.getPlayers()) {
          int ppX1 = ((x1Info + x2Info - 200)) / 2;
          int ppY1 = ((y1Info + 50 + p));
          g.setColor(Color.BLACK);
          g.drawString("Player Name : ".concat(player.getName()), ppX1, ppY1);
          p = p + 20;

          int ppX1111 = ((x1Info + x2Info - 200)) / 2;
          int ppY1111 = ((y1Info + 50 + p));
          g.setColor(Color.BLACK);
          g.drawString("Player Type : ".concat(player.getPlayerType().toString()),
                  ppX1111, ppY1111);
          p = p + 20;

          int ppX11 = ((x1Info + x2Info - 200)) / 2;
          int ppY11 = ((y1Info + 50 + p));
          g.setColor(Color.BLACK);
          g.drawString("Player Room : ".concat(player.getCurrentRoom().getName()),
                  ppX11, ppY11);
          p = p + 20;

          int ppX111 = ((x1Info + x2Info - 200)) / 2;
          int ppY111 = ((y1Info + 50 + p));
          g.setColor(Color.BLACK);
          g.drawString("Weapons Allowed : ".concat(String.valueOf(player.getWeaponLimit())),
                  ppX111, ppY111);
          p = p + 50;
        }
        if (controller.getNoOfTurns() > 0) {
          int ppX1T = ((x1Info + x2Info - 200)) / 2;
          int ppY1T = ((y1Info + 50 + p));
          g.setColor(Color.RED);
          g.drawString("Maximum Turns Allowed :: ".concat(String.valueOf(
                  controller.getNoOfTurns())),
                  ppX1T, ppY1T);
        }
      }
    }
  }
}
