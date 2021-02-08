public class Main {

    public static void main(String[] args) {
        printNumberInLetters(0);
        printNumberInLetters(4);
        printNumberInLetters(14);
        printNumberInLetters(45);
        printNumberInLetters(456);
        printNumberInLetters(4567);
        printNumberInLetters(45_678);
        printNumberInLetters(456_789);
        printNumberInLetters(999_999);
        printNumberInLetters(1_000_000);
    }

    public static void printNumberInLetters(int number) {
        System.out.print(number + ": ");
        System.out.println(ParseIntToString.parseIntToString(number));
    }
}