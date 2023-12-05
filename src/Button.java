import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    private Image originalIconImage;

    public Button() {
        super();
        setContentAreaFilled(false);
        setBorderPainted(true);
        setOpaque(false);
    }

    public Button(Icon icon) {
        super(icon);
        if (icon instanceof ImageIcon) {
            this.originalIconImage = ((ImageIcon) icon).getImage();
        }
        setContentAreaFilled(false);
        setBorderPainted(true);
        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    @Override
    public void setIcon(Icon icon) {
        if (icon instanceof ImageIcon) {
            this.originalIconImage = ((ImageIcon) icon).getImage();
        }
        super.setIcon(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Don't call super.paintComponent(g)

        if (originalIconImage != null) {
            int btnWidth = getWidth();
            int btnHeight = getHeight();
            int imgWidth = originalIconImage.getWidth(this);
            int imgHeight = originalIconImage.getHeight(this);

            // Find the scale that will fit the image within the button, preserving aspect
            // ratio
            double widthScale = (double) btnWidth / imgWidth;
            double heightScale = (double) btnHeight / imgHeight;
            double scale = Math.min(widthScale, heightScale);

            // Calculate new image dimensions that will fit within the button
            int newImgWidth = (int) (imgWidth * scale);
            int newImgHeight = (int) (imgHeight * scale);

            // Calculate the position to center the image on the button
            int x = (btnWidth - newImgWidth) / 2;
            int y = (btnHeight - newImgHeight) / 2;

            // Use higher quality rendering hints for scaling
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g2d.drawImage(originalIconImage, x, y, newImgWidth, newImgHeight, this);
        } else {
            // If no image to paint, call the superclass implementation
            super.paintComponent(g);
        }
    }
}