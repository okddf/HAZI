import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.ActionEvent;

class MainMenuPanel extends JPanel {
    private GUI gui;
    private GameLogic board;
    private BoardPanel boardPanel;
    private GamePanel gamePanel;

    public MainMenuPanel(GUI gui, GameLogic b, GamePanel gp, BoardPanel bp) {
        this.gui = gui;
        board = b;
        boardPanel = bp;
        gamePanel = gp;
        setLayout(new GridBagLayout());

        setBackground(new Color(247, 216, 183));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;

        JButton newGameButton = new Button(new ImageIcon("src/resources/newgame.jpg"));
        newGameButton.addActionListener(this::handleNewGameClick);
        newGameButton.setPreferredSize(new Dimension(500, 100));

        newGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                newGameButton.setPreferredSize(new Dimension(550, 125));

                newGameButton.getParent().revalidate();
                newGameButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                newGameButton.setPreferredSize(new Dimension(500, 100));

                newGameButton.getParent().revalidate();
                newGameButton.getParent().repaint();
            }
        });

        JPanel newgamebuttonContainer = new JPanel();
        newgamebuttonContainer.setBackground(new Color(247, 216, 183));
        newgamebuttonContainer.setPreferredSize(new Dimension(550, 125));
        newgamebuttonContainer.setLayout(new GridBagLayout());
        newgamebuttonContainer.add(newGameButton);

        JButton loadButton = new Button(new ImageIcon("src/resources/load.jpg"));
        loadButton.addActionListener(this::handleLoadClick);
        loadButton.setPreferredSize(new Dimension(500, 100));

        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadButton.setPreferredSize(new Dimension(550, 125));

                loadButton.getParent().revalidate();
                loadButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loadButton.setPreferredSize(new Dimension(500, 100));

                loadButton.getParent().revalidate();
                loadButton.getParent().repaint();
            }
        });

        JPanel loadbuttonContainer = new JPanel();
        loadbuttonContainer.setBackground(new Color(247, 216, 183));
        loadbuttonContainer.setPreferredSize(new Dimension(550, 125));
        loadbuttonContainer.setLayout(new GridBagLayout());
        loadbuttonContainer.add(loadButton);

        JButton exitButton = new Button(new ImageIcon("src/resources/exit.jpg"));
        exitButton.setPreferredSize(new Dimension(500, 100));
        exitButton.addActionListener(e -> System.exit(0));

        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setPreferredSize(new Dimension(550, 125));

                exitButton.getParent().revalidate();
                exitButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setPreferredSize(new Dimension(500, 100));

                exitButton.getParent().revalidate();
                exitButton.getParent().repaint();
            }
        });

        JPanel exitbuttonContainer = new JPanel();
        exitbuttonContainer.setBackground(new Color(247, 216, 183));
        exitbuttonContainer.setPreferredSize(new Dimension(550, 125));
        exitbuttonContainer.setLayout(new GridBagLayout());
        exitbuttonContainer.add(exitButton);

        add(newgamebuttonContainer, gbc);
        add(loadbuttonContainer, gbc);
        add(exitbuttonContainer, gbc);
    }

    private void handleNewGameClick(ActionEvent e) {
        board.reset(board.getsize());
        boardPanel.update();
        gui.showPanel("GAME_MENU");
    }

    private void handleLoadClick(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        Window window = SwingUtilities.getWindowAncestor(this);
        int choice = fileChooser.showOpenDialog(window);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                board.load(file.getAbsolutePath());
                JOptionPane.showMessageDialog(window, "SUCCESSFUL LOAD");
                gui.showPanel("GAME");
            } catch (IOException i) {
                JOptionPane.showMessageDialog(window, "Error loading game: ");
            } catch (ClassNotFoundException c) {
                JOptionPane.showMessageDialog(window, "Error loading game: ");
            }

        }
        boardPanel.update();
        gamePanel.updateRound();
    }
}
