package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DistanceExaminator {
    private Admin admin = new Admin();
    private ArrayList<Student> students = new ArrayList<Student>();
    private ArrayList<Question> questions = new ArrayList<Question>();
    private int CurrentUser = -2;
    public int getCurrentUser(){
        return CurrentUser;
    }
    public void addUser(String name, String login, String password, String repeatPassword) throws Exception{
        if (password.compareTo(repeatPassword) != 0) throw new Exception("Пароли не совпадают");
        if (login.compareTo(admin.getLogin()) == 0) throw new Exception("Данный логин уде занят");
        if (students.isEmpty()){

            Student buf = new Student(name, login, password);
            students.add(buf);
        }
        else {
            for (Student student : students) {
                if (student.getLogin().compareTo(login) == 0) throw new Exception("Данный логин уже занят");
            }
            Student buf = new Student(name, login, password);
            students.add(buf);
        }
    }
    public void changePassword(String login, String password, String newPassword, String repeatNewPassword) throws Exception {
        if (newPassword.compareTo(repeatNewPassword) != 0) throw new Exception("Пароли не совпадают");
        if (admin.enter(login, password)) {
            admin.changePassword(newPassword);
            return;
        }
        if (students.isEmpty()) throw new Exception("В системе нет ни одного зарегистрированного пользователя");
        for (Student student: students){
            if (student.enter(login, password))
            {
                CurrentUser = students.indexOf(student);
                break;
            }
            else CurrentUser = -2;
        }
        if (CurrentUser == -2) throw new Exception("Логин или пароль были введены неверно");
        students.get(CurrentUser).changePassword(newPassword);
        CurrentUser = -2;
    }
    public void logging(String login, String password)throws Exception{
        if (admin.enter(login, password)) {
            CurrentUser = -1;
            return;
        }
        if (students.isEmpty()) throw new Exception("В системе нет ни одного зарегистрированного пользователя");
        for (Student student: students){
            if (student.enter(login, password))
            {
                CurrentUser = students.indexOf(student);
                break;
            }
            else CurrentUser = -2;
        }
        if (CurrentUser == -2) throw new Exception("Логин или пароль были введены неверно");
    }
    public void exit(){
        CurrentUser = -2;
    }
    public void addQuestion(String question, String answer) throws Exception{
        if (CurrentUser == -1) {
            questions.add(new Question(question, answer));
        }
        else throw new Exception("Вы не имеете парв на добавление вопросов");
    }
    public void examination()throws Exception{
        int questionIndex, arr[] = {-1,-1,-1,-1,-1};
        boolean flag;
        Random random = new Random();
        String str;
        Scanner in = new Scanner(System.in);
        if (CurrentUser == -1) throw new Exception("Вы не можете отвечать на вопросы");
        if (CurrentUser < 0) throw new Exception("Вы не вошли в систему");
        if (questions.size() < 5) throw new Exception("Преподаватель еще не добавил нужное количество вопросов");
        if (students.get(CurrentUser).getQuestionCount() != 0) throw new Exception("Вы уже проходили экзамен");
        for (int i = 0; i < 5; i++){
            flag = true;
            questionIndex = random.nextInt(questions.size());
            for (int n = 0; n < i; n++){
                if (arr[n] == questionIndex){
                    flag = false;
                    break;
                }
            }
            if (flag) arr[i] = questionIndex;
            else i--;
        }
        for (int i = 1; i <= 5; i++){
            System.out.println("Вопрос №" + i + " :\n" + questions.get(arr[i - 1]).getQuestionn() + "\nОтвет: ");
            str = in.nextLine();
            students.get(CurrentUser).addQuestionCount();
            if (questions.get(arr[i - 1]).isCorrect(str))
                students.get(CurrentUser).addRightAnswers();
        }
    }
    public int getMark()throws Exception{
        if (CurrentUser < 0) throw new Exception("Вы не вошли в систему или не имеете доступа к этому меню");
        if (students.get(CurrentUser).getQuestionCount() == 0) throw new Exception("Вы еще не прошли экзамен");
        if (students.get(CurrentUser).getRightAnswers() <= 2)
            return 2;
        else
            return students.get(CurrentUser).getRightAnswers();
    }
}