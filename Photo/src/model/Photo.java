package model;

import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
/**
* Represents a Photo object, contains every photo's data
* @author  Weizheng Liang, Jiaqi He
* @version 1.0
* @since   2022-04-11
*/
public class Photo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String path;
	private Calendar date;
	private String caption;

	private ArrayList<Tag> TagList = new ArrayList<>();
	/**
	 * to construct a photo instance with it's file path
	 * @param String file path
	 */
	public Photo (String path) {
		this.setPath(path);
		File img = new File (path);
		Date d = new Date (img.lastModified());
		 DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		 // dateFormat.format(
		 dateFormat.format(d);
		 date = dateFormat.getCalendar();
		 date.set(Calendar.MILLISECOND,0);
	}
	
	/**
	 * to check for duplicate tag
	 * @param NewTag
	 * @return boolean value
	 */
	public boolean AddTag (Tag NewTag) {

		for (Tag tag : getTagList()) {
			if(tag.getTagKey().equals("location")&&NewTag.getTagKey().equals("location")) {
				return false;
			}
		}

		return true;
	}
	/**
	 * input a tag that you want to delete
	 * @param DelTag 
	 */
	public void DeleteTag (Tag DelTag) {
		getTagList().remove(getTagList().indexOf(DelTag));
	}
	
	/**
	 * alter caption text
	 * @param text
	 */
	public void Recaption (String text ) {
		setCaption(text);
	}
	
	/**
	 * 
	 * @return path
	 */
	public String getPath() {
		return path;
	}
	private void setPath(String path) {
		this.path = path;
	}
	/**
	 * 
	 * @return caption
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * 
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * 
	 * @return date
	 */
	public Calendar getdate() {
		return date;
	}
	/**
	 * 
	 * @return taglist
	 */
	public ArrayList<Tag> getTagList(){
		return TagList;
	}
/**
 * 
 * @param tagList
 */
	public void setTagList(ArrayList<Tag> tagList) {
		TagList = tagList;
	}
}
