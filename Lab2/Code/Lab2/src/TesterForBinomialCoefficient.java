public class TesterForBinomialCoefficient {

    public static void testAndPrint(ComputeCoefficient computeCoefficient, int m, int n) {
        System.out.printf("C(%d,%d) = %d\n", m, n, computeCoefficient.compute(m, n) );
    }

}
