package com.example.trabajo_final.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "historial")
public class Historial implements Serializable {

    @Id
    @GeneratedValue
    private Integer historyId;
    @OneToOne
    private Usuario user;
    @ManyToMany
    private Set<Articulo> browsingHistory;
    private ArrayList<Integer> amount;
    @ManyToMany
    private Set<Articulo> shoppingCart;

    // Constructors
    public Historial(){

    }

    public Historial(Usuario user) {
        this.setUser(user);
        this.setBrowsingHistory(new HashSet<>());
        this.setShoppingCart(new HashSet<>());
        this.setAmount(new ArrayList<>());
    }

    // Getters and Setters
    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Set<Articulo> getBrowsingHistory() {
        return browsingHistory;
    }

    public void setBrowsingHistory(Set<Articulo> browsingHistory) {
        this.browsingHistory = browsingHistory;
    }

    public Set<Articulo> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(Set<Articulo> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public ArrayList<Integer> getAmount() {
        return amount;
    }

    public void setAmount(ArrayList<Integer> amount) {
        this.amount = amount;
    }
}
