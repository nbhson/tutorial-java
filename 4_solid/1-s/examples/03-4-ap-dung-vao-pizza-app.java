// PizzaStore KHONG tu tao pizza — giao cho subclass qua factory method
public abstract class PizzaStore {
    public void orderPizza(String type) {
        Pizza pizza = CreatePizza(type); // tao pizza la trach nhiem cua subclass
        pizza.prepare();                 // quy trinh la trach nhiem cua Pizza
        pizza.bake();
        pizza.cut();
        pizza.box();
    }
    protected abstract Pizza CreatePizza(String type);
}
