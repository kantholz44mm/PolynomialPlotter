import MathExpression.ExpressionExecutor;

public class FunctionGrapher {
    public static void main(String[] args) {
        ExpressionExecutor exec = new ExpressionExecutor("cos(4 * tan(2 ^ sin(2)))");
        System.out.println(exec.evaluate());
    }
}
