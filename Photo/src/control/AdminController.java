package control;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DataSet;
import model.Photos;
import model.User;
/**
* controller to control the admin Page
* @author  Weizheng Liang, Jiaqi He
* @version 1.0
* @since   2022-04-11
*/
public class AdminController {
@FXML
private Button Create;
@FXML
private Button Delete;
@FXML
private TextField NewUser;
@FXML
private ListView<String> userlist;
@FXML
private Button admin_logout;

DataSet dataset = Photos.data;

/**
 * to start the Admin page
 */
public void start () {
	loadUser(userlist);
}

private void loadUser (ListView<String> userlist) {
	
	ArrayList<User> Userlist = dataset.getUser_list();
	
	ObservableList<String> users = FXCollections.observableArrayList();
	for(User user : Userlist) {
		if(Userlist.isEmpty()) break;
		users.add(user.getUserName());
	}
	userlist.setItems(users);
}

/**
 * to create a new user 
 * @param e
 * @throws IOException
 */
public void CreateUser (ActionEvent e) throws IOException {
	
	ObservableList<String> users = userlist.getItems();
	if (users.contains(NewUser.getText()) || NewUser.getText().equals("") || NewUser.getText().equals("admin")) {

		Alert alert = new Alert (AlertType.ERROR);
		alert.setTitle ("Error");
		alert.setHeaderText("Error");
		alert.setContentText("invalid username");
		alert.show();
	}else {
		users.add(NewUser.getText());
		
		User newuser = new User(NewUser.getText());
		dataset.getUser_list().add(newuser);
		dataset.save(dataset);
	}
	
	


}

/**
 * to delete a user from user list
 * @param e
 * @throws IOException
 */
public void DeleteUser (ActionEvent e) throws IOException {
	int selectedIdx = userlist.getSelectionModel().getSelectedIndex();
	if (selectedIdx > -1) {
		ObservableList<String> users = userlist.getItems();
		
		
		String text = userlist.getSelectionModel().getSelectedItem();
		
		dataset.getUser_list().removeIf(s -> s.getUserName().equals(text));
		users.remove(selectedIdx);
		dataset.save(dataset);
	}
	
	
	
}

/**
 * to logout admin account and return to the login page
 * @param e
 * @throws IOException
 */
public void Logout (ActionEvent e) throws IOException {
	Alert alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle("Logout");
	alert.setHeaderText("Log out");
	alert.setContentText("Are you sure that you want to log out?");
	if (alert.showAndWait().get()== ButtonType.OK) {
		Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		Scene scene = new Scene (root);
		Stage AppStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		AppStage.setScene(scene);
		AppStage.show();
	}
}

}