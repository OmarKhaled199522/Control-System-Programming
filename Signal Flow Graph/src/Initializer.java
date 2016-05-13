
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Initializer extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JTextField noOfNodesInput;
	public Initializer(){
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Number Of Nodes");
		setSize(700, 300);
		setLayout(null); 
		label = new JLabel("Enter the number of Nodes");
		label.setFont(new Font("Serif", Font.PLAIN + Font.BOLD, 40));
		label.setForeground(Color.RED);
		label.setBounds(80, 20, 500, 40);
		
		noOfNodesInput = new JTextField();
		noOfNodesInput.setFont(new Font("Serif", Font.PLAIN + Font.BOLD, 30));
		noOfNodesInput.setBounds(110, 110, 400, 50);
		noOfNodesInput.addActionListener(new Handler());
		
		add(label);
		add(noOfNodesInput);
		setVisible(true);
	}
	private class Handler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int nodesNo =Integer.parseInt( e.getActionCommand());
			new Options(nodesNo);
			dispose();
		}
		
	}
	

}
