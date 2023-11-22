package the_world_view_controller;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import player.GameOverException;
import player.Player;
import player.PlayerImpl;
import player.PlayerType;
import room.Room;
import the_world_view.ViewInterface;
import turncommand.Attack;
import turncommand.Exit;
import turncommand.Move;
import turncommand.Pick;
import turncommand.TurnCommands;
import weapon.Weapon;
import world.World;
import world.WorldModel;

/**
 * This is the controller for the world. It implements all the methods from
 * the controller interface.
 */
public class Controller implements ControllerInterface {
  private World model;
  private final ViewInterface view;

  /**
   * Constructor to set up view and model.
   *
   * @param view view.
   * @param model World model.
   */
  public Controller(ViewInterface view, World model) {
    this.view = view;
    this.model = model;
  }

  @Override
  public void playGame() {
    view.setupView(this);
  }

  @Override
  public String[] createRoomString() {
    String[] r = new String[model.getRooms().size()];
    for (Room room : model.getRooms()) {
      r[room.getIndex()] = room.getName();
    }
    return r;
  }

  @Override
  public void addPlayer(String name, String weapon, String space, String playerType) {
    int roomNumber = 0;
    for (Room r : model.getRooms()) {
      if (r.getName().equals(space)) {
        roomNumber = r.getIndex();
      }
    }
    PlayerType pt;
    if ("Human".equals(playerType)) {
      pt = PlayerType.HUMAN;
    } else {
      pt = PlayerType.COMPUTER;
    }
    Room roomToEnter = model.getRooms().get(roomNumber);
    Player player = new PlayerImpl(name, roomToEnter, Integer.parseInt(weapon), pt);
    model.addPlayer(player);
    roomToEnter.addPlayer(player);
  }

  @Override
  public void setTurns(String turns) {
    model.setNoOfTurns(Integer.parseInt(turns));
  }

  @Override
  public void moveAction() {
    if (model.getNoOfTurns() > 0) {
      showMoveDialog();
    } else {
      view.showDialog("Game Over : Turns exhausted");
    }
  }

  @Override
  public void pickAction() {
    if (model.getNoOfTurns() > 0) {
      try {
        showPickDialog();
      } catch (GameOverException ex) {
        view.showGameOverDialog(ex.getMessage());
      }
    } else {
      view.showDialog("Game Over : Turns exhausted");
    }
  }

  @Override
  public void attackAction() {
    if (model.getNoOfTurns() > 0) {
      if (!hasPlayersInTheVicinity()) {
        if (model.getTurn().getCurrentRoom().getName().equals(model.getTargetCharacterLocation())) {
          showAttackDialog();
        } else {
          view.showDialog("Target Character is not in the same room. You can't attack.");
        }
      } else {
        view.showDialog("Players in the vicinity. Can't Attack.");
      }
    } else {
      view.showDialog("Game Over : Turns exhausted");
    }
  }

  @Override
  public void lookAroundAction() {
    if (model.getNoOfTurns() > 0) {
      showLookAroundDialog();
    } else {
      view.showDialog("Game Over : Turns exhausted");
    }
  }

  @Override
  public void quitAction() {
    if (model.getNoOfTurns() > 0) {
      showQuitDialog();
    } else {
      view.showDialog("Game Over : Turns exhausted");
    }
  }

  /**
   * helper method to show move dialog.
   */
  private void showMoveDialog() {
    JDialog dialog = new JDialog();
    dialog.setTitle("Move");
    dialog.setSize(500, 300);
    dialog.setModal(true);

    JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    String[] r = new String[model.getTurn().getCurrentRoom().getNeighbor().size()];
    int i = 0;
    for (Room room : model.getTurn().getCurrentRoom().getNeighbor()) {
      r[i] = room.getName();
      i++;
    }

    JComboBox<String> spaceToMove = new JComboBox<>(r);

    formPanel.add(new JLabel("Space to Move:"));
    formPanel.add(spaceToMove);

    JButton addButton = new JButton("Move");
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String space = (String) spaceToMove.getSelectedItem();
        model.movePlayer(space);
        try {
          model.nextT();
        } catch (GameOverException ex) {
          view.showGameOverDialog(ex.getMessage());
        }
        view.refresh();
        if (model.getTurn().getPlayerType().equals(PlayerType.COMPUTER)) {
          try {
            playTheRandomGame(model);
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          } catch (GameOverException ex) {
            view.showGameOverDialog(ex.getMessage());
          }
        }
        dialog.dispose();
      }
    });

    dialog.add(formPanel, BorderLayout.CENTER);
    dialog.add(addButton, BorderLayout.SOUTH);
    dialog.setVisible(true);
  }

  /**
   * helper method to create attack dialog.
   */
  private void showAttackDialog() {
    JDialog dialog = new JDialog();
    dialog.setTitle("Pick");
    dialog.setSize(500, 300);
    dialog.setModal(true);

    JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    List<String> r = new ArrayList<>();
    for (Weapon weapon : model.getTurn().getWeapons()) {
      r.add(weapon.getWeaponName());
    }
    r.add("Poke");
    String[] k = r.toArray(new String[r.size()]);
    JComboBox<String> weapons = new JComboBox<>(k); // Example country options

    formPanel.add(new JLabel("Weapons to Attack"));
    formPanel.add(weapons);

    JButton addButton = new JButton("Attack");
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String selectedWeapon = (String) weapons.getSelectedItem();

        try {
          model.decreaseHealth(selectedWeapon);
          model.nextT();
        } catch (GameOverException ex) {
          view.showGameOverDialog(ex.getMessage());
        }
        view.refresh();
        if (model.getTurn().getPlayerType().equals(PlayerType.COMPUTER)) {
          try {
            playTheRandomGame(model);
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          } catch (GameOverException ex) {
            view.showGameOverDialog(ex.getMessage());
          }
        }
        dialog.dispose();
      }
    });

    dialog.add(formPanel, BorderLayout.CENTER);
    dialog.add(addButton, BorderLayout.SOUTH);
    dialog.setVisible(true);

  }

  /**
   * helper method to check players in the vicinity.
   */
  private boolean hasPlayersInTheVicinity() {
    Room currentRoom = model.getTurn().getCurrentRoom();
    List<Room> neighbours = currentRoom.getNeighbor();
    boolean hasPlayersInTheVicinity = false;
    for (Room room : neighbours) {
      if (room.getPlayers().size() > 0) {
        hasPlayersInTheVicinity = true;
      }
    }
    if (currentRoom.getPlayers().size() > 1) {
      hasPlayersInTheVicinity = true;
    }
    return hasPlayersInTheVicinity;
  }

  /**
   * helper method to create look around dialog.
   */
  private void showLookAroundDialog() {
    JDialog dialog = new JDialog();
    dialog.setTitle("Look Around");
    dialog.setSize(600, 400);
    dialog.setModal(true);

    JTextArea textArea = new JTextArea();
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setEditable(false);

    StringBuilder out = new StringBuilder();
    List<Room> neighbour = model.getTurn().getCurrentRoom().getNeighbor();
    Room currentRoom = model.getTurn().getCurrentRoom();
    out.append(":: Current location").append("\n");
    out.append("================").append("\n");
    out.append(currentRoom.getName()).append("\n");
    out.append("\n");
    out.append(":: Room occupants").append("\n");
    out.append("================").append("\n");
    if (currentRoom.hasTargetCharacter()) {
      out.append(model.getTargetCharacter()).append("\n");
    }
    if (currentRoom.getPlayers().size() > 0) {
      for (Player p : currentRoom.getPlayers()) {
        out.append(p.playerDescription()).append("\n");
      }
    }
    out.append("\n");
    out.append(":: Items in the Room").append("\n");
    out.append("================").append("\n");
    if (currentRoom.getWeapons().size() > 0) {
      for (Weapon p : currentRoom.getWeapons()) {
        out.append(p.weaponInfo()).append("\n");
      }
    }
    out.append("\n");
    out.append(":: Neighbors of the room").append("\n");
    out.append("================").append("\n");
    for (Room r : neighbour) {
      out.append(r.displaySingleRoomInformation(r, model)).append("\n");
    }
    textArea.setText(out.toString());

    JScrollPane scrollPane = new JScrollPane(textArea);
    dialog.add(scrollPane, BorderLayout.CENTER);

    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        view.refresh();
        dialog.dispose();
      }
    });

    dialog.add(closeButton, BorderLayout.SOUTH);
    dialog.setVisible(true);
  }

  /**
   * helper method to create pick dialog.
   */
  private void showPickDialog() throws GameOverException {
    JDialog dialog = new JDialog();
    dialog.setTitle("Pick");
    dialog.setSize(500, 300);
    dialog.setModal(true);

    JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    if (model.getTurn().getCurrentWeaponLimit() <= 0) {
      view.showDialog("Weapons limit exhausted");
    } else {
      Room room = model.getTurn().getCurrentRoom();
      if (room == null || room.getWeapons().size() <= 0) {
        view.showDialog("Weapons not available in the space");
      } else {
        String[] r = new String[model.getTurn().getCurrentRoom().getWeapons().size()];
        int i = 0;
        for (Weapon weapon : model.getTurn().getCurrentRoom().getWeapons()) {
          r[i] = weapon.getWeaponName();
          i++;
        }

        JComboBox<String> weapons = new JComboBox<>(r); // Example country options

        formPanel.add(new JLabel("Weapons to pick"));
        formPanel.add(weapons);

        JButton addButton = new JButton("Pick");
        addButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String selectedWeapon = (String) weapons.getSelectedItem();

            room.removeWeapon(selectedWeapon);
            model.addWeaponToPlayer(model.getTurn(), selectedWeapon);

            view.refresh();
            dialog.dispose(); // Close the dialog after adding
          }
        });

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(addButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
      }
    }
  }

  /**
   * helper method to create quit dialog.
   */
  private void showQuitDialog() {
    try {
      model.nextT();
      view.refresh();
      if (model.getTurn().getPlayerType().equals(PlayerType.COMPUTER)) {
        try {
          playTheRandomGame(model);
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        } catch (GameOverException ex) {
          view.showGameOverDialog(ex.getMessage());
        }
      }
    } catch (GameOverException ex) {
      view.showDialog(ex.getMessage());
    }
  }

  @Override
  public Room getTargetCharacterCurrentRoom() {

    return model.getRooms().get(model.getCurrentIndexTargetCharacter());
  }

  @Override
  public List<Player> getPlayers() {
    return model.getPlayer();
  }

  @Override
  public Player getTurn() {
    return model.getTurn();
  }

  @Override
  public int getTargetCharacterHealth() {
    return model.getHealth();
  }

  @Override
  public int getNoOfTurns() {
    return model.getNoOfTurns();
  }

  @Override
  public List<Room> getRooms() {
    return model.getRooms();
  }

  @Override
  public void showErrorDialog(String error) {
    view.showDialog(error);
  }

  @Override
  public void restartGame() {
    model = new WorldModel();
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("res/mansion.txt"));
      model = model.parseFile(reader);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * game play for computer player.
   * @param world World model.
   * @throws IOException IOException.
   * @throws GameOverException GameOverException.
   */
  private void playTheRandomGame(World world) throws IOException, GameOverException {
    Scanner scan = new Scanner(System.in);
    Appendable out = System.out;
    Map<String, TurnCommands> map = new HashMap<>();
    map.put("move", new Move(out, scan));
    map.put("pick", new Pick(out, scan));
    map.put("attack", new Attack(out, scan));
    map.put("exit", new Exit(out, scan));
    List<String> commands = new ArrayList<>();
    commands.add("move");
    commands.add("pick");
    commands.add("attack");
    commands.add("exit");
    String c = "";
    boolean moved = false;
    while (!moved) {
      c = commands.get(new Random().nextInt(commands.size()));
      System.out.println(c);
      TurnCommands tc = map.get(c);
      tc.goAutomatic(world);
      if (("move").equals(c) || ("attack").equals(c) || ("exit").equals(c)) {
        moved = true;
      }
    }
    view.refresh();
  }
}
