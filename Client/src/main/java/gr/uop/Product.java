package gr.uop;

public class Product {

    private int product_id = 0;
    private String product_name;
    private String car_price;
    private String jeep_price;
    private String motorbike_price;

    public Product(){
        this.product_id = 0;
        this.product_name = "";
        this.car_price = "";
        this.jeep_price = "";
        this.motorbike_price = "";
    }

    public Product(Integer product_id, String product_name, String car_price, String jeep_price, String motorbike_price){
        this.product_id = product_id;
        this.product_name = product_name;
        this.car_price = car_price;
        this.jeep_price = jeep_price;
        this.motorbike_price = motorbike_price;
    }

    public int getProduct_id() {
        return this.product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return this.product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCar_price() {
        return this.car_price;
    }

    public void setCar_price(String car_price) {
        this.car_price = car_price;
    }

    public String getJeep_price() {
        return this.jeep_price;
    }

    public void setJeep_price(String jeep_price) {
        this.jeep_price = jeep_price;
    }

    public String getMotorbike_price() {
        return this.motorbike_price;
    }

    public void setMotorbike_price(String motorbike_price) {
        this.motorbike_price = motorbike_price;
    }
}
