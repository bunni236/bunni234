package flappybird;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Bird 
{
    public float x, y, vx, vy;
    private int width;
    private int height;
    public static final int RAD = 25;
    private Image img;
    private Clip jumpSound;
    private Clip hitSound;
    private Clip eatSound;
    private Clip bossSound;

    public void setSize(int width, int height)
    {
        this.width= width;
        this.height= height;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public Bird() {
        x= FlappyBird.WIDTH/ 4;
        y= FlappyBird.HEIGHT/ 3;
        width= 2* RAD;
        height= 2* RAD;
        try{
            img= ImageIO.read(new File("flappybird.png"));
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        try 
        {
            AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(new File("jump.wav"));
            jumpSound= AudioSystem.getClip();
            jumpSound.open(audioInputStream);
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        try 
        {
            AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(new File("hit.wav"));
            hitSound= AudioSystem.getClip();
            hitSound.open(audioInputStream);
        } catch (UnsupportedAudioFileException| IOException| LineUnavailableException e) {
            e.printStackTrace();
        }
        try 
        {
            AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(new File("eat.wav"));
            eatSound= AudioSystem.getClip();
            eatSound.open(audioInputStream);
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        try 
        {
            AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(new File("boss.wav"));
            bossSound= AudioSystem.getClip();
            bossSound.open(audioInputStream);
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void physics()
    {
        x+= vx;
        y+= vy;
        vy+= 0.5f;
    }

    public void update(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.drawImage(img, Math.round(x- (width/ 2)), Math.round(y- (height/ 2)), width, height, null);    
    }
    public void jump(){
        vy= -8;
        playJumpSound();
    }
    public void reset()
    {
        x= FlappyBird.WIDTH/ 4;
        y= FlappyBird.HEIGHT/ 3;
        width= 2 * RAD;
        height= 2 * RAD;
        vx =vy =0;
    }
    private void playJumpSound()
    {
        if(jumpSound.isRunning())
        {
           jumpSound.stop();
        }
        jumpSound.setFramePosition(0);
        jumpSound.start();
    }

    public void playHitSound()
    {
        if(hitSound.isRunning())
        {
            hitSound.stop();
        }
        hitSound.setFramePosition(0);
        hitSound.start();
    }
    public void playEatSound()
    {
        if(eatSound.isRunning())
        {
            eatSound.stop();
        }
        eatSound.setFramePosition(0);
        eatSound.start();
    }
    public void playBossSound()
    {
        if(bossSound.isRunning())
        {
            bossSound.stop();
        }
        bossSound.setFramePosition(0);
        bossSound.start();
    }
    @Override
    protected void finalize() throws Throwable
    {
        if(jumpSound != null)
        {
            jumpSound.close();
        }
        if(hitSound != null)
        {
            hitSound.close();
        }
        if(eatSound != null)
        {
            eatSound.close();
        }
        if(bossSound != null)
        {
            bossSound.close();
        }
    }
}