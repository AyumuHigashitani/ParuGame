import javax.swing.*;
import java.awt.*;


    public class ParuGame extends JFrame {
        public ParuGame() {
            // タイトルを設定
            setTitle("パルさんの射撃練習");

            // メインパネルを作成してフレームに追加
            MainPanel panel = new MainPanel();
            Container contentPane = getContentPane();
            contentPane.add(panel);

            // パネルサイズに合わせてフレームサイズを自動設定
            pack();
        }

        public static void main(String[] args) {
            ParuGame frame = new ParuGame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
    }
