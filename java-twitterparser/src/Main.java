import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


public class Main {
	//GUI components
	JFrame frame;
	JPanel mainPanel;
	ArgPanel argPanel;
	JPanel formatPanel;
	JTextField formatField;
	InputPanel inputPanel;
	OutputPanel outputPanel;
	JPanel filePanel;
	JPanel controlPanel;
	JButton startButton;
	
	//Parsing variables
	public ArrayList<String> argList;
	public ArrayList<String> formList; 
	public String formatString;
	File inputFile;
	File outputFile;
	
	public Main() {
		argList = new ArrayList<String>();
		formList = new ArrayList<String>();
		inputFile = null;
		outputFile = null;
		buildGUI();
	}
	
	public void start() {
		try{
			formatString = formatField.getText();
			formList = createFormList(formatString);
			argPanel.finalize();
			inputPanel.finalize();
			outputPanel.finalize();
			inputFile = inputPanel.getFile();
			outputFile = outputPanel.getFile();
			ParseFile(inputFile, outputFile);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null,
				    "Setup not complete.\n"
				    + "Your particular error was:\n"
				    + e.toString(), "Setup Incomplete",
				    JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/*
	 * This is the key method of this app
	 * Takes an input file, tokenizes it into raw tweets
	 * Then uses the tokenized format string and argument list to print a formatted output. 
	 */
	public int ParseFile(File inputFile, File outputFile) {
		PrintStream printer;
		BufferedReader bufferedInput;
		
		try {
			printer = new PrintStream(outputFile);
			bufferedInput = new BufferedReader(new FileReader(inputFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return(1);
		}
		
		Scanner tweets = new Scanner(bufferedInput);
		tweets.useDelimiter("\\{new\\}");
		
		JSONObject nextTweet;
		String outputString;
		while(tweets.hasNext()) {
			nextTweet = (JSONObject) JSONValue.parse(tweets.next()); 
			
			outputString = "";
			for(int i = 0; i < formList.size(); i++) {
				outputString += formList.get(i);
				//May be one more form string than arg string
				if(i < argList.size()) {
					outputString += nextTweet.get(argList.get(0));
				}
			}
			
			printer.print(outputString);
		}
		
		try {
			tweets.close();
			printer.close();
			bufferedInput.close();
		} catch (IOException e) {
			e.printStackTrace();
			return(-1);
		}
		return 0;
	}
	
	/*
	 * This function splits the format string using the % as a delimiter. 
	 * Note that the java scanner read's in \n as \\n, which we fix.
	 * Same with \t
	 * Lastly, to make things easier during the printing stage we make the assumption that:
	 * All format strings won't start with an argument.
	 * So if this is the case we simple add a black string in the beginning of the list. 
	 */
	public ArrayList<String> createFormList(String formatString) {
		ArrayList<String> result = new ArrayList<String>();
		
		/*Add "" First if format string starts with %*/
		if(formatString.charAt(0) == '%') result.add("");
		
		/*Tokenize format string with %, replace special characters*/
		Scanner format = new Scanner(formatString);
		format.useDelimiter(Pattern.compile("%"));
		String nextForm;
		while(format.hasNext()) {
			nextForm = format.next();
			nextForm = nextForm.replaceAll("\\\\n", "\n");
			nextForm = nextForm.replaceAll("\\\\t", "\t");
			result.add(nextForm);
		}
		format.close();

		return result;
	}
	
	private void buildGUI() {

    	JFrame frame = new JFrame("Twitter Parser");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	mainPanel = new JPanel();
    	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    	frame.getContentPane().add(mainPanel);
    	
    	argPanel = new ArgPanel(argList);
    	mainPanel.add(argPanel);
    	
    	formatPanel = new JPanel();
    	formatPanel.setLayout(new FlowLayout());
    	JLabel formatLabel = new JLabel("Format String:");
    	formatField = new JTextField();
    	formatField.setPreferredSize(new Dimension(100,20));
    	formatPanel.add(formatLabel);
    	formatPanel.add(formatField);
    	mainPanel.add(formatPanel);
    	
    	inputPanel = new InputPanel();
    	mainPanel.add(inputPanel);
    	
    	outputPanel = new OutputPanel();
    	mainPanel.add(outputPanel);
    	
    	controlPanel = new JPanel();
    	startButton = new JButton("Start");
    	controlPanel.add(startButton);
    	mainPanel.add(controlPanel);
    	
    	startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {start();}
    	});
    	
    	frame.pack();
    	frame.setVisible(true);
	}
	

	public static void main(String[] args) {
		new Main();
	}
}
