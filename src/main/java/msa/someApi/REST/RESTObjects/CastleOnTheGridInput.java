package msa.someApi.REST.RESTObjects;

public class CastleOnTheGridInput {
    private String[] grid;
    private int startX;
    private int startY;
    private int goalX;
    private int goalY;

    public CastleOnTheGridInput(String[] grid, int startX, int startY, int goalX, int goalY) {
        this.grid = grid;
        this.startX = startX;
        this.startY = startY;
        this.goalX = goalX;
        this.goalY = goalY;
    }

    public String[] getGrid() {
        return this.grid;
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    public int getGoalX() {
        return this.goalX;
    }

    public int getGoalY() {
        return this.goalY;
    }

}
