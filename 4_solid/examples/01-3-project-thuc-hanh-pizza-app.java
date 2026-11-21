// Main.java
HuePizzaStore huePizzaStore = new HuePizzaStore();
huePizzaStore.orderPizza("cheese");

// PizzaStore.java (abstract)
public void orderPizza(String type) {
    Pizza pizza = CreatePizza(type);  // phương thức factory — mỗi store tự tạo pizza riêng
    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();
}
