package domain;

public class OrderRow {
    
    private Ingredient ingredient;
    private int amount;
    private double prize;  
    private Order order;
    
    public OrderRow(Ingredient ingredient, int amount, double prize, Order order){
        this.ingredient = ingredient;
        this.amount = amount;
        this.prize = prize;
        this.order = order;
    }
    
    public Ingredient getIngredient(){
        return ingredient;
    }
    
    public int getAmount(){
        return amount;
    }
    
    public double getPrize(){
        return prize;
    }
    
    public Order getOrder(){
        return order;
    }
    
    
    public void setIngredient(Ingredient ing){
        ingredient = ing;
    }
    
    public void setAmount(int amount){
        this.amount = amount;
    }
    
    public void setPrize(double prize){
        this.prize = prize;
    }
    
    public void setOrder(Order order){
        this.order = order;
    }
}






