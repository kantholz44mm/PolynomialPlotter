package MathExpression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private static final HashMap<Token.Type, Pattern> patterns = new HashMap<>() {{
        put(Token.Type.Number,              Pattern.compile("^[0-9]+(?:[.][0-9]+)?"));
        put(Token.Type.Parameter,           Pattern.compile("^([a-z](?=[^a-z])|[a-z]$)"));
        put(Token.Type.Function,            Pattern.compile("^(sin|cos|tan|sqrt|log|ln)"));
        put(Token.Type.Addition,            Pattern.compile("^\\+"));
        put(Token.Type.Subtraction,         Pattern.compile("^\\-"));
        put(Token.Type.Multiplication,      Pattern.compile("^\\*"));
        put(Token.Type.Division,            Pattern.compile("^\\/"));
        put(Token.Type.Exponentiation,      Pattern.compile("^\\^"));
        put(Token.Type.Parenthesis_Left,    Pattern.compile("^\\("));
        put(Token.Type.Parenthesis_Right,   Pattern.compile("^\\)"));
    }};

    public static List<Token> tokenizeExpression(String expression) {
        List<Token> tokens = new ArrayList<Token>();
        expression = expression.replace(" ", "");
        
        while(expression.length() > 0) {
            boolean validToken = false;

            for(var pattern : patterns.entrySet()) {
                Matcher matcher = pattern.getValue().matcher(expression);
                if(matcher.find()) {
                    String match = matcher.group();
                    expression = expression.substring(match.length());
                    tokens.add(new Token(pattern.getKey(), match));
                    validToken = true;
                    break;
                }
            }

            if(!validToken) {
                System.out.println("invalid token");
                return null;
            }
        }

        // handle unary '-''
        for(int i = 0; i < tokens.size() - 1; i++) {
            Token currentToken = tokens.get(i);
            Token nextToken = tokens.get(i + 1);
            boolean hasUnaryPrefix = (i == 0 || tokens.get(i - 1).type.isUnaryMinusPrefix());

            if(currentToken.type == Token.Type.Subtraction && nextToken.type == Token.Type.Number && hasUnaryPrefix) {
                tokens.remove(i);
                Token numericToken = tokens.get(i);
                numericToken.literal = "-" + numericToken.literal;
            }
        }

        return tokens;
    }
}
