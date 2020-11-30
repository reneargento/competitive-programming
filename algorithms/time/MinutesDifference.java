package algorithms.time;

/**
 * Created by Rene Argento on 17/11/20.
 */
public class MinutesDifference {

    public static void main(String[] args) {
        int minutesDifference = getMinutesDifference(22, 30, 9, 15);
        System.out.println("Minutes difference: " + minutesDifference);
        System.out.println("Expected: 645");
    }

    private static int getMinutesDifference(int startHour, int startMinute, int endHour, int endMinute) {
        int hoursDifference;
        int minutesDifference;

        if (startHour < endHour
                || (startHour == endHour && startMinute <= endMinute)) {
            hoursDifference = endHour - startHour;
        } else {
            hoursDifference = (24 - startHour) + endHour;
        }
        if (endMinute < startMinute) {
            hoursDifference--;
        }

        if (startMinute <= endMinute) {
            minutesDifference = endMinute - startMinute;
        } else {
            minutesDifference = (60 - startMinute) + endMinute;
        }
        return minutesDifference + hoursDifference * 60;
    }

}
