/**
 * A data class that defines a Matrix
 *
 * @author Willow
 * @version v0.01
 */
public class Matrix {
    /** The height (or number of rows) of the matrix */
    protected int myRows;
    /** The width (or number of columns) of the matrix */
    protected int myColumns;
    /** A character representation of the matrix */
    protected char myName;
    // Have boolean defining it as augmented?

    private float[][] myData;

    /**
     * Constructor for a matrix object. A matrix object's data must be passed after it's been constructed.
     *
     * @param theRows The height of the matrix
     * @param theColumns The width of the matrix
     * @param theName Single character representation of the matrix
     */
    public Matrix(final int theRows, final int theColumns, final char theName) {
        myRows = theRows;
        myColumns = theColumns;
        myName = theName;
        myData = new float[theRows][theColumns];
    }

    /**
     * Function for editing data. Data must be of width equal to the number of columns in the Matrix.
     *
     * @param theData The float array containing the data for the Matrix.
     * @param theRow The row where the data will be stored.
     * @throws InvalidInputException If theData width (size) does not match the matrix width.
     * @throws InvalidInputException If theRow is greater than the height of the matrix.
     * @throws IllegalArgumentException If theRow is negative.
     */
    public void editRow(final float[] theData, final int theRow) {
        if (theData.length != myColumns) {
            throw new InvalidInputException("The row of size " + (String) theData.length
                    + " does not fit in matrix of width " + (String) myColumns);
        }
        validateRow(theRow);

        float[] rowToEdit = myData[theRow];
        for (int i = 0; i < myColumns; i++) {
            rowToEdit[i] = myData[i];
        }
    }

    /**
     * Getting function for row data inside of the matrix object.
     *
     * @param theRow The row to be returned.
     * @return The data inside the row at theRow of the matrix.
     */
    public float[] getRow(final int theRow) {
        validateRow(theRow);
        return myData[theRow];
    }

    private void validateRow(final int theRow) {
        if(theRow < 0) {
            throw new IllegalArgumentException("The row to edit must be positive")
        } else if(theRow > myRows) {
            throw new InvalidInputException("The row to edit must be less than the matrix height of "
                    + (String) myRows);
        }
    }
}