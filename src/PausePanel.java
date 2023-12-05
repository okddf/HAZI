import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PausePanel extends JPanel {
    BufferedImage background;
    GUI gui;
    GameLogic board;

    public PausePanel(BufferedImage bg, GUI gui, GameLogic b) {
        background = bg;
        this.gui = gui;
        board = b;

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;

        JButton resumeButton = new Button(new ImageIcon("src/resources/resume.png"));
        resumeButton.addActionListener(this::handleresumeClick);
        resumeButton.setPreferredSize(new Dimension(500, 100));

        resumeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resumeButton.setPreferredSize(new Dimension(550, 125));

                resumeButton.getParent().revalidate();
                resumeButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resumeButton.setPreferredSize(new Dimension(500, 100));

                resumeButton.getParent().revalidate();
                resumeButton.getParent().repaint();
            }
        });

        JPanel resumebuttonContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            }
        };
        resumebuttonContainer.setOpaque(false);
        resumebuttonContainer.setPreferredSize(new Dimension(550, 125));
        resumebuttonContainer.setLayout(new GridBagLayout());
        resumebuttonContainer.add(resumeButton);

        JButton saveButton = new Button(new ImageIcon("src/resources/save.png"));
        saveButton.addActionListener(this::handlesaveClick);
        saveButton.setPreferredSize(new Dimension(500, 100));

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setPreferredSize(new Dimension(550, 125));

                saveButton.getParent().revalidate();
                saveButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setPreferredSize(new Dimension(500, 100));

                saveButton.getParent().revalidate();
                saveButton.getParent().repaint();
            }
        });

        JPanel savebuttonContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            }
        };
        savebuttonContainer.setOpaque(false);
        savebuttonContainer.setPreferredSize(new Dimension(550, 125));
        savebuttonContainer.setLayout(new GridBagLayout());
        savebuttonContainer.add(saveButton);

        JButton leaveButton = new Button(new ImageIcon("src/resources/leave.png"));
        leaveButton.addActionListener(this::handleleaveClick);
        leaveButton.setPreferredSize(new Dimension(500, 100));

        leaveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                leaveButton.setPreferredSize(new Dimension(550, 125));

                leaveButton.getParent().revalidate();
                leaveButton.getParent().repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                leaveButton.setPreferredSize(new Dimension(500, 100));

                leaveButton.getParent().revalidate();
                leaveButton.getParent().repaint();
            }
        });

        JPanel leavebuttonContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            }
        };
        leavebuttonContainer.setOpaque(false);
        leavebuttonContainer.setPreferredSize(new Dimension(550, 125));
        leavebuttonContainer.setLayout(new GridBagLayout());
        leavebuttonContainer.add(leaveButton);

        add(resumebuttonContainer, gbc);
        add(savebuttonContainer, gbc);
        add(leavebuttonContainer, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (background != null) {
            g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    private void handleresumeClick(ActionEvent e) {
        Window window = SwingUtilities.windowForComponent(PausePanel.this);
        if (window instanceof JDialog) {
            ((JDialog) window).dispose();
        }
    }

    private void handlesaveClick(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        Window window = SwingUtilities.getWindowAncestor(this);
        int choice = fileChooser.showSaveDialog(window);
        if (choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                board.save(file.getAbsolutePath());
                JOptionPane.showMessageDialog(window, "SUCCESFUL SAVE");
            } catch (IOException i) {
                JOptionPane.showMessageDialog(window, "Error saving game: ");
            }

        }
    }

    private void handleleaveClick(ActionEvent e) {
        gui.showPanel("MAIN_MENU");
        Window window = SwingUtilities.windowForComponent(PausePanel.this);
        if (window instanceof JDialog) {
            ((JDialog) window).dispose();
        }
    }
}
