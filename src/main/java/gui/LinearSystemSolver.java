package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarculaLaf;

import gui.NumericalMethods.GaussSeidel;
import gui.NumericalMethods.Jacobi;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class LinearSystemSolver extends JFrame {

	private JPanel contentPane;
	private JTextField dimentions;
	JCheckBox jacobiBox = new JCheckBox("Jacobi");
	JCheckBox gaussBox = new JCheckBox("Gauss-Sidel");
	JTextArea result = new JTextArea();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlatDarculaLaf.setup();
				}catch(Exception e) {
					e.printStackTrace();
				}
				try {
					LinearSystemSolver frame = new LinearSystemSolver();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
	}

	/**
	 * Create the frame.
	 */
	public LinearSystemSolver() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1271, 431);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Choose a method");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 0, 222, 50);
		panel.add(lblNewLabel);
		
		JLabel lblMethods = new JLabel("Methods : ");
		lblMethods.setFont(new Font("Calibri", Font.BOLD, 18));
		lblMethods.setBounds(20, 43, 259, 43);
		panel.add(lblMethods);
		jacobiBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gaussBox.setSelected(false);
			}
		});
		
		
	
		jacobiBox.setBounds(20, 103, 97, 23);
		panel.add(jacobiBox);
		gaussBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jacobiBox.setSelected(false);
			}
		});
		
		
		
		gaussBox.setBounds(137, 103, 97, 23);
		panel.add(gaussBox);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("NumericalMethods");
		chckbxNewCheckBox_2.setBounds(262, 103, 127, 23);
		panel.add(chckbxNewCheckBox_2);
		
		JLabel lblEnterInputs = new JLabel("Enter Input");
		lblEnterInputs.setFont(new Font("Calibri", Font.BOLD, 18));
		lblEnterInputs.setBounds(10, 171, 259, 43);
		panel.add(lblEnterInputs);
		
		JLabel lblEnterDimention = new JLabel("Enter Dimention : ");
		lblEnterDimention.setFont(new Font("Calibri", Font.BOLD, 18));
		lblEnterDimention.setBounds(20, 250, 153, 32);
		panel.add(lblEnterDimention);
		
		dimentions = new JTextField();
		dimentions.setBounds(183, 254, 127, 23);
		panel.add(dimentions);
		dimentions.setColumns(10);
		
		JButton fill = new JButton("Solve");
		fill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dime = dimentions.getText().toString();
				if(!dime.isEmpty() && (gaussBox.isSelected()||jacobiBox.isSelected())) {
					int dim = Integer.valueOf(dime);
					boolean jacoOrGauss = gaussBox.isSelected()?true:false;
					double matrix[][] = new double[dim][dim+1];
					matrix = InputFrame.matrix;
					if(jacoOrGauss) {
						GaussSeidel gauss = new GaussSeidel();
						String gaussResult = gauss.gaussSolver(matrix, dim);
						result.setText(gaussResult);
						
					}else {
						Jacobi jacobi = new Jacobi();
						String jacobiResult = jacobi.jacobiSolver(matrix, dim);
						result.setText(jacobiResult);
					}
				}else {
					//handl error
				}
			}

			private double[][] fillMatrix(int dim) {
				double matrix[][] = new double[dim][dim+1];
				 for (int i = 0; i < dim; i++) {
		                for (int j = 0; j < dim+1; j++) {
		                    String inputValue = JOptionPane.showInputDialog("Enter value for position (" + (i+1) + "," + (j+1) + "):");
		                    matrix[i][j] = Double.parseDouble(inputValue);
		                }
		            }
				return matrix;
			}
		});
		fill.setBounds(457, 254, 127, 23);
		panel.add(fill);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(104, 189, 357, 2);
		panel.add(separator_1);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(150, 25, 311, 2);
		panel.add(separator_1_1);
		
		JButton solve_1 = new JButton("Fill the Matrix");
		solve_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dime = dimentions.getText().toString();
				if(!dime.isEmpty()) {
					int dim = Integer.valueOf(dime);
					new InputFrame(dim);
				}
					
			}
		});
		solve_1.setBounds(320, 254, 127, 23);
		panel.add(solve_1);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		
		result.setText("choose a method and click solve ");
		panel_1.add(result, BorderLayout.CENTER);
		result.setLineWrap(true);
		result.setFont(new Font("Calibri", Font.BOLD, 18));
		JScrollPane bar = new JScrollPane(result,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		JScrollBar scrollBar = new JScrollBar();
//		panel_1.add(scrollBar, BorderLayout.EAST);
//		scrollBar.add
//		JScrollBar scrollBar_1 = new JScrollBar();
//		scrollBar_1.setOrientation(JScrollBar.HORIZONTAL);
//		panel_1.add(scrollBar_1, BorderLayout.SOUTH);
		panel_1.add(bar);
	}
}
