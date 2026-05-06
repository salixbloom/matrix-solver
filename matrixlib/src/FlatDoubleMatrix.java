import java.util.Arrays;
import java.util.Objects;

/**
 * A flat row-major array implementation of Matrix.
 * Rows and columns are exposed to the caller using 1-based indexing.
 * All 0-based offset arithmetic is handled internally and never visible through the public API.
 *
 * @author Willow, Jordan Eng
 * @version v1.0
 */
public class FlatDoubleMatrix {

    /**
     * The height (number of rows) of the matrix.
     */
    private final int myRows;
    /**
     * The width (number of columns) of the matrix.
     */
    private final int myColumns;
    /**
     * Flat row-major backing array.
     */
    private final double[] myData;

    /**
     * Constructs a zero-filled matrix with the given dimensions.
     * Both dimensions must be at least 1.
     * Element data is written after construction.
     *
     * @param theRows    the height of the matrix; must be at least 1.
     * @param theColumns the width of the matrix; must be at least 1;
     * @throws IllegalArgumentException if theRows or theColumns is less than 1.
     */
    public FlatDoubleMatrix(final int theRows, final int theColumns)
            throws IllegalArgumentException {
        super();
        if (theRows < 1) {
            throw new IllegalArgumentException(
                    "Matrix must have at least 1 row, got: " + theRows + ".");
        }
        if (theColumns < 1) {
            throw new IllegalArgumentException(
                    "Matrix must have at least 1 column, got: " + theColumns + ".");
        }
        // Check for integer overflow to ensure that the matrix doesn't silently fail.
        if (theRows > Integer.MAX_VALUE / theColumns) {
            throw new IllegalArgumentException("Matrix dimensions are too large for a flat array");
        }

        myRows = theRows;
        myColumns = theColumns;
        myData = new double[theRows * theColumns];
    }

    /**
     * Private helper method that converts 1-based (row, column) pair to a 0-based flat array index.
     * Inputs are assumed to be valid; callers are responsible for validating
     * first before calling this method.
     *
     * @param theRow    the 1-based row number.
     * @param theColumn the 1-based column number.
     * @return the corresponding index in the 1 dimensional array.
     */
    private int toIndex(final int theRow, final int theColumn) {
        return (theRow - 1) * myColumns + (theColumn - 1);
    }

    /**
     * Overwrites row data with the given data; data must be of equal width.
     *
     * @param theData the data that replaces the given row; length must be equal to the matrix width.
     * @param theRow  the 1-based row index to overwrite.
     * @throws IllegalArgumentException if theData.length does not match the matrix width,
     *                                  or theRow is out of bounds.
     */
    public void setRow(final double[] theData, final int theRow)
            throws IllegalArgumentException {
        if (theData.length != myColumns) {
            throw new IllegalArgumentException(
                    "Row data length " + theData.length + " does not match matrix width of " + myColumns + ".");
        }
        validateRow(theRow);
        /*
         * Parameters: Source, Source Position, Destination, Destination Position, Length.
         * Copy data from theData, index 0 through (myColumns - 1).
         * Overwrite data from myData starting at the proper index (toIndex).
         */
        System.arraycopy(theData, 0, myData, toIndex(theRow, 1), myColumns);
    }

    /**
     * Overwrites the column with the given data; data must be of equal height.
     *
     * @param theData   the data that replaces the given column; length must equal the matrix height.
     * @param theColumn the 1-based column index to overwrite.
     * @throws IllegalArgumentException if theData.length does not match the matrix height,
     *                                  or theColumn is out of bounds.
     */
    public void setColumn(final double[] theData, final int theColumn)
            throws IllegalArgumentException {
        if (theData.length != myRows) {
            throw new IllegalArgumentException(
                    "Column data length " + theData.length + " does not match matrix height of " + myRows + ".");
        }
        validateColumn(theColumn);
        // Cannot use arraycopy since column is not continuous.
        // Optimization: Calculate start once, then jump by row width.
        int index = toIndex(1, theColumn);
        for (int i = 0; i < myRows; i++) {
            myData[index] = theData[i];
            index += myColumns;
        }
    }

    /**
     * Overwrites the cell at the given 1-based index with the given data.
     *
     * @param theValue the data that overwrites the cell.
     * @param theRow the 1-based row.
     * @param theColumn the 1-based column index.
     * @throws IllegalArgumentException if theRow or theColumn is out of bounds.
     */
    public void setCell(final double theValue, final int theRow, final int theColumn)
            throws IllegalArgumentException {
        validateRow(theRow);
        validateColumn(theColumn);
        myData[toIndex(theRow, theColumn)] = theValue;
    }

    /**
     * Returns a defensive copy of the requested row.
     *
     * @param theRow the 1-based row index to copy.
     * @return a new double[] containing the row's values.
     * @throws IllegalArgumentException if theRow is out of bounds.
     */
    public double[] getRow(final int theRow)
            throws IllegalArgumentException {
        validateRow(theRow);
        // Make a new row with the proper width.
        final double[] row = new double[myColumns];
        System.arraycopy(myData, toIndex(theRow, 1), row, 0, myColumns);
        return row;
    }

    /**
     * Returns a defensive copy of the requested column.
     *
     * @param theColumn the 1-based column index to retrieve.
     * @return a new double[] containing the column's values.
     * @throws IllegalArgumentException if theColumn is out of bounds.
     */
    public double[] getColumn(final int theColumn)
            throws IllegalArgumentException {
        validateColumn(theColumn);
        // Make a new column with the proper height.
        final double[] column = new double[myRows];
        // Cannot use arraycopy since column is not continuous.
        // Optimization: Calculate start once, then jump by row width.
        int index = toIndex(1, theColumn);
        for (int i = 0; i < myRows; i++) {
            column[i] = myData[index];
            index += myColumns;
        }
        return column;
    }

    /**
     * Returns the values of a single cell.
     *
     * @param theRow the 1-based row index.
     * @param theColumn the 1-based column index.
     * @return the values stored at the given position.
     * @throws IllegalArgumentException if theRow or theColumn is out of bounds.
     */
    public double getCell(final int theRow, final int theColumn)
            throws IllegalArgumentException {
        validateRow(theRow);
        validateColumn(theColumn);
        return myData[toIndex(theRow, theColumn)];
    }

    /**
     * Returns the number of rows in this matrix.
     *
     * @return the row count.
     */
    public int getRows() {
        return myRows;
    }

    /**
     * Returns the number of columns in this matrix.
     *
     * @return the column count.
     */
    public int getColumns() {
        return myColumns;
    }

    /**
     * Validates that theRow is a legal 1-based row index.
     *
     * @param theRow the 1-based row.
     * @throws IllegalArgumentException if theRow is less than 1
     *                                  or greater than the total number of rows.
     */
    private void validateRow(final int theRow) {
        if (theRow < 1 || theRow > myRows) {
            throw new IllegalArgumentException(
                    "Row " + theRow + " is out of bounds. " + "Valid rows: 1 to " + myRows + ".");
        }
    }

    /**
     * Validates that theColumn is a legal 1-based column index.
     *
     * @param theColumn the 1-based column.
     * @throws IllegalArgumentException if theColumn is less than 1
     *                                  or greater than the total number of columns.
     */
    private void validateColumn(final int theColumn) {
        if (theColumn < 1 || theColumn > myColumns) {
            throw new IllegalArgumentException(
                    "Column " + theColumn + " is out of bounds. " + "Valid column: 1 to " + myColumns + ".");
        }
    }

    /**
     * Returns a string representation of this matrix.
     *
     * @return a formatted string representation of this matrix.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < myData.length; i++) {
            if (i % myColumns == 0) {
                sb.append("[ ");
            }
            sb.append(myData[i]).append(" ");

            if ((i + 1) % myColumns == 0) {
                sb.append("]\n");
            }
        }

        return sb.toString();
    }

    /**
     * Compares this matrix to the specified object for equality. Two matrices are considered equal if they
     * have the same number of rows, columns, and elements.
     *
     * @param theObj   the reference object with which to compare to this matrix.
     * @return true if the given object is a FlatDoubleMatrix with identical dimensions and element values.
     *         false if otherwise.
     */
    public boolean equals(final Object theObj) {
        if (theObj == this) {
            return true;
        }
        if (theObj == null || theObj.getClass() != getClass()) {
            return false;
        }
        final FlatDoubleMatrix other = (FlatDoubleMatrix) theObj;

        return myRows == other.myRows &&
                myColumns == other.myColumns &&
                java.util.Arrays.equals(myData, other.myData);
    }

    /**
     * Returns a hash code for this matrix, derived from its row count, column count, and element data.
     * Two matrices that are equal will return the same hash code.
     *
     * @return a hash code value for this matrix.
     */
    public int hashCode() {
        return Objects.hash(myRows, myColumns, Arrays.hashCode(myData));
    }
}