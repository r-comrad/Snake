import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Apple {
    private Point mAppleCoord;
    private int mTimeCounter = 0;
    private int mPeriod = 400;
    private boolean needAction = false;

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

    public void update(int adTime)
    {
        mTimeCounter += adTime;
        if (mTimeCounter >= mPeriod)
        {
            mTimeCounter %= mPeriod;
            needAction = true;
        }
    }

    public boolean isUpdated()
    {
        boolean result = needAction;
        needAction= false;
        return result;
    }

    public boolean isAte(ArrayList<Point> aCoordinates) {
        return aCoordinates.get(0).x == mAppleCoord.x && aCoordinates.get(0).y == mAppleCoord.y;
    }

    public Point getAppleCoordinates()
    {
        return mAppleCoord;
    }
}