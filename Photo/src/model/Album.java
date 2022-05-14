package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
* Represents an Album object
* @author  Weizheng Liang, Jiaqi He
* @version 1.0
* @since   2022-04-11
*/
public class Album implements Serializable{

 private static final long serialVersionUID = 1L;
 private String album_name;
 private ArrayList<Photo> photoList = new ArrayList<>();
 
 /**
  * Create an Album with a name
  * @param a String as its name
  */
 public Album(String album_name){
  this.setAlbum_name(album_name);
 }
 /**
  * to get the album name
  * @return returns this album instance's name
  */
 public String getAlbum_name() {
  return album_name;
 }
 /**
  * to set the album name
  */
 public void setAlbum_name(String album_name) {
  this.album_name = album_name;
 }
 /**
  * to get the album name
  * @return returns the album's a photo list
  */
 public ArrayList<Photo> getPhotoList() {
  return photoList;
 }
 /**
  * to set album's photo list
  */
 public void setPhotoList(ArrayList<Photo> photoList) {
  this.photoList = photoList;
 }
 
 
}
