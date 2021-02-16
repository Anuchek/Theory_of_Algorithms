public class CountDigitsInNumberIteratively implements CountDigitsInNumber {

    @Override
    public int count(int number) {
        int count = (number == 0) ? 1 : 0;
        while (number != 0) {
            count++;
            number /= 10;
        }
        return count;
    }

}
