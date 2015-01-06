package com.example.barrelhorse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.os.Environment;

/*
 * Written By: Gaurav Gandhi, Ashish Kalel, Vivek Parekh
 * Description: Class for file operations for saving score to the file.
 */

public class Score {
               
	/**
	 * @param args
	 */
	String firstName,score;
	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	static String FILENAME="scoredb.txt";
	
	public Score(String firstName,String score)
	{
		this.firstName=firstName;
		this.score=score;
	}
	public Score()
	{
		
	}
	// Method saves score
	
	/**
	 * @return the score
	 */
	
	//converts milli seconds to the mm:ss format
	public String getScoreTime() {
		
		int millis=Integer.parseInt(score);
		int seconds = (int) (millis / 1000);
		int minutes = seconds / 60;
		seconds = seconds % 60;
		millis = millis - minutes * 60 * 1000 - seconds * 1000;
	
	// If game is not started then timer will be 0:00:00
	String timer = minutes + ":" + seconds + ":" + millis;

		if (timer.length() > 8)
		timer = timer.substring(0, 8);
	
		return timer;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}
	public void saveScore()
	{
		BufferedWriter writer=null; 
		File sdCard = Environment.getExternalStorageDirectory();
		 File file=new File(sdCard, FILENAME);
		 try {
			 
			 if (!file.exists()) {
	                file.createNewFile();
	                System.out.println("File Created");
	            }
			 System.out.println(file.getAbsolutePath());
			 writer=new BufferedWriter(new FileWriter(file.getAbsoluteFile(),true));
			 writer.write(this.getWritableString());
			 writer.newLine();
			 writer.close();
			 sortScores();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    //This will return string to be written to the file based on  the score fields
    public String getWritableString() {
        String temp = "";
        temp += this.getFirstName() + "\t";
        temp += this.getScore()+"";
        return temp;
    }
    
    //This will return score class object from score string
    public static Score getScoreObject(String scoreInString) {
        Score c = new Score();
        String[] stringList = scoreInString.split("\t");
        try{
        c.firstName = stringList[0];
        c.score = stringList[1];
        }catch(Exception e){
        	
        	System.out.println("@@@@EXCEPTION"+stringList.length);
        }
        return c;
    }

    
    // This function will return scores in an ArrayList
    public static ArrayList<Score> getScores() { 
        ArrayList<Score> scoreBuffer = new ArrayList<Score>();
        File sdCard = Environment.getExternalStorageDirectory();
		File file=new File(sdCard, FILENAME);
		BufferedReader reader=null;
		
        try {
                reader= new BufferedReader(new FileReader(file.getAbsoluteFile()));
                String scoreInString;
                scoreInString = reader.readLine();
                while (scoreInString != null) {
                    scoreBuffer.add(getScoreObject(scoreInString));
                    scoreInString = reader.readLine();
                }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scoreBuffer;
    }
    //sort score as per time
    public static void sortScores() {
    ArrayList<Score> scoreBuffer = new ArrayList<Score>();
    scoreBuffer = getScores();
    int allscores[] = new int[scoreBuffer.size()];
    String allnames[] = new String[scoreBuffer.size()];
    int k=0;
    for (Score score : scoreBuffer) {
		System.out.println(score.getScore());
    	allscores[k]=Integer.parseInt(score.getScore());
		allnames[k]=score.getFirstName();
		k++;
	}
    
    for(int i=0;i<k-1;i++)
    {
    	for(int j=0;j<k-i-1;j++)
    	{
    		if(allscores[j]>allscores[j+1])
    		{
    			int temp=allscores[j];
    			String t=allnames[j];
    			
    			allscores[j]=allscores[j+1];
    			allnames[j]=allnames[j+1];
    			
    			allscores[j+1]=temp;
    			allnames[j+1]=t;
    					
    		}
    	}
    }
            
    BufferedWriter writer=null; 
	File sdCard = Environment.getExternalStorageDirectory();
	File file=new File(sdCard, FILENAME);
	
	try {
		writer=new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
		for (int i=0;i<k;i++) 
		{
			Score s=new Score(allnames[i], allscores[i]+"");
            writer.write(s.getWritableString());
            writer.newLine();
        }
        writer.close();
   }
	catch (IOException e) {
        e.printStackTrace();
    }
  }
}
