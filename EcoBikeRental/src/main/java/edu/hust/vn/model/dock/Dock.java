package edu.hust.vn.model.dock;

import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Dock {
    private int id;
    private String name;
    private String address;
    private float area;
    private int capacity;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
