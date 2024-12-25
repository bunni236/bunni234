package flappybird;
import javax.sound.sampled.*;
import java.io.File;
public class AudioManager
{
    private Clip currentMusicClip;
    public void playMusic(String filePath, boolean loop)
    {
        if (currentMusicClip!= null&& currentMusicClip.isRunning()){
            currentMusicClip.stop();
        }
        try{
            AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(new File(filePath));
            currentMusicClip= AudioSystem.getClip();
            currentMusicClip.open(audioInputStream);
            if(loop)
            {
                currentMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                currentMusicClip.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mute()
    {
        if(currentMusicClip != null&& currentMusicClip.isRunning())
        {
            currentMusicClip.stop();
        }
    }
    public void unmute()
    {
        if(currentMusicClip != null)
        {
            currentMusicClip.start();
        }
    }
}