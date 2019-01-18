import javax.swing.*;
import java.awt.*;

public class ScorePanel extends Panel {

    //終わったかどうかのフラグ
    private boolean isFinish;

    //読み込みファイル名
    private String[]  filenames = {"paru_end1.jpg"};

    public ScorePanel(){

        //パネルの大きさなどを設定
        setOpaque(false) ;
        setPreferredSize(new Dimension(getWIDTH(), getHEIGHT()));
        InVisible();

        //初期値設定
        this.F = 0.0f;

        //イメージのロード
        loadImage(filenames);

        //ゲーム用のスレッド
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {

            repaint();
            if (isFinish) {
                if (F < 0.95f) {
                    F += 0.05;
                } else {
                    F = 1.0f;
                }
            }

            try {
                if (F == 0.0f) {
                    Thread.sleep(2000);
                }else {
                    Thread.sleep(100);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // アルファ値
        AlphaComposite composite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, F);

        // アルファ値をセット（以後の描画は半透明になる）
        g2.setComposite(composite);

        g.drawImage(images[0],100,50,null);
    }

    public void InVisible(){
        setVisible(false);
        isFinish = false;
    }

    public void Visible(){
        setVisible(true);
        isFinish = true;
    }

}
