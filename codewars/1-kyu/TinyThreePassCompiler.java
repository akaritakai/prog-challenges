import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://www.codewars.com/kata/tiny-three-pass-compiler/
 */
public class TinyThreePassCompiler {
    
    public List<String> compile(final String prog) {
        return pass3(pass2(pass1(prog)));
    }

    /**
     * Returns an un-optimized AST
     */
    public Ast pass1(final String prog) {
        final Deque<String> tokens = tokenize(prog);
        // Process args
        final List<String> args = new ArrayList<>();
        if (tokens.peek().equals("[")) {
            tokens.pop(); // start of arg-list
            while (!tokens.peek().equals("]")) {
                args.add(tokens.pop());
            }
            tokens.pop(); // end of arg-list
        }
        // Shunting-Yard Algorithm
        final Stack<Ast> operands = new Stack<>();
        final Stack<String> operators = new Stack<>();
        while (!tokens.isEmpty()) {
            final String token = tokens.pop();
            if ("+-".contains(token)) {
                while (!operators.isEmpty() && "+-*/".contains(operators.peek())) {
                    processOp(operands, operators);
                }
                operators.push(token);
            }
            else if ("*/".contains(token)) {
                while (!operators.isEmpty() && "*/".contains(operators.peek())) {
                    processOp(operands, operators);
                }
                operators.push(token);
            }
            else if (token.equals("(")) {
                operators.push("(");
            }
            else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    processOp(operands, operators);
                }
                operators.pop();
            }
            else if (token.matches("[A-Za-z]+")) {
                operands.push(new UnOp("arg", args.indexOf(token)));
            }
            else {
                operands.push(new UnOp("imm", Integer.parseInt(token)));
            }
        }
        while (!operators.isEmpty()) {
            processOp(operands, operators);
        }
        return operands.pop();
    }

    /**
     * Returns an AST with constant expressions reduced
     */
    public Ast pass2(final Ast ast) {
        final Function<Ast, Ast> simplify = (node) -> {
            if (node instanceof BinOp) {
                final Ast a = ((BinOp)node).a();
                final Ast b = ((BinOp)node).b();
                if (a.op().equals("imm") && b.op().equals("imm")) {
                    final int lhs = ((UnOp)a).n();
                    final int rhs = ((UnOp)b).n();
                    switch (node.op()) {
                        case "+": return new UnOp("imm", lhs + rhs);
                        case "-": return new UnOp("imm", lhs - rhs);
                        case "*": return new UnOp("imm", lhs * rhs);
                        case "/": return new UnOp("imm", lhs / rhs);
                    }
                }
            }
            return node;
        };
        if (ast instanceof BinOp) {
            final BinOp binOp = (BinOp) ast;
            final Ast a = simplify.apply(pass2(binOp.a()));
            final Ast b = simplify.apply(pass2(binOp.b()));
            return new BinOp(ast.op(), a, b);
        }
        return ast;
    }

    /**
     * Returns assembly instructions
     */
    public List<String> pass3(final Ast ast) {
        final List<String> list = new ArrayList<>();
        if (ast instanceof BinOp) {
            final BinOp node = (BinOp) ast;
            list.addAll(pass3(node.a()));
            list.addAll(pass3(node.b()));
        }
        switch (ast.op()) {
            case "imm": {
                list.add("IM " + ((UnOp) ast).n());
                list.add("PU");
                break;
            }
            case "arg": {
                list.add("AR " + ((UnOp) ast).n());
                list.add("PU");
                break;
            }
            case "+": {
                list.addAll(Arrays.asList("PO", "SW", "PO", "AD", "PU"));
                break;
            }
            case "-": {
                list.addAll(Arrays.asList("PO", "SW", "PO", "SU", "PU"));
                break;
            }
            case "*": {
                list.addAll(Arrays.asList("PO", "SW", "PO", "MU", "PU"));
                break;
            }
            case "/": {
                list.addAll(Arrays.asList("PO", "SW", "PO", "DI", "PU"));
                break;
            }
        }
        return list;
    }

    private static void processOp(final Stack<Ast> operands, final Stack<String> operators) {
        final String op = operators.pop();
        final Ast rhs = operands.pop();
        final Ast lhs = operands.pop();
        operands.push(new BinOp(op, lhs, rhs));
    }

    private static Deque<String> tokenize(final String prog) {
        final Deque<String> tokens = new LinkedList<>();
        final Pattern pattern = Pattern.compile("[-+*/()\\[\\]]|[a-zA-Z]+|\\d+");
        final Matcher m = pattern.matcher(prog);
        while (m.find()) {
            tokens.add(m.group());
        }
        return tokens;
    }

}
