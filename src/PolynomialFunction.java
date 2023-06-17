import java.util.regex.*;
import java.util.Arrays;
import java.util.*;

public class PolynomialFunction implements ParametricFunction {

    private double[] coefficients;
    public String functionString;
    private static final double DEFAULT_START_X = -100.0;
    private static final double DEFAULT_END_X = 100.0;
    private static final double DEFAULT_STEP = 0.01;
    private static final Pattern TERM_PATTERN = Pattern.compile("([-+]?\\s*\\d*\\.?\\d*(?:/\\d+)*)?x(\\^(-?\\d+))?|([-+]?\\s*\\d+(/\\d+)?)");

    public PolynomialFunction(String polynomialString) {
        this.coefficients = new double[]{0};
        this.functionString = polynomialString;
        fromString(polynomialString);
    }

    public void fromString(String polynomial) {
        if (polynomial == null || polynomial.isEmpty()) {
            throw new IllegalArgumentException("Polynomial string cannot be null or empty");
        }

        Matcher matcher = TERM_PATTERN.matcher(polynomial);

        while (matcher.find()) {
            String coefStr = removeWhitespace(matcher.group(1));
            String expStr = removeWhitespace(matcher.group(3));
            String constantStr = removeWhitespace(matcher.group(4));

            double coef;
            int exp;

            if (!isEmpty(constantStr)) {
                coef = parseFraction(constantStr);
                exp = 0;
            } else {
                coef = 1.0;
                if (!isEmpty(coefStr)) {
                    coef = parseFractionOrSign(coefStr);
                }
                exp = isEmpty(expStr) ? 1 : Integer.parseInt(expStr);
            }

            extendCoefficientsArrayIfNeeded(exp);
            this.coefficients[exp] = coef;
        }
    }

    private String removeWhitespace(String str) {
        return str == null ? null : str.trim();
    }

    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private double parseFractionOrSign(String str) {
        if (str.equals("+") || str.equals("-")) {
            return str.equals("+") ? 1.0 : -1.0;
        }
        return parseFraction(str);
    }

    private void extendCoefficientsArrayIfNeeded(int exp) {
        if (exp >= this.coefficients.length) {
            this.coefficients = Arrays.copyOf(this.coefficients, exp + 1);
        }
    }

    private double parseFraction(String input) {
        if (input.contains("/")) {
            String[] fractionParts = input.split("/");
            return Double.parseDouble(fractionParts[0]) / Double.parseDouble(fractionParts[1]);
        } else {
            return Double.parseDouble(input);
        }
    }

    public int degree() {
        for (int i = coefficients.length - 1; i >= 0; i--) {
            double coefficient = coefficients[i];
            if (coefficient != 0.0) {
                return i;
            }
        }

        return 0;
    }

    @Override
    public Vector2D evaluate(double t) {
        // Horner scheme
        double result = 0;
        for (int i = this.coefficients.length - 1; i >= 0; i--) {
            result = result * t + this.coefficients[i];
        }
        return new Vector2D(t, result);
    }

    public void derive() {
        if (coefficients == null || coefficients.length == 0) {
            return;
        }

        for (int i = 1; i < coefficients.length; i++) {
            coefficients[i - 1] = i * coefficients[i];
        }

        coefficients[coefficients.length - 1] = 0;

        StringBuilder sb = new StringBuilder();
        for (int i = coefficients.length - 1; i >= 0; i--) {
            double coefficient = coefficients[i];
            if (coefficient != 0) {
                if (coefficient > 0 && sb.length() > 0) {
                    sb.append("+");
                }
                if (i > 0) {
                    sb.append(String.format("%.2fx^%d", coefficient, i));
                } else {
                    sb.append(String.format("%.2f", coefficient));
                }
            }
        }
        functionString =  sb.toString();
    }

    public List<Double> getZeroPoints() {
        return getZeroPoints(DEFAULT_START_X, DEFAULT_END_X, DEFAULT_STEP);
    }

    public List<Double> getExtremePoints() {
        return getExtremePoints(DEFAULT_START_X, DEFAULT_END_X, DEFAULT_STEP);
    }

    public List<Double> getZeroPoints(double start, double end, double step) {
        List<Double> zeros = new ArrayList<>();
        Vector2D prevPoint = evaluate(start);
        for (double x = start + step; x <= end; x += step) {
            Vector2D point = evaluate(x);
            if (prevPoint.y * point.y <= 0) {
                zeros.add(x - step / 2);
            }
            prevPoint = point;
        }
        return zeros;
    }

    public List<Double> getExtremePoints(double start, double end, double step) {
        List<Double> extremes = new ArrayList<>();
        Vector2D startPoint = evaluate(start);
        Vector2D nextPoint = evaluate(start + step);
        double prevSlope = (nextPoint.y - startPoint.y) / step;
        for (double x = start + 2 * step; x <= end; x += step) {
            Vector2D point = evaluate(x);
            double slope = (point.y - evaluate(x - step).y) / step;
            if (prevSlope * slope <= 0) {
                extremes.add(x - step);
            }
            prevSlope = slope;
        }
        return extremes;
    }

    public String getFunctionString() {
        return functionString;
    }
}
