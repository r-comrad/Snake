import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JPanel;

public class GameField extends JPanel implements ActionListener {
    private Apple mApple;
    private Snake mSnake;
    private Walls mWalls;

    private SnakeRender mSnakeRender;
    private AppleRender mAppleRender;

    private boolean mInGame = true;

    private Timer mTimer;
    private Map<Integer, Direction> encoding = new HashMap<>();

    private final int initialTimePeriod = 10;
    private final int mPoleSize = 18;

    public GameField() {
        setBackground(Color.blue);
        setFocusable(true);
        requestFocus();

        mSnake = new Snake(mPoleSize);
        mApple = new Apple(mPoleSize, mSnake.getCoordinates());

        mSnakeRender = new SnakeRender();
        mAppleRender = new AppleRender();
        mWalls = new Walls(mPoleSize);

        addKeyListener(new FieldKeyListener());

        encoding.put(KeyEvent.VK_LEFT, Direction.Left);
        encoding.put(KeyEvent.VK_RIGHT, Direction.Right);
        encoding.put(KeyEvent.VK_UP, Direction.Up);
        encoding.put(KeyEvent.VK_DOWN, Direction.Down);

        mTimer = new Timer(initialTimePeriod, this);
        mTimer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mInGame) {
            Graphics2D g2 = (Graphics2D) g;
            mAppleRender.draw(g2, mApple.getAppleCoordinates());
            mSnakeRender.draw(g2, mSnake.getCoordinates(), mSnake.getDirectionHistory());
            mWalls.draw(g2);
        } else {
            String str = "Game cacai!";
            g.setColor(Color.white);
            g.drawString(str, 180, 180);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mSnake.update(mTimer.getDelay());
        if (mSnake.isChanged() && mInGame) {
            if (mApple.isAte(mSnake.getCoordinates())) {
                mSnake.feed();
                mApple.createApple(mPoleSize, mSnake.getCoordinates());
            }
            if (mSnake.selfHarm(mPoleSize)) {
                mInGame = false;
            }
        }

        mApple.update(mTimer.getDelay());
        if (mApple.isChanged()) mAppleRender.update();

        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            Direction newDir = encoding.get(e.getKeyCode());
            mSnake.setDirection(newDir);
        }
    }
}