import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.*;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.event.*;
import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.stage.FileChooser.*;
import java.util.*;
import javafx.scene.input.KeyEvent;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import java.util.Scanner;
import java.io.InputStream;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TableColumn;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import java.io.FileOutputStream;
import javafx.scene.*;
import java.io.OutputStream;
import javafx.scene.control.Label;

public class CustomerProductController implements Initializable
{
    @FXML
    private JFXButton wishlistView;
    @FXML
    private ImageView productImage;
    @FXML
    private Label productName;
    @FXML
    private Label sellerName;
    @FXML
    private JFXTextArea productDescription;
    @FXML
    private JFXButton wishlist;
    @FXML
    private JFXButton addToCart;
    @FXML
    private JFXButton buyNow;
    @FXML
    private JFXButton home;
    @FXML
    private JFXButton shoppingCart;
    @FXML
    private JFXButton logOut;
    @FXML
    private TableView<ProductReview> reviewTable;
    @FXML
    private TableColumn<ProductReview, String> reviewerName;
    @FXML
    private TableColumn<ProductReview, String> review;
    @FXML
    private Label price;
    @FXML
    private TextField userReview;
    @FXML
    private JFXButton addReview;
    private Image im;
    public int productId;
    public int customerId;
    private final ObservableList<CustomerProduct> cData=FXCollections.observableArrayList();
    public void setCustomerId(int id)
    {
        this.customerId=id;
    }
    public TableView<ProductReview> getReviewTable()
    {
        return this.reviewTable;
    }
    public void setProductName(String name)
    {
        this.productName.setText(name);
    }
    public void setPrice(double pr)
    {
        this.price.setText("Price: $"+String.valueOf(pr));
    }
    public void setSellerName(String s)
    {
        this.sellerName.setText(s);
    }
    public void setProductId(int id)
    {
        this.productId=id;
    }
    public ImageView getProductImage()
    {
        return this.productImage;
    }
    public void setProductDescription(String description)
    {
        this.productDescription.setText(description);
    }
    @FXML
    void onAddReview(ActionEvent event)
    {

    }
    @FXML
    void onAddToCart(ActionEvent event) 
    {

    }
    @FXML
    void onWishlistView(ActionEvent event)
    {
        System.out.println("WISHES"+customerId);
    }
    @FXML
    void onBuyNow(ActionEvent event)
    {

    }
    @FXML
    void onHome(ActionEvent event)
    {
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement(); 
            PreparedStatement psUserImg=conn.prepareStatement("select IMAGE from Person_Image where ID=?");
            PreparedStatement psProCount=null;
            PreparedStatement makeTable=null;
            Statement stName=conn.createStatement();
            ResultSet rsid=stName.executeQuery("select * from person where id="+String.valueOf(customerId));
            psProCount=conn.prepareStatement("select count(*) from product");
            makeTable=conn.prepareStatement("select ProI.Product_image,Pro.Product_Id,Pro.Product_name,Pro.category,Pro.Seller_ID,Pro.price from Product Pro,Product_Image ProI where Pro.Product_Id=ProI.Product_Id");
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("CustomerView.fxml"));
            Stage stage=(Stage)home.getScene().getWindow();
            Parent root=loader.load();
            CustomerViewController cvc=(CustomerViewController)loader.getController();
            ResultSet rsProCount=psProCount.executeQuery();
            if(rsProCount.next());
            int i=0,n=rsProCount.getInt(1);
            cvc.setCustomerId(customerId);
            ImageView ivArr[]=new ImageView[n];
            CustomerProduct cpArr[]=new CustomerProduct[n];
            ResultSet rsMakeTable=makeTable.executeQuery();
            psUserImg.setInt(1, customerId);
            ResultSet rsUserImg=psUserImg.executeQuery();
            if(rsUserImg.next())
            {
                InputStream isUser=rsUserImg.getBinaryStream("IMAGE");
                OutputStream osUser=new FileOutputStream(new File("user_img.jpg"));
                byte[] content=new byte[1024];
                int size=0;
                while((size=isUser.read(content))!=-1)
                {
                    osUser.write(content,0,size);
                }
                im=new Image("file:user_img.jpg",100,150,true,true);
                ImageView ivUser=cvc.getProfileImg();
                ivUser.setImage(im);
                isUser.close();
                osUser.close();
            }
            while(rsMakeTable.next())
            {
                InputStream is=rsMakeTable.getBinaryStream("PRODUCT_IMAGE");
                OutputStream os=new FileOutputStream(new File("product_img.jpg"));
                byte[] content=new byte[1024];
                int size=0;
                while((size=is.read(content))!=-1)
                {
                    os.write(content,0,size);
                }
                os.close();
                is.close();
                im=new Image("file:product_img.jpg",100,150,true,true);
                ivArr[i]=new ImageView();
                ivArr[i].setImage(im);
                String sellerName="";
                ResultSet rsSellerName=stmt.executeQuery("select name from person where id="+String.valueOf(rsMakeTable.getInt("SELLER_ID")));
                if(rsSellerName.next())
                {
                    sellerName=rsSellerName.getString("Name");
                }
                cpArr[i]=new CustomerProduct(ivArr[i], rsMakeTable.getString("PRODUCT_NAME"), rsMakeTable.getDouble("PRICE"), rsMakeTable.getString("CATEGORY"), sellerName, rsMakeTable.getInt("PRODUCT_ID"));
                cData.add(cpArr[i]);
                i++;
            }
            TableView <CustomerProduct>tv=new TableView<>();
            cvc.setData(cData);
            tv=cvc.getTableView();
            tv.setItems(cData);
            if(rsid.next());
            cvc.setCustomerName("Hello, "+rsid.getString("NAME"));
            stage.setScene(new Scene(root,1000,600));
            stage.show();
            stmt.close();
            stName.close();
            psProCount.close();
            makeTable.close();
            psUserImg.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    void onLogOut(ActionEvent event)
    {
        try
        {
            Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Stage stage=(Stage)logOut.getScene().getWindow();
            stage.setScene(new Scene(root,700,400));
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    void onShoppingCart(ActionEvent event)
    {

    }
    @FXML
    void onWishlist(ActionEvent event)
    {

    }
    @Override
    public void initialize(URL url,ResourceBundle rb)
    {
        reviewerName.setCellValueFactory(new PropertyValueFactory<>("reviewerName"));
        review.setCellValueFactory(new PropertyValueFactory<>("review"));
    }
}