package components;

public class Node {
	int id;
	int x;
	int y;

	// Constructs a pokestop at chosen x, y location
	public Node(int id, int x, int y) {
		this.id = id;
        this.x = x;
        this.y = y;
    }

    //Gets pokestop's id
    public int getId() {
        return this.id;
    }

	// Gets pokestop's x coordinate
    public int getX() {
        return this.x;
    }

    // Gets pokestop's y coordinate
    public int getY() {
        return this.y;
    }

    // Gets the distance to given pokestop
    public double distanceTo(Node node) {
        long xDistance = Math.abs(getX() - node.getX());
        long yDistance = Math.abs(getY() - node.getY());
        double distance = Math.sqrt( (xDistance * xDistance) + (yDistance * yDistance));
        return (int) Math.round(distance);
    }
}
