//
//  NAME:       NGOC CHAU NGUYEN
//  PROJECT:    FOUR
//  DUE:        MAY 10TH, 2024
//  COURSE:     CS-2450-01-SPRING-2024
//
//  DESCRIPTION:
//              Implement a veresion of the Windows Notepad application as "Notepad".
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.util.Scanner;

//import java.io.FileFilter;
//import java.io.*;

public class Notepad
{
    //INSTANCE VARIABLES
    
    //CONSTRUCTOR
    public Notepad()
    {
        ////ABOUT FRAME
        //Create a new JFrame container.
        JFrame myFrame = new JFrame("Notepad");
        myFrame.setIconImage(new ImageIcon("Notepad.png").getImage());
        //Terminate the program when the user closes the application. 
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Specify BorderLayout for the layout manager
        myFrame.setLayout(new BorderLayout());
        //Give the frame an initial size.
        myFrame.setSize(800,600);
        
       
        ////MENU SKELETON
        JMenuBar myMenuBar = new JMenuBar();
        
        //FILE MENU SKELETON
        JMenu myFileMenu = new JMenu("File");
        myFileMenu.setMnemonic('F');
        
        JMenuItem myNewOption = new JMenuItem("New", 'N');
        myNewOption.setAccelerator(KeyStroke.getKeyStroke('N', ActionEvent.CTRL_MASK));
        myFileMenu.add(myNewOption);
        
        JMenuItem myOpenOption = new JMenuItem("Open...", 'O');
        myOpenOption.setAccelerator(KeyStroke.getKeyStroke('O', ActionEvent.CTRL_MASK));
        myFileMenu.add(myOpenOption);
        ////Handling event for OpenOption with FileChooser
        JFileChooser myOpenFileChooser = new JFileChooser(".");
        //myOpenFileChooser.setMultiSelectionEnabled(true); --> should I do it?
        
        
        
        myOpenFileChooser.setFileFilter(new JavaFileFilter());
        myOpenFileChooser.setFileFilter(new TextFileFilter());
        
        myOpenFileChooser.setAcceptAllFileFilterUsed(false);
        
        //Ask professor if you can do void setDragEnabled(boolean)
        
        myOpenOption.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event) throws HeadlessException
            {
                //Pass myFrame as the parent. This centers the dialog on the screen
                int returnValue = myOpenFileChooser.showOpenDialog(myFrame);
                if(returnValue == JFileChooser.APPROVE_OPTION)
                {
                    File myFile = myOpenFileChooser.getSelectedFile();
                    Scanner myFileData = null;
                    try
                    {
                        FileReader myFileReader = new FileReader(myFile.getName());
                    }
                    catch(FileNotFoundException exception)
                    {
                        System.out.println(exception.getMessage());
                    }
                }
            }
        });
        
        JMenuItem mySaveOption = new JMenuItem("Save", 'S');
        mySaveOption.setAccelerator(KeyStroke.getKeyStroke('S', ActionEvent.CTRL_MASK));
        JFileChooser mySaveFileChooser = new JFileChooser(".");
        mySaveOption.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent event)
            {
                int returnValue = mySaveFileChooser.showSaveDialog(myFrame);
            }
        });
        
        
        myFileMenu.add(mySaveOption);
        
        JMenuItem mySaveAsOption = new JMenuItem("Save As...");
        mySaveAsOption.setMnemonic('a'); //do I need this line?
        mySaveAsOption.setDisplayedMnemonicIndex(5);
        myFileMenu.add(mySaveAsOption);
    
        //Add separator for File Menu
        myFileMenu.addSeparator();
        //------------------------------------
        
        JMenuItem mySetupOption = new JMenuItem("Page Setup...",'u');
        myFileMenu.add(mySetupOption);
        
        JMenuItem myPrintOption = new JMenuItem("Print...", 'P');
        myPrintOption.setAccelerator(KeyStroke.getKeyStroke('P', ActionEvent.CTRL_MASK));
        myFileMenu.add(myPrintOption);
        
        myMenuBar.add(myFileMenu);
        
        //Add separator 
        myFileMenu.addSeparator();
        //------------------------------------
        
        JMenuItem myExitOption = new JMenuItem("Exit", 'E');
        myFileMenu.add(myExitOption);
        
        
        //EDIT MENU SKELETON
        JMenu myEditMenu = new JMenu("Edit");
        myEditMenu.setMnemonic('E');
        
        JMenuItem myUndoOption = new JMenuItem("Undo", 'U'); 
        myEditMenu.addSeparator();
        //------------------------------------
        
        JMenuItem myCutOption = new JMenuItem("Cut",'t');
        myCutOption.setAccelerator(KeyStroke.getKeyStroke('X', ActionEvent.CTRL_MASK));
        myEditMenu.add(myCutOption);
        
        JMenuItem myCopyOption = new JMenuItem("Copy", 'C');
        myCopyOption.setAccelerator(KeyStroke.getKeyStroke('C', ActionEvent.CTRL_MASK));
        myEditMenu.add(myCopyOption);
        
        JMenuItem myPasteOption = new JMenuItem("Paste", 'P');
        myPasteOption.setAccelerator(KeyStroke.getKeyStroke('V', ActionEvent.CTRL_MASK));
        myEditMenu.add(myPasteOption);
        
        JMenuItem myDeleteOption = new JMenuItem("Delete", 'l');
        //Set the accelerator "Del" key to Delete Option without any modifiers
        myDeleteOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
       
        myEditMenu.add(myDeleteOption);
        myEditMenu.addSeparator();
        //------------------------------------
        
        JMenuItem myFindOption = new JMenuItem("Find...", 'F');
        myFindOption.setAccelerator(KeyStroke.getKeyStroke('F', ActionEvent.CTRL_MASK));
        myEditMenu.add(myFindOption);
        
        JMenuItem myFindNextOption = new JMenuItem("Find Next");
        myFindNextOption.setDisplayedMnemonicIndex(5);
        myEditMenu.add(myFindNextOption);
        
        JMenuItem myReplaceOption = new JMenuItem("Replace...",'R');
        myReplaceOption.setAccelerator(KeyStroke.getKeyStroke('H', ActionEvent.CTRL_MASK));
        myEditMenu.add(myReplaceOption);
        
        JMenuItem myGoToOption = new JMenuItem("Go To...", 'G');
        myGoToOption.setAccelerator(KeyStroke.getKeyStroke('G', ActionEvent.CTRL_MASK));
        myEditMenu.add(myGoToOption);
        
        myEditMenu.addSeparator();
        //------------------------------------
        
        JMenuItem mySelectAllOption = new JMenuItem("Select All", 'A');
        mySelectAllOption.setAccelerator(KeyStroke.getKeyStroke('A', ActionEvent.CTRL_MASK));
        myEditMenu.add(mySelectAllOption);
        
        JMenuItem myTimeDateOption = new JMenuItem("Time/Date", 'D');
        myTimeDateOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        myEditMenu.add(myTimeDateOption);
        
        myMenuBar.add(myEditMenu);
        
        //FORMATE MENU SKELETON
        JMenu myFormatMenu = new JMenu("Format");
        myFormatMenu.setMnemonic('o');
        
        JMenuItem myWordWrapOption = new JMenuItem("Word Wrap", 'W');
        myFormatMenu.add(myWordWrapOption);
        
        JMenuItem myFontOption = new JMenuItem("Font...", 'F');
        myFormatMenu.add(myFontOption);
        
        JMenu myColorOption = new JMenu("Color");
        myColorOption.setMnemonic('C');
        
        JMenuItem myBackgroundColorOption = new JMenuItem("Background...", 'B');
        myColorOption.add(myBackgroundColorOption);
        
        JMenuItem myForegroundColorOption = new JMenuItem("Foreground...", 'F');
        myColorOption.add(myForegroundColorOption);
        
        myFormatMenu.add(myColorOption);
        myMenuBar.add(myFormatMenu);
        

        //VIEW MENU SKELETON
        JMenu myViewMenu = new JMenu("View");
        myViewMenu.setMnemonic('V');
        
        JMenuItem myStatusBarOption = new JMenuItem("Status Bar", 'S');
        myViewMenu.add(myStatusBarOption);
        
        myMenuBar.add(myViewMenu);
        //------------------------------------
        
        JMenu myHelpMenu = new JMenu("Help");
        myHelpMenu.setMnemonic('H');
        
        JMenuItem myViewHelpOption = new JMenuItem("View Help", 'H');
        myHelpMenu.add(myViewHelpOption);
        
        JMenuItem myExtraCredits = new JMenuItem("Extra Credits...", 'x');
        myHelpMenu.add(myExtraCredits);
        
        myHelpMenu.addSeparator();
        //------------------------------------
        
        JMenuItem aboutNotepadOption = new JMenuItem("About Notepad", 'A');
        myHelpMenu.add(aboutNotepadOption);
        
        myMenuBar.add(myHelpMenu);
        //------------------------------------
        
        myFrame.setJMenuBar(myMenuBar);
        
        //Create the text editor area--still need to work on this
        JTextArea myTextArea = new JTextArea();
        myTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        JScrollPane myScrollPane = new JScrollPane(myTextArea);
        myScrollPane.setPreferredSize(new Dimension(800, 600));
        
        myFrame.add(myScrollPane);
        
        ////GO BACK TO THE FRAME
        //Center the calculator on the default screen at startup.
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);
    
        //Display the frame.
        myFrame.setVisible(true);
    }
    
    //INNER CLASS FOR FILE FILTER
    private class JavaFileFilter extends FileFilter
    {
        
        public boolean accept(File myFile)
        {
            //Return true if the file is a Java Source File or Text File
            if(myFile.getName().endsWith(".java"))
            {
                return true;
            }
            return false;
        }
        
        @Override
        public String getDescription()
        {
            return "Java Files (*.java)";
        }
    }
    
    private class TextFileFilter extends FileFilter
    {
        public boolean accept(File myFile)
         {
             if(myFile.getName().endsWith(".txt"))
             {
                 return true;
             }
             return false;
         }
         
        @Override
        public String getDescription()
        {
            return "Text Files (*.txt)";
        }
    }
    
    public static void main(String[] args)
    {
        //Create the frame on the event dispatching thread. 
        //should be new Notepad(args[0])?!
        //Or is that new Notepad(args.length != 0 ? args[0] : null)
        //Need to read the input from command line arguments.
        
        SwingUtilities.invokeLater(() -> new Notepad());
    }
  
}
