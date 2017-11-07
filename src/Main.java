public class Main {
    public static void main(String[] args) {
        double[][] arrE = {{1, 0, 0, 0, 0}, {0, 1, 0, 0, 0}, {0, 0, 1, 0, 0}, {0, 0, 0, 1, 0}, {0, 0, 0, 0, 1}};
        double[][] input = {{0.7424, 0.0000, -0.1939, 0.1364, 0.2273},
                {-0.0455, 0.4848, 0.0000, -0.0924, 0.0303},
                {0.0152, -0.1364, 0.8787, 0.0167, 0.0530},
                {0.0455, 0.0000, -0.1106, 0.8787, 0.0000},
                {0.0303, -0.0455, 0.2197, -0.0182, 0.6363}};
        double[] inputRes = {3.5330, -3.4254, -2.2483, 1.4120, 2.6634};
        Matrix A = new Matrix(5, 5);
        A.copy(input, inputRes);
        Matrix E = new Matrix(5, 5);
        E.copy(arrE, new double[5]);

        Methods task = new Methods(A, E);

        System.out.println("Simple Iteration Method:");
        task.fillForSimpleIterationOrSeidel();
        task.solveSimpleIterationOrJacobi();
        task.showFinalFields();
        task.showDiscrepancy();
        System.out.println("\n==========================================================\nSeidel Method:");
        task.fillForSimpleIterationOrSeidel();
        task.solveSeidelOrGaussSeidel();
        task.showFinalFields();
        task.showDiscrepancy();
        System.out.println("\n==========================================================\nGauss-Seidel Method:");
        task.fillForJacobiOrGaussSeidel();
        task.solveSeidelOrGaussSeidel();
        task.showFinalFields();
        task.showDiscrepancy();
        System.out.println("\n==========================================================\nJacobi Method:");
        task.fillForJacobiOrGaussSeidel();
        task.solveSimpleIterationOrJacobi();
        task.showFinalFields();
        task.showDiscrepancy();
    }
}
