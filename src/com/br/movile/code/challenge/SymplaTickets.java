package com.br.movile.code.challenge;

import java.util.*;

public class SymplaTickets {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int months = scanner.nextInt();

        for(int month = 0; month < months; month++) {
            int juniorMoney = scanner.nextInt();

            int eventNumber = scanner.nextInt();
            int[] events = new int[eventNumber];

            // Maps event prices to indexes
            Map<Integer, Integer> eventsMap = new HashMap<>();

            for(int eventIndex = 0; eventIndex < events.length; eventIndex++) {
                int eventPrice = scanner.nextInt();
                events[eventIndex] = eventPrice;

                eventsMap.put(eventPrice, eventIndex);
            }

            // Second pass to find events
            for(int eventIndex = 0; eventIndex < events.length; eventIndex++) {
                int otherEventPrice = juniorMoney - events[eventIndex];

                if (eventsMap.containsKey(otherEventPrice)) {
                    int eventIndex1 = eventIndex + 1;
                    int eventIndex2 = eventsMap.get(otherEventPrice) + 1;

                    if (eventIndex1 == eventIndex2) {
                        continue;
                    }

                    System.out.println(eventIndex1 + " " + eventIndex2);
                    break;
                }
            }
        }
    }

}
