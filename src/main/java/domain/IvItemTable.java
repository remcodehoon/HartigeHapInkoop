/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Mart
 */
public class IvItemTable {
    private int itemId, supId, amount;
    private double price;
    private String name;
    
    public IvItemTable(int itemId, int supId, double price, int amount, String name){
        this.itemId = itemId;
        this.supId = supId;
        this.price = price;
        this.amount = amount;
        this.name = name;
    }
    
    public int getItemId(){
        return itemId;
    }
    
    public int getSupId(){
        return supId;
    }
    
    public int getAmount(){
        return amount;
    }
    
    public double getPrice(){
        return price;
    }
    
    public String getName(){
        return name;
    }
    
    public void setItemId(int itemId){
        this.itemId = itemId;
    }
    
    
    public void setSupId(int supId){
        this.supId = supId;
    }
    
    public void setAmount(int amount){
        this.amount = amount;
    }
    
    public void setPrice(int price){
        this.price = price;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
