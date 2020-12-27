package de.chojo.tictactoe;

import de.chojo.tictactoe.gameboard.AbstractGameboard;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicTacToe {
    private static int draws = 0;
    private static int xWins = 0;
    private static int oWins = 0;
    private static long totalTurns = 0;
    private static Map<Integer, Integer> winTurn = new HashMap<>();

    public static void main(String[] args) {
        AbstractGameboard gameboard = new ExampleGameboard(3, 3, 3);
        gameboard.enablePrint(false);
        gameboard.playTillEnd(false);
        int games = 100000;
        for (int i = 0; i < games; i++) {
            runGame(gameboard, i + 1);
        }

        System.out.printf("Draw:  %d%nX Win: %d%nO Win: %d%nTurns: %d%n", draws, xWins, oWins, totalTurns);
        String turnWins = winTurn.entrySet().stream().map(e -> String.format("Turn %d : %d Wins", e.getKey(), e.getValue())).collect(Collectors.joining("\n"));
        System.out.println("Turn Wins:\n" + turnWins);
    }

    /**
     * Initializes a new game
     *
     * @param gameboard the gameboard where a game should be run.
     */
    public static void runGame(AbstractGameboard gameboard, int game) {
        gameboard.reset();
        gameboard.run(getRandomSteps(gameboard));
        if (gameboard.getWinTurn() == -1) {
            System.out.printf("Game %d | Draw!%n", game);
            draws++;
        } else {
            System.out.printf("Game %d | %s wins in turn %d.%n", game, Character.toString(gameboard.getWinner()), gameboard.getWinTurn());
            if (gameboard.getWinner() == 'X') {
                xWins++;
            } else {
                oWins++;
            }
            winTurn.compute(gameboard.getWinTurn(), (k, v) -> v == null ? 1 : v + 1);
        }
        totalTurns += gameboard.getCurrTurn();
    }

    public static int[] getRandomSteps(AbstractGameboard gameboard) {
        List<Integer> integers = IntStream.range(0, gameboard.getDim().getX() * gameboard.getDim().getY())
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(integers);
        return integers.stream().mapToInt(i -> i).toArray();
    }
}
