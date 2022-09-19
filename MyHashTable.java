// This is a blank file, please complete your solution here.
public class MyHashTable extends HashTable {

    protected int hashFunction(String key) {
        int stringLength = key.length();
        int asciiValue = 0;
        for (int i = 0; i < stringLength; i++) { // loop through characters of string and add up ascii values
            char letter = key.charAt(i);
            asciiValue = asciiValue + (int) letter;
        }
        int hashFunction = asciiValue % storageArray.length; // modulo to calculate hash function value
        return hashFunction;
    }

    protected void resizeMap(int newSize) {
        String[] newStorage = new String[newSize]; // create string array of new size
        if (newSize > storageArray.length) { // increase size of storage array
            String[] tempArray = storageArray; // storage storage array values in temp variable
            storageArray = newStorage; // assign array of new size to be storageArray
            loadFactor = (double) numItems / (double) storageArray.length;
            for (int i = 0; i < tempArray.length; i++) {
                if (tempArray[i] == getPlaceholder()) {
                    tempArray[i] = null;
                }
                String name = tempArray[i];
                if (name != null) {
                    add(name); // remap old values from previous hash table to new sized hash table
                    numItems--;// not adding new items so decrement to counter incremnt in add method
                }
            }
        } else {
            String[] tempStorage = storageArray;
            storageArray = newStorage;
            for (int i = 0; i < tempStorage.length; i++) {
                if (tempStorage[i] == getPlaceholder()) {
                    tempStorage[i] = null;
                }
                String name = tempStorage[i];
                if (name != null) {
                    add(name);// remap old values from previous hash table to new sized hash table
                    numItems--;
                }
            }
        }
        loadFactor = (double) numItems / (double) storageArray.length; // calcualte new loadFactor
    }

    public boolean add(String name) {
        int hashFunctionValue = hashFunction(name);
        if (loadFactor >= 0.7) {
            resizeMap(storageArray.length * 2); // remap as load factor is 0.7 or higher
        }
        while (storageArray[hashFunctionValue] != null) { // string in that space at hash table
            if (storageArray[hashFunctionValue] == name) { // hash table contains same string
                return false;
            }
            hashFunctionValue++; // check next space
            if (hashFunctionValue >= storageArray.length) {
                resizeMap(storageArray.length * 2); // reached last key in table to resize
            }
        }
        storageArray[hashFunctionValue] = name;
        numItems++;
        loadFactor = (double) numItems / (double) storageArray.length;
        return true;
    }

    public boolean remove(String name) {
        int hashFunctionValue = hashFunction(name);
        for (int i = hashFunctionValue; i < storageArray.length; i++) {
            if (storageArray[hashFunctionValue] == name) { // if name is the same as the value to the key in the table
                storageArray[hashFunctionValue] = getPlaceholder(); // replace value with placeholder
                numItems--;
                loadFactor = (double) numItems / (double) storageArray.length;
                if (loadFactor <= 0.2) { // check load factor after removal
                    if (storageArray.length != 10) {
                        resizeMap(storageArray.length / 2);
                    }
                }
                return true;
            }
        }
        return false; // value not found so not removed
    }

    public boolean search(String name) {
        int hashFunctionValue = hashFunction(name);
        for (int i = hashFunctionValue; i < storageArray.length; i++) { // start search at hash function key for the
                                                                        // name
            if (storageArray[hashFunctionValue] == name) { // name is same as value at key
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        MyHashTable table = new MyHashTable();
        table.add("Hello");
        table.add("Cheese");
        table.add("Apple");
        table.add("Banana");
        table.remove("Apple");
        System.out.println(table.print());
        table.add("Pencil");
        System.out.println(table.print());
        table.remove("Hello");
        System.out.println(table.print());
        table.remove("Pencil");
        System.out.println(table.print());
        System.out.println(table.add("What"));
        table.add("Hi");
        table.add("Hii");
        table.add("Hiii");
        table.add("Hiiii");
        System.out.println(table.print());
        table.remove("Hiiii");
        System.out.println(table.print());
    }
}