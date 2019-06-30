import java.util.ArrayList;
import java.util.Random;

enum Direction {
    Left, Up, Right, Down;

    public boolean isOppositeDirection(Direction other) {
        return Math.abs(this.ordinal() - other.ordinal()) == 2;
    }
}

public class Snake extends GameObject {
    private ArrayList<Point> mCoordinates;
    private Direction mDirection;

    public Snake(int aPoleSize) {
        super(380);

        mDirection = Direction.values()[new Random().nextInt(4)];

        mCoordinates = new ArrayList<>();
        Point point = new Point(0, 0);
        point.x = new Random().nextInt(aPoleSize - 6) + 3;
        point.y = new Random().nextInt(aPoleSize - 6) + 3;
        mCoordinates.add(point);

        for (int i = 0; i < 2; ++i) {
            point = new Point(mCoordinates.get(i));
            if (mDirection == Direction.Up || mDirection == Direction.Down) {
                point.y += ((mDirection == Direction.Up) ? 1 : -1);
            } else {
                point.x += ((mDirection == Direction.Left) ? 1 : -1);
            }
            mCoordinates.add(point);
        }
    }

    protected void move() {
        for (int i = mCoordinates.size() - 1; i > 0; i--) {
            mCoordinates.set(i, new Point(mCoordinates.get(i - 1)));
        }

        Point point = mCoordinates.get(0);
        if (mDirection == Direction.Up || mDirection == Direction.Down) {
            point.y += ((mDirection == Direction.Up) ? -1 : 1);
        } else {
            point.x += ((mDirection == Direction.Left) ? -1 : 1);
        }
    }

    public ArrayList<Point> getCoordinates() {
        return mCoordinates;
    }

    public void feed() {
        changePeriod(1. / (1. + (getPoints() / 10.)));
        mCoordinates.add(new Point());
    }

    public boolean selfHarm(int aPoleSize) {
        boolean result = false;
        Point firstPoint = mCoordinates.get(0);

        for (int i = 1; i < mCoordinates.size(); ++i) {
            Point otherPoint = mCoordinates.get(i);
            if (firstPoint.x == otherPoint.x && firstPoint.y == otherPoint.y) {
                result = true;
            }
        }

        if (firstPoint.x >= aPoleSize || firstPoint.x < 0 ||
                firstPoint.y >= aPoleSize || firstPoint.y < 0) {
            result = true;
        }

        return result;
    }

    public void setDirection(Direction aNewDir) {
        if (!mDirection.isOppositeDirection(aNewDir)) mDirection = aNewDir;
    }

    private int getPoints() {
        return mCoordinates.size() - 3;
    }

    public Direction getDirection() {
        return mDirection;
    }
}