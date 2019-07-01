import java.util.ArrayList;
import java.util.Random;

enum Direction {
    Left, Up, Right, Down;

    public boolean isOppositeDirection(Direction other) {
        return Math.abs(this.ordinal() - other.ordinal()) == 2;
    }

    public boolean isHorizontal() {
        return this == Left || this == Right;
    }
}

public class Snake extends GameObject {
    private ArrayList<Point> mCoordinates;
    private ArrayList<Direction> mDirection;
    private Direction mNewDirection;

    public Snake(int aPoleSize) {
        super(380);


        Direction dir = Direction.values()[new Random().nextInt(4)];
        mDirection = new ArrayList<>();
        for (int i = 0; i < 3; ++i) mDirection.add(dir);
        mNewDirection = dir;

        mCoordinates = new ArrayList<>();
        Point point = new Point(0, 0);
        point.x = new Random().nextInt(aPoleSize - 6) + 3;
        point.y = new Random().nextInt(aPoleSize - 6) + 3;
        mCoordinates.add(point);

        for (int i = 0; i < 2; ++i) {
            point = new Point(mCoordinates.get(i));
            if (dir == Direction.Up || dir == Direction.Down) {
                point.y += ((dir == Direction.Up) ? 1 : -1);
            } else {
                point.x += ((dir == Direction.Left) ? 1 : -1);
            }
            mCoordinates.add(point);
        }
    }

    protected void move() {
        mDirection.set(0, mNewDirection);

        for (int i = mCoordinates.size() - 1; i > 0; i--) {
            mCoordinates.set(i, new Point(mCoordinates.get(i - 1)));
            mDirection.set(i, mDirection.get(i - 1));
        }

        Point point = mCoordinates.get(0);
        Direction dir = mDirection.get(0);
        if (dir == Direction.Up || dir == Direction.Down) {
            point.y += ((dir == Direction.Up) ? -1 : 1);
        } else {
            point.x += ((dir == Direction.Left) ? -1 : 1);
        }
    }

    public ArrayList<Point> getCoordinates() {
        return mCoordinates;
    }

    public void feed() {
        changePeriod(1. / (1. + (getPoints() / 10.)));
        mCoordinates.add(new Point());
        mDirection.add(mDirection.get(mDirection.size() - 1));
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
        Direction dir = mDirection.get(0);
        if (!dir.isOppositeDirection(aNewDir)) {
            mNewDirection = aNewDir;
        }
    }

    private int getPoints() {
        return mCoordinates.size() - 3;
    }

    public Direction getDirection() {
        return mDirection.get(0);
    }

    public ArrayList<Direction> getDirectionHistory() {
        return mDirection;
    }
}