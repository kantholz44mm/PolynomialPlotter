public class Vector2D {
    public double x = 0;
    public double y = 0;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D delta(Vector2D other) {
        return new Vector2D(other.x - x, other.y - y);
    }

    public double distance(Vector2D other) {
        return this.delta(other).length();
    }

    public Vector2D scale(double factor) {
        return new Vector2D(x * factor, y * factor);
    }
}
