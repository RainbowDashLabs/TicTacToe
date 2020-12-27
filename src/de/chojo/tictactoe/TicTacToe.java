package de.chojo.tictactoe;

import de.chojo.tictactoe.gameboard.AbstractGameboard;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicTacToe {
    public static void main(String[] args) {
        AbstractGameboard gameboard = new ExampleGameboard(3, 3, 3);
        gameboard.enablePrint(true);
        gameboard.playTillEnd(false);
        for (int i = 0; i < 1; i++) {
            runGame(gameboard);
        }
    }

    /**
     * Initializes a new game
     *
     * @param gameboard the gameboard where a game should be run.
     */
    public static void runGame(AbstractGameboard gameboard) {
        gameboard.reset();
        gameboard.run(getRandomSteps(gameboard));
        if (gameboard.getWinTurn() == -1) {
            System.out.println("Draw!");
        } else {
            System.out.println(Character.toString(gameboard.getWinner()) + " wins in turn " + gameboard.getWinTurn());
        }
    }

    public static int[] getRandomSteps(AbstractGameboard gameboard) {
        List<Integer> integers = IntStream.range(0, gameboard.getDim().getX() * gameboard.getDim().getY())
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(integers);
        return integers.stream().mapToInt(i -> i).toArray();
    }
}
