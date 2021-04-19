public interface Player {
    static final int NUMBER_OF_STONES = 4;
    static final int NUMBER_OF_FIELDS = 6;

    void initializePlayer();
    int move();
    int getWellScore();
}
