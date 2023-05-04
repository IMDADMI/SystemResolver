package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class InputFrame extends JFrame {
	public static double matrix [][];
    private JTextField[][] textFields;
    private JButton submitButton;
    private Thread t1;
    public InputFrame() {}
    public InputFrame(final int n) {
        super("Matrix");
    			setLayout(new BorderLayout());
    	        JPanel inputPanel = new JPanel();
    	        inputPanel.setLayout(new GridLayout(n, n+1));
    	        textFields = new JTextField[n][n+1];

    	        for (int i = 0; i < n; i++) {
    	            for (int j = 0; j < n+1; j++) {
    	            	final int x = i;
    	            	final int y = j;
    	            	textFields[i][j] = new JTextField(x+":"+y);
    	            	textFields[i][j].setForeground(Color.GRAY);
    	            	textFields[i][j].addFocusListener(new FocusListener() {
    	            	    public void focusGained(FocusEvent e) {
    	            	        if (textFields[x][y].getText().equals(x+":"+y)) {
    	            	        	textFields[x][y].setText("");
    	            	        	textFields[x][y].setForeground(Color.BLACK);
    	            	        }
    	            	    }
    	            	    public void focusLost(FocusEvent e) {
    	            	        if (textFields[x][y].getText().isEmpty()) {
    	            	        	textFields[x][y].setForeground(Color.GRAY);
    	            	        	textFields[x][y].setText(x+":"+y);
    	            	        }
    	            	    }
    	            	    });
    	                inputPanel.add(textFields[i][j]);
    	            }
    	        }

    	        submitButton = new JButton("Submit");
    	        InputFrame.matrix = new double[n][n+1];
    	        submitButton.addActionListener(new ActionListener() {
    	        	
    	            public void actionPerformed(ActionEvent e) {
    	                boolean allFilled = true;
    	                for (int i = 0; i < n; i++) {
    	                    for (int j = 0; j < n; j++) {
    	                        String inp = textFields[i][j].getText();
    	                    	if (inp.equals("")) {
    	                            allFilled = false;
    	                            break;
    	                        }else 
    	                        	InputFrame.matrix[i][j] = Integer.parseInt(inp);
    	                        
    	                    }
    	                }
    	                if (allFilled) {
    	                	InputFrame.this.setVisible(false);
    	                } else {
    	                    JOptionPane.showMessageDialog(InputFrame.this, "Please fill in all inputs.");
    	                }
    	            }
    	        });

    	        add(inputPanel, BorderLayout.CENTER);
    	        add(submitButton, BorderLayout.SOUTH);

    	        pack();
    	        setVisible(true);
    	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		}
        
    

	public Thread getT1() {
		return t1;
	}
	public void setT1(Thread t1) {
		this.t1 = t1;
	}
	public double[][] getMatrix(int dime) throws InterruptedException {
		t1 = new InputFrame(dime).getT1();
		t1.start();
		t1.join();
		return this.matrix;
	}
}
