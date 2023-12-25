package org.example;
import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


class StudentDuplicatException extends Exception {
    public StudentDuplicatException(String nume) {
        super("Studentul " + nume + " este deja inscris!");
    }
}

public class Secretariat extends Curs {
    private List<Curs<StudentLicenta>> cursuriLicenta;
    private List<Curs<StudentMaster>> cursuriMaster;
    private List<Student> studenti;

    public Secretariat() {
        cursuriLicenta = new ArrayList<>();
        cursuriMaster = new ArrayList<>();
        studenti = new ArrayList<Student>();
    }
    public List<Student> getStudenti(){
        return studenti;
    }

    public List<Curs<StudentLicenta>> getCursuriLicenta() {
        return cursuriLicenta;
    }

    public List<Curs<StudentMaster>> getCursuriMaster() {
        return cursuriMaster;
    }

    public int getNrStudenti() {
        return studenti.size();
    }

    public boolean duplicStud(String nume) {
        /*verifica daca studentul cu numele nume este deja inscris*/
        for (int i = 0; i < studenti.size(); i++) {
            if (studenti.get(i).getNume().equals(nume)) {
                return false;
            }
        }
        return true;
    }
    public void adaugaStudent(String tip, String nume, String output) throws StudentDuplicatException {
        /*adauga un student in lista de studenti dupa ce verifica daca acesta exista deja sau nu*/
        if (!duplicStud(nume)) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
                writer.write("***");
                writer.newLine();
                writer.write("Student duplicat: " + nume);
                writer.newLine();
                writer.close();
            } catch (IOException e) {
                System.out.println("Eroare la scrierea mediilor în fișierul de ieșire: " + e.getMessage());
            }
            throw new StudentDuplicatException(nume);
        }

        if (tip.equals("licenta")) {
            StudentLicenta student = new StudentLicenta(nume, 0);
            studenti.add(student);
        } else if (tip.equals("master")) {
            StudentMaster student = new StudentMaster(nume, 0);
            studenti.add(student);
        }
    }
    public void adaugaCurs(String tip, String nume, int capacitateMaxima) {
        /*adauga un curs in lista de cursuri in functie de tipul sau*/
        if (tip.equals("licenta")) {
            Curs<StudentLicenta> curs = new Curs<>(nume, capacitateMaxima);
            cursuriLicenta.add(curs);
        } else if (tip.equals("master")) {
            Curs<StudentMaster> curs = new Curs<>(nume, capacitateMaxima);
            cursuriMaster.add(curs);
        }
    }
    public void citesteMedii(String fisier, File folder, String output2) {
        /*cauta fisierele al caror nume incepe cu fisier si citeste mediile studentilor din acestea*/
        File[] files = folder.listFiles((dir, name) -> name.startsWith(fisier));

        if (files != null) {
            for (File file : files) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(" - ");
                        String nume = parts[0];
                        double medie = Double.parseDouble(parts[1]);
                        /*adauga mediile citite in lista de studenti*/
                        for (int i = 0; i < studenti.size(); i++) {
                            if (studenti.get(i).getNume().equals(nume)) {
                                studenti.get(i).setMedie(medie);
                            }
                        }
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void posteazaMedii(String fisier) {
        /*Sorteaza studentii dupa medie si apoi dupa nume*/
        studenti.sort(Comparator
                .comparingDouble(Student::getMedie)
                .reversed()
                .thenComparing(Student::getNume));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fisier, true))) {
            writer.write("***");
            writer.newLine();
            for (Student student : studenti) {
                writer.write(student.getNume() + " - " + student.getMedie());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Eroare la scrierea mediilor în fișierul de ieșire: " + e.getMessage());
        }
    }
    public void contestatie(String nume, double nouaMedie) {
        /*modifica media studentului cu numele nume cu nouaMedie*/
        for (int i = 0; i < studenti.size(); i++) {
            if (studenti.get(i).getNume().equals(nume)) {
                studenti.get(i).setMedie(nouaMedie);
            }
        }
    }
    public void adaugaPreferinte(String nume, ArrayList<String> preferinte) {
        /*adauga preferintele studentului cu numele nume*/
        for (int i = 0; i < studenti.size(); i++) {
            if (studenti.get(i).getNume().equals(nume)) {
                studenti.get(i).setPreferinte(preferinte);
            }
        }
    }
    public void repartizeaza() {
        /*Se sorteaza studentii dupa medie si se adauga in lista de studenti a cursurilor in functie de preferinte*/
        studenti.sort(Comparator
                .comparingDouble(Student::getMedie)
                .reversed());
        for (int i = 0; i < studenti.size(); i++) {
            /*Variabila ok verifica daca studentul a fost adaugat la un curs*/
            int ok = 0;
            if (studenti.get(i).getPreferinte() != null) {
                for (int j = 0; j < studenti.get(i).getPreferinte().size(); j++) {
                    /*Se verifica daca studentul este licenta sau master*/
                    if (studenti.get(i) instanceof StudentLicenta && ok == 0) {
                        for (int k = 0; k < cursuriLicenta.size(); k++) {
                            if (studenti.get(i).getPreferinte().get(j).equals(cursuriLicenta.get(k).getNume())) {
                                /*Se verifica daca cursul are locuri libere si se adauga studentul*/
                                if (cursuriLicenta.get(k).getCapacitateMaxima() > cursuriLicenta.get(k).getStudenti().size() && ok == 0) {
                                    cursuriLicenta.get(k).adaugaStudent((StudentLicenta) studenti.get(i));
                                    ok = 1;
                                } else if (cursuriLicenta.get(k).getCapacitateMaxima() <= cursuriLicenta.get(k).getStudenti().size() && ok == 0) {
                                    /*Daca nu are locuri libere, se verifica daca media studentului este mai mare decat a studentului cu cea mai mica medie din curs*/
                                    cursuriLicenta.get(k).getStudenti().sort(Comparator
                                            .comparingDouble(Student::getMedie));
                                    if (studenti.get(i).getMedie() == cursuriLicenta.get(k).getStudenti().get(0).getMedie()) {
                                        cursuriLicenta.get(k).adaugaStudent((StudentLicenta) studenti.get(i));
                                        ok = 1;
                                    }
                                }
                            }
                        }
                    }
                    else if (studenti.get(i) instanceof StudentMaster && ok == 0) {
                        for (int k = 0; k < cursuriMaster.size(); k++) {
                            if (studenti.get(i).getPreferinte().get(j).equals(cursuriMaster.get(k).getNume())) {
                                /*Se verifica daca cursul are locuri libere si se adauga studentul*/
                                if (cursuriMaster.get(k).getCapacitateMaxima() > cursuriMaster.get(k).getStudenti().size() && ok == 0) {
                                    cursuriMaster.get(k).adaugaStudent((StudentMaster) studenti.get(i));
                                    ok = 1;
                                } else if (cursuriMaster.get(k).getCapacitateMaxima() <= cursuriMaster.get(k).getStudenti().size() && ok == 0) {
                                    /*Daca nu are locuri libere, se verifica daca media studentului este mai mare decat a studentului cu cea mai mica medie din curs*/
                                    cursuriMaster.get(k).getStudenti().sort(Comparator
                                            .comparingDouble(Student::getMedie));
                                    if (studenti.get(i).getMedie() == cursuriMaster.get(k).getStudenti().get(0).getMedie()) {
                                        cursuriMaster.get(k).adaugaStudent((StudentMaster) studenti.get(i));
                                        ok = 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void posteazaCurs(String curs, String output) {
        /*Se cauta cursul cu numele curs si se posteaza in fisierul de output*/
        for (int i = 0; i < cursuriLicenta.size(); i++) {
            if (cursuriLicenta.get(i).getNume().equals(curs)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
                    writer.write("***");
                    writer.newLine();
                    writer.write(curs + " ("+ cursuriLicenta.get(i).getCapacitateMaxima()+")");
                    writer.newLine();
                    /*Se sorteaza studentii dupa nume*/
                    cursuriLicenta.get(i).getStudenti().sort(Comparator
                            .comparing(Student::getNume));
                    for (Student student : cursuriLicenta.get(i).getStudenti()) {
                        writer.write(student.getNume() + " - " + student.getMedie());
                        writer.newLine();
                    }
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Eroare la scrierea în fișierul de ieșire: " + e.getMessage());
                }
            }
        }
        for (int i = 0; i < cursuriMaster.size(); i++) {
            if (cursuriMaster.get(i).getNume().equals(curs)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
                    writer.write("***");
                    writer.newLine();
                    writer.write(curs + " (");
                    writer.write(cursuriMaster.get(i).getCapacitateMaxima()+")");
                    writer.newLine();
                    cursuriMaster.get(i).getStudenti().sort(Comparator
                            .comparing(Student::getNume));
                    for (Student student : cursuriMaster.get(i).getStudenti()) {
                        writer.write(student.getNume() + " - " + student.getMedie());
                        writer.newLine();
                    }
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Eroare la scrierea în fișierul de ieșire: " + e.getMessage());
                }
            }
        }
    }
    public void posteazaStudent(String student, String output) {
        /*Se cauta studentul cu numele student si se posteaza in fisierul de output datele necesare*/
        for (int i = 0; i < studenti.size(); i++) {
            if (studenti.get(i).getNume().equals(student)) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
                    writer.write("***");
                    writer.newLine();
                    writer.write("Student ");
                    if (studenti.get(i) instanceof StudentLicenta) {
                        writer.write("Licenta: ");
                    } else if (studenti.get(i) instanceof StudentMaster) {
                        writer.write("Master: ");
                    }
                    writer.write(studenti.get(i).getNume() + " - " + studenti.get(i).getMedie());
                    /*Se verifica tipul de student si lista de cursuri, pentru a se gasi unde a fost asignat*/
                    if (studenti.get(i) instanceof StudentLicenta) {
                        for (int j = 0; j < cursuriLicenta.size(); j++) {
                            for (int k = 0; k < cursuriLicenta.get(j).getStudenti().size(); k++) {
                                if (cursuriLicenta.get(j).getStudenti().get(k).getNume().equals(studenti.get(i).getNume())) {
                                    writer.write(" - ");
                                    writer.write(cursuriLicenta.get(j).getNume());
                                }
                            }
                        }
                    } else if (studenti.get(i) instanceof StudentMaster) {
                        for (int j = 0; j < cursuriMaster.size(); j++) {
                            for (int k = 0; k < cursuriMaster.get(j).getStudenti().size(); k++) {
                                if (cursuriMaster.get(j).getStudenti().get(k).getNume().equals(studenti.get(i).getNume())) {
                                    writer.write(" - ");
                                    writer.write(cursuriMaster.get(j).getNume());
                                }
                            }
                        }
                    }
                    writer.newLine();
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Eroare la scrierea în fișierul de ieșire: " + e.getMessage());
                }
            }
        }
    }
}