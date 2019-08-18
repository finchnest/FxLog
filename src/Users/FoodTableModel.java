
package Users;


public class FoodTableModel {
    
    String foodName,provider,type;
    int price,available;
    float rating;

    public FoodTableModel(String foodName, String provider, int price, String type, float rating, int available) {
        this.foodName = foodName;
        this.provider = provider;
        this.type = type;
        this.price = price;
        this.available = available;
        this.rating = rating;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    
    
    
}
