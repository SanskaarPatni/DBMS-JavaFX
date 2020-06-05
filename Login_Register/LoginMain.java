import javafx.fxml.*;
import javafx.scene.*;
import javafx.application.*;
import javafx.stage.*;
public class LoginMain extends Application
{
    public void start(Stage s)throws Exception
    {
        Parent root=FXMLLoader.load(getClass().getResource("login.fxml"));
        s.setTitle("User Login");
        s.setScene(new Scene(root,700,400));
        s.show();
    }
    public static void main(String []args)
    {
        launch(args);
    }
}
