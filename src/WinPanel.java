import javax.crypto.spec.DESKeySpec;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WinPanel extends JPanel {
    BufferedImage background;
    GameLogic board;
    GUI gui;

    public WinPanel(BufferedImage bg, GameLogic b, GUI g) {
        background = bg;
        board = b;
        gui = g;
        WinContainer wincontainer = new WinContainer(board, "src/resources/x_won.png");
        setLayout(new GridBagLayout());
        if (board.getcurrentplayer() == 2)
            wincontainer.setImage("src/resources/o_won.png");
        wincontainer.setOpaque(false);
        wincontainer.setPreferredSize(new Dimension(1030, 730));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JButton playagainButton = new Button(new ImageIcon("src/resources/play_again.png"));
        playagainButton.addActionListener(this::handleplayagainClick);
        playagainButton.setPreferredSize(new Dimension(500, 100));

        playagainButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playagainButton.setPreferredSize(new Dimension(550, 125));

                playagainButton.getParent().revalidate();
                playagainButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playagainButton.setPreferredSize(new Dimension(500, 100));

                playagainButton.getParent().revalidate();
                playagainButton.getParent().repaint();
            }
        });

        JPanel playagainButtonContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            }
        };
        playagainButtonContainer.setOpaque(false);
        playagainButtonContainer.setPreferredSize(new Dimension(550, 125));
        playagainButtonContainer.setLayout(new GridBagLayout());
        playagainButtonContainer.add(playagainButton);

        JButton continueButton = new Button(new ImageIcon("src/resources/continue.png"));
        continueButton.addActionListener(this::handlecontinueClick);
        continueButton.setPreferredSize(new Dimension(500, 100));

        continueButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                continueButton.setPreferredSize(new Dimension(550, 125));

                continueButton.getParent().revalidate();
                continueButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                continueButton.setPreferredSize(new Dimension(500, 100));

                continueButton.getParent().revalidate();
                continueButton.getParent().repaint();
            }
        });

        JPanel continueButtonContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            }
        };
        continueButtonContainer.setOpaque(false);
        continueButtonContainer.setPreferredSize(new Dimension(550, 125));
        continueButtonContainer.setLayout(new GridBagLayout());
        continueButtonContainer.add(continueButton);

        add(wincontainer, gbc);
        add(playagainButtonContainer);
        add(continueButtonContainer);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    private void handlecontinueClick(ActionEvent e) {
        Window window = SwingUtilities.windowForComponent(WinPanel.this);
        if (window instanceof JDialog) {
            ((JDialog) window).dispose();
        }
        gui.showPanel("MAIN_MENU");
    }

    private void handleplayagainClick(ActionEvent e) {
        Window window = SwingUtilities.windowForComponent(WinPanel.this);
        if (window instanceof JDialog) {
            ((JDialog) window).dispose();
        }
        board.reset(board.getsize());
        gui.reset();
        gui.showPanel("GAME");
    }

}
