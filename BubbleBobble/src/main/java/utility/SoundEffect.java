package utility;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * SoundEffect handles the game's SFX.
 * Classes that want to use SFX will call the static variables in this enum and
 * play them via the play() method.
 */
public enum SoundEffect {
    FRUIT("sfx/fruit.wav"),
    DEATH("sfx/death.wav"),
    SHOOT("sfx/shoot.wav"),
    POP("sfx/pop.wav"),
    BUBBLED("sfx/bubbled.wav"),
    JUMP("sfx/jump.wav"),
    EXPLODE("sfx/explode.wav"),
    LAND("sfx/land.wav");


    /**
     * Volumn of the sound mute low medium and high
     */
    public enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    private Clip clip;
    private static boolean isMute = false;

    /**
     * SoundEffect constructor. Initialize the audio.
     * @param soundFileName
     */
    SoundEffect(String soundFileName) {
        try {
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }
    /**
     * play the sound effect
     */
    public void play() {
        // plays the sound effect
        if (volume!=Volume.MUTE) {

            if (clip.isRunning()) {
                clip.stop();

            }
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void setToLoop() {
        // sets whether or not the sound effect loops
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }
    /**
     * Set to loud
     */
    public void setToLoud() {
        // sets volume to be loud
        if (volume != Volume.MUTE) {
            volume = Volume.HIGH;
        }
    }
    /**
     * Set mute
     * @param  bool
     */
    public static void setMute(boolean bool){
        if (bool==true){
            isMute = true;
            volume = Volume.MUTE;

        }
        else if (bool==false){
            isMute = false;
            volume = Volume.HIGH;
        }

    }
    /**
     * Get isMute
     * @return isMute
     */
    public static boolean getIsMute(){return isMute;}

}
