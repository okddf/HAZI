import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class BoardPanel extends JPanel {
    private JButton[][] boardCells;
    private Runnable roundUpdateCallback;
    private GameLogic board;
    private GUI gui;
    private Ai ai;

    ImageIcon emptycell_icon = new ImageIcon("src/resources/cellbutton.jpg");
    ImageIcon x_cell_icon = new ImageIcon("src/resources/cellbutton_x.jpg");
    ImageIcon o_cell_icon = new ImageIcon("src/resources/cellbutton_o.jpg");
    ImageIcon x_cell_icon_hover = new ImageIcon("src/resources/cellbutton_x_hover.jpg");
    ImageIcon o_cell_icon_hover = new ImageIcon("src/resources/cellbutton_o_hover.jpg");
    ImageIcon cell_icon_o_winner = new ImageIcon("src/resources/cellbutton_o_winner.jpg");
    ImageIcon cell_icon_x_winner = new ImageIcon("src/resources/cellbutton_x_winner.jpg");

    public BoardPanel(GameLogic b, GUI g, Ai a) {
        board = b;
        gui = g;
        ai = a;
        setLayout(new SquareGridLayout(board.getsize(), board.getsize()));

        setBackground(new Color(247, 216, 183));

        boardCells = new Button[board.getsize()][board.getsize()];
        for (int i = 0; i < board.getsize(); i++) {
            for (int j = 0; j < board.getsize(); j++) {
                if (board.getboard()[i][j] == 1)
                    boardCells[i][j] = new Button(x_cell_icon);
                if (board.getboard()[i][j] == 2)
                    boardCells[i][j] = new Button(o_cell_icon);
                else
                    boardCells[i][j] = new Button(emptycell_icon);
                boardCells[i][j].addActionListener(new CellClickListener(i, j));
                boardCells[i][j].setBorderPainted(false);
                boardCells[i][j].setFocusPainted(false);
                boardCells[i][j].setContentAreaFilled(false);
                final int finalI = i;
                final int finalJ = j;

                boardCells[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (b.getboard()[finalI][finalJ] == 0 && b.validmove(finalI, finalJ)) {
                            if (b.getcurrentplayer() == 1)
                                boardCells[finalI][finalJ].setIcon(x_cell_icon_hover);
                            else if (b.getcurrentplayer() == 2)
                                boardCells[finalI][finalJ].setIcon(o_cell_icon_hover);

                            boardCells[finalI][finalJ].getParent().revalidate();
                            boardCells[finalI][finalJ].getParent().repaint();
                        }

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (b.getboard()[finalI][finalJ] == 0 && b.validmove(finalI, finalJ)) {
                            boardCells[finalI][finalJ].setIcon(emptycell_icon);

                            boardCells[finalI][finalJ].getParent().revalidate();
                            boardCells[finalI][finalJ].getParent().repaint();
                        }
                    }
                });
                add(boardCells[i][j]);
            }
        }
    }

    private class CellClickListener implements ActionListener {
        private int row, col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            handleCellClick(row, col);
        }
    }

    private void handleCellClick(int row, int col) {
        if (board.validmove(row, col)) {
            board.makemove(row, col);

            if (board.checkwin(row, col, board.getcurrentplayer())) {
                for (Integer[] coords : board.getwinners()) {
                    if (board.getcurrentplayer() == 1)
                        board.getboard()[coords[0]][coords[1]] = 3;
                    else
                        board.getboard()[coords[0]][coords[1]] = 4;
                }
                update();
                Timer timer = new Timer(1700, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        win();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }

            if (board.getgamemode() == 1 && board.getstate() != 1) {
                board.switchplayer();
                int[] move = ai.aiMove();
                if (move[0] != -1 && move[1] != -1) {
                    board.getboard()[move[0]][move[1]] = 2;
                    board.setround(board.getround() + 1);
                }
                if (board.checkwin(move[0], move[1], board.getcurrentplayer())) {
                    for (Integer[] coords : board.getwinners()) {
                        if (board.getcurrentplayer() == 1)
                            board.getboard()[coords[0]][coords[1]] = 3;
                        else
                            board.getboard()[coords[0]][coords[1]] = 4;
                    }
                    update();
                    Timer timer = new Timer(1700, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            win();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                roundUpdateCallback.run();
            }

            board.switchplayer();
            roundUpdateCallback.run();
            update();

        }

    }

    public void win() {
        board.switchplayer();
        Container topLevelContainer = SwingUtilities.getAncestorOfClass(GamePanel.class, this);

        BufferedImage image = new BufferedImage(topLevelContainer.getWidth(), topLevelContainer.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        topLevelContainer.paint(g); // This now paints the entire GamePanel
        g.dispose();

        // Applying a simple blur effect
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
        JDialog winDialog = new JDialog(frame, "Amoba", true);
        winDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        winDialog.setPreferredSize(new Dimension(1600, 1000));
        winDialog.setUndecorated(true);
        winDialog.setLocation(frame.getLocation());

        WinPanel winpanel = new WinPanel(blurredImage, board, gui);
        winDialog.setContentPane(winpanel);
        winDialog.pack();
        winDialog.setVisible(true);
    }

    public void update() {
        for (int i = 0; i < board.getsize(); i++) {
            for (int j = 0; j < board.getsize(); j++) {
                if (board.getboard()[i][j] == 1)
                    boardCells[i][j].setIcon(x_cell_icon);
                if (board.getboard()[i][j] == 2)
                    boardCells[i][j].setIcon(o_cell_icon);
                if (board.getboard()[i][j] == 0)
                    boardCells[i][j].setIcon(emptycell_icon);
                if (board.getboard()[i][j] == 3)
                    boardCells[i][j].setIcon(cell_icon_x_winner);
                if (board.getboard()[i][j] == 4)
                    boardCells[i][j].setIcon(cell_icon_o_winner);
            }
        }
        repaint();
    }

    public void setRoundUpdateCallback(Runnable callback) {
        this.roundUpdateCallback = callback;
    }

}