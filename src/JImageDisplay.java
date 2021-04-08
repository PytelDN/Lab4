import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {

    private java.awt.image.BufferedImage bufferedImage;

    public JImageDisplay(int width, int height) {

        this.bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new java.awt.Dimension(width,height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void clearImage(int x, int y){
        bufferedImage.setRGB(0, 0 , bufferedImage.getWidth(), bufferedImage.getHeight(), null, 0,1);
    }

    public void drawPixel(int x, int y, int rgbColor){
        bufferedImage.setRGB(x,y,rgbColor);
    }
}
