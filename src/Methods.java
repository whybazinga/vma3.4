public class Methods {
    private Matrix A;
    private Matrix E;
    private Matrix B;
    private double[] b;
    private double[] x;
    double amount;
    double theoreticalAmount;

    public static final double EPS = 10E-5;

    Methods(Matrix A, Matrix E) {
        this.A = A;
        this.E = E;
        B = new Matrix(5, 5);
        amount = 0;
        theoreticalAmount = 0;
        b = new double[5];
        x = new double[5];
    }

    public void showFinalFields() {
        System.out.println("Matrix B:");
        B.viewMatrix();
        System.out.println("b vector:");
        for (double elem : b) {
            System.out.print(String.format("%.4f ", elem));
        }
        System.out.println();
        System.out.println("x vector:");
        for (double elem : x) {
            System.out.print(String.format("%.4f ", elem));
        }
        System.out.println();
        if (theoreticalAmount != 0) {
            System.out.println("Actual amount/Theoretical amount of iterations\n" + amount + " / " + theoreticalAmount);
        } else {
            System.out.println("Actual amount of iterations\n" + amount);
        }

    }

    public void showDiscrepancy() {
        double[] discrepancy = new double[A.n];
        for (int i = 0; i < A.n; i++) {
            for (int j = 0; j < A.m; j++) {
                discrepancy[i] += A.p[i][j] * x[j];
            }
            discrepancy[i] -= A.res[i];
        }
        System.out.println("Discrepancy: ");
        for (int i = 0; i < A.n; i++) {
            System.out.print(String.format("%.2E\t", discrepancy[i]));
        }
    }

    static boolean checkCond(double[] first, double[] second) {
        boolean flag = true;
        for (int i = 0; i < 5; i++) {
            if (Math.abs(first[i] - second[i]) < EPS && (first[i] != 0 && second[i] != 0)) {
                flag = false;
            }
        }
        return flag;
    }

    public void fillForJacobiOrGaussSeidel() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                B.p[i][j] = (i == j) ? 0 : -A.p[i][j] / A.p[i][i];
            }
        }
        for (int i = 0; i < 5; i++) {
            b[i] = A.res[i] / A.p[i][i];
        }
    }

    public void fillForSimpleIterationOrSeidel() {
        B.copy(E.subMatrixes(A.transpose().mulMatrixes(A).divMatrixOnDouble(A.transpose().mulMatrixes(A).getNorm())));
        b = A.transpose().mulMatrixOnColumn(A.res);
        for (int i = 0; i < 5; i++) {
            b[i] /= A.transpose().mulMatrixes(A).getNorm();
        }
    }

    public void solveSeidelOrGaussSeidel() {
        double xPrev[] = new double[5];
        while (checkCond(x, xPrev)) {
            amount++;
            for (int i = 0; i < 5; i++) {
                xPrev[i] = x[i];
            }
            for (int i = 0; i < 5; i++) {
                double sum = 0;
                for (int j = 0; j < i; j++) {
                    sum += B.p[i][j] * x[j];
                }
                for (int j = i; j < 5; j++) {
                    sum += B.p[i][j] * xPrev[j];
                }
                sum += b[i];
                x[i] = sum;
            }
        }
        theoreticalAmount = 0;
    }

    public void solveSimpleIterationOrJacobi() {
        double xPrev[] = new double[5];
        while (checkCond(x, xPrev)) {
            amount++;
            xPrev = x;
            x = B.mulMatrixOnColumn(xPrev);
            for (int i = 0; i < 5; i++) {
                x[i] += b[i];
            }
        }
        this.createTheoreticalAmount();
    }

    private void createTheoreticalAmount() {
        double normB = B.getNorm();
        double normb = 0;
        for (int i = 0; i < 5; i++)
            normb = Math.max(normb, Math.abs(b[i]));
        double numerator = Math.log(EPS * (1 - normB) / normb);
        double denumerator = Math.log(normB);
        theoreticalAmount = Math.floor(numerator / denumerator) - 1;
    }
}
