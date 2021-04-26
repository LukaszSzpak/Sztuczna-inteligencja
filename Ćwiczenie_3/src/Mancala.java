public class Mancala {
    private Player playerA;
    private Player playerB;
    private Player actPlayer;

    public Mancala(Player playerA, Player playerB) {
        this.playerA = playerA;
        this.playerB = playerB;

        playerA.initializePlayer();
        playerB.initializePlayer();
        this.actPlayer = this.playerA;
    }

    public Mancala(Mancala mancalaToCopy) {
        this.playerA = mancalaToCopy.playerA;
        this.playerB = mancalaToCopy.playerB;
        this.actPlayer = mancalaToCopy.actPlayer;
    }

    public boolean theEndOfGame() {
        return this.playerA.checkAllFieldsEmpty() || this.playerB.checkAllFieldsEmpty();
    }

    public void nextMove() {
        int fieldNumber = actPlayer.move();
        int stonesCounter = actPlayer.getNumberOfStonesFromFieldNumberAndErase(fieldNumber);
        Player lastMovePlayer = this.actPlayer;
        Player stonesPlayer = this.actPlayer;
        boolean canBePlayerChange = true;


        while (stonesCounter > 0) {
            fieldNumber++;
            if (fieldNumber >= Player.NUMBER_OF_FIELDS) {
                if (stonesPlayer == this.actPlayer) {
                    this.actPlayer.addStoneToWell();
                    stonesCounter--;
                }
                stonesPlayer = getAnotherPlayer(stonesPlayer);
                fieldNumber = 0;
            }

            if (stonesCounter == 1 && stonesPlayer.getNumberOfStonesFromFieldNumber(fieldNumber) == 0) {
                canBePlayerChange = false;
                stonesPlayer.addStonesToWall(getAnotherPlayer(stonesPlayer).
                        getNumberOfStonesFromFieldNumberAndErase(Player.NUMBER_OF_FIELDS - fieldNumber - 1));
            }

            stonesPlayer.addStoneToField(fieldNumber);
            stonesCounter--;
        }

        if (canBePlayerChange && !(stonesPlayer == lastMovePlayer && stonesCounter == 0))
            this.actPlayer = getAnotherPlayer(this.actPlayer);

    }

    private Player getAnotherPlayer(Player actPlayer) {
        return actPlayer == this.playerA ? this.playerB : this.playerA;
    }

    public void printGame() {
        System.out.print("\t");
        for (int i = Player.NUMBER_OF_FIELDS - 1; i >= 0; i--)
            System.out.print(this.playerA.getNumberOfStonesFromFieldNumber(i) + "\t");

        System.out.print("\n" + this.playerA.getWellScore() + "\t");
        for (int i = Player.NUMBER_OF_FIELDS - 1; i >= 0; i--)
            System.out.print("\t");

        System.out.print(this.playerB.getWellScore() + "\n\t");
        for (int i = 0; i < Player.NUMBER_OF_FIELDS; i++)
            System.out.print(this.playerB.getNumberOfStonesFromFieldNumber(i) + "\t");
        System.out.println("");
    }
}
