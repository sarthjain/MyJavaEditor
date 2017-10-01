import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.text.DefaultEditorKit;

public class MyEditor implements ActionListener,FocusListener
{
	JFrame jf,frame;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem menuItem,exit;
	JButton submit,cancel,newButton,saveButton,clearButton,openButton,closeButton,runButton,compileButton,about_me,about,exitButton,wordwrap;
	JScrollPane jsp,jsp1;
	JTextArea jta,jta1,jinput;
	JPanel p1,p2,p3,pbut;
	JToolBar mainToolBar;
	File file;
	JLabel l,l2,text;
	Runtime r;
	String s="";
	String s3="",s4="";
	String result="";
	String result1="";
	final JFileChooser fc;
	int i=0,j=0,k=0,width,height;
	boolean inputGiven=false;
	MyEditor()
	{
		jf=new JFrame("MY EDITOR");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		jf.setBounds(0,0,width,height);

		//creation of Menu in function createmMenu()
		createMenu();
		menuBar.setPreferredSize(new Dimension(width,height/36));
		jf.setJMenuBar(menuBar);

		//creation of Toolbar
		mainToolBar=new JToolBar();
		createToolBar();
		jf.add(mainToolBar,BorderLayout.NORTH);

		submit=new JButton("SUBMIT");
		cancel=new JButton("CANCEL");
		p2=new JPanel();
		p2.setPreferredSize(new Dimension(width,4*(height/18)));
		jta=new JTextArea("");
		jta.setFont(new Font("Century Gothic",Font.PLAIN,15));
		jta1=new JTextArea("");
		jta.addFocusListener(new FocusListener()
		{
			public void focusGained(FocusEvent e)
			{
				if(i==0)
				{
					jta1.requestFocus();
					JOptionPane.showMessageDialog(jf,"No file is currently open");
				}
			}

	
			public void focusLost(FocusEvent e)
			{}
		});
		jta1.setFont(new Font("varinda",Font.PLAIN,15));
		jta1.setEditable(false);
		jinput=new JTextArea("");
		jinput.setFont(new Font("varinda",Font.PLAIN,15));
		jinput.setLineWrap(true);
		jinput.setWrapStyleWord(true);
		jinput.addFocusListener(this);
		jsp=new JScrollPane(jta);
		jsp1=new JScrollPane(jta1);
		submit.setPreferredSize(new Dimension(width/10,height/9));
		submit.setBackground(Color.gray);
		submit.setForeground(Color.white);
		submit.setFont(new Font("Serif", Font.BOLD, 16));
		set();
		cancel.setEnabled(false);
		cancel.setPreferredSize(new Dimension(width/10,height/9));
		cancel.setBackground(Color.gray);
		cancel.setForeground(Color.white);
		cancel.setFont(new Font("Serif", Font.BOLD, 16));
		submit.addActionListener(this);
		cancel.addActionListener(this);
		submit.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
		cancel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.black));
		l2=new JLabel("Enter Input",SwingConstants.CENTER);
		l2.setOpaque(true);
		l2.setFont(new Font("Serif", Font.BOLD, 16));
		l2.setBackground(Color.lightGray);
		l2.setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, Color.black));
		pbut=new JPanel();
		pbut.setLayout(new BorderLayout());
		pbut.setPreferredSize(new Dimension(width/3,2*height/3));
		jsp.setPreferredSize(new Dimension(2*(width/3),2*height/3));
		jinput.setPreferredSize(new Dimension(width/3,5*(height/9)));
		jsp1.setPreferredSize(new Dimension(width,4*(height/18)));
		jsp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
		jsp1.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
		jinput.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, Color.black));
		jinput.setBackground(Color.lightGray);
		jta1.setBackground(Color.lightGray);
		pbut.add(jinput,BorderLayout.NORTH);
		pbut.add(submit,BorderLayout.WEST);
		pbut.add(l2,BorderLayout.CENTER);
		pbut.add(cancel,BorderLayout.EAST);
		p3=new JPanel();
		p3.setLayout(new BorderLayout());
		p3.setPreferredSize(new Dimension(width,height/2));
		p3.add(jsp,BorderLayout.WEST);
		p3.add(pbut,BorderLayout.CENTER);
		jf.add(p3,BorderLayout.CENTER);
		p3.setVisible(true);
		l=new JLabel(" CONSOLE SCREEN");
		l.setOpaque(true);
		l.setFont(new Font("Serif", Font.BOLD, 18));
		l.setBackground(Color.darkGray);
		l.setForeground(Color.white);
		l.setSize(width,height/18);
		p2.setLayout(new BorderLayout());
		p2.add(l,BorderLayout.NORTH);
		p2.add(jsp1,BorderLayout.CENTER);
		jf.add(p2,BorderLayout.SOUTH);
		fc = new JFileChooser();
		r=Runtime.getRuntime();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		p2.setVisible(true);
		jf.setVisible(true);
		/*try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(jf);
		} catch(Exception e) {}
		jf.pack();*/
	}
	@Override
	public void focusGained(FocusEvent e)
	{
		if(!inputGiven && jinput.isEditable())
		{
			jinput.setText("");
			inputGiven=true;
		}
	}

	@Override
	public void focusLost(FocusEvent e)
	{}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("New File") || e.getSource()==newButton)
		{
			if(i==0)
			{
				jta.setText("\npublic class Newclass\n{\n	public static void main(String... args)\n	{\n		\n	}\n}");
				i=1;
				setButton();
			}
			else
				JOptionPane.showMessageDialog(jf,"one file is already open, first close that file...");
		}
		else if(e.getActionCommand().equals("Open File") || e.getSource()==openButton)
		{
			if(i==0)
			{
			int returnVal = fc.showOpenDialog(jf);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fc.getSelectedFile();
				s=file.getAbsolutePath();
				StringTokenizer st=new StringTokenizer(s,"\\");
				s3=s3+st.nextToken();
				while(st.hasMoreTokens())
				{
					s3=s3+"\\"+st.nextToken();
					if(st.hasMoreTokens())
						s4=s3;
				}
				s4=s4+"\\";
				boolean b=s.endsWith(".java");
				if(b)
				{
				try{
				BufferedReader ir=new BufferedReader(new FileReader(file));
				String sr=ir.readLine();
				while(!sr.equals(null))
				{
					jta.append(sr+"\n");
					sr=ir.readLine();
				}
				}catch(Exception g){}
				i=1;
				j=1;
				setButton();
				}
				else{
					JOptionPane.showMessageDialog(jf,"Cannot open this file.... ");
				}
			}
			}
			else
				JOptionPane.showMessageDialog(jf,"one file is already open, first close that file...");
		}
		else if(e.getActionCommand().equals("Save") || e.getSource()==saveButton)
		{
			if(j==0 && k==0 && i==1)
			{
				int userSelection = fc.showSaveDialog(jf);
 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile();
					s=file.getAbsolutePath();
					StringTokenizer st=new StringTokenizer(s,"\\");
					s3=s3+st.nextToken();
					while(st.hasMoreTokens())
					{
						s3=s3+"\\"+st.nextToken();
						if(st.hasMoreTokens())
						s4=s3;
					}
					s4=s4+"\\";
					try{
					PrintWriter pw=new PrintWriter(new FileWriter(file));
					String s1=jta.getText();
					pw.println(s1);
					pw.flush();
					}catch(Exception g){}
					k=1;
				}
				
			}
			else if(i==0)
				JOptionPane.showMessageDialog(jf,"No file is opened...");
			else
			{
				try{
					set();
					file.delete();
					PrintWriter pw=new PrintWriter(new FileWriter(file));
					String s1=jta.getText();
					pw.println(s1);
					pw.flush();
					}catch(Exception g){}
			}
		}
		else if(e.getActionCommand().equals("Save As"))
		{	if(i==1)
			{
			int userSelection = fc.showSaveDialog(jf);
 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					set();
					file = fc.getSelectedFile();
					s=file.getAbsolutePath();
					StringTokenizer st=new StringTokenizer(s,"\\");
					s3=s3+st.nextToken();
					while(st.hasMoreTokens())
					{
						s3=s3+"\\"+st.nextToken();
						if(st.hasMoreTokens())
							s4=s3;
					}
					s4=s4+"\\";
					try{
					PrintWriter pw=new PrintWriter(new FileWriter(file));
					String s1=jta.getText();
					pw.println(s1);
					pw.flush();
					}catch(Exception g){}
					k=1;
				}
				
			}
			else
				JOptionPane.showMessageDialog(jf,"no file is opened...");
		}
		else if(e.getActionCommand().equals("Compile") || e.getSource()==compileButton)
		{
			if(j==1 || k==1)
			{
				try{
					set();
				Process error =r.exec("javac -d "+s4+" "+s3);
				BufferedReader err =  new BufferedReader(new InputStreamReader(error.getErrorStream()));
				while(true)
				{
					String temp=err.readLine();
					jta1.setText(temp);
					if(temp!=null)
					{
						result+=temp;
						result+="\n";
					}
					else break;
				}
					if(result.equals(""))
					{
						jta1.setText("Compilation Successfull...."+s);
						err.close();
					}
					else jta1.setText(result);
				result="";
				}catch(Exception g){}
			}
			else if(i==0)
				JOptionPane.showMessageDialog(jf,"No file is opened...");
			else
			{
				JOptionPane.showMessageDialog(jf,"First save the file, then compile...");	
			}
		}
		else if(e.getActionCommand().equals("Run") || e.getSource()==runButton)
		{
			if(j==1 || k==1)
			{
				jinput.setBackground(Color.white);
				jinput.setText("Give the Input here if it's required in program otherwise press Submit");
				inputGiven=false;
				submit.setEnabled(true);
				cancel.setEnabled(true);
				jinput.setEditable(true);
				//jinput.requestFocus();
			}
			else if(i==0)
				JOptionPane.showMessageDialog(jf,"No file is opened...");
			else
			{
				JOptionPane.showMessageDialog(jf,"First save the file, then run");	
			}
		}
		else if(e.getSource()==submit)
		{
			try
			{
				String str=jinput.getText();
				if(!inputGiven)
					str="";
				int n=file.getName().indexOf('.');
				String s2=file.getName().substring(0,n);
				ProcessBuilder builder=new ProcessBuilder("cmd.exe","/c","cd /d \""+s4+"\" && java "+s2);
				builder.redirectErrorStream(true);
				Process p=builder.start();
				BufferedWriter input = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
				input.write(str);
				input.close();
				BufferedReader output =  new BufferedReader(new InputStreamReader(p.getInputStream()));
				BufferedReader err =  new BufferedReader(new InputStreamReader(p.getErrorStream()));
				while(true)
				{
					String temp=output.readLine();
					if(temp!=null)
					{
						result+=temp;
						result+="\n";
					}
					else break;
				}
				while(true)
				{
					String temp=err.readLine();
					if(temp!=null)
					{
						result1+=temp;
						result1+="\n";
					}
					else break;
				}
				output.close();
				err.close();
				jta1.setText(result+"\n"+result1);
				result="";
				result1="";
				set();
			}catch(Exception g){}
		}
		else if(e.getSource()==cancel)
		{
			set();
			jinput.setText("");
			//jinput.requestFocus();
		}
		else if(e.getActionCommand().equals("Close File") ||e.getSource()==closeButton)
		{
			s="";
			s3="";
			s4="";
			file=null;
			jta.setText("");
			jta1.setText("");
			i=j=k=0;
			jta1.requestFocus();
			setButton();
		}
		else if(e.getActionCommand().equals("Exit") || e.getSource()==exitButton)
		{
			int a=JOptionPane.showConfirmDialog(jf,"Are you sure?");  
			if(a==JOptionPane.YES_OPTION)  
   				System.exit(0); 
		}
		else if(e.getSource()==clearButton || e.getActionCommand().equals("Clear"))
		{
			if(i==1)
			{
				int a=JOptionPane.showConfirmDialog(jf,"Do you really want to clear the Text?");  
				if(a==JOptionPane.YES_OPTION)  
   					jta.setText("");
			}
		}
		else if(e.getSource()==wordwrap)
		{
			if(jta.getLineWrap())
			{	
				jta.setLineWrap(false);
				wordwrap.setBackground(Color.white);
			}
			else
			{
				jta.setLineWrap(true);
				wordwrap.setBackground(Color.lightGray);
			}
		}
		else if(e.getSource()==about_me || e.getActionCommand().equals("About Me"))
		{
			frame = new JFrame("About Me");
			frame.setVisible(true);
        	frame.setSize(500,300);
	       	String contentText =
	        "<html><body><p>" +
	        "Author: Sarthak Jain<br />" +
	        "Contact me at: " +
	        "<a href='mailto:" + "sarthak734@gmail.com" + "?subject=About the NotePad PH Software'>" + "sarthak734@gmail.com" + "</a>" +"</p></body></html>";
	        text = new JLabel(contentText,SwingConstants.CENTER);
	        frame.add(text);
		}
		else if(e.getSource()==about || e.getActionCommand().equals("About MyEditor"))
		{
			frame = new JFrame("About MyEditor");
			frame.setVisible(true);
        	frame.setSize(500,300);
        	text = new JLabel();
	       	String contentText =
	        "<html><body><p>" +
	       	"Name: " + "MyEditor" + "<br />" +
       		"Version: " + "1.0" +"</p></body></html>";
	        text = new JLabel(contentText,SwingConstants.CENTER);
	        frame.add(text);
		}
	}
	public static void main(String[] args)
	{
		new MyEditor();
	}

	private void set()
	{
		submit.setEnabled(false);
		cancel.setEnabled(false);
		jinput.setEditable(false);
		jinput.setText("");
		jinput.setBackground(Color.lightGray);
		jta.requestFocus();
	}

	private void createMenu()
	{
		
		Font f = new Font("sans-serif", Font.BOLD, 16);
        UIManager.put("Menu.font", f);

		//creates a menuBar
		menuBar = new JMenuBar();

		//creates a menu called File and add it to menuBar
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.setPreferredSize(new Dimension(width/24,height/18));
		menuBar.add(menu);

		//Add menuItems to File menu
		//New File
		menuItem = new JMenuItem("New File");
		menuItem.addActionListener(this);
		menuItem.setMnemonic(KeyEvent.VK_N);
		menuItem.setIcon(new ImageIcon("images/new.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		//Open File
		menuItem = new JMenuItem("Open File");
		menuItem.addActionListener(this);
		menuItem.setMnemonic(KeyEvent.VK_O);
		menuItem.setIcon(new ImageIcon("images/open.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		//Save File
		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(this);
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.setIcon(new ImageIcon("images/save.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		//SAve As
		menuItem = new JMenuItem("Save As");
		menuItem.addActionListener(this);
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.setIcon(new ImageIcon("images/saveas.gif"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		menu.add(menuItem);

		//add seperator line
		menu.addSeparator();

		//Close File
		menuItem = new JMenuItem("Close File");
		menuItem.addActionListener(this);
		menuItem.setMnemonic(KeyEvent.VK_C);
		menuItem.setIcon(new ImageIcon("images/close_file.jpg"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		//add seperator line
		menu.addSeparator();

		//Exit
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		exit.setIcon(new ImageIcon("images/close.png"));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menu.add(exit);

		//Add Another menu Edit
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);
		menu.setPreferredSize(new Dimension(width/24,height/18));
		menuBar.add(menu);

		//Add menuItems to Edit menu
		//Select All
		menuItem = new JMenuItem("Select All");
		menuItem.addActionListener(this);
		menuItem.setIcon(new ImageIcon("images/selectall.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		//Clear
		menuItem = new JMenuItem("Clear");
		menuItem.addActionListener(this);
		menuItem.setIcon(new ImageIcon("images/clear.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K,ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		//Cut
		menuItem = new JMenuItem(new DefaultEditorKit.CutAction());
		menuItem.setText("Cut");
		menuItem.setIcon(new ImageIcon("images/cut.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		//Copy
		menuItem = new JMenuItem(new DefaultEditorKit.CopyAction());
		menuItem.setText("Copy");
		menuItem.setIcon(new ImageIcon("images/copy.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		//Paste
		menuItem = new JMenuItem(new DefaultEditorKit.PasteAction());
		menuItem.setText("Paste");
		menuItem.setIcon(new ImageIcon("images/paste.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
		menu.add(menuItem);

		//Add Another menu Tools
		menu = new JMenu("Tools");
		menu.setMnemonic(KeyEvent.VK_T);
		menu.setPreferredSize(new Dimension(width/20,height/18));
		menuBar.add(menu);

		//Add menuItems to Tools menu
		//Compile
		menuItem = new JMenuItem("Compile");
		menuItem.addActionListener(this);
		menuItem.setMnemonic(KeyEvent.VK_M);
		menuItem.setIcon(new ImageIcon("images/compile.gif"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9,ActionEvent.ALT_MASK));
		menu.add(menuItem);

		//Run
		menuItem = new JMenuItem("Run");
		menuItem.addActionListener(this);
		menuItem.setMnemonic(KeyEvent.VK_R);
		menuItem.setIcon(new ImageIcon("images/run.jpg"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
	
		//Add Another menu About
		menu = new JMenu("About");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.setPreferredSize(new Dimension(width/20,height/18));
		menuBar.add(menu);

		//Add menuItems to Tools menu
		//About Me
		menuItem = new JMenuItem("About Me");
		menuItem.addActionListener(this);
		menuItem.setIcon(new ImageIcon("images/about_me.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
		menu.add(menuItem);

		menuItem = new JMenuItem("About MyEditor");
		menuItem.addActionListener(this);
		menuItem.setIcon(new ImageIcon("images/about.png"));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,0));
		menu.add(menuItem);

	}


	private void createToolBar()
	{
		newButton = new JButton(new ImageIcon("images/new.png"));
        newButton.setToolTipText("New");
        newButton.addActionListener(this);
        mainToolBar.add(newButton);
        mainToolBar.addSeparator();

		openButton = new JButton(new ImageIcon("images/open.png"));
        openButton.setToolTipText("Open");
        openButton.addActionListener(this);
        mainToolBar.add(openButton);
        mainToolBar.addSeparator();

		saveButton = new JButton(new ImageIcon("images/save.png"));
       	saveButton.setToolTipText("Save");
        saveButton.addActionListener(this);
        mainToolBar.add(saveButton);
        mainToolBar.addSeparator();

        wordwrap = new JButton(new ImageIcon("images/wordwrap.png"));
        wordwrap.setToolTipText("Word Wrap");
        wordwrap.setEnabled(false);
        wordwrap.addActionListener(this);
        mainToolBar.add(wordwrap);
        mainToolBar.addSeparator();  

		clearButton = new JButton(new ImageIcon("images/clear.png"));
        clearButton.setToolTipText("Clear");
        clearButton.setEnabled(false);
        clearButton.addActionListener(this);
        mainToolBar.add(clearButton);
        mainToolBar.addSeparator();

		compileButton = new JButton(new ImageIcon("images/compile.gif"));
        compileButton.setToolTipText("Compile");
        compileButton.setEnabled(false);
        compileButton.addActionListener(this);
        mainToolBar.add(compileButton);
        mainToolBar.addSeparator();

		runButton = new JButton(new ImageIcon("images/run.jpg"));
        runButton.setToolTipText("Run");
        runButton.setEnabled(false);
        runButton.addActionListener(this);
        mainToolBar.add(runButton);
        mainToolBar.addSeparator();

		closeButton = new JButton(new ImageIcon("images/close_file.jpg"));
        closeButton.setToolTipText("Close File");
        closeButton.setEnabled(false);
        closeButton.addActionListener(this);
        mainToolBar.add(closeButton);
        mainToolBar.addSeparator();
        mainToolBar.addSeparator();
        mainToolBar.addSeparator();
        mainToolBar.addSeparator();
        mainToolBar.addSeparator();
        mainToolBar.addSeparator();
        mainToolBar.addSeparator();

        about_me = new JButton(new ImageIcon("images/about_me.png"));
        about_me.setToolTipText("About Me");
        about_me.addActionListener(this);
        mainToolBar.add(about_me);
        mainToolBar.addSeparator();

        about = new JButton(new ImageIcon("images/about.png"));
        about.setToolTipText("About Software");
        about.addActionListener(this);
        mainToolBar.add(about);
        mainToolBar.addSeparator();
		mainToolBar.addSeparator();
        mainToolBar.addSeparator();
        mainToolBar.addSeparator();
       
       	exitButton = new JButton(new ImageIcon("images/close.png"));
        exitButton.setToolTipText("Close File");
        exitButton.addActionListener(this);
        mainToolBar.add(exitButton);
	}

	private void setButton()
	{
		if(i==0)
		{
			closeButton.setEnabled(false);
			compileButton.setEnabled(false);
			runButton.setEnabled(false);
			clearButton.setEnabled(false);
			wordwrap.setEnabled(false);
		}
		else if(i==1)
		{
			closeButton.setEnabled(true);
			compileButton.setEnabled(true);
			runButton.setEnabled(true);
			clearButton.setEnabled(true);
			wordwrap.setEnabled(true);
		}
	}
} 