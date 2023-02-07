package edu.hust.vn.entity;

public class Lock extends Entity{
    private int barCode;
    private int bikeId;
    private int dockId;
    private boolean LOCK_STATUS;

    public Lock(int barCode, int bikeId, int dockId, boolean LOCK_STATUS) {
        this.barCode = barCode;
        this.bikeId = bikeId;
        this.dockId = dockId;
        this.LOCK_STATUS = LOCK_STATUS;
    }

    public int getBarCode() {
        return barCode;
    }

    public void setBarCode(int barCode) {
        this.barCode = barCode;
    }

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public int getDockId() {
        return dockId;
    }

    public void setDockId(int dockId) {
        this.dockId = dockId;
    }

    public boolean isLOCK_STATUS() {
        return LOCK_STATUS;
    }

    public void setLOCK_STATUS(boolean LOCK_STATUS) {
        this.LOCK_STATUS = LOCK_STATUS;
    }
}
