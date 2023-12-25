[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/yw5p-AaX)
# tema-2-poo-2023

Programul incepe prin prelucrarea informatiilor  legate de fisierele de intrare si iesire. Intr-un bloc de try-catch pentru eroare de IO se incearca citirea din fisierul de input, pana la ultima linie.  Fiecare linie in parte este impartita in cuvinte si se verfiica ce comanda a fost apelata, in functie de comanda apelata se intra in if ul respectiv.
Pentru toate comenziile se procedeaza aproximativ la fel, cu mici diferente intre ce tip de informatii trebuie sa recunoastem in continuarea din comanda data de la terminal si dupa comanda ce trebuie sa o apelez in functie, prin intermediul unei variabile de tipul clasei secretariat. De exemplu, daca trebuie sa adaugam un student, se recunoaste in continuare din comanda numele si tipul studentului si se utilizeaza comanda de adaugaStudent  create in cadrul clasei Secretariat.
Clasa Student contine o variabila private string pentru nume, una private double pentru medie si un arraylist de stringuri private pentru preferinte, precum si getterele si setterele necesare. Clasa student are doua subclase StudentMaster si StudentLicenta, care ne ajuta la generalizare.
Clasa Curs contine o variabila private string pentru nume, una private int pentru capacitatea maxima cursului si o lista private generalizata in functie de tipurile de student, pentru a retine ce student sunt inscrisi la cursul respective. A fost folosita generalizarea aici pentru ca la anumite cursuri sa fie inscrisi numai student de la master/licenta. Cu exceptia getterlor si settelerlor mai avem si functia de afisare a informatiilor studentilor de la cursul respective.
Clasa Secretariat prezinta doua liste generalizate pentru cursuri de licenta si master si o lista pentru studenti. Functia de duplicStud verifica daca numele studentului dat ca parametru se afla deja sau nu in baza de date a secretariatului. Daca se afla atunci returneaza false, altfel true. Functia adaugaStudent verifica daca studentul se afla deja in baza de date si scris in fisier mesajul specific, iar  daca nu adauga studentul in baza de date a secretariatului in functie de tipul de studii la care este acesta.  Functia adaugaCurs creaza un curs in fucntie de informatiile primate ca parametrii si in functie de tipul cursurlui. Daca este curs de licenta se adauga la lista respective, oar dac ae de master la cealalta. Functia citesteMedii cauta fisierele al caror nume incepe cu ”note” si citeste mediile studentilor din acestea. Dupa parcurge baza de date a secretariatului si le seteaza noile medii. Functia posteazaMedii prima oara sorteaza studentii dupa medie si nume, apoi deschide un fisier cu scopul de a scrie in continuarea lui toate datele studentilor, verificandu-se intr-un block try-catch exceptia de IO. In functia contestatie se modifica vechea medie a studentului, cu numele dat ca parametru functiei, cu una noua. Functia adauga preferinte adauga preferinte studentului cu numele dat ca parametru. Functia repartizeaza sorteaza studentii in functie de media lor, dupa parcirge lista lor de preferinte si se verifica fiecare curs in parte in functie de tipul acestuia. Daca cursul inca nu este plin se adauga studentul si se seteaza variabila okm ca 1, adica acesta a fost deja adaugat undeva.  Daca capacitatea maxima a cursului a fost atinsa se verifica ca studentul sa nu aiba media la fel ca media cea mai mica a unui student deja repartizat la cursul respectiv. Functia posteazaCurs parcurge listele de cursuri si dupa scrie in continuarea fisierului informatiile studentilor ordonati in functie de nume. Functia posteazaStudent cauta studentul in baza de date dupa nume, dupa verifica daca e la licenta sau master, ca apoi sa scrie in fisier toate datele sale (nume, medi si cursurile sale).

