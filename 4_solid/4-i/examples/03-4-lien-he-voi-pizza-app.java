// PizzaStore: hop dong nho, vua khit — khong method nao thua
public abstract class PizzaStore {
    public void orderPizza(String type) { /* quy trinh chung */ }
    protected abstract Pizza CreatePizza(String type); // duy nhat subclass phai lam
}
