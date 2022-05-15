package Objects;

import structures.IRecord;

import java.io.*;
import java.util.Date;

public class PCR_date implements IRecord<PCR_date>, Comparable<PCR_date> {
    private Date dateTime;
    public Long PCR;

    public PCR_date() {
        this.PCR = (long) -1;
    }

    public PCR_date(Date dateTime_) {
        this.dateTime = dateTime_;
    }

    public PCR_date(Date dateTime_, long pcr_) {
        this.dateTime = dateTime_;
        this.PCR = pcr_;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(arrayOut);

        try {
            dataOut.writeInt(dateTime.getYear());
            dataOut.writeInt(dateTime.getMonth());
            dataOut.writeInt(dateTime.getDate());
            dataOut.writeInt(dateTime.getHours());
            dataOut.writeInt(dateTime.getMinutes());

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
            this.dateTime = new Date(dataIn.readInt(), dataIn.readInt(), dataIn.readInt(), dataIn.readInt(), dataIn.readInt(), 0);

            this.PCR = dataIn.readLong();

        } catch (IOException e) {
            System.out.println(e);;
        }
    }

    @Override
    public int getSize() {
        return 5 * Integer.BYTES + Long.BYTES;
    }

    @Override
    public boolean myEquals(PCR_date data) {
        return this.dateTime.compareTo(data.getDateTime()) == 0;
    }

    @Override
    public PCR_date createClass() {
        return new PCR_date();
    }

    @Override
    public String myToString() {
        return this.dateTime.getDate() + "." + (this.dateTime.getMonth() + 1) + "." + (this.dateTime.getYear() + 1900) + ": " + this.PCR;
    }

    public long getPCR() {
        return PCR;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public int compareTo(PCR_date o) {
        return this.dateTime.compareTo(o.getDateTime());
    }
}
