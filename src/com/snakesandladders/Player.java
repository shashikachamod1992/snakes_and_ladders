package com.snakesandladders;

/**
 * Created by shash on 10/16/2018.
 */
public class Player {

    private String name;
    private int currentPosition;
    private int currentNumber;
    private boolean isActive;
    private Player opponent;
    private boolean isWinner;

    public Player(){
      this.currentNumber = 0;
      this.currentPosition =0;
      this.isActive = true;
      this.isWinner =  false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }
}
