import Objects.TestObject;
import structures.BTree;
import structures.UnsortedFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class TestTree {
    private final Scanner scanner = new Scanner(System.in);

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

    String[] girlsName = {
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

    public TestTree() throws IOException {
        this.firstTest();
        /*System.out.println("Vitajte v aplikacii. Zadajte ktory test chcete vykonat.");
        System.out.println("1: Test podla pradepodobnosti vykonania prikazu pridaj zmaz a hladaj. Zadava sa aj pocet vykonavania operacii.");
        System.out.println("2: pridanie zadaneho poctu prvkov prvkov, najdenie vsetkych prvkov a ich vymazanie. No konci zostane pocet zadanych prvkov.");
        System.out.println("3: kontrola hladania nahodnych intervalov.");
        System.out.println("4: kontrola konecnej hlbky stromu pri vlozeni 1000000 prvkov.");
        int druhTestu = scanner.nextInt();

        while (druhTestu != 1 && druhTestu != 2 && druhTestu != 3 && druhTestu != 4) {
            System.out.print("Zadaj znova: ");
            druhTestu = scanner.nextInt();
        }
        if (druhTestu == 1) {
            this.vloz();
        } else if (druhTestu == 2) {
            this.delete();
        } else if (druhTestu == 3) {
            this.testInterval();
        } else {
            this.testDepth();
        }*/
    }

    public void vloz() throws IOException {
        structures.UnsortedFile<TestObject> file = new structures.UnsortedFile<>("pomFile");
        file.getFile().setLength(0);
        ArrayList<TestObject> poleOsob = new ArrayList<TestObject>();
        ArrayList<Long> poleAdries = new ArrayList<Long>();
        for (Integer i = 0; i < 10000; i++) {
            TestObject person = new TestObject(i, this.girlsName[(int)(Math.random() * this.girlsName.length)],
                    this.girlsSurnames[(int)(Math.random() * this.girlsSurnames.length)]);

            poleOsob.add(person);
            poleAdries.add(file.add(person, new TestObject()));
        }

        /*for (Objects.Person p : poleOsob) {
            System.out.println(p.getRC());
        }*/
        BTree<TestObject> tree = new BTree<TestObject>("testTree", new TestObject());

        for (Integer i = 0; i < poleOsob.size(); i++) {
            tree.add(poleOsob.get(i));
        }

        for (Integer i = 0; i < poleOsob.size(); i++) {
            if (tree.find(poleOsob.get(i)) == null) {
                System.out.println("Nenasiel sa prvok: " + poleOsob.get(i).getId() + " na adrese " + poleAdries.get(i));
                return;
            }
        }
        System.out.println("Test presiel");
    }

    public void delete() throws IOException {
        structures.UnsortedFile<TestObject> file = new structures.UnsortedFile<>("pomFile");
        file.getFile().setLength(0);
        ArrayList<TestObject> poleOsob = new ArrayList<TestObject>();
        ArrayList<Long> poleAdries = new ArrayList<Long>();
        for (Integer i = 0; i < 10000; i++) {
            TestObject person = new TestObject(i, this.girlsName[(int)(Math.random() * this.girlsName.length)],
                    this.girlsSurnames[(int)(Math.random() * this.girlsSurnames.length)]);

            poleOsob.add(person);
            poleAdries.add(file.add(person, new TestObject()));
        }

        BTree<TestObject> tree = new BTree<TestObject>("testTree", new TestObject());

        for (Integer i = 0; i < poleOsob.size(); i++) {
            tree.add(poleOsob.get(i));
        }

        for (Integer i = 0; i < poleOsob.size(); i++) {
            if (tree.find(poleOsob.get(i)) == null) {
                System.out.println("Nenasiel sa prvok: " + poleOsob.get(i).getId() + " na adrese " + poleAdries.get(i));
                return;
            }
        }

        for (Integer i = 0; i < poleOsob.size(); i++) {
            if (!tree.delete(poleOsob.get(i))) {
                System.out.println("Prvok: " + poleOsob.get(i).getId() + " sa nepodarilo vymazat na adrese " + poleAdries.get(i));
                System.out.println(tree.getCount());
                return;
            }
        }

        for (Integer i = 0; i < poleOsob.size(); i++) {
            if (tree.find(poleOsob.get(i)) != null) {
                System.out.println("Nasiel sa vymazany prvok: " + poleOsob.get(i).getId() + " na adrese " + poleAdries.get(i));
                return;
            }
        }

        System.out.println("Test presiel");
    }

    public void firstTest() throws IOException {
        structures.UnsortedFile<TestObject> file = new structures.UnsortedFile<>("files/pomFile");
        file.getFile().setLength(0);
        ArrayList<TestObject> poleOsob = new ArrayList<TestObject>();
        ArrayList<Long> poleAdries = new ArrayList<Long>();
        ArrayList<TestObject> poleVlozenychOsob = new ArrayList<TestObject>();
        ArrayList<Long> poleVlozenychAdries = new ArrayList<Long>();
        for (Integer i = 0; i < 100000; i++) {
            TestObject person = new TestObject(i, this.girlsName[(int)(Math.random() * this.girlsName.length)],
                    this.girlsSurnames[(int)(Math.random() * this.girlsSurnames.length)]);

            poleOsob.add(person);
            poleAdries.add(file.add(person, new TestObject()));
        }

        BTree<TestObject> tree = new BTree<TestObject>("testTree", new TestObject());


        int pVloz = 0, pMaz = 0, pHladaj = 0;

        while (pVloz+pMaz+pHladaj != 100) {
            System.out.print("Zadaj pravdepodobnost pre vkladanie: ");
            pVloz = scanner.nextInt();
            System.out.print("Zadaj pravdepodobnost pre mazanie: ");
            pMaz = scanner.nextInt();
            System.out.print("Zadaj pravdepodobnost pre hladanie: ");
            pHladaj = scanner.nextInt();

            if ((pVloz+pMaz+pHladaj) != 100) {
                System.out.println("Suma nedava hodnotu 100. Zadaj znova!" + (pVloz+pMaz+pHladaj));
            }
        }
        System.out.print("Zadaj pocet pokusov: ");
        int pocetPokusov = scanner.nextInt();
        System.out.println("Proces zacina.");
        int pocetV = 0, pocetM = 0, pocetHTrue = 0, pocetHFalse = 0, pocetMNull = 0;

        for (int i = 0; i < pocetPokusov; i++) {
            double metoda = Math.random() * 100;
            if (metoda < pVloz) {
                //System.out.println("Vklada sa prvok: " + polePrvkov.get(0));
                tree.add(poleOsob.get(0));
                poleVlozenychOsob.add(poleOsob.get(0));
                poleVlozenychAdries.add(poleAdries.get(0));
                poleOsob.remove(0);
                poleAdries.remove(0);
                pocetV++;
            } else if (metoda < (pVloz + pMaz)) {
                //System.out.println(poleVlozenychPrvkov);
                if (poleVlozenychOsob.size() != 0) {
                    tree.delete(poleVlozenychOsob.get(0));
                    poleVlozenychOsob.remove(0);
                    poleVlozenychAdries.remove(0);
                    pocetM++;
                    pocetMNull--;
                }
                pocetMNull++;
            } else {
                if (poleVlozenychOsob.size() != 0) {
                    int randomNum = (int)(Math.random()*(poleVlozenychOsob.size()));
                    if (tree.find(poleVlozenychOsob.get(randomNum)) != null) {
                        pocetHTrue++;
                    } else {
                        pocetHFalse++;
                    }
                }
            }
        }
        System.out.println(tree.getInOrderArrayL());
        System.out.println("Pocet vlozeni: " + pocetV);
        System.out.println("Pocet mazani: " + pocetM);
        System.out.println("Pocet mazani pri prazdnom strome: " + pocetMNull);
        System.out.println("Pocet najdeni(true): " + pocetHTrue);
        System.out.println("Pocet najdeni(false): " + pocetHFalse);
        System.out.println("Pocet prvkov v konecnom strome: " + (pocetV - pocetM));
        System.out.println("Pocet prvkov: " + tree.getCount());
    }

    /*public void secondTest() {
        BTree<String, Person> tree = new BTree<String, Person>("testTree");
        ArrayList<String> pole = new ArrayList<String>();
        int pocetVlozeni = 0;
        System.out.println("Zadaj pocet: ");
        int pocetOperacii = scanner.nextInt();
        System.out.println("Zadaj pocet prvkov, ktory ostane na konci testovania.");
        int pocetPrvkovNaKonci = scanner.nextInt();
        for (int i = 0; i < pocetOperacii; i++) {
            pole.add(girlsSurnames[(int) (Math.random() * girlsSurnames.length)] + girlsSurnames[(int) (Math.random() * girlsSurnames.length)]);
        }
        Collections.shuffle(pole);
        //pole = {8, 7, 6, 4, 1, 3, 9, 2, 5, 0};
        //System.out.println("\nShuffled List : \n" + pole);
        for (int i = 0; i < pole.size(); i++) {
            tree.add(pole.get(i), (long) i);
            pocetVlozeni++;
        }

        System.out.println("Vkladalo sa " + pole.size() + " prvkov a vlozilo sa " + pocetVlozeni + " prvkov.");
        System.out.println();


        int pocetNajdeni = 0;
        for (int i = 0; i < pole.size(); i++) {
            if (tree.find(pole.get(i)) != null) {
                pocetNajdeni++;
            }
        }
        System.out.println("Naslo sa " + pocetNajdeni + " prvkov z " + pocetVlozeni);
        System.out.println();

        Collections.shuffle(pole);
        int pocetMazani = 0;
        for (int i = 0; i < pole.size() - pocetPrvkovNaKonci; i++) {
            if (!tree.delete(pole.get(i), (long) i)) {
                System.out.println("Cislo " + i + "sa nepodarilo vymazat!");
            } else {
                //System.out.println("Cislo " + i);
                pocetMazani++;
            }
        }
        System.out.println("Vymazalo sa " + pocetMazani + " prvkov.");
        System.out.println(tree.getInOrderArrayL());
        System.out.println("Pocet prvkov: " + tree.getCount());
    }*/

    public void testInterval() {
        structures.UnsortedFile<TestObject> file = new structures.UnsortedFile<>("pomFile");
        try {
            file.getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<TestObject> poleOsob = new ArrayList<TestObject>();
        ArrayList<Long> poleAdries = new ArrayList<Long>();
        for (int i = 0; i < 1000; i++) {
            TestObject person = new TestObject(i, this.girlsName[(int)(Math.random() * this.girlsName.length)],
                    this.girlsSurnames[(int)(Math.random() * this.girlsSurnames.length)]);

            poleOsob.add(person);
            poleAdries.add(file.add(person, new TestObject()));
        }

        BTree<TestObject> tree = new BTree<TestObject>("testTree", new TestObject());

        for (int i = 0; i < poleOsob.size(); i++) {
            tree.add(poleOsob.get(i));
        }

        for (int i = 0; i < 1000; i++) {
            double rand = Math.random()*(poleOsob.size());
            int first = (int) rand;
            rand = Math.random()*poleOsob.size();
            int second = (int) rand;
            //System.out.println(first);
            //System.out.println(second);
            BTree<TestObject> treeA;
            if (first < second) {
                treeA = tree.findInterval(new TestObject(first), new TestObject(second));
            } else {
                treeA = tree.findInterval(new TestObject(second), new TestObject(first));
            }
            if (treeA != null) {
                ArrayList<TestObject> ar = treeA.getInOrderArrayL();

                for (int j = 0; j < ar.size(); j++) {
                    //TestObject object = file.getData(ar.get(j), new TestObject());
                    if (first < second) {
                        if (ar.get(j).getId() < first || ar.get(j).getId() > second) {
                            System.out.println("Test je neuspesny");
                            return;
                        }
                    } else {
                        if (ar.get(j).getId() > first || ar.get(j).getId() < second) {
                            System.out.println("Test je neuspesny");
                            return;
                        }
                    }
                }
                //System.out.println(treeA.getInOrderArrayL());
            }
            //System.out.println(treeA.getInOrderArrayL());
        }
        System.out.println("Test prebehol uspesne");
    }

    public void testDepth() {
        structures.UnsortedFile<TestObject> file = new structures.UnsortedFile<>("pomFile");
        try {
            file.getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<TestObject> poleOsob = new ArrayList<TestObject>();
        ArrayList<Long> poleAdries = new ArrayList<Long>();
        for (int i = 0; i < 1000; i++) {
            TestObject person = new TestObject(i, this.girlsName[(int)(Math.random() * this.girlsName.length)],
                    this.girlsSurnames[(int)(Math.random() * this.girlsSurnames.length)]);

            poleOsob.add(person);
            poleAdries.add(file.add(person, new TestObject()));
        }

        BTree<TestObject> tree = new BTree<TestObject>("testTree", new TestObject());

        for (int i = 0; i < poleOsob.size(); i++) {
            tree.add(poleOsob.get(i));
        }

        System.out.println(tree.controlDepth());
    }
}
