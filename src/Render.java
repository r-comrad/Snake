import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Render {
    private final int DOT_SIZE = 16;
    private Image dot;
    private Image mSnakeHeadImage;

    private Image apple;

    private BufferedImage mBufferedImage;
    private Graphics2D mGraphics;

    public Render()
    {
        ImageIcon appleIcon = new ImageIcon("apple.png");
        apple = appleIcon.getImage();

        ImageIcon dotIcon = new ImageIcon("dot.png");
        dot = dotIcon.getImage();

        mSnakeHeadImage = new ImageIcon("res/head.png").getImage();

        mBufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        mGraphics = (Graphics2D) mBufferedImage.getGraphics();
        mGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void update()
    {
        mBufferedImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        mGraphics = (Graphics2D) mBufferedImage.getGraphics();
        mGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void appleRender(Graphics2D g, Point aAppleCoord)
    {
        g.drawImage(apple, aAppleCoord.x * DOT_SIZE, aAppleCoord.y * DOT_SIZE, null);
    }

    public void snakeRender(ArrayList<Point> aSnakeCoord)
    {
        snakeHeadRender(aSnakeCoord.get(0));
        snakeBodyRender(aSnakeCoord);
    }

    private void snakeBodyRender(ArrayList<Point> aSnakeCoord)
    {
        for (int i = 1; i < aSnakeCoord.size(); ++i) {
            mGraphics.drawImage(dot, aSnakeCoord.get(i).x * DOT_SIZE,  aSnakeCoord.get(i).y * DOT_SIZE, null);
        }

    }

    private void snakeHeadRender(Point aHeadCoord)
    {
        //Direction snakeDir = mSnake.getDirection();
        Direction snakeDir = Direction.Down;
        AffineTransform originTransform = mGraphics.getTransform();
        AffineTransform newTransform = (AffineTransform)(originTransform.clone());

        double angle = 0;
        if (snakeDir == Direction.Left) angle = 90;
        if (snakeDir == Direction.Right) angle = 180;
        if (snakeDir == Direction.Down) angle = -90;

        newTransform.rotate(Math.toRadians(angle)) ;
        mGraphics.setTransform(newTransform);
        mGraphics.drawImage(mSnakeHeadImage, aHeadCoord.x * DOT_SIZE,
                aHeadCoord.y * DOT_SIZE, null);
        mGraphics.setTransform(originTransform);
    }
}
