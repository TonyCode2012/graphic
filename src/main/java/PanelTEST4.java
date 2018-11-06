import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.Thread.sleep;

class MyPanel3 extends JPanel
{
    private int x = 200;
    private int y = 200;

    private int frame_g = 0;
    private int preStep = 0;
    private int area = 490;

    private int imgHeight;
    private int imgWidth;

    private HashMap<Point,Boolean> randsMap = new HashMap<Point,Boolean>();
    private ArrayList<Point> randsArry = new ArrayList<>();

    private Graphics g;
    private BufferedImage im ;
    private int rgbGap = 100000;
    private HashMap<Integer,ArrayList<Integer>> typeMap = new HashMap<>();
    private HashMap<Integer,HashMap<Integer,ArrayList<Point>>> typeMap_g = new HashMap<>();
    private ArrayList<Integer> painted_arry_g = new ArrayList<>();
    private ArrayList<Integer> unpainted_arry_g = new ArrayList<>();
    private int loop_g;

    private void preprocessImg(BufferedImage im) {
        for(int i=0;i<imgWidth;i++) {
            for(int j=0;j<imgHeight;j++){
                int rgb = im.getRGB(i, j);
                if(rgb == 0) continue;
                boolean find = false;
                for(Map.Entry<Integer,HashMap<Integer,ArrayList<Point>>> rgbEntry: typeMap_g.entrySet()) {
                    int rgbC = rgbEntry.getKey();
                    if(rgbC + rgbGap > rgb && rgbC - rgbGap < rgb) {
                        HashMap<Integer,ArrayList<Point>> tmpMap = rgbEntry.getValue();
                        if(tmpMap.containsKey(rgb)) {
                            tmpMap.get(rgb).add(new Point(i,j));
                        } else {
                            ArrayList<Point> tmpArry =  new ArrayList<>();
                            tmpArry.add(new Point(i,j));
                            tmpMap.put(rgb,tmpArry);
                        }
                        find = true;
                        break;
                    }
                }
                if(!find) {
                    HashMap<Integer,ArrayList<Point>> tmpMap = new HashMap<>();
                    ArrayList<Point> tmpArry = new ArrayList<>();
                    tmpArry.add(new Point(i,j));
                    tmpMap.put(rgb,tmpArry);
                    typeMap_g.put(rgb,tmpMap);
                    unpainted_arry_g.add(rgb);
                }
            }
        }
        System.out.println("type number:"+typeMap_g.size());
        this.loop_g = typeMap_g.size();
    }

    //构造方法，获得外部Image对像的引用
    public MyPanel3(BufferedImage im)
    {
        if(im != null)
        {
            this.im = im;
            imgHeight = im.getHeight();
            imgWidth = im.getWidth();
            g = im.getGraphics();
            preprocessImg(im);
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

        /*
        if(frame_g < 400*400) {
            Random r = new Random();
            for (int i = 0; i < 20; i++) {
                while (true) {
                    int x = r.nextInt(400);
                    int y = r.nextInt(400);
                    Point p = new Point(x, y);
                    if (!randsMap.containsKey(p)) {
                        randsMap.put(p, true);
                        break;
                    }
                }
            }
            randsMap.keySet().forEach(p -> {
                int x = p.x;
                int y = p.y;
                int rgb = im.getRGB(x, y);
                if (rgb != 0) {
                    g.setColor(new Color(rgb));
                    g.drawLine(x, y, x, y);
                }
            });
            frame_g += 20;
        } else {
            g.drawImage(im,0,0,this);
        }
        */

        /*
        if(frame_g < imgWidth) {
            for(int i=0;i<frame_g;i++) {
                for (int j = 0; j < imgHeight; j++) {
                    int rgb = im.getRGB(i, j);
                    if (rgb == 0) continue;
                    g.setColor(new Color(rgb));
                    g.drawLine(i, j, i, j);
                }
            }
            frame_g++;
        } else {
            g.drawImage(im,0,0,this);
        }
        */
        
        if(frame_g < loop_g) {
            if(unpainted_arry_g.size() != 0) {
                painted_arry_g.add(unpainted_arry_g.get(0));
                unpainted_arry_g.remove(0);
            }
            for(int paintedType:painted_arry_g) {
                HashMap<Integer,ArrayList<Point>> rgb2PointArry = typeMap_g.get(paintedType);
                for(Map.Entry<Integer,ArrayList<Point>> rgbEntry: rgb2PointArry.entrySet()) {
                    int rgb = rgbEntry.getKey();
//                    System.out.println("rgb:"+rgb);
                    g.setColor(new Color(rgb));
                    for(Point point: rgbEntry.getValue()) {
                        int x = point.x;
                        int y = point.y;
                        g.drawLine(x,y,x,y);
                    }
                }
            }
            frame_g++;
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

            jf.setBounds(200, 200, 600, 600);
            jp.setSize(600, 600);
            jf.add(jp);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setVisible(true);

//            jp.display();

            int acc = 10000;
            while (acc-- > 0) {
                jp.display();

                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
