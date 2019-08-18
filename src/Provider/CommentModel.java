
package Provider;

public class CommentModel {
    
    String foodItem,commentor,producer,comment;

    public CommentModel(String foodItem, String commentor, String producer, String comment) {
        this.foodItem = foodItem;
        this.commentor = commentor;
        this.producer = producer;
        this.comment = comment;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getCommentor() {
        return commentor;
    }

    public void setCommentor(String commentor) {
        this.commentor = commentor;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
    
}
