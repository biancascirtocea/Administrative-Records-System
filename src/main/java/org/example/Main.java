package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
	    if (args.length == 0) {
            System.out.println("Introduceti numele fisierului!");
            return;
        }
        /*Preluarea primului argument dat*/
        String testFolderName = args[0];
        /*Preluarea numelor fisierelor de input si output*/
        String input = "src/main/resources/" + testFolderName + "/" + testFolderName + ".in";
        String output = "src/main/resources/" + testFolderName + "/" + testFolderName + ".out";
        String output2 = "src/main/resources/" + testFolderName + "/" + testFolderName + "2.out";

        try {
            /*Citirea comenzilor din fisierul de input*/
            Secretariat secretariat = new Secretariat();
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String command = null;
            while ((command = reader.readLine()) != null) {
                String[] commandParts = command.split(" - ");

                if (commandParts[0].equals("adauga_student")) {
                    /*Preluarea numelui si tipului studentului*/
                    String nume = commandParts[2];
                    String tip = commandParts[1];
                    /*Adaugarea studentului in functie de tip*/
                    try {
                        secretariat.adaugaStudent(tip, nume, output);
                    } catch (StudentDuplicatException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (commandParts[0].equals("adauga_curs")) {
                    /*Preluarea tipului, numelui si capacitatii maxime a cursului*/
                    String tip = commandParts[1];
                    String nume = commandParts[2];
                    int capacitateMax = Integer.parseInt(commandParts[3]);
                    /*Adaugarea cursului in functie de tip*/
                    secretariat.adaugaCurs(tip, nume, capacitateMax);
                }
                if (commandParts[0].equals("citeste_mediile")) {
                    /*Preluarea numelui fisierului*/
                    String fisier = "note_";
                    File folder = new File("src/main/resources/"+testFolderName+"/");
                    /*Citirea mediilor din fisierul dat*/
                    secretariat.citesteMedii(fisier, folder, output2);
                }
                if (commandParts[0].equals("posteaza_mediile")) {
                    secretariat.posteazaMedii(output);
                }
                if (commandParts[0].equals("contestatie")) {
                    /*Preluarea numelui si noii medii*/
                    String nume = commandParts[1];
                    double nouaMedie = Double.parseDouble(commandParts[2]);
                    /*Contestarea mediei*/
                    secretariat.contestatie(nume, nouaMedie);
                }
                if (commandParts[0].equals("adauga_preferinte")) {
                    /*Preluarea numelui si preferintelor*/
                    String nume = commandParts[1];
                    ArrayList<String> preferinte = new ArrayList<String>();
                    for (int i = 2; i < commandParts.length; i++) {
                        String curs = commandParts[i];
                        preferinte.add(curs);
                    }
                    /*Adaugarea preferintelor*/
                    secretariat.adaugaPreferinte(nume, preferinte);
                }
                if (commandParts[0].equals("repartizeaza")) {
                    /*Repartizarea studentilor*/
                    secretariat.repartizeaza();
                }
                if (commandParts[0].equals("posteaza_curs")) {
                    /*Preluarea numelui cursului si postarea informatiilor*/
                    String curs = commandParts[1];
                    secretariat.posteazaCurs(curs, output);
                }
                if (commandParts[0].equals("posteaza_student")) {
                    /*Preluarea numelui studentului si postarea informatiilor*/
                    String student = commandParts[1];
                    secretariat.posteazaStudent(student, output);
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
