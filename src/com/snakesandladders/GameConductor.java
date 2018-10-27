package com.snakesandladders;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by shash on 10/16/2018.
 */
public class GameConductor {

    private int snakes[][] = new int[3][2];
    private int ladders[][] = new int[3][2];
    private int winningPosition = 36;

    public GameConductor(){
        setLadderPositions();
        setSnakePositions();
    }

    public Player playGame(Player p) {


        if (p.getCurrentNumber() == 1 || p.getCurrentNumber() == 6) {
            p.setActive(true);
            p.getOpponent().setActive(false);
        } else {
            p.setActive(false);
            p.getOpponent().setActive(true);
        }

        p.setCurrentPosition(p.getCurrentPosition() + p.getCurrentNumber());

        if (!p.isActive()) {
            for (int i = 0; i < snakes.length; i++) {
                if (snakes[i][0] == p.getCurrentPosition()) {
                    p.setCurrentPosition(snakes[i][1]);
                }
            }

            for (int i = 0; i < ladders.length; i++) {
                if (ladders[i][0] == p.getCurrentPosition()) {
                    p.setCurrentPosition(ladders[i][1]);
                }
            }
        }

        if (p.getCurrentPosition() >= winningPosition) {
            p.setWinner(true);
        }
        return p;
    }

    public int getNewIndice() {
        Random rand = new Random();
        int n = rand.nextInt(6) + 1;
        return n;
    }

    public int[][] getSnakePositions() {

        return snakes;
    }

    public int[][] getLadderPositions() {

        return ladders;
    }

    public void setSnakePositions() {



        for (int i = 0; i < 3; i++) {

            int min = ThreadLocalRandom.current().nextInt(1, 35);
            int max = ThreadLocalRandom.current().nextInt(min+1, 36);

            snakes[i][0] = max;
            snakes[i][1] = min;
        }

    }

    public void setLadderPositions() {


        for (int i = 0; i < 3; i++) {

            int min = ThreadLocalRandom.current().nextInt(1, 35);
            int max = ThreadLocalRandom.current().nextInt(min+1, 36);
            ladders[i][0] = min;
            ladders[i][1] = max;
        }
    }

}
