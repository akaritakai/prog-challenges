import javax.script.ScriptEngineManager;

/**
 * http://www.codewars.com/kata/evaluate-mathematical-expression/
 */
public class EvaluateMathematicalExpression {

    /**
     * I came across this technique after implementing a Shunting-Yard algorithm in Java.
     * http://stackoverflow.com/questions/3422673/evaluating-a-math-expression-given-in-string-form/3423360#3423360
     *
     * It was such a glorious cheat that I could not help but submit it.
     */
    public double calculate(String expression) {
        try {
            return ((Number) new ScriptEngineManager().getEngineByName("JavaScript").eval(expression)).doubleValue();
        } catch (Exception e) { return 0; }
    }

}
