package MathExpression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private static final HashMap<Token.Type, Pattern> patterns = new HashMap<>() {{
        put(Token.Type.Number,              Pattern.compile("^(\\d+(?:\\.\\d+)?)"));
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

        return tokens;
    }
}
