import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class InputPanel extends JPanel implements ActionListener{
	Main main;
	File inputFile;
	JButton pick;
	JLabel current;

	public InputPanel() {
		super();
		inputFile = null;
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		pick = new JButton("Choose File");
		current = new JLabel("Current Input: ");
		
		this.add(pick);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(current);
		
		pick.addActionListener(this);
	}
	
	public void finalize() throws Exception{
		if(inputFile == null) throw(new Exception("No directory chosen."));
	}
	
	public File getFile() {
		return inputFile;
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(null);
		chooser.setDialogTitle("Choose an Input File");
	    chooser.setFileFilter(new FileNameExtensionFilter(".tws only", "tws"));
	    
		if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
			inputFile = chooser.getSelectedFile();
			current.setText("Current Input File: " + inputFile.toString());
		}
	}
}