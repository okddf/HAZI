import javax.swing.*;
import java.awt.*;

public class WinContainer extends JPanel {
    private Image backgroundImage;
    private GameLogic board;

    public WinContainer(GameLogic b, String fileName) {
        board = b;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0;
        gbc.weighty = 0;

        backgroundImage = new ImageIcon(fileName).getImage();
        int hours = board.getseconds() / 3600;
        int minutes = (board.getseconds() % 3600) / 60;
        int seconds = board.getseconds() % 60;

        // format the string to HH:mm:ss
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        JLabel timerLabel = new JLabel(timeString, SwingConstants.CENTER);
        JLabel roundLabel = new JLabel("" + board.getround() / 2, SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 75));
        timerLabel.setForeground(new Color(255, 239, 220));

        roundLabel.setFont(new Font("Arial", Font.BOLD, 75));
        roundLabel.setForeground(new Color(255, 239, 220));

        JPanel timerContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            }
        };
        timerContainer.setOpaque(false);
        timerContainer.setPreferredSize(new Dimension(1030, 470));
        timerContainer.setLayout(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(280, 10, 10, 10);
        timerContainer.add(timerLabel, gbc1);

        JPanel roundContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
            }
        };
        roundContainer.setOpaque(false);
        roundContainer.setPreferredSize(new Dimension(1030, 250));
        roundContainer.setLayout(new GridBagLayout());
        roundContainer.add(roundLabel);

        add(timerContainer, gbc);
        add(roundContainer, gbc);
    }

    public void setImage(String fileName) {
        backgroundImage = new ImageIcon(fileName).getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }
}
