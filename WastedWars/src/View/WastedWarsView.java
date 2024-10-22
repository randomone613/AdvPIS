package WastedWars.src.View;

import WastedWars.src.Model.Player;
import WastedWars.src.Model.WastedWarsModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WastedWarsView {
    private static final int MAX_PLAYERS = 6;
    private WastedWarsModel model;
    private JFrame frame;
    private JPanel playerPanel;
    private JButton addPlayerButton;
    private GridBagConstraints gbcp;
    private JButton randomModeButton;
    private JButton chooseModeButton;

    public WastedWarsView(WastedWarsModel model) {
        this.model = model;
        initializeView();
    }

    private void initializeView() {
        frame = new JFrame("Wasted Wars");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Wasted Wars", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel modePanel = new JPanel();
        modePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);
        randomModeButton = new JButton("Start Game");
        randomModeButton.setPreferredSize(new Dimension(150, 150));
        modePanel.add(randomModeButton, gbc);

        mainPanel.add(modePanel, BorderLayout.CENTER);

        playerPanel = new JPanel(new GridBagLayout());
        gbcp = new GridBagConstraints();
        gbcp.insets = new Insets(5, 5, 5, 5);
        gbcp.anchor = GridBagConstraints.CENTER;
        refreshPlayers();

        JScrollPane scrollPane = new JScrollPane(playerPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void refreshPlayers() {
        playerPanel.removeAll();

        List<Player> players = model.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            addPlayerSquare(i, players.get(i).getUsername());
        }

        addAddPlayerButton();
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    private void addPlayerSquare(int index, String playerName) {
        JPanel playerBox = new JPanel();
        playerBox.setLayout(new BorderLayout());
        playerBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        playerBox.setPreferredSize(new Dimension(100, 100));

        JPanel square = new JPanel();
        square.setPreferredSize(new Dimension(100, 100));
        square.setBackground(Color.LIGHT_GRAY);
        playerBox.add(square, BorderLayout.CENTER);

        JLabel nameLabel = new JLabel(playerName, SwingConstants.CENTER);
        nameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String newName = JOptionPane.showInputDialog(frame, "Enter new name:", playerName);
                    if (newName != null && !newName.isEmpty()) {
                        nameLabel.setText(newName);
                        model.getPlayers().get(index).setUsername(newName);
                    }
                }
            }
        });

        playerBox.add(nameLabel, BorderLayout.SOUTH);

        if (index >= 2) {
            JButton removeButton = new JButton("-");
            removeButton.setPreferredSize(new Dimension(30, 30));
            removeButton.addActionListener(e -> {
                model.removePlayer(index);
                refreshPlayers();
            });
            playerBox.add(removeButton, BorderLayout.NORTH);
        }

        gbcp.gridx = index;
        playerPanel.add(playerBox, gbcp);
    }

    private void addAddPlayerButton() {
        if (model.getPlayers().size() < MAX_PLAYERS) {
            if (addPlayerButton == null) {
                addPlayerButton = new JButton("+ Add Player");
                addPlayerButton.setPreferredSize(new Dimension(100, 100));
            }

            gbcp.gridx = model.getPlayers().size();
            playerPanel.add(addPlayerButton, gbcp);
        }
    }

    public JButton getAddPlayerButton() {
        return addPlayerButton;
    }

    public JButton getRandomModeButton() {
        return randomModeButton;
    }

    public JButton getChooseModeButton() {
        return chooseModeButton;
    }

    public JFrame getFrame() {
        return frame;
    }
}
