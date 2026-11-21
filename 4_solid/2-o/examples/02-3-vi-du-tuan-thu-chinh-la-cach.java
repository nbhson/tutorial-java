// ✅ abstract class "dong" voi sua doi, "mo" voi ke thua
public abstract class PizzaStore {
    public void orderPizza(String type) {
        Pizza pizza = CreatePizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }

    protected abstract Pizza CreatePizza(String type); // "o cam" — diem mo rong
}

// Them khu vuc moi = TAO CLASS MOI, khong sua PizzaStore
public class NghePizzaStore extends PizzaStore {
    @Override
    protected Pizza CreatePizza(String type) {
        return switch (type) {
            case "cheese" -> new NgheCheesePizza();
            case "greek"  -> new NgheGreekPizza();
            default       -> new NghePepperonPizza();
        };
    }
}
