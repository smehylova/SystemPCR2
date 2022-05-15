package structures;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class BTree<K extends Comparable<K> & IRecord<K>> {
    private Long root;
    private UnsortedFile<BNode<K>> fileTree;
    private int count;
    private String nameFile;
    private K typeK;

    public BTree(String nameFile, K typeK_) {
        this.count = 0;
        this.fileTree = new UnsortedFile<BNode<K>>("f" + nameFile);
        this.nameFile = nameFile;
        this.typeK = typeK_;
        this.loadData();
    }

    public UnsortedFile<BNode<K>> getFileTree() {
        return fileTree;
    }

    public void setFileTree(UnsortedFile<BNode<K>> fileTree) {
        this.fileTree = fileTree;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRoot(Long root) {
        this.root = root;
        if (this.root == null) {
            this.count = 0;
        }
    }

    public Long getRoot() { return root; }

    //vrati pocet prvkov v strome
    public int getCount() {
        return count;
    }

    //vrati hodnotu boolean, ci je node listom
    public boolean isLeaf(BNode<K> leaf) { return leaf.getRightSon() == null && leaf.getLeftSon() == null; }

    //vrati hodnotu boolean, ci je node korenom stromu
    public boolean isRoot(BNode<K> root) { return root.getFather() == null; }

    //zistuje ktory syn je aktualnz node svojho otca (lavy-1, stredny-0, pravy--1)
    public int getSonPosition(BNode<K> actualNode, Long address) {
        if (!isRoot(actualNode)) {
            Long adr = actualNode.getFather();
            BNode<K> dad = fileTree.getData(adr, new BNode<>(typeK));

            if (Objects.equals(dad.getLeftSon(), address)) {
                return 1;//lavy syn
            } else if (Objects.equals(dad.getMiddleSon(), address)) {
                return  0;//stredny syn
            } else if (Objects.equals(dad.getRightSon(), address)) {
                return -1;//pravy syn
            }
        }
        return -2;
    }

    //vytvorené podľa prednášky a vlastných poznámok
    public void add(K key) {
        //kontrola, ci strom uz dany prvok neuchovava
        if (this.find(key) != null) {
            System.out.println("Strom uz dany prvok obsahuje");
            return;
        }
        count++;

        //najdenie listu stromu, do ktoreho ideme vkladat novy kluc
        BDataNode<K> dataNode = this.findLeaf(key);

        //ak je strom prazdny, prida prvok a koncime
        if (dataNode == null) {
            BNode<K> node = new BNode<>(key);
            node.addItem(key);
            this.root = fileTree.add(node, new BNode<>(key));
            this.saveData();
            return;
        }
        Long leafAddress = dataNode.getAddress();
        BNode<K> leaf = dataNode.getNode();

        //pridanie prvku
        leaf.addItem(key);
        fileTree.setData(leafAddress, leaf);

        while (true) {
            //ak ma po pridani node 2 kluce tak koncime
            if (leaf.getLengthKeys() == 2) {
                this.saveData();
                return;
            }

            //zistime, ktory syn je u svojho otca
            int son = this.getSonPosition(leaf, leafAddress);

            K middleK = leaf.getKeys().get(1);
            K maxK = leaf.getKeys().get(2);
            //Long middleA = leaf.getAddresses().get(1);
            //Long maxA = leaf.getAddresses().get(2);

            leaf.deleteItem(middleK);
            leaf.deleteItem(maxK);
            fileTree.setData(leafAddress, leaf);

            BNode<K> maxL = new BNode<>(key);
            maxL.addItem(maxK);
            Long maxLAddress = fileTree.add(maxL, new BNode<>(key));

            if (isLeaf(leaf)) {
                if (isRoot(leaf)) {
                    // NEMA SYNOV A NEMA OTCA
                    BNode<K> newRoot = new BNode<>(key);
                    newRoot.addItem(middleK);

                    newRoot.setLeftSon(leafAddress);
                    newRoot.setRightSon(maxLAddress);
                    this.root = fileTree.add(newRoot, new BNode<>(key));

                    leaf.setFather(this.root);
                    fileTree.setData(leafAddress, leaf);
                    maxL.setFather(this.root);
                    fileTree.setData(maxLAddress, maxL);
                    this.saveData();
                    return;
                } else {// NEMA SYNOV A MA OTCA
                    Long dadAddress = leaf.getFather();
                    BNode<K> dad = fileTree.getData(leaf.getFather(), new BNode<>(key));

                    dad.addItem(middleK);

                    if (dad.getLengthKeys() == 2) {//otec je 3-vrchol
                        if (son == 1) {
                            dad.setMiddleSon(maxLAddress);
                        } else {
                            dad.setMiddleSon(leafAddress);
                            dad.setRightSon(maxLAddress);
                        }
                        fileTree.setData(dadAddress, dad);

                        BNode<K> dadRight = fileTree.getData(dad.getRightSon(), new BNode<>(key));
                        dadRight.setFather(leaf.getFather());
                        fileTree.setData(dad.getRightSon(), dadRight);
                        maxL.setFather(leaf.getFather());
                        fileTree.setData(maxLAddress, maxL);
                        leaf.setFather(leaf.getFather());
                        fileTree.setData(leafAddress, leaf);

                        this.saveData();
                        return;
                    } else {//otec je 2-vrchol
                        dad.setPomSon(maxLAddress);
                        fileTree.setData(dadAddress, dad);

                        BNode<K> dadPom = fileTree.getData(dad.getPomSon(), new BNode<>(key));
                        dadPom.setFather(leaf.getFather());
                        fileTree.setData(dad.getPomSon(), dadPom);
                        maxL.setFather(dadAddress);
                        fileTree.setData(maxLAddress, maxL);

                        leaf = dad;
                        leafAddress = dadAddress;
                    }
                }
            } else {
                if (isRoot(leaf)) {
                    // MA SYNOV A NEMA OTCA
                    BNode<K> newRoot = new BNode<>(key);
                    newRoot.addItem(middleK);

                    BNode<K> pom = fileTree.getData(leaf.getPomSon(), new BNode<>(key));
                    if (pom.getKeys().get(0).compareTo(maxL.getKeys().get(0)) > 0) {
                        maxL.setRightSon(leaf.getPomSon());
                        maxL.setLeftSon(leaf.getRightSon());
                        leaf.setRightSon(leaf.getMiddleSon());
                        //fileTree.setData(maxLAddress, maxL);
                        //fileTree.setData(leafAddress, leaf);
                    } else {
                        maxL.setRightSon(leaf.getRightSon());
                        //Long middleAddress = leaf.getMiddleSon();
                        BNode<K> middle = fileTree.getData(leaf.getMiddleSon(), new BNode<>(key));
                        if (pom.getKeys().get(0).compareTo(middle.getKeys().get(0)) > 0) {
                            maxL.setLeftSon(leaf.getPomSon());
                            leaf.setRightSon(leaf.getMiddleSon());
                        } else {
                            maxL.setLeftSon(leaf.getMiddleSon());
                            if (pom.getKeys().get(0).compareTo(leaf.getKeys().get(0)) > 0) {
                                leaf.setRightSon(leaf.getPomSon());
                            } else {
                                leaf.setRightSon(leaf.getLeftSon());
                                leaf.setLeftSon(leaf.getPomSon());
                            }
                        }
                        //fileTree.setData(maxLAddress, maxL);
                    }
                    leaf.setMiddleSon(null);
                    leaf.setPomSon(null);
                    //fileTree.setData(leafAddress, leaf);

                    newRoot.setLeftSon(leafAddress);
                    newRoot.setRightSon(maxLAddress);
                    this.root = fileTree.add(newRoot, new BNode<>(key));

                    leaf.setFather(this.root);
                    maxL.setFather(this.root);
                    fileTree.setData(leafAddress, leaf);
                    fileTree.setData(maxLAddress, maxL);

                    BNode<K> leftLeft = fileTree.getData(leaf.getLeftSon(), new BNode<>(key));
                    BNode<K> leftRight = fileTree.getData(leaf.getRightSon(), new BNode<>(key));
                    BNode<K> rightLeft = fileTree.getData(maxL.getLeftSon(), new BNode<>(key));
                    BNode<K> rightRight = fileTree.getData(maxL.getRightSon(), new BNode<>(key));
                    leftLeft.setFather(newRoot.getLeftSon());
                    leftRight.setFather(newRoot.getLeftSon());
                    rightLeft.setFather(newRoot.getRightSon());
                    rightRight.setFather(newRoot.getRightSon());
                    fileTree.setData(leaf.getLeftSon(), leftLeft);
                    fileTree.setData(leaf.getRightSon(), leftRight);
                    fileTree.setData(maxL.getLeftSon(), rightLeft);
                    fileTree.setData(maxL.getRightSon(), rightRight);

                    this.saveData();
                    return;
                } else {
                    // MA SYNOV A MA OTCA
                    Long dadAddress = leaf.getFather();
                    BNode<K> dad = fileTree.getData(leaf.getFather(), new BNode<>(key));
                    dad.addItem(middleK);

                    Long pomAddress = leaf.getPomSon();
                    BNode<K> pom = fileTree.getData(leaf.getPomSon(), new BNode<>(key));
                    if (dad.getLengthKeys() == 2) {//otec je 3-vrchol
                        leaf.setPomSon(null);
                        if (pom.getKeys().get(0).compareTo(maxL.getKeys().get(0)) > 0) {
                            maxL.setRightSon(pomAddress);
                            maxL.setLeftSon(leaf.getRightSon());
                            leaf.setRightSon(leaf.getMiddleSon());
                        } else {
                            maxL.setRightSon(leaf.getRightSon());
                            BNode<K> middle = fileTree.getData(leaf.getMiddleSon(), new BNode<>(key));
                            if (pom.getKeys().get(0).compareTo(middle.getKeys().get(0)) > 0) {
                                maxL.setLeftSon(pomAddress);
                                leaf.setRightSon(leaf.getMiddleSon());
                            } else {
                                maxL.setLeftSon(leaf.getMiddleSon());
                                if (pom.getKeys().get(0).compareTo(leaf.getKeys().get(0)) > 0) {
                                    leaf.setRightSon(pomAddress);
                                } else {
                                    leaf.setRightSon(leaf.getLeftSon());
                                    leaf.setLeftSon(pomAddress);
                                }
                            }
                        }
                        leaf.setMiddleSon(null);

                        if (son == 1) {
                            dad.setMiddleSon(maxLAddress);
                        } else {
                            dad.setMiddleSon(leafAddress);
                            dad.setRightSon(maxLAddress);
                        }
                        fileTree.setData(maxLAddress, maxL);
                        fileTree.setData(leafAddress, leaf);
                        fileTree.setData(dadAddress, dad);

                        //BNode<K> dadLeft = fileTree.getData(dad.getLeftSon(), new BNode<>());
                        //BNode<K> dadRight = fileTree.getData(dad.getRightSon(), new BNode<>());
                        ///BNode<K> dadMiddle = fileTree.getData(dad.getMiddleSon(), new BNode<>());
                        //dadLeft.setFather(leaf.getFather());
                        maxL.setFather(leaf.getFather());
                        //leaf.setFather(leaf.getFather());
                        //fileTree.setData(dad.getLeftSon(), dadLeft);
                        fileTree.setData(maxLAddress, maxL);
                        fileTree.setData(leafAddress, leaf);

                        //BNode<K> dadLeftLeft = fileTree.getData(dadLeft.getLeftSon(), new BNode<>());
                        //BNode<K> dadLeftRight = fileTree.getData(dadLeft.getRightSon(), new BNode<>());
                        BNode<K> dadRightLeft = fileTree.getData(maxL.getLeftSon(), new BNode<>(key));
                        BNode<K> dadRightRight = fileTree.getData(maxL.getRightSon(), new BNode<>(key));
                        //BNode<K> dadMiddleLeft = fileTree.getData(leaf.getLeftSon(), new BNode<>());
                        //BNode<K> dadMiddleRight = fileTree.getData(leaf.getRightSon(), new BNode<>());
                        //dadLeftLeft.setFather(dad.getLeftSon());
                        //dadLeftRight.setFather(dad.getLeftSon());
                        dadRightLeft.setFather(maxLAddress);
                        dadRightRight.setFather(maxLAddress);
                        //dadMiddleLeft.setFather(dad.getMiddleSon());
                        //dadMiddleRight.setFather(dad.getMiddleSon());
                        //fileTree.setData(dadLeft.getLeftSon(), dadLeftLeft);
                        //fileTree.setData(dadLeft.getRightSon(), dadLeftRight);
                        fileTree.setData(maxL.getLeftSon(), dadRightLeft);
                        fileTree.setData(maxL.getRightSon(), dadRightRight);
                        //fileTree.setData(leaf.getLeftSon(), dadMiddleLeft);
                        //fileTree.setData(leaf.getRightSon(), dadMiddleRight);

                        this.saveData();
                        return;
                    } else {//otec je 2-vrchol

                        leaf.setPomSon(null);
                        if (pom.getKeys().get(0).compareTo(maxL.getKeys().get(0)) > 0) {
                            maxL.setRightSon(pomAddress);
                            maxL.setLeftSon(leaf.getRightSon());
                            leaf.setRightSon(leaf.getMiddleSon());
                        } else {
                            maxL.setRightSon(leaf.getRightSon());
                            BNode<K> middle = fileTree.getData(leaf.getMiddleSon(), new BNode<>(key));
                            if (pom.getKeys().get(0).compareTo(middle.getKeys().get(0)) > 0) {
                                maxL.setLeftSon(pomAddress);
                                leaf.setRightSon(leaf.getMiddleSon());
                            } else {
                                maxL.setLeftSon(leaf.getMiddleSon());
                                if (pom.getKeys().get(0).compareTo(leaf.getKeys().get(0)) > 0) {
                                    leaf.setRightSon(pomAddress);
                                } else {
                                    leaf.setRightSon(leaf.getLeftSon());
                                    leaf.setLeftSon(pomAddress);
                                }
                            }
                        }
                        leaf.setMiddleSon(null);

                        if (son == 1) {
                            dad.setLeftSon(leafAddress);
                        } else if (son == 0) {
                            dad.setMiddleSon(leafAddress);
                        } else {
                            dad.setRightSon(leafAddress);
                        }
                        dad.setPomSon(maxLAddress);
                        fileTree.setData(maxLAddress, maxL);
                        fileTree.setData(leafAddress, leaf);
                    }
                    fileTree.setData(dadAddress, dad);

                    //BNode<K> dadLeft = fileTree.getData(dad.getLeftSon(), new BNode<>());
                    //BNode<K> dadRight = fileTree.getData(dad.getRightSon(), new BNode<>());
                    //BNode<K> dadMiddle = fileTree.getData(dad.getMiddleSon(), new BNode<>());
                    //BNode<K> dadPom = fileTree.getData(dad.getPomSon(), new BNode<>());
                    //leaf.setFather(leaf.getFather());
                    //dadRight.setFather(leaf.getFather());
                    maxL.setFather(leaf.getFather());
                    fileTree.setData(leafAddress, leaf);
                    //fileTree.setData(dad.getRightSon(), dadRight);
                    fileTree.setData(dad.getPomSon(), maxL);

                    //BNode<K> dadLeftLeft = fileTree.getData(leaf.getLeftSon(), new BNode<>());
                    //BNode<K> dadLeftRight = fileTree.getData(leaf.getRightSon(), new BNode<>());
                    //BNode<K> dadRightLeft = fileTree.getData(dadRight.getLeftSon(), new BNode<>());
                    //BNode<K> dadRightRight = fileTree.getData(dadRight.getRightSon(), new BNode<>());
                    //BNode<K> dadMiddleLeft = fileTree.getData(dadMiddle.getLeftSon(), new BNode<>());
                    //BNode<K> dadMiddleRight = fileTree.getData(dadMiddle.getRightSon(), new BNode<>());
                    BNode<K> dadPomLeft = fileTree.getData(maxL.getLeftSon(), new BNode<>(key));
                    BNode<K> dadPomRight = fileTree.getData(maxL.getRightSon(), new BNode<>(key));
                    //dadLeftLeft.setFather(leafAddress);
                    //dadLeftRight.setFather(leafAddress);
                    //dadRightLeft.setFather(dad.getRightSon());
                    //dadRightRight.setFather(dad.getRightSon());
                    //dadMiddleLeft.setFather(dad.getMiddleSon());
                    //dadMiddleRight.setFather(dad.getMiddleSon());
                    dadPomLeft.setFather(dad.getPomSon());
                    dadPomRight.setFather(dad.getPomSon());
                    //fileTree.setData(leaf.getLeftSon(), dadLeftLeft);
                    //fileTree.setData(leaf.getRightSon(), dadLeftRight);
                    //fileTree.setData(dadRight.getLeftSon(), dadRightLeft);
                    //fileTree.setData(dadRight.getRightSon(), dadRightRight);
                    //fileTree.setData(dadMiddle.getLeftSon(), dadMiddleLeft);
                    //fileTree.setData(dadMiddle.getRightSon(), dadMiddleRight);
                    fileTree.setData(maxL.getLeftSon(), dadPomLeft);
                    fileTree.setData(maxL.getRightSon(), dadPomRight);

                    leaf = dad;
                    leafAddress = dadAddress;
                }
            }
        }
    }

    //vytvorené podľa prednášky a vlastných poznámok
    public boolean delete(K key) {
        //najdeme node z ktoreho mazeme
        BDataNode<K> dataNode = this.findNode(key);
        long actualAddress = dataNode.getAddress();

        if (actualAddress == -1) {
            System.out.println("prvok s klucom " + key + " sa v strome nenachadzal");
            return false;
        }
        this.count--;
        BNode<K> actualNode = dataNode.getNode();

        //ak node nie je list tak vymazany prvok najdeme v inorder liste a presuvame sa na dany list
        if (!isLeaf(actualNode)) {
            BDataNode<K> dataN = findInOrder(actualAddress, key);
            Long pomAddress = dataN.getAddress();
            BNode<K> pomNode = dataN.getNode();

            actualNode.deleteItem(key);
            actualNode.addItem(pomNode.getKeys().get(0));
            fileTree.setData(actualAddress, actualNode);

            pomNode.deleteItem(pomNode.getKeys().get(0));
            fileTree.setData(pomAddress, pomNode);

            actualNode = pomNode;
            actualAddress = pomAddress;
        } else {
            actualNode.deleteItem(key);
            fileTree.setData(actualAddress, actualNode);
        }

        //zaciname od listu
        while (true) {
            if (actualNode.getLengthKeys() == 0 && actualNode.getFather() == null) {
                if (actualNode.getLeftSon() != null) {
                    this.root = actualNode.getLeftSon();
                } else if (actualNode.getRightSon() != null) {
                    this.root = actualNode.getRightSon();
                } else {
                    this.root = actualNode.getMiddleSon();
                }
                fileTree.delete(actualAddress, actualNode);
                this.saveData();
                return true;
            }
            if (actualNode.getLengthKeys() == 1) {//koncime ak ma node 1 kluc
                this.saveData();
                return true;
            }
            //sme v koreni stromu a prehadzujeme referenciu korena stromu na novy node
            if (isRoot(actualNode)) {
                //Long adr = actualNode.getLeftSon();
                BNode<K> node = fileTree.getData(actualNode.getLeftSon(), new BNode<>(key));
                if (node != null) {
                    node.setFather(null);
                    this.root = actualNode.getLeftSon();
                    fileTree.setData(actualNode.getLeftSon(), node);
                    fileTree.delete(actualAddress, actualNode);/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    this.saveData();
                    return true;
                }

                //adr = actualNode.getRightSon();
                node = fileTree.getData(actualNode.getRightSon(), new BNode<>(key));
                if (node != null) {
                    node.setFather(null);
                    this.root = actualNode.getRightSon();
                    fileTree.setData(actualNode.getRightSon(), node);
                    fileTree.delete(actualAddress, actualNode);/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                    this.saveData();
                    return true;
                }
                node = fileTree.getData(actualNode.getMiddleSon(), new BNode<>(key));
                if (node != null) {
                    node.setFather(null);
                    this.root = actualNode.getMiddleSon();
                    fileTree.setData(actualNode.getMiddleSon(), node);
                    fileTree.delete(actualAddress, actualNode);/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                } else {
                    fileTree.delete(actualAddress, actualNode);/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    this.root = null;
                }
                this.saveData();
                return true;
            }
            int son = this.getSonPosition(actualNode, actualAddress);

            Long dadAddress = actualNode.getFather();
            BNode<K> dad = fileTree.getData(actualNode.getFather(), new BNode<>(key));
            if (dad.getLengthKeys() == 2) {//otec je 3-vrchol
                if (son == 1) {//lavy syn
                    Long dadMiddleAddress = dad.getMiddleSon();
                    BNode<K> dadMiddle = fileTree.getData(dad.getMiddleSon(), new BNode<>(key));
                    if (dadMiddle.getLengthKeys() == 2) {
                        actualNode.addItem(dad.getKeys().get(0));
                        dad.deleteItem(dad.getKeys().get(0));
                        dad.addItem(dadMiddle.getKeys().get(0));
                        fileTree.setData(dadAddress, dad);

                        dadMiddle.deleteItem(dadMiddle.getKeys().get(0));

                        if (!isLeaf(actualNode)) {
                            if (actualNode.getRightSon() != null) {
                                actualNode.setLeftSon(actualNode.getRightSon());
                            }
                            actualNode.setRightSon(dadMiddle.getLeftSon());
                            dadMiddle.setLeftSon(dadMiddle.getMiddleSon());
                            dadMiddle.setMiddleSon(null);

                            Long rightAddress = actualNode.getRightSon();
                            BNode<K> right = fileTree.getData(actualNode.getRightSon(), new BNode<>(key));
                            right.setFather(actualAddress);
                            fileTree.setData(rightAddress, right);
                        }
                        fileTree.setData(actualAddress, actualNode);
                        fileTree.setData(dadMiddleAddress, dadMiddle);
                    } else {
                        actualNode.addItem(dad.getKeys().get(0));
                        dad.deleteItem(dad.getKeys().get(0));
                        actualNode.addItem(dadMiddle.getKeys().get(0));
                        dadMiddle.deleteItem(dadMiddle.getKeys().get(0));
                        fileTree.setData(dadMiddleAddress, dadMiddle);

                        if (!isLeaf(actualNode)) {
                            if (actualNode.getRightSon() != null) {
                                actualNode.setLeftSon(actualNode.getRightSon());
                            }
                            actualNode.setMiddleSon(dadMiddle.getLeftSon());
                            actualNode.setRightSon(dadMiddle.getRightSon());

                            BNode<K> middle = fileTree.getData(actualNode.getMiddleSon(), new BNode<>(key));
                            BNode<K> right = fileTree.getData(actualNode.getRightSon(), new BNode<>(key));
                            middle.setFather(actualAddress);
                            right.setFather(actualAddress);
                            fileTree.setData(actualNode.getMiddleSon(), middle);
                            fileTree.setData(actualNode.getRightSon(), right);
                        }
                        //vymazanie stredneho syna otca
                        fileTree.delete(dad.getMiddleSon(), new BNode<>(key));

                        dad.setMiddleSon(null);
                        fileTree.setData(dadAddress, dad);
                        fileTree.setData(actualAddress, actualNode);
                    }
                } else if (son == 0) {//stredny syn
                    Long dadLeftAddress = dad.getLeftSon();
                    Long dadRightAddress = dad.getRightSon();
                    BNode<K> dadLeft = fileTree.getData(dad.getLeftSon(), new BNode<>(key));
                    BNode<K> dadRight = fileTree.getData(dad.getRightSon(), new BNode<>(key));
                    if (dadLeft.getLengthKeys() == 1) {
                        dadLeft.addItem(dad.getKeys().get(0));
                        dad.deleteItem(dad.getKeys().get(0));

                        if (!isLeaf(actualNode)) {
                            dadLeft.setMiddleSon(dadLeft.getRightSon());
                            if (actualNode.getLeftSon() != null) {
                                dadLeft.setRightSon(actualNode.getLeftSon());
                            } else {
                                dadLeft.setRightSon(actualNode.getRightSon());
                            }
                            BNode<K> dadLeftRight = fileTree.getData(dadLeft.getRightSon(), new BNode<>(key));
                            dadLeftRight.setFather(dad.getLeftSon());
                            fileTree.setData(dadLeft.getRightSon(), dadLeftRight);
                        }
                        //vymazanie stredneho syna otca
                        fileTree.delete(dad.getMiddleSon(), new BNode<>(key));

                        dad.setMiddleSon(null);
                        fileTree.setData(dadLeftAddress, dadLeft);
                        fileTree.setData(dadAddress, dad);
                        //fileTree.setData(actualAddress, actualNode);

                        /*if (dad.getFather() == null) {
                            actualNode.setFather(null);
                            this.root = actualAddress;
                            fileTree.setData(actualAddress, actualNode);
                            return true;
                        }*/
                        actualNode = dad;
                        actualAddress = dadAddress;
                    } else if (dadRight.getLengthKeys() == 1) {
                        dadRight.addItem(dad.getKeys().get(1));
                        dad.deleteItem(dad.getKeys().get(1));

                        if (!isLeaf(actualNode)) {
                            dadRight.setMiddleSon(dadRight.getLeftSon());
                            if (actualNode.getLeftSon() != null) {
                                dadRight.setLeftSon(actualNode.getLeftSon());
                            } else {
                                dadRight.setLeftSon(actualNode.getRightSon());
                            }
                            BNode<K> dadRightLeft = fileTree.getData(dadRight.getLeftSon(), new BNode<>(key));
                            dadRightLeft.setFather(dad.getRightSon());
                            fileTree.setData(dadRight.getLeftSon(), dadRightLeft);
                        }
                        //vymazanie stredneho syna otca
                        fileTree.delete(dad.getMiddleSon(), new BNode<>(key));

                        dad.setMiddleSon(null);
                        fileTree.setData(dadRightAddress, dadRight);
                        fileTree.setData(dadAddress, dad);
                        fileTree.setData(actualAddress, actualNode);

                        if (dad.getFather() == null && dad.getLengthKeys() == 0) {
                            actualNode.setFather(null);
                            this.root = actualAddress;
                            fileTree.setData(actualAddress, actualNode);
                            this.saveData();
                            return true;
                        }
                        actualNode = dad;
                        actualAddress = dadAddress;
                    } else {
                        actualNode.addItem(dad.getKeys().get(1));
                        dad.deleteItem(dad.getKeys().get(1));
                        dad.addItem(dadRight.getKeys().get(0));
                        dadRight.deleteItem(dadRight.getKeys().get(0));

                        if (!isLeaf(actualNode)) {
                            if (actualNode.getRightSon() != null) {
                                actualNode.setLeftSon(actualNode.getRightSon());
                            }
                            actualNode.setRightSon(dadRight.getLeftSon());
                            dadRight.setLeftSon(dadRight.getMiddleSon());
                            dadRight.setMiddleSon(null);
                            BNode<K> right = fileTree.getData(actualNode.getRightSon(), new BNode<>(key));
                            right.setFather(actualAddress);
                            fileTree.setData(actualNode.getRightSon(), right);
                        }
                        fileTree.setData(actualAddress, actualNode);
                        fileTree.setData(dadAddress, dad);
                        fileTree.setData(dadRightAddress, dadRight);
                    }
                } else if (son == -1) {//pravy syn
                    Long dadMiddleAddress = dad.getMiddleSon();
                    BNode<K> dadMiddle = fileTree.getData(dad.getMiddleSon(), new BNode<>(key));
                    if (dadMiddle.getLengthKeys() == 2) {
                        actualNode.addItem(dad.getKeys().get(1));
                        dad.deleteItem(dad.getKeys().get(1));
                        dad.addItem(dadMiddle.getKeys().get(1));
                        dadMiddle.deleteItem(dadMiddle.getKeys().get(1));

                        if (!isLeaf(actualNode)) {
                            if (actualNode.getLeftSon() != null) {
                                actualNode.setRightSon(actualNode.getLeftSon());
                            }
                            actualNode.setLeftSon(dadMiddle.getRightSon());
                            dadMiddle.setRightSon(dadMiddle.getMiddleSon());
                            dadMiddle.setMiddleSon(null);

                            BNode<K> left = fileTree.getData(actualNode.getLeftSon(), new BNode<>(key));
                            left.setFather(actualAddress);
                            fileTree.setData(actualNode.getLeftSon(), left);
                        }
                        fileTree.setData(actualAddress, actualNode);
                        fileTree.setData(dadAddress, dad);
                        fileTree.setData(dadMiddleAddress, dadMiddle);
                    } else {
                        actualNode.addItem(dad.getKeys().get(1));
                        dad.deleteItem(dad.getKeys().get(1));
                        actualNode.addItem(dadMiddle.getKeys().get(0));
                        dadMiddle.deleteItem(dadMiddle.getKeys().get(0));

                        if (!isLeaf(actualNode)) {
                            if (actualNode.getLeftSon() != null) {
                                actualNode.setRightSon(actualNode.getLeftSon());
                            }
                            actualNode.setMiddleSon(dadMiddle.getRightSon());
                            actualNode.setLeftSon(dadMiddle.getLeftSon());

                            BNode<K> left = fileTree.getData(actualNode.getLeftSon(), new BNode<>(key));
                            BNode<K> middle = fileTree.getData(actualNode.getMiddleSon(), new BNode<>(key));
                            left.setFather(actualAddress);
                            middle.setFather(actualAddress);
                            fileTree.setData(actualNode.getLeftSon(), left);
                            fileTree.setData(actualNode.getMiddleSon(), middle);
                        }
                        //vymazanie stredneho syna otca
                        fileTree.delete(dad.getMiddleSon(), new BNode<>(key));

                        dad.setMiddleSon(null);
                        fileTree.setData(actualAddress, actualNode);
                        fileTree.setData(dadAddress, dad);
                        fileTree.setData(dadMiddleAddress, dadMiddle);
                    }
                }
                return true;
            } else {//otec je 2-vrchol
                if (son == 1) {//lavy syn
                    Long dadRightAddress = dad.getRightSon();
                    BNode<K> dadRight = fileTree.getData(dad.getRightSon(), new BNode<>(key));
                    if (dadRight.getLengthKeys() == 2) {//
                        actualNode.addItem(dad.getKeys().get(0));
                        dad.deleteItem(dad.getKeys().get(0));
                        dad.addItem(dadRight.getKeys().get(0));
                        dadRight.deleteItem(dadRight.getKeys().get(0));

                        if (!isLeaf(actualNode)) {
                            if (actualNode.getRightSon() != null) {
                                actualNode.setLeftSon(actualNode.getRightSon());
                            }
                            actualNode.setRightSon(dadRight.getLeftSon());
                            dadRight.setLeftSon(dadRight.getMiddleSon());
                            dadRight.setMiddleSon(null);

                            BNode<K> right = fileTree.getData(actualNode.getRightSon(), new BNode<>(key));
                            right.setFather(actualAddress);
                            fileTree.setData(actualNode.getRightSon(), right);
                        }
                        fileTree.setData(actualAddress, actualNode);
                        fileTree.setData(dadAddress, dad);
                        fileTree.setData(dadRightAddress, dadRight);
                        this.saveData();
                        return true;
                    } else {
                        actualNode.addItem(dad.getKeys().get(0));
                        dad.deleteItem(dad.getKeys().get(0));
                        actualNode.addItem(dadRight.getKeys().get(0));
                        dadRight.deleteItem(dadRight.getKeys().get(0));

                        if (!isLeaf(actualNode)) {
                            if (actualNode.getRightSon() != null) {
                                actualNode.setLeftSon(actualNode.getRightSon());
                            }
                            actualNode.setMiddleSon(dadRight.getLeftSon());
                            actualNode.setRightSon(dadRight.getRightSon());

                            BNode<K> right = fileTree.getData(actualNode.getRightSon(), new BNode<>(key));
                            BNode<K> middle = fileTree.getData(actualNode.getMiddleSon(), new BNode<>(key));
                            right.setFather(actualAddress);
                            middle.setFather(actualAddress);
                            fileTree.setData(actualNode.getRightSon(), right);
                            fileTree.setData(actualNode.getMiddleSon(), middle);
                        }
                        //vymazanie stredneho syna otca
                        fileTree.delete(dad.getRightSon(), new BNode<>(key));

                        dad.setRightSon(null);
                        fileTree.setData(actualAddress, actualNode);
                        fileTree.setData(dadAddress, dad);
                        //fileTree.setData(dadRightAddress, dadRight);

                        if (dad.getFather() == null) {
                            actualNode.setFather(null);
                            this.root = actualAddress;
                            fileTree.delete(dadAddress, dad);
                            fileTree.setData(actualAddress, actualNode);
                            this.saveData();
                            return true;
                        }
                        actualNode = dad;
                        actualAddress = dadAddress;
                    }
                } else {//pravy syn
                    Long dadLeftAddress = dad.getLeftSon();
                    BNode<K> dadLeft = fileTree.getData(dad.getLeftSon(), new BNode<>(key));
                    if (dadLeft.getLengthKeys() == 2) {
                        actualNode.addItem(dad.getKeys().get(0));
                        dad.deleteItem(dad.getKeys().get(0));
                        dad.addItem(dadLeft.getKeys().get(1));
                        dadLeft.deleteItem(dadLeft.getKeys().get(1));

                        if (!isLeaf(actualNode)) {
                            if (actualNode.getLeftSon() != null) {
                                actualNode.setRightSon(actualNode.getLeftSon());
                            }
                            actualNode.setLeftSon(dadLeft.getRightSon());

                            dadLeft.setRightSon(dadLeft.getMiddleSon());
                            dadLeft.setMiddleSon(null);

                            BNode<K> left = fileTree.getData(actualNode.getLeftSon(), new BNode<>(key));
                            left.setFather(actualAddress);
                            fileTree.setData(actualNode.getLeftSon(), left);
                        }
                        fileTree.setData(actualAddress, actualNode);
                        fileTree.setData(dadAddress, dad);
                        fileTree.setData(dadLeftAddress, dadLeft);
                        this.saveData();
                        return true;
                    } else {
                        actualNode.addItem(dad.getKeys().get(0));
                        dad.deleteItem(dad.getKeys().get(0));
                        actualNode.addItem(dadLeft.getKeys().get(0));
                        dadLeft.deleteItem(dadLeft.getKeys().get(0));

                        if (!isLeaf(actualNode)) {
                            if (actualNode.getLeftSon() != null) {
                                actualNode.setRightSon(actualNode.getLeftSon());
                            }
                            actualNode.setMiddleSon(dadLeft.getRightSon());
                            actualNode.setLeftSon(dadLeft.getLeftSon());

                            BNode<K> left = fileTree.getData(actualNode.getLeftSon(), new BNode<>(key));
                            BNode<K> middle = fileTree.getData(actualNode.getMiddleSon(), new BNode<>(key));
                            left.setFather(actualAddress);
                            middle.setFather(actualAddress);
                            fileTree.setData(actualNode.getLeftSon(), left);
                            fileTree.setData(actualNode.getMiddleSon(), middle);

                        }
                        //vymazanie stredneho syna otca
                        fileTree.delete(dad.getLeftSon(), new BNode<>(key));

                        dad.setLeftSon(null);
                        fileTree.setData(actualAddress, actualNode);
                        fileTree.setData(dadAddress, dad);
                        //fileTree.setData(dadLeftAddress, dadLeft);

                        if (dad.getFather() == null) {
                            actualNode.setFather(null);
                            this.root = actualAddress;
                            fileTree.setData(actualAddress, actualNode);
                            fileTree.delete(dadAddress, dad);
                            this.saveData();
                            return true;
                        }
                        actualNode = dad;
                        actualAddress = dadAddress;
                    }
                }
            }
        }
    }

    //pri metode delete hladam list, z ktoreho vyberiem kluc pre nahradenie
    public BDataNode<K> findLeaf(K key) {
        if (this.root == null) {
            return null;
        }

        BNode<K> actualNode = fileTree.getData(this.root, new BNode<>(key));
        Long actualAddress = this.root;
        while (true) {
            if (this.isLeaf(actualNode)) {
                return new BDataNode<K>(null, actualNode, actualAddress);
            }

            if (actualNode.getLengthKeys() == 2) {
                if (key.compareTo(actualNode.getKeys().get(0)) <= 0) {
                    actualAddress = actualNode.getLeftSon();
                    actualNode = fileTree.getData(actualNode.getLeftSon(), new BNode<>(key));
                } else if (key.compareTo(actualNode.getKeys().get(1)) > 0) {
                    actualAddress = actualNode.getRightSon();
                    actualNode = fileTree.getData(actualNode.getRightSon(), new BNode<>(key));
                } else {
                    actualAddress = actualNode.getMiddleSon();
                    actualNode = fileTree.getData(actualNode.getMiddleSon(), new BNode<>(key));
                }
            } else {
                if (key.compareTo(actualNode.getKeys().get(0)) <= 0) {
                    actualAddress = actualNode.getLeftSon();
                    actualNode = fileTree.getData(actualNode.getLeftSon(), new BNode<>(key));
                } else {
                    actualAddress = actualNode.getRightSon();
                    actualNode = fileTree.getData(actualNode.getRightSon(), new BNode<>(key));
                }
            }
        }
    }

    //hladam node v ktorom sa nachadza zadany kluc
    public BDataNode<K> findNode(K key) {
        if (this.root == null || this.root == -1) {
            return null;
        }
        BNode<K> actualNode = fileTree.getData(this.root, new BNode<>(key));
        long actualAddress = this.root;

        while (true) {
            if (actualNode.getLengthKeys() == 2) { //3-vrchol
                if (actualNode.getKeys().get(0).compareTo(key) == 0 || actualNode.getKeys().get(1).compareTo(key) == 0) {
                    return new BDataNode<K>(null, actualNode, actualAddress);
                } else if (key.compareTo(actualNode.getKeys().get(0)) < 0 && actualNode.getLeftSon() != null) {
                    actualAddress = actualNode.getLeftSon();
                    actualNode = fileTree.getData(actualNode.getLeftSon(), new BNode<>(key));
                } else if (key.compareTo(actualNode.getKeys().get(1)) > 0 && actualNode.getRightSon() != null) {
                    actualAddress = actualNode.getRightSon();
                    actualNode = fileTree.getData(actualNode.getRightSon(), new BNode<>(key));
                } else if (key.compareTo(actualNode.getKeys().get(0)) > 0 && key.compareTo(actualNode.getKeys().get(1)) < 0 && actualNode.getMiddleSon() != null) {
                    actualAddress = actualNode.getMiddleSon();
                    actualNode = fileTree.getData(actualNode.getMiddleSon(), new BNode<>(key));
                } else {
                    return null;
                }

            } else { //2-vrchol
                if (key.compareTo(actualNode.getKeys().get(0)) == 0) {
                    return new BDataNode<K>(null, actualNode, actualAddress);
                } else if (key.compareTo(actualNode.getKeys().get(0)) < 0 && actualNode.getLeftSon() != null) {
                    actualAddress = actualNode.getLeftSon();
                    actualNode = fileTree.getData(actualNode.getLeftSon(), new BNode<>(key));
                } else if (key.compareTo(actualNode.getKeys().get(0)) > 0 && actualNode.getRightSon() != null) {
                    actualAddress = actualNode.getRightSon();
                    actualNode = fileTree.getData(actualNode.getRightSon(), new BNode<>(key));
                } else {
                    return null;
                }
            }
        }
    }

    //vytvorené podľa prednášky a vlastných poznámok
    public BDataNode<K> find(K key) {
        BDataNode<K> dataNode = findNode(key);
        if (dataNode == null) {
            return null;
        }
        BNode<K> node = fileTree.getData(dataNode.getAddress(), new BNode<>(key));

        if (node.getKeys().get(0).compareTo(key) == 0) {
            return new BDataNode<K>(node.getKeys().get(0), null, null);
        } else {
            return new BDataNode<K>(node.getKeys().get(1), null, null);
        }
    }

    public BTree<K> findInterval(K min_, K max_) {
        BTree<K> tree = new BTree<K>(this.fileTree.nameFile + "Interval", typeK);
        tree.clean();
        //najdem najmensi kluc do intervalu
        BDataNode<K> dataNode = this.getMinKey(min_);
        if (dataNode == null) {
            return null;
        }
        K actualKey = dataNode.getKey();
        //Long actualAddress = dataNode.getObjectAddress();
        if (actualKey == null || actualKey.compareTo(max_) > 0) {
            return null;
        }

        //prehladavam az kym aktualny kluc nie je null a zaroven nie je vacsi ako maximalny kluc
        do {
            tree.add(actualKey);
            dataNode = this.getInOrder(dataNode);
            actualKey = dataNode != null ? dataNode.getKey() : null;
            //actualAddress = dataNode != null ? dataNode.getObjectAddress() : null;
        } while (actualKey != null && actualKey.compareTo(max_) <= 0);

        return tree;
    }

    //pomocna metoda pre findInterval, najde dataNode obsahujuci kluc, ktory je najblizsi k minimu intervalu
    public BDataNode<K> getMinKey(K key) {
        BDataNode<K> dataNode = this.findLeaf(key);
        if (dataNode == null) {
            return null;
        }
        BNode<K> node = fileTree.getData(dataNode.getAddress(), new BNode<>(key));

        if (node.getLengthKeys() == 1) {
            if (node.getKeys().get(0).compareTo(key) >= 0) {
                return new BDataNode<K>(node.getKeys().get(0), node, dataNode.getAddress());
            } else {
                return this.getInOrder(new BDataNode<K>(node.getKeys().get(0), node, dataNode.getAddress()));
            }
        } else {
            if (node.getKeys().get(1).compareTo(key) >= 0 && node.getKeys().get(0).compareTo(key) < 0) {
                return new BDataNode<K>(node.getKeys().get(1), node, dataNode.getAddress());
            } else if (node.getKeys().get(0).compareTo(key) >= 0) {
                return new BDataNode<K>(node.getKeys().get(0), node, dataNode.getAddress());
            } else {
                return this.getInOrder(new BDataNode<K>(node.getKeys().get(1), node, dataNode.getAddress()));
            }
        }
    }

    //najde in order nasledovnika zadanemu dataNode
    public BDataNode<K> getInOrder(BDataNode<K> dataNode) {
        Long searchedAddress = dataNode.getAddress();
        BNode<K> searchedNode = fileTree.getData(searchedAddress, new BNode<>(typeK));
        K key = dataNode.getKey();
        if (isLeaf(searchedNode) && searchedNode.getLengthKeys() > 1 && searchedNode.getKeys().get(0).compareTo(key) == 0) {
            //dataNode.setObjectAddress(searchedNode.getAddresses().get(1));
            dataNode.setKey(searchedNode.getKeys().get(1));
            return dataNode;
        }
        if (searchedNode.myEquals(this.getLastNode())) {
            return null;
        }
        if (searchedNode.getLengthKeys() == 2 && searchedNode.getKeys().get(0).compareTo(key) == 0) {
            if (isLeaf(searchedNode)) {
                dataNode.setAddress(searchedAddress);
                dataNode.setKey(searchedNode.getKeys().get(1));
                //dataNode.setObjectAddress(searchedNode.getAddresses().get(1));
                return dataNode;
            }
        }

        if (isLeaf(searchedNode)) {
            Long dadAddress = searchedNode.getFather();
            BNode<K> dad = fileTree.getData(searchedNode.getFather(), new BNode<>(key));
            if (dad.getLengthKeys() == 2) {
                switch (this.getSonPosition(searchedNode, searchedAddress)) {
                    case 1:
                        dataNode.setAddress(dadAddress);
                        dataNode.setKey(dad.getKeys().get(0));
                        //dataNode.setObjectAddress(dad.getAddresses().get(0));
                        return dataNode;
                    case 0:
                        dataNode.setAddress(dadAddress);
                        dataNode.setKey(dad.getKeys().get(1));
                        //dataNode.setObjectAddress(dad.getAddresses().get(1));
                        return dataNode;
                    case -1:
                        do {
                            searchedAddress = searchedNode.getFather();
                            searchedNode = fileTree.getData(searchedNode.getFather(), new BNode<>(key));
                        } while (this.getSonPosition(searchedNode, searchedAddress) == -1);

                        dadAddress = searchedNode.getFather();
                        dad = fileTree.getData(searchedNode.getFather(), new BNode<>(key));
                        if (this.getSonPosition(searchedNode, searchedAddress) == 1) {
                            dataNode.setAddress(dadAddress);
                            dataNode.setKey(dad.getKeys().get(0));
                            //dataNode.setObjectAddress(dad.getAddresses().get(0));
                            return dataNode;
                        } else {
                            dataNode.setAddress(dadAddress);
                            dataNode.setKey(dad.getKeys().get(1));
                            //dataNode.setObjectAddress(dad.getAddresses().get(1));
                            return dataNode;
                        }
                }
            } else {
                if (this.getSonPosition(searchedNode, searchedAddress) == 1) {
                    dataNode.setAddress(dadAddress);
                    dataNode.setKey(dad.getKeys().get(0));
                    //dataNode.setObjectAddress(dad.getAddresses().get(0));
                    return dataNode;
                } else {
                    do {
                        searchedAddress = searchedNode.getFather();
                        searchedNode = fileTree.getData(searchedNode.getFather(), new BNode<>(key));
                    } while (this.getSonPosition(searchedNode, searchedAddress) == -1);

                    dadAddress = searchedNode.getFather();
                    dad = fileTree.getData(searchedNode.getFather(), new BNode<>(key));
                    if (this.getSonPosition(searchedNode, searchedAddress) == 1) {
                        dataNode.setAddress(dadAddress);
                        dataNode.setKey(dad.getKeys().get(0));
                        //dataNode.setObjectAddress(dad.getAddresses().get(0));
                        return dataNode;
                    } else {
                        dataNode.setAddress(dadAddress);
                        dataNode.setKey(dad.getKeys().get(1));
                        //dataNode.setObjectAddress(dad.getAddresses().get(1));
                        return dataNode;
                    }
                }
            }
        } else {
            if (searchedNode.getLengthKeys() == 2) {
                if (searchedNode.getKeys().get(0).compareTo(key) == 0) {
                    searchedAddress = searchedNode.getMiddleSon();
                    searchedNode = fileTree.getData(searchedNode.getMiddleSon(), new BNode<>(key));
                    while (!isLeaf(searchedNode)) {
                        searchedAddress = searchedNode.getLeftSon();
                        searchedNode = fileTree.getData(searchedNode.getLeftSon(), new BNode<>(key));
                    }
                    dataNode.setAddress(searchedAddress);
                    dataNode.setKey(searchedNode.getKeys().get(0));
                    //dataNode.setObjectAddress(searchedNode.getAddresses().get(0));
                    return dataNode;
                } else {
                    searchedAddress = searchedNode.getRightSon();
                    searchedNode = fileTree.getData(searchedNode.getRightSon(), new BNode<>(key));
                    while (!isLeaf(searchedNode)) {
                        searchedAddress = searchedNode.getLeftSon();
                        searchedNode = fileTree.getData(searchedNode.getLeftSon(), new BNode<>(key));
                    }
                    dataNode.setAddress(searchedAddress);
                    dataNode.setKey(searchedNode.getKeys().get(0));
                    //dataNode.setObjectAddress(searchedNode.getAddresses().get(0));
                    return dataNode;
                }
            } else {
                searchedAddress = searchedNode.getRightSon();
                searchedNode = fileTree.getData(searchedNode.getRightSon(), new BNode<>(key));
                while (!isLeaf(searchedNode)) {
                    searchedAddress = searchedNode.getLeftSon();
                    searchedNode = fileTree.getData(searchedNode.getLeftSon(), new BNode<>(key));
                }
                dataNode.setAddress(searchedAddress);
                dataNode.setKey(searchedNode.getKeys().get(0));
                //dataNode.setObjectAddress(searchedNode.getAddresses().get(0));
                return dataNode;
            }
        }
        dataNode.setAddress(searchedAddress);
        dataNode.setKey(searchedNode.getKeys().get(0));
        //dataNode.setObjectAddress(searchedNode.getAddresses().get(0));
        return dataNode;
    }

    //pomocna metoda pri kontrole hlbky stromu, najde inorder v liste
    public BDataNode<K> findInOrder(Long address, K key) {
        Long searchedAddress = address;
        BNode<K> searchedNode = fileTree.getData(address, new BNode<>(key));
        boolean isLeftKey = searchedNode.getKeys().get(0).compareTo(key) == 0;

        if (isLeaf(searchedNode)) {
            Long dadAddress = searchedNode.getFather();
            BNode<K> dad = fileTree.getData(searchedNode.getFather(), new BNode<>(key));
            if (dad.getLengthKeys() == 2) {
                switch (this.getSonPosition(searchedNode, searchedAddress)) {
                    case 1:
                        return findInOrder(dadAddress, dad.getKeys().get(0));
                    case 0:
                        return findInOrder(dadAddress, dad.getKeys().get(1));
                    case -1:
                        do {
                            searchedAddress = searchedNode.getFather();
                            searchedNode = fileTree.getData(searchedNode.getFather(), new BNode<>(key));
                        } while (this.getSonPosition(searchedNode, searchedAddress) == -1);

                        dadAddress = searchedNode.getFather();
                        dad = fileTree.getData(searchedNode.getFather(), new BNode<>(key));
                        if (this.getSonPosition(searchedNode, searchedAddress) == 1) {
                            return findInOrder(dadAddress, dad.getKeys().get(0));
                        } else {
                            return findInOrder(dadAddress, dad.getKeys().get(1));
                        }
                }
            } else {
                if (this.getSonPosition(searchedNode, searchedAddress) == 1) {
                    return findInOrder(dadAddress, dad.getKeys().get(0));
                } else {
                    do {
                        searchedAddress = searchedNode.getFather();
                        searchedNode = fileTree.getData(searchedNode.getFather(), new BNode<>(key));
                    } while (this.getSonPosition(searchedNode, searchedAddress) == -1);

                    dadAddress = searchedNode.getFather();
                    dad = fileTree.getData(searchedNode.getFather(), new BNode<>(key));
                    if (this.getSonPosition(searchedNode, searchedAddress) == 1) {
                        return findInOrder(dadAddress, dad.getKeys().get(0));
                    } else {
                        return findInOrder(dadAddress, dad.getKeys().get(1));
                    }
                }
            }
        } else {
            if (searchedNode.getLengthKeys() == 1 || !isLeftKey) {
                searchedAddress = searchedNode.getRightSon();
                searchedNode = fileTree.getData(searchedNode.getRightSon(), new BNode<>(key));
            } else {
                searchedAddress = searchedNode.getMiddleSon();
                searchedNode = fileTree.getData(searchedNode.getMiddleSon(), new BNode<>(key));
            }
            while (true) {
                if (searchedNode.getLeftSon() != null) {
                    searchedAddress = searchedNode.getLeftSon();
                    searchedNode = fileTree.getData(searchedNode.getLeftSon(), new BNode<>(key));
                } else {
                    return new BDataNode<K>(null, searchedNode, searchedAddress);
                }
            }
        }
        return null;
    }

    //prvy node v strome
    public BDataNode<K> getFirstNode() {
        if (this.root == null) {
            return null;
        }
        Long actualAddress = this.root;
        BNode<K> actualNode = fileTree.getData(this.root, new BNode<>(typeK));
        while (true) {
            if (!isLeaf(actualNode)) {
                actualAddress = actualNode.getLeftSon();
                actualNode = fileTree.getData(actualNode.getLeftSon(), new BNode<>(typeK));
            } else {
                return new BDataNode<K>(null, actualNode, actualAddress);
            }
        }
    }

    //posledny node v strome
    public BNode<K> getLastNode() {
        if (this.root == null) {
            return null;
        }
        BNode<K> actualNode = fileTree.getData(this.root, new BNode<>(typeK));
        while (true) {
            if (!isLeaf(actualNode)) {
                actualNode = fileTree.getData(actualNode.getRightSon(), new BNode<>(typeK));
            } else {
                return actualNode;
            }
        }
    }

    //pretranformuje strom na arraylist, ktory ulahci pracu pri vypise
    public ArrayList<K> getInOrderArrayL() {
        ArrayList<K> array = new ArrayList<>(this.count);
        int x = 0;
        if (this.count == 0) {
            return array;
        }
        BDataNode<K> dataN = this.getFirstNode();
        BNode<K> actualNode = dataN.getNode();

        if (actualNode == null) {
            System.out.println("Strom je prazdy.");
            return null;
        }

        BDataNode<K> dataNode = new BDataNode<K>(actualNode.getKeys().get(0), actualNode, dataN.getAddress());
        K actualKey = actualNode.getKeys().get(0);
        if (actualKey == null) {
            return null;
        }

        do {
            array.add(dataNode.getKey());
            dataNode = this.getInOrder(dataNode);
        } while (dataNode != null);

        return array;
    }
    /*public ArrayList<K> getInOrderArrayLObject() {
        ArrayList<K> array = new ArrayList<>(this.count);
        int x = 0;
        if (this.count == 0) {
            return array;
        }
        BDataNode<K> dataN = this.getFirstNode();
        BNode<K> actualNode = dataN.getNode();

        if (actualNode == null) {
            System.out.println("Strom je prazdy.");
            return null;
        }

        BDataNode<K> dataNode = new BDataNode<K>(actualNode.getKeys().get(0), actualNode, dataN.getAddress());
        K actualKey = actualNode.getKeys().get(0);
        if (actualKey == null) {
            return null;
        }

        do {
            array.add(dataNode.getKey());
            dataNode = this.getInOrder(dataNode);
        } while (dataNode != null);

        return array;
    }*/

    //kontrola hlbky stromu
    public boolean controlDepth() {
        BDataNode<K> dataNode = getFirstNode();
        BNode<K> actualNode = dataNode.getNode();
        Long actualAddress = dataNode.getAddress();
        //Long pomAddress;
        BNode<K> pomNode = null;
        BNode<K> lastNode = getLastNode();
        int count = 0;
        if (isLeaf(actualNode)) {
            //pomAddress = actualAddress;
            pomNode = actualNode;
            while (true) {
                if (isRoot(pomNode)) {
                    break;
                } else {
                    count++;
                    //pomAddress = pomNode.getFather();
                    pomNode = fileTree.getData(pomNode.getFather(), new BNode<>(typeK));
                }
            }
        }
        while (!actualNode.myEquals(lastNode)) {
            dataNode = findInOrder(actualAddress, actualNode.getKeys().get(0));
            actualAddress = dataNode.getAddress();
            actualNode = dataNode.getNode();
            pomNode = actualNode;
            //pomAddress = actualAddress;
            if (isLeaf(pomNode)) {
                int count2 = 0;
                while (true) {
                    if (isRoot(pomNode)) {
                        break;
                    } else {
                        count2++;
                        //pomAddress = pomNode.getFather();
                        pomNode = fileTree.getData(pomNode.getFather(), new BNode<>(typeK));
                    }
                }
                if (count != count2) {
                    return false;
                }
            }
        }
        return true;
    }

    public void saveData() {
        File f = new File(this.nameFile + ".txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(f);

            writer.write(this.root == null ? "" : String.valueOf(this.root) + "\n");
            writer.write(this.count == 0 ? "" : String.valueOf(this.count) + "\n");
            writer.close();
            fileTree.saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        File file = new File(this.nameFile + ".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line == null || line == "") {
                return;
            }
            System.out.println(line);
            this.root = Long.valueOf(line);
            this.count = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }
        //return null;
    }

    public void clean() {
        this.root = null;
        this.count = 0;
        this.saveData();

        this.fileTree.clean();
    }
}
