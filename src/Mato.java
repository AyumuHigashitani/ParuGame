import java.awt.*;
import javax.swing.ImageIcon;

public class Mato {

    private int x,y,hanbun;
    private Image image;
    private Rectangle rectangle;

    public Mato() {
        x = (int) (Math.random() * 600) + 500;
        y = (int) (Math.random() * 500);
        this.hanbun = 64;

        loadImage("mato.gif");
    }

    public void drow(Graphics g){

        g.drawImage(image,x,y,hanbun,hanbun,null);
        //g.drawRect(x,y,hanbun,hanbun);

    }

    public void loadImage(String filename){

        //なんとか頑張ってみる・・・
        //getClass　＝　クラスをとる　　getRsource　＝　ソースファイルの位置はどこか
        ImageIcon icon = new ImageIcon(getClass().getResource("image/" + filename));
        image = icon.getImage();
    }

    public boolean close(int mouseX,int mouseY){
        if (Paru.getCount() == 3) {
            rectangle = new Rectangle(x, y, hanbun, hanbun);
            return rectangle.contains(mouseX, mouseY);
        }else {
            return false;
        }
    }
 }

