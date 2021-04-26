import java.util.Optional;

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
        if (mancalaToCopy.playerA instanceof AI)
            this.playerA = new AI((AI) mancalaToCopy.playerA);
        else
            this.playerA = new Human((Human) mancalaToCopy.playerA);

        if (mancalaToCopy.playerB instanceof AI)
            this.playerB = new AI((AI) mancalaToCopy.playerB);
        else
            this.playerB = new Human((Human) mancalaToCopy.playerB);

        if (mancalaToCopy.actPlayer == mancalaToCopy.playerA)
            this.actPlayer = this.playerA;
        else
            this.actPlayer = this.playerB;
    }

    public boolean theEndOfGame() {
        return this.playerA.checkAllFieldsEmpty() || this.playerB.checkAllFieldsEmpty();
    }

    public void nextMove(Optional<Integer> optMove) throws NoSuchMethodException {
        int fieldNumber;
        if (optMove.isPresent())
            fieldNumber = optMove.get();
        else
            fieldNumber = actPlayer.move(this);

        int stonesCounter = actPlayer.getNumberOfStonesFromFieldNumberAndErase(fieldNumber);
        Player stonesPlayer = this.actPlayer;
        Player movePlayer = this.actPlayer;
        boolean canBePlayerChange = true;
        boolean addedToWell = false;


        while (stonesCounter > 0) {
            fieldNumber++;
            addedToWell = false;

            if (fieldNumber >= Player.NUMBER_OF_FIELDS) {
                if (stonesPlayer == this.actPlayer) {
                    this.actPlayer.addStoneToWell();
                    stonesCounter--;
                    addedToWell = true;
                }
                stonesPlayer = getAnotherPlayer(stonesPlayer);
                fieldNumber = 0;
            }

            if (stonesCounter == 1 &&
                    stonesPlayer.getNumberOfStonesFromFieldNumber(fieldNumber) == 0 &&
                    stonesPlayer == movePlayer) {
                canBePlayerChange = false;
                stonesPlayer.addStonesToWall(getAnotherPlayer(stonesPlayer).
                        getNumberOfStonesFromFieldNumberAndErase(Player.NUMBER_OF_FIELDS - fieldNumber - 1));
            }

            if (stonesCounter > 0) {
                stonesPlayer.addStoneToField(fieldNumber);
                stonesCounter--;
                addedToWell = false;
            }

        }

        if (canBePlayerChange && !addedToWell)
            this.actPlayer = getAnotherPlayer(this.actPlayer);

    }

    public Player getAnotherPlayer(Player actPlayer) {
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
        System.out.println("\n\n");
    }
}
