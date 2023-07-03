package MathExpression;

import Core.ParametricFunction;
import Core.Vector2D;

public class ParametricExpression implements ParametricFunction {

    public final double lowerBound;
    public final double upperBound;
    public final int numSamples;
    private ExpressionExecutor xExecutor = null;
    private ExpressionExecutor yExecutor = null;
    
    public ParametricExpression(String xFunction, String yFunction, double lowerBound, double upperBound, int numSamples) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.numSamples = numSamples;
        this.xExecutor = new ExpressionExecutor(xFunction);
        this.yExecutor = new ExpressionExecutor(yFunction);
    }

    @Override
    public Vector2D evaluate(double t) {
        xExecutor.setParameter('t', t);
        yExecutor.setParameter('t', t);
        return new Vector2D(xExecutor.evaluate(), yExecutor.evaluate());
    }
}
