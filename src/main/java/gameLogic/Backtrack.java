package gameLogic;

import table.SudokuBoard;

public class Backtrack {
    private int rowCoordinates;
    private int colCoordinates;
    private String guessedValue;
    private SudokuBoard board;

    public Backtrack(int rowCoordinates, int colCoordinates, String guessedValue, SudokuBoard board) {
        this.rowCoordinates = rowCoordinates;
        this.colCoordinates = colCoordinates;
        this.guessedValue = guessedValue;
        this.board = board;
    }

    public int getRowCoordinates() {
        return rowCoordinates;
    }

    public int getColCoordinates() {
        return colCoordinates;
    }

    public String getGuessedValue() {
        return guessedValue;
    }

    public SudokuBoard getBoard() {
        return board;
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
    }
}
