import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTES];
    private int[] y = new int[ALL_DOTES];
    private int dots;
    private Timer timer;
    private boolean inGame = true;
    //private Direction dir = Direction.Right;
    private Direction dir = Direction.Right;
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
        dots = 3;
        for (int i = 0; i < dots; ++i) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }

        //timer = new Timer(((dots - 3)/5 + 1) * 250, this);
        timer = new Timer( initialTimePeriod, this);
        timer.start();
        createApple();
    }

    public void createApple() {
        boolean check = true;
        while (check) {
            appleX = new Random().nextInt(19) * DOT_SIZE;
            appleY = new Random().nextInt(19) * DOT_SIZE;

            check = false;
            for (int i = 0; i < dots; ++i) check |= appleX == x[i] && appleY == y[i];
        }
        ;

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
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; ++i) {
                g.drawImage(dot, x[i], y[i], this);
            }
        } else {
            String str = "Game cacai!";
            //Font font = new Font("Areal", 14, Font.BOLD);
            g.setColor(Color.white);
            //g.setFont(font);
            g.drawString(str, 125, SIZE / 2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (dir == Direction.Up || dir == Direction.Down) {
            y[0] += ((dir == Direction.Up) ? -1 : 1) * DOT_SIZE;
        } else {
            x[0] += ((dir == Direction.Left) ? -1 : 1) * DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
            timer.setDelay((int)((double)initialTimePeriod / ((dots - 3.) / 10. + 1.)));
            //timer = new Timer((int)(250. / ((dots - 3.) + 1.)), this);
        }
    }

    public void checkCollisions() {
        for (int i = 3; i < dots; ++i) {
            if (x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        if (x[0] > SIZE || x[0] < 0 || y[0] > SIZE || y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            Direction newDir = encoding.get(e.getKeyCode());
            if (!dir.isOppositeDirection(newDir)) dir = newDir;
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