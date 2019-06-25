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

public class GameField extends JPanel implements ActionListener /*implements Runnable*/ {
    private Render mRender;
    private Apple mApple;
    private Snake mSnake;
    private boolean inGame = true;
    //private Thread mThread;
    private Timer timer;

    public GameField() {
        //super(); // активируем консруктор родителя
        //setPreferredSize(new Dimension(400, 400));

        //setBackground((Color.black));

        mRender= new Render();
        mSnake = new Snake(19);
        mApple = new Apple(19, mSnake.getCoordinates());
        //mThread = new Thread(this);

        //mSnakeHeadImage = new ImageIcon("res/head.png").getImage();

        timer = new Timer(250,this);
        timer.start();
        //image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        //g2 = (Graphics2D) image.getGraphics();
        //this.setBackground(Color.blue);
        setBackground(Color.blue);
        /*g2.setColor(Color.black);
        g2.fillRect(100,100,50,50);*/
        setFocusable(true);
        requestFocus();
        //mThread.start();
    }

    public void draw() {
       // g2.setColor(Color.black);
        //g2.fillRect(100,100,50,50);
        //g2.drawImage(mSnakeHeadImage, 0, 0, null);

    }

    public void paintComponent(Graphics g)
    {
        //draw();
        super.paintComponent(g);
        //g.drawImage(mSnakeHeadImage, 0, 0, null);
        //this.setBackground(Color.blue);
        mRender.snakeRender( (Graphics2D) g, mSnake.getCoordinates());
        mRender.appleRender( (Graphics2D) g, mApple.getAppleCoordinates());
        //g.setColor(Color.black);
        //g.fillRect(100,100,50,50);
        //g.drawImage(image, 0, 0, null);
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
        repaint();
    }
}