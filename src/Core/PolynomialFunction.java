package Core;

import MathExpression.ExpressionExecutor;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.*;
import java.util.*;

public class PolynomialFunction implements ParametricFunction {
    private String functionString;
    private double[] coefficients;
    public Set<Double> roots;
    public Set<Double> extremePoints;
    public Color graphColor;
    private static final double EPSILON = 1E-18;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public PolynomialFunction(String polynomialString, Color color) {
        this.coefficients = new double[]{0};
        this.functionString = polynomialString;
        this.graphColor = color;
        fromString(polynomialString);
    }

    public void fromString(String polynomial) throws IllegalArgumentException {
        if (polynomial == null || polynomial.isEmpty()) {
            throw new IllegalArgumentException("Polynomial string cannot be null or empty");
        }

        // Split the polynomial into terms by '+' or '-'
        // (but not within parentheses) and include the delimiter in the term
        String[] terms = polynomial.split("((?<=\\b)|(?<=\\B))(?=[+-])(?![^(]*\\))");

        for (String term : terms) {
            Matcher matcher = Pattern.compile("([-+]?\\s*([^x^]+)?\\s*x?(\\^(-?\\d+))?)?").matcher(term);

            if (matcher.matches()) {
                String coefStr = removeWhitespace(matcher.group(2));
                String expStr = removeWhitespace(matcher.group(4));
                ExpressionExecutor executor = new ExpressionExecutor("");

                double coef;
                int exp;

                if (isNullOrEmpty(coefStr)) {
                    coef = 1.0;
                } else {
                    executor.setExpression(coefStr);
                    coef = executor.evaluate();
                }

                if (term.startsWith("-")) {
                    coef *= -1.0;
                }

                if (matcher.group(3) != null) {
                    // 'x' is present, so this term has an exponent
                    exp = isNullOrEmpty(expStr) ? 1 : Integer.parseInt(expStr);
                } else {
                    // 'x' is not present, so this term is a constant
                    exp = 0;
                }

                extendCoefficientsArrayIfNeeded(exp);
                this.coefficients[exp] += coef;
            } else {
                throw new IllegalArgumentException("Invalid term: " + term);
            }
        }
    }

    private String removeWhitespace(String str) {
        return str == null ? null : str.trim();
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void extendCoefficientsArrayIfNeeded(int exp) {
        if (exp >= this.coefficients.length) {
            this.coefficients = Arrays.copyOf(this.coefficients, exp + 1);
        }
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

    public void calcRootsAndExtremes(double start, double end, double step) {
        // If function is a constant skip the calculation
        if(coefficients.length == 1) {
            roots = new HashSet<>();
            extremePoints = new HashSet<>();
            roots.add(Double.NaN);
            extremePoints.add(Double.NaN);
            return;
        }
        Callable<Set<Double>> task1 = () -> calcRoots(start, end, step);
        Callable<Set<Double>> task2 = () -> calcExtremes(start, end, step);

        try {
            List<Future<Set<Double>>> results = executorService.invokeAll(Arrays.asList(task1, task2));
            roots = results.get(0).get();
            extremePoints = results.get(1).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Set<Double> calcRoots(double start, double end, double step) {
        Set<Double> roots = new HashSet<>();
        double prevY = evaluate(start).y;
        for (double x = start + step; x <= end; x += step) {
            double y = evaluate(x).y;
            // If the value is incredibly close to zero, it is a root => Add
            if (Math.abs(y) < EPSILON) {
                roots.add(roundDouble(x));
            // First use Incremental Search to check for a sign change (if-statement)
            // then calculate an approximately really close position using linear interpolation
            } else if (prevY * y < 0) {
                double root = (x - step) + step * Math.abs(prevY) / (Math.abs(prevY) + Math.abs(y));
                roots.add(roundDouble(root));

            }
            prevY = y;
        }
        return roots;
    }

    private Set<Double> calcExtremes(double start, double end, double step) {
        Set<Double> extremes = new HashSet<>();
        double prevSlope = evaluateDerivative(start);
        for (double x = start + step; x <= end; x += step) {
            double slope = evaluateDerivative(x);
            // If the value of the derivative is incredibly close to zero, it is an extreme point => Add
            if (Math.abs(slope) < EPSILON) {
                extremes.add(roundDouble(x));
            // First use Incremental Search to check for a sign change (if-statement)
            // then calculate an approximately really close position using linear interpolation
            } else if (prevSlope * slope < 0) {
                double extreme = (x - step) + step * Math.abs(prevSlope) / (Math.abs(prevSlope) + Math.abs(slope));
                extremes.add(roundDouble(extreme));
            }
            prevSlope = slope;
        }
        return extremes;
    }

    private double roundDouble(double value) {
        return new BigDecimal(value).setScale(6, RoundingMode.HALF_UP).doubleValue();
    }

    private double evaluateDerivative(double x) {
        double result = 0;
        for (int i = this.coefficients.length - 1; i >= 1; i--) {
            result = result * x + this.coefficients[i] * i;
        }
        return result;
    }

    public String getFunctionString() {
        return functionString;
    }
}
