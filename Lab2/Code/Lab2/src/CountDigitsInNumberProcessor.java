public class CountDigitsInNumberProcessor {
    private final CountDigitsInNumber countDigitsInNumbers;
    private final int[] arrayOfNumbers;

    public CountDigitsInNumberProcessor(CountDigitsInNumber countDigitsInNumbers, int ... arrayOfNumbers) {
        this.countDigitsInNumbers = countDigitsInNumbers;
        this.arrayOfNumbers = arrayOfNumbers;
    }


    public void process() {
        for (int num : arrayOfNumbers) {
            TesterForCountDigitsInNumber.testAndPrint(countDigitsInNumbers, num);
        }
    }

}
