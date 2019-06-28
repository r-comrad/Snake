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
    private Render mRender;

    private Apple mApple;
    private Snake mSnake;

    private boolean mInGame = true;

    private Timer mTimer;
    private Map<Integer, Direction> encoding = new HashMap<>();

    private final int initialTimePeriod = 10;

    public GameField() {
        mRender = new Render();

        mSnake = new Snake(19);
        mApple = new Apple(19, mSnake.getCoordinates());

        setBackground(Color.blue);
        setFocusable(true);
        requestFocus();

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
            mRender.appleRender((Graphics2D) g, mApple.getAppleCoordinates());
            mRender.snakeRender((Graphics2D) g, mSnake.getCoordinates(), mSnake.getDirection());
        } else {
            String str = "Game cacai!";
            g.setColor(Color.white);
            g.drawString(str, 125, 125);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mSnake.update(mTimer.getDelay());

        mApple.update(mTimer.getDelay());
        if (mApple.isChanged()) mRender.updateAppleImg();

        if (mInGame) {
            if (mApple.isAte(mSnake.getCoordinates())) {
                mSnake.feed();
                mApple.createApple(19, mSnake.getCoordinates());
            }
            if (mSnake.selfHarm(19)) {
                mInGame = false;
            }
        }
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