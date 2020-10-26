public class HashTable {

    private HashObject[] hashTable;
    private int m;
    private int n;
    private String type;
    private int totalDuplicates;
    private int totalProbes;
    private int totalElements;

    public HashTable(int m, String type) {
        this.m = m;
        this.n = 0;
        this.totalProbes = 0;
        this.totalDuplicates = 0;
        this.totalElements = 0;
        this.hashTable = new HashObject[m];
        this.type = type;
    }

    private int hash1(HashObject hashObject) {
        int code = hashObject.getKey().hashCode();
        return code % this.m;
    }

    private int hash2(HashObject hashObject) {
        int code = hashObject.getKey().hashCode();
        return (1 + (code % (m - 2)));
    }

    public void add(HashObject hashObject) {
        if (n >= m)
            return;
        totalElements++;
        switch (this.type) {
            case "linear":
                int slot = hash1(hashObject);
                if (slot < 0) {
                    slot += m;
                }

                int count = 0;
                while (true) {
                    int nextSlot = (slot + count) % m;

                    if (nextSlot < 0) {
                        nextSlot += m;
                    }

                    if (hashTable[nextSlot] != null && hashTable[nextSlot].equals(hashObject)) {
                        hashTable[nextSlot].incrementDuplicateCount();
                        totalDuplicates++;
                        return;
                    }
//                    totalProbes++;
                    hashObject.incrementProbeCount();

                    if (hashTable[nextSlot] == null) {
                        hashTable[nextSlot] = hashObject;
                        n++;
                        totalProbes += hashObject.getProbeCount();
                        break;
                    }
                    count++;
                }
                break;

            case "double":
                int slot1 = hash1(hashObject);
                if (slot1 < 0) {
                    slot1 += m;
                }

                int slot2 = hash2(hashObject);
                if (slot2 < 0) {
                    slot2 += m;
                }

                int i = 0;
                while (true) {
                    int nextSlot = (slot1 + (i * slot2)) % m;

                    if (nextSlot < 0) {
                        nextSlot += m;
                    }

                    if (hashTable[nextSlot] == null) {
                        hashTable[nextSlot] = hashObject;
                        n++;
                        hashObject.setProbeCount(i + 1);
                        totalProbes += i+1;
                        break;
                    } else if (hashTable[nextSlot] != null && hashTable[nextSlot].equals(hashObject)) {
                        hashTable[nextSlot].incrementDuplicateCount();
                        totalDuplicates++;
                        return;
                    }
                    i++;
                }
                break;
        }
    }

    public double getLoad() {
        return (double) n / (double) m;
    }

    public String getType() {
        return this.type;
    }

    public int getTotalDuplicates() {
        return this.totalDuplicates;
    }

    public int getTotalProbes() {
        return this.totalProbes;
    }

    public int getTotalElements() {
        return this.totalElements;
    }

    public int getN() {
        return this.n;
    }

    @Override
    public String toString() {
        String toString = "";

        for (int index = 0; index < m; index++) {
            toString += "table[" + index + "]: ";
            if (hashTable[index] == null) {
                toString += "null\n";
            } else {
                toString += (hashTable[index].toString() + "\n");
            }
        }
        return toString;
    }

}
