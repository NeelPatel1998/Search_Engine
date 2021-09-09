/**
 *  Developed by Abishek Rajagopal
 */

package Egine_Windiow_UI;

import java.awt.*;  
import javax.swing.JLabel;

import Inverted_Index.InvertedIndex;

import java.awt.*;  
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List; 


class Window extends Frame implements ActionListener{  
	TextField SearchField,TimeField;  
	  
	Label l1,l2,l3;
	TextArea TA,Time;
	public Window()
	{  
  
		
		l1=new Label("Search Here :");  
	    l1.setBounds(60,50,170,20);  
	    
	    
	    SearchField = new TextField();  
		SearchField.setBounds(150,50,170,20);  
		
		Button b=new Button("Search");  
		b.setBounds(350,50,80,30);  
		  
		TA = new TextArea(); 
		TA.setBounds(60,100, 980,620);  
		
		l2=new Label("Time Taken :");  
	    l2.setBounds(60,750,170,20);  
	    
	    Time=new TextArea(); 
	    Time.setBounds(60,780,200,20); 
//	    TimeField.setEditable(false); 
		
		b.addActionListener(this);
		  
		
		add(b);
		add(SearchField);    
		add(l1);
		add(l2);
		add(Time);
		add(TA);
		setSize(1080,820);  
		setLayout(null);  
		setVisible(true);  
		
		addWindowListener(new WindowAdapter(){  
            public void windowClosing(WindowEvent e) {  
                dispose();  
            }  
		});  

		
	}
	
	public void actionPerformed(ActionEvent e) 
	{  
		String File = "";
		long start, end;
		start = System.currentTimeMillis();
		try {
		InvertedIndex index = new InvertedIndex();
		index.initializeIndex();
		TA.setText(File);
		Time.setText(File);
	    
		List<String> files = index.search(SearchField.getText());
		
		for (int i = 0; i < files.size(); i=i+2)   
		{  
		//prints the elements of the List  
			File = files.get(i+1) + "\n" + files.get(i) + "\n\n";
			TA.append(File);
			
		}  
		end = System.currentTimeMillis();
		
		Time.append(String.valueOf((end - start)/1000) + " seconds");
		}
		catch (IOException r) {
	      System.out.println("An error occurred.");
	      r.printStackTrace();
	    }
		
		  
		
	}  

	public static void main(String args[])
	{  
		new Window();  
	}  
}  