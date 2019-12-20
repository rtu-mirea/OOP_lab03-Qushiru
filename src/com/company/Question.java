package com.company;

public class Question  {
    private String questionn = "";
    private String questionAnswers = "";
    public Question(String str, String answer){
        questionn = str;
        questionAnswers = answer;
    }
    public boolean isCorrect(String answer){
        return questionAnswers.compareTo(answer) == 0;
    }
    public String getQuestionn() {
        return questionn;
    }
}