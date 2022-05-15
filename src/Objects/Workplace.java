package Objects;

import structures.BTree;
import structures.IRecord;

import java.io.*;
import java.util.Date;

public class Workplace implements IRecord<Workplace>, Comparable<Workplace> {
    private Integer id;
    private BTree<PCR_date> PCR;

    public Workplace(int id_) {
        this.id = id_;
        this.PCR = new BTree<PCR_date>("files/" + id_ + "WorkplacePCR", new PCR_date());
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(arrayOut);

        try {
            dataOut.writeInt(this.id);

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
        } catch (IOException e) {
            System.out.println(e);;
        }

        this.PCR = new BTree<>("files/" + this.id + "WorkplacePCR", new PCR_date());
    }

    @Override
    public int getSize() {
        return Integer.BYTES;
    }

    @Override
    public boolean myEquals(Workplace data) {
        return this.id == data.getId();
    }

    @Override
    public Workplace createClass() {
        return new Workplace(-1);
    }

    public int getId() {
        return id;
    }

    public BTree<PCR_date> getPCR() {
        return PCR;
    }

    @Override
    public String myToString() {
        return this.id + "";
    }

    @Override
    public int compareTo(Workplace o) {
        return Integer.compare(this.id, o.getId());
    }
}
