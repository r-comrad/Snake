import javax.swing.*;



public class MainWindow extends JFrame
{
    public MainWindow()
    {
        setTitle("ХУЙ");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320, 345);
        setLocation(470, 125);
        //setLocationRelativeTo(null);
        add(new GameField());
        setVisible(true);
    }

   /* public static void main(String[] args) {
        GameField panel = new GameField();// создаём объект панель
        panel.setSize(400, 400);



        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLocation(150, 150);// имзменяем местоположение фрейма.
        startFrame.setContentPane(panel); // перенос в фрейм панели с GamePanel
        startFrame.setSize(400, 400);
        startFrame.pack();//размер фрейма как и размер его компонентов
        //panel.start();// заускаем поток панели
        startFrame.setVisible(true);
    }*/

    public static void main(String[] args)
    {
        MainWindow mw = new MainWindow();
    }
}
