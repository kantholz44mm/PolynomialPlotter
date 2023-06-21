package MathExpression;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class ExpressionExecutor {

    private List<Token> tokens = null;

    public ExpressionExecutor(String expression) {
        setExpression(expression);
    }

    public void setExpression(String expression) {
        tokens = ShuntingYard.infixToPostfix(Tokenizer.tokenizeExpression(expression));
    }

    private void executeToken(Token token, Stack<Double> numericStack) {
        switch(token.type) {
            case Number:
            {
                numericStack.push(Double.parseDouble(token.literal));
                break;
            }

            case Exponentiation:
            {
                double b = numericStack.pop();
                double a = numericStack.pop();
                numericStack.push(Math.pow(a, b));
                break;
            }

            case Multiplication:
            {
                double b = numericStack.pop();
                double a = numericStack.pop();
                numericStack.push(a * b);
                break;
            }

            case Division:
            {
                double b = numericStack.pop();
                double a = numericStack.pop();
                numericStack.push(a / b);
                break;
            }

            case Addition:
            {
                double b = numericStack.pop();
                double a = numericStack.pop();
                numericStack.push(a + b);
                break;
            }

            case Subtraction:
            {
                double b = numericStack.pop();
                double a = numericStack.pop();
                numericStack.push(a - b);
                break;
            }

            default: 
            {
                break;
            }
        }
    }

    public double evaluate() {
        Stack<Token> executionStack = new Stack<>();
        Stack<Double> numericStack = new Stack<>();

        executionStack.addAll(tokens);
        Collections.reverse(executionStack);

        while(!executionStack.isEmpty()) {
            Token token = executionStack.pop();
            executeToken(token, numericStack);
        }

        return numericStack.pop();
    }
}
