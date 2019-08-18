
package Provider;


public class FoodModel {
    
    String foodN;
    int price;
    String foodT;
    int ratersCount;
    int foodCount;
    float rating;

    public FoodModel(String foodN, int price, String foodT, float rating, int ratersCount,int foodCount) {
        this.foodN = foodN;
        this.price = price;
        this.foodT = foodT;
        this.ratersCount = ratersCount;
        this.foodCount = foodCount;
        this.rating = rating;
    }

    public String getFoodN() {
        return foodN;
    }

    public double getPrice() {
        return price;
    }

    public String getFoodT() {
        return foodT;
    }

    public int getRatersCount() {
        return ratersCount;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public double getRating() {
        return rating;
    }
    
    
    
}
