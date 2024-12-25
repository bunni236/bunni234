package flappybird;

import java.awt.*;
import java.awt.image.BufferedImage;
public class Candy
{
    private BufferedImage candyImage;
    public double x, y;
    public static final int WIDTH= 30;
    public static final int HEIGHT= 30;

    public Candy(double x, double y, BufferedImage candyImage)
    {
        this.x= x;
        this.y= y;
        this.candyImage= candyImage;
    }

    public void draw(Graphics g) 
    {
        g.drawImage(candyImage, (int)x, (int)y, WIDTH, HEIGHT, null);
    }

    public Rectangle getBounds() 
    {
        return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
    }
}