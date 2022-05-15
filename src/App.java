import Objects.*;
import structures.BDataNode;
import structures.BNode;
import structures.BTree;
import structures.UnsortedFile;

import javax.swing.*;
import java.io.*;
import java.util.*;

import static javax.swing.JOptionPane.showMessageDialog;


public class App {
    public BTree<PCR_date> PCRtests;
    public BTree<PCR_id> PCRtestsId;
    public BTree<Person> people;
    public BTree<Workplace> workplaces;
    public BTree<District> districts;
    public BTree<Region> regions;

    public UnsortedFile<PCR> fileTests;

    public App() {
        PCRtests = new BTree<>("files/PCRtest", new PCR_date());
        PCRtestsId = new BTree<>("files/PCRtestsID", new PCR_id());
        people = new BTree<>("files/People", new Person());
        workplaces = new BTree<>("files/Workplaces", new Workplace(-1));
        districts = new BTree<>("files/Districts", new District(-1));
        regions = new BTree<>("files/Regions", new Region(-1));

        fileTests = new UnsortedFile<PCR>("tests");
    }

    public void generateData(int countDistricts, int countRegions, int countWorkplaces, int countPeople, int countTests) {
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
        String[] boysName = {
                "Adam", "Adolf", "Adrián", "Aladár", "Alan", "Albert", "Albín", "Aleš", "Alexander", "Alex", "Alexej", "Alfonz",
                "Alfréd", "Alojz", "Ambróz", "Andreas", "Andrej", "Anton", "Arnold", "Artur", "Arpád", "Atila", "Augustín", "Aurel", "Bartolomej",
                "Belo", "Beňadik", "Benedikt", "Benjamín", "Bernard", "Blahoslav", "Blažej", "Bohdan", "Bohumil", "Bohumír", "Bohuš",
                "Bohuslav", "Boleslav", "Bonifác", "Boris", "Branislav", "Bronislav", "Bruno", "Bystrík", "Ctibor", "Cyprián", "Cyril", "Dalibor",
                "Damián", "Daniel", "Dárius", "Dávid", "Demeter", "Denis", "Dezider", "Dionýz", "Dobroslav", "Dominik", "Drahomír",
                "Drahoslav", "Dušan", "Edmund", "Eduard", "Eliáš", "Emanuel", "Emil", "Erich", "Erik", "Ernest", "Ervín", "Eugen",
                "Fedor", "Félix", "Ferdinand", "Filip", "Florián", "František", "Frederik", "Fridrich", "Gabriel", "Gašpar", "Gejza",
                "Gregor", "Gustáv", "Henrich", "Hubert", "Hugo", "Ignác", "Igor", "Iľja", "Imrich", "Ivan", "Izidor", "Jakub", "Ján",
                "Jarolím", "Jaromír", "Jaroslav", "Jerguš", "Jonáš", "Jozef", "Július", "Juraj", "Justín", "Kamil", "Karol", "Kazimír", "Kevin",
                "Klaudius", "Klement", "Koloman", "Konštantín", "Kornel", "Kristián", "Krištof", "Ladislav", "Leo", "Leonard", "Leopold", "Levoslav",
                "Ľubomír", "Ľubor", "Ľuboš", "Ľuboslav", "Ľudomil", "Ľudovít", "Lukáš", "Marcel", "Marek", "Marián", "Mário", "Marko", "Markus",
                "Maroš", "Martin", "Matej", "Matúš", "Max", "Maxim", "Maximilián", "Medard", "Metod", "Michael", "Michal", "Mikuláš", "Milan",
                "Miloš", "Miloslav", "Miroslav", "Mojmír", "Móric", "Nátan", "Nikolaj", "Nikolas", "Noel", "Norbert", "Oldrich", "Oleg", "Oliver",
                "Ondrej", "Oskár", "Oto", "Pankrác", "Patrik", "Pavol", "Peter", "Pravoslav", "Prokop", "Radomír", "Radoslav",
                "Radovan", "Radúz", "Rastislav", "René", "Richard", "Róbert", "Roland", "Roman", "Ronald", "Rudolf", "Samuel", "Sebastián", "Sergej",
                "Servác", "Severín", "Silvester", "Simon", "Šimon", "Slavomír", "Stanislav", "Štefan", "Svätopluk", "Svetozár", "Tadeáš", "Teo",
                "Teodor", "Tibor", "Tichomír", "Timon", "Timotej", "Tobias", "Tobiáš", "Tomáš", "Urban", "Václav", "Valentín",
                "Valér", "Vasil", "Vavrinec", "Vendelín", "Viktor", "Viliam", "Vincent", "Vít", "Víťazoslav", "Vladimír",
                "Vladislav", "Vlastimil", "Vojtech", "Vratislav", "Vratko", "Zdenko", "Zdeno", "Žigmund", "Zlatko", "Zoltán", "Zoran"
        };

        String[] girlsSurnames = {
                "Višňovská", "Hýroššová", "Trúchlyová", "Majtánová", "Trajčíková", "Beniačová", "Markušová", "Ševčíková", "Valachová", "Jonášová", "Tamášiová", "Kaňová", "Brezánová",
                "Baráneková", "Rajčanová", "Čičkánová", "Pilková", "Čepecová", "Šmehylová", "Držíková",
                "Jakubeková", "Deáková", "Kučerová", "Detková", "Kramerová", "Sopková", "Blažejová", "Ďurišová", "Hulejová", "Cibulková", "Trnovecová",
                "Mandová", "Molitorová", "Veliká", "Kadašová", "Kubicová", "Martinková", "Kemková", "Smiešková", "Valášeková", "Pokorná", "Chochulová", "Novosadová",
                "Šoltýsová", "Varinská", "Tomašecová", "Turská", "Púčeková", "Rakovská", "Hájniková", "Kmeťová", "Slotová", "Bednárová", "Tvrdá", "Cabuková", "Kočanová",
                "Palacková", "Plánková", "Slatinská", "Omastová", "Erdélyiová", "Kopecká", "Tichá", "Kuchareková", "Berníková", "Šípošová", "Sitárová", "Porubská",
                "Hudeková", "Černáková", "Levková", "Mrázová", "Gábrišová", "Ondrušeková", "Smolková", "Šmáriková", "Rudinská", "Krčmárová", "Horecká", "Hurtošová",
                "Hladíková", "Rybáriková", "Kováčiková", "Ponechalová", "Chládeková", "Vrábelová", "Lipovská", "Lemešová", "Velčicová", "Matušíková", "Korbelová", "Koláriková"
        };

        String[] boysSurnames = {
                "Višňovský", "Hýrošš", "Trúchly", "Majtán", "Trajčík", "Beniač", "Markuš", "Ševčík", "Valach", "Jonáš", "Tamáši", "Kaňa", "Brezány",
                "Baránek", "Rajčan", "Čičkán", "Pilka", "Čepec", "Šmehyl", "Držík",
                "Jakubek", "Deák", "Kučera", "Detko", "Kramer", "Sopko", "Blažej", "Ďuriš", "Hulej", "Cibulka", "Trnovec",
                "Manda", "Molitor", "Veliký", "Kadaš", "Kubica", "Martinko", "Kemka", "Smieško", "Valášek", "Pokorný", "Chochul", "Novosad",
                "Šoltýs", "Varinský", "Tomašec", "Turský", "Púček", "Rakovský", "Hájnik", "Kmeťo", "Slota", "Bednár", "Tvrdý", "Cabuk", "Kočan",
                "Palacka", "Plánka", "Slatinský", "Omasta", "Erdélyi", "Kopecký", "Tichý", "Kucharek", "Berník", "Šípoš", "Sitár", "Porubský",
                "Hudek", "Černák", "Levko", "Mráz", "Gábriš", "Ondrušek", "Smolka", "Šmárik", "Rudinský", "Krčmár", "Horecký", "Hurtoš",
                "Hladík", "Rybárik", "Kováčik", "Ponechal", "Chládek", "Vrábel", "Lipovský", "Lemeš", "Velčic", "Matušík", "Korbel", "Kolárik"
        };

        ArrayList<Person> pp = new ArrayList<>(countPeople);

        for (int i = 0; i < countPeople; i++) {
            int gender = (Math.random() > 0.5) ? 1 : 0;
            int day = (int)(Math.random() * 28);
            int month = 1 + (int)(Math.random() * 11);
            int year = 1000 + (int)(Math.random() * 1021);
            String fullDay = "";
            fullDay += (day > 9) ? day : "0" + day;
            String fullMonth = "";
            fullMonth += (gender == 1 ? (month + 50) : ((month > 9) ? month : "0" + month));
            String fullYear = "";
            fullYear += (year % 100 > 9) ? (year % 100) : "0" + (year % 100);
            String pomRc = fullYear + fullMonth + fullDay + (1000 + (int)(Math.random() * 9000));
            String name = gender == 1 ? girlsName[(int)(Math.random() * girlsName.length)] : boysName[(int)(Math.random() * boysName.length)];
            String surname = gender == 1 ? girlsSurnames[(int)(Math.random() * girlsSurnames.length)] : boysSurnames[(int)(Math.random() * boysSurnames.length)];
            Date birthday = new Date(year - 1900, month - 1, day, 0, 0, 0);

            Person p = new Person(name, surname, birthday, pomRc);
            this.people.add(p);
            pp.add(p);
            p.getPCR().getFileTree().clean();
        }

        ArrayList<Region> rr;
        if (regions.getCount() > 0) {
            rr = regions.getInOrderArrayL();
        } else {
            rr = new ArrayList<>(countRegions);
            for (int i = 0; i < countRegions; i++) {
                Region region = new Region(i);
                regions.add(region);
                rr.add(region);
                region.getPCR().getFileTree().clean();
            }
        }

        ArrayList<District> dd;
        if (districts.getCount() > 0) {
            dd = districts.getInOrderArrayL();
        } else {
            dd = new ArrayList<>(countDistricts);
            for (int i = 0; i < countDistricts; i++) {
                District district = new District(i);
                districts.add(district);
                dd.add(district);
                district.getPCR().getFileTree().clean();
            }
        }

        ArrayList<Workplace> ww;
        if (workplaces.getCount() > 0) {
            ww = workplaces.getInOrderArrayL();
        } else {
            ww = new ArrayList<>(countWorkplaces);
            for (int i = 0; i < countWorkplaces; i++) {
                Workplace workplace = new Workplace(i);
                workplaces.add(workplace);
                ww.add(workplace);
                workplace.getPCR().getFileTree().clean();
            }
        }

        for (int i = 0; i < countTests; i++) {
            Date date = new Date((int)(Math.random() * (122)), (int)(Math.random() * 11), (int)(1 + Math.random() * 28), (int)(Math.random() * 23), (int)(Math.random() * 59), 0);
            String code = UUID.randomUUID().toString();

            boolean result = Math.random() > 0.5;

            Person person = pp.get((int)(Math.random() * countPeople));
            Workplace workplace = ww.get((int)(Math.random() * countWorkplaces));
            District district = dd.get((int)(Math.random() * countDistricts));
            Region region = rr.get((int)(Math.random() * countRegions));

            PCR pcr = new PCR(date, code, person.getRC(), workplace.getId(), district.getId(), region.getId(), result, "notes");

            Long address = fileTests.add(pcr, new PCR());
            PCRtests.add(new PCR_date(date, address));
            PCRtestsId.add(new PCR_id(pcr.getId(), address));

            person.getPCR().add(new PCR_date(date, address));
            workplace.getPCR().add(new PCR_date(date, address));
            district.getPCR().add(new PCR_date(date, address));
            region.getPCR().add(new PCR_date(date, address));
        }
    }

    public void deleteAllData() {
        fileTests.clean();
        fileTests.saveData();
        PCRtests.clean();
        PCRtests.saveData();
        PCRtestsId.clean();
        PCRtestsId.saveData();
        people.clean();
        people.saveData();
        workplaces.clean();
        workplaces.saveData();
        districts.clean();
        districts.saveData();

        Arrays.stream(new File("files").listFiles()).forEach(File::delete);
        Arrays.stream(new File("outputffiles").listFiles()).forEach(File::delete);
    }

    //ULOHA 14
    public String writeRegions(String date, int countDays) {
        ArrayList<Region> arrayRegions = new ArrayList<>(regions.getCount());
        ArrayList<Integer> arrayCounts = new ArrayList<>(regions.getCount());

        Date dateTo;
        if (!date.equals("")) {
            String[] dateToStr = date.split("\\.");
            dateTo = new Date(Integer.parseInt(dateToStr[2]) - 1900, Integer.parseInt(dateToStr[1]) - 1, Integer.parseInt(dateToStr[0]), 0, 0, 0);
        } else {
            dateTo = new Date();
        }
        Date dateFrom = new Date();
        long time = dateTo.getTime() - (countDays * 86400000L);
        dateFrom.setTime(time);

        for (Region region : regions.getInOrderArrayL()) {
            int count = 0;

            //Region region = new Region(regionKey);
            BTree<PCR_date> PCRofRegions = region.getPCR().findInterval(new PCR_date(dateFrom), new PCR_date(dateTo));
            BTree<PCR_date> positivePCRofRegion = new BTree<>("files/positiveRegion", new PCR_date());
            positivePCRofRegion.clean();

            if (PCRofRegions != null) {
                Long nodeAddress = PCRofRegions.getFirstNode().getAddress();
                BNode<PCR_date> node = PCRofRegions.getFirstNode().getNode();
                BDataNode<PCR_date> dataNode;
                if (node != null) {
                    dataNode= new BDataNode<PCR_date>(node.getKeys().get(0), node, nodeAddress);
                    BDataNode<PCR_date> dataNodeBefore = new BDataNode<PCR_date>(node.getKeys().get(0), node, nodeAddress);
                    while (dataNode != null) {
                        PCR pcr = fileTests.getData(dataNode.getKey().getPCR(), new PCR());
                        if (!pcr.isResult()) {
                            dataNode = PCRofRegions.getInOrder(dataNode);
                        } else {
                            positivePCRofRegion.add(dataNode.getKey());
                            dataNode = PCRofRegions.getInOrder(dataNode);
                        }
                    }
                }
            }

            count = positivePCRofRegion.getCount();

            if (arrayCounts.size() == 0) {
                arrayCounts.add(0, count);
                arrayRegions.add(0, region);
            } else {
                for (int i = 0; i < arrayCounts.size(); i++) {
                    if (arrayCounts.get(i).compareTo(count) >= 0) {
                        arrayCounts.add(i, count);
                        arrayRegions.add(i, region);
                        i = arrayCounts.size();
                    } else if (i == arrayCounts.size() - 1) {
                        arrayCounts.add(i + 1, count);
                        arrayRegions.add(i + 1, region);
                        i++;
                    }
                }
            }

            /*int count = 0;

            TwoThreeTree<PCR> PCRofRegion = region.getPCR().findInterval(new PCR(dateFrom), new PCR(dateTo));
            TwoThreeTree<PCR> positivePCRofRegion = new TwoThreeTree<>();

            if (PCRofRegion != null) {
                TwoThreeNode<PCR> node = PCRofRegion.getFirstNode();
                TwoThreeDataNode<PCR> dataNode = new TwoThreeDataNode<PCR>(node.getKeys().get(0), node);
                TwoThreeDataNode<PCR> dataNodeBefore = new TwoThreeDataNode<PCR>(node.getKeys().get(0), node);
                while (dataNode != null) {
                    if (!dataNode.getKey().getResult()) {
                        PCRofRegion.delete(dataNode.getKey());
                        dataNode.setKey(dataNodeBefore.getKey());
                        dataNode.setNode(PCRofRegion.findNode(dataNodeBefore.getKey()));
                    }
                    dataNode = PCRofRegion.getInOrder(dataNode);
                }
                /*for (PCR pcr : PCRofRegion) {
                    if (pcr.getResult()) {
                        positivePCRofRegion.add(pcr);
                    }
                }
                count = PCRofRegions.getCount();
            } else {
                count = 0;
            }


            if (arrayCounts.size() == 0) {
                arrayCounts.add(0, count);
                arrayRegions.add(0, region);
            } else {
                for (int i = 0; i < arrayCounts.size(); i++) {
                    if (arrayCounts.get(i).compareTo(count) >= 0) {
                        arrayCounts.add(i, count);
                        arrayRegions.add(i, region);
                        i = arrayCounts.size();
                    } else if (i == arrayCounts.size() - 1) {
                        arrayCounts.add(i + 1, count);
                        arrayRegions.add(i + 1, region);
                        i++;
                    }
                }
            }*/
        }

        String text = "";
        for (int i = 0; i < arrayRegions.size(); i++) {
            text += arrayRegions.get(i).getId() + " \t" + arrayCounts.get(i) + "\n";
        }
        return text;
    }

    //ULOHA 13
    public String writeDistricts(String date, int countDays) {
        BTree<District> districts = this.districts;
        ArrayList<District> arrayDistricts = new ArrayList<>(districts.getCount());
        ArrayList<Integer> arrayCounts = new ArrayList<>(districts.getCount());

        Date dateTo;
        if (!date.equals("")) {
            String[] dateToStr = date.split("\\.");
            dateTo = new Date(Integer.parseInt(dateToStr[2]) - 1900, Integer.parseInt(dateToStr[1]) - 1, Integer.parseInt(dateToStr[0]), 0, 0, 0);
        } else {
            dateTo = new Date();
        }
        Date dateFrom = new Date();
        long time = dateTo.getTime() - (countDays * 86400000L);
        dateFrom.setTime(time);

        for (District district : districts.getInOrderArrayL()) {
            int count = 0;

            //District district = new District(districtKey);
            BTree<PCR_date> PCRofDistrict = district.getPCR().findInterval(new PCR_date(dateFrom), new PCR_date(dateTo));
            BTree<PCR_date> positivePCRofDistrict = new BTree<>("files/positiveDistrict", new PCR_date());
            positivePCRofDistrict.clean();

            if (PCRofDistrict != null) {
                Long nodeAddress = PCRofDistrict.getFirstNode().getAddress();
                BNode<PCR_date> node = PCRofDistrict.getFirstNode().getNode();
                BDataNode<PCR_date> dataNode;
                if (node != null) {
                    dataNode= new BDataNode<PCR_date>(node.getKeys().get(0), node, nodeAddress);
                    BDataNode<PCR_date> dataNodeBefore = new BDataNode<PCR_date>(node.getKeys().get(0), node, nodeAddress);
                    while (dataNode != null) {
                        PCR pcr = fileTests.getData(dataNode.getKey().getPCR(), new PCR());
                        if (!pcr.isResult()) {
                            dataNode = PCRofDistrict.getInOrder(dataNode);
                        } else {
                            positivePCRofDistrict.add(dataNode.getKey());
                            dataNode = PCRofDistrict.getInOrder(dataNode);
                        }
                    }
                }
            }

            count = positivePCRofDistrict.getCount();

            if (arrayCounts.size() == 0) {
                arrayCounts.add(0, count);
                arrayDistricts.add(0, district);
            } else {
                for (int i = 0; i < arrayCounts.size(); i++) {
                    if (arrayCounts.get(i).compareTo(count) >= 0) {
                        arrayCounts.add(i, count);
                        arrayDistricts.add(i, district);
                        i = arrayCounts.size();
                    } else if (i == arrayCounts.size() - 1) {
                        arrayCounts.add(i + 1, count);
                        arrayDistricts.add(i + 1, district);
                        i++;
                    }
                }
            }
        }

        String text = "";
        for (int i = 0; i < arrayDistricts.size(); i++) {
            text += arrayDistricts.get(i).getId() + " \t" + arrayCounts.get(i) + "\n";
        }
        return text;
    }

    //ULOHA 19
    public String deletePerson(String rc) {
        //Long personAddress = this.people.find(rc).getObjectAddress();
        Person person = this.people.find(new Person(rc)).getKey();
        if (person == null) {
            return "Osoba s daným rodným čislom sa nenašla.";
        }

        String text = "Osoba bola vymazaná.\n";
        text += "Rodné číslo: " + person.getRC() + "\n";
        text += "Meno: " + person.getName() + "\n";
        text += "Priezvisko: " + person.getSurname() + "\n";
        text += "Dátum narodenia: " + person.getBirthday().getDate() + "." + (person.getBirthday().getMonth() + 1) + "." + (person.getBirthday().getYear() + 1900) + "\n";

        ArrayList<PCR_date> arrayPerson = person.getPCR().getInOrderArrayL();
        if (arrayPerson != null) {
            for (PCR_date pcrDate : arrayPerson) {
                PCR pcr = fileTests.getData(pcrDate.getPCR(), new PCR());
                //PCR_id pcrId = this.PCRtestsId.find(new PCR_id(pcr.getId()));
                //PCR pcr1 = this.PCRtests.find(pcr);

                Workplace workplace = new Workplace(pcr.getWorkplace());
                workplace.getPCR().delete(new PCR_date(pcr.getDateTime()));
                District district = new District(pcr.getDistrict());
                district.getPCR().delete(new PCR_date(pcr.getDateTime()));
                Region region = new Region(pcr.getRegion());
                region.getPCR().delete(new PCR_date(pcr.getDateTime()));

                long pcrAddress = this.PCRtestsId.find(new PCR_id(pcr.getId())).getKey().getPCR();
                this.PCRtestsId.delete(new PCR_id(pcr.getId()));
                this.PCRtests.delete(new PCR_date(pcr.getDateTime()));
                this.fileTests.delete(pcrAddress, new PCR());
            }
        }
        this.people.delete(person);

        return text;
    }

    //ULOHA 18
    public String deletePCR(String idPCR) {
        BDataNode<PCR_id> dataNode = this.PCRtestsId.find(new PCR_id(idPCR));
        PCR pcr;
        long pcrAddress;
        if (dataNode == null) {
            return "Test PCR s daným id sa nenašiel.";
        } else {
            pcr = fileTests.getData(dataNode.getKey().getPCR(), new PCR());
            pcrAddress = dataNode.getKey().getPCR();
        }

        String text = "Test bol vymazany.\n";
        text += "Kód PCR: " + pcr.getId() + "\n";
        text += "Dátum a čas: " + pcr.getDateTime().getDate() + "." + (pcr.getDateTime().getMonth() + 1) + "." + (pcr.getDateTime().getYear() + 1900) + " " + pcr.getDateTime().getHours() + ":" + pcr.getDateTime().getMinutes() + "\n";

        Workplace workplace = new Workplace(pcr.getWorkplace());
        text += "Pracovisko: " + workplace.getId() + "\n";

        Region region = new Region(pcr.getRegion());
        text += "Kraj: " + region.getId() + "\n";

        District district = new District(pcr.getDistrict());
        text += "Okres: " + district.getId() + "\n";
        text += "Výsledok testu: " + pcr.isResult() + "\n";
        text += "Poznámka: " + pcr.getNotes() + "\n" + "\n";


        Person person = this.people.find(new Person(pcr.getPerson())).getKey();
        text += "Rodné číslo: " + person.getRC() + "\n";
        text += "Meno: " + person.getName() + "\n";
        text += "Priezvisko: " + person.getSurname() + "\n";
        text += "Dátum narodenia: " + person.getBirthday().getDate() + "." + (person.getBirthday().getMonth() + 1) + "." + (person.getBirthday().getYear() + 1900) + "\n";

        person.getPCR().delete(new PCR_date(pcr.getDateTime(), pcrAddress));
        workplace.getPCR().delete(new PCR_date(pcr.getDateTime(), pcrAddress));
        district.getPCR().delete(new PCR_date(pcr.getDateTime(), pcrAddress));
        region.getPCR().delete(new PCR_date(pcr.getDateTime(), pcrAddress));

        this.PCRtestsId.delete(new PCR_id(pcr.getId(), pcrAddress));
        this.PCRtests.delete(new PCR_date(pcr.getDateTime(), pcrAddress));
        this.fileTests.delete(pcrAddress, new PCR());

        return text;
    }

    //ULOHA 10, 11, 12
    public ArrayList<PCR> searchPeople(String idDistrict, String idRegion, String date, int spCountDaysPeople) {
        BTree<PCR_date> pomPcr = new BTree<PCR_date>("files/pomPCR", new PCR_date());
        try {
            pomPcr.getFileTree().getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<PCR_date> ResultPcr = new ArrayList<>();
        ArrayList<PCR> ResultPcr2 = new ArrayList<>();

        if (!idDistrict.equals("")) {
            //BDataNode dataNode = this.districts.find(Integer.parseInt(idDistrict));
            District district = new District(Integer.parseInt(idDistrict));
            if (district.getPCR().getCount() != 0) {
                pomPcr = district.getPCR();
                ResultPcr = pomPcr.getInOrderArrayL();
            } else {
                pomPcr = null;
            }
        }
        if (!idRegion.equals("")) {
            //BDataNode dataNode = this.regions.find(Integer.parseInt(idRegion));
            Region region = new Region(Integer.parseInt(idRegion));
            if (region.getPCR().getCount() != 0) {
                pomPcr = region.getPCR();
                ResultPcr = pomPcr.getInOrderArrayL();
            } else {
                pomPcr = null;
            }
        }
        if (ResultPcr == null) {
            return null;
        }

        BTree<PCR_date> prcTests = null;
        if (pomPcr == null) {
            return null;
        } else if (ResultPcr != null && ResultPcr.size() != 0) {
            prcTests = pomPcr;
        } else {
            prcTests = this.PCRtests;
        }

        Date dateTo = new Date();
        if (!date.equals("")) {
            String[] dateToStr = date.split("\\.");
            dateTo = new Date(Integer.parseInt(dateToStr[2]) - 1900, Integer.parseInt(dateToStr[1]) - 1, Integer.parseInt(dateToStr[0]), 0, 0, 0);
        }
        Date dateFrom = new Date();
        long time = dateTo.getTime() - (spCountDaysPeople * 86400000L);
        dateFrom.setTime(time);

        if (dateFrom.compareTo(dateTo) < 0) {
            BTree<PCR_date> tree = prcTests.findInterval(new PCR_date(dateFrom), new PCR_date(dateTo));
            if (tree != null) {
                ResultPcr = tree.getInOrderArrayL();
            } else {
                ResultPcr = null;
            }

            if (ResultPcr == null || ResultPcr.size() == 0) {
                pomPcr = null;
            }
        } else {
            pomPcr = null;
        }
        if (pomPcr != null && ResultPcr != null && ResultPcr.size() != 0) {
            for (PCR_date p : ResultPcr) {
                PCR pcr = fileTests.getData(p.getPCR(), new PCR());
                if (pcr.isResult()) {
                    ResultPcr2.add(pcr);
                }
            }
        }
        //ResultPcr = ResultPcr2;
        if (pomPcr != null && ResultPcr != null && ResultPcr2.size() != 0) {
            return ResultPcr2;
        } else {
            return null;
        }
    }

    //ULOHA 17
    public void addPerson(String name, String surname, int year, int month, int day, String rc) {
        Person person = new Person(name, surname, new Date(year - 1900, month - 1, day, 0, 0, 0), rc);
        if (this.people.find(new Person(person.getRC())) == null) {
            person.getPCR().getFileTree().clean();
            this.people.add(person);
            showMessageDialog(null, "Osoba s rodným číslo " + rc + " je vytvorená.");
        } else {
            showMessageDialog(null, "Osoba s rodným číslo " + rc + " už existuje.");
        }
    }

    //ULOHA 1
    public void addPCR(int year, int month, int day, int hour, int minute, String rc, String idWorkplace, String idDistrict, String idRegion, int result, String notes) {
        UUID uuid = UUID.randomUUID();

        Date dateTime = new Date((year - 1900), month - 1, day, hour, minute, 0);
        Person person = this.people.find(new Person(rc)).getKey();
        //Long workplaceAddress = this.workplaces.find(Integer.parseInt(idWorkplace)).getObjectAddress();
        Workplace workplace = new Workplace(Integer.parseInt(idWorkplace));
        //Long districtAddress = this.districts.find(Integer.parseInt(idDistrict)).getObjectAddress();
        District district = new District(Integer.parseInt(idDistrict));
        //Long regionAddress = this.regions.find(Integer.parseInt(idRegion)).getObjectAddress();
        Region region = new Region(Integer.parseInt(idRegion));

        PCR pcr = new PCR(dateTime, uuid.toString(), rc, Integer.parseInt(idWorkplace), Integer.parseInt(idDistrict), Integer.parseInt(idRegion), result == 1, notes);

        Long pcrAddress = fileTests.add(pcr, new PCR());
        this.PCRtests.add(new PCR_date(dateTime, pcrAddress));
        this.PCRtestsId.add(new PCR_id(pcr.getId(), pcrAddress));
        if (person == null) {
            showMessageDialog(null, "Osoba s rodným číslo " + rc + " neexistuje! Vytvorte najskôr osobu.");
            return;
        }
        System.out.println(uuid);
        person.getPCR().add(new PCR_date(pcr.getDateTime(), pcrAddress));
        workplace.getPCR().add(new PCR_date(pcr.getDateTime(), pcrAddress));
        district.getPCR().add(new PCR_date(pcr.getDateTime(), pcrAddress));
        region.getPCR().add(new PCR_date(pcr.getDateTime(), pcrAddress));

        showMessageDialog(null, "PCR test s id " + uuid + " je vytvorený.");
    }

    //ULOHA 2, 16
    public ArrayList<PCR> findPCRById(String idPcr, String rc) {
        BTree<PCR_date> pomPcr = new BTree<PCR_date>("files/pomPCR", new PCR_date());
        pomPcr.clean();

        //ArrayList<Long> ResultPcr = new ArrayList<>();
        ArrayList<PCR> ResultPcr2 = new ArrayList<>();

        BDataNode<PCR_id> dataNode = this.PCRtestsId.find(new PCR_id(idPcr));
        Long pcrAddress = dataNode != null ? dataNode.getKey().getPCR() : null;
        PCR pcr = pcrAddress != null ? fileTests.getData(pcrAddress, new PCR()) : null;
        if (pcr != null) {
            if (!Objects.equals(rc, "")) {
                if (this.people.find(new Person(pcr.getPerson())).getKey().getRC().compareTo(rc) == 0) {
                    pomPcr.add(new PCR_date(pcr.getDateTime(), pcrAddress));
                    ResultPcr2.add(pcr);
                } else {
                    pomPcr = null;
                }
            } else {
                pomPcr.add(new PCR_date(pcr.getDateTime(), pcrAddress));
                ResultPcr2.add(pcr);
            }
        } else {
            pomPcr = null;
        }
        if (pomPcr != null) {
            return ResultPcr2;
        } else {
            return null;
        }
    }

    //ULOHA 4, 5
    public ArrayList<PCR> findPCRByDistrict(int idDistrict, String dateF, String dateT, int result) {
        BTree<PCR_date> pomPcr = new BTree<PCR_date>("files/pomPCR", new PCR_date());
        try {
            pomPcr.getFileTree().getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<PCR_date> ResultPcr = new ArrayList<>();
        ArrayList<PCR> ResultPcr2 = new ArrayList<>();

        //Long districtAddress = this.districts.find(idDistrict).getObjectAddress();
        District district = new District(idDistrict);
        if (district != null) {
            if (!Objects.equals(dateF, "") && !Objects.equals(dateT, "")) {
                String[] dateFromStr = dateF.split("\\.");
                String[] dateToStr = dateT.split("\\.");
                Date dateFrom = new Date(Integer.parseInt(dateFromStr[2]) - 1900, Integer.parseInt(dateFromStr[1]) - 1, Integer.parseInt(dateFromStr[0]), 0, 0, 0);
                Date dateTo = new Date(Integer.parseInt(dateToStr[2]) - 1900, Integer.parseInt(dateToStr[1]) - 1, Integer.parseInt(dateToStr[0]), 0, 0, 0);

                if (dateFrom.compareTo(dateTo) < 0) {
                    BTree<PCR_date> tree = district.getPCR().findInterval(new PCR_date(dateFrom), new PCR_date(dateTo));
                    if (tree == null) {
                        ResultPcr = null;
                    } else {
                        ResultPcr = tree.getInOrderArrayL();
                        for (PCR_date p : ResultPcr) {
                            ResultPcr2.add(fileTests.getData(p.getPCR(), new PCR()));
                        }
                    }
                    if (ResultPcr == null || ResultPcr.size() == 0) {
                        pomPcr = null;
                    }
                } else {
                    pomPcr = null;
                }
            } else {
                pomPcr = district.getPCR();
                ResultPcr = district.getPCR().getInOrderArrayL();
                for (PCR_date p : ResultPcr) {
                    ResultPcr2.add(fileTests.getData(p.getPCR(), new PCR()));
                }
            }

            boolean pom;
            if (/*!tfResult.getText().isEmpty()*/result != -1 && pomPcr != null) {
                ResultPcr2.clear();
                for (PCR_date p : ResultPcr) {
                    PCR pcr = fileTests.getData(p.getPCR(), new PCR());
                    if (pcr.isResult() == (Objects.equals(result, 1))) {
                        ResultPcr2.add(pcr);
                    }
                }
                //ResultPcr = ResultPcr2;
            }
        } else {
            pomPcr = null;
        }
        if (pomPcr != null && ResultPcr != null) {
            return ResultPcr2;
        } else {
            return null;
        }
    }

    //ULOHA 15
    public ArrayList<PCR> findPCRByWorplace(int idWorkplace, String dateF, String dateT) {
        BTree<PCR_date> pomPcr = new BTree<PCR_date>("files/pomPCR", new PCR_date());
        try {
            pomPcr.getFileTree().getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<PCR_date> ResultPcr = new ArrayList<>();
        ArrayList<PCR> ResultPcr2 = new ArrayList<>();

        //Long workplaceAddress = this.workplaces.find(idWorkplace).getObjectAddress();
        Workplace workplace = new Workplace(idWorkplace);
        if (workplace != null) {
            if (!Objects.equals(dateF, "") && !Objects.equals(dateT, "")) {
                String[] dateFromStr = dateF.split("\\.");
                String[] dateToStr = dateT.split("\\.");
                Date dateFrom = new Date(Integer.parseInt(dateFromStr[2]) - 1900, Integer.parseInt(dateFromStr[1]) - 1, Integer.parseInt(dateFromStr[0]), 0, 0, 0);
                Date dateTo = new Date(Integer.parseInt(dateToStr[2]) - 1900, Integer.parseInt(dateToStr[1]) - 1, Integer.parseInt(dateToStr[0]), 0, 0, 0);

                if (dateFrom.compareTo(dateTo) < 0) {
                    BTree<PCR_date> tree = workplace.getPCR().findInterval(new PCR_date(dateFrom), new PCR_date(dateTo));
                    if (tree == null) {
                        ResultPcr = null;
                    } else {
                        ResultPcr = tree.getInOrderArrayL();
                    }
                    if (ResultPcr.size() == 0) {
                        pomPcr = null;
                    }
                } else {
                    pomPcr = null;
                }
            } else {
                pomPcr = workplace.getPCR();
                ResultPcr = workplace.getPCR().getInOrderArrayL();
            }
        } else {
            pomPcr = null;
        }
        if (pomPcr != null && ResultPcr != null) {
            for (PCR_date p : ResultPcr) {
                ResultPcr2.add(fileTests.getData(p.getPCR(), new PCR()));
            }
            return ResultPcr2;
        } else {
            return null;
        }
    }

    //ULOHA 6, 7
    public ArrayList<PCR> findPCRByRegion(int idRegion, String dateF, String dateT, int result) {
        BTree<PCR_date> pomPcr = new BTree<PCR_date>("files/pomPCR", new PCR_date());
        try {
            pomPcr.getFileTree().getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<PCR_date> ResultPcr = new ArrayList<>();
        ArrayList<PCR> ResultPcr2 = new ArrayList<>();

        //Long regionAddress = this.regions.find(idRegion).getObjectAddress();
        Region region = new Region(idRegion);
        if (region != null) {
            if (!dateF.equals("") && !dateT.equals("")) {
                String[] dateFromStr = dateF.split("\\.");
                String[] dateToStr = dateT.split("\\.");
                Date dateFrom = new Date(Integer.parseInt(dateFromStr[2]) - 1900, Integer.parseInt(dateFromStr[1]) - 1, Integer.parseInt(dateFromStr[0]), 0, 0, 0);
                Date dateTo = new Date(Integer.parseInt(dateToStr[2]) - 1900, Integer.parseInt(dateToStr[1]) - 1, Integer.parseInt(dateToStr[0]), 0, 0, 0);

                if (dateFrom.compareTo(dateTo) < 0) {
                    BTree<PCR_date> tree = region.getPCR().findInterval(new PCR_date(dateFrom), new PCR_date(dateTo));
                    if (tree == null) {
                        ResultPcr = null;
                    } else {
                        ResultPcr = tree.getInOrderArrayL();
                        for (PCR_date p : ResultPcr) {
                            ResultPcr2.add(fileTests.getData(p.getPCR(), new PCR()));
                        }
                    }
                    if (ResultPcr != null && ResultPcr.size() == 0) {
                        pomPcr = null;
                    }
                } else {
                    pomPcr = null;
                }
            } else {
                pomPcr = region.getPCR();
                ResultPcr = region.getPCR().getInOrderArrayL();
                for (PCR_date p : ResultPcr) {
                    ResultPcr2.add(fileTests.getData(p.getPCR(), new PCR()));
                }
            }

            if (result != -1) {
                if (ResultPcr != null) {
                    ResultPcr2.clear();
                    for (PCR_date p : ResultPcr) {
                        PCR pcr = fileTests.getData(p.getPCR(), new PCR());
                        if (pcr.isResult() == (result == 1)) {
                            ResultPcr2.add(pcr);
                        }
                    }
                }
                //ResultPcr = ResultPcr2;
            }
        } else {
            pomPcr = null;
        }
        if (pomPcr != null && ResultPcr != null) {
            return ResultPcr2;
        } else {
            return null;
        }
    }

    //ULOHA 3
    public ArrayList<PCR> findPCRByRC(String rc, String dateF, String dateT) {
        BTree<PCR_date> pomPcr = new BTree<PCR_date>("files/pomPCR", new PCR_date());
        try {
            pomPcr.getFileTree().getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<PCR_date> ResultPcr = new ArrayList<>();
        ArrayList<PCR> ResultPcr2 = new ArrayList<>();

        //Long personAddress = this.people.find(rc).getObjectAddress();
        Person person = this.people.find(new Person(rc)).getKey();
        if (person != null) {
            if (!dateF.equals("")) {
                String[] dateFromStr = dateF.split("\\.");
                String[] dateToStr = dateT.split("\\.");
                Date dateFrom = new Date(Integer.parseInt(dateFromStr[2]) - 1900, Integer.parseInt(dateFromStr[1]) - 1, Integer.parseInt(dateFromStr[0]), 0, 0, 0);
                Date dateTo = new Date(Integer.parseInt(dateToStr[2]) - 1900, Integer.parseInt(dateToStr[1]) - 1, Integer.parseInt(dateToStr[0]), 0, 0, 0);

                if (dateFrom.compareTo(dateTo) < 0) {
                    BTree<PCR_date> tree = person.getPCR().findInterval(new PCR_date(dateFrom), new PCR_date(dateTo));
                    if (tree == null) {
                        ResultPcr = null;
                    } else {
                        ResultPcr = tree.getInOrderArrayL();
                    }
                    if (ResultPcr.size() == 0) {
                        pomPcr = null;
                    }
                } else {
                    pomPcr = null;
                }
            } else {
                pomPcr = person.getPCR();
                ResultPcr = person.getPCR().getInOrderArrayL();
            }
        } else {
            pomPcr = null;
        }
        if (pomPcr != null && ResultPcr != null) {
            for (PCR_date p : ResultPcr) {
                ResultPcr2.add(fileTests.getData(p.getPCR(), new PCR()));
            }
            return ResultPcr2;
        } else {
            return null;
        }
    }

    //ULOHA 8, 9
    public ArrayList<PCR> findPCRByDate(String dateF, String dateT, int result) {
        BTree<PCR_date> pomPcr = new BTree<PCR_date>("files/pomPCR", new PCR_date());
        try {
            pomPcr.getFileTree().getFile().setLength(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<PCR_date> ResultPcr = new ArrayList<>();
        ArrayList<PCR> ResultPcr2 = new ArrayList<>();

        String[] dateFromStr = dateF.split("\\.");
        String[] dateToStr = dateT.split("\\.");
        Date dateFrom = new Date(Integer.parseInt(dateFromStr[2]) - 1900, Integer.parseInt(dateFromStr[1]) - 1, Integer.parseInt(dateFromStr[0]), 0, 0, 0);
        Date dateTo = new Date(Integer.parseInt(dateToStr[2]) - 1900, Integer.parseInt(dateToStr[1]) - 1, Integer.parseInt(dateToStr[0]), 0, 0, 0);

        if (dateFrom.compareTo(dateTo) < 0) {
            BTree<PCR_date> tree = this.PCRtests.findInterval(new PCR_date(dateFrom), new PCR_date(dateTo));
            if (tree == null) {
                ResultPcr = null;
            } else {
                ResultPcr = tree.getInOrderArrayL();
                for (PCR_date p : ResultPcr) {
                    ResultPcr2.add(fileTests.getData(p.getPCR(), new PCR()));
                }
            }
            if (ResultPcr == null || ResultPcr.size() == 0) {
                pomPcr = null;
            } else {
                if (result != -1) {
                    ResultPcr2.clear();
                    for (PCR_date p : ResultPcr) {
                        PCR pcr = fileTests.getData(p.getPCR(), new PCR());
                        if (pcr.isResult() == (result == 1)) {
                            ResultPcr2.add(pcr);
                        }
                    }
                    //ResultPcr = ResultPcr2;
                }
            }
        } else {
            pomPcr = null;
        }
        if (pomPcr != null && ResultPcr != null) {
            return ResultPcr2;
        } else {
            return null;
        }
    }

    public String getFileTest() {
        return this.getFileTests().myToString(new PCR());
    }

    public String getFilePeople() {
        return this.getPeople().getFileTree().myToString(new BNode<>(new Person()));
    }

    public String getFileTestDate() {
        return this.getPCRtests().getFileTree().myToString(new BNode<>(new PCR_date()));
    }

    public String getFileTestId() {
        return this.getPCRtestsId().getFileTree().myToString(new BNode<>(new PCR_id()));
    }





    public BTree<PCR_date> getPCRtests() {
        return PCRtests;
    }

    public BTree<PCR_id> getPCRtestsId() {
        return PCRtestsId;
    }

    public BTree<Person> getPeople() {
        return people;
    }

    public UnsortedFile<PCR> getFileTests() {
        return fileTests;
    }
}
