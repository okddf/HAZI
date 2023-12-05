import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);
    private GamePanel gamePanel;
    private BoardPanel boardPanel;
    private GameLogic board;
    private Ai ai;

    public GUI(GameLogic b, Ai a) {
        board = b;
        ai = a;
        JFrame frame = new JFrame("Amoba");
        frame.setUndecorated(true);

        boardPanel = new BoardPanel(board, this, ai);
        gamePanel = new GamePanel(board, this, boardPanel, ai);
        MainMenuPanel mainMenuPanel = new MainMenuPanel(this, board, gamePanel, boardPanel);
        GameMenuPanel gameMenuPanel = new GameMenuPanel(this, gamePanel, board);
        boardPanel.setRoundUpdateCallback(gamePanel::updateRound);

        cardPanel.add(mainMenuPanel, "MAIN_MENU");
        cardPanel.add(gameMenuPanel, "GAME_MENU");
        cardPanel.add(gamePanel, "GAME");

        frame.setJMenuBar(createMenuBar());

        frame.add(cardPanel);
        frame.getContentPane().setBackground(new Color(187, 242, 222));
        frame.setSize(1600, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 60);
            }
        };
        menuBar.setBackground(new Color(247, 216, 183));

        JMenu menu = new JMenu("Menu Options");
        customizeMenuItem(menu);
        menuBar.add(menu);

        JMenuItem mainMenuItem = new JMenuItem("Main Menu");
        mainMenuItem.addActionListener(e -> showPanel("MAIN_MENU"));
        customizeMenuItem(mainMenuItem);
        menu.add(mainMenuItem);

        JMenuItem gameMenuItem = new JMenuItem("Game Menu");
        gameMenuItem.addActionListener(e -> showPanel("GAME_MENU"));
        customizeMenuItem(gameMenuItem);
        menu.add(gameMenuItem);

        JMenuItem exit = new JMenuItem("Exit");
        customizeMenuItem(exit);
        exit.addActionListener(e -> System.exit(0));
        menu.add(exit);

        return menuBar;
    }

    private static void customizeMenuItem(JMenuItem menuItem) {
        menuItem.setBackground(new Color(247, 216, 183));
        menuItem.setFont(new Font("Arial", Font.PLAIN, 30));
        menuItem.setOpaque(true);
        menuItem.setBorderPainted(false);
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    public void reset() {
        gamePanel.reset();
        boardPanel.update();
    }

}
