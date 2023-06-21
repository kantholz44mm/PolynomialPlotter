package MathExpression;

public class Token {
    // in order of precedence
    public enum Type {
        Number,
        Function,
        Exponentiation,
        Multiplication,
        Division,
        Addition,
        Subtraction,
        Parenthesis_Left,
        Parenthesis_Right;

        public boolean isOperator() {
            switch(this) {
                case Exponentiation:
                case Division:
                case Multiplication:
                case Addition:
                case Subtraction:
                    return true;
                default: 
                    return false;
            }
        }
    }

    public Type type;
    public String literal;

    public Token(Type type, String literal) {
        this.type = type;
        this.literal = literal;
    }
}
