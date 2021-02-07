public class Switch {

    public static String switchForOneDigit(int digit) {
        return switch (digit) {
            case 1 -> "one ";
            case 2 -> "two ";
            case 3 -> "three ";
            case 4 -> "four ";
            case 5 -> "five ";
            case 6 -> "six ";
            case 7 -> "seven ";
            case 8 -> "eight ";
            case 9 -> "nine ";
            default -> "";
        };

    }

    public static String switchForTwoDigits(int digits) {
        return switch (digits / 10) {
            case 1 -> switchForTwoDigitsPart2(digits);
            case 2 -> "twenty ";
            case 3 -> "thirty ";
            case 4 -> "forty ";
            case 5 -> "fifty ";
            case 6 -> "sixty ";
            case 7 -> "seventy ";
            case 8 -> "eighty ";
            case 9 -> "ninety ";
            default -> "";
        };
    }

    public static String switchForTwoDigitsPart2(int digits) {
        return switch (digits % 10) {
            case 0 -> "ten ";
            case 1 -> "eleven ";
            case 2 -> "twelve";
            case 3 -> "thirteen ";
            case 4 -> "fourteen ";
            case 5 -> "fifteen ";
            case 6 -> "sixteen ";
            case 7 -> "seventeen ";
            case 8 -> "eighteen ";
            case 9 -> "nineteen ";
            default -> "";
        };
    }
}