import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Apple {
    private Point mAppleCoord;

    public Apple(int aPoleSize, ArrayList<Point> aCoordinates)
    {
        mAppleCoord = new Point();
        createApple(aPoleSize, aCoordinates);
    }

    public void createApple(int aPoleSize, ArrayList<Point> aCoordinates) {
        boolean check = true;
        while (check) {
            mAppleCoord.x = new Random().nextInt(aPoleSize);
            mAppleCoord.y = new Random().nextInt(aPoleSize);

            check = false;
            for (int i = 0; i < aCoordinates.size(); ++i)
            {
                check |= mAppleCoord.x == aCoordinates.get(i).x && mAppleCoord.y == aCoordinates.get(i).y;
            }
        }
    }

    public boolean isAte(ArrayList<Point> aCoordinates) {
        return aCoordinates.get(0).x == mAppleCoord.x && aCoordinates.get(0).y == mAppleCoord.y;
    }

    public Point getAppleCoordinates()
    {
        return mAppleCoord;
    }
}