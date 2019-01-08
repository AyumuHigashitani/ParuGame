import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

public class MainPanel extends JPanel implements Runnable, MouseMotionListener, MouseListener {

    // パネルサイズ
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    //読み込みたいファイル名
    private String[] filenames = {"pointaNomal.gif", "pointa.gif","paru_end1.jpg","haikei.jpg"};

    //ゲーム用スレッド
    private Thread gameLoop;

    //ポインターイメージ
    private Image[] images;

    //ポインターの押した座標
    private int xPressed;
    private int yPressed;

    //ポインターの座標
    private int x;
    private int y;

    //ポインターの状態
    private boolean isNomal;

    //タイム計測
    private int no;
    private String finishTime;

    //時間によって行われる項目のための変数
    private int num;
    private int times1;
    private int times2;

    //的1，的2
    private Mato mato;
    private Mato mato2;

    //パルさん
    private Paru paru;


    public MainPanel() {
        // パネルの推奨サイズを設定、pack()するときに必要
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        loadImage(filenames);

        //的の移動までの時間初期値
        times1 = 200;
        times2 = 200;

        //パルさん読み込み+位置指定
        paru = new Paru(10,350);

        //的の初期
        mato = new Mato();
        mato2 = new Mato();

        //マウスモーションを扱えるように
        addMouseMotionListener(this);
        addMouseListener(this);

        //ゲーム用のスレッド
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    //マウスのカーソルを動かしたとき
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        repaint();
        isNomal = true;
    }

    //マウスをドラッグしたまま移動させたとき
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        repaint();
        isNomal = true;
    }

    @Override
    //マウスが押されたとき
    public void mousePressed(MouseEvent e) {
        xPressed = e.getX(); // マウスのX座標
        yPressed = e.getY(); // ラケットを移動
        paru.setCount(0);
        paru.setDir(paru.ATTACK);
        repaint();
    }

    @Override
    //マウスがクリックされたとき
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        isNomal = false;
        repaint();
    }

    @Override
    //マウスが離れたとき
    public void mouseReleased(MouseEvent e){
        x = e.getX();
        y = e.getY();
        repaint();
    }

    @Override
    //マウスがアプレット上に乗った
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    //マウスがアプレット上から離れたとき
    public void mouseExited(MouseEvent e) {

    }

    public void run() {
        while (true) {

            no += 1;

            repaint();

            //休止
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * gameLoop.start　→　run, repaint()　→　paintComponent　とくっついているので，ここで書けば，runの休止期間毎に呼び出される
     * @param g
     */

    public void paintComponent(Graphics g) {

        super.paintComponent(g); //いる？？

        //マス目
        for (int i = 0; i*100 < WIDTH;i++){
            g.drawLine(i*100,0,i*100,WIDTH);
        }

        for (int j = 0; j*100 < WIDTH;j++){
            g.drawLine(0,j*100,WIDTH,j*100);
        }

        //背景
        g.drawImage(images[3],0,0,null);

        //パルさん描画
        paru.drow(g);

        //まとを何回叩いたらゲームが終わるか
        if (num < 10) { //今はいいが，後々runメソッドに書く

            //時間
            String Time = "time" + no;
            g.drawString(Time,400,50);

            //的1
            if (times1 + 350 == no) {
                times1 = no;
                num++;
                mato = new Mato();
            } else if (mato.close(xPressed, yPressed)) {
                mato = new Mato();
                times1 = no;
                num++;
            }

            //的2
            if (num > 5) {
                if (times2 + 350 == no) {
                    times2 = no;
                    mato2 = new Mato();
                    num++;

                } else if (mato2.close(xPressed, yPressed)) {
                    mato2 = new Mato();
                    times2 = no;
                    num++;
                }
            }

            mato.drow(g);
            if (num > 5) {
                mato2.drow(g);
            }

            finishTime ="GAME FINISH!! YOUR TIME IS " + no;

        } else {
            g.drawString(finishTime,WIDTH/2,HEIGHT/2);
            g.drawImage(images[2],0,0,null);
        }

        //マウスのdrow
        if (isNomal) {
            g.drawImage(images[0], x - 8, y - 8, null);
        }else {
            g.drawImage(images[1],x - 8,y - 8,null);
        }

    }

    public void loadImage(String[] filenames) {

        images = new Image[filenames.length];

        //なんとか頑張ってみる・・・
        //getClass　＝　クラスをとる　　getRsource　＝　ソースファイルの位置はどこか
        for (int i = 0; i < filenames.length; i++) {
            ImageIcon iccon = new ImageIcon(getClass().getResource("image/" + filenames[i]));
            images[i] = iccon.getImage();
        }
    }
}
