// PizzaStore abstract hứa: "orderPizza(type) sẽ chạy đủ 4 bước prepare→box"
// Mọi subclass đều giữ đúng hợp đồng đó:

public class HuePizzaStore extends PizzaStore {
    @Override
    protected Pizza CreatePizza(String type) {
        switch (type) {
            case "cheese": return new HueCheesePizza();
            case "greek":  return new HueGreekPizza();
            default:       return new HuePepperonPizza();
        }
    }
}

// Caller hoàn toàn an tâm:
PizzaStore store = new HuePizzaStore();   // có thể thay SGPizzaStore, DNPizzaStore
store.orderPizza("cheese");               // hợp đồng không đổi theo subclass
