package org.example.snakegame.data;

// This is the most important class which defines the position of each entity in the game
public class Point implements SizeData{
    private final int pointX;
    private final int pointY;

    public Point(int x, int y){
        this.pointX = x;
        this.pointY = y;
    }

    public int getPointX() {
        return pointX;
    }

    public int getPointY() {
        return pointY;
    }

    // for actual position in the display based on pointX and pointY value
    public double getLayoutXValue(){
        return pointX * GRID_SIZE;
    }
    public double getLayoutYValue(){
        return pointY * GRID_SIZE;
    }


    public boolean pointEquals(Point point){
        return (point.getPointX() == getPointX()) && (point.getPointY() == getPointY());
    }

    // returns next point for a given direction, this function determines the movement of snake in a particular direction
    public Point getNextPoint(Direction direction){
        switch (direction){
            case UP -> {
                return new Point(pointX, pointY - 1);
            }
            case RIGHT -> {
                return new Point(pointX + 1, pointY);
            }
            case DOWN -> {
                return new Point(pointX, pointY + 1);
            }
            case LEFT -> {
                return new Point(pointX - 1, pointY);
            }
            default -> {
                return this;
            }
        }
    }

    // Boundary Collision Condition
    public boolean isNotSafeForBoundaries(){
        return pointX == 0 || pointY == 0 || pointX == X_NUM - 1 || pointY == Y_NUM - 1;
    }

    @Override
    public String toString(){
        return "[ X: " + pointX + ", Y: " + pointY + "]";
    }
}
