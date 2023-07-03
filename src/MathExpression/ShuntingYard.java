package MathExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import MathExpression.Token.Type;

public class ShuntingYard {

    private static void processToken(Token token, List<Token> postfix, Stack<Token> shunt) {
        switch(token.type) {
            case Number: 
            case Parameter:
            {
                postfix.add(token); 
                break;
            }

            case Addition:
            case Subtraction:
            case Multiplication:
            case Division:
            case Exponentiation:
            {
                // pop any previous, higher precedence operators.
                while(!shunt.isEmpty() && shunt.peek().type.isOperator() && shunt.peek().type.ordinal() <= token.type.ordinal()) {
                    postfix.add(shunt.pop());
                }
                shunt.push(token);
                break;
            }

            case Function:
            case Parenthesis_Left:
            {
                shunt.push(token);
                break;
            }
                
            case Parenthesis_Right:
            {
                // unwind parentheses
                while(!shunt.isEmpty() && shunt.peek().type != Type.Parenthesis_Left) {
                    postfix.add(shunt.pop());
                }

                shunt.pop();
                if(!shunt.isEmpty() && shunt.peek().type == Type.Function) {
                    postfix.add(shunt.pop());
                }
                break;
            }

            default:
            {
                break;
            }
        }
    }

    public static List<Token> infixToPostfix(List<Token> infix) {
        List<Token> postfix = new ArrayList<>();
        Stack<Token> shunt = new Stack<>();

        for(Token token : infix) {
            processToken(token, postfix, shunt);
        }

        while (!shunt.isEmpty()) {
            postfix.add(shunt.pop());
        }
        
        return postfix;
    }
}