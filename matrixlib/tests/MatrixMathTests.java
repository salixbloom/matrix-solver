import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatrixMathTests {

    /**
     * Tests a Matrix that is already in Row Echelon Form and verifies that none of the values are changed.
     */
    @Test
    public void testMatrixAlreadyREF() {
        Matrix matrix = new Matrix(3, 4, 'A');
        matrix.editRow(new double[]{1.0, 1.0, 1.0, 1.0}, 0);
        matrix.editRow(new double[]{0.0, 1.0, 1.0, 1.0}, 1);
        matrix.editRow(new double[]{0.0, 0.0, 1.0, 1.0}, 2);
        Matrix refMatrix = MatrixMath.ref(matrix);
        assertArrayEquals(new double[]{1.0, 1.0, 1.0, 1.0}, refMatrix.getRow(0),
                "Row should not have changed when already in REF");
        assertArrayEquals(new double[]{0.0, 1.0, 1.0, 1.0}, refMatrix.getRow(1),
                "Row should not have changed when already in REF");
        assertArrayEquals(new double[]{0.0, 0.0, 1.0, 1.0}, refMatrix.getRow(2),
                "Row should not have changed when already in REF");
    }

    // Bug Throws ArrayIndexOutOfBoundsException
    @Test
    public void testMatrixAlreadyREFTwoFreeVars() {
        Matrix matrix = new Matrix(3, 4, 'A');
        matrix.editRow(new double[]{1.0, 1.0, 1.0, 1.0}, 0);
        matrix.editRow(new double[]{0.0, 0.0, 0.0, 0.0}, 1);
        matrix.editRow(new double[]{0.0, 0.0, 0.0, 0.0}, 2);
        Matrix refMatrix = MatrixMath.ref(matrix);
        assertAll(
              "Grouped array equal assertions for REF Two Free Variables",
                () -> assertArrayEquals(new double[]{1.0, 1.0, 1.0, 1.0}, refMatrix.getRow(0)),
                () -> assertArrayEquals(new double[]{0.0, 0.0, 0.0, 0.0}, refMatrix.getRow(1)),
                () -> assertArrayEquals(new double[]{0.0, 0.0, 0.0, 0.0}, refMatrix.getRow(2))
        );
    }

    // Unexpected Behavior
    @Test
    public void testMatrixREFProb1() {
        Matrix matrix = new Matrix(3, 3, 'A');
        matrix.editRow(new double[]{0.0, 1.0, 3.0}, 0);
        matrix.editRow(new double[]{-1.0, -3.0, 3.0}, 1);
        matrix.editRow(new double[]{1.0, -3.0, 0.0}, 2);
        Matrix refMatrix = MatrixMath.ref(matrix);
        assertAll(
                "Grouped array equal assertions for REF Problem 2",
                () -> assertArrayEquals(new double[]{-1.0, -3.0, 3.0}, refMatrix.getRow(0)),
                () -> assertArrayEquals(new double[]{0.0, 1.0, 3.0}, refMatrix.getRow(1)),
                () -> assertArrayEquals(new double[]{0.0, 0.0, 21.0}, refMatrix.getRow(2))
        );
    }

    @Test
    public void testMatrixREFProb2() {
        Matrix matrix = new Matrix(3, 4, 'A');
        matrix.editRow(new double[]{4.0, -1.0, 3.0, 5.0}, 0);
        matrix.editRow(new double[]{0.0, 2.0, 5.0, 9.0}, 1);
        matrix.editRow(new double[]{-6.0, 1.0, -3.0, 10.0}, 2);
        Matrix refMatrix = MatrixMath.ref(matrix);
        assertAll(
                "Grouped array equal assertions for REF Problem 2",
                () -> assertArrayEquals(new double[]{4.0, -1.0, 3.0, 5.0}, refMatrix.getRow(0)),
                () -> assertArrayEquals(new double[]{0.0, 2.0, 5.0, 9.0}, refMatrix.getRow(1)),
                () -> assertArrayEquals(new double[]{0.0, 0.0, 2.75, 19.75}, refMatrix.getRow(2))
        );
    }

    @Test
    public void testMatrixREFProb3() {
        Matrix matrix = new Matrix(3, 4, 'A');
        matrix.editRow(new double[]{1.0, 2.0, 3.0, 6.0}, 0);
        matrix.editRow(new double[]{2.0, -3.0, 2.0, 14.0}, 1);
        matrix.editRow(new double[]{3.0, 1.0, -1.0, -2.0}, 2);
        Matrix refMatrix = MatrixMath.ref(matrix);
        assertAll(
                "Grouped array equal assertions for REF Problem 2",
                () -> assertArrayEquals(new double[]{1.0, 2.0, 3.0, 6.0}, refMatrix.getRow(0)),
                () -> assertArrayEquals(new double[]{0.0, -7.0, -4.0, 2.0}, refMatrix.getRow(1)),
                () -> assertArrayEquals(new double[]{0.0, 0.0,
                        -7.142857142857142,
                        -150.0/7.0}, refMatrix.getRow(2))
        );
    }
}
