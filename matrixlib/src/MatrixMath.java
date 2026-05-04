//import java.util.Arrays;
//import java.util.stream.IntStream;
//
///**
// * A utility class defining algorithms that can be preformed on Matrix structures
// *
// * @author Willow
// * @version v0.01
// */
//public class MatrixMath {
//
//    /** Private constructor to prevent instantiation */
//    private MatrixMath() {
//        super();
//    }
//
//    /**
//     * Swaps rows in Matrix inplace.
//     *
//     * @param theFlatDoubleMatrix Matrix to get rows from
//     * @param currentRow Row to swap with
//     * @param targetRow Row to swap with
//     */
//    public static void swapRow(final FlatDoubleMatrix theFlatDoubleMatrix, final int currentRow, final int targetRow) {
//        if (currentRow < 0 || targetRow < 0) {
//            throw new IllegalArgumentException("Both currentRow and targetRow must be a positive number");
//        } else if (currentRow > theFlatDoubleMatrix.myRows || targetRow > theFlatDoubleMatrix.myRows) {
//            throw new IllegalArgumentException("Both currentRow and targetRow must be less than"
//                    + " the number of rows in the matrix");
//        }
//        double[] firstRow = theFlatDoubleMatrix.getRow(currentRow);
//        double[] secondRow = theFlatDoubleMatrix.getRow(targetRow);
//
//        // Swap first with second
//        theFlatDoubleMatrix.editRow(firstRow, targetRow);
//        // Swap second with first
//        theFlatDoubleMatrix.editRow(secondRow, currentRow);
//    }
//
//    /**
//     * This function scales a row in a Matrix by a given scalar.
//     *
//     * @param theFlatDoubleMatrix The matrix to apply the row scalar to
//     * @param theRow The row to apply the row scalar to
//     * @param scalar The scalar to multiply each element of the row by
//     */
//    public static void scaleRow(final FlatDoubleMatrix theFlatDoubleMatrix, final int theRow, final double scalar) {
//        theFlatDoubleMatrix.validateRow(theRow);
//        double[] data = theFlatDoubleMatrix.getRow(theRow).clone();
//        double[] mappedData = Arrays.stream(data).map(item -> item * scalar).toArray();
//        theFlatDoubleMatrix.editRow(mappedData, theRow);
//    }
//
//    /**
//     * This function returns a scaled row from a selected Matrix.
//     *
//     * @param theFlatDoubleMatrix The matrix to access
//     * @param theRow The row to access from the matrix
//     * @param scalar The scalar to apply to each value in the row
//     * @return A double array
//     */
//    public static double[] getScaledRow(final FlatDoubleMatrix theFlatDoubleMatrix, final int theRow, final double scalar) {
//        theFlatDoubleMatrix.validateRow(theRow);
//        double[] data = theFlatDoubleMatrix.getRow(theRow).clone();
//        return Arrays.stream(data).map(item -> item * scalar).toArray();
//    }
//
//    /**
//     * Adds a given row (theData) to a specified row inside of a matrix (theRow and theMatrix respectively).
//     * I.e. Elementary row addition
//     *
//     * @param theFlatDoubleMatrix The Matrix to perform row addition on
//     * @param theRow The row inside of the matrix to perform the row addition on
//     * @param theData The row data to add to the row
//     */
//    public static void addRow(final FlatDoubleMatrix theFlatDoubleMatrix, final int theRow, final double[] theData) {
//        theFlatDoubleMatrix.validateRow(theRow);
//        double[] data = theFlatDoubleMatrix.getRow(theRow).clone();
//        if(theData.length != data.length) {
//            throw new IllegalArgumentException("Passed data must be of same width of Matrix");
//        }
//        double[] mappedData = IntStream.range(0, theData.length)
//                .mapToDouble(i -> data[i] + theData[i])
//                .toArray();
//        theFlatDoubleMatrix.editRow(mappedData, theRow);
//    }
//
//    /**
//     * Returns a matrix which has been put into Row Echelon Form
//     *
//     * @param theFlatDoubleMatrix The matrix to work on
//     * @return Another Matrix in Row Echelon Form
//     */
//    public static FlatDoubleMatrix ref(final FlatDoubleMatrix theFlatDoubleMatrix) {
//        // What to do when current spot already 0?
//        // Check if entire column is 0, if entire column is zero than we have no info on said variable
//        // If entire column is not 0, find first non-zero occurence and add that to the row with targeted leading variable
//        // Start simplifying, attempt to get to zero in each leading variable
//        // If a leading variable gets set to 0 during elementary operations swap row with the next row
//        // If its not the last row
//
//        FlatDoubleMatrix outputFlatDoubleMatrix = new FlatDoubleMatrix(theFlatDoubleMatrix.myRows, theFlatDoubleMatrix.myColumns, theFlatDoubleMatrix.myName);
//        for (int i = 0; i < theFlatDoubleMatrix.myRows; i++) {
//            double[] rowToCopy = theFlatDoubleMatrix.getRow(i);
//            outputFlatDoubleMatrix.editRow(rowToCopy, i);
//        }
//        // Copied matrix
//
//        for (int i = 0; i < outputFlatDoubleMatrix.myRows - 1; i++) {
//            if (columnNotEmpty(outputFlatDoubleMatrix, i)) {
//                // Make sure current leading variable is not zero
//                while(outputFlatDoubleMatrix.getRow(i)[i] == 0) {
//                    boolean foundLeadingVar = false;
//                    int currentRow = i;
//                    while(!foundLeadingVar) {
//                        // Get next row
//                        outputFlatDoubleMatrix.validateRow(i);
//                        // Since we know the column is not empty (there exists at least one non-zero)
//                        // This validate should NEVER fail
//                        double[] nextRow = outputFlatDoubleMatrix.getRow(currentRow);
//                        if (nextRow[i] != 0) {
//                            swapRow(outputFlatDoubleMatrix, currentRow, i);
//                            foundLeadingVar = true;
//                        } else {
//                            currentRow++;
//                        }
//                    }
//                }
//                // We know that the current column is NOT empty and that there
//                // is a leading variable
//                double[] workingRow = outputFlatDoubleMatrix.getRow(i);
//                for (int j = i+1; j < outputFlatDoubleMatrix.myRows; j++) {
//                    outputFlatDoubleMatrix.validateRow(j);
//                    double[] rowToEdit = outputFlatDoubleMatrix.getRow(j);
//                    double scalar = rowToEdit[i] / workingRow[i];
//                    addRow(outputFlatDoubleMatrix, j, getScaledRow(outputFlatDoubleMatrix, i, (-1) * scalar));
//                }
//            }
//        }
//        return(outputFlatDoubleMatrix);
//    }
//
//    /**
//     * Function to get the row reduced echelon form of a given Matrix.
//     *
//     * @param theFlatDoubleMatrix The Matrix to get the row reduced echelon form result from
//     * @return A Matrix in row reduced echelon form
//     */
//    public static FlatDoubleMatrix rref(final FlatDoubleMatrix theFlatDoubleMatrix) {
//        // Get ref form and do back substitution
//        FlatDoubleMatrix outputFlatDoubleMatrix = ref(theFlatDoubleMatrix);
//
//        for (int i = outputFlatDoubleMatrix.myRows - 1; i >= 0; i--) {
//            if (columnNotEmpty(theFlatDoubleMatrix, i)) {
//                double leadingVariable = outputFlatDoubleMatrix.getRow(i)[i];
//                double epsilon = 1e-6;
//                // leadingVariable may be incredibly close to 0 but not exactly zero
//                if (Math.abs(leadingVariable) > epsilon) {
//                    // Scale row to 1
//                    double scalar = 1 / leadingVariable;
//                    System.out.println(scalar);
//                    scaleRow(outputFlatDoubleMatrix, i, scalar);
//
//                    for (int j = i - 1; j >= 0; j--) {
//                        double variableToRemove = outputFlatDoubleMatrix.getRow(j)[i];
//                        addRow(outputFlatDoubleMatrix, j, getScaledRow(outputFlatDoubleMatrix, i, (-1) * variableToRemove));
//                    }
//                }
//            }
//        }
//        return outputFlatDoubleMatrix;
//    }
//
//    private static boolean columnNotEmpty(final FlatDoubleMatrix theFlatDoubleMatrix, final int theColumn) {
//        for(int i = 0; i < theFlatDoubleMatrix.myRows; i++) {
//            if(theFlatDoubleMatrix.getRow(i)[theColumn] != 0){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    static void main() {
//        FlatDoubleMatrix theFlatDoubleMatrix = new FlatDoubleMatrix(3, 4, 'A');
//        theFlatDoubleMatrix.editRow(new double[]{1, 2, 3, 1}, 0);
//        theFlatDoubleMatrix.editRow(new double[]{4, 5, 6, 1}, 1);
//        theFlatDoubleMatrix.editRow(new double[]{7, 8, 9, 1}, 2);
//        FlatDoubleMatrix ref = ref(theFlatDoubleMatrix);
//        FlatDoubleMatrix rref = rref(theFlatDoubleMatrix);
//
//        System.out.println("hi");
//    }
//}