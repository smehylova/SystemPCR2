import Objects.*;
import structures.BNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class formMain extends JFrame{
    private JPanel panelMain;
    private JTabbedPane tpGroup;
    private JPanel panelTests;
    private JPanel panelPeople;
    private JPanel panelAddPCR;
    private JPanel panelAddPerson;
    private JTextField tfDateFrom;
    private JTextField tfIDpcr;
    private JTextField tfRC;
    private JTextField tfDateTo;
    private JButton btnSearch;
    private JTextArea textAreaTests;
    private JTextField tfIdPcr;
    private JLabel lbRC;
    private JLabel lbRegion;
    private JLabel lbDistrict;
    private JLabel lbWorkplace;
    private JLabel lbDate;
    private JLabel lbTime;
    private JLabel lbResult;
    private JLabel lbNotes;
    private JTextField tfNotesAddTest;
    private JButton btnAddPCR;
    private JLabel lbName;
    private JTextField tfNameAddPerson;
    private JLabel lbSurname;
    private JTextField tfSurnameAddPerson;
    private JLabel lbBirthday;
    private JTextField tfRCAddTest;
    private JTextField tfDistrictAddTest;
    private JTextField tfRegionAddTest;
    private JTextField tfWorkplaceAddTest;
    private JTextField tfResultAddTest;
    private JButton btnAddPeson;
    private JTextField tfRCAddPerson;
    private JTextField tfDistrictPeople;
    private JTextField tfRegionPeople;
    private JTextField tfDatePeople;
    private JTextArea textAreaPeople;
    private JButton btnSearchPeople;
    private JPanel panelDeletePCR;
    private JTextField tfIdPCRDeletePCR;
    private JTextArea textAreaDeletePCR;
    private JButton btnDeletePCR;
    private JPanel panelDeletePerson;
    private JTextField tfRCDeletePerson;
    private JTextArea textAreaDeletePerson;
    private JButton btnDeletePerson;
    private JPanel panelDistricts;
    private JPanel panelRegions;
    private JTextField tfCountDaysDistricts;
    private JTextArea textAreaDistricts;
    private JTextField tfCountDaysRegions;
    private JTextArea textAreaRegions;
    private JButton btnDistricts;
    private JButton btnRegions;
    private JTextField tfDateDistricts;
    private JTextField tfDateRegions;
    private JPanel panelGenerate;
    private JSpinner spCountPeopleGenerate;
    private JSpinner spCountTestsGenerate;
    private JTextArea textAreaGenerate;
    private JButton btnGenerate;
    private JButton btnDeleteDate;
    private JButton btnClose;
    private JComboBox cbResult;
    private JSpinner spCountDaysPeople;
    private JSpinner spDayAddTest;
    private JSpinner spMonthAddTest;
    private JSpinner spYearAddTest;
    private JSpinner spHourAddTest;
    private JSpinner spMinuteAddTest;
    private JComboBox cbResultAddTest;
    private JSpinner spDayAddPerson;
    private JSpinner spMonthAddPerson;
    private JSpinner spYearsAddPerson;
    private JSpinner spCountDaysDistrict;
    private JSpinner spCountDaysRegions;
    private JTextField tfCountTests;
    private JSpinner spDistrict;
    private JSpinner spRegion;
    private JSpinner spWorkplace;
    private JTextField tfCountPeople;
    private JPanel panelWrite;
    private JTextArea textAreaWrite;
    private JButton btnWritePcr;
    private JButton btnWritePeople;
    private JButton btnWriteDistricts;
    private JButton btnWriteId;
    private JButton btnWriteDate;

    public formMain(App app) {
        setContentPane(panelMain);
        setTitle("Zoznam PCR testov");
        setSize(1285, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        if (app.getPCRtests().getCount() != 0) {
            ArrayList<PCR_date> arrayAddresses = app.getPCRtests().getInOrderArrayL();
            ArrayList<PCR> arrayPCR = new ArrayList<>(arrayAddresses.size());
            for (PCR_date pcr : arrayAddresses) {
                arrayPCR.add(app.getFileTests().getData(pcr.getPCR(), new PCR()));
            }

            if (arrayPCR != null) {
                setZoznamPCR(arrayPCR, app);
            }
        }

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TwoThreeTree<PCR> pomPcr = new TwoThreeTree<>();
                //ArrayList<PCR> ResultPcr = new ArrayList<>();
                //ArrayList<PCR> ResultPcr2 = new ArrayList<>();

                //ULOHA 2, 16
                if (!tfIDpcr.getText().isEmpty()) {
                    ArrayList<PCR> array = app.findPCRById(tfIDpcr.getText(), tfRC.getText());
                    if (array == null) {
                        textAreaTests.setText("Nenašiel sa PCR test vyhovujúci podmienkam.");
                    } else {
                        setZoznamPCR(array, app);
                    }
                    return;
                }

                //ULOHA 4, 5
                if ((int)spDistrict.getValue() > 0) {
                    ArrayList<PCR> array = app.findPCRByDistrict((int)spDistrict.getValue(), tfDateFrom.getText(), tfDateTo.getText(), cbResult.getSelectedIndex() == 0 ? -1 : (cbResult.getSelectedIndex() == 1 ? 1 : 0));
                    if (array == null) {
                        textAreaTests.setText("Nenašiel sa PCR test vyhovujúci podmienkam.");
                    } else {
                        setZoznamPCR(array, app);
                    }
                    return;
                }

                //ULOHA 15
                if ((int)spWorkplace.getValue() > 0) {
                    ArrayList<PCR> array = app.findPCRByWorplace((int)spWorkplace.getValue(), tfDateFrom.getText(), tfDateTo.getText());
                    if (array == null) {
                        textAreaTests.setText("Nenašiel sa PCR test vyhovujúci podmienkam.");
                    } else {
                        setZoznamPCR(array, app);
                    }
                    return;
                }

                //ULOHA 6, 7
                if ((int)spRegion.getValue() > 0) {
                    ArrayList<PCR> array = app.findPCRByRegion((int)spRegion.getValue(), tfDateFrom.getText(), tfDateTo.getText(), cbResult.getSelectedIndex() == 0 ? -1 : (cbResult.getSelectedIndex() == 1 ? 1 : 0));
                    if (array == null) {
                        textAreaTests.setText("Nenašiel sa PCR test vyhovujúci podmienkam.");
                    } else {
                        setZoznamPCR(array, app);
                    }
                    return;
                }

                //ULOHA 3
                if (!tfRC.getText().isEmpty()) {
                    ArrayList<PCR> array = app.findPCRByRC(tfRC.getText(), tfDateFrom.getText(), tfDateTo.getText());
                    if (array == null) {
                        textAreaTests.setText("Nenašiel sa PCR test vyhovujúci podmienkam.");
                    } else {
                        setZoznamPCR(array, app);
                    }
                    return;
                }

                //ULOHA 8, 9
                if (!tfDateFrom.getText().isEmpty()) {
                    ArrayList<PCR> array = app.findPCRByDate(tfDateFrom.getText(), tfDateTo.getText(), cbResult.getSelectedIndex() == 0 ? -1 : (cbResult.getSelectedIndex() == 1 ? 1 : 0));
                    if (array == null) {
                        textAreaTests.setText("Nenašiel sa PCR test vyhovujúci podmienkam.");
                    } else {
                        setZoznamPCR(array, app);
                    }
                    return;
                }

                ArrayList<PCR_date> aAddresses = app.getPCRtests().getInOrderArrayL();
                ArrayList<PCR> a = new ArrayList<>(aAddresses.size());
                for (PCR_date pcr : aAddresses) {
                    a.add(app.getFileTests().getData(pcr.getPCR(), new PCR()));
                }

                if (a != null) {
                    setZoznamPCR(a, app);
                } else {
                    textAreaTests.setText("");
                    tfCountTests.setText("0");
                }
            }
        });

        //ULOHA 1
        btnAddPCR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.addPCR((int)spYearAddTest.getValue(), (int)spMonthAddTest.getValue(), (int)spDayAddTest.getValue(), (int)spHourAddTest.getValue(), (int)spMinuteAddTest.getValue(), tfRCAddTest.getText(), tfWorkplaceAddTest.getText(), tfDistrictAddTest.getText(), tfRegionAddTest.getText(), cbResultAddTest.getSelectedIndex() == 0 ? -1 : (cbResultAddTest.getSelectedIndex() == 1 ? 1 : 0), tfNotesAddTest.getText());
            }
        });

        //ULOHA 17
        btnAddPeson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.addPerson(tfNameAddPerson.getText(), tfSurnameAddPerson.getText(), (int)spYearsAddPerson.getValue(), (int)spMonthAddPerson.getValue(), (int)spDayAddPerson.getValue(), tfRCAddPerson.getText());
            }
        });

        //ULOHA 10, 11, 12
        btnSearchPeople.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<PCR> array = app.searchPeople(tfDistrictPeople.getText(), tfRegionPeople.getText(), tfDatePeople.getText(), (int)spCountDaysPeople.getValue());
                if (array == null) {
                    textAreaPeople.setText("Nenašiel sa PCR test vyhovujúci podmienkam.");
                } else {
                    setZoznamPeople(array, app);
                }
            }
        });

        //ULOHA 18
        btnDeletePCR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaDeletePCR.setText(app.deletePCR(tfIdPCRDeletePCR.getText()));
            }
        });

        //ULOHA 19
        btnDeletePerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaDeletePerson.setText(app.deletePerson(tfRCDeletePerson.getText()));
            }
        });

        //ULOHA 13
        btnDistricts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaDistricts.setText(app.writeDistricts(tfDateDistricts.getText(), (int)spCountDaysDistrict.getValue()));
            }
        });

        //ULOHA 14
        btnRegions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textAreaRegions.setText(app.writeRegions(tfDateRegions.getText(), (int)spCountDaysRegions.getValue()));
            }
        });

        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //try {
                    app.generateData(10, 10, 10, (Integer)spCountPeopleGenerate.getValue(), (Integer)spCountTestsGenerate.getValue());
                //} catch (IOException ex) {
                //    System.out.println(ex);
                //}
                textAreaGenerate.setText("Generovanie sa dokocilo.\nBolo vygenerovaných " + (Integer)spCountPeopleGenerate.getValue() + " osôb a " + (Integer)spCountTestsGenerate.getValue() + " testov.");
            }
        });

        btnDeleteDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.deleteAllData();
                textAreaGenerate.setText("Data boli odstránené.");
            }
        });

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        btnWritePcr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = app.getFileTest();
                textAreaWrite.setText(text);
            }
        });
        btnWritePeople.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = app.getFilePeople();
                textAreaWrite.setText(text);
            }
        });
        btnWriteDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = app.getFileTestDate();
                textAreaWrite.setText(text);
            }
        });
        btnWriteId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = app.getFileTestId();
                textAreaWrite.setText(text);
            }
        });
    }

    public void setZoznamPCR(ArrayList<PCR> arrayPCR, App app) {
        long startTime = System.nanoTime();
        tfCountTests.setText("" + arrayPCR.size());
        textAreaTests.setText("***********************************************************************************************************************************************\n");
        for (int i = 0; i < arrayPCR.size(); i++) {
            PCR pcr = arrayPCR.get(i);
            Person person = app.getPeople().find(new Person(pcr.getPerson())).getKey();
            District district = new District(arrayPCR.get(i).getDistrict());
            Region region = new Region(arrayPCR.get(i).getRegion());
            Workplace workplace = new Workplace(arrayPCR.get(i).getWorkplace());

            textAreaTests.append("Kod PCR:" + pcr.getId() + '\n');
            textAreaTests.append("Rodne cislo: " + person.getRC() + "\t\t Okres: " + district.getId() + "\t\t Datum: " + pcr.getDateTime().getDate() + "." + (pcr.getDateTime().getMonth() + 1) + "." + (pcr.getDateTime().getYear() + 1900) + "\t\t Vysledok: " + (pcr.isResult() ? "POZITÍVNY ++" : "NEGATÍVNY --") + '\n');
            textAreaTests.append("Meno: " + person.getName() + (person.getName().length() < 7 ? "\t" : "") + "\t\t Kraj: " + region.getId() + "\t\t Čas: " + pcr.getDateTime().getHours() + ":" + pcr.getDateTime().getMinutes() + '\n');
            textAreaTests.append("Priezvisko: " + person.getSurname() + "\t\t Pracovisko: " + workplace.getId() + '\n');
            textAreaTests.append("Dátum narodenia: " + person.getBirthday().getDate() + "." + (person.getBirthday().getMonth() + 1) + "." + (person.getBirthday().getYear() + 1900) + '\n');
            textAreaTests.append("***********************************************************************************************************************************************\n");
        }
        //textAreaTests.setText(zoznam);
        System.out.println(System.nanoTime() - startTime);
    }

    public void setZoznamPeople(ArrayList<PCR> arrayPeople, App app) {
        tfCountPeople.setText("" + arrayPeople.size());
        textAreaPeople.setText("***********************************************************************************************************************************************\n");
        for (int i = 0; i < arrayPeople.size(); i++) {
            PCR pcr = arrayPeople.get(i);
            Person person = app.getPeople().find(new Person(pcr.getPerson())).getKey();
            District district = new District(arrayPeople.get(i).getDistrict());
            Region region = new Region(arrayPeople.get(i).getRegion());
            Workplace workplace = new Workplace(arrayPeople.get(i).getWorkplace());
            textAreaPeople.append("Kod PCR:" + pcr.getId() + '\n');
            textAreaPeople.append("Rodne cislo: " + person.getRC() + "\t\t Okres: " + district.getId() + "\t\t Datum: " + pcr.getDateTime().getDate() + "." + (pcr.getDateTime().getMonth() + 1) + "." + (pcr.getDateTime().getYear() + 1900) + "\t\t Vysledok: " + (pcr.isResult() ? "POZITÍVNY ++" : "NEGATÍVNY --") + '\n');
            textAreaPeople.append("Meno: " + person.getName() + (person.getName().length() < 7 ? "\t" : "") + "\t\t Kraj: " + region.getId() + "\t\t Čas: " + pcr.getDateTime().getHours() + ":" + pcr.getDateTime().getMinutes() + '\n');
            textAreaPeople.append("Priezvisko: " + person.getSurname() + "\t\t Pracovisko: " + workplace.getId() + '\n');
            textAreaPeople.append("Dátum narodenia: " + person.getBirthday().getDate() + "." + (person.getBirthday().getMonth() + 1) + "." + (person.getBirthday().getYear() + 1900) + '\n');
            textAreaPeople.append("***********************************************************************************************************************************************\n");
        }
        //textAreaPeople.setText(zoznam);
    }
}
