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
import java.time.LocalDateTime;
//import java.io.FileFilter;
//import java.io.*;

public class Notepad
{
    //INSTANCE VARIABLES
    private JFrame myFrame;
    private JTextArea myTextArea;
    public JFileChooser myOpenFileChooser;
    private String myFileName;
    boolean wrapped;
    private File myFile;
    private FileReader myFileReader;
    private boolean newFileCreated;
    private boolean fileExisted;
    private String myDirectoryPath;
    //CONSTRUCTOR
    public Notepad()
    {
        myFileName = "";
        ////ABOUT FRAME
        //Create a new JFrame container.
        myFrame = new JFrame("Notepad");
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
        myOpenFileChooser = new JFileChooser(".");
        myOpenFileChooser.setFileFilter(new JavaFileFilter());
        myOpenFileChooser.setFileFilter(new TextFileFilter());

        myOpenFileChooser.setAcceptAllFileFilterUsed(false);

        //Ask professor if you can do void setDragEnabled(boolean)

        myOpenOption.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event) throws HeadlessException
                {
                    //Dialogs.fileOpenDialog(myFrame);
                    if(myOpenFileChooser.showOpenDialog(myFrame) == JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            myFile = myOpenFileChooser.getSelectedFile();
                            myFileName = myFile.getName();
                            System.out.println("Want to open: "+myFileName);
                            
                            if(!myFile.exists())
                            {
                                int answer = JOptionPane.showConfirmDialog(myFrame, myFileName + "\nFile not found.\nClick Cancel if you want to check the file name and try again.\nClick OK if you want to create a new file.", "Create a new file?", JOptionPane.OK_CANCEL_OPTION);
                                if(answer == JOptionPane.OK_OPTION)
                                {
                                    myDirectoryPath = myOpenFileChooser.getCurrentDirectory().getName();
                                    FileFilter chosenFilter = myOpenFileChooser.getFileFilter();
                                    switch(chosenFilter.getDescription())
                                    {
                                        case "Java Files (*.java)":
                                            myFileName = myFileName + ".java";
                                            break;
                                        case "Text Files (*.txt)":
                                            myFileName = myFileName + ".txt";
                                            break;
                                    }
                                    myFile = new File(myDirectoryPath + myFileName);
                                    myFile.createNewFile();
                                    newFileCreated = true;
                                }
                                else
                                {
                                    newFileCreated = false;
                                    myOpenFileChooser.showOpenDialog(myFrame);
                                }
                            }
                            else
                            {
                                fileExisted = true;
                            }
                            if(fileExisted || newFileCreated)
                            {
                                myFileName = myFile.getName();
                                myFileReader = new FileReader(myFileName);
                                System.out.println("You chose to open this file: " + myOpenFileChooser.getSelectedFile().getName());
                                myTextArea.read(myFileReader, null);
                                myFileReader.close();
                                myFrame.setTitle("Notepad - " + myFileName); 
                            }
                        }
                        catch(FileNotFoundException e)
                        {
                            //Will need to create a new file if file not exist;
                            System.out.println("New file has been created for you.");

                        }
                        catch(IOException e)
                        {
                            System.out.println("Cannot read file.");
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
                    if(!myFileName.equals(""))
                    {
                        try
                        {
                            FileWriter myFileWriter = new FileWriter(myFileName);
                            myFileWriter.write(myTextArea.getText());
                            myFileWriter.close();
                        }
                        catch(IOException e)
                        {
                            //need to create a new file
                        } 
                    }
                    else
                    {
                        //Will call Save As Dialog 
                    }

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

        //myTextArea.append(LocalDateTime.now());
        myEditMenu.add(myTimeDateOption);

        myMenuBar.add(myEditMenu);

        //FORMATE MENU SKELETON
        JMenu myFormatMenu = new JMenu("Format");
        myFormatMenu.setMnemonic('o');

        JMenuItem myWordWrapOption = new JMenuItem("Word Wrap", 'W');
        wrapped = false;
        myWordWrapOption.addActionListener(event->
            {
                if(!wrapped)
                {
                    myTextArea.setLineWrap(true);
                    wrapped = true;
                }
                else
                {
                    wrapped = false;
                    myTextArea.setLineWrap(false);
                }
            });
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
        aboutNotepadOption.addActionListener(event ->
            {
                JOptionPane.showMessageDialog(null,"Notepad (Special Edition 2024)\n(c)Ngoc Chau Nguyen", "About", JOptionPane.INFORMATION_MESSAGE);
            });
        myHelpMenu.add(aboutNotepadOption);

        myMenuBar.add(myHelpMenu);
        //------------------------------------

        myFrame.setJMenuBar(myMenuBar);

        //Create the text editor area--still need to work on this
        myTextArea = new JTextArea();
        myTextArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        JScrollPane myScrollPane = new JScrollPane(myTextArea);
        myScrollPane.setPreferredSize(new Dimension(800, 600));

        myFrame.add(myScrollPane);

        //CREATE POP UP MENU
        JPopupMenu myPopupMenu = new JPopupMenu();

        JMenuItem myPopupCutOption = new JMenuItem("Cut", 't');
        JMenuItem myPopupCopyOption = new JMenuItem("Copy", 'C');
        JMenuItem myPopupPastOption = new JMenuItem("Paste", 'P');

        myPopupMenu.add(myPopupCutOption);
        //myPopupCutOption.addActionListener(this);

        myPopupMenu.add(myPopupCopyOption);
        myPopupMenu.add(myPopupPastOption);

        //Add a listener for the popup trigger.
        myFrame.addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent event)
                {
                    if(event.isPopupTrigger())
                    {
                        myPopupMenu.show(event.getComponent(), event.getX(), event.getY());
                    }
                }

                public void mouseReleased(MouseEvent event)
                {
                    if(event.isPopupTrigger())
                    {
                        myPopupMenu.show(event.getComponent(), event.getX(), event.getY());
                    }
                }
            });

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

    //INNER CLASS FOR SAVE AS DIALOG
    private void saveAsDialog()
    {
        JDialog myFindDialog = new JDialog(myFrame, "Save As");

    }
    //INNER CLASS FOR FIND DIALOG
    //don't know if I need to pass any parameter in
    //public void showFindDialog()
    //{
    //    JDialog myFindDialog = new JDialog(myFrame,"Find", false);
    //    myFindDialog.setSize(48, 64);
    //    myFindDialog.setLayout(new FlowLayout());

    //    JLable findWhat = new JLabel("Find what:  ");
    //    JTextField keyWordField = new JTextField(30);
    //    findWhat.setLabelFor(keyWordField);

    //    myFindDialog.add(findWhat);
    //    myFindDialog.add(keyWordField);

    //    myFindDialog.setVisible(true);
    //}
}
