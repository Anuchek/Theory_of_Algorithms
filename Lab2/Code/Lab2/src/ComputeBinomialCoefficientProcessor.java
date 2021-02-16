public class ComputeBinomialCoefficientProcessor {
    ComputeCoefficient computeCoefficient;
    int[][] arraysOfNandM;

    public ComputeBinomialCoefficientProcessor(ComputeCoefficient computeCoefficient, int[][] arraysOfNandM) {
        this.computeCoefficient = computeCoefficient;
        this.arraysOfNandM = arraysOfNandM;
    }

    public void process() {
        for (int[] array : arraysOfNandM) {
            try {
                TesterForBinomialCoefficient.testAndPrint(computeCoefficient, array[0], array[1]);
            } catch (IllegalArgumentException e) {
                System.out.println(e);
            }
        }
    }
}
