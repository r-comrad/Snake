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
}

public class GameField extends JPanel implements ActionListener {
    private Render mRender;
    private Apple mApple;
    private Snake mSnake;
    private boolean inGame = true;
    private Timer mTimer;
    private Map<Integer, Direction> encoding  = new HashMap<>();
    private final int initialTimePeriod = 10;

    public GameField() {
        mRender= new Render();
        mSnake = new Snake(19);
        mApple = new Apple(19, mSnake.getCoordinates());

        mTimer = new Timer(initialTimePeriod,this);
        mTimer.start();

        setBackground(Color.blue);
        setFocusable(true);
        requestFocus();

        addKeyListener(new FieldKeyListener());

        encoding.put(KeyEvent.VK_LEFT, Direction.Left);
        encoding.put(KeyEvent.VK_RIGHT, Direction.Right);
        encoding.put(KeyEvent.VK_UP, Direction.Up);
        encoding.put(KeyEvent.VK_DOWN, Direction.Down);
    }

    public void draw() {
       // g2.setColor(Color.black);
        //g2.fillRect(100,100,50,50);
        //g2.drawImage(mSnakeHeadImage, 0, 0, null);

    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (inGame)
        {//this.setBackground(Color.blue);
            mRender.appleRender((Graphics2D) g, mApple.getAppleCoordinates());
            mRender.snakeRender((Graphics2D) g, mSnake.getCoordinates(), mSnake.getDirection());
        }
                else
        {
            String str = "Game cacai!";
            //Font font = new Font("Areal", 14, Font.BOLD);
            g.setColor(Color.white);
            //g.setFont(font);
            g.drawString(str, 125, 125);
        }
    }

    /*@Override
    public void run() {
        while(true)
        {
            try {
                mThread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        mSnake.update(mTimer.getDelay());
        mApple.update(mTimer.getDelay());
        if (mApple.isUpdated()) mRender.updateAppleImg();

        if (inGame) {
            if (mApple.isAte(mSnake.getCoordinates()))
            {
                mSnake.feed();
                mApple.createApple(19, mSnake.getCoordinates());
                //timer.setDelay((int) (initialTimePeriod / (1. + mSnake.getPoints() / 7.5)));
            }
            if (mSnake.selfHarm(19)) inGame = false;
            //mSnake.move();
            draw();
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