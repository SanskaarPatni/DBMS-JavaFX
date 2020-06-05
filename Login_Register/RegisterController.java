import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.*;
import javafx.scene.control.Label;
import javafx.scene.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import java.io.File;
import java.util.Scanner;
import java.sql.*;
import hvhash.Hashing;
public class RegisterController
{
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField dob;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXPasswordField pass1;
    @FXML
    private JFXPasswordField pass2;
    @FXML
    private JFXTextField accounttype;
    @FXML
    private JFXButton signup;
    @FXML
    public Label personcount;
    @FXML
    private Label reaction;
    boolean verifyPhone(String ph)
    {
        if(ph.length()>10||ph.length()<10||ph.charAt(0)=='0')
            return false;
        for(int i=0;i<10;i++)
        {
            char c=ph.charAt(i);
            if(c>='0'&&c<='9')
            {
                continue;
            }
            else
                return false;
        }
        return true;
    }
    boolean verifyDate(String date)
    {
        int day=Integer.parseInt(date.substring(0,2));
        int month=Integer.parseInt(date.substring(3,5));
        int year=Integer.parseInt(date.substring(6));
        int arr[]={0,31,28,31,30,31,30,31,31,30,31,30,31};
        if(year%4==0)
        {
            if(year%100==0)
            {
                if(year%400==0)
                    arr[2]=29;
            }
            else
                arr[2]=29;
        }
        if(day>arr[month]||day<=0)
            return false;
        return true;
    }
    boolean duplicateMail(String mail)
    {
        int c=0;
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("select email from person");
            while(rs.next())
            {
                if(mail.equalsIgnoreCase(rs.getString("EMAIL")))
                    c=1;
            }
            stmt.close();
            conn.close();
        }
        catch(Exception e){e.printStackTrace();}
        if(c==0)
            return true;
        return false;
    }
    @FXML
    void onRegister(ActionEvent event)
    {
        try
        {
            File fileURL=new File("url_user_pass.txt");
            Scanner sc=new Scanner(fileURL);
            Class.forName(sc.nextLine());
            Connection conn=DriverManager.getConnection(sc.nextLine(),sc.nextLine(),sc.nextLine());
            Statement stmt=conn.createStatement();
            PreparedStatement persadd=conn.prepareStatement("Insert into Person Values(PERSON_ID.NEXTVAL,?,?,?,?,SYSDATE,?)");
            ResultSet rs=stmt.executeQuery("SELECT count(*) from person");
            String s="";
            if(rs.next())
                s=Integer.toString(rs.getInt(1))+"+ Members!";
            CallableStatement cstmt=null; 
            personcount.setText(s);
            reaction.setText("");
            if(email.getText().indexOf("@")==-1)
            {
                reaction.setText("Invalid Email Address!");
            }
            else if(!duplicateMail(email.getText()))
            {
                reaction.setText("Email Already Exists");
            }
            else
            {
                persadd.setString(1,email.getText());
                persadd.setString(2,name.getText());
                String date=dob.getText();
                if(!verifyDate(date))
                {
                    reaction.setText("Invalid Date of Birth!");
                }
                else
                {
                    persadd.setString(3,date);
                    String ph=phone.getText();
                    if(!verifyPhone(ph))
                    {
                        reaction.setText("Invalid Phone Number!");
                    }
                    else
                    {
                        persadd.setString(4,ph);
                        if(!pass1.getText().equals(pass2.getText()))
                        {
                            reaction.setText("Password Un-Match!");
                        }
                        else
                        {
                            int id=0;
                            String acc=accounttype.getText();
                            ResultSet rsid=stmt.executeQuery("select PERSON_ID.NEXTVAL from dual");
                            if(acc.equalsIgnoreCase("SELLER"))
                            {
                                persadd.setString(5,"Y");
                                persadd.executeQuery();
                                if(rsid.next())
                                    id=rsid.getInt(1)+1;
                                cstmt=conn.prepareCall("{call ADD_PERSON.ADD_SELLER(?,?)}");
                                cstmt.setString(1,Hashing.hash(pass1.getText()));
                                cstmt.setInt(2,id);
                                cstmt.execute();
                                try
                                {
                                    Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
                                    Stage stage=(Stage)signup.getScene().getWindow();
                                    stage.setScene(new Scene(root,700,400));
                                    stage.show();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            else if(acc.equalsIgnoreCase("CUSTOMER"))
                            {
                                persadd.setString(5,"N");
                                persadd.executeQuery();
                                if(rsid.next())
                                    id=rsid.getInt(1)+1;
                                cstmt=conn.prepareCall("{call ADD_PERSON.ADD_CUSTOMER(?,?)}");
                                cstmt.setString(1,Hashing.hash(pass1.getText()));
                                cstmt.setInt(2,id);
                                cstmt.execute();
                                try
                                {
                                    Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
                                    Stage stage=(Stage)signup.getScene().getWindow();
                                    stage.setScene(new Scene(root,700,400));
                                    stage.show();
                                }
                                catch(Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            else
                            {
                                reaction.setText("Invalid Account Type!");
                            }
                        }
                    }
                }
            }
            if(cstmt!=null)
                cstmt.close();
            if(persadd!=null)
                persadd.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}