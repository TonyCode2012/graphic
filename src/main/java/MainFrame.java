import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame {
    private JPanel MainPanel;

    private static String rootPath = System.getProperty("user.dir");

    public MainFrame() {
//        try {
//            int width = 330;
////            ImageIcon icon = setIconByWidth(1, width, rootPath.concat("/img/mickyMouse.png"));
//            BufferedImage im = null;
//            im = ImageIO.read(new File(rootPath.concat("/img/mickyMouse.png")));
//            GraphicLabel.setIcon(new ImageIcon(im));
//            System.out.println("x:"+GraphicLabel.getX()+"y:"+GraphicLabel.getY());
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }

    public static ImageIcon setIconByWidth(double ratio,double fWidth,String iconPath) {
        if(ratio > 1) {
            System.out.println("[ERROR] Can't set ratio more than 1!");
            ratio = 1;
        }
        ImageIcon icon = new ImageIcon(iconPath);
        double iWidth = icon.getIconWidth();
        double iHeight = icon.getIconHeight();
        double iRatio = iHeight / iWidth;
        int iconWidth = (int) (fWidth * ratio);
        int iconHeight = (int) (iconWidth * iRatio);
        icon.setImage(icon.getImage().getScaledInstance(iconWidth,iconHeight,Image.SCALE_DEFAULT));
        return icon;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainFrame");
        MainFrame mf = new MainFrame();
        frame.setContentPane(mf.MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(600, 200);
        frame.setPreferredSize(new Dimension(330, 480));
        frame.setLayout(new GridLayout(1,0));
        frame.pack();
        frame.setVisible(true);
//        JPanel jp = new JPanel();
//        jp.setLayout(new GridLayout(1,0));
        JLabel label = new JLabel();
        mf.MainPanel.add(label);
        try {
//            int width = 330;
//            ImageIcon icon = setIconByWidth(1, width, rootPath.concat("/img/mickyMouse.png"));
            BufferedImage im = ImageIO.read(new File(rootPath.concat("/img/mickyMouse.png")));
            label.setIcon(new ImageIcon(im));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//        mf.MainPanel.updateUI();
    }
}