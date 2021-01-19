import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {
	private int width;
	private int height;

	private BufferedImage bImg;
	private Graphics g;

	// Конструктор
	public JImageDisplay(int width, int height) {
		this.width = width;
		this.height = height;

		bImg = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(600,600));
		g = bImg.getGraphics();
		clearImage();
		repaint();
	}

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bImg, 0, 0, null);
    }

	public void clearImage() {
		this.repaint();
	}

	// Нарисовать пиксель
	public void drawPixel(int x, int y, Color color) {
		g.setColor(color);
		g.fillRect(x, y, 1, 1);
		this.repaint();
	}

}