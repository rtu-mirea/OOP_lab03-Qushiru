package com.company;

public class Student extends User {
    private int questionCount = 0;
    private int rightAnswers = 0;
    public Student(String name, String login, String password){
        setUser(name, login, password);
    }
    public void addQuestionCount(){
        questionCount++;
    }
    public void addRightAnswers() {
        rightAnswers++;
    }
    public int getQuestionCount(){
        return questionCount;
    }
    public int getRightAnswers(){
        return rightAnswers;
    }
}