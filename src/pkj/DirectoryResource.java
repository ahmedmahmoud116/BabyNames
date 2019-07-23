package pkj;

import java.io.File;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class DirectoryResource extends JFrame{
	
	public DirectoryResource(){
		
	}
	
	public Iterable<File> selectedFiles(){
		Iterable<File> it = null;
		JFileChooser chose = new JFileChooser();
		chose.setMultiSelectionEnabled(true);
		chose.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = chose.showOpenDialog(this);
		if(result == JFileChooser.APPROVE_OPTION) {
			File[] f = chose.getSelectedFiles();
			ArrayList<File> al = new ArrayList<>(Arrays.asList(f));
			it = al;
			return it;
		}
		return it;
		
	}
}
