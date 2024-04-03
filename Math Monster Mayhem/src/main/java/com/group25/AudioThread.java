/**
 * The com.group25 package contains classes developed by Group 25.
 */
package com.group25;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * The AudioThread class provides a threaded mechanism for playing audio.
 * It supports both looping and non-looping playback of audio files.
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 */
public class AudioThread extends Thread {

	private static String audioPath = null;
    private static boolean loop;
    private static Clip clip;
    private static AudioThread instance;

    /**
     * Constructor to initialize an AudioThread audio player.
     *
     * @param filePath The path to the audio file to be played.
     * @param toLoop   A boolean flag indicating whether the audio should loop.
     */
	public AudioThread(String filePath, boolean toLoop){

		audioPath = filePath;
        loop = toLoop;
	}

    /**
     * Retrieves a singleton instance of AudioThread.
     *
     * @param filePath The path to the audio file to be played.
     * @param toLoop   A boolean flag indicating whether the audio should loop.
     * @return An instance of AudioThread.
     */
    public static AudioThread getInstance(String filePath, boolean toLoop) {
        if (clip != null) {
            clip.close();
            audioPath = filePath;
            loop = toLoop;
        }
        instance = new AudioThread(filePath, toLoop);
        return instance;
    }
    /**
     * The run method handles audio playback. It loads the audio file, creates a Clip,
     * starts playback (with looping if specified), and closes the Clip when it has finished.    
    */
	public void run() { //create run method

		try { //try required as AudioInputStream has unhandled exceptions

			File backgroundSound = new File(audioPath); //initialize sound file
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(backgroundSound); //access the AudioInputStream and get the input which is the file
			clip = AudioSystem.getClip(); //create a clip using the AudioSystem
            clip.open(audioInput); //open the clip
            

            if (!loop) {  //making sure clip doesn't loop unless specified
			    clip.loop(0);
            
                // //LineListener to wait for clip to stop playing before terminating the thread
                // clip.addLineListener(event -> {
                //     if (event.getType() == LineEvent.Type.STOP) {
                //         clip.close();  // Close the clip
                //         this.interrupt();
                //     }
                // });
            }
            else{
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

			clip.start(); //start the clip (no need to set clip.loop to loop because its set to Clip.LOOP_INDEFINETLY automatically)
            
		} catch(Exception e) {
			e.printStackTrace(); //made for troubleshooting INCASE
		}
	}
    /*
     * method to stop audio
     */
    public void stopAudio(){
        clip.close();
        this.interrupt();
    }
}