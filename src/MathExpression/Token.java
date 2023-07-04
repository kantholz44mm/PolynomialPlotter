package MathExpression;

public class Token {
    // in order of precedence
    public enum Type {
        Number,
        Parameter,
        Exponentiation,
        Division,
        Multiplication,
        Addition,
        Subtraction,
        Function,
        Parenthesis_Left,
        Parenthesis_Right;

        public boolean isOperator() {
            return switch(this) {
                case Exponentiation, Division, Multiplication, Addition, Subtraction, Function -> true;
                default -> false;
            };
        }

        public boolean isUnaryMinusPrefix() {
            return switch(this) {
                case Exponentiation, Division, Multiplication, Addition, Subtraction, Parenthesis_Left -> true;
                default -> false;
            };
        }
    }

    public Type type;
    public String literal;

    public Token(Type type, String literal) {
        this.type = type;
        this.literal = literal;
    }
}
