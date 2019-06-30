import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class SnakeRender extends Render {
    private Image mSnakeBodyImage;
    private Image mSnakeHeadImage;

    public SnakeRender() {
        ImageIcon snakeBodyIcon = new ImageIcon("res/snake_body.png");
        mSnakeBodyImage = snakeBodyIcon.getImage();

        ImageIcon snakeHeadIcon = new ImageIcon("res/snake_head.png");
        mSnakeHeadImage = snakeHeadIcon.getImage();
    }

    public void draw(Graphics2D g, ArrayList<Point> aSnakeCoord, Direction aSnakeDirection) {
        snakeBodyRender(g, aSnakeCoord);
        snakeHeadRender(g, aSnakeCoord.get(0), aSnakeDirection);
    }

    private void snakeBodyRender(Graphics2D g, ArrayList<Point> aSnakeCoord) {
        for (int i = 1; i < aSnakeCoord.size(); ++i) {
            g.drawImage(mSnakeBodyImage, aSnakeCoord.get(i).x * DOT_SIZE + mCoordinatesOffset,
                    aSnakeCoord.get(i).y * DOT_SIZE + mCoordinatesOffset, null);
        }

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