import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;
public class CustomerProduct
{
    private ImageView image;
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;
    private final SimpleStringProperty category;
    private final SimpleStringProperty seller;
    private final SimpleIntegerProperty productId;
    CustomerProduct(ImageView im,String na,Double pr,String ca,String se,Integer pid)
    {
        this.image=im;
        this.name=new SimpleStringProperty(na);
        this.price=new SimpleDoubleProperty(pr);
        this.category=new SimpleStringProperty(ca);
        this.seller=new SimpleStringProperty(se);
        this.productId=new SimpleIntegerProperty(pid);
    }
    public ImageView getImage()
    {
        return image;
    }
    public void setImage(ImageView photo)
    {
        this.image=photo;
    }
    public String getName()
    {
        return name.get();
    }
    public void setName(String name)
    {
        this.name.set(name);
    }
    public double getPrice()
    {
        return price.get();
    }
    public void setPrice(double price)
    {
        this.price.set(price);
    }
    public String getCategory()
    {
        return category.get();
    }
    public void setCategory(String category)
    {
        this.category.set(category);
    }
    public String getSeller()
    {
        return seller.get();
    }
    public void setSeller(String seller)
    {
        this.seller.set(seller);
    }
    public int getProductId()
    {
        return productId.get();
    }
    public void setProductId(int productId)
    {
        this.productId.set(productId);
    }
}