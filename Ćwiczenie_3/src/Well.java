public class Well {
    private int stonesCounter;

    public Well() {
        this.stonesCounter = 0;
    }

    public void addStone() {
        this.stonesCounter++;
    }

    public int getScore() {
        return this.stonesCounter;
    }
}
