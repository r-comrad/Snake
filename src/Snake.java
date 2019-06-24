import java.util.ArrayList;
import java.util.Random;

public class Snake {
    private ArrayList<Point> mCoordinates;
    private Direction mDirection;

    public Snake(int aPoleSize) {
        mDirection = Direction.values()[new Random().nextInt(4)];

        mCoordinates = new ArrayList<>();
        Point point = new Point(0, 0);
        point.x = new Random().nextInt(aPoleSize - 6) + 3;
        point.y = new Random().nextInt(aPoleSize - 6) + 3;
        mCoordinates.add(point);

        for (int i = 0; i < 2; ++i) {
            //point = new Point();
            point = new Point(mCoordinates.get(i));
            if (mDirection == Direction.Up || mDirection == Direction.Down) {
                point.y += ((mDirection == Direction.Up) ? 1 : -1);
            } else {
                point.x += ((mDirection == Direction.Left) ? 1 : -1);
            }
            mCoordinates.add(point);
        }
    }

    public void move() {
        for (int i = mCoordinates.size() - 1; i > 0; i--) {
            mCoordinates.set(i, new Point(mCoordinates.get(i - 1)));
        }

        Point point = mCoordinates.get(0);
        if (mDirection == Direction.Up || mDirection == Direction.Down) {
            point.y += ((mDirection == Direction.Up) ? -1 : 1);
        } else {
            point.x += ((mDirection == Direction.Left) ? -1 : 1);
        }
        //mCoordinates.set(0, point);
    }

    public ArrayList<Point> getCoordinates() {
        return mCoordinates;
    }

    public void feed() {
        mCoordinates.add(new Point());
    }

    public boolean selfHarm(int aPoleSize) {
        boolean result = false;
        for (int i = 3; i < mCoordinates.size(); ++i) {
            if (mCoordinates.get(0).x == mCoordinates.get(i).x &&
                    mCoordinates.get(0).y == mCoordinates.get(i).y) {
                result = true;
            }
        }

        if (mCoordinates.get(0).x > aPoleSize || mCoordinates.get(0).x < 0 ||
                mCoordinates.get(0).y > aPoleSize || mCoordinates.get(0).y < 0) {
            result = true;
        }
        return result;
    }

    public void setmDirection(Direction aNewDir) {
        if (!mDirection.isOppositeDirection(aNewDir)) mDirection = aNewDir;
    }

    public int getPoints()
    {
        return mCoordinates.size() - 3;
    }

    public Direction getDirection()
    {
        return mDirection;
    }
}