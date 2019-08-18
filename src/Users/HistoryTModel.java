
package Users;


public class HistoryTModel {

    String provider,foodName,buyer;

    public HistoryTModel(String buyer, String provider, String foodName) {
        this.provider = provider;
        this.foodName = foodName;
        this.buyer = buyer;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    

    
}
