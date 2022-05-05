package ru.cynteka;

class Coordinates {
    private final int nCoord;
    private final int mCoord;

    public Coordinates(int nCoord, int mCoord) {
        this.nCoord = nCoord;
        this.mCoord = mCoord;
    }

    public int getNCoord() {
        return nCoord;
    }

    public int getMCoord() {
        return mCoord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (mCoord != that.mCoord) return false;
        return nCoord == that.nCoord;
    }

    @Override
    public int hashCode() {
        int result = mCoord;
        result = 31 * result + nCoord;
        return result;
    }

    @Override
    public String toString() {
        return "Coordinates{" + "nCoord=" + nCoord + ", mCoord=" + mCoord + '}';
    }
}