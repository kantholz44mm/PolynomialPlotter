import java.util.List;

public interface ParametricFunction {
    Vector2D evaluate(double t);
    void derive();
    List<Double> getZeroPoints();
    List<Double> getExtremePoints();
    String getFunctionString();
}

