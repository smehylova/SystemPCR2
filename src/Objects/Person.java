package Objects;//import structures.TwoThreeTree;

import structures.BNode;
import structures.BTree;
import structures.IRecord;

import java.io.*;
import java.util.Date;
import java.util.Objects;

public class Person implements IRecord<Person>, Comparable<Person> {
    private String name;
    private static final int NAME_LENGTH = 15;
    private String surname;
    private static final int SURNAME_LENGTH = 20;
    private Date birthday;
    private String RC;
    private static final int RC_LENGTH = 10;
    private boolean isValid = true;
    private BTree<PCR_date> PCR;

    public Person() {
        this.RC = "";
        this.name = "";
        this.surname = "";
        this.birthday = new Date();
    }

    public Person(String RC_) {
        this.RC = RC_;
        this.PCR = new BTree<PCR_date>("files/" + this.RC + "Person", new PCR_date());
    }

    public Person(String name_, String surname_, Date birthday_, String RC_) {
        this.name = name_;
        this.surname = surname_;
        this.birthday = birthday_;
        this.RC = RC_;
        this.PCR = new BTree<PCR_date>("files/" + this.RC + "Person", new PCR_date());
    }

    @Override
    public int compareTo(Person o) {
        return this.RC.compareTo(o.getRC());
    }

    public BTree<PCR_date> getPCR() {
        return PCR;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getRC() {
        return RC;
    }

    @Override
    public boolean myEquals(Person data) {
        return Objects.equals(this.RC, data.getRC())
                && Objects.equals(this.name, data.getName())
                && Objects.equals(this.surname, data.getSurname());
    }

    @Override
    public Person createClass() {
        return new Person();
    }

    @Override
    public String myToString() {
        return this.name + " " + this.surname + ": " + this.getRC();
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(arrayOut);

        try {
            dataOut.writeChars(this.RC);
            for (int i = 0; i < (RC_LENGTH - this.RC.length()); i++) {
                dataOut.writeChar('-');
            }
            dataOut.writeChars(this.name);
            for (int i = 0; i < (NAME_LENGTH - this.name.length()); i++) {
                dataOut.writeChar('-');
            }
            dataOut.writeChars(this.surname);
            for (int i = 0; i < (SURNAME_LENGTH - this.surname.length()); i++) {
                dataOut.writeChar('-');
            }
            dataOut.writeInt(this.birthday.getDate());
            dataOut.writeInt(this.birthday.getMonth());
            dataOut.writeInt(this.birthday.getYear());

            return arrayOut.toByteArray();
        } catch (IOException e) {
            System.out.println(e);;
        }
        return new byte[0];
    }

    @Override
    public void fromByteArray(byte[] array) {
        ByteArrayInputStream arrayIn = new ByteArrayInputStream(array);
        DataInputStream dataIn = new DataInputStream(arrayIn);

        try {
            for (int i = 0; i < RC_LENGTH; i++) {
                this.RC += dataIn.readChar();
                if (i == 0 && this.RC.equals("-")) {
                    this.isValid = false;
                    return;
                }
            }

            for (int i = 0; i < NAME_LENGTH; i++) {
                this.name += dataIn.readChar();
            }
            this.name = this.name.split("-")[0];

            for (int i = 0; i < SURNAME_LENGTH; i++) {
                this.surname += dataIn.readChar();
            }
            this.surname = this.surname.split("-")[0];

            this.birthday.setDate(dataIn.readInt());
            this.birthday.setMonth(dataIn.readInt());
            this.birthday.setYear(dataIn.readInt());

            this.PCR = new BTree<PCR_date>("files/" + this.RC + "Person", new PCR_date());
        } catch (IOException e) {
            System.out.println(e);;
        }
    }

    @Override
    public int getSize() {
        return Character.BYTES * (NAME_LENGTH + SURNAME_LENGTH + RC_LENGTH) + Integer.BYTES * 3;
    }
}
