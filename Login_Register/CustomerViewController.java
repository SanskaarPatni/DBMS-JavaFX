import com.jfoenix.controls.JFXButton;
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
public class CustomerViewController implements Initializable
{
    @FXML
    private JFXButton wishlistView;
    @FXML
    private ImageView profileimg;
    @FXML
    private Label customername;
    @FXML
    private TextField search;
    @FXML
    private JFXButton orders;
    @FXML
    private JFXButton shoppingCart;
    @FXML
    private JFXButton logOut;
    @FXML
    private JFXTextField rating;
    @FXML
    private TableView<CustomerProduct> productTable;
    @FXML
    private TableColumn<CustomerProduct, String> image;
    @FXML
    private TableColumn<CustomerProduct, String> name;
    @FXML
    private TableColumn<CustomerProduct, Double> price;
    @FXML
    private TableColumn<CustomerProduct, String> category;
    @FXML
    private TableColumn<CustomerProduct, String> seller;
    @FXML
    private TableColumn<CustomerProduct, Integer> productId;
    private final ObservableList<ProductReview> reviewData=FXCollections.observableArrayList();
    public int customerId;
    private ObservableList<CustomerProduct> data=FXCollections.observableArrayList();
    public void setCustomerId(int id)
    {
        customerId=id;
    }
    public void setData(ObservableList<CustomerProduct> d)
    {
        this.data=d;
    }
    public void setCustomerName(String s)
    {
        customername.setText(s);
    }
    public ImageView getProfileImg()
    {
        return this.profileimg;
    }
    public TableView<CustomerProduct> getTableView()
    {
        return this.productTable;
    }
    @FXML
    void onWishlistView(ActionEvent event)
    {
        System.out.println("WISHES"+customerId);
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
    void onOrders(ActionEvent event)
    {

    }
    @FXML
    void onSearch(KeyEvent event)
    {
        FilteredList<CustomerProduct> filteredData=new FilteredList<>(data,b->true);
        search.textProperty().addListener((observable,oldValue,newValue)->
        {
            filteredData.setPredicate(product->
            {
                if(newValue==null||newValue.isEmpty())
                {
                    return true;
                }
                String lcf=newValue.toLowerCase();
                if(product.getName().toLowerCase().indexOf(lcf)!=-1)
                    return true;
                else if(product.getCategory().toLowerCase().indexOf(lcf)!=-1)
                    return true;
                else if(String.valueOf(product.getPrice()).indexOf(lcf)!=-1)
                    return true;
                else if(product.getSeller().toLowerCase().indexOf(lcf)!=-1)
                    return true;
                else return false;
            });
        });
        SortedList<CustomerProduct> sortedData=new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedData);
    }
    @FXML
    void onSelect(MouseEvent event)
    {
        Parent root=null;
        FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("CustomerProductPage.fxml"));
        try
        {
            root=(Parent)loader.load();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Logger.getLogger(CustomerViewController.class.getName()).log(Level.SEVERE,null,e);
        }
        CustomerProductController cpc=new CustomerProductController();
        cpc=(CustomerProductController)loader.getController();
        int id=productTable.getSelectionModel().getSelectedItem().getProductId();
        cpc.setProductId(id);
        cpc.setCustomerId(customerId);
        ImageView productImage=cpc.getProductImage();
        TableView<ProductReview> reviewTable=cpc.getReviewTable();
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement();
            Statement stReview=conn.createStatement();
            Statement stDescription=conn.createStatement();
            Statement stCount=conn.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT PRODUCT_IMAGE from Product_image where product_id="+String.valueOf(id));
            if(rs.next())
            {
                InputStream is=rs.getBinaryStream("PRODUCT_IMAGE");
                OutputStream os=new FileOutputStream(new File("product_img.jpg"));
                byte[] content=new byte[1024];
                int size=0;
                while((size=is.read(content))!=-1)
                {
                    os.write(content,0,size);
                }
                os.close();
                is.close();
                Image im=new Image("file:product_img.jpg",100,150,true,true);
                productImage.setImage(im);
            }
            ResultSet rsCount=stCount.executeQuery("SELECT count(*) from reviews where product_id="+String.valueOf(id));
            if(rsCount.next());
            int n=rsCount.getInt(1);
            ResultSet rsReview=stReview.executeQuery("Select Per.NAME,Rev.COMMENTS from Person Per,Reviews Rev where Rev.CUSTOMER_ID=Per.ID and Product_ID="+String.valueOf(id));
            ProductReview arr[]=new ProductReview[n];
            int i=0;
            while(rsReview.next())
            {
                arr[i]=new ProductReview(rsReview.getString("NAME"),rsReview.getString("COMMENTS"));
                reviewData.add(arr[i]);
                i++;
            }
            ResultSet rsDescription=stDescription.executeQuery("select description from product where product_id="+String.valueOf(id));
            if(rsDescription.next());
            cpc.setProductDescription(rsDescription.getString("DESCRIPTION"));
            reviewTable.setItems(reviewData);
            stCount.close();
            stReview.close();
            stDescription.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        cpc.setProductName(productTable.getSelectionModel().getSelectedItem().getName());
        cpc.setSellerName(productTable.getSelectionModel().getSelectedItem().getSeller());
        cpc.setPrice(productTable.getSelectionModel().getSelectedItem().getPrice());
        Stage stage=(Stage)profileimg.getScene().getWindow();
        stage.setScene(new Scene(root,800,610));
        stage.show();
    }
    @FXML
    void onShoppingCart(ActionEvent event)
    {

    }
    @FXML
    void onUpload(MouseEvent event)
    {
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement();
            PreparedStatement psImgUpdate=null;
            PreparedStatement psGetImg=conn.prepareStatement("select image from person_image where id=?");
            FileChooser fil=new FileChooser();
            fil.getExtensionFilters().addAll(
                new ExtensionFilter("All Files","*.*"),
                new ExtensionFilter("Text Files","*.txt"),
                new ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg","*.gif"),
                new ExtensionFilter("Audio Files","*.wav","*.mp3","*.aac")
            );
            Stage st=(Stage)profileimg.getScene().getWindow();
            File file=fil.showOpenDialog(st);
            psGetImg.setInt(1,customerId);
            ResultSet rsGetImg=psGetImg.executeQuery();
            if(file!=null)
            {
                Image userImage=new Image(file.toURI().toURL().toString(),100,150,true,true);
                FileInputStream fis=new FileInputStream(file);
                if(rsGetImg.next())
                {
                    psImgUpdate=conn.prepareStatement("update person_image set image=? where id=?");
                    psImgUpdate.setBinaryStream(1,(InputStream)fis,(int)file.length());
                    psImgUpdate.setInt(2,customerId);
                    psImgUpdate.executeUpdate();
                }
                else
                {
                    psImgUpdate=conn.prepareStatement("insert into person_image values(?,?)");
                    psImgUpdate.setInt(1,customerId);
                    psImgUpdate.setBinaryStream(2,(InputStream)fis,(int)file.length());
                    psImgUpdate.execute();
                }
            }
            //psGetImg.setInt(1,sellerId);
            rsGetImg=psGetImg.executeQuery();
            if(rsGetImg.next())
            {
                InputStream is=rsGetImg.getBinaryStream("IMAGE");
                OutputStream os=new FileOutputStream(new File("user_img.jpg"));
                byte[] content=new byte[1024];
                int size=0;
                while((size=is.read(content))!=-1)
                {
                    os.write(content,0,size);
                }
                os.close();
                is.close();
                Image userImage=new Image("file:user_img.jpg",100,150,true,true);
                profileimg.setImage(userImage);
            }
            psGetImg.close();
            psImgUpdate.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url,ResourceBundle rb)
    {
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        seller.setCellValueFactory(new PropertyValueFactory<>("seller"));
        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
    }
}