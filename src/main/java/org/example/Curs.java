package org.example;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
public class Curs<E extends Student> {
    private String nume;
    private int capacitateMaxima;
    private List<E> studenti;
    Curs() {
        studenti = new ArrayList<>();
    }
    public Curs(String nume, int capacitateMaxima) {
        this.nume = nume;
        this.capacitateMaxima = capacitateMaxima;
        studenti = new ArrayList<E>();
    }
    public String getNume() {
        return nume;
    }
    public int getCapacitateMaxima() {
        return capacitateMaxima;
    }
    public List<E> getStudenti() {
        return studenti;
    }
    public void adaugaStudent(E student) {
            studenti.add(student);
    }
    public void afiseazaStudenti() {
        for (E student : studenti) {
            System.out.println(student.getNume() + " " + student.getMedie());
        }
    }
}
