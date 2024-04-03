package com.group25;


/**
 * Manages audio playback, including sound effects and background music 
 * Supports looping and the ability to load audio from specified file paths
 * 
 * @author Adam Murdock Gale, Sameer Mehboob Bugti, Saad Meshaal Koker, Jesse Titus, Fahmid Abdullah 
 * @version 1.2
 */
public class AudioPlayer{


    private boolean loop; //indicates if audio should loop
    private AudioThread audio; //reference to the Audio thread
    //private static AudioPlayer instance; // singleton instance

    // Define the sound effect paths as an enum
    public enum SoundEffect {
        CorrectAnswer,
        IncorrectAnswer,
        MenuMusic,
        Fluffingaduck,
        AdminUnlocked,
        LevelComplete,
        FinalVictory,
        LevelDefeat,
        Quit,
        Dragon,
        DragonDeath,
        Person,
        Lamb,
        LambDeath,
        Genie,
        GenieDeath,
        Ghost,
        GhostDeath,
        Sword,
        SwordDeath
    }

    /**
     * Constructs a new AudioPlayerSwing object
     * Made private to ensure singleton pattern usage
     * @param loop  Determines whether audio should be played in a loop
     */
    public AudioPlayer(boolean loop){

        this.loop = loop;
    }

    /**
     * Returns the singleton instance of AudioPlayer.
     * 
     * @param loop Determines whether audio should be played in a loop
     * @return The singleton instance of AudioPlayer
     */

    // public static AudioPlayer getInstance(boolean loop) {
    //     if (instance == null) {
    //         instance = new AudioPlayer(loop);
    //     }
    //     return instance;
    // }

    /**
     * Plays audio from the specified file path.
     *
     * @param soundEffectEnum soundEffectEnum The SoundEffect to be played.
     */
    public void play(SoundEffect soundEffectEnum) {

        String soundEffectFile = null;
        boolean newThreadNeeded = false;

        // load sound FX path dynamically with enum
        switch (soundEffectEnum) {
            case CorrectAnswer:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/bell.wav";
                newThreadNeeded = true;
                break;
            case IncorrectAnswer:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/wrong.wav";
                newThreadNeeded = true;
                break;
            case MenuMusic:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/menusong.wav";
                break;
            case Fluffingaduck:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/fluffingaduck.wav";
                break;
            case AdminUnlocked:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/anime-wow-sound-effect.wav";
                newThreadNeeded = true;
                break;
            case LevelComplete:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/level-complete.wav";
                newThreadNeeded = true;
                break;
            case LevelDefeat:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/level-defeat.wav";
                newThreadNeeded = true;
                break;
            case FinalVictory:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/final-victory-music.wav";
                newThreadNeeded = true;
                break;
            case Quit:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/Click.wav";
                break;
            case Dragon:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/Dragon.wav";
                newThreadNeeded = true;
                break;
            case DragonDeath:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/DragonDeathNoise.wav";
                newThreadNeeded = true;
                break;  
            case Person:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/Person.wav";
                newThreadNeeded = true;
                break; 
            case Lamb:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/Lamb.wav";
                newThreadNeeded = true;
                break; 
            case LambDeath:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/LambDeathNoise.wav";
                newThreadNeeded = true;
                break; 
            case Genie:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/Genie.wav";
                newThreadNeeded = true;
                break; 
            case GenieDeath:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/GenieDeathNoise.wav";
                newThreadNeeded = true;
                break; 
            case Ghost:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/Ghost.wav";
                newThreadNeeded = true;
                break; 
            case GhostDeath:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/GhostDeathNoise.wav";
                newThreadNeeded = true;
                break;
            case Sword:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/Sword.wav";
                newThreadNeeded = true;
                break; 
            case SwordDeath:
                soundEffectFile = "src/main/java/com/group25/GUI/assets/SwordDeathNoise.wav";
                newThreadNeeded = true;
                break;
            default:
                System.out.println("Sound effect file not set corretly");
                break;
        }
        
        if (getOperatingSystem().toLowerCase().contains("windows")){
            soundEffectFile.replaceAll("/", "\\\\");
            System.out.println(soundEffectFile);
        }
        // Load the sound effect file
        if (soundEffectFile == null) {
            // Handle the case when the sound effect file is not set
            System.out.println("Sound effect file not set.");
            return;
        }

        // if (newThreadNeeded) {
            this.audio = new AudioThread(soundEffectFile, loop);
        // }
        // else{
        //     this.audio = AudioThread.getInstance(soundEffectFile, loop);
        // }
        this.audio.run();
    }
    /**
     * Plays audio from the specified file path.
     *
     * @param filePath The absolute or relative path to the audio file.
     */
    public void play(String filePath) {

        if (filePath == null || filePath.isEmpty()) {
            System.out.println("the path cannot be empty or null");
        }

        this.audio = new AudioThread(filePath, loop);
        this.audio.run();
    }
    /*
     * gets OS
     */
    public String getOperatingSystem() {
        String os = System.getProperty("os.name");
        return os;
    }
    /*
     * Stops any currently playing audio
     */
    public void stop(){
        if (this.audio != null) { 
            this.audio.stopAudio();
        }
    }
}
