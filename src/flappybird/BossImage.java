package flappybird;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BossImage
{
    private BufferedImage image;
    public double x, y;
    private long displayTime;
    private long startTime;
    public BossImage(double x, double y, BufferedImage image, long displayTime)
    {
        this.x= x;
        this.y= y;
        this.image= image;
        this.displayTime= displayTime;
        this.startTime= System.currentTimeMillis();
    }
    public void draw(Graphics g){
        if (isVisible()) {
            g.drawImage(image, (int)x, (int)y, null);
        }
    }
    public boolean isVisible(){
        return(System.currentTimeMillis()-startTime)<displayTime;
    }
}