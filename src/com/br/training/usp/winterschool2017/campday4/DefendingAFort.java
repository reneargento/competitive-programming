package com.br.training.usp.winterschool2017.campday4;

import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Created by rene on 13/07/17.
 */
public class DefendingAFort {

    private static class Wall implements Comparable<Wall>{
        long attackers, killed, attackersThatCanBeStopped;

        Wall(long attackers, long killed) {
            this.attackers = attackers;
            this.killed = killed;

            if (attackers > killed) {
                attackersThatCanBeStopped = killed;
            } else {
                attackersThatCanBeStopped = attackers;
            }
        }

        @Override
        public int compareTo(Wall that) {
            if (this.attackersThatCanBeStopped > that.attackersThatCanBeStopped) {
                return -1;
            } else if (this.attackersThatCanBeStopped < that.attackersThatCanBeStopped) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int walls = scanner.nextInt();
        long defenders = scanner.nextLong();

        long totalAttackers = 0;

        PriorityQueue<Wall> priorityQueue = new PriorityQueue<>();

        for(int i = 0; i < walls; i++) {
            long attackers = scanner.nextLong();
            long killed = scanner.nextLong();

            totalAttackers += attackers;

            priorityQueue.offer(new Wall(attackers, killed));
        }

        while (priorityQueue.size() > 0) {
            Wall currentWall = priorityQueue.poll();

            long defendersUsed;
            long defendersRequired = currentWall.attackers / currentWall.killed;

            if (defendersRequired <= defenders) {
                defendersUsed = defendersRequired;
            } else {
                defendersUsed = defenders;
            }

            defenders -= defendersUsed;
            long attackersKilled = defendersUsed * currentWall.killed;
            if (attackersKilled > currentWall.attackers) {
                attackersKilled = currentWall.attackers;
            }

            totalAttackers -= attackersKilled;

            currentWall.attackers = currentWall.attackers - attackersKilled;
            currentWall.killed = currentWall.attackers;
            currentWall.attackersThatCanBeStopped = currentWall.killed;
            if (currentWall.killed > 0) {
                priorityQueue.offer(currentWall);
            }

            if (totalAttackers == 0 || defenders == 0) {
                break;
            }
        }

        System.out.println(totalAttackers);
    }
}
