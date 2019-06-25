import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Render {
    private final int DOT_SIZE = 16;
    private Image dot;
    private Image mSnakeHeadImage;

    private Image apple;

    private BufferedImage mBufferedImage;

    public Render()
    {
        ImageIcon appleIcon = new ImageIcon("apple.png");
        apple = appleIcon.getImage();

        ImageIcon dotIcon = new ImageIcon("dot.png");
        dot = dotIcon.getImage();

        mSnakeHeadImage = new ImageIcon("res/head.png").getImage();

        mBufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        //mGraphics = (Graphics2D) mBufferedImage.getGraphics();
        //mGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void update()
    {
        mBufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        //mGraphics = (Graphics2D) mBufferedImage.getGraphics();
        //mGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void appleRender(Graphics2D g, Point aAppleCoord)
    {
        g.drawImage(apple, aAppleCoord.x * DOT_SIZE, aAppleCoord.y * DOT_SIZE, null);
    }

    public void snakeRender(Graphics2D g, ArrayList<Point> aSnakeCoord, Direction aSnakeDirection)
    {
        snakeBodyRender(g, aSnakeCoord);
        snakeHeadRender(g, aSnakeCoord.get(0), aSnakeDirection);
    }

    private void snakeBodyRender(Graphics2D g, ArrayList<Point> aSnakeCoord)
    {
        for (int i = 1; i < aSnakeCoord.size(); ++i) {
            g.drawImage(dot, aSnakeCoord.get(i).x * DOT_SIZE,  aSnakeCoord.get(i).y * DOT_SIZE, null);
        }

    }

    private void snakeHeadRender(Graphics2D g, Point aHeadCoord, Direction aSnakeDirection)
    {
        double angle = -90;
        if (aSnakeDirection == Direction.Left)
        {
            angle = 180;
        }
        if (aSnakeDirection == Direction.Right)
        {
            angle = 0;
        }
        if (aSnakeDirection == Direction.Down) angle = 90;

        AffineTransform transform =
                AffineTransform.getTranslateInstance
                        (aHeadCoord.x * DOT_SIZE, aHeadCoord.y * DOT_SIZE);
        transform.rotate(Math.toRadians(angle),
                mSnakeHeadImage.getWidth(null)/2,
                mSnakeHeadImage.getHeight(null)/2);
        g.drawImage(mSnakeHeadImage, transform, null);

    }
}
