package jp.ac.uryukyu.ie.e185747;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;


public class ScorePanel extends Panel {

    //MainPanelが終わったかどうかのフラグ
    private boolean isFinish;

    //String型scoreの保存用
    private String[] stScore;

    //int型のscoreの保存用
    private int[] intScore;

    //読み込みファイル名
    private String[]  filenames = {"0.png","1.png","2.png","3.png","4.png","5.png","6.png","7.png","8.png","9.png","paru_end.png"};

    /*
    コンストラクタ
     */
    public ScorePanel(){

        //パネルの大きさなどを設定
        setOpaque(false) ;
        setPreferredSize(new Dimension(getWIDTH(), getHEIGHT()));
        InVisible();

        //初期値設定
        this.F = 0.0f;
        intScore = new int[5]; //5桁分だけ箱を用意

        //イメージのロード
        loadImage(filenames);

        //ゲーム用のスレッド
        thread = new Thread(this);
    }

    /*
    動く部分
     */
    public void run() {
        while (true) {

            repaint();

            if (F < 0.95f) {
                F += 0.05;
            } else {
                F = 1.0f;
            }
            try {
                Thread.sleep(150);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
    描画メソッド
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // アルファ値
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, F);
        g2.setComposite(composite);

        g.drawImage(images[10],0,0,null);

        //scoreの数字を画像で表示させる　好きなfontが見つからなかったので。。。
        int num = 0;
        for (int i : intScore){
            num++;
            g.drawImage(images[i],850 + num * 40,180,null);
        }
    }

    /*
    scoreをint型から，Str型[]に変え，さらにint[]型にする
    scoreの表示fontに気にいるのがなかったので，番号にあった数字の画像を表示させるための下準備
     */
    public void scoreChangeIntToString(int score){

        int num = 0;

        stScore = String.valueOf(score).split("");
        for (String i : stScore) {
            num++;
            intScore[num] = Integer.parseInt(i);
        }
    }

    /*
    ScorePanelを見えてない状態にする
     */
    public void InVisible(){
        setVisible(false);
        isFinish = false;
    }

    /*
    ScorePanelを見えるようにする
     */
    public void Visible(){

        if (!isFinish){ //１回だけここを実行させるため
            thread.start();
            setVisible(true);
            scoreChangeIntToString(MainPanel.score);
            isFinish = true;
        }
    }
}