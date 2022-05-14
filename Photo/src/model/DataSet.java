package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
* Represents a Dataset object, contains every all data
* @author  Weizheng Liang, Jiaqi He
* @version 1.0
* @since   2022-04-11
*/
public class DataSet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final String filePath = "./data/userList.txt";
	private ArrayList<User> user_list = new ArrayList<User>() ;
	 /**
	  * To save the general data set into a file
	  * @param the dataset instance
	  */
	public void save(DataSet dataset) throws IOException {
		
		
		FileOutputStream fos = new FileOutputStream(filePath);
		try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(dataset);
			oos.close();
		}
		
		
	}
	
	 /**
	  * to load user data from file
	  * @return returns the general data set
	  */
	public DataSet load() {
		File file = new File(filePath);
		DataSet dataset = new DataSet();
		if (file.length() != 0) {
			try {
				FileInputStream fis = new FileInputStream(filePath);
		        ObjectInputStream ois;
				ois = new ObjectInputStream(fis);
				dataset = (DataSet) ois.readObject();		        
		        ois.close();
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dataset;

		
		

        
	}
	
	/**
	 * to get user list from data set
	 * @return returns a user list
	 */
	public ArrayList<User> getUser_list() {
		return user_list;
	}

	/**
	 * to set a user list
	 * @param user_list
	 */
	public void setUser_list(ArrayList<User> user_list) {
		this.user_list = user_list;
	}


	
	
}
