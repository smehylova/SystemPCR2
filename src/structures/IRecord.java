package structures;

//Spracovane podla cvicenia
public interface IRecord<T> {
    public byte[] toByteArray();
    public void fromByteArray(byte[] array);
    public int getSize();
    public boolean myEquals(T data);
    public T createClass();
    public String myToString();
}
