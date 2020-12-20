package HePT;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Main {

	private JFrame frame;
	static double u;
	static int i, j, k;
	private JTextArea txtHPT;
	private JTextArea txtResult;
	double Matran[][]=null;
	private JTextField txtFilePath;
	String str;
	private JTextArea txtMultil;
	private JTextField txtColumn;
	private JTextField txtRow;
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 912, 656);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnImport = new JButton("Import");
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Importing");
				txtMultil.append("Importing \n");
				String FilePath=txtFilePath.getText();
				try {
					double Matrix[][] = Main.loadData(FilePath);
					if(Matrix==null)
					{				
						txtHPT.setText("");
						txtHPT.append("Thieu duong dan file");
					}
					else {
						txtHPT.setText("");
						HePhuongTrinh();
					}	
					System.out.println("Import succsess");
					txtMultil.append("Import succsess \n");
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}					
			}

			private void HePhuongTrinh() {
				// TODO Auto-generated method stub				
				txtHPT.append("Giai he phuong trinh \n");
				try {
					String FilePath=txtFilePath.getText();
					double Matrix[][] = Main.loadData(FilePath);
					//String cot=String.valueOf(Matrix[0].length);
					
					//String hang=String.valueOf(Matrix.length);
					
					Matran=Matrix;
					String tmp="";
					for(int i=0;i<Matrix.length;i++)
					{
						for(int j=0;j<Matrix[i].length;j++)
						{
							if(j == 0)
							{
								tmp = String.valueOf(Matrix[i][j])+" X"+(j+1)+" ";
								
							}
							else {
								if(j < (Matrix[i].length-2))
								{
									if(Matrix[i][j] >= 0)
									{
										tmp = " +  "+String.valueOf(Math.abs(Matrix[i][j]) )+" X"+(j+1)+" ";
									}
									else {
										tmp = " -  "+String.valueOf(Math.abs(Matrix[i][j]))+" X"+(j+1)+" ";
									}
									
								}
								if(j == (Matrix[i].length-1)) {
									tmp = String.valueOf("="+Matrix[i][j])+"\n";
								}				
							}
										
							txtHPT.append(tmp);
						}
					}
					
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}			
			}
		});
		btnImport.setBounds(40, 166, 91, 21);
		frame.getContentPane().add(btnImport);

		JButton btnResult = new JButton("Result");
		btnResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Result");
				txtMultil.append("Result");
				if(Matran==null)
				{
					txtResult.setText("");
					txtResult.append("Loi");
				}
				else {
					txtResult.setText("");
					Result(Matran);
				}
				
				System.out.println("Result succsess");
				txtMultil.append("Result succsess");
			}

			private void Result(double[][] a) {
				// TODO Auto-generated method stub
				int m = a.length;
				int n = a[0].length;
				String tmpReSult="";
				double[] x = new double[m];
				double sum;
				if (n > m + 1)
				{
					txtResult.append("He phuong trinh vo nghiem");
				}
				else {
					if((m + 1) > n)
					{
						txtResult.append("He phuong trinh vo nghiem");
					}
					else {
						for (k = 0; k < m - 1; k++) {
							
							txtMultil.append("PT so :"+k+"\n");
							
							for (i = k + 1; i < m; i++) {
								txtMultil.append("Thead :"+i+"\n");
								Thread thread1 = new Thread() {
									public void run() {
										u = a[i][k] / a[k][k];
									}
								};
								txtMultil.append("u = "+ u +" \n ");
								txtMultil.append("Gia tri hang: \n ");
								thread1.run();
								for (j = k; j < m + 1; j++) {
									Thread thread2 = new Thread() {
										public void run() {
											a[i][j] -= u * a[k][j];
										}
									};
									thread2.run();
									thread2.stop();
									txtMultil.append(" "+a[i][j]+ "   ");
								}
								thread1.stop();
								
								txtMultil.append("\n");
							}
							txtMultil.append("\n");
						}
						m = m - 1;
						x[m] = a[m][m + 1] / a[m][m];
						for (int i = m - 1; i >= 0; i--) {
							sum = 0;
							for (int j = i + 1; j <= m; j++) {
								sum += a[i][j] * x[j];
							}
							x[i] = (a[i][m + 1] - sum) / a[i][i];
						}
						;
						txtResult.append("He phuong trinh co nghiem la:\n");
						for (int o = 0; o < x.length; o++) {
							tmpReSult="x[" + o + "] = " + (double) Math.round(x[o] * 100000) / 100000;
							txtResult.append(tmpReSult+"\n");
						}
					}
				}
				
				
				
				
			}
		});
		btnResult.setBounds(40, 352, 77, 21);
		frame.getContentPane().add(btnResult);
		
		txtFilePath = new JTextField();
		txtFilePath.setBounds(117, 7, 267, 19);
		frame.getContentPane().add(txtFilePath);
		txtFilePath.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("File Path");
		lblNewLabel_3.setBounds(40, 10, 50, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				txtFilePath.setText("");
				txtColumn.setText("");
				txtRow.setText("");
				txtHPT.setText("");
				txtResult.setText("");
				txtMultil.setText("");
				Matran=null;
			}
		});
		btnReset.setBounds(789, 352, 77, 21);
		frame.getContentPane().add(btnReset);
		
		JButton btnBrowser = new JButton("Browser");
		btnBrowser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Chon duong dan");
				txtMultil.append("Chon duong dan \n");
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(btnBrowser);
				str = fileChooser.getSelectedFile().getAbsolutePath();
				txtFilePath.setText(str);
				System.out.println("Chon duong dan succsess");  
				txtMultil.append("Chon duong dan succsess \n");
			}
		});
		btnBrowser.setBounds(396, 6, 91, 21);
		frame.getContentPane().add(btnBrowser);
		
		JScrollPane scrollPaneHPT = new JScrollPane();
		scrollPaneHPT.setBounds(40, 383, 826, 210);
		frame.getContentPane().add(scrollPaneHPT);
		
		txtResult = new JTextArea();
		scrollPaneHPT.setViewportView(txtResult);
		txtResult.setEditable(false);
		
		JScrollPane scrollPane_Status = new JScrollPane();
		scrollPane_Status.setBounds(40, 197, 447, 128);
		frame.getContentPane().add(scrollPane_Status);
		
		txtHPT = new JTextArea();
		scrollPane_Status.setViewportView(txtHPT);
		txtHPT.setEditable(false);
		
		JScrollPane scrollPane_Result = new JScrollPane();
		scrollPane_Result.setBounds(524, 60, 335, 265);
		frame.getContentPane().add(scrollPane_Result);
		
		txtMultil = new JTextArea();
		scrollPane_Result.setViewportView(txtMultil);
		txtMultil.setEditable(false);
		txtMultil.setWrapStyleWord(true);
		txtMultil.setLineWrap(true);
		
		JButton btnRamdom = new JButton("Ramdom");
		btnRamdom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtHPT.setText("");
				txtResult.setText("");
				//txtMultil.setText("");
				try {
					System.out.println("Ramdom matrix");
					txtMultil.append("Ramdom matrix \n");
					File  f= new File( txtFilePath.getText());
								
					FileWriter fw = new FileWriter(f,false);
					int Column=Integer.parseInt(txtColumn.getText());
					int Row=Integer.parseInt(txtRow.getText());
					
					fw.write(Row +" ");
					fw.write(Column +"\n");
					
					Matran = new double[Row][Column];
					for (int i=0; i< Row; i++) {
					    for (int j=0; j< Column; j++) {
					    	
					    	Matran[i][j] = (double)(Math.random()*10);
					    	
					    	fw.write(Matran[i][j]+" ");
					    }  
					    fw.write("\n");
					}
					fw.close();
					JOptionPane.showMessageDialog(frame," Ramdom succsess");
					System.out.println("Ramdom succsess");
					txtMultil.append("Ramdom succsess \n");
				}catch (NumberFormatException i  ) {
					JOptionPane.showMessageDialog(frame," Ramdom fail");
					
				} catch (IOException e1) {
					
					JOptionPane.showMessageDialog(frame," Ramdom fail");
					
				}
				
			}
		});
		btnRamdom.setBounds(396, 101, 91, 21);
		frame.getContentPane().add(btnRamdom);
		
		txtColumn = new JTextField();
		txtColumn.setBounds(117, 51, 267, 19);
		frame.getContentPane().add(txtColumn);
		txtColumn.setColumns(10);
		
		txtRow = new JTextField();
		txtRow.setBounds(117, 102, 267, 19);
		frame.getContentPane().add(txtRow);
		txtRow.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Column");
		lblNewLabel.setBounds(40, 54, 50, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Row");
		lblNewLabel_1.setBounds(40, 105, 50, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Console");
		lblNewLabel_2.setBounds(524, 32, 50, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		
	}
	
	public static double[][] loadData(String filePath) throws InterruptedException {
		double matrix[][] = null;
		Path path = Paths.get(filePath);
		Charset charset = Charset.forName("US-ASCII");
		try {
			BufferedReader reader = Files.newBufferedReader(path, charset);
			String line0 = reader.readLine(); // skip a first line
			String[] s = line0.split(" ");
			matrix = new double[Integer.parseInt(s[0])][Integer.parseInt(s[1])];
			String line1 = null;
			int count = 0;
			while ((line1 = reader.readLine()) != null) {
				String[] readElemnents = line1.split(" ");
				for (int i = 0; i < readElemnents.length; i++)
					matrix[count][i] = Double.parseDouble(readElemnents[i]);
				count++;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return matrix;
	}
}
