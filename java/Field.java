public class Field {

    /** Default count of row and column */
    public static final int DEFAULT_COUNT = 19;

    private int mCount;
    private Cell[][] mCells;

    public Field(int aCount) {
        mCount = aCount;
        mCells = new Cell[mCount][mCount];
        for (int i = 0; i < mCount; i++)
            for (int j = 0; j < mCount; j++)
                mCells[i][j] = new Cell(Cell.EMPTY);
    }

    public int getCount() {
        return mCount;
    }

    public int getCellType(int aX, int aY) {
        if (aX < 0 || aX >= mCount || aY < 0 || aY >= mCount) return Cell.NOT_CELL.getType();
        return mCells[aX][aY].getType();
    }

    public boolean setCellType(int aX, int aY, int aType) {
        if (aX < 0 || aX >= mCount || aY < 0 || aY >= mCount) return false;
        if (mCells[aX][aY].getType() != Cell.EMPTY) return false;
        mCells[aX][aY].setType(aType);
        return true;
    }
}
