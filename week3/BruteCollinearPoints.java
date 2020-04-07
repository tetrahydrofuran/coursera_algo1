

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] lineSegments;
    private int segmentCount;

    public BruteCollinearPoints(Point[] points) {
        // region Setup
        // Null argument check
        if (points == null) {
            throw new IllegalArgumentException();
        }
        // Null check traversal
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        // Brute force double loop equality check
        for (int i = 0; i < points.length; i++) {
            for (int j = i; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        this.points = points;
        lineSegments = new LineSegment[1];  // Initialize with one
        // endregion

        // Calculation
        /* Mis-interpreted the original assignment and thought Points[] points would only be an array of four.
        // Check if lines are on the same segment by using the slopes
        // Then need to take the terminus to avoid overlaps
        double slopeOneTwo = points[0].slopeTo(points[1]);
        double slopeOneThree = points[0].slopeTo(points[2]);
        double slopeOneFour = points[0].slopeTo(points[3]);
        double slopeTwoThree = points[1].slopeTo(points[2]);
        double slopeTwoFour = points[1].slopeTo(points[3]);
        double ThreeFour = points[2].slopeTo(points[3]);
        */

        // Need to loop over each 4-combo to check slopes
        for (int one = 0; one < points.length - 3; one++) {
            for (int two = one + 1; two < points.length - 2; two++) {
                for (int three = two + 1; three < points.length - 1; three++) {
                    for (int four = three + 1; four < points.length; four++) {
                        // Only need to take the case where ALL four points are collinear; no 3-point segments
                        double slopeOneTwo = points[one].slopeTo(points[two]);
                        double slopeOneThree = points[one].slopeTo(points[three]);
                        double slopeOneFour = points[one].slopeTo(points[four]);
                        // Take absolute values to eliminate any directionality
                        slopeOneTwo = Math.abs(slopeOneTwo);
                        slopeOneThree = Math.abs(slopeOneThree);
                        slopeOneFour = Math.abs(slopeOneFour);

                        if (slopeOneTwo == slopeOneThree && slopeOneTwo == slopeOneFour) {
                            // Point[] line = new Point[] {points[one], points[two], points[three], points[four]};
                            // Arrays.sort(line, Point.slope)

                            // Doesn't look like we're supposed to use comparator, so sum over number of times
                            // A point is greatest, and take min/max to get first and last points
                            Point[] minMax = getMinMaxPoint(points[one], points[two], points[three], points[four]);
                            addSegment(new LineSegment(minMax[0], minMax[1]));
                        }


                    }
                }
            }
        }


    }

    private void addSegment(LineSegment line) {
        // Double size and copy contents if needed
        if (segmentCount > lineSegments.length) {
            LineSegment[] t = new LineSegment[2 * segmentCount];
            for (int i = 0; i < lineSegments.length; i++) {
                t[i] = lineSegments[i];
            }
            lineSegments = t;
        }

        // Increment and add
        lineSegments[segmentCount++] = line;
    }

    private Point[] getMinMaxPoint(Point one, Point two, Point three, Point four) {
        // Yeah this is kind of ugly, but it's the brute force method and I don't know enough Java to do this elegantly
        // so...
        // Calls the Point compare functions and sums up the greater-than counts to get the greatest and least points
        Point min;
        Point max;

        // Can't really think right now if every comparison is strictly necessary
        // Could also probably throw in early stopping condition if min/max already found
        int oneScore = isGreater(one, two) + isGreater(one, three) + isGreater(one, four);
        int twoScore = isGreater(two, three) + isGreater(two, four) + isGreater(two, one);
        int threeScore = isGreater(three, two) + isGreater(three, four) + isGreater(three, one);
        // int fourScore = isGreater(four, one) + isGreater(four, three) + isGreater(four, one);

        if (oneScore == 3) {
            max = one;
        } else if (twoScore == 3) {
            max = two;
        } else if (threeScore == 3) {
            max = three;
        } else {
            max = four;
        }

        if (oneScore == 3) {
            min = one;
        } else if (twoScore == 3) {
            min = two;
        } else if (threeScore == 3) {
            min = three;
        } else {
            min = four;
        }

        return new Point[]{min, max};
    }

    private int isGreater(Point one, Point two) {
        // I guess Java doesn't have a clip/clamp function?
        int x = one.compareTo(two);
        if (x > 0) {
            return x;
        } else {
            return 0;
        }
    }


    public int numberOfSegments() {
        return segmentCount;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }
}
