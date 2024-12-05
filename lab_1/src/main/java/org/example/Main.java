package org.example;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a1 = sc.nextInt();
        int a2 = sc.nextInt();
        int b1 = sc.nextInt();
        int b2 = sc.nextInt();

        int minA = Math.min(a1, a2);
        int maxA = Math.max(a1, a2);
        int minB = Math.min(b1, b2);
        int maxB = Math.max(b1, b2);

        if ((minA < minB && minB < maxA && maxA < maxB) ||
                (minB < minA && minA < maxB && maxB < maxA)) {
            System.out.println("Резинки пересекаются.");
        } else {
            System.out.println("Резинки не пересекаются.");
        }
    }
}