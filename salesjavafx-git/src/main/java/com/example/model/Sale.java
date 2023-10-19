package com.example.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.List;

public class Sale {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> dateSale = new SimpleObjectProperty<>();

    private final DoubleProperty value = new SimpleDoubleProperty();

    private List<ItemSale> itemSale;

    private Client client;


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public LocalDate getDateSale() {
        return dateSale.get();
    }

    public ObjectProperty<LocalDate> dateSaleProperty() {
        return dateSale;
    }

    public void setDateSale(LocalDate dateSale) {
        this.dateSale.set(dateSale);
    }

    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public List<ItemSale> getItemSale() {
        return itemSale;
    }

    public void setItemSale(List<ItemSale> itemSale) {
        this.itemSale = itemSale;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
