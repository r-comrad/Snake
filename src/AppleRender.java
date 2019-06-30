import javax.swing.*;
import java.awt.*;

public class AppleRender extends Render {
    private Image mAppleOnImage, mAppleOffImage;
    private boolean mAppleState;

    public AppleRender() {
        ImageIcon appleOnIcon = new ImageIcon("res/apple_on.png");
        mAppleOnImage = appleOnIcon.getImage();

        ImageIcon appleOffIcon = new ImageIcon("res/apple_off.png");
        mAppleOffImage = appleOffIcon.getImage();

        mAppleState = false;
    }

    public void update() {
        mAppleState = !mAppleState;
    }

    public void draw(Graphics2D g, Point aAppleCoord) {
        if (mAppleState) {
            g.drawImage(mAppleOnImage, aAppleCoord.x * DOT_SIZE + mCoordinatesOffset,
                    aAppleCoord.y * DOT_SIZE + mCoordinatesOffset, null);
        } else {
            g.drawImage(mAppleOffImage, aAppleCoord.x * DOT_SIZE + mCoordinatesOffset,
                    aAppleCoord.y * DOT_SIZE + mCoordinatesOffset, null);
        }
    }
}