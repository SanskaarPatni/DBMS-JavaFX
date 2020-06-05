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
public class AddItemController
{
    private Image im;
    private File file;
    public int sellerId;
    private final ObservableList<SellerProduct> data=FXCollections.observableArrayList();
    @FXML
    private ImageView productImage;
    @FXML
    private JFXTextArea productDescription;
    @FXML
    private JFXTextField price;
    @FXML
    private JFXTextField quantity;
    @FXML
    private JFXTextField category;
    @FXML
    private JFXTextField productName;
    @FXML
    private JFXButton dashboard;
    @FXML
    private JFXButton sales;
    @FXML
    private JFXButton logOut;
    @FXML
    private JFXButton update;
    public void setSellerId(int id)
    {
        this.sellerId=id;
    }
    @FXML
    void onDashboard(ActionEvent event)
    {
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement();
            PreparedStatement psUserImg=conn.prepareStatement("select IMAGE from Person_Image where ID=?");
            PreparedStatement psProCount=conn.prepareStatement("select count(*) from Product where Seller_id=?");
            PreparedStatement makeTable=conn.prepareStatement("select ProI.Product_image,Pro.Product_Id,Pro.Product_name,Pro.category,Pro.Quantity,Pro.price from Product Pro,Product_Image ProI where Pro.Product_Id=ProI.Product_Id and Pro.Seller_Id=?");
            FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("SellerView.fxml"));
            Stage stage=(Stage)dashboard.getScene().getWindow();
            Parent root=loader.load();
            ResultSet rsid=stmt.executeQuery("Select Name from person where id="+String.valueOf(sellerId));
            if(rsid.next());
            SellerViewController svc=(SellerViewController)loader.getController();
            makeTable.setInt(1,sellerId);
            psProCount.setInt(1,sellerId);
            ResultSet rsProCount=psProCount.executeQuery();
            if(rsProCount.next());
            int i=0,n=rsProCount.getInt(1);
            svc.setSellerId(sellerId);
            ImageView ivArr[]=new ImageView[n];
            SellerProduct spArr[]=new SellerProduct[n];
            ResultSet rsMakeTable=makeTable.executeQuery();
            psUserImg.setInt(1, sellerId);
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
                ImageView ivUser=svc.getProfileImg();
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
                spArr[i]=new SellerProduct(ivArr[i], rsMakeTable.getString("PRODUCT_NAME"), rsMakeTable.getDouble("PRICE"), rsMakeTable.getString("CATEGORY"), rsMakeTable.getInt("QUANTITY"), rsMakeTable.getInt("PRODUCT_ID"));
                data.add(spArr[i]);
                i++;
            }
            TableView <SellerProduct>tv=new TableView<>();
            svc.setData(data);
            tv=svc.getTableView();
            tv.setItems(data);
            svc.setSellerName("Hello, "+rsid.getString("NAME"));
            stage.setScene(new Scene(root,1000,600));
            stage.show();
            psUserImg.close();
            stmt.close();
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
    void onSales(ActionEvent event)
    {
        System.out.println("SALES");
    }
    @FXML
    void onUpdate(ActionEvent event)
    {
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement();
            PreparedStatement psProdImg=conn.prepareStatement("insert into product_image values(?,?)");
            PreparedStatement ps=conn.prepareStatement("INSERT INTO PRODUCT VALUES(PRODUCT_ID.NEXTVAL,?,?,?,?,?,?)");
            ps.setInt(1,Integer.parseInt(quantity.getText()));
            ps.setDouble(2,Double.parseDouble(price.getText()));
            ps.setString(3,productName.getText());
            ps.setInt(4,sellerId);
            ps.setString(5,productDescription.getText());
            ps.setString(6,category.getText());
            ps.execute();
            ResultSet rs=stmt.executeQuery("select product_id.nextval from dual");
            if(file!=null)
            {
                if(rs.next());
                psProdImg.setInt(1,(rs.getInt(1)-1));
                FileInputStream fis=new FileInputStream(file);
                psProdImg.setBinaryStream(2,(InputStream)fis,(int)file.length());
                psProdImg.execute();
            }
            ps.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    void onUpload(MouseEvent event)
    {
        try
        {
            FileChooser fil=new FileChooser();
            fil.getExtensionFilters().addAll(
                new ExtensionFilter("All Files","*.*"),
                new ExtensionFilter("Text Files","*.txt"),
                new ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg","*.gif"),
                new ExtensionFilter("Audio Files","*.wav","*.mp3","*.aac")
            );
            Stage stage=(Stage)productImage.getScene().getWindow();
            file=fil.showOpenDialog(stage);
            if(fil!=null)
            {
                im=new Image(file.toURI().toURL().toString(),100,150,true,true);
                productImage.setImage(im);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}