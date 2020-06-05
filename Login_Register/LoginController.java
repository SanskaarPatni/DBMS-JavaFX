import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.application.*;
import javafx.scene.control.TableView;
import javafx.event.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.*;
import javafx.scene.control.Label;
import java.sql.*;
import java.io.File;
import java.util.Scanner;
import hvhash.Hashing;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileOutputStream;
public class LoginController
{
    @FXML
    private Label reaction;
    @FXML
    private JFXTextField usernametxt;
    @FXML
    private JFXPasswordField passwordtxt;
    @FXML
    private JFXButton login;
    @FXML
    private JFXButton signup;
    private Image im;
    private final ObservableList<SellerProduct> data=FXCollections.observableArrayList();
    private final ObservableList<CustomerProduct> cData=FXCollections.observableArrayList();
    @FXML
    void onLogIn(ActionEvent event)
    {
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement();
            PreparedStatement psUserImg=conn.prepareStatement("select IMAGE from Person_Image where ID=?");
            PreparedStatement getId=conn.prepareStatement("select * from Person where EMAIL=?");
            PreparedStatement getPwd=null;
            PreparedStatement psProCount=null;
            PreparedStatement makeTable=null;
            getId.setString(1,usernametxt.getText());
            ResultSet rsid=getId.executeQuery();
            if(rsid.next())
            {
                String check=rsid.getString("IsSeller");
                int id=rsid.getInt("ID");
                if(check.equals("N"))
                    getPwd=conn.prepareStatement("select PASSWORD from Customer where CUSTOMER_ID=?");
                else
                    getPwd=conn.prepareStatement("select PASSWORD from Seller where SELLER_ID=?");
                getPwd.setInt(1,id);
                ResultSet rspwd=getPwd.executeQuery();
                rspwd.next();
                String cpwd=rspwd.getString("PASSWORD");
                String pwd=passwordtxt.getText();
                pwd=Hashing.hash(pwd);
                if(cpwd.equals(pwd))
                {
                    reaction.setText("Login Successful");
                    if(check.equals("Y"))
                    {
                        makeTable=conn.prepareStatement("select ProI.Product_image,Pro.Product_Id,Pro.Product_name,Pro.category,Pro.Quantity,Pro.price from Product Pro,Product_Image ProI where Pro.Product_Id=ProI.Product_Id and Pro.Seller_Id=?");
                        psProCount=conn.prepareStatement("select count(*) from Product where Seller_id=?");
                        FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("SellerView.fxml"));
                        Stage stage=(Stage)reaction.getScene().getWindow();
                        Parent root=loader.load();
                        SellerViewController svc=(SellerViewController)loader.getController();
                        makeTable.setInt(1,id);
                        psProCount.setInt(1,id);
                        ResultSet rsProCount=psProCount.executeQuery();
                        if(rsProCount.next());
                        int i=0,n=rsProCount.getInt(1);
                        svc.setSellerId(id);
                        ImageView ivArr[]=new ImageView[n];
                        SellerProduct spArr[]=new SellerProduct[n];
                        ResultSet rsMakeTable=makeTable.executeQuery();
                        psUserImg.setInt(1, id);
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
                    }
                    else
                    {
                        psProCount=conn.prepareStatement("select count(*) from product");
                        makeTable=conn.prepareStatement("select ProI.Product_image,Pro.Product_Id,Pro.Product_name,Pro.category,Pro.Seller_ID,Pro.price from Product Pro,Product_Image ProI where Pro.Product_Id=ProI.Product_Id");
                        FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("CustomerView.fxml"));
                        Stage stage=(Stage)reaction.getScene().getWindow();
                        Parent root=loader.load();
                        CustomerViewController cvc=(CustomerViewController)loader.getController();
                        ResultSet rsProCount=psProCount.executeQuery();
                        if(rsProCount.next());
                        int i=0,n=rsProCount.getInt(1);
                        cvc.setCustomerId(id);
                        ImageView ivArr[]=new ImageView[n];
                        CustomerProduct cpArr[]=new CustomerProduct[n];
                        ResultSet rsMakeTable=makeTable.executeQuery();
                        psUserImg.setInt(1, id);
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
                        cvc.setCustomerName("Hello, "+rsid.getString("NAME"));
                        stage.setScene(new Scene(root,1000,600));
                        stage.show();
                    }
                }
                else
                {
                    reaction.setText("Wrong Password");
                }
            }
            else
            {
                reaction.setText("User Doesn't Exist.");
            }
            conn.close();
            stmt.close();
            getId.close();
            if(getPwd!=null)
                getPwd.close();
            if(makeTable!=null)
                makeTable.close();
            if(psProCount!=null)
                psProCount.close();
            if(psUserImg!=null)
                psUserImg.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    @FXML
    void onSignUp(ActionEvent event)throws Exception
    {
        Stage st=(Stage)signup.getScene().getWindow();
        FXMLLoader loader=new FXMLLoader(getClass().getClassLoader().getResource("register.fxml"));
        Parent root=loader.load();
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement();
            RegisterController reg=(RegisterController)loader.getController();
            ResultSet rs=stmt.executeQuery("select count(*) from person");
            String s="";
            if(rs.next())
                s=Integer.toString(rs.getInt(1))+"+ Members!";
            reg.personcount.setText(s);
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        st.setTitle("User Registration");
        st.setScene(new Scene(root,700,400));
        st.show();
    }
}
