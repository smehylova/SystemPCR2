import Objects.Person;
import structures.UnsortedFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class TestFile {
    private String[] girlsSurnames = {
            "Višňovská", "Hýroššová", "Trúchlyová", "Majtánová", "Trajčíková", "Beniačová", "Markušová", "Ševčíková", "Valachová", "Jonášová", "Tamášiová", "Kaňová", "Brezánová",
            "Baráneková", "Rajčanová", "Čičkánová", "Pilková", "Čepecová", "Šmehylová", "Držíková",
            "Jakubeková", "Deáková", "Kučerová", "Detková", "Kramerová", "Sopková", "Blažejová", "Ďurišová", "Hulejová", "Cibulková", "Trnovecová",
            "Mandová", "Molitorová", "Veliká", "Kadašová", "Kubicová", "Martinková", "Kemková", "Smiešková", "Valášeková", "Pokorná", "Chochulová", "Novosadová",
            "Šoltýsová", "Varinská", "Tomašecová", "Turská", "Púčeková", "Rakovská", "Hájniková", "Kmeťová", "Slotová", "Bednárová", "Tvrdá", "Cabuková", "Kočanová",
            "Palacková", "Plánková", "Slatinská", "Omastová", "Erdélyiová", "Kopecká", "Tichá", "Kuchareková", "Berníková", "Šípošová", "Sitárová", "Porubská",
            "Hudeková", "Černáková", "Levková", "Mrázová", "Gábrišová", "Ondrušeková", "Smolková", "Šmáriková", "Rudinská", "Krčmárová", "Horecká", "Hurtošová",
            "Hladíková", "Rybáriková", "Kováčiková", "Ponechalová", "Chládeková", "Vrábelová", "Lipovská", "Lemešová", "Velčicová", "Matušíková", "Korbelová", "Koláriková"
    };
    private String[] girlsName = {
            "Adela", "Adriána", "Agáta", "Agnesa", "Alana", "Albína", "Alena", "Alexandra", "Alexia", "Alica", "Alojzia",
            "Alžbeta", "Amália", "Anabela", "Anastázia", "Andrea", "Aneta", "Anežka", "Angela", "Angelika", "Anna", "Antónia", "Aurélia",
            "Auróra", "Barbara", "Barbora", "Beáta", "Berta", "Bianka", "Bibiána", "Blanka", "Blažena", "Bohdana", "Bohumila", "Bohuslava", "Božena", "Božidara", "Branislava\", " +
            "Brigita", "Bronislava", "Cecília", "Dagmara", "Dana", "Danica", "Daniela", "Dária", "Darina", "Daša", "Denisa", "Diana",
            "Dobromila", "Dobroslava", "Dominika", "Dorota", "Drahomíra", "Drahoslava", "Dušana", "Edita", "Ela", "Elena", "Eliána", "Eleonóra",
            "Elisa", "Eliška", "Elvíra", "Ema", "Emília", "Erika", "Ernestína", "Estera", "Etela", "Eugénia", "Eva", "Filoména", "Františka",
            "Gabriela", "Galina", "Gertrúda", "Gréta", "Hana", "Hedviga", "Helena", "Henrieta", "Hermína", "Hilda", "Hortenzia", "Ida",
            "Ingrida", "Irena", "Irma", "Ivana", "Iveta", "Ivica", "Izabela", "Jana", "Jarmila", "Jaromíra", "Jaroslava", "Jela", "Jesika",
            "Johana", "Jolana", "Jozefína", "Judita", "Júlia", "Juliána", "Justína", "Kamila", "Karolína", "Karina", "Katarína", "Kiara",
            "Klára", "Klaudia", "Kornélia", "Kristína", "Kvetoslava", "Ladislava", "Lara", "Laura", "Lea", "Lenka", "Lesana", "Lesia",
            "Liana", "Libuša", "Liliana", "Linda", "Lívia", "Ľubica", "Ľubomíra", "Ľuboslava", "Lucia", "Ľudmila", "Lujza", "Lýdia",
            "Magdaléna", "Malvína", "Marcela", "Margaréta", "Margita", "Mária", "Marianna", "Marína", "Marta", "Martina", "Matilda",
            "Melánia", "Melisa", "Mia", "Michaela", "Milada", "Milena", "Milica", "Miloslava", "Milota", "Miriama", "Miroslava", "Monika",
            "Naďa", "Nadežda", "Natália", "Nataša", "Nela", "Nikola", "Nina", "Noéma", "Nora", "Oľga", "Olívia", "Olympia", "Otília",
            "Oxana", "Patrícia", "Paula", "Paulína", "Pavla", "Perla", "Petra", "Petrana", "Petronela", "Radka", "Radoslava", "Radovana",
            "Rebeka", "Regina", "Renáta", "Rita", "Romana", "Rozália", "Ružena", "Sabína", "Sandra", "Sára", "Sidónia", "Silvia",
            "Simona", "Sláva", "Slávka", "Slavomíra", "Sofia", "Soňa", "Stanislava", "Štefánia", "Stela", "Svetlana", "Tamara", "Táňa",
            "Tatiana", "Tereza", "Terézia", "Tímea", "Uršuľa", "Valentína", "Valéria", "Vanda", "Vanesa", "Veronika", "Viera",
            "Vieroslava", "Viktória", "Vilma", "Viola", "Viviána", "Vladimíra", "Vlasta", "Xénia", "Zara", "Žaneta",
            "Zdenka", "Želmíra", "Zina", "Zita", "Zlatica", "Žofia", "Zoja", "Zora", "Zuzana"
    };

    public TestFile() {
        //this.test(40, 40, 20, 1000000);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Vitajte v aplikacii. Zadajte ktory test chcete vykonat.");
        System.out.println("1: Test pridavania.");
        System.out.println("2: Test mazania.");
        int druhTestu = scanner.nextInt();

        while (druhTestu != 1 && druhTestu != 2) {
            System.out.print("Zadaj znova: ");
            druhTestu = scanner.nextInt();
        }
        if (druhTestu == 1) {
            this.testAdd();
        } else if (druhTestu == 2) {
            this.testDelete();
        }
    }

    public boolean testAdd() {
        HashMap<Long, Person> tableData = new HashMap<>();
        UnsortedFile<Person> file = new UnsortedFile<>("testAdd");
        try {
            file.getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {
            String rc = "";
            for (int j = 0; j < 11; j++) {
                if (j == 6) {
                    rc += "/";
                } else {
                    rc += (int)(Math.random() * 9);
                }
            }
            Person person = new Person(this.girlsName[(int)(Math.random() * this.girlsName.length)],
                    this.girlsSurnames[(int)(Math.random() * this.girlsSurnames.length)],
                    new Date(99, 1, 17),
                    rc);

            tableData.put(file.add(person, new Person()), person);
        }

        Object[] keys = tableData.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            Person personFromFile = file.getData((Long) keys[i], new Person());
            Person personFromTable = tableData.get((Long) keys[i]);

            if (!personFromFile.myEquals(personFromTable)) {
                System.out.println("Osoba zo suboru: " + personFromFile.getName() + personFromFile.getSurname() + personFromFile.getRC() + personFromFile.getBirthday());
                System.out.println("Osoba z tabulky: " + personFromTable.getName() + personFromTable.getSurname() + personFromTable.getRC() + personFromTable.getBirthday());
                return false;
            } else {
                System.out.println(personFromFile.getRC() + " " + personFromTable.getRC());
            }
        }

        System.out.println("********************************");
        System.out.println("TEST DOPADOL USPESNE!");
        System.out.println("********************************");
        System.out.println("Nasli sa vsetky pridane zaznamy na adresach aj s rovnakymi hodnotami");
        return true;
    }

    public boolean testDelete() {
        HashMap<Long, Person> tableData = new HashMap<>();
        UnsortedFile<Person> file = new UnsortedFile<>("testAdd");
        try {
            file.getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {
            String rc = "";
            for (int j = 0; j < 11; j++) {
                if (j == 6) {
                    rc += "/";
                } else {
                    rc += (int)(Math.random() * 9);
                }
            }
            Person person = new Person(this.girlsName[(int)(Math.random() * this.girlsName.length)],
                    this.girlsSurnames[(int)(Math.random() * this.girlsSurnames.length)],
                    new Date(99, 1, 17),
                    rc);

            tableData.put(file.add(person, new Person()), person);
        }

        Object[] keys = tableData.keySet().toArray();
        for (int i = 0; i < keys.length - 2; i++) {
            file.delete((Long) keys[i], new Person());
            //tableData.remove((Long) keys[i]);
        }

        if (file.getEmptyAddresses().size() > keys.length - 2) {
            System.out.println("Zoznam prazdnych zaznamov je vacsi ako by mal byt!");
            return false;
        }

        for (int i = 0; i < keys.length/2; i++) {
            if (file.getData((Long) keys[i], new Person()) != null) {
                System.out.println("Nasiel sa vymazany zaznam!");
                return false;
            }
        }

        System.out.println("********************************");
        System.out.println("TEST DOPADOL USPESNE!");
        System.out.println("********************************");
        System.out.println("Pocet prazdnych zaznamov v subore: " + file.getEmptyAddresses().size());
        System.out.println("Pocet odstranenych zaznamov: " + (keys.length - 2));
        return true;
    }

    public boolean test(int add, int delete, int find, int count) {
        int countAdd = 0, countDelete = 0, countFind = 0;
        if (add + delete + find != 100) {
            delete = 100 - add;
        }

        HashMap<Long, Person> tableData = new HashMap<>();
        UnsortedFile<Person> file = new UnsortedFile<>("testAdd");
        try {
            file.getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < count; i++) {
            int rand = (int) (Math.random() * 100);
            if (rand < add) {//vloz
                countAdd++;
                String rc = "";
                for (int j = 0; j < 11; j++) {
                    if (j == 6) {
                        rc += "/";
                    } else {
                        rc += (int)(Math.random() * 9);
                    }
                }
                Person person = new Person(this.girlsName[(int)(Math.random() * this.girlsName.length)],
                        this.girlsSurnames[(int)(Math.random() * this.girlsSurnames.length)],
                        new Date(99, 1, 17),
                        rc);

                tableData.put(file.add(person, new Person()), person);
            } else if (rand < delete + add) {//maz
                if (tableData.size() > 0) {
                    countDelete++;
                    Object[] keys = tableData.keySet().toArray();
                    int key = (int) (Math.random() * keys.length);

                    file.delete((Long) keys[key], new Person());
                    tableData.remove((Long) keys[key]);
                }
            } else {//hladaj
                if (tableData.size() > 0) {
                    countFind++;
                    Object[] keys = tableData.keySet().toArray();
                    int key = (int) (Math.random() * keys.length);

                    Person personFromFile = file.getData((Long) keys[key], new Person());
                    Person personFromTable = tableData.get((Long) keys[key]);

                    if (!personFromFile.myEquals(personFromTable)) {
                        System.out.println("Osoba zo suboru: " + personFromFile.getName() + personFromFile.getSurname() + personFromFile.getRC() + personFromFile.getBirthday());
                        System.out.println("Osoba z tabulky: " + personFromTable.getName() + personFromTable.getSurname() + personFromTable.getRC() + personFromTable.getBirthday());
                        return false;
                    }
                }
            }
        }
        if (countAdd - countDelete != tableData.size()) {
            System.out.println("Pocet zaznamov v subore nesedi s vysledkom.");
            return false;
        }

        Object[] keys = tableData.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            Person personFromFile = file.getData((Long) keys[i], new Person());
            Person personFromTable = tableData.get((Long) keys[i]);

            if (!personFromFile.myEquals(personFromTable)) {
                System.out.println("Nasiel sa nespravny zaznam.");
                System.out.println("Osoba zo suboru: " + personFromFile.getName() + personFromFile.getSurname() + personFromFile.getRC() + personFromFile.getBirthday());
                System.out.println("Osoba z tabulky: " + personFromTable.getName() + personFromTable.getSurname() + personFromTable.getRC() + personFromTable.getBirthday());
                return false;
            } else {
                System.out.println(personFromFile.getRC() + " " + personFromTable.getRC());
            }
        }

        System.out.println("********************************");
        System.out.println("TEST DOPADOL USPESNE!");
        System.out.println("********************************");
        System.out.println("Pocet prvkov v subore: " + tableData.size());
        System.out.println("Pocet pridavani: " + countAdd);
        System.out.println("Pocet hladani: " + countFind);
        System.out.println("Pocet mazani: " + countDelete);
        return true;
    }
}
