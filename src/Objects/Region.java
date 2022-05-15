package Objects;

import structures.BTree;
import structures.IRecord;

import java.io.*;
import java.util.Date;

public class Region implements IRecord<Region>, Comparable<Region> {
    private int id;
    private BTree<PCR_date> PCR;

    public int getId() {
        return id;
    }

    public BTree<PCR_date> getPCR() {
        return PCR;
    }

    public Region(int id_) {
        this.id = id_;
        this.PCR = new BTree<PCR_date>("files/" + this.id + "RegionPCR", new PCR_date());
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

        this.PCR = new BTree<PCR_date>("files/" + this.id + "RegionPCR", new PCR_date());
    }

    @Override
    public int getSize() {
        return Integer.BYTES;
    }

    @Override
    public boolean myEquals(Region data) {
        return this.id == data.getId();
    }

    @Override
    public Region createClass() {
        return new Region(-1);
    }

    @Override
    public String myToString() {
        return this.id + "";
    }

    @Override
    public int compareTo(Region o) {
        return Integer.compare(this.id, o.getId());
    }
}
