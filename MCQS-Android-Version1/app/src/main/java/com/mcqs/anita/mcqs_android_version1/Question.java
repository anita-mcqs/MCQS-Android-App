package com.mcqs.anita.mcqs_android_version1;

/**
 * Created by david-MCQS on 07/09/2015.
 */
public class Question {

    private String[] optionsArray = new String[5];//5 options
    private int correctAnswer;
    private String correctNode;
    private String background;
    private String question;



    private String core;
    private String explanation;
    private int attemptId;
    private String questionId;

    public Question(){
        //default constructor
    }
    public Question(String[] optionsArray, int correctAnswer, String correctNode, String background, String question, String core, String explanation, int attemptId, String questionId){
        this.optionsArray = optionsArray;
        this.correctAnswer = correctAnswer;
        this.correctNode = correctNode;
        this.background = background;
        this.question = question;
        this.core = core;
        this.explanation=explanation;
        this.attemptId = attemptId;
        this.questionId = questionId;
    }
    public String[] getOptionsArray() {
        return optionsArray;
    }

    public void setOptionsArray(String[] optionsArray) {
        this.optionsArray = optionsArray;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectNode() {
        return correctNode;
    }

    public void setCorrectNode(String correctNode) {
        this.correctNode = correctNode;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(int attemptId) {
        this.attemptId = attemptId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
