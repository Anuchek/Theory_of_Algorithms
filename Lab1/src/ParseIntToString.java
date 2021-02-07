public class ParseIntToString {

    public static String parseIntToString(int num) {
        //Step 1
        //in case if number equals to million or zero we return const String
        if (num == 0) return "zero";
        if (num == 1_000_000) return "one million";

        StringBuilder resultBuilder = new StringBuilder();
        boolean twoDigitFirstDigitIs1 = false;
        boolean fiveDigitFourthIs1 = false;


        for (int amountOfDigits = Integer.toString(num).length(); amountOfDigits > 0; amountOfDigits--, num %= Math.pow(10, amountOfDigits)) {
            switch (amountOfDigits) {
                case 1 -> {
                    if (!twoDigitFirstDigitIs1) resultBuilder.append(Switch.switchForOneDigit(num));
                }
                case 2 -> {
                    if (num / 10 == 1) twoDigitFirstDigitIs1 = true;
                    resultBuilder.append(Switch.switchForTwoDigits(num));
                }
                case 3 -> resultBuilder.append(Switch.switchForOneDigit(num / 100)).append("hundred ");

                case 4 -> {
                    if (!fiveDigitFourthIs1)
                        resultBuilder.append(Switch.switchForOneDigit(num / 1000)).append("thousand ");
                }
                case 5 -> {
                    resultBuilder.append(Switch.switchForTwoDigits(num / 1000));
                    if (num / 10000 == 1) {
                        fiveDigitFourthIs1 = true;
                        resultBuilder.append("thousand ");
                    }
                }
                case 6 -> resultBuilder.append(Switch.switchForOneDigit(num / 100_000)).append("hundred ");
            }
        }
        return resultBuilder.toString();
    }
}
