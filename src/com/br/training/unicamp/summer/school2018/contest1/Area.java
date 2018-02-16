package com.br.training.unicamp.summer.school2018.contest1;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by rene on 22/01/18.
 */
// http://codeforces.com/group/3qadGzUdR4/contest/101704/problem/A
public class Area {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int vertices = scanner.nextInt();

        Point[] points = new Point[vertices + 1];

        for(int vertex = 0; vertex < vertices; vertex++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points[vertex] = new Point(x, y);
        }

        points[vertices] = points[0];

        System.out.println(area(points));
    }

    private static double area(Point[] points) {
        double sum = 0.0;

        for (int i = 0; i < points.length - 1; i++) {
            sum = sum + (points[i].x * points[i + 1].y) - (points[i].y * points[i + 1].x);
        }
        return 0.5 * sum;
    }

}
