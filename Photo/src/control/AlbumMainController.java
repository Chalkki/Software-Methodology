package control;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

/**
 * The controller for album main page
 * @author WeiZheng Liang and Jiaqi He
 *
 */
public class AlbumMainController implements Initializable{
@FXML
private Button BackButton;
@FXML
private Button AddPhotoButton;
@FXML
private Button DeletePhotoButton;
@FXML
private Button CaptionOrRecaptionButton;
@FXML
private Button CaptionOrRecaptionConfirmButton;
@FXML
private Button CaptionOrRecaptionCancel;
@FXML
private Button AddTagButton;
@FXML
private Button AddTagConfirmButtion;
@FXML
private Button AddTagCancel;
@FXML
private Button DeleteTagButton;
@FXML
private Button LogoutButton;
@FXML 
private AnchorPane AlbumFunctionPane;
@FXML
private AnchorPane AddTagPane;
@FXML
private AnchorPane CaptionOrRecaptionPane;
@FXML
private Label CaptionText;
@FXML
private TextField CaptionEditText;
@FXML
private TextField NewTagKey;
@FXML
private TextField NewTagValue;
@FXML
private ListView<Tag> TagList;
@FXML 
private BorderPane ChosenPhotoPane;
@FXML 
private ScrollPane ThumbnailScroll;
@FXML
private TilePane ThumbnailPane;
@FXML
private Button LeftSlideButton;
@FXML
private Button RightSlideButton;
@FXML
private Label DateLabel;
@FXML
private Button CopyButton;
@FXML
private Button MoveButton;
@FXML
private Label AlbumName;
@FXML
private ChoiceBox<String> tagChoice;
//
DataSet dataset = Photos.data;
User currentUser = Photos.currentUser;
private int currentImageidx = -1;
private int previousImageidx = -1;
private ArrayList<ImageView> images = new ArrayList<>();
//rember to set currentAlbum to the chosen album , this is just a test
Album currentAlbum = Photos.currentAlbum;
//
ColorAdjust chosen = new ColorAdjust();
ColorAdjust NotChosen = new ColorAdjust();
/**
 * The initialization for showing this page
 * @throws Exception
 */
public void start () throws Exception {
	AlbumName.setText("Album: " + currentAlbum.getAlbum_name());
	loadPhoto(currentAlbum);
}

/**
 * Loading Photo from the current album
 * @param currentAlbum
 */
private void loadPhoto (Album currentAlbum) {
	String currentPath;
     chosen.setBrightness(0);
     NotChosen.setBrightness(-0.5);
     ThumbnailScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
     ThumbnailScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
     ThumbnailScroll.setFitToWidth(true);
     ThumbnailScroll.setContent(ThumbnailPane);
	 ThumbnailPane.setPadding(new Insets(15,15,15,15));
     ThumbnailPane.setHgap(15);
     ThumbnailPane.setVgap(15);
     
     ArrayList<Photo>PhotoList = currentAlbum.getPhotoList();
     for(int i = 0; i<PhotoList.size();i++) {
    	 if (PhotoList.get(i).getPath().startsWith("file:")) {
				currentPath = PhotoList.get(i).getPath();
			}else {
				currentPath = "file:"+PhotoList.get(i).getPath();
			}
    	 images.add(new ImageView(currentPath));
     }
     for (int i = 0; i<images.size();i++) {
    	 images.get(i).setFitWidth(50);
		 images.get(i).setFitHeight(50);

     }
     Refresh();
	 try{
		 for (int i = 0; i < images.size(); i++){
			 
			 AddImageIntoPane(images.get(i),PhotoList.get(i).getCaption());
		 }
	 } catch (IllegalArgumentException e){
		 
	 }
}
/**
 * The method that add the thumbnail information of image into the tile pane
 * @param image Thumnail image
 * @param CaptionText Thumbnail caption
 */
private void AddImageIntoPane(ImageView image, String CaptionText) {
		 try{
				 TilePane tempPane = new TilePane();
				 tempPane.setMaxHeight(50);
				 tempPane.setMaxWidth(90);
				 Label caption = new Label();
				 caption.setText(CaptionText);
				 caption.setMaxWidth(40);
				 tempPane.getChildren().addAll(image, caption);
				// ImageAndCaption.add(tempPane);
				 tempPane.setEffect(NotChosen);
				 ThumbnailPane.getChildren().add(tempPane);
		 } catch (IllegalArgumentException e){
		 }
	}
/**
 * The method for user to return to main page
 * @param e Action Event for back button
 * @throws IOException
 */
public void ClickOnBack (ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Back");
		alert.setHeaderText("Back");
		alert.setContentText("Are you sure you want to return to main page?");
		if(alert.showAndWait().get() == ButtonType.OK) {
			
			FXMLLoader fxmlloader = new FXMLLoader();
			fxmlloader.setLocation(getClass().getResource("/view/UserMain.fxml"));
			Parent root = fxmlloader.load();
			Scene scene = new Scene (root);
			Stage AppStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			AppStage.setScene(scene);
			UserMainController usermainController = fxmlloader.getController();
			usermainController.start();
			AppStage.show();
		}
	}
	/**
	 * Add Photo Method for user to add photo into the file
	 */
	public void AddPhoto (ActionEvent e) throws Exception {
		String currentPath;
		FileChooser filechooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif","*.bmp");
		filechooser.getExtensionFilters().add(extFilter);
		File imgfile = filechooser.showOpenDialog(null);
		if(imgfile ==null) {
			return;
		}else {
			
			// this is some magic shit
//			File absolutePath2 = new File("/Users/weizhengliang/OneDrive_RutgersUniversity/CS213/photos21");
//			URI path_2 = absolutePath2.toURI();
//			URI path_1 = imgfile.toURI();
//			URI relativePath = path_2.relativize(path_1);
//			String path_final = relativePath.getPath();
//			System.out.println(path_final);
			
//			String path = path_final;	
			
			
			String path = imgfile.getPath();

			if (path.startsWith("file:")) {
				currentPath = path;
			}else {
				currentPath = "file:"+path;
			}
			Photo newPhoto = new Photo(currentPath);
			
			ArrayList<Photo>PhotoList = currentAlbum.getPhotoList();
			for (Photo photo : PhotoList) {
				if(photo.getPath().equals(newPhoto.getPath())) {
					Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle ("Error");
					alert.setHeaderText("Error");
					alert.setContentText("You cannot add the same photo in one album");
					alert.show();
					return;
				}
			}
			PhotoList.add(newPhoto);
			currentAlbum.setPhotoList(PhotoList);
			ImageView image = new ImageView(currentPath);
	    	 image.setFitWidth(50);
			 image.setFitHeight(50);
			images.add(image);
			dataset.save(dataset);
			Refresh();
		AddImageIntoPane(image, "");
		}
	}
	/**
	 * Every time the user adding or deleting a photo, then the index of photo in the list would change. This method would help to fix the index problem.
	 */
	public void Refresh() {
		for(int i = 0; i <images.size();i++) {
			final int currentidx = i;
			images.get(currentidx).setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					
					if (event.getClickCount() == 1){
						if(currentImageidx == -1 && previousImageidx == -1) {
							currentImageidx = currentidx;
						}else {
							previousImageidx = currentImageidx;
							currentImageidx = currentidx;
							if(previousImageidx != -1 &&previousImageidx != currentImageidx) {
								TilePane previousPane = (TilePane) ThumbnailPane.getChildren().get(previousImageidx);
								previousPane.setEffect(NotChosen);	
							}
						}
						TilePane currentPane = (TilePane) ThumbnailPane.getChildren().get(currentImageidx);
						currentPane.setEffect(chosen);
						FillCaption();
						ShowImage();
						ShowDate();
						ShowTag();
					}
				} 
			 });	
		}
	}
	/**
	 * Fill out the caption on the right side of the UI
	 */
	public void FillCaption() {
		TilePane currentPane = (TilePane) ThumbnailPane.getChildren().get(currentImageidx);
		Label CaptionOfImage = ((Label)currentPane.getChildren().get(1));
		CaptionText.setText(CaptionOfImage.getText());
	}
	/**
	 * Show the chosen image in the middle of the UI.
	 */
	public void ShowImage() {
		String currentPath;
		ArrayList<Photo> photolist = currentAlbum.getPhotoList();
		if (photolist.get(currentImageidx).getPath().startsWith("file:")) {
			currentPath = photolist.get(currentImageidx).getPath();
		}else {
			currentPath = "file:"+photolist.get(currentImageidx).getPath();
		}
		ImageView image =new ImageView(currentPath);
		image.setFitWidth(200);
		image.setFitHeight(213);
		ChosenPhotoPane.getChildren().clear();
		ChosenPhotoPane.getChildren().add(image);
	}
	/**
	 * Left Manual Slide method
	 */
	public void SlideLeft () {
		if(currentAlbum.getPhotoList().size() < 1) {
			return;
		}
			
		if(currentImageidx == -1) {
			currentImageidx = 0;
			ShowImage();
			TilePane currentPane = (TilePane) ThumbnailPane.getChildren().get(currentImageidx);
			currentPane.setEffect(chosen);
			ShowDate();
			ShowTag();
		}
		if(currentImageidx > 0) {
			previousImageidx = currentImageidx;
			currentImageidx --;
			ShowImage();
			TilePane previousPane = (TilePane) ThumbnailPane.getChildren().get(previousImageidx);
			previousPane.setEffect(NotChosen);	
			TilePane currentPane = (TilePane) ThumbnailPane.getChildren().get(currentImageidx);
			currentPane.setEffect(chosen);
			ShowDate();
			ShowTag();
		}
	}
	/**
	 *  Show up the date of the image on the right side of the UI
	 */
	public void ShowDate() {
		String strdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		ArrayList<Photo> photolist = currentAlbum.getPhotoList();
		Photo currentPhoto = photolist.get(currentImageidx);
		strdate = sdf.format(currentPhoto.getdate().getTime());
		DateLabel.setText(strdate);
	}
	/**
	 * Right Manual Slide method
	 */
	public void SlideRight() {
		if(currentAlbum.getPhotoList().size() < 1) {
			return;
		}
		if( currentImageidx < images.size() - 1) {
			previousImageidx = currentImageidx;
			currentImageidx ++;
			ShowImage();
			//if the user did not choose a picture at first
			if(currentImageidx != 0) {
				TilePane previousPane = (TilePane) ThumbnailPane.getChildren().get(previousImageidx);
				previousPane.setEffect(NotChosen);		
			}
			TilePane currentPane = (TilePane) ThumbnailPane.getChildren().get(currentImageidx);
			currentPane.setEffect(chosen);
			ShowDate();
			ShowTag();
		}
	}
	/**
	 * Delete Photo method
	 * @param e Action Event
	 * @throws IOException
	 */
	public void DeletePhoto (ActionEvent e) throws IOException {
		if(currentImageidx == -1) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Please select a photo before adding tag");
			alert.show();	
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete");
		alert.setHeaderText("Delete");
		alert.setContentText("Are you sure that you want to delete the chosen photo?");
		if (alert.showAndWait().get()== ButtonType.OK) {
			ThumbnailPane.getChildren().remove(currentImageidx);
			ArrayList<Photo> photolist = currentAlbum.getPhotoList();
			photolist.remove(currentImageidx);
			currentAlbum.setPhotoList(photolist);
			images.remove(currentImageidx);
			previousImageidx = -1;
			currentImageidx = -1;
			Refresh();
			ChosenPhotoPane.getChildren().clear();
			dataset.save(dataset);
		}
	}
	/**
	 * When the user call this function, the program will switch the right side scene for user to change the caption of chosen photo
	 * @param e
	 */
	public void CaptionOrRecaption (ActionEvent e) {
		if(currentImageidx == -1) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Please select a photo before adding tag");
			alert.show();	
			return;
		}
		AlbumFunctionPane.setVisible(false);
		CaptionOrRecaptionPane.setVisible(true);
		CaptionEditText.setText(CaptionText.getText());
		CaptionText.setVisible(false);
		CaptionEditText.setVisible(true);
	}
	
	/**
	 * When the user call this function, the program will switch the right side scene for user to add a tag to the chosen photo
	 * @param e
	 */
	public void AddTag (ActionEvent e) {
		if(currentImageidx == -1) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Please select a photo before adding tag");
			alert.show();	
			return;
		}
		AlbumFunctionPane.setVisible(false);
		AddTagPane.setVisible(true);
		TagList.setVisible(false);
		NewTagKey.setVisible(true);
		NewTagValue.setVisible(true);
		tagChoice.setVisible(true);
		tagChoice.setValue("");
		ArrayList<String> defaultTag = new ArrayList<>();
		defaultTag.addAll(currentUser.getTagTypeList());
		ObservableList<String> defaultTags = FXCollections.observableArrayList(defaultTag);
		tagChoice.setItems(defaultTags);
		tagChoice.getSelectionModel().selectFirst();
		

	}
	/**
	 * Delete the chosen tag
	 * @param e
	 */
	public void DeleteTag (ActionEvent e) {
		int index = TagList.getSelectionModel().getSelectedIndex();
		if (index == -1) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Please select a tag first");
			alert.show();
			return;
		}
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Tag");
		alert.setHeaderText("Delete Tag");
		alert.setContentText("Are you sure that you want to delete the chosen tag?");
		if(alert.showAndWait().get() == ButtonType.OK) {
			ArrayList<Photo> photolist = currentAlbum.getPhotoList();
			Photo currentPhoto = photolist.get(currentImageidx);
			//Tag currentTag = currentPhoto.getTagList().get(index);
			currentPhoto.getTagList().remove(index);
			currentAlbum.setPhotoList(photolist);
			ShowTag();	
			//currentUser.getTagTypeList().remove(currentTag.getTagKey());
		}
	}
	/**
	 * Method for user to log out
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
	/**
	 * Add Tag if the user entered valid tag info and clicked the confirm button
	 * @param e
	 * @throws IOException
	 */
	public void AddTagConfirmation (ActionEvent e) throws IOException {
		String tagKey = NewTagKey.getText();
		String tagValue = NewTagValue.getText();
		ArrayList<Photo> photolist = currentAlbum.getPhotoList();
		Photo currentPhoto = photolist.get(currentImageidx);
		
		if (!tagChoice.getValue().equals("")) {
			tagKey = tagChoice.getValue();
			System.out.println("tagkey"+tagKey);
		}
		if(tagKey.equals("") || tagValue.equals("")) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Tag name and Tag value cannot be empty");
			alert.show();
			return;
		}
		Tag newTag = new Tag(tagKey,tagValue);
		if (currentPhoto.AddTag(newTag) == false) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("You cannot put two locations on the same photo");
			alert.show();
			return;
		}
		for(Tag tag: currentPhoto.getTagList()) {
			if(tag.toString().toLowerCase().equals(newTag.toString().toLowerCase())) {
				Alert alert = new Alert (AlertType.ERROR);
				alert.setTitle ("Error");
				alert.setHeaderText("Error");
				alert.setContentText("You cannot have duplicated tag in one photo(same tag name and tag value)");
				alert.show();
				return;
			}
		}
		AddTagPane.setVisible(false);
		AlbumFunctionPane.setVisible(true);
		currentPhoto.getTagList().add(newTag);
		currentAlbum.setPhotoList(photolist);
		TagList.setVisible(true);
		NewTagKey.setVisible(false);
		NewTagValue.setVisible(false);
		tagChoice.setVisible(false);
		NewTagKey.clear();
		NewTagValue.clear();
		ShowTag();
		// adding the new tag to tag type list.
		if(!currentUser.getTagTypeList().contains(tagKey)) currentUser.getTagTypeList().add(tagKey);
		dataset.save(dataset);
	}
	/**
	 * Load and show tag(s) of the chosen photo on the right side of the UI
	 */
	public void ShowTag() {
		Photo currentPhoto = currentAlbum.getPhotoList().get(currentImageidx);
		ObservableList<Tag> Tags = FXCollections.observableArrayList();
		for (Tag tag : currentPhoto.getTagList()) {
			Tags.add(tag);
		}
		TagList.setItems(Tags);
	}
	/**
	 * Caption confirmation
	 * @param e
	 * @throws IOException
	 */
	public void CaptionOrRecaptionConfirmation (ActionEvent e) throws IOException {
		CaptionOrRecaptionPane.setVisible(false);
		AlbumFunctionPane.setVisible(true);
		CaptionText.setText(CaptionEditText.getText());
		CaptionText.setVisible(true);
		CaptionEditText.setVisible(false);
		TilePane tempPane = (TilePane) ThumbnailPane.getChildren().get(currentImageidx);
		Label caption = new Label();
		 caption.setText(CaptionEditText.getText());
		 caption.setMaxWidth(40);
		tempPane.getChildren().set(1, caption);
		ArrayList<Photo> photolist = currentAlbum.getPhotoList();
		Photo editPhoto = photolist.get(currentImageidx);
		editPhoto.setCaption(CaptionEditText.getText());
		currentAlbum.setPhotoList(photolist);
		dataset.save(dataset);
	}
	/**
	 * Cancel action for add tag
	 * @param e
	 */
	public void AddTagCancel (ActionEvent e) {
		AddTagPane.setVisible(false);
		AlbumFunctionPane.setVisible(true);
		TagList.setVisible(true);
		NewTagKey.setVisible(false);
		NewTagValue.setVisible(false);
		tagChoice.setVisible(false);
		NewTagKey.clear();
		NewTagValue.clear();
	}
	/**
	 * Cancel action for delete tag
	 * @param e
	 */
	public void CaptionOrRecaptionCancel (ActionEvent e) {
		CaptionOrRecaptionPane.setVisible(false);
		AlbumFunctionPane.setVisible(true);
		CaptionText.setVisible(true);
		CaptionEditText.setVisible(false);
	}
/**
 * Copy method that will switch to a scene for user to copy the chosen file into other album
 * @param e
 * @throws IOException
 */
	public void ClickOnCopy (ActionEvent e) throws IOException {
		if(currentImageidx == -1) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Please select the photo");
			alert.show();
			return;
		}
		String currentPath;
		ArrayList<Photo> photolist = currentAlbum.getPhotoList();
		if (photolist.get(currentImageidx).getPath().startsWith("file:")) {
			currentPath = photolist.get(currentImageidx).getPath();
		}else {
			currentPath = "file:"+photolist.get(currentImageidx).getPath();
		}
		//
		FXMLLoader fxmlloader = new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("/view/CopyAndMove.fxml"));
		Parent root = fxmlloader.load();
		Scene scene = new Scene (root);
		Stage AppStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		AppStage.setScene(scene);
		CopyAndMoveController CopyController = fxmlloader.getController();
		CopyController.StartWithCopy(currentPath);
		AppStage.show();
	}
	/**
	 * Copy method that will switch to a scene for user to move the chosen file into other album
	 * @param e
	 * @throws IOException
	 */
	public void ClickOnMove (ActionEvent e) throws IOException {
		if(currentImageidx == -1) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Please select the photo");
			alert.show();
			return;
		}
		String currentPath;
		ArrayList<Photo> photolist = currentAlbum.getPhotoList();
		if (photolist.get(currentImageidx).getPath().startsWith("file:")) {
			currentPath = photolist.get(currentImageidx).getPath();
		}else {
			currentPath = "file:"+photolist.get(currentImageidx).getPath();
		}
		FXMLLoader fxmlloader = new FXMLLoader();
		fxmlloader.setLocation(getClass().getResource("/view/CopyAndMove.fxml"));
		Parent root = fxmlloader.load();
		Scene scene = new Scene (root);
		Stage AppStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		AppStage.setScene(scene);
		CopyAndMoveController MoveController = fxmlloader.getController();
		MoveController.StartWithMove(currentPath);
		AppStage.show();
	}
/**
 * Initializer for the initialization of the tag choice box
 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tagChoice.setValue("");
		tagChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		      @Override
		      public void changed(ObservableValue<? extends String> observableValue, String oldval, String newval) {
		    	  if (newval == null) {
		    		  newval = "";
		    	  }
		        if (newval.equals("")) {
		        	NewTagKey.setDisable(false);

		        }else {
		        	NewTagKey.setDisable(true);
		        	NewTagKey.setText("");
		        }
		      }
		    });
		
	}
}
