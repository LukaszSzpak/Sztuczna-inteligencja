public class Section {
    private static int detMatrix(int xx, int xy, int yx, int yy, int zx, int zy) {
        return (xx*yy + yx*zy + zx*xy - zx*yy - xx*zy - yx*xy);
    }

    public static boolean checkCrossing(int[] xPoints, int[] yPoints) {
        if ((detMatrix(xPoints[0], yPoints[0], xPoints[1], yPoints[1], xPoints[2], yPoints[2])) *
                (detMatrix(xPoints[0], yPoints[0], xPoints[1], yPoints[1], xPoints[3], yPoints[3])) >= 0)
            return false;
        return (detMatrix(xPoints[2], yPoints[2], xPoints[3], yPoints[3], xPoints[0], yPoints[0])) *
                (detMatrix(xPoints[2], yPoints[2], xPoints[3], yPoints[3], xPoints[1], yPoints[1])) < 0;
    }
}