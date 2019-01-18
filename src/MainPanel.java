import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

public class MainPanel extends Panel implements MouseMotionListener, MouseListener {

    //読み込みたいファイル名
    private String[] filenames = {"pointaNomal.gif", "pointa.gif", "paru_end1.jpg", "haikei.jpg"};

    //ゲームが終わったかどうかの変数
    private boolean isFinish;

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

    //scorepanel
    ScorePanel scorePanel;

    //Threadのsleep秒
    private int sleepTime;

    //スタートできるかどうか？
    private boolean canStart;

    public MainPanel() {
        scorePanel = new ScorePanel();

        // パネルの推奨サイズを設定、pack()するときに必要
        setPreferredSize(new Dimension(getWIDTH(), getHEIGHT()));

        //初期値設定
        this.isFinish = false;
        this.canStart = false;
        this.F = 0.0f;
        this.sleepTime = 10;

        //イメージのロード
        loadImage(filenames);

        //的の移動までの時間初期値
        times1 = 200;
        times2 = 200;

        //パルさん読み込み+位置指定
        paru = new Paru(10, 350);

        //的の初期
        mato = new Mato();
        mato2 = new Mato();

        //マウスモーションを扱えるように
        addMouseMotionListener(this);
        addMouseListener(this);

        //ゲーム用のスレッド
        thread = new Thread(this);
        thread.start();
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
        canStart = true;
        xPressed = e.getX(); // マウスのX座標
        yPressed = e.getY(); // ラケットを移動
        paru.setDir(paru.ATTACK);
        paru.stop();
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
    public void mouseReleased(MouseEvent e) {
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

    /*
    終了した場合の処理
     */
    public void finish() {
        scorePanel.Visible();
    }

    public void run() {
        while (true) {
            if (canStart) {
                no += 1;
                if (num < 2) { //何回的を壊せば終了するか
                    if (times1 + 300 - num * 10 == no) { //的1つ目
                        times1 = no;
                        num++;
                        mato = new Mato();
                    } else if (mato.close(xPressed, yPressed)) {
                        mato = new Mato();
                        times1 = no;
                        num++;
                    }
                    if (num > 5) { //的2つ目は5個以上壊したら出現
                        if (times2 + 300 - num * 10 == no) {
                            times2 = no;
                            mato2 = new Mato();
                            num++;
                        } else if (mato2.close(xPressed, yPressed)) {
                            mato2 = new Mato();
                            times2 = no;
                            num++;
                        }
                    }
                    //finishTime ="GAME FINISH!! YOUR TIME IS " + no;
                } else {
                    isFinish = true;
                    sleepTime = 200;//フェードアウト時のみ描画を遅くするため
                    if (F < 0.6f) {
                        F += 0.05;
                    } else {
                        finish();
                    }
                }
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception e) {

                }
            }
            repaint();
        }
    }

    /**
     * gameLoop.start　→　run, repaint()　→　paintComponent　とくっついているので，ここで書けば，runの休止期間毎に呼び出される
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //いる？？
        g.drawImage(images[3], 0, 0, null); //背景
        paru.drow(g);//パルさん描画

        if (canStart) {
            //時間
            //String Time = "time" + no;
            //g.drawString(Time,400,50);

            if (!isFinish) {
                mato.drow(g);
                if (num > 5) {
                    mato2.drow(g);
                }
            } else {
                Graphics2D g2 = (Graphics2D) g;

                // アルファ値
                AlphaComposite composite = AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, F);

                // アルファ値をセット（以後の描画は半透明になる）
                g2.setComposite(composite);
                g.setColor(Color.gray);
                g.fillRect(0, 0, getWIDTH(), getHEIGHT());

            }
        }
        //finishTime ="GAME FINISH!! YOUR TIME IS " + no;

        //マウスのdrow
        if (isNomal) {
            g.drawImage(images[0], x - 8, y - 8, null);
        } else {
            g.drawImage(images[1], x - 8, y - 8, null);
        }
    }
}