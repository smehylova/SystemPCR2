package Objects;//import structures.TwoThreeTree;

import structures.BTree;
import structures.IRecord;

import java.io.*;
import java.util.Date;
import java.util.Objects;

public class TestObject implements IRecord<TestObject>, Comparable<TestObject> {
    private Integer id;
    private String name;
    private static final int NAME_LENGTH = 20;
    private String surname;
    private static final int SURNAME_LENGTH = 20;
    private double dd = 0.0;

    public TestObject() {
        this.id = -1;
        this.name = "";
        this.surname = "";
    }

    public TestObject(int id_) {
        this.id = id_;
    }

    public TestObject(int id_, String name_, String surname_) {
        this.id = id_;
        this.name = name_;
        this.surname = surname_;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean myEquals(TestObject data) {
        return Objects.equals(this.id, data.getId())
                && Objects.equals(this.name, data.getName())
                && Objects.equals(this.surname, data.getSurname());
    }

    @Override
    public TestObject createClass() {
        return new TestObject();
    }

    @Override
    public String myToString() {
        return this.name + " " + this.surname + ": " + this.getId();
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(arrayOut);

        try {
            dataOut.writeInt(this.id);
            dataOut.writeChars(this.name);
            for (int i = 0; i < (NAME_LENGTH - this.name.length()); i++) {
                dataOut.writeChar('-');
            }
            dataOut.writeChars(this.surname);
            for (int i = 0; i < (SURNAME_LENGTH - this.surname.length()); i++) {
                dataOut.writeChar('-');
            }
            dataOut.writeDouble(this.dd);

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
            this.id = dataIn.readInt();

            for (int i = 0; i < NAME_LENGTH; i++) {
                this.name += dataIn.readChar();
            }
            this.name = this.name.split("-")[0];

            for (int i = 0; i < SURNAME_LENGTH; i++) {
                this.surname += dataIn.readChar();
            }
            this.surname = this.surname.split("-")[0];

            this.dd = dataIn.readDouble();
        } catch (IOException e) {
            System.out.println(e);;
        }
    }

    @Override
    public int getSize() {
        return Character.BYTES * (NAME_LENGTH + SURNAME_LENGTH) + Integer.BYTES + Double.BYTES;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(TestObject o) {
        return Integer.compare(this.id, o.getId());
    }
}
