public class Cell {

    /** Cell's Type Enum*/
    public static final int NOTHING = -1;
    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    public static final Cell NOT_CELL = new Cell(NOTHING);

    /** Cell's Type */
    public int mType;

    public Cell(int aType) { mType = aType; }

    public int getType() { return mType; }

    public void setType(int aType) { mType = aType; }

}
