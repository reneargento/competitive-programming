package algorithms.geometry;

/**
 * Created by Rene Argento on 26/12/17.
 */
public class IntegerCoordinatesInLine {

    // Based on https://math.stackexchange.com/questions/918362/what-is-the-number-of-integer-coordinates-on-a-line-segment
    private static int countIntegerCoordinatesInLine(int point1X, int point1Y, int point2X, int point2Y) {
        int minX = Math.min(point1X, point2X);
        int maxX = Math.max(point1X, point2X);
        int minY = Math.min(point1Y, point2Y);
        int maxY = Math.max(point1Y, point2Y);

        // Slope = (y2 - y1) / (x2 - x1)
        int slopeY = maxY - minY;
        int slopeX = maxX - minX;

        int gcd = (int) gcd(slopeY, slopeX);

        int reducedY = slopeY / gcd;
        int reducedX = slopeX / gcd;

        int integerCoordinatesInLine = 0;

        int currentX = minX;
        int currentY = minY;

        while (currentX <= maxX && currentY <= maxY) {
            integerCoordinatesInLine++;

            currentX += reducedX; // Increase reducedX for every point to check
            currentY += reducedY; // Increase reducedY for every point to check
        }

        return integerCoordinatesInLine;
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }
}
