package structures;

public class BDataNode<K extends Comparable<K> & IRecord<K>> {
    private K key;
    //private Long objectAddress;
    private BNode<K> node;
    private Long address;


    public BDataNode(K key_, BNode<K> node_, Long address_) {
        this.key = key_;
        this.address = address_;
        //this.objectAddress = objectAddress_;
        this.node = node_;
    }

    /*public Long getObjectAddress() {
        return objectAddress;
    }

    public void setObjectAddress(Long objectAddress) {
        this.objectAddress = objectAddress;
    }*/

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }

    public BNode<K> getNode() {
        return node;
    }

    public void setNode(BNode<K> node) {
        this.node = node;
    }
}
