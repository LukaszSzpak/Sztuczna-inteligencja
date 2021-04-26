public class Field {
    private int stonesCounter;

    public Field() {
        this.stonesCounter = Player.NUMBER_OF_STONES;
    }

    public Field(Field field) {
        this.stonesCounter = field.stonesCounter;
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

    public boolean isEmpty() {
        return this.stonesCounter == 0;
    }
}
