import java.util.Arrays;

/**
 * Smoke test, delete later.
 */
public class Main {
    public static void main(String[] theArgs) {
        FlatDoubleMatrix flatDoubleMatrix = new FlatDoubleMatrix(3, 3);
        double[] row1 = {1, 2, 3};
        double[] row2 = {4, 5, 6};
        double[] row3 = {7, 8, 9};
        flatDoubleMatrix.setRow(row1, 1);
        flatDoubleMatrix.setRow(row2, 2);
        flatDoubleMatrix.setRow(row3, 3);
        System.out.println(flatDoubleMatrix);

        flatDoubleMatrix.setCell(0, 2, 2);
        System.out.println(flatDoubleMatrix);

        double[] column3 = {9, 6, 3};
        flatDoubleMatrix.setColumn(column3, 3);
        System.out.println(flatDoubleMatrix);

        System.out.println(flatDoubleMatrix.getCell(3, 2));
        System.out.println(Arrays.toString(flatDoubleMatrix.getColumn(1)));
        System.out.println(Arrays.toString(flatDoubleMatrix.getRow(2)));
        System.out.println(flatDoubleMatrix.getRows());
        System.out.println(flatDoubleMatrix.getColumns());
    }
}
