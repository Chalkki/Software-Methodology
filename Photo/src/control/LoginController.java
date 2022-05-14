package control;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import javafx.scene.control.Alert.AlertType;

/**
* controller to control the login Page
* @author  Weizheng Liang, Jiaqi He
* @version 1.0
* @since   2022-04-11
*/
public class LoginController {
	@FXML
	private Button Enter;
	@FXML
	private TextField Username;
	/**
	 * general data set contains all info
	 */
	public DataSet data = Photos.data;
	/**
	 * initialize the currentUser var
	 */
	public User currentUser;
	/**
	 * to enter to the user account
	 * @param e
	 * @throws IOException
	 */
	public void ClickEnterButton (ActionEvent e) throws IOException {
		if (Username.getText().length()==0) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Invalid username");
			alert.show();	
		}else if (Username.getText().equals("admin")) {
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/view/admin.fxml"));
			Parent root = fxmlloader.load();
			Scene scene = new Scene (root);
			Stage AppStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			AppStage.setScene(scene);
			AdminController admincontroller = fxmlloader.getController();
			admincontroller.start();
			AppStage.show();
			
		}else {
			for (User user : data.getUser_list() ){
				if(user.getUserName().equals(Username.getText())) {
					currentUser = user;
					Photos.currentUser = user;
					
					FXMLLoader fxmlloader = new FXMLLoader();
					fxmlloader.setLocation(getClass().getResource("/view/UserMain.fxml"));
					Parent root = fxmlloader.load();
					Scene scene = new Scene (root);
					Stage AppStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
					AppStage.setScene(scene);
					UserMainController usermainController = fxmlloader.getController();
					usermainController.start();
					AppStage.show();
					return;
				}
			}
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Username Not Found");
			alert.show();	
		}
	}
}
