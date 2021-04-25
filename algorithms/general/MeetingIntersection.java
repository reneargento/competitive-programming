package algorithms.general;

/**
 * Created by Rene Argento on 29/03/21.
 */
// Given a meeting with start and end hours, check if it intersects with a given start hour and its length
public class MeetingIntersection {

    private static class Meeting {
        int start;
        int end;

        public Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    private static boolean intersects(Meeting meeting, int start, int length) {
        if (start <= meeting.start && start + length >= meeting.end) {
            return false;
        }
        if (start > meeting.start && start + length <= meeting.end) {
            return false;
        }
        if (start <= meeting.start && start + length >= meeting.start) {
            return false;
        }
        if (start < meeting.end && start + length >= meeting.end) {
            return false;
        }
        return true;
    }

}
