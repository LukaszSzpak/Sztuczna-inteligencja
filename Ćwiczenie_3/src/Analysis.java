public class Analysis {
    private long creationTime;
    private int moveCount;

    public Analysis() {
        this.creationTime = System.currentTimeMillis();
        this.moveCount = 0;
    }

    public void addMove() {
        this.moveCount++;
    }

    public void printStats() {
        System.out.println("----------------------\n" +
                "Total time: " + (System.currentTimeMillis() - this.creationTime) + "\n" +
                "Total moves: " + this.moveCount);
    }
}
