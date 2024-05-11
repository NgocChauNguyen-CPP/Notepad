
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Dialogs
{
    public static String fileOpenDialog(JFrame parent)
    {
        JFileChooser myOpenFileChooser = new JFileChooser(".");
        String myFileName = "";
        if(myOpenFileChooser.showOpenDialog(parent) ==  JFileChooser.APPROVE_OPTION)
        {
            myFileName = myOpenFileChooser.getSelectedFile().getName();
        }
        else
        {
            myFileName = "File not exist.";
        }

        /*if (fileName.equals("File not exist.")) 
        {
            fileName = null;
            return fileName;
        */
        System.out.println(myFileName);
        return myFileName;
    }
    /*private class TextFileFilter extends FileFilter
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
    }*/
}
    

