package me.max.main.SQL;

/**
 * Created by Maximus on 27/11/2017.
 *
 * SQL getters and setters to request a file
 */
@SuppressWarnings("unused")
public class SQLRequest {
    private String Name;
    private String Bonus;
    private String Score;
    private String Correct;
    private String Incorrect;
    private String Time;
    private String Date;


    public String getName() {
        return Name;
    }

    public void getName(String Name) { this.Name = Name; }

    public String getBonus() {
        return Bonus;
    }

    public void getBonus(String Bonus) { this.Bonus = Bonus; }

    public String getScore() {
        return Score;
    }

    public void getScore(String Score) {
        this.Score = Score;
    }

    public String getCorrect() {
        return Correct;
    }

    public void getCorrect(String Correct) {
        this.Correct = Correct;
    }

    public String getIncorrect() {
        return Incorrect;
    }

    public void getIncorrect(String Incorrect) {
        this.Incorrect = Incorrect;
    }

    public String getDate(){
        return Date;
    }

    public void getDate(String Date){
        this.Date = Date;
    }

    public String getTIme(){
        return Time;
    }

    public void getTime(String Time){
        this.Time = Time;
    }


}
