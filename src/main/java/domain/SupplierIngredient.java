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
public class SupplierIngredient {
    private Ingredient ingredient;
    private Supplier supplier;
    private int quantity;
    private double price;  
    
    public SupplierIngredient(Ingredient ingredient, Supplier supplier, int quantity, double price){
        this.ingredient = ingredient;
        this.supplier = supplier;
        this.quantity = quantity;
        this.price = price;
    }
    
    public Ingredient getIngredient(){
        return ingredient;
    }
    
    public Supplier getSupplier(){
        return supplier;
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public double getPrice(){
        return price;
    }
    
    public void setIngredient(Ingredient ing){
        ingredient = ing;
    }
    
    public void setSupplier(Supplier supplier){
        this.supplier = supplier;
    }
    
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    
    public void setPrice(double price){
        this.price = price;
    }
    
    @Override
    public String toString(){
        return this.getIngredient().getName() + "  " + this.getPrice() +  " " + this.getQuantity() + "  " + this.getSupplier().getName();
    }
}
