package de.chojo.tictactoe;

import de.chojo.tictactoe.gameboard.AbstractGameboard;
import de.chojo.tictactoe.util.Vector2d;

import java.util.function.BiFunction;

/**
 * Example implementation and a bit overengineered game board
 */
public class ExampleGameboard extends AbstractGameboard {

    /**
     * Creates a new game board with flexible size and win condition.
     * <p>
     * A game is won if a player has the amount of {@link #getWinCon()} in one direction.
     *
     * @param xDim   x dimensions
     * @param yDim   y dimensions
     * @param winCon amount of marks in one direction to win the game
     */
    public ExampleGameboard(int xDim, int yDim, int winCon) {
        super(xDim, yDim, winCon);
    }

    /**
     * Checks if the current player won the game based on the last changed field.
     * <p>
     * This is done by checking if the fields in horizontal, vertical and diagonal direction.
     * <p>
     * A player wins if the player has the amount of {@link #getWinCon()} in one direction.
     *
     * @param changed the changed field
     *
     * @return true if a player won this game
     */
    @Override
    protected boolean isWon(Vector2d changed) {
        for (Direction dir : Direction.values()) {
            if (count(getCurrentPlayer(), changed, dir) >= getWinCon()) return true;
        }
        return false;
    }

    private int count(int player, Vector2d changed, Direction direction) {
        return countFunc(direction.first, changed, player) + countFunc(direction.second, changed, player) + 1;
    }

    private int countFunc(BiFunction<Vector2d, Integer, Vector2d> func, Vector2d changed, int player) {
        int row = 0;
        for (int i = 1; i < getWinCon(); i++) {
            Vector2d currCoord = func.apply(changed, i);
            if (isFieldOfPlayer(player, currCoord)) {
                row++;
            } else {
                break;
            }
        }
        return row;
    }

    /**
     * Checks if a field is owned by the player which owns the given mark
     *
     * @param player mark of player
     * @param coord  coord to check
     *
     * @return true if the player owns this field
     */
    @Override
    protected boolean isFieldOfPlayer(int player, Vector2d coord) {
        return isInField(coord) && getField(coord) == player;
    }


    /**
     * Checks if a vector is inside the game field.
     *
     * @param currCoord coordinate to check
     *
     * @return true if the vector is inside the field
     */
    @Override
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
    @Override
    protected boolean isInField(int x, int y) {
        if (x < 0 || y < 0) return false;
        return x < getDim().getX() && y < getDim().getY();
    }

    private enum Direction {
        HORIZONTAL((changed, i) -> changed.add(-i, 0),
                (changed, i) -> changed.add(i, 0)),
        VERTICAL((changed, i) -> changed.add(0, -i),
                (changed, i) -> changed.add(0, i)),
        MINUS_45((changed, i) -> changed.add(-i, -i),
                (changed, i) -> changed.add(i, i)),
        PLUS_45((changed, i) -> changed.add(-i, i),
                (changed, i) -> changed.add(i, -i));

        private final BiFunction<Vector2d, Integer, Vector2d> first;
        private final BiFunction<Vector2d, Integer, Vector2d> second;

        Direction(BiFunction<Vector2d, Integer, Vector2d> first, BiFunction<Vector2d, Integer, Vector2d> second) {
            this.first = first;
            this.second = second;
        }
    }
}
