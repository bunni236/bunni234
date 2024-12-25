package flappybird;

import java.awt.*;
import java.awt.image.BufferedImage;
public class Attack
{
    private BufferedImage attackImage;
    public double x, y;
    public static final int WIDTH= 200;
    public static final int HEIGHT= 100;

    public Attack(double x, double y, BufferedImage attackImage)
    {
        this.x= x;
        this.y= y;
        this.attackImage= attackImage;
    }

    public void draw(Graphics g) 
    {
        g.drawImage(attackImage, (int)x, (int)y, WIDTH, HEIGHT, null);
    }

    public Rectangle getBounds() 
    {
        return new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
    }
}