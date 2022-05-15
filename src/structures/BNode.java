package structures;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class BNode<K extends  Comparable<K> & IRecord<K>> implements  IRecord<BNode<K>> {
    private Long leftSon;
    private Long rightSon;
    private Long middleSon;
    private Long pomSon;
    private Long father;
    private List<K> keys;
    private int lengthKeys;

    private K typeK;

    public BNode(K typeK) {
        this.lengthKeys = 0;
        this.keys = new ArrayList<K>(3);
        this.typeK = typeK;
    }

    public void addItem(K key) {
        if (this.lengthKeys == 0) {
            this.keys.add(0, key);
        }
        for (int i = 0; i < this.lengthKeys; i++) {
            if (this.keys.get(i).compareTo(key) > 0) {
                this.keys.add(i, key);
                i = this.lengthKeys;
            } else if (i == this.lengthKeys - 1) {
                this.keys.add(i + 1, key);
            }
        }
        this.lengthKeys++;
    }

    public void deleteItem(K key) {
        for (int i = 0; i < lengthKeys; i++) {
            if (this.keys.get(i).compareTo(key) == 0) {
                lengthKeys--;
                this.keys.remove(i);
            }
        }
    }

    public void setKeys(List<K> keys) {
        this.keys = keys;
    }

    public Long getLeftSon() {
        return leftSon;
    }

    public void setLeftSon(Long leftSon) {
        this.leftSon = leftSon;
    }

    public Long getRightSon() {
        return rightSon;
    }

    public void setRightSon(Long rightSon) {
        this.rightSon = rightSon;
    }

    public Long getMiddleSon() {
        return middleSon;
    }

    public void setMiddleSon(Long middleSon) {
        this.middleSon = middleSon;
    }

    public Long getPomSon() {
        return pomSon;
    }

    public void setPomSon(Long pomSon) {
        this.pomSon = pomSon;
    }

    public Long getFather() {
        return father;
    }

    public void setFather(Long father) {
        this.father = father;
    }

    public List<K> getKeys() {
        return keys;
    }

    public int getLengthKeys() {
        return lengthKeys;
    }

    @Override
    public byte[] toByteArray() {
        ByteArrayOutputStream arrayOut = new ByteArrayOutputStream();
        DataOutputStream dataOut = new DataOutputStream(arrayOut);

        try {
            dataOut.writeInt(this.lengthKeys);
            dataOut.writeLong(this.father == null ? -1 : this.father);
            dataOut.writeLong(this.leftSon == null ? -1 : this.leftSon);
            dataOut.writeLong(this.middleSon == null ? -1 : this.middleSon);
            dataOut.writeLong(this.rightSon == null ? -1 : this.rightSon);
            dataOut.writeLong(this.pomSon == null ? -1 : this.pomSon);

            for (int i = 0; i < 3; i++) {
                if (i < this.lengthKeys) {
                    dataOut.write(this.keys.get(i).toByteArray());
                } else {
                    dataOut.write(new byte[this.typeK.getSize()]);
                }
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
            this.lengthKeys = dataIn.readInt();

            var pom = dataIn.readLong();
            this.father = pom == -1 ? null : pom;
            pom = dataIn.readLong();
            this.leftSon = pom == -1 ? null : pom;
            pom = dataIn.readLong();
            this.middleSon = pom == -1 ? null : pom;
            pom = dataIn.readLong();
            this.rightSon = pom == -1 ? null : pom;
            pom = dataIn.readLong();
            this.pomSon = pom == -1 ? null : pom;

            for (int i = 0; i < this.lengthKeys; i++) {
                byte[] arrayData = new byte[typeK.getSize()];
                dataIn.read(arrayData);

                K findClass = typeK.createClass();
                findClass.fromByteArray(arrayData);
                this.keys.add(findClass);
            }

        } catch (IOException e) {
            System.out.println(e);;
        }
    }

    @Override
    public int getSize() {
        return 5 * Long.BYTES + Integer.BYTES + 3 * this.typeK.getSize();
    }

    @Override
    public boolean myEquals(BNode<K> data) {
        return this.keys.get(0).compareTo(data.getKeys().get(0)) == 0;
    }

    @Override
    public BNode<K> createClass() {
        return new BNode<>(typeK);
    }

    @Override
    public String myToString() {
        String text = this.lengthKeys + "\t";
        text += this.leftSon + "\t" + this.middleSon + "\t" + this.rightSon + "\t" + this.father + "\n";
        for (int i = 0; i < this.lengthKeys; i++) {
            text += this.keys.get(i).myToString() + "\t";
        }
        text += "\n";
        return text;
    }
}
