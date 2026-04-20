import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * A utility class defining algorithms that can be preformed on Matrix structures
 *
 * @author Willow
 * @version v0.01
 */
public class MatrixMath {

    /** Private constructor to prevent instantiation */
    private MatrixMath() {
        super();
    }

    /**
     * Swaps rows in Matrix inplace.
     *
     * @param theMatrix Matrix to get rows from
     * @param currentRow Row to swap with
     * @param targetRow Row to swap with
     */
    public static void swapRow(final Matrix theMatrix, final int currentRow, final int targetRow) {
        if (currentRow < 0 || targetRow < 0) {
            throw new IllegalArgumentException("Both currentRow and targetRow must be a positive number");
        } else if (currentRow > theMatrix.myRows || targetRow > theMatrix.myRows) {
            throw new IllegalArgumentException("Both currentRow and targetRow must be less than"
                    + " the number of rows in the matrix");
        }
        double[] firstRow = theMatrix.getRow(currentRow);
        double[] secondRow = theMatrix.getRow(targetRow);

        // Swap first with second
        theMatrix.editRow(firstRow, targetRow);
        // Swap second with first
        theMatrix.editRow(secondRow, currentRow);
    }

    /**
     * This function scales a row in a Matrix by a given scalar.
     *
     * @param theMatrix The matrix to apply the row scalar to
     * @param theRow The row to apply the row scalar to
     * @param scalar The scalar to multiply each element of the row by
     */
    public static void scaleRow(final Matrix theMatrix, final int theRow, final double scalar) {
        theMatrix.validateRow(theRow);
        double[] data = theMatrix.getRow(theRow).clone();
        double[] mappedData = Arrays.stream(data).map(item -> item * scalar).toArray();
        theMatrix.editRow(mappedData, theRow);
    }

    public static double[] getScaledRow(final Matrix theMatrix, final int theRow, final double scalar) {
        theMatrix.validateRow(theRow);
        double[] data = theMatrix.getRow(theRow).clone();
        return Arrays.stream(data).map(item -> item * scalar).toArray();
    }

    /**
     * Adds a given row (theData) to a specified row inside of a matrix (theRow and theMatrix respectively).
     * I.e. Elementary row addition
     *
     * @param theMatrix The Matrix to perform row addition on
     * @param theRow The row inside of the matrix to perform the row addition on
     * @param theData The row data to add to the row
     */
    public static void addRow(final Matrix theMatrix, final int theRow, final double[] theData) {
        theMatrix.validateRow(theRow);
        double[] data = theMatrix.getRow(theRow).clone();
        if(theData.length != data.length) {
            throw new IllegalArgumentException("Passed data must be of same width of Matrix");
        }
        double[] mappedData = IntStream.range(0, theData.length)
                .mapToDouble(i -> data[i] + theData[i])
                .toArray();
        theMatrix.editRow(mappedData, theRow);
    }

    /**
     *
     *
     * @param theMatrix
     * @return
     */
    public static Matrix ref(final Matrix theMatrix) {
        // What to do when current spot already 0?
        // Check if entire column is 0, if entire column is zero than we have no info on said variable
        // If entire column is not 0, find first non-zero occurence and add that to the row with targeted leading variable
        // Start simplifying, attempt to get to zero in each leading variable
        // If a leading variable gets set to 0 during elementary operations swap row with the next row
        // If its not the last row

        Matrix outputMatrix = new Matrix(theMatrix.myRows, theMatrix.myColumns, theMatrix.myName);
        for (int i = 0; i < theMatrix.myRows; i++) {
            double[] rowToCopy = theMatrix.getRow(i);
            outputMatrix.editRow(rowToCopy, i);
        }
        // Copied matrix

        for (int i = 0; i < theMatrix.myColumns - 1; i++) {
            if(!columnEmpty(theMatrix, i)) {

            }
        }

        //TODO Finish this shit lmao
        return new Matrix(0,0,'A');
    }

    private static boolean columnEmpty(final Matrix theMatrix, final int theColumn) {
        for(int i = 0; i < theMatrix.myRows; i++) {
            if(theMatrix.getRow(i)[theColumn] != 0){
                return false;
            }
        }
        return true;
    }
}
