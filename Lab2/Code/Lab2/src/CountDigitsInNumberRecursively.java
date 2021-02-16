public class CountDigitsInNumberRecursively implements CountDigitsInNumber {

    @Override
    public int count(int number) {
        if (number < 10) return 1;
        int counter = 0;
        int result = number/10;
        counter++;
        counter += count(result);
        return counter;
    }

}
