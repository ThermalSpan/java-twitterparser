import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ArgPanel extends JPanel{
	private ArrayList<String> argList;
	final private DefaultListModel<String> dataModel;
	

	public ArgPanel(ArrayList<String> argList) {
		super();
		this.argList = argList;
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		final JLabel title = new JLabel("Argument List");
		this.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		dataModel = new DefaultListModel<String>();
		final JList<String> optionList = new JList<String>(dataModel);
		final JScrollPane scrollPane = new JScrollPane(optionList);
		this.add(scrollPane);
		scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//This information can be retrieved though a REST Api, this should be added as a feature in the future. 
		String options[] = {"annotations", "contributors", "coordinates", "created_at", "current_user_retweet", "entities", "favorite_count", "favorited", "filter_level", "geo", "id", "id_str", "in_reply_to_screen_name", "in_reply_to_status_id", "in_reply_to_status_id_str", "in_reply_to_user_id", "in_reply_to_user_id_str", "lang", "place", "possibly_sensitive", "scopes", "retweet_count", "retweeted", "retweeted_status", "source", "text", "truncated", "user", "user:id", "withheld_copyright", "withheld_in_countries", "withheld_scope", "screen_name", "type"};
		final JComboBox<String> comboBox = new JComboBox<String>();
		for(int i = 0; i< options.length; i++){
			comboBox.addItem(options[i]);
		}
		
		
		
		final JButton removeButton = new JButton("Del. Argument");
		removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(comboBox);
		controlPanel.add(removeButton);
		this.add(controlPanel);

		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(String s: optionList.getSelectedValuesList()){
					dataModel.removeElement(s);
				}
			}
		});
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dataModel.addElement(comboBox.getItemAt(comboBox.getSelectedIndex()));
			}
		});
	}

	public void finalize() {
		argList.clear();
		//Note that this is Oracle's suggested use case as of java 7
		for(Enumeration<String> e = dataModel.elements(); e.hasMoreElements();){
			argList.add(e.nextElement());
		}
	}

}