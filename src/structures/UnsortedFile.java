package structures;

import com.sun.jdi.ClassType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Objects;
import java.util.PriorityQueue;

public class UnsortedFile<T extends IRecord<T>> {
    private RandomAccessFile file;
    String nameFile;
    private PriorityQueue<Long> emptyAddresses;
    private long lastDataAddress;
    private int dataLength;

    public UnsortedFile(String fileName) {
        try {
            this.file = new RandomAccessFile(fileName, "rw");
            this.nameFile = fileName;
            this.emptyAddresses = new PriorityQueue<>();
            this.loadData();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public void setFile(RandomAccessFile file) {
        this.file = file;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public void setEmptyAddresses(PriorityQueue<Long> emptyAddresses) {
        this.emptyAddresses = emptyAddresses;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public long getLastDataAddress() {
        return lastDataAddress;
    }

    public void setLastDataAddress(long lastDataAddress) {
        this.lastDataAddress = lastDataAddress;
    }

    public RandomAccessFile getFile() {
        return file;
    }

    public PriorityQueue<Long> getEmptyAddresses() {
        return emptyAddresses;
    }

    //public T find(int addressData) {

    //}

    public T getData(long address, T typeT) {
        try {
            if (address >= this.file.length()) {
                //System.out.println("Adresa neexistuje, lebo sa hlada na konci suboru!");
                return null;
            }

            byte[] arrayData = new byte[typeT.getSize()];
            file.seek(address);
            file.read(arrayData);

            String str = new String(arrayData);

            if (str.charAt(1) == '-') {
                return null;
            }

            T findClass = (T) typeT.createClass();
            findClass.fromByteArray(arrayData);
            return findClass;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setData(Long address, T data) {
        try {
            file.seek(address);
            file.write(data.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long add(T data, T typeT) {
        this.dataLength = data.getSize();
        try {
            if (emptyAddresses.isEmpty()) {
                long address = file.length();

                file.seek(address);
                file.write(data.toByteArray());

                this.lastDataAddress = address;
                if (this.getData(address, typeT) == null) {
                    System.out.println("Zaznam sa vlozil nespravne.");
                }
                this.saveData();
                return address;
            } else {
                long address = emptyAddresses.poll();

                file.seek(address);
                file.write(data.toByteArray());

                if (this.getData(address, typeT) == null) {
                    System.out.println("Zaznam sa vlozil nespravne. Pri vyberani z prazdnych");
                }
                this.saveData();
                return address;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Do suboru sa nepodarilo zapisat zaznam");
        return -1;
    }

    public void delete(long address, T typeT) {
        if (address == this.lastDataAddress) {
            try {
                do {
                    this.emptyAddresses.remove(this.lastDataAddress);

                    System.out.println(this.lastDataAddress);
                    System.out.println(this.file.length());
                    file.setLength(this.lastDataAddress);
                    System.out.println(this.lastDataAddress);
                    System.out.println(this.file.length());

                    this.lastDataAddress -= typeT.getSize();

                    if (this.lastDataAddress > 0) {
                        file.seek(this.lastDataAddress);
                    }
                } while (this.lastDataAddress >= 0 && this.emptyAddresses.contains(this.lastDataAddress));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.emptyAddresses.add(address);
            //nahradit zaznam v subore
            try {
                file.seek(address);
                file.write(((T) typeT.createClass()).toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.saveData();
    }

    public void clean() {
        try {
            this.file.setLength(0);
            this.dataLength = -1;
            this.emptyAddresses = new PriorityQueue<>();
            this.lastDataAddress = -1;
            this.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        File f = new File("output" + this.nameFile + ".txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(f);

            //StringBuilder text = new StringBuilder();
            writer.write(this.getLastDataAddress() + "\n");
            writer.write(this.getDataLength() + "\n");
            String emptyA = "";
            int count = this.getEmptyAddresses().size();
            for (int i = 0; i < count; i++) {
                emptyA += this.getEmptyAddresses().poll() + ",";
            }
            writer.write(emptyA + "\n");
            writer.close();
            this.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        File file = new File("output" + this.nameFile + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line == null) {
                return;
            }
            this.setLastDataAddress(Integer.parseInt(line));
            this.setDataLength(Integer.parseInt(br.readLine()));
            String[] emptyD = br.readLine().split(",");
            for (int i = 0; i < emptyD.length; i++) {
                if (!Objects.equals(emptyD[i], "")) {
                    this.getEmptyAddresses().add(Long.valueOf(emptyD[i]));
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        //return null;
    }

    public String myToString(T typeT) {
        try {
            long i = 0;
            String text = "";
            System.out.println(this.file.length());
            while (i < this.file.length()) {
                if (!this.emptyAddresses.contains(i)) {
                    text += "\nAdresa: " + i + "\n";
                    T pomText = this.getData(i, typeT);
                    text += pomText == null ? "ziadny zaznam" : pomText.myToString() + "\n";
                } else {
                    text += "\nAdresa: " + i + "\n";
                    text += "Žiadny záznam." + "\n";
                }
                i += typeT.getSize();
            }
            text += this.emptyAddresses + "\t" + this.lastDataAddress;
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
