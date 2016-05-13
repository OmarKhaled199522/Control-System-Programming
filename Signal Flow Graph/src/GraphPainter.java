import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GraphPainter extends JPanel {
	private final int WIDTH = 1100;
	private final int NODES_HEIGHT = 353;
	private final double EPS = 1e-12;
	private static final long serialVersionUID = 1L;
	private double graphArr[][];

	public GraphPainter(double graphArr[][]) {
		this.graphArr = graphArr;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		int displacement = WIDTH / (graphArr.length + 2);
		int start = displacement;
		Font font = new Font("Serif", Font.PLAIN + Font.BOLD, 20);
		g2d.setFont(font);
		for (int i = 0; i < graphArr.length; i++) {
			g2d.setColor(Color.RED);
			g2d.fillOval(start, 350, 25, 25);
			g2d.setColor(Color.BLACK);
			g2d.drawString(String.valueOf(i + 1), start + 12, 425);
			if (i == 0) {
				g2d.drawString("R(S)", start + 5, 330);
			} else if (i == graphArr.length - 1) {
				g2d.drawString("G(S)", start + 5, 330);
			}
			start += displacement;
			for (int j = 0; j < graphArr.length; j++) {
				if (graphArr[i][j] > EPS || graphArr[i][j] < -EPS) {
					String gain = String.valueOf(graphArr[i][j]);
					int x1 = displacement * (i + 1) + 10;
					int x3 = displacement * (j + 1) + 10;
					int x2 = displacement * (j + i + 2) / 2 + 10;
					int height = Math.abs(x3 - x1) / 2 + 150;
					if (j - i == 1 ) {
						g2d.drawLine(x1, NODES_HEIGHT, x3, NODES_HEIGHT);
						g2d.drawString(gain, x2, NODES_HEIGHT + 30);
						this.drawArrow(x2, NODES_HEIGHT, true, g2d);
					} else if (i == j) {
						g2d.drawOval(x1 - 25, NODES_HEIGHT, 50, 100);
						g2d.drawString(gain, x1 - 25, NODES_HEIGHT + 100 + 30);
						this.drawArrow(x1, NODES_HEIGHT + 100, true, g2d);

					} else {
						if (i < j) {
							g2d.drawArc(x1, (NODES_HEIGHT - height/2 ),(x3 - x1), height, 0, 180);
							drawArrow(x2, (NODES_HEIGHT - height /2), true,g2d);
							g2d.drawString(gain, x2, NODES_HEIGHT - height/2 -15);
						} else {
							g2d.drawArc(x3, (NODES_HEIGHT - height / 2),(x1 - x3), height, 0, -180);
							drawArrow(x2, (NODES_HEIGHT + height / 2), false,g2d);
							g2d.drawString(gain, x2, NODES_HEIGHT + height/2 +18);
						}
					}

				}
			}
		}

	}

	private void drawArrow(int x, int y, boolean isRight, Graphics2D g2d) {
		g2d.drawLine(x, y + 10, x, y - 10);
		if (isRight) {
			g2d.drawLine(x, y + 10, x + 10, y);
			g2d.drawLine(x, y - 10, x + 10, y);
		} else {
			g2d.drawLine(x, y + 10, x - 10, y);
			g2d.drawLine(x, y - 10, x - 10, y);
		}
	}

}
