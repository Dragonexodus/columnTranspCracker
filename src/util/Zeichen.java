package util;

public class Zeichen {
    private char c;
    private int positionCol;

    public Zeichen(char c, int positionCol) {
        this.c = c;
        this.positionCol = positionCol;
    }

    public void print() {
        System.out.println("Zeichen: " + c + " Position: " + positionCol);
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public int getPositionCol() {
        return positionCol;
    }

    public void setPositionCol(int positionCol) {
        this.positionCol = positionCol;
    }
}
