public class HashObject<T> {

    private int duplicateCount;
    private int probeCount;
    private final T key;

    public HashObject(T key) {
        this.key = key;
        this.duplicateCount = 0;
        this.probeCount = 0;
    }

    public T getKey() {
        return this.key;
    }

    public void incrementDuplicateCount() {
        this.duplicateCount++;
    }

    public void incrementProbeCount() {
        this.probeCount++;
    }

    public void setProbeCount(int i) { this.probeCount = i; }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this.getClass().equals(o.getClass())) {
            return (this.getKey().equals(((HashObject<T>) o).getKey()));
        }
        return false;
    }

    @Override
    public String toString() {
        return key.toString() + " " + this.duplicateCount + " " + this.probeCount;
    }

    public int getProbeCount() {
        return this.probeCount;
    }
}
