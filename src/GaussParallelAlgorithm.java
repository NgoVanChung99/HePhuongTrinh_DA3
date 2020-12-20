import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GaussParallelAlgorithm {
	
	static double u;
	static int i,j,k;
	public double[][] loadData(String filePath) throws InterruptedException {
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
	
	@SuppressWarnings("deprecation")
	public static void solveEquation(double[][] a) {
		int m = a.length;
		int n = a[0].length;

		double[] x = new double[m];
		double sum;
		if (n > m + 1)
			System.out.println("Phuong trinh co vo so nghiem");
		else {
			for (k = 0; k < m - 1; k++) {
				for (i = k + 1; i < m; i++) {
					Thread thread1 = new Thread() {
						public void run() {
							u = a[i][k] / a[k][k];
						}
					};
					thread1.run();
					for (j = k; j < m + 1; j++) {
						Thread thread2 = new Thread() {
							public void run() {
								a[i][j] -= u * a[k][j];
							}
						};
						thread2.run();
						thread2.stop();
					}
					thread1.stop();
					
				}
			}
			m = m - 1;
			x[m] = a[m][m + 1] / a[m][m];			
			for (int i = m - 1; i >= 0; i--) {
				sum = 0;
				for (int j = i + 1; j <= m; j++) {
					sum += a[i][j] * x[j];
				}
				x[i] = (a[i][m + 1] - sum) / a[i][i];
			};
			System.out.println("He phuong trinh tren co nghiem la: ");
			for (int o = 0; o < x.length; o++) {
				System.out.println("x[" + o + "] = " + (double) Math.round(x[o] * 100000) / 100000);
			}
		}

	}
	
	
	public static void main(String[] args) throws InterruptedException {
		GaussParallelAlgorithm print = new GaussParallelAlgorithm();
		double matrix[][] = print.loadData("matrix.txt");
		System.out.println("Giai he phuong trinh: ");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++)
				System.out.print(Math.round(matrix[i][j] * 100000) / 100000 + " ");
			System.out.println();
		}
		solveEquation(matrix);

	}
}

