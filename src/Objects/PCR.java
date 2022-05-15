package Objects;

import structures.IRecord;

import java.io.*;
import java.util.Date;

public class PCR implements IRecord<PCR> {
    private Date dateTime;
    private String id;
    private static final int ID_LENGTH = 36;
    private String person;
    private static final int PERSON_LENGTH = 10;
    private Integer workplace;
    private Integer district;
    private Integer region;
    private boolean result;
    private String notes;
    private static final int NOTES_LENGTH = 20;

    public PCR() {
        this.id = "";
        this.notes = "";
        this.person = "";
        this.workplace = -1;
        this.district = -1;
        this.region = -1;
    }

    public PCR(Date date_) {
        this.dateTime = date_;
        this.id = "0";
    }

    public PCR(Date dateTime_, String id_, String person_, Integer workplace_, Integer district_,Integer region_,boolean result_, String notes_) {
        this.dateTime = dateTime_;
        this.id = id_;
        this.person = person_;
        this.workplace = workplace_;
        this.district = district_;
        this.region = region_;
        this.result = result_;
        this.notes = notes_;
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

            dataOut.writeLong(this.dateTime == null ? -1 : this.dateTime.getTime());

            dataOut.writeChars(this.person);
            for (int i = 0; i < PERSON_LENGTH - this.person.length(); i++) {
                dataOut.writeChar('-');
            }
            dataOut.writeInt(this.workplace);
            dataOut.writeInt(this.district);
            dataOut.writeInt(this.region);

            dataOut.writeInt(this.result ? 1 : 0);
            dataOut.writeChars(this.notes);
            for (int i = 0; i < (NOTES_LENGTH - this.notes.length()); i++) {
                dataOut.writeChar('_');
            }

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
                if (c != '_') {
                    this.id += c;
                }
            }

            this.dateTime = new Date(dataIn.readLong());

            this.person = "";
            for (int i = 0; i < PERSON_LENGTH; i++) {
                char c = dataIn.readChar();
                if (c != '-') {
                    this.person += c;
                }
            }

            this.workplace = dataIn.readInt();
            this.district = dataIn.readInt();
            this.region = dataIn.readInt();

            this.result = dataIn.readInt() == 1;

            this.notes = "";
            for (int i = 0; i < NOTES_LENGTH; i++) {
                char c = dataIn.readChar();
                if (c == '_') {
                    break;
                }
                this.notes += c;
            }

        } catch (IOException e) {
            System.out.println(e);;
        }
    }

    @Override
    public int getSize() {
        return (ID_LENGTH + NOTES_LENGTH + PERSON_LENGTH) * Character.BYTES + Long.BYTES + 4 + 3 * Integer.BYTES;
    }

    @Override
    public boolean myEquals(PCR data) {
        return this.dateTime == data.getDateTime();
    }

    @Override
    public PCR createClass() {
        return new PCR();
    }

    @Override
    public String myToString() {
        return this.id + ": " +
                this.dateTime.getDate() + "." + (this.dateTime.getMonth() + 1) + "." + (this.dateTime.getYear() + 1900) + " " + this.dateTime.getHours() + ":" + this.dateTime.getMinutes() + " " +
                this.result + " " + this.notes;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getId() {
        return id;
    }

    public String getPerson() {
        return person;
    }

    public Integer getWorkplace() {
        return workplace;
    }

    public Integer getDistrict() {
        return district;
    }

    public Integer getRegion() {
        return region;
    }

    public boolean isResult() {
        return result;
    }

    public String getNotes() {
        return notes;
    }
}
