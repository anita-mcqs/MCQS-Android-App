package com.mcqs.anita.mcqs_android_version1;

/**
 * Created by david-MCQS on 07/09/2015.
 */
public class Question {

  //  private String[] optionsArray = new String[5];//5 options
   // private int correctAnswer;
 //   private String correctNode;
    private String background;
    private String question;
    private QuestionOptions[] questionOptions = new QuestionOptions[5];
    private String core;
    private String explanation;
  //  private int attemptId;
  //  private String questionId;

    public Question(){
        //default constructor
    }
    public Question(QuestionOptions[] questionOptions, String background, String question, String core, String explanation){
        this.questionOptions = questionOptions;
        this.background = background;
        this.question = question;
        this.core = core;
        this.explanation=explanation;
    }
    public QuestionOptions[] getQuestionOptions() {
        return questionOptions;
    }

    public void setQuestionOptions(QuestionOptions[] questionOptions) {
        this.questionOptions = questionOptions;
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
}
