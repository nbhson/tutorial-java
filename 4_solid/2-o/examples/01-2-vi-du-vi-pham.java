// ✗ moi khi them loai pizza moi phai sua switch nay — sua code da chay
class PizzaStore {
    Pizza orderPizza(String type) {
        Pizza pizza;
        if (type.equals("cheese")) {
            pizza = new DNCheesePizza();
        } else if (type.equals("greek")) {
            pizza = new DNGreekPizza();
        } else if (type.equals("pepperoni")) {
            pizza = new DNPepperonPizza();
        } else {
            throw new IllegalArgumentException("Khong biet loai: " + type);
        }
        pizza.prepare(); pizza.bake(); pizza.cut(); pizza.box();
        return pizza;
    }
}
