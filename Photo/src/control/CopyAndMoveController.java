package control;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
/**
* controller to control copy and move photo function
* @author  Weizheng Liang, Jiaqi He
* @version 1.0
* @since   2022-04-11
*/
public class CopyAndMoveController {
DataSet dataset = Photos.data;
User currentUser = Photos.currentUser;
Album currentAlbum = Photos.currentAlbum;
@FXML
private ListView<String> AlbumList;
@FXML
private Label Title;
@FXML
private BorderPane ImagePane;
@FXML
private Label AlbumName;
@FXML
private Button ConfirmButton;
@FXML
private Button CancelButton;
private String currentStatus;
private String currentPhotoPath;
/**
 * The start method for copy
 * @param photoPath the photo's path for the photo to be copied
 */
public void StartWithCopy(String photoPath) {
	currentPhotoPath = photoPath;
	loadAlbum();
	loadPhoto(photoPath);
	Title.setText("Copy the photo to the chosen album in the list");
	currentStatus = "Copy";
}
/**
 * The start method for copy
 * @param photoPath the photo's path for the photo to be copied
 */
public void StartWithMove(String photoPath) {
	currentPhotoPath = photoPath;
	loadAlbum();
	loadPhoto(photoPath);
	Title.setText("Move the photo to the chosen album in the list");
	currentStatus = "Move";
}
/**
 * Load the existing album list of the user
 */
private void loadAlbum () {
 	AlbumName.setText("Source album: "+ currentAlbum.getAlbum_name());
 	ArrayList<Album> Albumlist = currentUser.getAlbumList();

 	
 	ObservableList<String> albums = FXCollections.observableArrayList();
 	for(Album album : Albumlist) {
 		if(album.getAlbum_name().equals(currentAlbum.getAlbum_name())) {
 			continue;
 		}
 		albums.add(album.getAlbum_name());
 	}
 	AlbumList.setItems(albums);
 }
/**
 * Load the chosen photo and put it on the right side of the UI to display
 * @param photoPath
 */
private void loadPhoto (String photoPath) {
	ImageView image =new ImageView(photoPath);
	image.setFitWidth(200);
	image.setFitHeight(200);
	ImagePane.getChildren().clear();
	ImagePane.getChildren().add(image);
}
/**
 * 
 * @param e
 * @throws Exception
 */
public void ClickOnConfirm(ActionEvent e) throws Exception {
	if(currentStatus.equals("Copy")) {
		CopyConfirm();
	}else {
		MoveConfirm();
	}
}
/**
 * The copy confirmation method
 * @throws Exception
 */
private void CopyConfirm() throws Exception {
	Photo newPhoto = new Photo(currentPhotoPath);
	String AlbumName = AlbumList.getSelectionModel().getSelectedItem();
	if(AlbumName == null) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error");
		alert.setContentText("Please select an album from list");
		alert.show();
		return;
	}
	for(Album album : currentUser.getAlbumList()) {
		if(album.getAlbum_name().equals(AlbumName)) {
			ArrayList<Photo> photolist = album.getPhotoList();
			for (Photo photo : photolist) {
				if (currentPhotoPath.equals(photo.getPath())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Error");
					alert.setContentText("The same photo has already existed in the destination album");
					alert.show();
					return;
				}
			}
			photolist.add(newPhoto);
			album.setPhotoList(photolist);
			break;
		}
	}
	dataset.save(dataset);
ReturnToAlbumMainPage();
}
/**
 * The move confirmation method
 * @throws Exception
 */
private void MoveConfirm() throws Exception {
	Photo newPhoto = new Photo(currentPhotoPath);
	String AlbumName = AlbumList.getSelectionModel().getSelectedItem();
	if(AlbumName == null) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error");
		alert.setContentText("Please select an album from list");
		alert.show();
		return;
	}
	for(Album album : currentUser.getAlbumList()) {
		if(album.getAlbum_name().equals(AlbumName)) {
			ArrayList<Photo> photolist = album.getPhotoList();
			for (Photo photo : photolist) {
				if (currentPhotoPath.equals(photo.getPath())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Error");
					alert.setContentText("The same photo has already existed in the destination album");
					alert.show();
					return;
				}
			}
			photolist.add(newPhoto);
			album.setPhotoList(photolist);
			break;
		}
	}
	dataset.save(dataset);
		ArrayList <Photo> currentPhotolist = currentAlbum.getPhotoList();
		for(Photo photo :currentPhotolist) {
			if(photo.getPath().equals(currentPhotoPath)) {
				currentPhotolist.remove(photo);
				currentAlbum.setPhotoList(currentPhotolist);
				dataset.save(dataset);
				ReturnToAlbumMainPage();
				return;
			}
		}
	}
/**
 * 
 * @param e
 * @throws Exception
 */
public void ClickOnCancel(ActionEvent e) throws Exception {
	ReturnToAlbumMainPage();
}
/**
 * Return method when the user clicked either confirm button(with valid action) or cancel
 * @throws Exception
 */
private void ReturnToAlbumMainPage() throws Exception {
	FXMLLoader fxmlloader = new FXMLLoader();
	fxmlloader.setLocation(getClass().getResource("/view/AlbumMainPage.fxml"));
	Parent root = fxmlloader.load();
	Scene scene = new Scene (root);
	Stage AppStage = (Stage) ConfirmButton.getScene().getWindow();
	AppStage.setScene(scene);
	AlbumMainController AlbumController = fxmlloader.getController();
	AlbumController.start();
	AppStage.show();
}
}
