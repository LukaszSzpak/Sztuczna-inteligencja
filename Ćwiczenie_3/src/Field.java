public class Field {
    private int stonesCounter;

    public Field() {
        this.stonesCounter = 0;
    }

    public void addStone() {
        this.stonesCounter++;
    }

    public int getNumberOfStones() {
        return stonesCounter;
    }

    public int getAndEraseStones() {
        int temp = this.stonesCounter;
        this.stonesCounter = 0;
        return temp;
    }
}
