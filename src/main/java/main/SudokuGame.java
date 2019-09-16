package main;

import gameLogic.GameStarter;
import table.SudokuBoard;

public class SudokuGame {

    public static void main(String[] args) {
        System.out.println("Welcome to Sudoku Solver v.1.0\n");
        boolean nextGame = true;
        while(nextGame) {
            GameStarter gameStarter = new GameStarter();
            SudokuBoard board = gameStarter.constructStarterBoard();
            nextGame = gameStarter.startSudoku(board);
        }
    }
}

