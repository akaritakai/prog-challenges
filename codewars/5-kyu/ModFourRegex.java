import java.util.regex.Pattern;

/**
 * http://www.codewars.com/kata/mod4-regex/
 */
public class ModFourRegex {

    /**
     * This one requires a lot of explanation.
     *
     * Suppose we have a decimal number 'n', and we add a digit 'd' to it. The number becomes 10*n + d.
     * Using this, we can enumerate all of our expected scenarios:
     *
     * n mod 4 = 0
     *   (10n + 0) mod 4 = 0
     *   (10n + 1) mod 4 = 1
     *   ...
     * n mod 4 = 1
     *   (10n + 0) mod 4 = 2
     *   (10n + 1) mod 4 = 3
     *   ...
     * n mod 4 = 2
     *   (10n + 0) mod 4 = 0
     *   (10n + 1) mod 4 = 1
     *   ...
     * n mod 4 = 3
     *   (10n + 0) mod 4 = 2
     *   (10n + 1) mod 4 = 3
     *   ...
     * and so on.
     *
     * Using this information, we have enough info to create a 4 state DFA.
     *
     * Turning it into a regex is a bit harder. For that, I actually used a tool called JFLAP rather than working it
     * out myself on pencil and paper. But it is pretty trivial to "eliminate" states in lieu of regular expressions,
     * until you arrive at a single regex.
     *
     * Looking at what everyone else put, this can be simplified a lot.
     */
    public static Pattern mod4 = Pattern.compile("^.*\\[(\\+|-)?(0|4|8|(1|5|9)(3|7)*(2|6)|(2|6|(1|5|9)(3|7)*(0|4|8))(2|6|(1|5|9)(3|7)*(0|4|8))*(0|4|8|(1|5|9)(3|7)*(2|6))|(3|7|(1|5|9)(3|7)*(1|5|9)|(2|6|(1|5|9)(3|7)*(0|4|8))(2|6|(1|5|9)(3|7)*(0|4|8))*(3|7|(1|5|9)(3|7)*(1|5|9)))(1|5|9|(3|7)(3|7)*(1|5|9)|(0|4|8|(3|7)(3|7)*(0|4|8))(2|6|(1|5|9)(3|7)*(0|4|8))*(3|7|(1|5|9)(3|7)*(1|5|9)))*(2|6|(3|7)(3|7)*(2|6)|(0|4|8|(3|7)(3|7)*(0|4|8))(2|6|(1|5|9)(3|7)*(0|4|8))*(0|4|8|(1|5|9)(3|7)*(2|6))))(0|4|8|(1|5|9)(3|7)*(2|6)|(2|6|(1|5|9)(3|7)*(0|4|8))(2|6|(1|5|9)(3|7)*(0|4|8))*(0|4|8|(1|5|9)(3|7)*(2|6))|(3|7|(1|5|9)(3|7)*(1|5|9)|(2|6|(1|5|9)(3|7)*(0|4|8))(2|6|(1|5|9)(3|7)*(0|4|8))*(3|7|(1|5|9)(3|7)*(1|5|9)))(1|5|9|(3|7)(3|7)*(1|5|9)|(0|4|8|(3|7)(3|7)*(0|4|8))(2|6|(1|5|9)(3|7)*(0|4|8))*(3|7|(1|5|9)(3|7)*(1|5|9)))*(2|6|(3|7)(3|7)*(2|6)|(0|4|8|(3|7)(3|7)*(0|4|8))(2|6|(1|5|9)(3|7)*(0|4|8))*(0|4|8|(1|5|9)(3|7)*(2|6))))*\\].*$");

}
