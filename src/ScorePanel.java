import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel implements Runnable{

    private Thread thread;

    //終わったかどうかのフラグ
    private boolean isFinish;

    //イメージの保存
    private Image[] images;

    private String[]  filenames = {"paru_end1.jpg"};

    //不透明度
    float F;

    public ScorePanel(){
        //初期値設定
        this.F = 0.0f;

        loadImage(filenames);
        setOpaque(false) ;
        setPreferredSize(new Dimension(1200, 600));
        InVisible();

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
                    Thread.sleep(2500);
                }else {
                    Thread.sleep(100);
                }
            }catch (Exception e){

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

    public void loadImage(String[] filenames) {

        images = new Image[filenames.length];

        //なんとか頑張ってみる・・・
        //getClass　＝　クラスをとる　　getRsource　＝　ソースファイルの位置はどこか
        for (int i = 0; i < filenames.length; i++) {
            ImageIcon iccon = new ImageIcon(getClass().getResource("image/" + filenames[i]));
            images[i] = iccon.getImage();
        }
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
