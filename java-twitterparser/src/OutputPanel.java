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

public class OutputPanel extends JPanel implements ActionListener{
	Main main;
	File outputFile;
	JButton pick;
	JLabel current;

	public OutputPanel() {
		super();
		outputFile = null;
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		pick = new JButton("Choose File");
		current = new JLabel("Current Output: ");
		
		this.add(pick);
		this.add(Box.createRigidArea(new Dimension(5,0)));
		this.add(current);
		
		pick.addActionListener(this);
	}
	
	public void finalize() throws Exception{
		if(outputFile == null) throw(new Exception("No directory chosen."));
	}
	
	public File getFile() {
		return outputFile;
	}

	public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(null);
		chooser.setDialogTitle("Choose an Output File");
	    
		if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
			outputFile = chooser.getSelectedFile();
			current.setText("Current Output File: " + outputFile.toString());
		}
	}
}