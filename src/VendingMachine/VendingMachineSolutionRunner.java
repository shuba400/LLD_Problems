package VendingMachine;

import SolutionRunner.SolutionRunner;

import java.util.*;


class Product{
    int id;
    String name;
    int price;
    int quantity;

    Product(int id,String name,int price,int quantity){
        this.id = id;
        this.name = name;
        this.price= price;
        this.quantity = quantity;
    }
}

class Inventory{
    List<Product> productsList;
    Inventory(){
        productsList = new ArrayList<>();
    }
}

class Order{
    HashMap<Product,Integer> purchasedProduct;

    Order(){
        purchasedProduct = new HashMap<>();
    }

    int getTotalPrice(){
        int total = 0;
        for(Map.Entry<Product,Integer> productIntegerEntry : purchasedProduct.entrySet()){
            total += productIntegerEntry.getValue() * productIntegerEntry.getKey().price;
        }
        return total;
    }
}


interface  PaymentStrategy{
    boolean makePayment(int amount);
}

class DummyPaymentStrategy implements PaymentStrategy {
    Random random;

    DummyPaymentStrategy() {
        random = new Random();
    }

    @Override
    public boolean makePayment(int amount) {
        return (random.nextInt() % 2 == 1);
    }
}


abstract class VendingState{
    VendingContext vendingContext;
    VendingState(VendingContext vendingContext){
        this.vendingContext = vendingContext;
    }

    abstract VendingState next(); //this is equivalent to pressing enter

    void pressEnter(){
        this.vendingContext.vendingState = next();
    }

    void addProduct(int productId){
        throw new RuntimeException("Not in a suitable state");
    }

    void removeProduct(int productId){
        throw new RuntimeException("Not in a suitable state");
    }

    boolean makePayment(){
        throw new RuntimeException("Not in a suitable state");
    }

    void updateInventory(){
        throw new RuntimeException("Not in a suitable state");
    }
}

class InitVendingState extends VendingState{
    InitVendingState(VendingContext vendingContext){
        super(vendingContext);
        System.out.println("Hello, welcome to the vending, please press enter to continue\n");
    }

    @Override
    public VendingState next() {
        return new InventorySelectionState(this.vendingContext);
    }


}

class InventorySelectionState extends VendingState{

    InventorySelectionState(VendingContext vendingContext) {
        super(vendingContext);
    }

    @Override
    VendingState next() {
        return new MakePaymentState(this.vendingContext);
    }

    void addProduct(int productId) {
        Product product = vendingContext.inventory.productsList.get(productId);
        int currentQuantity = 0;
        if (vendingContext.order.purchasedProduct.containsKey(product)) {
            currentQuantity += vendingContext.order.purchasedProduct.get(product);
        }
        if (currentQuantity + 1 <= product.quantity) {
            currentQuantity++;
            vendingContext.order.purchasedProduct.put(product, currentQuantity);
            System.out.printf("Added Product %s, new quantity %d\n", product.name, currentQuantity);
        } else {
            System.out.printf("Not able to add %s, since peak quantity is reached %d\n", product.name, currentQuantity);
        }
    }
}

class MakePaymentState extends VendingState {

    MakePaymentState(VendingContext vendingContext) {
        super(vendingContext);
        System.out.println("Please press enter to make payment\n");
    }

    boolean makePayment(){
        System.out.printf("Please make a payment of %d\n",this.vendingContext.order.getTotalPrice());
        return this.vendingContext.paymentStrategy.makePayment(this.vendingContext.order.getTotalPrice());
    }

    @Override
    VendingState next() {
        if(makePayment()){
            System.out.println("Payment Successful\n");
            return new PostProcessState(this.vendingContext);
        }
        System.out.println("Payment was not successful, kindly try again");
        return this;
    }
}


class PostProcessState extends VendingState {

    PostProcessState(VendingContext vendingContext){
        super(vendingContext);
        System.out.println("Please collect item from tray table");
    }

    void updateInventory(){
        for (Map.Entry<Product,Integer> productIntegerEntry : vendingContext.order.purchasedProduct.entrySet()){
            productIntegerEntry.getKey().quantity -= productIntegerEntry.getValue();
        }
        return;
    }

    @Override
    VendingState next() {
        updateInventory();
        return new InitVendingState(this.vendingContext);
    }
}

class VendingContext {
    PaymentStrategy paymentStrategy;
    VendingState vendingState;
    Inventory inventory;
    Order order;
    VendingContext(){
        paymentStrategy = new DummyPaymentStrategy();
        vendingState = new InitVendingState(this);
        inventory = new Inventory();
        order = new Order();
    }

    void pressEnter(){
        System.out.println("Moving to next window");
        vendingState.pressEnter();
    }
}


/*
 Vending Machine
 Startphase ---> Selection (Inventories) [make_payment] ---> PaymentState ---> HandleOrder

 Product --> ID,Name,Quantity,Price
 Inventory --> List<Product>
 Order --> Product,Quantity,int itemTotal()
 Payment --> booolean makePayment(price)
 */

public class VendingMachineSolutionRunner implements SolutionRunner {
    @Override
    public void run() {
        VendingContext vendingContext = new VendingContext();
        vendingContext.inventory.productsList.add(new Product(0,"banana",10,3));
        vendingContext.inventory.productsList.add(new Product(1,"apple",10,3));
        vendingContext.inventory.productsList.add(new Product(2,"cake",10,3));

        vendingContext.pressEnter();

        vendingContext.vendingState.addProduct(0);
        vendingContext.vendingState.addProduct(0);
        vendingContext.vendingState.addProduct(0);
        vendingContext.vendingState.addProduct(0);
        vendingContext.pressEnter();

        vendingContext.pressEnter();
        while (vendingContext.vendingState instanceof MakePaymentState) {
            System.out.println("Retrying payment.....");
            vendingContext.vendingState.pressEnter();
        }
        vendingContext.pressEnter();
    }
}
