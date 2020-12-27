package de.chojo.tictactoe.util;

/**
 * Vector2d represents a two dimensional vector.
 */
public class Vector2d {
    private final int x, y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector2d add(int x, int y) {
        return new Vector2d( this.x + x, this.y + y);
    }
}
