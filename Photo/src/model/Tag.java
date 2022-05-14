package model;

import java.io.Serializable;

/**
* Tag class
* @author  Weizheng Liang, Jiaqi He
* @version 1.0
* @since   2022-04-11
*/
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;
	private String tagKey;
	private String tagVal;

	/**
	 * The tag class constructor
	 * @param tagKey The tag name of a tag
	 * @param tagVal The tag value of tag 
	 */
	public Tag(String tagKey, String tagVal){
		this.tagKey = tagKey;
		this.tagVal = tagVal;
	}
	/**
	 * 
	 * @return Return tag name
	 */
	public String getTagKey() {
		return tagKey;
	}
	/**
	 * 
	 * @param tagKey tag key
	 */
	public void setTagKey(String tagKey) {
		this.tagKey = tagKey;
	}
	/**
	 * 
	 * @return Return tag value 
	 */
	public String getTagVal() {
		return tagVal;
	}
	/**
	 * 
	 * @param tagVal tag value
	 */
	public void setTagVal(String tagVal) {
		this.tagVal = tagVal;
	}
	/**
	 * To string method
	 */
	public String toString() {
		return this.tagKey+ " | " + this.tagVal;
	}
	
}
