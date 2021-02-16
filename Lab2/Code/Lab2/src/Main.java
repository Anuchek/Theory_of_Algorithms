public class Main {
    public static void main(String[] args) {
        CountDigitsInNumberProcessor countDigits = new CountDigitsInNumberProcessor(new CountDigitsInNumberRecursively(),
                 0, 5, 12, 123, 1234, 12_345, 1_000_000);
        countDigits.process();

        ComputeBinomialCoefficientProcessor computeBiCoef = new ComputeBinomialCoefficientProcessor(new ComputeBinomialCoefficient(),
                new int[][] { {1,2}, {2,4}, {5,5}, {3,2}, {0,2}, {-1, 2} });
        computeBiCoef.process();
    }
}

