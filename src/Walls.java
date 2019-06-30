import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Walls extends Render {
    private ArrayList<Point> mCoordinates;
    private Image mBlockImage;

    public Walls(int aPoleSize) {
        mCoordinates = new ArrayList<>();
        for (int i = 0; i < aPoleSize; ++i) {
            mCoordinates.add(new Point(i, -1));
            mCoordinates.add(new Point(i, aPoleSize));
            mCoordinates.add(new Point(-1, i));
            mCoordinates.add(new Point(aPoleSize, i));
        }
        mCoordinates.add(new Point(-1, -1));
        mCoordinates.add(new Point(-1, aPoleSize));
        mCoordinates.add(new Point(aPoleSize, -1));
        mCoordinates.add(new Point(aPoleSize, aPoleSize));

        ImageIcon blockIcon = new ImageIcon("res/block.png");
        mBlockImage = blockIcon.getImage();
    }

    public void draw(Graphics2D g) {
        for (Point i : mCoordinates)
        g.drawImage(mBlockImage, i.x * DOT_SIZE + mCoordinatesOffset,
                i.y * DOT_SIZE + mCoordinatesOffset, null);
    }
}