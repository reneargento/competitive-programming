package algorithms.number.theory;

/**
 * Created by Rene Argento on 01/01/18.
 */
public class RoundDouble {

    // Based on https://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
    private double roundValuePrecisionDigits(double value, int digits) {
        long valueToMultiply = (long) Math.pow(10, digits);
        return (double) Math.round(value * valueToMultiply) / valueToMultiply;
    }

}
