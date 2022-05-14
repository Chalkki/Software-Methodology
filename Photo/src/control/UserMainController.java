package control;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.DataSet;
import model.Photo;
import model.Photos;
import model.Tag;
import model.User;
/**
* controller to control the User Main Page
* @author  Weizheng Liang, Jiaqi He
* @version 1.0
* @since   2022-04-11
*/
public class UserMainController implements Initializable{
	
	@FXML private Button delete;
	@FXML private Button rename;
	@FXML private Button create;
	@FXML private Button search_with_date;
	@FXML private Button search_with_tags;
	@FXML private ListView<String> user_main_album_list;
	@FXML private Button LogoutButton;
	@FXML private Button renameConfirm;
	@FXML private BorderPane user_main_border_pane;
	@FXML private AnchorPane createAlbumPane;
	@FXML private AnchorPane renameAlbumPane;
	@FXML private AnchorPane searchDatePane;
	@FXML private AnchorPane searchTagPane;
	@FXML private TextField new_album_name;
	@FXML private TextField albumOldName;
	@FXML private TextField albumNewName;
	@FXML private DatePicker dateStart;
	@FXML private DatePicker dateEnd;
	@FXML private Button dateSearch;
	@FXML private ChoiceBox<String> tagType1;
	@FXML private ChoiceBox<String> tagType2;
	@FXML private ChoiceBox<String> andOr;
	@FXML private TextField tagVal1;
	@FXML private TextField tagVal2;
	@FXML private Button searchWithTagBtn;
	@FXML private Button resultAlbumCreateBtn;
	@FXML private Button resultCancel;
	@FXML private TextField resultNewAlbum;
	@FXML private ScrollPane resultScrollPane;
	@FXML private TilePane resultTilePane;
	@FXML private AnchorPane resultAnchorPane;
	
	/**
	 * A mouse event. 
	 * Implementation: 
	 * By double click selected-cell on the album list view, enter this album
	 * By single click cell on the album list view, select this album element.
	 * @param arg0
	 */
	@FXML public void elementClicked(MouseEvent arg0) {
		user_main_album_list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

		    @Override
		    public ListCell<String> call(ListView<String> list) {
		        ListCell<String> cell = new ListCell<String>() {
		            public void updateItem(String item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty || item == null) {
		                    setText(null);
		                } else {
		                    setText(item);
		                }
		            }
		        };
		        
		        cell.setOnMouseClicked(new EventHandler<MouseEvent>() {

				    @Override
				    public void handle(MouseEvent click) {
				    	if(click.getClickCount() == 1 && user_main_album_list.getSelectionModel()
		                        .getSelectedItem()!=null&& cell.isFocused()) {
				    	String currentclick = user_main_album_list.getSelectionModel().getSelectedItem();
						albumOldName.setText(currentclick); 
				    	}else if (click.getClickCount() == 2 && user_main_album_list.getSelectionModel()
		                        .getSelectedItem()!=null&&cell.isFocused()) {
				           //Use album ListView's getSelected Item
				           String currentItemSelected = user_main_album_list.getSelectionModel()
				                                                    .getSelectedItem();
				           for (Album album : currentUser.getAlbumList()) {
				        	   if (album.getAlbum_name().equals(currentItemSelected)) {
				        		   
				        		   Photos.currentAlbum = album;
				        	   }
				           }
				           FXMLLoader fxmlloader = new FXMLLoader();
							fxmlloader.setLocation(getClass().getResource("/view/AlbumMainPage.fxml"));
							Parent root = null;
							try {
								root = fxmlloader.load();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Scene scene = new Scene (root);
							Stage AppStage = (Stage) ((Node) click.getSource()).getScene().getWindow();
							AppStage.setScene(scene);
							AlbumMainController albummaincontroller = fxmlloader.getController();
							try {
								albummaincontroller.start();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							AppStage.show();
				        }
				    }
				    
				});

		        return cell;
		    }
		    
		});

		
		
	}
	
	 DataSet dataset = Photos.data;
	 User currentUser = Photos.currentUser;
	 ArrayList<Photo> searchResult = new ArrayList<>();

	ArrayList<ImageView> resultImageView = new ArrayList<>();
	 
	 
	 
	 /**
	  * starter method of the UserMainPage.
	  */
	 public void start () {
	 	loadAlbum(user_main_album_list);
	 	searchResult.clear();
	 }
	 /**
	  * to load album from the user data onto album list view
	  * @param user_main_album_list
	  */
	 private void loadAlbum (ListView<String> user_main_album_list) {
	 	
	 	ArrayList<Album> Albumlist = currentUser.getAlbumList();

	 	
	 	ObservableList<String> albums = FXCollections.observableArrayList();
	 	for(Album album : Albumlist) {
	 		albums.add(album.getAlbum_name());
	 	}
	 	user_main_album_list.setItems(albums);
	 }
	 
	 /**
	  * the action event for the confirm creation of a new album
	  * @param event
	  * @throws IOException
	  */
	 public void confirm_create(ActionEvent event) throws IOException{
			
			
			ObservableList<String> albums = user_main_album_list.getItems();
			if (albums.contains(new_album_name.getText()) || new_album_name.getText().equals("")) {

				Alert alert = new Alert (AlertType.ERROR);
				alert.setTitle ("Error");
				alert.setHeaderText("Error");
				alert.setContentText("invalid album name");
				alert.show();
			}else {
				albums.add(new_album_name.getText());
				
				Album newalbum = new Album(new_album_name.getText());
				currentUser.getAlbumList().add(newalbum);
				dataset.save(dataset);
			}
			
	 }
	 
	 /**
	  * the action event for deleting an album
	  * @param e
	  * @throws IOException
	  */
	 public void delete_album (ActionEvent e) throws IOException {
			Alert alert = new Alert (AlertType.CONFIRMATION);
			alert.setTitle ("Attention");
			alert.setHeaderText("attention");
			alert.setContentText("You sure you wanna delete this album?");
			if (alert.showAndWait().get()== ButtonType.OK) {
				int selectedIdx = user_main_album_list.getSelectionModel().getSelectedIndex();
				if (selectedIdx > -1) {
					ObservableList<String> albums = user_main_album_list.getItems();
					
					
					String text = user_main_album_list.getSelectionModel().getSelectedItem();
					
					currentUser.getAlbumList().removeIf(s -> s.getAlbum_name().equals(text));
					albums.remove(selectedIdx);
					dataset.save(dataset);
				}
			}
	}
	 
	 
	/**
	 * the action event for rename an album
	 * @param e
	 * @throws IOException
	 */
	 public void rename_album (ActionEvent e) throws IOException {

		 ObservableList<String> albums = user_main_album_list.getItems();
		 String albumName = user_main_album_list.getSelectionModel().getSelectedItem();
		 
		 if (albums.contains(albumNewName.getText()) || albumNewName.getText().equals("") || albumName==null) {

				Alert alert = new Alert (AlertType.ERROR);
				alert.setTitle ("Error");
				alert.setHeaderText("Error");
				alert.setContentText("invalid input");
				alert.show();
			}else {
				
				 for (int i =0; i< currentUser.getAlbumList().size();i++) {
					 if (currentUser.getAlbumList().get(i).getAlbum_name().equals(albumName)) {
						 currentUser.getAlbumList().get(i).setAlbum_name(albumNewName.getText());
					 }
					 
				 }
				 dataset.save(dataset);
				 loadAlbum(user_main_album_list);
			}
		 
			
			
			
	 }
	 /**
	  * search photo by tag
	  */
	 public void searchTag () {

		 searchResult.clear();
		 Boolean isDuplicate = false;
		 String tag1 = tagType1.getValue();
		 String val1 = tagVal1.getText();
		 String tag2 = tagType2.getValue();
		 String val2 = tagVal2.getText();
		 Boolean rightTag1;
		 Boolean rightTag2;
		 //To find corresponding tag
		 if (andOr.getValue().equals("and") || andOr.getValue().equals("or")) {
			 if ((!tag1.equals("") && !val1.equals("")) 
					 && 
					 (!tag2.equals("") && !val2.equals(""))) {
				 for (Album album : currentUser.getAlbumList()) {
					 for (Photo photo : album.getPhotoList()) {
						 rightTag1=false;
						 rightTag2=false;
						 for (Tag tag: photo.getTagList()) {
				
							 if (tag.getTagKey().equals(tag1) && tag.getTagVal().equals(val1)) rightTag1=true;
							 if (tag.getTagKey().equals(tag2) && tag.getTagVal().equals(val2)) rightTag2=true;
							 if (andOr.getValue().equals("and")) {
								 if (rightTag1 == true && rightTag2 == true) {
									 for (Photo photoresult:searchResult) {
										 if (photoresult.getPath().equals(photo.getPath())) {
											 isDuplicate = true;
										 }
									 }
								 	 if (isDuplicate==false) {
								 		 searchResult.add(photo);
								 		
								 	 }
								 	isDuplicate = false;
								 }
							 }
							 if (andOr.getValue().equals("or")) {
								 if (rightTag1 == true || rightTag2 == true) {
									 for (Photo photoresult:searchResult) {
										 if (photoresult.getPath().equals(photo.getPath())) {
											 isDuplicate = true;
										 }
									 }
								 	 if (isDuplicate==false) {
								 		 searchResult.add(photo);
								 		
								 	 }
								 	isDuplicate = false;
								 }
							 }
							 
						 }
					 }
				 }
				 if (searchResult.size()<1) {
					 Alert alert = new Alert (AlertType.ERROR);
						alert.setTitle ("Error");
						alert.setHeaderText("Error");
						alert.setContentText("no matching result!");
						alert.show();
				 }else {
					 ToResultPage();
					 loadPhoto(searchResult);
				 }
				 
				 
			 }else {
				 Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle ("Error");
					alert.setHeaderText("Error");
					alert.setContentText("please fill all required fields");
					alert.show();
			 }
		 }else {
			 if ((!tag1.equals("") && !val1.equals(""))) {
				 for (Album album : currentUser.getAlbumList()) {
					 for (Photo photo : album.getPhotoList()) {
						 for (Tag tag: photo.getTagList()) {
							 if (tag.getTagKey().equals(tag1) && tag.getTagVal().equals(val1)) {
								 for (Photo photoresult:searchResult) {
									 if (photoresult.getPath().equals(photo.getPath())) {
										 isDuplicate = true;
									 }
									 
								 }
							 	 if (!isDuplicate) {
							 		 searchResult.add(photo);
							 		isDuplicate = false;
							 	 }
							 }
						 }
					 }
				 }
				 if (searchResult.size()<1) {
					 Alert alert = new Alert (AlertType.ERROR);
						alert.setTitle ("Error");
						alert.setHeaderText("Error");
						alert.setContentText("no matching result!");
						alert.show();
				 }else {
					 ToResultPage();
					 loadPhoto(searchResult);
				 }
				 
			 }else {
				 Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle ("Error");
					alert.setHeaderText("Error");
					alert.setContentText("please fill all required fields");
					alert.show();
			 }
		 }
		 
		 
		 
	 }
	 /**
	  * jump to search result page
	  */
	 
	 public void ToResultPage() {

		 user_main_border_pane.setVisible(false);
		 resultAnchorPane.setVisible(true);
		 
	 }
	 /**
	  * to cancel search result and return to user main page
	  */
	 
	 public void cancelSearchResult() {

		 resultAnchorPane.setVisible(false);
		 user_main_border_pane.setVisible(true);
		 searchResult.clear();
		 resultImageView.clear();
		 resultTilePane.getChildren().clear();
		 
	 }
	 /**
	  * search photo by date
	  * @throws IOException
	  */
	 public void createResultAlbum() throws IOException {
			ObservableList<String> albums = user_main_album_list.getItems();
			if (albums.contains(resultNewAlbum.getText()) || resultNewAlbum.getText().equals("")) {

				Alert alert = new Alert (AlertType.ERROR);
				alert.setTitle ("Error");
				alert.setHeaderText("Error");
				alert.setContentText("invalid album name");
				alert.show();
			}else {
				albums.add(resultNewAlbum.getText());
				Album newalbum = new Album(resultNewAlbum.getText());
				for (Photo photo: searchResult) {
					newalbum.getPhotoList().add(photo);
				}
				currentUser.getAlbumList().add(newalbum);
				
				dataset.save(dataset);
				resultNewAlbum.clear();
				Alert alert = new Alert (AlertType.INFORMATION);
				alert.setTitle ("congrat");
				alert.setHeaderText("congrat");
				alert.setContentText("Creation Success!");
				alert.show();
				
			}
	 }
	 
	public void searchDate () {
		searchResult.clear();
		Boolean isDuplicate=false;
		LocalDate startdate = dateStart.getValue();
		LocalDate enddate = dateEnd.getValue();
		
		if (startdate == null||enddate==null||startdate.isAfter(enddate) ) {
			Alert alert = new Alert (AlertType.ERROR);
			alert.setTitle ("Error");
			alert.setHeaderText("Error");
			alert.setContentText("invalid date range");
			alert.show();
		}else {

			
			for (Album album:currentUser.getAlbumList()) {
				for (Photo photo: album.getPhotoList()) {
					if (calToLocaldate(photo.getdate()).isAfter(startdate) || 
							calToLocaldate(photo.getdate()).isBefore(enddate) || 
							calToLocaldate(photo.getdate()).equals(startdate) || 
							calToLocaldate(photo.getdate()).equals(enddate)) 
					{
						for (Photo photoresult:searchResult) {
							 if (photoresult.getPath().equals(photo.getPath())) {
								 isDuplicate = true;
							 }
							 
						 }
					 	 if (!isDuplicate) {
					 		 searchResult.add(photo);
					 		isDuplicate = false;
					 	 }
					}
				}
			}
			if (searchResult.size()<1) {
				 Alert alert = new Alert (AlertType.ERROR);
					alert.setTitle ("Error");
					alert.setHeaderText("Error");
					alert.setContentText("no matching result!");
					alert.show();
			 }else {
				 ToResultPage();
				loadPhoto(searchResult);
			 }
			
		}
		
		
	}
	public LocalDate calToLocaldate(Calendar calendar) {
		
		LocalDate localDate = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
		return localDate;
	}
	
	/*
	 * to load search result photo 
	 */
	public void loadPhoto (ArrayList<Photo> searchResult) {
		resultImageView.clear();
		String currentPath;

	     resultScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
	     resultScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
	     resultScrollPane.setFitToWidth(true);
	     resultScrollPane.setContent(resultTilePane);
		 resultTilePane.setPadding(new Insets(15,15,15,15));
		 resultTilePane.setHgap(15);
		 resultTilePane.setVgap(15);
	     
	     
	     for(int i = 0; i<searchResult.size();i++) {
	    	 if (searchResult.get(i).getPath().startsWith("file:")) {
	    		 currentPath = searchResult.get(i).getPath();
	    	 }else {
	    		 currentPath = "file:"+searchResult.get(i).getPath();
	    	 }
	    	 resultImageView.add(new ImageView(currentPath));
	     }
	     for (int i = 0; i<resultImageView.size();i++) {
	    	 resultImageView.get(i).setFitWidth(50);
	    	 resultImageView.get(i).setFitHeight(50);

	     }
//	     Refresh();
		 try{
			 for (int i = 0; i < resultImageView.size(); i++){
				 AddImageIntoPane(resultImageView.get(i));
			 }
		 } catch (IllegalArgumentException e){
			 
		 }
	}
	private void AddImageIntoPane(ImageView image) {
		 try{
				 TilePane tempPane = new TilePane();
				 tempPane.setMaxHeight(50);
				 tempPane.setMaxWidth(90);


				 tempPane.getChildren().addAll(image);

				 resultTilePane.getChildren().add(tempPane);
		 } catch (IllegalArgumentException e){
		 }
	}

	
	/**
	 * logout current user and return to login page
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
	 * to go to create album page
	 * @param e
	 */
	public void createAlbum (ActionEvent e) {
		searchTagPane.setVisible(false);
		searchDatePane.setVisible(false);
		renameAlbumPane.setVisible(false);
		createAlbumPane.setVisible(true);
		new_album_name.setText("");
		
	}
	public void renameAlbum (ActionEvent e) {
		searchTagPane.setVisible(false);
		searchDatePane.setVisible(false);
		createAlbumPane.setVisible(false);
		renameAlbumPane.setVisible(true);
		albumOldName.setText("");
		albumNewName.setText("");
		

	}
	public void searchWithDate (ActionEvent e) {
		searchTagPane.setVisible(false);
		createAlbumPane.setVisible(false);
		renameAlbumPane.setVisible(false);
		searchDatePane.setVisible(true);

	}
	public void searchWithTag (ActionEvent e) {
		
		createAlbumPane.setVisible(false);
		renameAlbumPane.setVisible(false);
		searchDatePane.setVisible(false);
		searchTagPane.setVisible(true);

	}
	

	/**
	 * to initializa this user main page
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		//init create album
		new_album_name.setText("");
		
		//init rename album
		albumOldName.setText("");
		albumNewName.setText("");
		
		//init search tag
		ArrayList<String> defaultTag = new ArrayList<>();
		defaultTag.addAll(currentUser.getTagTypeList());
		
		ObservableList<String> defaultTags = FXCollections.observableArrayList(defaultTag);
		tagType1.setItems(defaultTags);
		tagType2.setItems(defaultTags);
		tagType1.setValue("");
		tagType2.setValue("");
		
		ObservableList<String> defaultTags2 = FXCollections.observableArrayList("","and","or");
		andOr.setItems(defaultTags2);
		andOr.setValue("");

		
		tagVal2.setDisable(true);
    	tagType2.setDisable(true);
		
		andOr.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		      @Override
		      public void changed(ObservableValue<? extends String> observableValue, String oldval, String newval) {
		        if (newval.equals("")) {
		        	tagVal2.setDisable(true);
		        	tagType2.setDisable(true);
		        }else {
		        	tagVal2.setDisable(false);
		        	tagType2.setDisable(false);
		        }
		      }
		    });
		
		
		//init search date
		dateStart.getEditor().setDisable(true);
		dateEnd.getEditor().setDisable(true);
		
		
	}



	
	
	
}
