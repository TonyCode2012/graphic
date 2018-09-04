import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Thread.sleep;

class MyPanel3 extends JPanel
{
    private int x = 200;
    private int y = 200;

    private int step = 0;
    private int preStep = 0;

    private HashMap<Point,Boolean> randsMap = new HashMap<Point,Boolean>();
    private ArrayList<Point> randsArry = new ArrayList<>();

    private Graphics g;
    private BufferedImage im ;

    //构造方法，获得外部Image对像的引用
    public MyPanel3(BufferedImage im)
    {
        if(im != null)
        {
            this.im = im;
            g = im.getGraphics();
        }
    }

    public void display()
    {
        x ++;
        y ++;
        if(g != null)
        {
//            调用的super.paint(g),让父类做一些事前的工作，如刷新屏幕
//            super.paint(g);
//            g.setColor(Color.RED);				//设置画图的颜色
//            g.fill3DRect(x, y, 100, 100, true);	//填充一个矩形
            //更新缓图
            this.repaint();
//            this.paint(g);
        }
    }

    /**
     * repaint方法会调用paint方法，并自动获得Graphics对像
     * 然后可以用该对像进行2D画图
     * 注：该方法是重写了JPanel的paint方法
     */
    public void paint(Graphics g)
    {

//        if(step < 400*400) {
//            Random r = new Random();
//            for (int i = 0; i < 20; i++) {
//                while (true) {
//                    int x = r.nextInt(400);
//                    int y = r.nextInt(400);
//                    Point p = new Point(x, y);
//                    if (!randsMap.containsKey(p)) {
//                        randsMap.put(p, true);
//                        break;
//                    }
//                }
//            }
//            randsMap.keySet().forEach(p -> {
//                int x = p.x;
//                int y = p.y;
//                int rgb = im.getRGB(x, y);
//                if (rgb != 0) {
//                    g.setColor(new Color(rgb));
//                    g.drawLine(x, y, x, y);
//                }
//            });
//            step += 20;
//        } else {
//            g.drawImage(im,0,0,this);
//        }

        if(step < 420) {
            for(int i=0;i<step;i++) {
                for (int j = 0; j < 420; j++) {
                    int rgb = im.getRGB(i, j);
                    if (rgb == 0) continue;
                    g.setColor(new Color(rgb));
                    g.drawLine(i, j, i, j);
                }
            }
            step++;
        } else {
            g.drawImage(im,0,0,this);
        }
    }
}

public class PanelTEST4
{
    public static void main(String[] args)
    {
        //在自定义Panel的外部定义一个Image绘图区
//        Image im = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);

        try {
            String rootPath = System.getProperty("user.dir");
            BufferedImage im = ImageIO.read(new File(rootPath.concat("/img/mickyMouse.png")));
            JFrame jf = new JFrame();
            //通过构造方法将缓冲缓冲区对像的引用传给自定义Panel
            MyPanel3 jp = new MyPanel3(im);

            jf.setBounds(200, 200, 500, 500);
            jp.setSize(300, 300);
            jf.add(jp);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setVisible(true);

//            jp.display();

            int acc = 420;
            while (acc-- > 0) {
                jp.display();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
