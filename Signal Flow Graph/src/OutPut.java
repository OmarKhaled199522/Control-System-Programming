import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class OutPut extends JFrame {
	private JLabel forwardPathsLabel = new JLabel("Forward Paths");
	private static final long serialVersionUID = 1L;
	private int startY ,startX;
	private Font font1 = new Font("Serif", Font.PLAIN + Font.BOLD , 20);
	private Font font2 = new Font("Serif", Font.PLAIN  , 20);
	public OutPut(GraphBuilder builder) {
		setTitle("OutPut");
		setLayout(null);
		setBounds(0, 0, 2000, 1000);
		forwardPathsLabel.setBounds(0, 0, 200, 50);
		forwardPathsLabel.setFont(font1);
		forwardPathsLabel.setForeground(Color.RED);
		add(forwardPathsLabel);
		startY=20 ; startX = 0 ;
		drawContents(builder.getForwardPaths(),false);
		JLabel loops = new JLabel("Loops");
		loops.setBounds(0, startY, 200, 50);
		loops.setFont(font1);
		loops.setForeground(Color.RED);
		add(loops);
		startX=0;
		startY+=20;
		drawContents(builder.getGraphcycles(),true);
		
		
		for(int i = 1;i<builder.getNonTouchingLoops().size();i++){
			if(builder.getNonTouchingLoops().get(i).size() == 0)
				continue;
			Integer order = i + 1;
			JLabel loop = new JLabel(order.toString()+ "-non touching Loops");
			loop.setBounds(0, startY, 200, 50);
			loop.setFont(font1);
			loop.setForeground(Color.RED);
			add(loop);
			startX=0;
			startY+=20;
			drawContents(builder.getNonTouchingLoops().get(i) , false);
		}
		startY+=20;
		Double delta = builder.getBigDelta();
		JLabel deltaLabel = new JLabel("\u0394 = "+delta.toString());
		deltaLabel.setBounds(0, startY, 200, 50);
		deltaLabel.setFont(font1);
		deltaLabel.setForeground(Color.ORANGE);
		add(deltaLabel);
		startX=0;
		startY+=20;
		
		double deltas[] = builder.getDeltas();
		for(int i =0;i<deltas.length;i++){
			Double deltaI = deltas[i];
			JLabel deltaILabel = new JLabel("\u0394" + new Integer(i+1).toString() +" = " + deltaI.toString());
			deltaILabel.setBounds(startX, startY, 200, 50);
			deltaILabel.setFont(font1);
			deltaILabel.setForeground(Color.BLUE);
			add(deltaILabel);
			startX +=200;
			if(startX >=1600){
				startX = 0;
				startY+=20;
			}
		}
		startX=0;
		startY+=60;
		font1 = new Font("Serif", Font.PLAIN + Font.BOLD , 30);
		JLabel result = new JLabel("Transfer Function = " +new Double(builder.getTransferResult()).toString() );
		result.setBounds(0, startY, 800, 50);
		result.setFont(font1);
		result.setForeground(Color.BLACK);
		add(result);
		

		setVisible(true);
	}
	private void drawContents( ArrayList<ArrayList<Integer>> list , boolean isGraphCycle){
		for(int i = 0;i<list.size();i++){
			String path = "";
			for(int j = 0;j<list.get(i).size();j++){
				Integer elem =list.get(i).get(j)+1; 
				path += elem.toString() + " ";
			}
			if(isGraphCycle){
				Integer elem =i +1 ;
				path = "("+elem.toString() + ") " + path;
			}
			JLabel pathLabel = new JLabel(path);
			pathLabel.setBounds(startX,startY,300,50 );
			pathLabel.setFont(font2);
			startX +=200;
			if(startX >=1600){
				startX = 0;
				startY+=20;
			}
			add(pathLabel);
		}
		startX=0;
		startY+=20;
		
	}
	


}
