import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class GamePanel extends JPanel {

    private BoardPanel boardPanel;
    private GUI gui;
    private GameLogic board;
    private JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
    private JLabel roundLabel = new JLabel("1", SwingConstants.CENTER);
    private Timer gameTimer;
    private Ai ai;

    public GamePanel(GameLogic b, GUI gui, BoardPanel bp, Ai a) {
        this.gui = gui;
        board = b;
        boardPanel = bp;
        ai = a;

        timerLabel.setFont(new Font("Arial", Font.BOLD, 75));
        timerLabel.setForeground(new Color(255, 239, 220));

        roundLabel.setFont(new Font("Arial", Font.BOLD, 75));
        roundLabel.setForeground(new Color(255, 239, 220));

        startTimer();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;

        JButton time = new Button(new ImageIcon("src/resources/timelabel.jpg"));
        time.setPreferredSize(new Dimension(250, 100));
        JPanel timeContainer = new JPanel();
        timeContainer.setBackground(new Color(247, 216, 183));
        timeContainer.setPreferredSize(new Dimension(330, 250));
        timeContainer.setLayout(new GridBagLayout());
        timeContainer.add(time, gbc);
        timeContainer.add(timerLabel, gbc);

        JButton round = new Button(new ImageIcon("src/resources/roundlabel.jpg"));
        round.setPreferredSize(new Dimension(250, 100));
        JPanel roundContainer = new JPanel();
        roundContainer.setBackground(new Color(247, 216, 183));
        roundContainer.setPreferredSize(new Dimension(330, 250));
        roundContainer.setLayout(new GridBagLayout());
        roundContainer.add(round, gbc);
        roundContainer.add(roundLabel, gbc);

        setBackground(new Color(247, 216, 183));
        setLayout(new BorderLayout());
        boardPanel.setPreferredSize(new Dimension(940, 940));
        add(boardPanel, BorderLayout.CENTER);

        JPanel leftfiller = new JPanel(new BorderLayout());
        leftfiller.setBackground(new Color(247, 216, 183));
        leftfiller.setPreferredSize(new Dimension(330, 1000));
        add(leftfiller, BorderLayout.WEST);

        JPanel dataContainer = new JPanel();
        dataContainer.setBackground(new Color(247, 216, 183));
        dataContainer.setPreferredSize(new Dimension(330, 500));
        dataContainer.add(timeContainer);
        dataContainer.add(roundContainer);

        leftfiller.add(dataContainer, BorderLayout.CENTER);

        JPanel rightfiller = new JPanel();
        rightfiller.setBackground(new Color(247, 216, 183));
        rightfiller.setPreferredSize(new Dimension(330, 1000));
        add(rightfiller, BorderLayout.EAST);

        JPanel upfiller = new JPanel();
        upfiller.setBackground(new Color(247, 216, 183));
        upfiller.setPreferredSize(new Dimension(1600, 30));
        add(upfiller, BorderLayout.NORTH);

        JPanel downfiller = new JPanel();
        downfiller.setBackground(new Color(247, 216, 183));
        downfiller.setPreferredSize(new Dimension(1600, 30));
        add(downfiller, BorderLayout.SOUTH);

        JButton pauseButton = new Button(new ImageIcon("src/resources/pause.jpg"));
        pauseButton.addActionListener(this::handlepauseClick);
        pauseButton.setPreferredSize(new Dimension(150, 150));
        rightfiller.add(pauseButton);

        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (board.getstate() != 1) {
                    pauseButton.setPreferredSize(new Dimension(175, 175));
                    pauseButton.getParent().revalidate();
                    pauseButton.getParent().repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (board.getstate() != 1) {
                    pauseButton.setPreferredSize(new Dimension(150, 150));

                    pauseButton.getParent().revalidate();
                    pauseButton.getParent().repaint();
                }
            }
        });

        JButton undoButton = new Button(new ImageIcon("src/resources/undo.jpg"));
        undoButton.addActionListener(this::handleundoClick);
        undoButton.setPreferredSize(new Dimension(150, 150));

        JPanel undobuttonContainer = new JPanel();
        undobuttonContainer.setBackground(new Color(247, 216, 183));
        undobuttonContainer.setPreferredSize(new Dimension(175, 175));
        undobuttonContainer.setLayout(new GridBagLayout());
        undobuttonContainer.add(undoButton);

        leftfiller.add(undobuttonContainer, BorderLayout.NORTH);
        undoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (board.getlastmovei() != board.getsize() && board.getlastmovej() != board.getsize()
                        && board.getstate() != 1) {
                    undoButton.setPreferredSize(new Dimension(175, 175));

                    undoButton.getParent().revalidate();
                    undoButton.getParent().repaint();
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                undoButton.setPreferredSize(new Dimension(150, 150));

                undoButton.getParent().revalidate();
                undoButton.getParent().repaint();

            }
        });
    }

    private void handlepauseClick(ActionEvent e) {
        if (board.getstate() != 1) {
            pauseTimer();
            BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
            this.paint(image.getGraphics());

            float[] matrix = {
                    0.02f, 0.02f, 0.02f, 0.02f, 0.02f, 0.02f,
                    0.02f, 0.02f, 0.02f, 0.02f, 0.02f, 0.02f,
                    0.02f, 0.02f, 0.02f, 0.02f, 0.02f, 0.02f,
                    0.02f, 0.02f, 0.02f, 0.02f, 0.02f, 0.02f,
                    0.02f, 0.02f, 0.02f, 0.02f, 0.02f, 0.02f,
                    0.02f, 0.02f, 0.02f, 0.02f, 0.02f, 0.02f,
            };

            BufferedImageOp op = new ConvolveOp(new Kernel(6, 6, matrix));
            BufferedImage blurredImage = op.filter(image, null);
            Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
            JDialog pauseDialog = new JDialog(frame, "Amoba", true);
            pauseDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            pauseDialog.setPreferredSize(new Dimension(1600, 1000));
            pauseDialog.setUndecorated(true);
            pauseDialog.setLocation(frame.getLocation());

            PausePanel pausePanel = new PausePanel(blurredImage, gui, board);
            pauseDialog.setContentPane(pausePanel);
            pauseDialog.pack();
            pauseDialog.setVisible(true);
            resumeTimer();
        }
    }

    private void handleundoClick(ActionEvent e) {
        if (board.getlastmovei() != board.getsize() && board.getlastmovej() != board.getsize()
                && board.getstate() != 1 && board.getgamemode() == 0) {
            board.getboard()[board.getlastmovei()][board.getlastmovej()] = 0;
            board.switchplayer();
            board.setlastmovei(board.getsize());
            board.setlastmovej(board.getsize());
            board.setround(board.getround() - 1);
            boardPanel.update();
            updateRound();
        }

        if (board.getlastmovei() != board.getsize() && board.getlastmovej() != board.getsize()
                && board.getstate() != 1 && board.getgamemode() == 1) {
            board.getboard()[board.getlastmovei()][board.getlastmovej()] = 0;
            board.getboard()[ai.getlastmovei()][ai.getlastmovej()] = 0;
            board.setlastmovei(board.getsize());
            board.setlastmovej(board.getsize());
            board.setround(board.getround() - 2);
            boardPanel.update();
            updateRound();
        }
    }

    private void startTimer() {
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.getstate() != 1) {
                    board.setseconds(board.getseconds() + 1);
                    int hours = board.getseconds() / 3600;
                    int minutes = (board.getseconds() % 3600) / 60;
                    int seconds = board.getseconds() % 60;

                    String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                    timerLabel.setText(timeString);
                }
            }
        });
        gameTimer.start();
    }

    public void updateRound() {
        int currentRound = board.getround() / 2;

        roundLabel.setText("" + currentRound);
    }

    public void reset() {
        roundLabel.setText("1");
        timerLabel.setText("00:00:00");

        board.setseconds(0);

    }

    private void pauseTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
    }

    private void resumeTimer() {
        if (gameTimer != null) {
            gameTimer.start();
        }
    }

    public JLabel getRoundLabel() {
        return roundLabel;
    }

    public JLabel getTimerLabel() {
        return timerLabel;
    }
}