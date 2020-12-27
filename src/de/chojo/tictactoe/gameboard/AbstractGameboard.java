package de.chojo.tictactoe.gameboard;

import de.chojo.tictactoe.util.Vector2d;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A abstract implementation of a Game board. Provides basic implementation.
 */
public abstract class AbstractGameboard {
    private final int xDim, yDim;
    private final int winCon;
    private int currentPlayer = 'X';
    private int winner = 0;
    private int winTurn = -1;
    private int[][] fields;
    private int currTurn = 1;
    private boolean print = false;
    private boolean playTillEnd = true;

    public AbstractGameboard() {
        this(3, 3, 3);
    }

    /**
     * Creates a new game board with flexible size and win condition.
     * <p>
     * A game is won if a player has the amount of {@link #winCon} in one direction.
     *
     * @param xDim   x dimensions
     * @param yDim   y dimensions
     * @param winCon amount of marks in one direction to win the game
     */
    public AbstractGameboard(int xDim, int yDim, int winCon) {
        this.xDim = xDim;
        this.yDim = yDim;
        this.winCon = winCon;
        reset();
    }

    /**
     * Resets the field.
     */
    public void reset() {
        for (int[] field : fields = new int[yDim][xDim]) {
            Arrays.fill(field, 0);
        }
        currentPlayer = 'X';
        winner = 0;
        currTurn = 1;
        winTurn = -1;
    }

    public void run(int[] steps) {
        for (int step : steps) {
            Vector2d coord = fieldToCoord(step);
            placeStone(coord);
            if (isWon(coord) && winTurn == -1) {
                winTurn = currTurn;
                winner = currentPlayer;
                if (!playTillEnd) {
                    printField();
                    return;
                }
            }
            nextTurn();
        }
    }

    /**
     * Place the stone based on the entered steps. Each step is a field.
     * <p>
     * The field is defined by a number starting from the first field on the top left corner.
     *
     * @param coord field which is made by the players.
     */
    public final void placeStone(Vector2d coord) {
        setField(coord, currentPlayer);
    }

    /**
     * Change the current player to the opposite.
     */
    protected void nextTurn() {
        printField();
        currentPlayer = 'O' == (currentPlayer) ? 'X' : 'O';
        currTurn++;
    }

    /**
     * Checks if the current player won the game based on the last changed field.
     * <p>
     * This is done by checking if the fields in horizontal, vertical and diagonal direction.
     * <p>
     * A player wins if the player has the amount of {@link #winCon} in one direction.
     *
     * @param changed the changed field
     *
     * @return true if a player won this game
     */
    protected abstract boolean isWon(Vector2d changed);

    /**
     * Checks if a field is owned by the player which owns the given mark
     *
     * @param player mark of player
     * @param coord  coord to check
     *
     * @return true if the player owns this field
     */
    protected abstract boolean isFieldOfPlayer(int player, Vector2d coord);

    /**
     * Get the field value of a vector.
     *
     * @param field field to retrieve
     *
     * @return value of the field
     */
    protected int getField(Vector2d field) {
        return fields[field.getY()][field.getX()];
    }

    protected void setField(Vector2d field, int value) {
        fields[field.getY()][field.getX()] = value;
    }

    /**
     * Checks if a vector is inside the game field.
     *
     * @param currCoord coordinate to check
     *
     * @return true if the vector is inside the field
     */
    protected boolean isInField(Vector2d currCoord) {
        return isInField(currCoord.getX(), currCoord.getY());
    }

    /**
     * Checks if a coordinate is inside the game field
     *
     * @param x x coordinate
     * @param y y coordinate
     *
     * @return true if the coordinates are inside the field
     */
    protected abstract boolean isInField(int x, int y);

    /**
     * Get the winner of the current game
     *
     * @return mark of the player
     */
    public int getWinner() {
        return winner;
    }

    public int getWinTurn() {
        return winTurn;
    }

    /**
     * Converts a field number to a 2d field coordinate
     *
     * @param field field to convert
     *
     * @return field as vector
     */
    protected Vector2d fieldToCoord(int field) {
        return new Vector2d(field % fields[0].length, field / fields.length);
    }

    public final void enablePrint(boolean state) {
        print = state;
    }

    public final void playTillEnd(boolean state) {
        playTillEnd = state;
    }

    private void printField() {
        if (!print) return;
        String field = String.format("Turn %d | Player %s%n", currTurn, Character.toString(currentPlayer)) + Arrays.stream(fields)
                .map(f -> Arrays.stream(f)
                        .mapToObj(v -> v == 0 ? "_" : Character.toString(v))
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining("\n"));
        System.out.println(field);
    }

    public Vector2d getDim() {
        return new Vector2d(xDim, yDim);
    }

    public int getWinCon() {
        return winCon;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrTurn() {
        return currTurn;
    }
}
