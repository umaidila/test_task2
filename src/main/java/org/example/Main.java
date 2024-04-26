package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static class CirclePointsDrawer {

        private List<int[]> result;
        private final int dpi;

        public CirclePointsDrawer(int dpiParam) {
            this.dpi = dpiParam;
        }

        private int mmToPixels(int mm) {
            return (int) Math.round (mm / 25.4 * dpi);
        }

        public List<int[]> getPoints(int centerXmm, int centerYmm, int diameterMm) {
            result = new ArrayList<>();
            int radiusPixels = mmToPixels((diameterMm / 2));
            int centerXPixels = mmToPixels(centerXmm);
            int centerYPixels = mmToPixels(centerYmm);

            int x;
            int y = radiusPixels;
            // Bresenham's algorithm
            int delta = 3-2*y;
            for (x=0; x <= y; x++) {
               if (delta < 0) {
                   delta += 4 * x + 6;
               } else {
                   delta += 4 * (x - y) + 10;
                   y--;
               }
                addPointsSymmetry(centerXPixels, centerYPixels, x, y);
            }
            return result;
        }

        private void addPointsSymmetry(int centerX, int centerY, int x, int y) {
            if (x == 0) {
                result.add(new int[] {centerX, centerY + y});
                result.add(new int[] {centerX, centerY - y});
                result.add(new int[] {centerX + y, centerY});
                result.add(new int[] {centerX - y, centerY});
                return;
            }
            result.add(new int[] {centerX + x, centerY + y});
            result.add(new int[] {centerX - x, centerY + y});
            result.add(new int[] {centerX + x, centerY - y});
            result.add(new int[] {centerX - x, centerY - y});
            result.add(new int[] {centerX + y, centerY + x});
            result.add(new int[] {centerX - y, centerY + x});
            result.add(new int[] {centerX + y, centerY - x});
            result.add(new int[] {centerX - y, centerY - x});
        }
    }
    public static void main(String[] args) {
        CirclePointsDrawer circlePointsDrawer = new CirclePointsDrawer(100);
        List<int[]> points = circlePointsDrawer.getPoints(0, 0, 100);
        int breakCounter = 0;
        for (int[] point : points) {
            System.out.println("(" + point[0] + ", " + point[1] + ")");
            breakCounter++;
            if (breakCounter == 20) {
                break;
            }
        }
    }
}