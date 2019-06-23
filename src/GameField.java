import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

enum Direction {
    Left, Up, Right, Down;

    public boolean isOppositeDirection(Direction other)
    {
        return Math.abs(this.ordinal() - other.ordinal()) == 2;
    }


    /*public void update(int aNum) {
        if (this.switchPossible(aNum)) {
            this.num = aNum;
        }
    }*/
}

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTES = 400;
    private Image dot;
    private Image apple;
    Apple mApple;
    Snake mSnake;
    private final int POLE_SIZE = 19;
    private Timer timer;
    private boolean inGame = true;
    //private Direction dir = Direction.Right;

    private Map<Integer, Direction> encoding  = new HashMap<>();
    private final int initialTimePeriod = 380;

    public GameField() {
        setBackground((Color.black));
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);



        encoding.put(KeyEvent.VK_LEFT, Direction.Left);
        encoding.put(KeyEvent.VK_RIGHT, Direction.Right);
        encoding.put(KeyEvent.VK_UP, Direction.Up);
        encoding.put(KeyEvent.VK_DOWN, Direction.Down);
    }

    public void initGame() {
        mSnake = new Snake(POLE_SIZE);
        mApple = new Apple(POLE_SIZE, mSnake.getCoordinates());
        timer = new Timer( initialTimePeriod, this);
        timer.start();
    }

    public void loadImages() {
        ImageIcon appleIcon = new ImageIcon("apple.png");
        apple = appleIcon.getImage();

        ImageIcon dotIcon = new ImageIcon("dot.png");
        dot = dotIcon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            Point appleCoord = mApple.getAppleCoordinates();
            ArrayList<Point> mSnakeCoord = mSnake.getCoordinates();

            g.drawImage(apple, appleCoord.x * DOT_SIZE, appleCoord.y * DOT_SIZE, this);
            for (int i = 0; i < mSnakeCoord.size(); ++i) {
                g.drawImage(dot, mSnakeCoord.get(i).x * DOT_SIZE,  mSnakeCoord.get(i).y * DOT_SIZE, this);
            }
        } else {
            String str = "Game cacai!";
            //Font font = new Font("Areal", 14, Font.BOLD);
            g.setColor(Color.white);
            //g.setFont(font);
            g.drawString(str, 125, SIZE / 2);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            if (mApple.isAte(mSnake.getCoordinates()))
            {
                mSnake.feed();
                mApple.createApple(POLE_SIZE, mSnake.getCoordinates());
                timer.setDelay((int) (initialTimePeriod / (1. + mSnake.getPoints() / 7.5)));
            }
            if (mSnake.selfHarm(SIZE)) inGame = false;
            mSnake.move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            Direction newDir = encoding.get(e.getKeyCode());
            mSnake.setmDirection(newDir);
            /*if (key == KeyEvent.VK_LEFT && dir != Direction.Right) {
                dir = Direction.Left;
            }
            if (key == KeyEvent.VK_RIGHT && dir != Direction.Left) {
                dir = Direction.Right;
            }
            if (key == KeyEvent.VK_UP && dir != Direction.Down) {
                dir = Direction.Up;
            }
            if (key == KeyEvent.VK_DOWN && dir != Direction.Up) {
                dir = Direction.Down;
            }*/
        }
    }
}