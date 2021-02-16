public class ComputeBinomialCoefficient implements ComputeCoefficient {

    @Override
    public int compute(int m, int n) {
        if (0 > m || m > n) throw new IllegalArgumentException("m must be positive and more than n!");
        if (m == n || m == 0) return 1;
        return compute(m, n - 1) + compute(m - 1, n - 1);
    }

}
