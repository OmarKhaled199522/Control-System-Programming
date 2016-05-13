import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GraphFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	public GraphFrame(double graphArr[][]) {
		panel = new GraphPainter(graphArr);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Graph");
		setLayout(null);
		setBounds(800,0,1100,900);
		setVisible(true);
		panel.setBackground(Color.white);
		panel.setBounds(0,0,1100,850);
		add(panel);
	}
	public JPanel getPanel() {
		return panel;
	}
	

}
