package model;

import java.io.*;

/**
 * HighScore is for storing the player name and the total point they get. Also it create a file for storing history<br>
 * top five record, and handle everything related to the file.
 */
public class HighScore implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private int score;

    /**
     * HighScore constructor
     * @param name player name
     * @param score score player get
     */
    public HighScore(String name,int score){
        this.name = name;
        this.score = score;

    }

    /**
     * Set the score
     * @param score
     */
    public void setScore(int score){
        this.score = score;
    }
    /**
     * Get the score
     * @return score
     */
    public int getScore(){
        return score;
    }
    /**
     * Get the player name
     * @return player name
     */
    public String getName(){
        return name;
    }

    /**
     * Initialize the file this is to prevent when the file does not exist.
     */
    private static void initializeFile()
    {
        HighScore[] h={new HighScore("",0),new HighScore("",0),new HighScore("",0),
                new HighScore(" ",0),new HighScore(" ",0)
                };
        try
        {
            ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("HighScoreBoard.dat"));//the file created
            o.writeObject(h);//write in
            o.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Could not find the file");;}
        catch (IOException e) {e.printStackTrace();}
    }
    /**
     * Get the history five of the score record with player names
     * @return Highscore[]
     */
    public static HighScore[] getHighScores()
    {
        if (!new File("HighScoreBoard.dat").exists()) //check whether exist or not
            initializeFile();
        try
        {
            ObjectInputStream o=new ObjectInputStream(new FileInputStream("HighScoreBoard.dat"));
            HighScore[] h=(HighScore[]) o.readObject();//read

            return h;
        } catch (IOException e) {e.printStackTrace();}
        catch (ClassNotFoundException e) {e.printStackTrace();}

        return null;
    }

    /**
     * Add a new record in the history list when the score is higher than one of the history score.
     * @param highScore
     */
    //Adds a new HighScore to the file compare to the history five records to see whether update or not.
    public static void addHighScore(HighScore highScore)
    {
        HighScore[] highScores=getHighScores();//get the records
        for (int i=highScores.length-1; i>=0; i--)
        {
            if (highScores[i].compare(highScore)==1)
            {
                break;

            }
            else if (highScores[i].compare(highScore)==0 && i==0){ //check whether it's index zero
                highScores[i]=highScore;
                break;
            }
            else if(highScores[i].compare(highScore)==0 && highScores[i-1].compare(highScore)==1){
                //if the highscore bigger than the highscore in index i and smaller or equal to the high
                // score in index i-1 set the index i high score to this new high score.
                highScores[i]=highScore;
                break;
            }
        }
        try
        {
            ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("HighScoreBoard.dat"));
            o.writeObject(highScores);//update remain the same if no record is modified
            o.close();
        } catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
    }

    /**
     * compare the record with the score which gets from the current player
     * @param highScore
     */
    public int compare(HighScore highScore)
    {
        if (this.score>=highScore.score){
            return 1;
        }
        else {
            return 0;
        }

    }
}
