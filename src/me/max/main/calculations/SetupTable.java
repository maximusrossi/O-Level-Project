package me.max.main.calculations;

@SuppressWarnings("unused")
public class SetupTable {

	private String name;
	private Double totalscore;
	private Double bonus;
	private Double score;
	private int correct;
	private int incorrect;
	private int time;
	private String date;

	public SetupTable() {
		this.name = "";
		this.totalscore = 0.0;
		this.bonus = 0.0;
		this.score = 0.0;
		this.correct = 0;
		this.incorrect = 0;
		this.time = 0;
		this.date = "";
	}

	public SetupTable(String name, Double totalscore, Double bonus, Double score, int correct, int incorrect, int time, String date) {
		this.name = name;
		this.totalscore = totalscore;
		this.bonus = bonus;
		this.score = score;
		this.correct = correct;
		this.incorrect = incorrect;
		this.time = time;
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void getName(String name) {
		this.name = name;
	}

	public Double getTotalscore() {
		return totalscore;
	}

	public void setTotalscore(Double totalscore) {
		this.totalscore = totalscore;
	}
	
	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		this.score = bonus;
	}
	
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public int getCorrect() {
		return correct;
	}

	public void setCorrect(int correct) {
		this.correct = correct;
	}

	public int getIncorrect() {
		return incorrect;
	}

	public void setIncorrect(int incorrect) {
		this.incorrect = incorrect;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
