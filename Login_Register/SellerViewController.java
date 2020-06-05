import com.jfoenix.controls.JFXButton;
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
public class SellerViewController implements Initializable
{
    @FXML
    private ImageView profileimg;
    @FXML
    private Label sellername;
    @FXML
    private TextField search;
    @FXML
    private JFXButton sales;
    @FXML
    private JFXButton addItem;
    @FXML
    private JFXButton logOut;
    @FXML
    private TableView<SellerProduct> productTable;
    @FXML
    private TableColumn<SellerProduct, String> image;
    @FXML
    private TableColumn<SellerProduct, String> name;
    @FXML
    private TableColumn<SellerProduct, Double> price;
    @FXML
    private TableColumn<SellerProduct, String> category;
    @FXML
    private TableColumn<SellerProduct, Integer> quantity;
    @FXML
    private TableColumn<SellerProduct, Integer> productId;
    public int sellerId;
    private ObservableList<SellerProduct> data=FXCollections.observableArrayList();
    public void setSellerId(int id)
    {
        sellerId=id;
    }
    @FXML
    void onAddItem(ActionEvent event)
    {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("AddItem.fxml"));
            Parent root=(Parent)loader.load();
            Stage stage=(Stage)addItem.getScene().getWindow();
            AddItemController aic=new AddItemController();
            aic=(AddItemController)loader.getController();
            aic.setSellerId(sellerId);
            stage.setScene(new Scene(root,700,460));
            stage.show();
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
    void onProductSelect(MouseEvent event)
    {
        Parent root=null;
        FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("SellerProductPage.fxml"));
        try
        {
            root=(Parent)loader.load();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Logger.getLogger(SellerViewController.class.getName()).log(Level.SEVERE,null,e);
        }
        SellerProductController ppc=new SellerProductController();
        ppc=(SellerProductController)loader.getController();
        int id=productTable.getSelectionModel().getSelectedItem().getProductId();
        ppc.setProductId(id);
        ImageView productImage=ppc.getProductImage();
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement();
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
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        ppc.setSellerId(sellerId);
        ppc.setPrice(productTable.getSelectionModel().getSelectedItem().getPrice());
        ppc.setCategory(productTable.getSelectionModel().getSelectedItem().getCategory());
        ppc.setQuantity(productTable.getSelectionModel().getSelectedItem().getQuantity());
        ppc.setName(productTable.getSelectionModel().getSelectedItem().getName());
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT Description from Product where product_id="+String.valueOf(id));
            if(rs.next())
            {
                ppc.setDescription(rs.getString("DESCRIPTION"));
            }
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Stage st=(Stage)profileimg.getScene().getWindow();
        st.setScene(new Scene(root,700,460));
        st.show();
    }
    @FXML
    void onSearch(KeyEvent event)
    {
        FilteredList<SellerProduct> filteredData=new FilteredList<>(data,b->true);
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
                else if(String.valueOf(product.getQuantity()).indexOf(lcf)!=-1)
                    return true;
                else return false;
            });
        });
        SortedList<SellerProduct> sortedData=new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productTable.comparatorProperty());
        productTable.setItems(sortedData);
    }
    @FXML
    void onSales(ActionEvent event)
    {
        System.out.println("On Sales");
    }
    public void setData(ObservableList<SellerProduct> d)
    {
        this.data=d;
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
            psGetImg.setInt(1,sellerId);
            ResultSet rsGetImg=psGetImg.executeQuery();
            if(file!=null)
            {
                Image userImage=new Image(file.toURI().toURL().toString(),100,150,true,true);
                FileInputStream fis=new FileInputStream(file);
                if(rsGetImg.next())
                {
                    psImgUpdate=conn.prepareStatement("update person_image set image=? where id=?");
                    psImgUpdate.setBinaryStream(1,(InputStream)fis,(int)file.length());
                    psImgUpdate.setInt(2,sellerId);
                    psImgUpdate.executeUpdate();
                }
                else
                {
                    psImgUpdate=conn.prepareStatement("insert into person_image values(?,?)");
                    psImgUpdate.setInt(1,sellerId);
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
    public TableView<SellerProduct> getTableView()
    {
        return productTable;
    }
    public void setSellerName(String s)
    {
        sellername.setText(s);
    }
    public ImageView getProfileImg()
    {
        return profileimg;
    }
    @Override
    public void initialize(URL url,ResourceBundle rb)
    {
        image.setCellValueFactory(new PropertyValueFactory<>("image"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productId.setCellValueFactory(new PropertyValueFactory<>("productId"));
    }
}