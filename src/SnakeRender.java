import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class SnakeRender extends Render {
    private Image mSnakeBodyImage;
    private Image mSnakeBodyTurnedImage;
    private Image mSnakeHeadImage;
    private Image mSnakeTailImage;

    public SnakeRender() {
        ImageIcon snakeBodyIcon = new ImageIcon("res/snake_body.png");
        mSnakeBodyImage = snakeBodyIcon.getImage();

        ImageIcon snakeBodyTurnedIcon = new ImageIcon("res/snake_body_turned.png");
        mSnakeBodyTurnedImage = snakeBodyTurnedIcon.getImage();

        ImageIcon snakeHeadIcon = new ImageIcon("res/snake_head.png");
        mSnakeHeadImage = snakeHeadIcon.getImage();

        ImageIcon snakeTailIcon = new ImageIcon("res/snake_tail.png");
        mSnakeTailImage = snakeTailIcon.getImage();
    }

    public void draw(Graphics2D g, ArrayList<Point> aSnakeCoord, ArrayList<Direction> aDirectionHistory) {
        snakeBodyRender(g, aSnakeCoord, aDirectionHistory);
        snakeHeadRender(g, aSnakeCoord.get(0), aDirectionHistory.get((0)));
        snakeTailRender(g, aSnakeCoord.get(aSnakeCoord.size() - 1), aDirectionHistory.get(aSnakeCoord.size() - 1));
    }

    private void snakeBodyRender(Graphics2D g, ArrayList<Point> aSnakeCoord, ArrayList<Direction> aDirectionHistory) {
        for (int i = 1; i < aSnakeCoord.size() - 1; ++i) {
            if(aDirectionHistory.get(i).isHorizontal() != aDirectionHistory.get(i + 1).isHorizontal())
            {
                Direction newDir = aDirectionHistory.get(i);
                Direction oldDir = aDirectionHistory.get(i + 1);

                double angle = 0;
                if (oldDir == Direction.Left && newDir == Direction.Up) angle = -90;
                else if (oldDir == Direction.Left && newDir == Direction.Down) angle = 0;
                else if (oldDir == Direction.Right && newDir == Direction.Up) angle = 180;
                else if (oldDir == Direction.Right && newDir == Direction.Down) angle = 90;
                else if (oldDir == Direction.Up && newDir == Direction.Right) angle = 0;
                else if (oldDir == Direction.Up && newDir == Direction.Left) angle = 90;
                else if (oldDir == Direction.Down && newDir == Direction.Right) angle = -90;
                else if (oldDir == Direction.Down && newDir == Direction.Left) angle = 180;

                AffineTransform transform =
                        AffineTransform.getTranslateInstance
                                (aSnakeCoord.get(i).x * DOT_SIZE + mCoordinatesOffset,
                                        aSnakeCoord.get(i).y * DOT_SIZE + mCoordinatesOffset);
                transform.rotate(Math.toRadians(angle),
                        mSnakeBodyTurnedImage.getWidth(null) / 2,
                        mSnakeBodyTurnedImage.getHeight(null) / 2);

                g.drawImage(mSnakeBodyTurnedImage, transform, null);
            }
            else if(aDirectionHistory.get(i).isHorizontal())
            {
                g.drawImage(mSnakeBodyImage, aSnakeCoord.get(i).x * DOT_SIZE + mCoordinatesOffset,
                        aSnakeCoord.get(i).y * DOT_SIZE + mCoordinatesOffset, null);
            }
            else
            {
                AffineTransform transform =
                        AffineTransform.getTranslateInstance
                                (aSnakeCoord.get(i).x * DOT_SIZE + mCoordinatesOffset,
                                        aSnakeCoord.get(i).y * DOT_SIZE + mCoordinatesOffset);
                transform.rotate(Math.toRadians(90),
                        mSnakeBodyImage.getWidth(null) / 2,
                        mSnakeBodyImage.getHeight(null) / 2);

                g.drawImage(mSnakeBodyImage, transform, null);
            }
        }

        /*g.drawImage(mSnakeTailImage, aSnakeCoord.get(aSnakeCoord.size() - 1).x *
                        DOT_SIZE + mCoordinatesOffset, aSnakeCoord.get(aSnakeCoord.size() - 1).y *
                DOT_SIZE + mCoordinatesOffset, null);*/
    }

    private void snakeTailRender(Graphics2D g, Point aTailCoord, Direction aTailDirection) {
        double angle = -90;
        if (aTailDirection == Direction.Left) angle = 180;
        else if (aTailDirection == Direction.Right) angle = 0;
        else if (aTailDirection == Direction.Down) angle = 90;

        AffineTransform transform =
                AffineTransform.getTranslateInstance
                        (aTailCoord.x * DOT_SIZE + mCoordinatesOffset,
                                aTailCoord.y * DOT_SIZE + mCoordinatesOffset);
        transform.rotate(Math.toRadians(angle),
                mSnakeTailImage.getWidth(null) / 2,
                mSnakeTailImage.getHeight(null) / 2);

        g.drawImage(mSnakeTailImage, transform, null);
    }

    private void snakeHeadRender(Graphics2D g, Point aHeadCoord, Direction aSnakeDirection) {
        double angle = -90;
        if (aSnakeDirection == Direction.Left) angle = 180;
        else if (aSnakeDirection == Direction.Right) angle = 0;
        else if (aSnakeDirection == Direction.Down) angle = 90;

        AffineTransform transform =
                AffineTransform.getTranslateInstance
                        (aHeadCoord.x * DOT_SIZE + mCoordinatesOffset,
                                aHeadCoord.y * DOT_SIZE + mCoordinatesOffset);
        transform.rotate(Math.toRadians(angle),
                mSnakeHeadImage.getWidth(null) / 2,
                mSnakeHeadImage.getHeight(null) / 2);

        g.drawImage(mSnakeHeadImage, transform, null);
    }
}