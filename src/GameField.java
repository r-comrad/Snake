import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
    private final int ALL_DOTES = 400;

    Apple mApple;
    Snake mSnake;
    Render mRender;
    private final int POLE_SIZE = 19;
    private Timer timer;
    private boolean inGame = true;
    private Graphics2D g;
    private BufferedImage image;

    private Map<Integer, Direction> encoding  = new HashMap<>();
    private final int initialTimePeriod = 380;

    public GameField() {
        super(); // активируем консруктор родителя
        setPreferredSize(new Dimension(400, 400));

        setBackground((Color.black));
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
        requestFocus();

        encoding.put(KeyEvent.VK_LEFT, Direction.Left);
        encoding.put(KeyEvent.VK_RIGHT, Direction.Right);
        encoding.put(KeyEvent.VK_UP, Direction.Up);
        encoding.put(KeyEvent.VK_DOWN, Direction.Down);

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//сглаживание соседних пиксеей

        Toolkit kit = Toolkit.getDefaultToolkit();// переназначение
        BufferedImage buffered = new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB);// панель для рисования 16х16
        Graphics2D g3 = (Graphics2D) buffered.getGraphics();//
        g3.setColor(new Color( 255,255,255));//
        g3.drawOval(0,0,16,16);//
        g3.drawLine(8,0,8,16);//
        g3.drawLine(0,8,16,8);//
        g3.dispose();
    }

    public void initGame() {
        mSnake = new Snake(POLE_SIZE);
        mApple = new Apple(POLE_SIZE, mSnake.getCoordinates());
        mRender = new Render();
        timer = new Timer( initialTimePeriod, this);
        timer.start();
    }

    public void loadImages() {
    }

    private void gameDraw() {
        setBackground((Color.black));
        Graphics g2 = this.getGraphics();// переоппред Graphics2d на Graphics
        g2.drawImage(image, 0, 0, null);// рисуем
        g2.dispose();// команда для уборщщика мусора

    }

    /*public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

    }*/

    public void draw() {
        //super.paint(g);
        /*AffineTransform at = AffineTransform.getRotateInstance(Math.PI / 6.0, 100, 100);
        at.concatenate(AffineTransform.getScaleInstance(1.0, 0.5));
        g.setTransform(at);
        g.setColor(Color.blue);
        //g2d.setFont(newFont("Serif", Font.ITALIC, 24));
        g.drawString("Графическиепримитивы", 100, 50);
        g.drawRect(75, 25, 300, 50);*/



        //super.paint(g);
        if (inGame) {
            image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            mRender.appleRender(g, mApple.getAppleCoordinates());
            //mRender.snakeRender(mSnake.getCoordinates());

            /*mRender.appleRender(mApple.getAppleCoordinates());
            mRender.snakeRender(mSnake.getCoordinates());*/

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
            draw();
            gameDraw();
        }
        //repaint();
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