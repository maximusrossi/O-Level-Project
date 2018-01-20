package me.max.main.SQL;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

/**
 * Created by Maximus on 28/11/2017.
 *
 * Thread class
 */
class Run extends Thread{
    //This class was made to group the SQL runnables together.
    // Placing SQL queries in runnables avoids the program from lagging as this code will be run on a seperate thread.

    //mysql

    String name;
    Double score;
    Double bonusScore;
    Double noBonusScore;
    int correctScore;
    int incorrectScore;
    String userTime;
    String userDate;

    @Override
    public void run(){

        String host  = "imnaraltd.uk.to";
        String port = "3306";
        String database = "javaprograms";
        String DBtable = "quiztime";
        String username = "studentweb";
        String password = "turtledove";

        Sql2o sql2o = new Sql2o("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        try (Connection connection = sql2o.open()){
            connection
                    .createQuery("insert into " + DBtable
                            + "(Name, TotalScore, Bonus, Score, Correct, Incorrect, Time, Date)"
                            + "values (:Name, :TotalScore, :Bonus, :Score, :Correct, :Incorrect, :Time, :Date)").addParameter("Name", name)
                    .addParameter("TotalScore", Double.toString(score)).addParameter("Bonus", Double.toString(bonusScore)).addParameter("Score", Double.toString(noBonusScore))
                    .addParameter("Correct", Integer.toString(correctScore)).addParameter("Incorrect", Integer.toString(incorrectScore))
                    .addParameter("Time", userTime).addParameter("Date", userDate).executeUpdate();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

public class SQLQuery{
    public void main(String name, Double score, Double bonusScore, Double noBonusScore, int correctScore, int incorrectScore, String userTime, String userDate){

        Run main = new Run();

        //Equating the public value to the local value passed on from the Main class.

        main.name = name;
        main.score = score;
        main.bonusScore = bonusScore;
        main.noBonusScore = noBonusScore;
        main.correctScore = correctScore;
        main.incorrectScore = incorrectScore;
        main.userTime = userTime;
        main.userDate = userDate;

        main.start();
    }
}
