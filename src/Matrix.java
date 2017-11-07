import java.util.Scanner;

public class Matrix {
    int n, m;
    double[][] p;
    double[] res;

    Matrix(int _n, int _m) {
        if (_n > 0 & _m > 0) {
            n = _n;
            m = _m;
        } else throw new NegativeArraySizeException();

        p = new double[n][m];
        res = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                p[i][j] = 0;
            res[i] = 0;
        }
    }

    Matrix(Matrix other) {
        n = other.n;
        m = other.m;
        p = new double[n][m];
        res = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                p[i][j] = other.p[i][j];
            res[i] = other.res[i];
        }
    }

    public Matrix copy(double[][] other, double[] otherRes) {
        if (m != other[0].length || n != other.length) {
            System.out.println("Wrong array sizes");
            return this;
        } else {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++)
                    p[i][j] = other[i][j];
                res[i] = otherRes[i];
            }
        }
        return this;
    }

    public Matrix copy(Matrix other) {
        if (this == other)
            return this;
        else if (m != other.m || n != other.n) {
            System.out.println("Wrong array sizes");
            return this;
        } else {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++)
                    p[i][j] = other.p[i][j];
                res[i] = other.res[i];
            }
        }
        return this;
    }

    private void copyRes(double[] other) {
        for (int i = 0; i < n; i++) {
            res[i] = other[i];
        }
    }

    public void viewMatrix() {
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(String.format("%.4f\t", p[i][j]));
            }
            System.out.println();
        }
    }

    public Matrix mulMatrixes(Matrix other) {
        Matrix result = new Matrix(this.n, other.m);
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < other.m; j++) {
                for (int k = 0; k < other.n; k++) {
                    double temp = this.p[i][k] * other.p[k][j];
                    result.p[i][j] += temp;
                }
            }
        }
        result.copyRes(this.mulMatrixOnColumn(this.res));
        return result;
    }

    public double[] mulMatrixOnColumn(double[] other) {
        double[] result = new double[this.n];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                result[i] += p[i][j] * other[j];
            }
        }
        return result;
    }

    public Matrix subMatrixes(Matrix other) {
        Matrix result = new Matrix(this);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result.p[i][j] -= other.p[i][j];
            }
        }
        return result;
    }

    public double getNorm() {
        double[] temp = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (temp[i] < Math.abs(p[i][j])) {
                    temp[i] = Math.abs(p[i][j]);
                }
            }
        }
        double max = 0;
        for (int i = 0; i < n; i++) {
            if (max < temp[i]) {
                max = temp[i];
            }
        }
        return max;
    }

    public Matrix divMatrixOnDouble(double other) {
        Matrix result = new Matrix(this);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result.p[i][j] /= other;
            }
        }
        return result;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(this);
        double temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < m; j++) {
                temp = result.p[i][j];
                result.p[i][j] = result.p[j][i];
                result.p[j][i] = temp;
            }
        }
        return result;
    }

}
