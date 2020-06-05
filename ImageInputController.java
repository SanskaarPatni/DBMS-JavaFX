import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.event.*;
import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import javafx.scene.control.TextField;
import javafx.stage.*;
import javafx.stage.FileChooser.*;
import java.util.*;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
public class ImageInputController
{
    @FXML
    private ImageView uploadimg;
    @FXML
    private Button upload;
    @FXML
    private TextField filepath; private Image image;
    private FileInputStream fis;
    @FXML
    void onUpload(ActionEvent event)
    {
        Stage st=(Stage)upload.getScene().getWindow();
        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL","hr","Vpcf137hg");
            Statement stmt=conn.createStatement();
            PreparedStatement pst=conn.prepareStatement("insert into Product_Image values(?,?)");
            FileChooser fil=new FileChooser();
            fil.getExtensionFilters().addAll(
                new ExtensionFilter("All Files","*.*"),
                new ExtensionFilter("Text Files","*.txt"),
                new ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg","*.gif"),
                new ExtensionFilter("Audio Files","*.wav","*.mp3","*.aac")
            );
            //Single file choice
            File file=fil.showOpenDialog(st);
            if(file!=null)
            {
                int n=Integer.parseInt(filepath.getText());
                pst.setInt(1,n);
                image=new Image(file.toURI().toURL().toString(),100,150,true,true);//Preserve Ratio and Smooth
                uploadimg.setImage(image);
                fis=new FileInputStream(file);
                pst.setBinaryStream(2,(InputStream)fis,(int)file.length());
                pst.executeQuery();
                System.out.println("INSERTED SUCCESSFULLY");
            }
            fis.close();
            pst.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}