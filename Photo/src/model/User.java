package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
* User class
* @author  Weizheng Liang, Jiaqi He
* @version 1.0
* @since   2022-04-11
*/
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private ArrayList<Album> albumList = new ArrayList<>();
	private ArrayList<String> tagTypeList;
	
	/**
	 * The constructor of user class
	 * @param userName UserName
	 */
	public User(String userName) {
		this.userName = userName;
		this.tagTypeList = new ArrayList<>();
		this.tagTypeList.add("");
		this.tagTypeList.add("weather");
		this.tagTypeList.add("person");
		this.tagTypeList.add("location");

	}
	
/**
 * 
 * @return Return user name
 */
	public String getUserName() {
		return userName;
	}

/**
 * 
 * @param userName User Name
 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

/**
 * 
 * @return Return albumList
 */
	public ArrayList<Album> getAlbumList() {
		return albumList;
	}

/**
 * 
 * @param albumList album list
 */
	public void setAlbumList(ArrayList<Album> albumList) {
		this.albumList = albumList;
	}
/**
 * 
 * @return Return tagTyoe list
 */
	public ArrayList<String> getTagTypeList() {
		return tagTypeList;
	}
/**
 * 
 * @param tagTypeList 
 */
	public void setTagTypeList(ArrayList<String> tagTypeList) {
		this.tagTypeList = tagTypeList;
	}
}

