/**
 * Smoke test, delete later.
 */
void main() {
    FlatDoubleMatrix flatDoubleMatrix = new FlatDoubleMatrix(3, 3);
    double[] row1 = {1, 2, 3};
    double[] row2 = {4, 5, 6};
    double[] row3 = {7, 8, 9};
    flatDoubleMatrix.setRow(row1, 1);
    flatDoubleMatrix.setRow(row2, 2);
    flatDoubleMatrix.setRow(row3, 3);
    IO.println(flatDoubleMatrix);

    flatDoubleMatrix.setCell(0, 2, 2);
    IO.println(flatDoubleMatrix);

    double[] column3 = {9, 6, 3};
    flatDoubleMatrix.setColumn(column3, 3);
    IO.println(flatDoubleMatrix);

    IO.println(flatDoubleMatrix.getCell(3, 2));
    IO.println(Arrays.toString(flatDoubleMatrix.getColumn(1)));
    IO.println(Arrays.toString(flatDoubleMatrix.getRow(2)));
    IO.println(flatDoubleMatrix.getRows());
    IO.println(flatDoubleMatrix.getColumns());
}
