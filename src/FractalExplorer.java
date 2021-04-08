import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class FractalExplorer {
    private JFrame jFrame = new JFrame();
    private JImageDisplay jImageDisplay;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;
    private int displaySize;

    public FractalExplorer(int displaySize) {
        this.displaySize = displaySize;
        this.range = new Rectangle2D.Double();
        this.fractalGenerator = new Mandelbrot();
        fractalGenerator.getInitialRange(range);
    }

    public void createAndShowGUI(){
        JButton jButton = new JButton("Reset Display");
        jFrame.setLayout(new BorderLayout());
        jImageDisplay = new JImageDisplay(displaySize,displaySize);
        jFrame.add(jImageDisplay,BorderLayout.CENTER);
        jButton.addActionListener(new ResetHandler());
        jFrame.add(jButton,BorderLayout.SOUTH);
        jFrame.setTitle("Генератор фракталов");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.addMouseListener(new MouseHandler());
        drawFractal();
        jImageDisplay.repaint();
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setResizable(false);
    }

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();

    }

    private void drawFractal(){
        for (int x = 0; x < jImageDisplay.getBufferedImage().getWidth(); x++){
            double xCoord = fractalGenerator.getCoord(range.x,  range.x  +  range.width, displaySize, x);
            for (int y = 0; y < jImageDisplay.getBufferedImage().getHeight(); y++){
                double yCoord = fractalGenerator.getCoord(range.y,  range.y  +  range.height, displaySize, y);
                if (fractalGenerator.numIterations(xCoord,yCoord)==-1) {
                    jImageDisplay.drawPixel(x,y,0);
                } else {
                    float hue = 0.7f + (float) fractalGenerator.numIterations(xCoord,yCoord) / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    jImageDisplay.drawPixel(x,y,rgbColor);
                }
            }
        }
        jImageDisplay.repaint();
    }

    private class ResetHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            range = new Rectangle2D.Double();
            fractalGenerator.getInitialRange(range);
            drawFractal();
        }
    }

    /**
     * При получении события о щелчке мышью, класс
     * отображает пиксельные кооринаты щелчка в область фрактала, а затем вызывает
     * метод генератора recenterAndZoomRange() с координатами, по которым
     * произошел клик, и масштабом 0.5. Таким образом, нажимая на какое-либо место на
     * фрактальном отображении, изображение увеличивается.
     */
    private class MouseHandler extends MouseAdapter
    {
        public void mouseClicked(MouseEvent e)
        {
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width,
                    displaySize, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height,
                    displaySize, e.getY());

            fractalGenerator.recenterAndZoomRange(range,xCoord, yCoord, 0.5);
            drawFractal();
        }
    }
}
