import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;

public class FractalExplorer {

	public static void main(String[] args) {
		FractalExplorer explorer = new FractalExplorer(700);
		explorer.createAndShowGUI();
		explorer.drawFractal();
	}

	private int width;
	private int height;
	
	private JImageDisplay display;
	private Rectangle2D.Double range;
	private JFrame frame;
	private JButton button;
	private Mandelbrot mandelbrot;

	//Конструкторы
	public FractalExplorer() {
		this(600);

		// Создание объекта, содержащего диапазон
		this.range = new Rectangle2D.Double();

		// Создание объекта Mandelbrot
		this.mandelbrot = new Mandelbrot();

		// Задание пределов фрактала
		mandelbrot.getInitialRange(range);
	}
	
	public FractalExplorer(int size) {
		this(size, size);

		// Создание объекта, содержащего диапазон
		this.range = new Rectangle2D.Double();

		// Создание объекта Mandelbrot
		this.mandelbrot = new Mandelbrot();

		// Задание пределов фрактала
		mandelbrot.getInitialRange(range);
	}
	
	public FractalExplorer(int width, int height) {
		this.width = width;
		this.height = height;
		
		// Создание объекта, содержащего диапазон
		this.range = new Rectangle2D.Double();
		
		// Создание объекта Mandelbrot
		this.mandelbrot = new Mandelbrot();
		
		// Задание пределов фрактала
		mandelbrot.getInitialRange(range);
	}


	//Классы обработки событий нажатия кнопки и мыши
	private class resetButton implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// Сброс границ фрактала и вызов функции отрисовки
			mandelbrot.getInitialRange(range);
			FractalExplorer.this.drawFractal();
		}
	}

	private class mouseClick implements MouseListener {

		// Событие нажатия на кнопку мыщи
		public void mouseClicked(MouseEvent e) {
			//System.out.println("Mouse button clicked!");

			// Координаты клика мыши
			int x = e.getX();
			int y = e.getY();

			// Перевод координат в комплексную плоскость
			double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, display.getWidth(), x);
			double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, display.getHeight(), y);

			// Нажатие левой кнопкой мыши
			if (e.getButton() == MouseEvent.BUTTON1) {
				// Масштабирование
				mandelbrot.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
			}

			// Нажатие правой кнопкой мыши
			if (e.getButton() == MouseEvent.BUTTON3) {
				// Масштабирование
				mandelbrot.recenterAndZoomRange(range, xCoord, yCoord, 1.5);
			}

			// Перерисовка фрактала
			FractalExplorer.this.drawFractal();
		}

		/*
		 * Need just to override them (error with russian words here)
		 */
		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}
	}


	// Создание формы с компонентами
	public void createAndShowGUI() {
		// Создание окна
		this.frame = new JFrame("Фрактал Мандельброта");
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.frame.setSize(this.width, this.height);
		this.frame.setResizable(false);
		
		// Кнопка
		this.button = new JButton("Reset display");
		frame.getContentPane().add(BorderLayout.SOUTH, this.button);
		button.addActionListener(new resetButton());

		// Панель для отображения
		this.display = new JImageDisplay(this.frame.getWidth(), this.frame.getHeight());
		frame.getContentPane().add(BorderLayout.CENTER, this.display);
		display.addMouseListener(new mouseClick());
		frame.setVisible(true);
	}
	
	// Нарисовать фрактал
	public void drawFractal() {
		for (int x = 0; x < this.width; x++) {
			for (int y = 0; y < this.height; y++) {
				double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, display.getWidth(), x);
				double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, display.getHeight(), y);
				
				// Определение итерации точки в множество Мандельброта
				int numOfIter = mandelbrot.numIterations(xCoord, yCoord);

				int rgbColor;
				if (numOfIter != -1) {
					float hue = 0.7f + (float) numOfIter / 200f; 
					rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
				} 
				else {
					rgbColor = Color.HSBtoRGB(0, 0, 0);
				}

				display.drawPixel(x, y, new Color(rgbColor));
				
			}
		}
	}

}