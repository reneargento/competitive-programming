package com.br.movile.code.challenge;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by rene.argento on 26/02/18.
 */
public class RapiddoProjects {

    private static class Project {
        int cost;
        int profit;

        Project(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int maxProjects = scanner.nextInt();
        int money = scanner.nextInt();
        int projects = scanner.nextInt();

        int[] costs = new int[projects];
        int[] profits = new int[projects];

        for (int projectId = 0; projectId < projects; projectId++) {
            costs[projectId] = scanner.nextInt();
        }

        for (int projectId = 0; projectId < projects; projectId++) {
            profits[projectId] = scanner.nextInt();
        }

        PriorityQueue<Project> costsHeap = new PriorityQueue<>(new Comparator<Project>() {
            @Override
            public int compare(Project project1, Project project2) {
                return project1.cost - project2.cost;
            }
        });

        PriorityQueue<Project> profitsHeap = new PriorityQueue<>(new Comparator<Project>() {
            @Override
            public int compare(Project project1, Project project2) {
                if (project1.profit > project2.profit) {
                    return -1;
                } else if (project1.profit < project2.profit) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        for (int projectId = 0; projectId < projects; projectId++) {
            costsHeap.add(new Project(costs[projectId], profits[projectId]));
        }

        for (int selectedProject = 0; selectedProject < maxProjects; selectedProject++) {
            while (!costsHeap.isEmpty() && costsHeap.peek().cost <= money) {
                profitsHeap.add(costsHeap.poll());
            }

            if (!profitsHeap.isEmpty()) {
                money += profitsHeap.poll().profit;
            }
        }

        System.out.println(money);
    }

}
