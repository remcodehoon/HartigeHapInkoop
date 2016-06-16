package domain;

public class OrderRow {
    
    private Ingredient ingredient;
    private Order order;
    private Supplier supplier;
    private int amount;  
    
    public OrderRow(Ingredient ingredient, Order order, Supplier supplier, int amount){
        this.ingredient = ingredient;
        this.order = order;
        this.supplier = supplier;
        this.amount = amount;
    }
    
    public Ingredient getIngredient(){
        return ingredient;
    }
    
    public Order getOrder(){
        return order;
    }
    
    public Supplier getSupplier(){
        return supplier;
    }
    
    public int getAmount(){
        return amount;
    }
    
    public void setIngredient(Ingredient ing){
        ingredient = ing;
    }
    
    public void setOrder(Order order){
        this.order = order;
    }
    
    public void setSupplier(Supplier sup){
        supplier = sup;
    }
    
    public void setAmount(int amount){
        this.amount = amount;
    }
}






