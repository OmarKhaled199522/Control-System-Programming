import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Options extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private double graphArr[][];
	private int fromNode , toNode;
	private JLabel fromLabel, toLabel, gainLabel,functionRatio,deleteLabel;
	private JButton enter, calculate , add,delete;
	private JTextField fromTextField, toTextField, gainTextField,funcFrom,funcTo , deleteFrom,deleteTo;
	private GraphFrame graphFrame;
	private ArrayList<ArrayList<GraphConnection>> graphEdges;
	private ArrayList<GraphConnection> tempConnection;
	private GraphBuilder builder;
	private boolean fromEdges [];
	
	public Options(int nodesNo) {
		this.getContentPane().setBackground(Color.BLACK);
		graphArr = new double[nodesNo][nodesNo];
		graphFrame = new GraphFrame(graphArr);
		Font font = new Font("Serif", Font.PLAIN + Font.BOLD, 35);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("User Input");
		setLayout(null);
		setBounds(0, 0, 800, 800);

		fromLabel = new JLabel("From");
		fromLabel.setBounds(50, 100, 100, 25);
		fromLabel.setFont(font);
		fromLabel.setForeground(Color.WHITE);
		add(fromLabel);

		fromTextField = new JTextField();
		fromTextField.setBounds(175, 90, 150, 50);
		fromTextField.setFont(font);
		add(fromTextField);

		toLabel = new JLabel("To");
		toLabel.setBounds(450, 100, 100, 25);
		toLabel.setFont(font);
		toLabel.setForeground(Color.WHITE);
		add(toLabel);

		toTextField = new JTextField();
		toTextField.setBounds(560, 90, 150, 50);
		toTextField.setFont(font);
		add(toTextField);

		gainLabel = new JLabel("Gain");
		gainLabel.setBounds(50, 225, 100, 25);
		gainLabel.setFont(font);
		gainLabel.setForeground(Color.WHITE);
		add(gainLabel);

		gainTextField = new JTextField();
		gainTextField.setBounds(175, 212, 150, 50);
		gainTextField.setFont(font);
		add(gainTextField);

		enter = new JButton("Enter Edge");
		enter.setFont(font);
		enter.setBackground(Color.YELLOW);
		enter.setBounds(450, 220, 250, 50);
		add(enter);
		
		functionRatio = new JLabel("Get Transfer function between");
		functionRatio.setBounds(0, 350, 330, 50);
		functionRatio.setFont(new Font("Serif", Font.CENTER_BASELINE+ Font.BOLD, 25));
		functionRatio.setForeground(Color.WHITE);
		add(functionRatio);
		
		funcFrom = new JTextField();
		funcFrom.setBounds(340, 350, 100, 50);
		funcFrom.setFont(font);
		add(funcFrom);
		
		funcTo = new JTextField();
		funcTo.setBounds(465, 350, 100, 50);
		funcTo.setFont(font);
		add(funcTo);
		
		add = new JButton("Set");
		add.setFont(font);
		add.setBackground(Color.YELLOW);
		add.setBounds(600, 350, 200, 50);
		add(add);
		
		
		deleteLabel = new JLabel("     Delete an edge between");
		deleteLabel.setBounds(0, 450, 330, 50);
		deleteLabel.setFont(new Font("Serif", Font.CENTER_BASELINE+ Font.BOLD, 25));
		deleteLabel.setForeground(Color.WHITE);
		add(deleteLabel);
		
		deleteFrom = new JTextField();
		deleteFrom.setBounds(340, 450, 100, 50);
		deleteFrom.setFont(font);
		add(deleteFrom);
		
		deleteTo = new JTextField();
		deleteTo.setBounds(465, 450, 100, 50);
		deleteTo.setFont(font);
		add(deleteTo);
		
		delete = new JButton("Delete");
		delete.setFont(font);
		delete.setBackground(Color.YELLOW);
		delete.setBounds(600, 450, 200, 50);
		add(delete);
		
		
		

		calculate = new JButton("Calculate");
		calculate.setBackground(Color.YELLOW);
		calculate.setFont(font);
		calculate.setBounds(300, 620, 200, 100);
		add(calculate);
		
		
		
		

		Handler handler = new Handler();
		enter.addActionListener(handler);
		calculate.addActionListener(handler);
		add.addActionListener(handler);
		delete.addActionListener(handler);

		setVisible(true);
	}

	private class Handler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				if (event.getSource() == calculate) {
					// do calculations
					////// here is the linkage part
					
					graphEdges = new ArrayList<ArrayList<GraphConnection>>();
					int numOfNodes = graphArr.length;
					fromEdges = new boolean [numOfNodes];
					
					for(int i = 0; i < numOfNodes; i++){
						
						tempConnection = new ArrayList<GraphConnection>();
						
						for(int j = 0; j < numOfNodes; j++){
							
							if(graphArr[i][j] != 0){
								
								fromEdges[j] = true;
								
								GraphConnection tempGraphConnection = 
										new GraphConnection(i, j, graphArr[i][j]); 
								
								tempConnection.add(tempGraphConnection);
							}
						}
						
						graphEdges.add(tempConnection);
					}
					
					SignalImplementation signal = new SignalImplementation();
					if(fromNode == 0&& toNode == graphArr.length-1){
						builder = signal.input(numOfNodes, graphEdges, fromNode, toNode , true);
						new OutPut(builder);
					}
					else if (fromNode == 0){
						builder = signal.input(numOfNodes, graphEdges, fromNode, toNode, false);
						new OutPut(builder);
						
					}
					else{
						builder = signal.input(numOfNodes, graphEdges, 0, toNode, false);
						new OutPut(builder);
						double func1 = builder.getTransferResult();
						builder = signal.input(numOfNodes, graphEdges, 0, fromNode, false);
						new OutPut(builder);
						double func2 = builder.getTransferResult();
						JOptionPane.showMessageDialog(null,
								"Transfer function = " +(func1/func2), "Output",
								JOptionPane.PLAIN_MESSAGE);
						
					}
					
					//end calculations
				} else if (event.getSource() == enter) {
					int from = Integer.parseInt(fromTextField.getText());
					int to = Integer.parseInt(toTextField.getText());
					double gain = Double.parseDouble(gainTextField.getText());
					if (from == graphArr.length)
						throw new RuntimeException();
					else if (to == 1)
						throw new RuntimeException();
					else{
					graphArr[from-1][to-1] = gain;
					graphFrame.getPanel().repaint();
					}
				}
				else if(event.getSource() == add){
					fromNode = Integer.parseInt(funcFrom.getText()) - 1;
					toNode = Integer.parseInt(funcTo.getText()) - 1;
					JOptionPane.showMessageDialog(null,
							"Done", "Done Message",
							JOptionPane.PLAIN_MESSAGE);
				}
				else if(event.getSource() == delete){
					int from = Integer.parseInt(deleteFrom.getText());
					int to = Integer.parseInt(deleteTo.getText());
					graphArr[from-1][to-1] = 0;
					graphFrame.getPanel().repaint();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						"There Is Something Wrong in Logic!", "WARNING!",
						JOptionPane.PLAIN_MESSAGE);
			}
		}

	}

}
