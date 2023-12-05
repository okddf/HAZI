import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameMenuPanel extends JPanel {
    private GUI gui;
    private GamePanel gamePanel;
    private GameLogic board;

    public GameMenuPanel(GUI gui, GamePanel gp, GameLogic b) {
        this.gui = gui;
        gamePanel = gp;
        board = b;
        setLayout(new GridBagLayout());

        setBackground(new Color(247, 216, 183));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;

        JButton singleplayerButton = new Button(new ImageIcon("src/resources/singleplayer.jpg"));
        singleplayerButton.addActionListener(this::handlesingleplayerClick);
        singleplayerButton.setPreferredSize(new Dimension(500, 100));

        singleplayerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set the button's size to be larger when mouse enters
                singleplayerButton.setPreferredSize(new Dimension(550, 125));

                // Inform the layout manager of the change
                singleplayerButton.getParent().revalidate();
                singleplayerButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Set the button's size back to default when mouse exits
                singleplayerButton.setPreferredSize(new Dimension(500, 100));

                // Inform the layout manager of the change
                singleplayerButton.getParent().revalidate();
                singleplayerButton.getParent().repaint();
            }
        });

        JPanel singleplayerbuttonContainer = new JPanel();
        singleplayerbuttonContainer.setBackground(new Color(247, 216, 183));
        singleplayerbuttonContainer.setPreferredSize(new Dimension(550, 125));

        singleplayerbuttonContainer.setLayout(new GridBagLayout());
        singleplayerbuttonContainer.add(singleplayerButton);

        JButton multiplayerButton = new Button(new ImageIcon("src/resources/multiplayer.jpg"));
        multiplayerButton.addActionListener(this::handlemultiplayerClick);
        multiplayerButton.setPreferredSize(new Dimension(500, 100));

        multiplayerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                multiplayerButton.setPreferredSize(new Dimension(550, 125));

                multiplayerButton.getParent().revalidate();
                multiplayerButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                multiplayerButton.setPreferredSize(new Dimension(500, 100));

                multiplayerButton.getParent().revalidate();
                multiplayerButton.getParent().repaint();
            }
        });

        JPanel multiplayerContainer = new JPanel();
        multiplayerContainer.setBackground(new Color(247, 216, 183));
        multiplayerContainer.setPreferredSize(new Dimension(550, 125));
        multiplayerContainer.setLayout(new GridBagLayout());
        multiplayerContainer.add(multiplayerButton);

        JButton gobackButton = new Button(new ImageIcon("src/resources/go_back.jpg"));
        gobackButton.addActionListener(this::handleGoBackClick);
        gobackButton.setPreferredSize(new Dimension(500, 100));

        gobackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                gobackButton.setPreferredSize(new Dimension(550, 125));

                gobackButton.getParent().revalidate();
                gobackButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                gobackButton.setPreferredSize(new Dimension(500, 100));

                gobackButton.getParent().revalidate();
                gobackButton.getParent().repaint();
            }
        });

        JPanel gobackContainer = new JPanel();
        gobackContainer.setBackground(new Color(247, 216, 183));
        gobackContainer.setPreferredSize(new Dimension(550, 125));
        gobackContainer.setLayout(new GridBagLayout());
        gobackContainer.add(gobackButton);

        add(singleplayerbuttonContainer, gbc);
        add(multiplayerContainer, gbc);
        add(gobackContainer, gbc);
    }

    private void handleGoBackClick(ActionEvent e) {
        gui.showPanel("MAIN_MENU");
    }

    private void handlesingleplayerClick(ActionEvent e) {
        gamePanel.reset();
        board.setgamemode(1);
        gui.showPanel("GAME");
    }

    private void handlemultiplayerClick(ActionEvent e) {
        gamePanel.reset();
        board.setgamemode(0);
        gui.showPanel("GAME");
    }
}
