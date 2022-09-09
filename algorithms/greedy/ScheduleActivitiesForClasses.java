package algorithms.greedy;

import java.io.IOException;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * Created by Rene Argento on 11/05/22.
 */
// Given N activities with start and end times and C classes, compute what is the maximum number of activities
// that can be scheduled.
// Classes can only have one activity at a time and (on the same class) an activity must end before
// another can start (n1.end < n2.start).
// Available at https://open.kattis.com/problems/classrooms
public class ScheduleActivitiesForClasses {

    private static class Activity implements Comparable<Activity> {
        int start;
        int end;

        public Activity(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Activity other) {
            if (end != other.end) {
                return Integer.compare(end, other.end);
            }
            return Integer.compare(start, other.start);
        }
    }

    private static class Time implements Comparable<Time> {
        int value;
        int index;

        public Time(int value, int index) {
            this.value = value;
            this.index = index; // Needed to have more than one node with the same time in the tree
        }

        @Override
        public int compareTo(Time other) {
            if (value != other.value) {
                return Integer.compare(value, other.value);
            }
            return Integer.compare(index, other.index);
        }
    }

    private static int computeMaximumActivitiesScheduled(Activity[] activities, int classrooms) {
        Arrays.sort(activities);
        TreeSet<Time> scheduledActivities = new TreeSet<>();

        int maximumActivitiesScheduled = 0;
        for (int i = 0; i < activities.length; i++) {
            Time startTime = new Time(-activities[i].start, 0);
            Time earliestEndTime = scheduledActivities.ceiling(startTime);

            // If it starts before all end times
            if (earliestEndTime == null) {
                if (scheduledActivities.size() < classrooms) {
                    scheduledActivities.add(new Time(-activities[i].end - 1, i));
                    maximumActivitiesScheduled++;
                }
                continue;
            }

            // Remove the activity that ends before the current activity and add it
            scheduledActivities.remove(earliestEndTime);
            scheduledActivities.add(new Time(-activities[i].end - 1, i));
            maximumActivitiesScheduled++;
        }
        return maximumActivitiesScheduled;
    }

    public static void main(String[] args) throws IOException {
        Activity[] activities1 = new Activity[4];
        activities1[0] = new Activity(1, 4);
        activities1[1] = new Activity(2, 9);
        activities1[2] = new Activity(4, 7);
        activities1[3] = new Activity(5, 8);
        int classrooms1 = 2;
        int maximumActivitiesScheduled1 = computeMaximumActivitiesScheduled(activities1, classrooms1);
        System.out.println("Maximum activities: " + maximumActivitiesScheduled1);
        System.out.println("Expected: 3");

        Activity[] activities2 = new Activity[8];
        activities2[0] = new Activity(2, 4);
        activities2[1] = new Activity(6, 8);
        activities2[2] = new Activity(10, 15);
        activities2[3] = new Activity(1, 3);
        activities2[4] = new Activity(5, 11);
        activities2[5] = new Activity(3, 5);
        activities2[6] = new Activity(7, 10);
        activities2[7] = new Activity(12, 14);
        int classrooms2 = 2;
        int maximumActivitiesScheduled2 = computeMaximumActivitiesScheduled(activities2, classrooms2);
        System.out.println("\nMaximum activities: " + maximumActivitiesScheduled2);
        System.out.println("Expected: 6");
    }
}
