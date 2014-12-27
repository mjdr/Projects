package math;
import java.io.Serializable;

public class Matrix implements Cloneable , Serializable
{
	/**
	 * hhh
	 */
	private static final long serialVersionUID = 7393409026592366658L;
	private int n , m;
	private float[][] data;
	/**
	 *Create new matrix NxM 
	 */
	public Matrix(int n , int m) 
	{
		this.n = n;
		this.m = m;
		data = new float[n][m];
	}
	public Matrix(float[][] data)
	{
		if(data == null || data.length == 0 || data[0].length == 0)
			throw new IllegalArgumentException("Data is null!");
		this.data = data;
		n = data.length;
		m = data[0].length;
	}
	public Matrix(Matrix mat)
	{
		if(mat == null || mat.data == null)
			throw new IllegalArgumentException("Data is null!");
		n = mat.data.length;
		m = mat.data[0].length;
		data = new float[n][m];
		for(int i = 0;i < n;i++)
			System.arraycopy(mat.data[i], 0, data[i], 0, m);
	}
	public void set(int i , int j , float val)
	{
		data[i][j] = val;
	}
	public float get(int i , int j)
	{
		return data[i][j];
	}
	@Override
	protected Matrix clone() 
	{
		return new Matrix(this);
	}
	public Matrix multiplay(Matrix b)
	{
		return new Matrix(multiply(data, b.transposition().data)).transposition();
	}
	
	private float[][] multiply(final float A[][], final float B[][]) {
		        int ni = A.length;
		        int nk = A[0].length;
		        int nj = B[0].length;
		        float C[][] = new float[ni][nj];
		        if (B.length != nk || C.length != ni || C[0].length != nj) {
		            throw new IllegalArgumentException("Error in Matrix.multiply, incompatible sizes");
		        }

		        for (int i = 0; i < ni; i++)
		            for (int j = 0; j < nj; j++) {
		                C[i][j] = 0F;
		                for (int k = 0; k < nk; k++)
		                    C[i][j] = C[i][j] + A[i][k] * B[k][j];
		            }
		        return C;
		    }
	
	public Matrix multiplay(float k)
	{
		Matrix res = clone();
		for(int i = 0;i < n;i++)
			for(int j = 0;j < m;j++)
				res.data[i][j] *= k;
		return res;
	}
	public Matrix transposition()
	{
		Matrix res = new Matrix(m, n);

		for(int i = 0;i < n;i++)
			for(int j = 0;j < m;j++)
				res.data[j][i] = data[i][j];
		return res;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("	Matrix ").append(n).append('x').append(m).append('\n');
		for(int y = 0;y < m;y++)
		{
			for(int x = 0;x < n;x++)
				sb.append(data[x][y]).append("\t");
			sb.append('\n');
		}
		return sb.toString();
	}
	public void dispose() 
	{
		data = null;
	}
	
	
	
	
    public Matrix invert() {
    	Matrix mtx = clone();
        int n = mtx.n;
        int row[] = new int[n];
        int col[] = new int[n];
        double temp[] = new double[n];
        int hold, I_pivot, J_pivot;
        double pivot, abs_pivot;

        if (mtx.m != n) 
            throw new IllegalArgumentException("Error in Matrix.invert, inconsistent array sizes.");
        
        // установиим row и column как вектор изменений.
        for (int k = 0; k < n; k++) {
            row[k] = k;
            col[k] = k;
        }
        // начало главного цикла
        for (int k = 0; k < n; k++) {
            // найдем наибольший элемент для основы
            pivot = mtx.get(row[k],col[k]);
            I_pivot = k;
            J_pivot = k;
            for (int i = k; i < n; i++) {
                for (int j = k; j < n; j++) {
                    abs_pivot = Math.abs(pivot);
                    if (Math.abs(mtx.get(row[i],col[j])) > abs_pivot) {
                        I_pivot = i;
                        J_pivot = j;
                        pivot = mtx.get(row[i],col[j]);
                    }
                }
            }
            if (Math.abs(pivot) < 1.0E-10)
                throw new IllegalArgumentException("Matrix is singular !");
                
            //перестановка к-ой строки и к-ого столбца с стобцом и строкой, содержащий основной элемент(pivot основу)
            hold = row[k];
            row[k] = row[I_pivot];
            row[I_pivot] = hold;
            hold = col[k];
            col[k] = col[J_pivot];
            col[J_pivot] = hold;
            // k-ую строку с учетом перестановок делим на основной элемент
            mtx.set(row[k],col[k],(float) (1.0 / pivot)) ;
            for (int j = 0; j < n; j++) {
                if (j != k) {
                    mtx.set(row[k],col[j],mtx.get(row[k] ,col[j]) * mtx.get(row[k] ,col[k]));
                }
            }
            // внутренний цикл
            for (int i = 0; i < n; i++) {
                if (k != i) {
                    for (int j = 0; j < n; j++) {
                        if (k != j) {
                            mtx.set(row[i],col[j],mtx.get(row[i],col[j]) - mtx.get(row[i] , col[k]) * mtx.get(row[k] , col[j]));
                        }
                    }
                    mtx.set(row[i] , col[k] , -mtx.get(row[i],col[k]) * mtx.get(row[k],col[k]));
                }
            }
        }
        // конец главного цикла

        // переставляем назад rows
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                temp[col[i]] = mtx.get(row[i], j);
            }
            for (int i = 0; i < n; i++) {
                mtx.set(i, j, (float) temp[i]);
            }
        }
        // переставляем назад columns
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[row[j]] = mtx.get(i, col[j]);
            }
            for (int j = 0; j < n; j++) {
                mtx.set(i, j , (float) temp[j]);
            }
        }
        return mtx;
    }
	
}
