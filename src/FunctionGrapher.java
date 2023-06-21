import MathExpression.ExpressionExecutor;

public class FunctionGrapher {
    public static void main(String[] args) {
        ExpressionExecutor exec = new ExpressionExecutor("15 ^ 2+ 123 / (12.2 ^ 12.224 * 2.0)");
        System.out.println(exec.evaluate());
    }
}
