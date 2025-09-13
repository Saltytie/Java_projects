
import java.util.Scanner;

public class Main {
    private static int answer;
    private static boolean error;
    public static void postfixCalculator(String line, int x1, int x2, int x3, int x4, int x5) {
        Stack stack = new Stack(line.length());
        int indexDigit;
        int lastElementLater;
        int lastElementEarlier;
        StringBuilder strDigit = new StringBuilder();
        String nextNum;

        for (int i = 0; i < line.length(); ++i) {
            char symbol = line.charAt(i);
            if (Character.isDigit(symbol)) {
                indexDigit = i;
                while (indexDigit != line.length() && Character.isDigit(line.charAt(indexDigit))) {
                    strDigit.append(line.charAt(indexDigit));
                    indexDigit += 1;
                }
                if (indexDigit != line.length() && !Character.toString(line.charAt(indexDigit)).equals(" ")) {
                    System.out.println("Error: there must be a space after the number. Rewrite the expression.");
                    error = true;
                    return;
                }
                i = indexDigit - 1;
                int digit = Integer.parseInt(strDigit.toString());
                stack.push(digit);
                strDigit = new StringBuilder();

            } else if (Character.toString(symbol).equals("+")) {

                if (stack.isTwoElements()) {
                    if (i != line.length() - 1 && Character.toString(line.charAt(i + 1)).equals(" ")) {
                        lastElementLater = stack.pop();
                        lastElementEarlier = stack.pop();
                        stack.push(lastElementEarlier + lastElementLater);
                    } else if (i == line.length() - 1){
                        lastElementLater = stack.pop();
                        lastElementEarlier = stack.pop();
                        stack.push(lastElementEarlier + lastElementLater);
                    } else {
                        System.out.println("Error: it should be a space after an operand.");
                        error = true;
                        return;
                    }
                } else {
                    System.out.println("Error: too few numbers for so many operands. Rewrite the expression.");
                    error = true;
                    return;
                }
            } else if (Character.toString(symbol).equals("-")) {

                if (stack.isTwoElements()) {
                    if (i != line.length() - 1 && Character.toString(line.charAt(i + 1)).equals(" ")) {
                        lastElementLater = stack.pop();
                        lastElementEarlier = stack.pop();
                        stack.push(lastElementEarlier - lastElementLater);
                    } else if (i == line.length() - 1){
                        lastElementLater = stack.pop();
                        lastElementEarlier = stack.pop();
                        stack.push(lastElementEarlier - lastElementLater);
                    } else {
                        System.out.println("Error: it should be a space after an operand.");
                        error = true;
                        return;
                    }
                } else {
                    System.out.println("Error: too few numbers for so many operands. Rewrite the expression.");
                    error = true;
                    return;
                }
            } else if (Character.toString(symbol).equals("/")) {

                if (stack.isTwoElements()) {
                    if (i != line.length() - 1 && Character.toString(line.charAt(i + 1)).equals(" ")) {
                        lastElementLater = stack.pop();
                        lastElementEarlier = stack.pop();
                        stack.push(lastElementEarlier / lastElementLater);
                    } else if (i == line.length() - 1){
                        lastElementLater = stack.pop();
                        lastElementEarlier = stack.pop();
                        stack.push(lastElementEarlier / lastElementLater);
                    } else {
                        System.out.println("Error: it should be a space after an operand.");
                        error = true;
                        return;
                    }
                } else {
                    System.out.println("Error: too few numbers for so many operands. Rewrite the expression.");
                    error = true;
                    return;
                }
            } else if (Character.toString(symbol).equals("*")) {

                if (stack.isTwoElements()) {
                    if (i != line.length() - 1 && Character.toString(line.charAt(i + 1)).equals(" ")) {
                        lastElementLater = stack.pop();
                        lastElementEarlier = stack.pop();
                        stack.push(lastElementEarlier * lastElementLater);
                    } else if (i == line.length() - 1){
                        lastElementLater = stack.pop();
                        lastElementEarlier = stack.pop();
                        stack.push(lastElementEarlier * lastElementLater);
                    } else {
                        System.out.println("Error: it should be a space after an operand.");
                        error = true;
                        return;
                    }
                } else {
                    System.out.println("Error: too few numbers for so many operands. Rewrite the expression.");
                    error = true;
                    return;
                }
            } else if (Character.toString(symbol).equals(" ")) {
                if (i == 0 || i == (line.length() - 1)) {
                    System.out.println("Error: delete spaces along the edges. Rewrite the expression.");
                    error = true;
                    return;
                } else if (Character.toString(line.charAt(i + 1)).equals(" ")) {
                    System.out.println("Error: too many spaces. Rewrite the expression.");
                    error = true;
                    return;
                }

            } else if (Character.toString(symbol).equals("x")) {
                if ((i + 1) != line.length() && (i != 0)) {

                    nextNum = Character.toString(line.charAt(i - 1)) + Character.toString((line.charAt(i + 1)));


                    switch (nextNum) {
                        case " 1":
                            stack.push(x1);
                            i += 1;
                            break;
                        case " 2":
                            stack.push(x2);
                            i += 1;
                            break;
                        case " 3":
                            stack.push(x3);
                            i += 1;
                            break;
                        case " 4":
                            stack.push(x4);
                            i += 1;
                            break;
                        case " 5":
                            stack.push(x5);
                            i += 1;
                            break;
                        default:
                            System.out.println("Error: unknown variable name. Rewrite the expression.");
                            error = true;
                            return;
                    }
                } else if ((i + 1) != line.length() && (i == 0)) {
                    nextNum = Character.toString((line.charAt(i + 1)));
                    switch (nextNum) {
                        case "1":
                            stack.push(x1);
                            i += 1;
                            break;
                        case "2":
                            stack.push(x2);
                            i += 1;
                            break;
                        case "3":
                            stack.push(x3);
                            i += 1;
                            break;
                        case "4":
                            stack.push(x4);
                            i += 1;
                            break;
                        case "5":
                            stack.push(x5);
                            i += 1;
                            break;
                        default:
                            System.out.println("Error: unknown variable name. Rewrite the expression.");
                            error = true;
                            return;
                    }
                } else {
                    System.out.println("Error: unknown variable name: x. Rewrite the expression.");
                    error = true;
                    return;
                }

            } else {
                System.out.println("Error: unknown symbol: " + symbol);
                error = true;
                return;
            }
        }
        if (stack.top == 0) {
            answer = stack.lookElement();
            System.out.println(stack.lookElement());

        } else {
            System.out.println("Error: too few operands for so many numbers. Rewrite the expression.");
            error = true;

        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write an expression, if you want to stop, enter an empty line.");
        String expression = scanner.nextLine();

        int x1 = 0;
        int x2 = 0;
        int x3 = 0;
        int x4 = 0;
        int x5 = 0;
        int nextNum;
        int indexEq = 0;
        StringBuilder afterEq;
        StringBuilder beforeEq;

        while (!expression.isEmpty()) {
            error = false;
            afterEq = new StringBuilder();
            beforeEq = new StringBuilder();
            boolean appropriationOrNot = false;
            for (int i = 0; i < expression.length(); ++i) {
                if (Character.toString(expression.charAt(i)).equals("=")) {
                    appropriationOrNot = true;
                    indexEq = i;
                    if (indexEq == 0 || indexEq == (expression.length() - 1)) {
                        System.out.println("Error: assignment operation performed incorrectly.");
                        error = true;
                    }
                    else if (indexEq != 3) {
                        System.out.println("Error: unknown assignment variable. Rewrite expression.");
                        error = true;
                    }
                    break;

                }
            }

            if (appropriationOrNot && !error){
                for (int i = 0; i < indexEq + 2; ++i) {
                    beforeEq.append(expression.charAt(i));
                }
                for (int i = (indexEq + 2); i < expression.length(); ++i){
                    afterEq.append(expression.charAt(i));
                }
                switch (beforeEq.toString()) {
                    case "x1 = ":
                        postfixCalculator(afterEq.toString(), x1, x2, x3, x4, x5);
                        if (!error) {
                            x1 = answer;
                        }
                        break;
                    case "x2 = ":
                        postfixCalculator(afterEq.toString(), x1, x2, x3, x4, x5);
                        if (!error) {
                            x2 = answer;
                        }
                        break;
                    case "x3 = ":
                        postfixCalculator(afterEq.toString(), x1, x2, x3, x4, x5);
                        if (!error) {
                            x3 = answer;
                        }
                        break;
                    case "x4 = ":
                        postfixCalculator(afterEq.toString(), x1, x2, x3, x4, x5);
                        if (!error) {
                            x4 = answer;
                        }
                        break;
                    case "x5 = ":
                        postfixCalculator(afterEq.toString(), x1, x2, x3, x4, x5);
                        if (!error) {
                            x5 = answer;
                        }
                        break;
                    default:
                        System.out.println("Error: unknown variable.");
                        error = true;
                        break;
                }
            }
            else if (!error){
                postfixCalculator(expression, x1, x2, x3, x4, x5);
            }
            System.out.println("Write an expression.");
            expression = scanner.nextLine();
        }
    }
}


