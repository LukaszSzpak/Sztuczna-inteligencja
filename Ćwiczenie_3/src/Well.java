public class Well {
    private int stonesCounter;

    public Well() {
        this.stonesCounter = 0;
    }

    public Well(Well well) {
        this.stonesCounter = well.stonesCounter;
    }

    public void addStone() {
        this.stonesCounter++;
    }

    public void addStones(int numberOfStones) {
        this.stonesCounter += numberOfStones;
    }

    public int getScore() {
        return this.stonesCounter;
    }
}
