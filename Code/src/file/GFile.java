package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import shape.GShape;

public class GFile {
	
	private File file;

	public GFile() {
		this.file = null;
	}
	
	public Object readObject(File file) {
		try {
			this.file = file;
			FileInputStream fileInputStream = new FileInputStream(this.file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			objectInputStream.close();
			return object;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveAsObject(Object object, File file) {
		try {
			this.file = file;
			FileOutputStream fileOutputStream = new FileOutputStream(this.file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(object);
			objectOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean saveObject(Object object) {
		if (this.file == null) {
			return false;
		} else {
			this.saveAsObject(object, file);
			return true;
		}
	}
}
