import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class ProductReview
{
    private final SimpleStringProperty reviewerName;
    private final SimpleStringProperty review;
    ProductReview(String reviewerName,String review)
    {
        this.reviewerName=new SimpleStringProperty(reviewerName);
        this.review=new SimpleStringProperty(review);
    }
    public String getReviewerName()
    {
        return this.reviewerName.get();
    }
    public void setReviewerName(String name)
    {
        this.reviewerName.set(name);
    }
    public String getReview()
    {
        return this.review.get();
    }
    public void setReview(String review)
    {
        this.review.set(review);
    }
}