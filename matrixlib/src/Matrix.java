//TODO: Create Javadoc

public class Matrix {
    protected int myRows;
    protected int myColumns;
    protected char myName;
    // Have boolean defining it as augmented?

    private float[][] myData;

    public Matrix(final int theRows, final int theColumns, final char theName) {
        myRows = theRows;
        myColumns = theColumns;
        myName = theName;
        myData = new float[theRows][theColumns];
    }

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
