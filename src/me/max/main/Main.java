package me.max.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import me.max.main.SQL.SQLQuery;
import me.max.main.calculations.Correct;
import me.max.main.calculations.SetupTable;
import me.max.main.data.Answers;
import me.max.main.data.Questions;
import me.max.main.windows.errorWindow;
import me.max.main.windows.helpWindow;

public class Main extends Application {

    private errorWindow errorWindow = new errorWindow();
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private Stage window;
    private Integer[] randomNumbers = new Integer[50];
    private String[] file_username = new String[1000];
    private Double[] file_usertotalscore = new Double[1000];
    private Double[] file_userbonus = new Double[1000];
    private Double[] file_userscore = new Double[1000];
    private int[] file_usercorrect = new int[1000];
    private int[] file_userincorrect = new int[1000];
    private int[] file_usertime = new int[1000];
    private String[] file_userdate = new String[1000];
    private int[] shuffledNumbers = new int[50];
    private String status = "No answer";
    private String userDate = "";
    private int i = 0;
    private double totalScore;
    private double bonusScore;
    private double noBonusScore;
    private int correctScore = 0;
    private int incorrectScore = 0;
    private int scoreCount = -1;
    private boolean error = false;
    private String errorStatus = "";
    private String startTime = "";
    private String endTime = "";
    private String userTime = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        generateQuestions();
        correctScore = 0;
        incorrectScore = 0;
        bonusScore = 0;
        i = 0;

        window = primaryStage;
        window.setTitle("QuizTime - Home");

        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(10, 10, 10, 10));

        VBox midMenu = new VBox();
        midMenu.setPadding(new Insets(10, 10, 10, 10));
        midMenu.setSpacing(20);

        HBox midMenu1 = new HBox();
        midMenu1.setPadding(new Insets(10, 10, 10, 10));
        midMenu1.setSpacing(10);

        VBox midMenu2 = new VBox();
        midMenu1.setPadding(new Insets(10, 10, 10, 10));
        midMenu1.setSpacing(20);

        HBox botMenu = new HBox();
        botMenu.setPadding(new Insets(10, 10, 10, 10));
        botMenu.setSpacing(10);

        Label updateLabel = new Label("Last Updated: 27/11/2017 9:24am");
        Button helpButton = new Button("Help - Please read - Click here.");

        // Welcome Label
        Label welcomeLabel = new Label("Welcome to QuizTime!");
        welcomeLabel.setFont(Font.font("Verdana Pro Semibold", 22));

        // Play Button
        Button playButton = new Button("Play");
        playButton.setFont(Font.font("Verdana Pro Semibold", 22));

        // Top Score Button
        Button topScoreButton = new Button("Top Scores");
        topScoreButton.setFont(Font.font("Verdana Pro Semibold", 22));

        // Quit Button
        Button quitButton = new Button("Quit");
        quitButton.setFont(Font.font("Verdana Pro Semibold", 22));

		/*
		 * Button debugButton = new Button("Debug"); debugButton.setOnAction(e
		 * -> { endQuiz(primaryStage); });
		 */

        Label featureLabel1 = new Label("Features:");
        featureLabel1.setFont(Font.font("Verdana Pro Semibold", FontWeight.BOLD, 22));

        Label featureLabel2 = new Label("\n[*] High scores table.\n" + "[*] Easy and simple to use.\n" + "[*] Includes hang-man.\n"
                + "[*] Keep track of time taken to complete.\n" + "[*] Keep track of correct and incorrect score.\n"
                + "[*] Save stats with custom username.\n" + "[*] Quicker to finish = more points + more comming soon.\n");

        featureLabel2.setFont(Font.font("Verdana Pro Semibold", 12));

        topMenu.getChildren().addAll(welcomeLabel);
        midMenu1.getChildren().addAll(playButton, topScoreButton, quitButton);
        midMenu2.getChildren().addAll(featureLabel1, featureLabel2);
        midMenu.getChildren().addAll(midMenu1, midMenu2);
        botMenu.getChildren().addAll(helpButton, updateLabel);

        topMenu.setAlignment(Pos.CENTER);
        midMenu.setAlignment(Pos.CENTER);
        midMenu1.setAlignment(Pos.CENTER);
        midMenu2.setAlignment(Pos.CENTER);
        botMenu.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();

        borderPane.setTop(topMenu);
        borderPane.setCenter(midMenu);
        borderPane.setBottom(botMenu);

        Scene homeScene = new Scene(borderPane, 815, 430);
        window.setScene(homeScene);
        window.show();

        playButton.setOnAction(e -> {
            beginQuiz(primaryStage);
            startTimer();
        });

        topScoreButton.setOnAction(e -> scoreDisplay(homeScene));

        quitButton.setOnAction(e -> window.close());

        helpButton.setOnAction(e -> {
            helpWindow helpWindow = new helpWindow();
            helpWindow.window();
        });

    }

    private void beginQuiz(Stage primaryStage) {

        Questions questionClass = new Questions();
        Answers answerClass = new Answers();
        window.setTitle("QuizTime - Question " + (i + 1) + " of 10");

        questionClass.listQuestions();
        answerClass.listAnswers();

        Button answerButton = new Button("Answer!");
        answerButton.setFont(Font.font("Verdana Pro Semibold", 17));
        Button quitButton = new Button("Quit to Home Screen.");
        quitButton.setFont(Font.font("Verdana Pro Semibold", 12));

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(20);
        layout.setHgap(10);

        Label questionLabel = new Label(questionClass.printQuestion(shuffledNumbers[i]));
        questionLabel.setFont(Font.font("Verdana Pro Semibold", 18));
        GridPane.setConstraints(questionLabel, 0, 0);

        CheckBox answerOne = new CheckBox(answerClass.printAnswer(shuffledNumbers[i], 0));
        answerOne.setFont(Font.font("Arial Unicode MS", 17));
        GridPane.setConstraints(answerOne, 0, 1);

        CheckBox answerTwo = new CheckBox(answerClass.printAnswer(shuffledNumbers[i], 1));
        answerTwo.setFont(Font.font("Arial Unicode MS", 17));
        GridPane.setConstraints(answerTwo, 0, 2);

        CheckBox answerThree = new CheckBox(answerClass.printAnswer(shuffledNumbers[i], 2));
        answerThree.setFont(Font.font("Arial Unicode MS", 17));
        GridPane.setConstraints(answerThree, 0, 3);

        CheckBox answerFour = new CheckBox(answerClass.printAnswer(shuffledNumbers[i], 3));
        answerFour.setFont(Font.font("Arial Unicode MS", 17));
        GridPane.setConstraints(answerFour, 0, 4);

        GridPane.setConstraints(answerButton, 0, 6);

        Pane spacePane = new Pane();
        GridPane.setConstraints(spacePane, 0, 5);

        layout.getChildren().addAll(spacePane, questionLabel, answerButton, answerOne, answerTwo, answerThree, answerFour);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(layout);

        Scene questionsScene = new Scene(borderPane, 815, 430);
        window.setScene(questionsScene);
        window.show();

        Label lastAnswerLabel = new Label();
        lastAnswerLabel.setText("Correct: " + correctScore + " | Incorrect: " + incorrectScore + " | Last Question: " + status + " | Chances: "
                + (6 - incorrectScore));
        lastAnswerLabel.setFont(Font.font("Verdana Pro Semibold", 12));

        // Bottom Area
        HBox bottomMenu = new HBox();
        bottomMenu.setPadding(new Insets(10, 10, 10, 10));
        bottomMenu.setSpacing(10);
        bottomMenu.setAlignment(Pos.CENTER);
        bottomMenu.getChildren().addAll(lastAnswerLabel, quitButton);

        borderPane.setBottom(bottomMenu);

        int question = i;
        answerButton.setOnAction(e -> {
            i++;
            handleOptions(answerOne, answerTwo, answerThree, answerFour, question, primaryStage);
        });

        quitButton.setOnAction(e -> {
            try {
                start(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        Label hangLabel = new Label();
        hangLabel.setFont(Font.font("Verdana Pro Semibold", 12));

        if (incorrectScore == 1) {
            hangLabel.setText("|\n|\n|\n|\n|\n|");
        }

        if (incorrectScore == 2) {
            hangLabel.setText("_________\n|\n|\n|\n|\n|\n|");
        }

        if (incorrectScore == 3) {
            hangLabel.setText("_________\n" + "|             |\n" + "|\n" + "|\n" + "|\n" + "|\n" + "|");
        }

        if (incorrectScore == 4) {
            hangLabel.setText("_________\n" + "|             |\n" + "|             0\n" + "|\n" + "|\n" + "|\n" + "|");
        }

        if (incorrectScore == 5) {
            hangLabel.setText("_________\n" + "|             |\n" + "|             0\n" + "|            /|\\\n" + "|\n" + "|\n" + "|");
        }

        if (incorrectScore == 6) {
            hangLabel.setText("_________\n" + "|             |\n" + "|             0\n" + "|            /|\\\n" + "|            / \\\n" + "|\n"
                    + "|  Last Chance!");
        }

        borderPane.setCenter(hangLabel);

    }

    private void handleOptions(CheckBox answerOne, CheckBox answerTwo, CheckBox answerThree, CheckBox answerFour, int i, Stage primaryStage) {

        Correct correctClass = new Correct();

        if (answerOne.isSelected() && answerTwo.isSelected()) {
            errorStatus = "twoOrMoreSelectedError";
            clearCheckBoxes(answerOne, answerTwo, answerThree, answerFour);
            errorWindow.generalErrors(errorStatus);
            return;
        } else if (answerOne.isSelected() && answerThree.isSelected()) {
            errorStatus = "twoOrMoreSelectedError";
            clearCheckBoxes(answerOne, answerTwo, answerThree, answerFour);

            errorWindow.generalErrors(errorStatus);
            return;
        } else if (answerOne.isSelected() && answerFour.isSelected()) {
            errorStatus = "twoOrMoreSelectedError";
            clearCheckBoxes(answerOne, answerTwo, answerThree, answerFour);
            errorWindow.generalErrors(errorStatus);
            return;
        } else if (answerTwo.isSelected() && answerThree.isSelected()) {
            errorStatus = "twoOrMoreSelectedError";
            clearCheckBoxes(answerOne, answerTwo, answerThree, answerFour);
            errorWindow.generalErrors(errorStatus);
            return;
        } else if (answerTwo.isSelected() && answerFour.isSelected()) {
            errorStatus = "twoOrMoreSelectedError";
            clearCheckBoxes(answerOne, answerTwo, answerThree, answerFour);
            errorWindow.generalErrors(errorStatus);
            return;
        } else if (answerThree.isSelected() && answerFour.isSelected()) {
            errorStatus = "twoOrMoreSelectedError";
            clearCheckBoxes(answerOne, answerTwo, answerThree, answerFour);
            errorWindow.generalErrors(errorStatus);
            return;
        } else if (answerOne.isSelected()) {
            correctClass.correctAnswer(shuffledNumbers[i], 1);
        } else if (answerTwo.isSelected()) {
            correctClass.correctAnswer(shuffledNumbers[i], 2);
        } else if (answerThree.isSelected()) {
            correctClass.correctAnswer(shuffledNumbers[i], 3);
        } else if (answerFour.isSelected()) {
            correctClass.correctAnswer(shuffledNumbers[i], 4);
        } else {
            errorStatus = "nothingSelectedError";
            errorWindow.generalErrors(errorStatus);
            return;
        }

        correctScore = correctScore + correctClass.getGood();
        incorrectScore = incorrectScore + correctClass.getBad();
        status = "";
        status = status + correctClass.getStatus();

        try {
            if (incorrectScore == 7) {
                window.close();
                lostHangMan(primaryStage);
            } else if (i < 9) {
                beginQuiz(primaryStage);
            } else {
                endTimer();
                sumTimer();
                sumScore();
                endQuiz(primaryStage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void endQuiz(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("QuizTime - End");

        // layout one

        VBox topMenu = new VBox();
        topMenu.setPadding(new Insets(10, 10, 10, 10));
        topMenu.setAlignment(Pos.CENTER);

        HBox midMenu = new HBox();
        midMenu.setPadding(new Insets(10, 10, 10, 10));
        midMenu.setSpacing(20);
        midMenu.setAlignment(Pos.CENTER);

        HBox botMenu = new HBox();
        botMenu.setPadding(new Insets(10, 10, 10, 10));
        botMenu.setAlignment(Pos.CENTER);

        Label label1 = new Label("Quiz finished!");
        label1.setFont(Font.font("Verdana Pro Semibold", 22));

        Label label2 = new Label("Enter name to save score(21 max).");
        label2.setFont(Font.font("Verdana Pro Semibold", 22));

        Label label3 = new Label("Correct: " + correctScore + " | Incorrect: " + incorrectScore + " | Time Taken: " + userTime + " seconds.");
        label3.setFont(Font.font("Verdana Pro Semibold", 12));

        TextField usernameField = new TextField();
        usernameField.setFont(Font.font(18));
        usernameField.setPromptText("username");

        Button saveButton = new Button("Save");
        saveButton.setFont(Font.font("Verdana Pro Semibold", 18));

        topMenu.getChildren().addAll(label1, label2);
        midMenu.getChildren().addAll(usernameField, saveButton);
        botMenu.getChildren().addAll(label3);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setCenter(midMenu);
        borderPane.setBottom(botMenu);

        Scene scene1 = new Scene(borderPane, 815, 430);
        window.setScene(scene1);
        window.show();

        saveButton.setOnAction(e -> {
            if (usernameField.getText().equalsIgnoreCase("")) {
                errorStatus = "nonameError";
                errorWindow.generalErrors(errorStatus);
            } else if (usernameField.getLength() > 21) {
                usernameField.clear();
                errorStatus = "usernameLengthError";
                errorWindow.generalErrors(errorStatus);
            } else {
                loadDate();
                try {
                    writeScore(usernameField, totalScore, userTime, userDate);
                    start(primaryStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

    }

    private void lostHangMan(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Lost");

        HBox layout = new HBox();
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("You've lost the hangman!  ");
        Button button = new Button("Continue");

        layout.getChildren().addAll(label, button);

        Scene scene = new Scene(layout, 300, 200);
        window.setScene(scene);
        window.show();

        button.setOnAction(e -> {
            window.close();
            try {
                start(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    private void generateQuestions() {
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = i;
        }
        Collections.shuffle(Arrays.asList(randomNumbers));
        for (int i = 0; i < randomNumbers.length; i++) {
            shuffledNumbers[i] = randomNumbers[i];
        }
    }

    private void writeScore(TextField name, Double score, String userTime, String userDate) {
        try (FileWriter fw = new FileWriter("quiztime_data.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(name.getText() + "\n" + Double.toString(score) + "\n" + bonusScore + "\n" + noBonusScore + "\n" + correctScore + "\n" + incorrectScore + "\n"
                    + userTime + "\n" + userDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLQuery sqlQuery = new SQLQuery();
        sqlQuery.main(name.getText(), score, bonusScore, noBonusScore, correctScore, incorrectScore,  userTime, userDate);

    }

    @SuppressWarnings("unchecked")
    private void scoreDisplay(Scene homeScene) {
        loadScores();
        if (error) {
            return;
        }
        window.setTitle("QuizTime - Scores");
        TableView<SetupTable> table;
        // Name column
        TableColumn<SetupTable, String> usernameColumn = new TableColumn<>("Name");
        usernameColumn.setMinWidth(150);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Score column
        TableColumn<SetupTable, Double> usertotalscoreColumn = new TableColumn<>("Total Score");
        usertotalscoreColumn.setMinWidth(75);
        usertotalscoreColumn.setCellValueFactory(new PropertyValueFactory<>("totalscore"));

        // Bonus column
        TableColumn<SetupTable, Double> userbonusColumn = new TableColumn<>("Bonus");
        userbonusColumn.setMinWidth(75);
        userbonusColumn.setCellValueFactory(new PropertyValueFactory<>("bonus"));

        // Score column
        TableColumn<SetupTable, Double> userscoreColumn = new TableColumn<>("Score");
        userscoreColumn.setMinWidth(75);
        userscoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Correct column
        TableColumn<SetupTable, Integer> usercorrectColumn = new TableColumn<>("Correct");
        usercorrectColumn.setMinWidth(75);
        usercorrectColumn.setCellValueFactory(new PropertyValueFactory<>("correct"));

        // Incorrect column
        TableColumn<SetupTable, Integer> userIncorrectColumn = new TableColumn<>("Incorrect");
        userIncorrectColumn.setMinWidth(75);
        userIncorrectColumn.setCellValueFactory(new PropertyValueFactory<>("incorrect"));

        // Time taken Column
        TableColumn<SetupTable, Integer> usertimeColumn = new TableColumn<>("Time (s)");
        usertimeColumn.setMinWidth(75);
        usertimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        // Date Column
        TableColumn<SetupTable, String> userdateColumn = new TableColumn<>("Date");
        userdateColumn.setMinWidth(75);
        userdateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        Button backButton = new Button("Back");

        table = new TableView<>();
        table.setItems(getUsername());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(usernameColumn, usertotalscoreColumn, userbonusColumn, userscoreColumn, usercorrectColumn, userIncorrectColumn, usertimeColumn,
                userdateColumn);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(backButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vBox);
        borderPane.setBottom(hBox);

        Scene scoreDisplayScene = new Scene(borderPane, 815, 430);
        window.setScene(scoreDisplayScene);

        backButton.setOnAction(e -> {
            window.setTitle("QuizTime - Home");
            window.setScene(homeScene);
        });

    }

    private ObservableList<SetupTable> getUsername() {
        int tableCount;
        ObservableList<SetupTable> username = FXCollections.observableArrayList();
        for (tableCount = 0; tableCount <= scoreCount; tableCount++) {
            username.add(new SetupTable(file_username[tableCount], file_usertotalscore[tableCount], file_userbonus[tableCount], file_userscore[tableCount],
                    file_usercorrect[tableCount], file_userincorrect[tableCount], file_usertime[tableCount], file_userdate[tableCount]));
        }
        return username;
    }

    private void loadScores() {
        File file = new File("quiztime_data.txt");
        scoreCount = -1;
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                scoreCount++;
                String uname = sc.nextLine();
                file_username[scoreCount] = uname;
                String utotalscore = sc.nextLine();
                file_usertotalscore[scoreCount] = Double.parseDouble(utotalscore);
                String ubonus = sc.nextLine();
                file_userbonus[scoreCount] = Double.parseDouble(ubonus);
                String uscore = sc.nextLine();
                file_userscore[scoreCount] = Double.parseDouble(uscore);
                String ucorr = sc.nextLine();
                file_usercorrect[scoreCount] = Integer.parseInt(ucorr);
                String uincorr = sc.nextLine();
                file_userincorrect[scoreCount] = Integer.parseInt(uincorr);
                String utime = sc.nextLine();
                file_usertime[scoreCount] = Integer.parseInt(utime);
                String udate = sc.nextLine();
                file_userdate[scoreCount] = udate;
            }
            sc.close();
        } catch (Exception e) {
            errorStatus = "fileError";
            errorWindow.generalErrors(errorStatus);
            error = true;
        }

    }

    private void loadDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        userDate = dateFormat.format(date);
    }

    private void startTimer() {
        Date time = new Date();
        startTime = timeFormat.format(time);
    }

    private void endTimer() {
        Date time = new Date();
        endTime = timeFormat.format(time);
    }

    private void sumTimer() {
        try {
            Date timeStarted = timeFormat.parse(startTime);
            Date timeEnded = timeFormat.parse(endTime);
            long difference = (timeEnded.getTime() - timeStarted.getTime()) / 1000;
            userTime = Long.toString(difference);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void clearCheckBoxes(CheckBox answerOne, CheckBox answerTwo, CheckBox answerThree, CheckBox answerFour) {
        answerOne.setSelected(false);
        answerTwo.setSelected(false);
        answerThree.setSelected(false);
        answerFour.setSelected(false);
    }

    private void sumScore() {
        DecimalFormat roundFormat = new DecimalFormat("0.00");
        int time = Integer.parseInt(userTime);
        totalScore = correctScore * 10;
        if (time <= 10) {
            totalScore = totalScore * 3;
        } else if (time >= 11 && time <= 15) {
            totalScore = totalScore * 2.0;
        } else if (time >= 16 && time <= 20) {
            totalScore = totalScore * 1.9;
        } else if (time >= 21 && time <= 25) {
            totalScore = totalScore * 1.8;
        } else if (time >= 26 && time <= 30) {
            totalScore = totalScore * 1.7;
        } else if (time >= 31 && time <= 35) {
            totalScore = totalScore * 1.6;
        } else if (time >= 36 && time <= 40) {
            totalScore = totalScore * 1.5;
        } else if (time >= 41 && time <= 45) {
            totalScore = totalScore * 1.4;
        } else if (time >= 46 && time <= 50) {
            totalScore = totalScore * 1.3;
        } else if (time >= 51 && time <= 55) {
            totalScore = totalScore * 1.2;
        } else {
            totalScore = correctScore * 10;
        }
        String score = roundFormat.format(totalScore);
        try {
            totalScore = Double.parseDouble(score);
            bonusScore = totalScore - (correctScore * 10);
            noBonusScore = totalScore - bonusScore;
        } catch (Exception e) {
            errorStatus = "sumScoreError";
            errorWindow errorWindow = new errorWindow();
            errorWindow.generalErrors(errorStatus);
        }
    }

}