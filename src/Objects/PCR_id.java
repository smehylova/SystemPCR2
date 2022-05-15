package Objects;

import structures.IRecord;

import java.io.*;

public class PCR_id implements IRecord<PCR_id>, Comparable<PCR_id> {
    private String id;
    private static final int ID_LENGTH = 36;
    public long PCR;

    public PCR_id() {
        this.id = "";
        this.PCR = -1;
    }

    public PCR_id(String id_) {
        this.id = id_;
    }

    public PCR_id(String id_, long pcr_) {
        this.id = id_;
        this.PCR = pcr_;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(arrayOut);

        try {
            dataOut.writeChars(this.id);
            for (int i = 0; i < (ID_LENGTH - this.id.length()); i++) {
                dataOut.writeChar('_');
            }

            dataOut.writeLong(this.PCR);

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
            this.id = "";
            for (int i = 0; i < ID_LENGTH; i++) {
                char c = dataIn.readChar();
                if (c == '_') {
                    break;
                }
                this.id += c;
            }

            this.PCR = dataIn.readLong();

        } catch (IOException e) {
            System.out.println(e);;
        }
    }

    @Override
    public int getSize() {
        return ID_LENGTH * Character.BYTES + Long.BYTES;
    }

    @Override
    public boolean myEquals(PCR_id data) {
        return this.id.compareTo(data.getId()) == 0;
    }

    @Override
    public PCR_id createClass() {
        return new PCR_id();
    }

    @Override
    public String myToString() {
        return this.id + ": " + this.PCR;
    }

    public String getId() {
        return id;
    }

    public long getPCR() {
        return PCR;
    }

    @Override
    public int compareTo(PCR_id o) {
        return this.id.compareTo(o.getId());
    }
}
