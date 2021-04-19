public class StartApp {
    public static void main(String[] args) {
        Mancala mancala = new Mancala(new Human("HumanA"), new Human("HumanB"));
        mancala.printGame();

        while (!mancala.theEndOfGame()) {
            mancala.nextMove();
            mancala.printGame();
        }

    }
}
