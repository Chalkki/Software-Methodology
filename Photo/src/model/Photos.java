package model;
	
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * The Photo main class
 * @author WeiZheng Liang and Jiaqi He
 *
 */
public class Photos extends Application {
	public static DataSet data;
	public static User currentUser;
	public static Album currentAlbum;
	/**
	 * The start method for showing scene
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			data = new DataSet();
			data = data.load();
			Parent root= FXMLLoader.load(getClass().getResource("/view/login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
