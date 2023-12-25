package org.example;

import java.util.ArrayList;

public class Student {
    private String nume;
    private double medie;
    private ArrayList<String> preferinte;
    private Student() {
    }
    public Student(String nume, double medie) {
        this.nume = nume;
        this.medie = medie;
    }
    public String getNume() {
        return nume;
    }
    public double getMedie() {
        return medie;
    }
    public void setMedie(double medie) {
        this.medie = medie;
    }
    public ArrayList<String> getPreferinte() {
        return preferinte;
    }
    public void setPreferinte(ArrayList<String> preferinte) {
        this.preferinte = preferinte;
    }
}

class StudentLicenta extends Student {
    public StudentLicenta(String nume, double medie) {
        super(nume, medie);
    }
}

class StudentMaster extends Student {
    public StudentMaster(String nume, double medie) {
        super(nume, medie);
    }
}
