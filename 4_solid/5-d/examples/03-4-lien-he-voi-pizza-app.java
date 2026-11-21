// Store (cap cao) khong biet ton tai DNCheesePizza (chi tiet)
public abstract class PizzaStore {
    public void orderPizza(String type) {
        Pizza pizza = CreatePizza(type);  // chi biet Pizza (abstraction)
        pizza.prepare();                  // goi qua hop dong cua Pizza
        pizza.bake();
        pizza.cut();
        pizza.box();
    }
    protected abstract Pizza CreatePizza(String type);
}
