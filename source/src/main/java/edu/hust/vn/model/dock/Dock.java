package edu.hust.vn.model.dock;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Dock {
    private int id;
    private StringProperty name = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private FloatProperty area = new SimpleFloatProperty();
    private IntegerProperty capacity = new SimpleIntegerProperty();

    private ObservableList<Lock> locks;

    public Dock(){
        locks = FXCollections.observableArrayList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public FloatProperty areaProperty() {
        return area;
    }

    public float getArea() {
        return area.get();
    }

    public void setArea(float area) {
        this.area.set(area);
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public int getCapacity() {
        return capacity.get();
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public Lock getLockByBarCode(String barCode){
        for(Lock l : locks){
            if(l.getBarCode().equals(barCode)){
                return l;
            }
        }
        return null;
    }
    public ObservableList<Lock> getLocks() {
        return locks;
    }

    public void setLocks(List<Lock> locks) {
        locks.forEach(lock -> {
            this.locks.add(lock);
            lock.setDock(this);
        });
    }

    public void updateLocks(List<Lock> locks){
        locks.forEach(lock -> {
            Lock currentLock = null;
            for(Lock l : this.locks){
                if(l.getId() == lock.getId()){
                    currentLock = l;
                    break;
                }
            }
            if(currentLock != null){
                currentLock.setDockId(lock.getDockId());
                currentLock.setBike(lock.getBike());
            }else{
                this.locks.add(lock);
                lock.setDock(this);
            }
        });
    }

    public int getAvailableLots(){
        int count = 0;
        for(Lock lock : locks){
            if(lock.getState() == Lock.LOCK_STATE.RELEASED) count++;
        }
        return count;
    }

    public int getAvailableBikes(){
        return locks.size() - getAvailableLots();
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Dock){
            if(((Dock)o).getId() == this.id){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "Dock: " + this.name;
    }
}
