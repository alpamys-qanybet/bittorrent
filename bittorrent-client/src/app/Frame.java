package app;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Frame extends JFrame
{
	private Panel panel;
	
	public Frame()
	{
		super("Bit Torrent");
        //====================UIManager=====================
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
        UIManager.LookAndFeelInfo looks[]= UIManager.getInstalledLookAndFeels();
        try { UIManager.setLookAndFeel( looks[3].getClassName() ); }
        catch (Exception e) { e.printStackTrace(); }
        finally { SwingUtilities.updateComponentTreeUI( this ); }

        setBounds(100, 100, 220, 270);
        setResizable(false);
        
        add(panel = new Panel());
	}
}

class Panel extends JPanel implements ActionListener
{
	private JButton downloadButton, sendButton;
	private final JFileChooser fileChooser;
	private JTextField textField;
	
	public Panel()
	{
		textField = new JTextField(20);
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(this);
		
		add(textField);
		add(downloadButton);
		
		fileChooser = new JFileChooser();
		sendButton = new JButton("Upload");
		sendButton.addActionListener(this);
		
		add(sendButton);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == downloadButton)
		{
			String fileName = textField.getText();
			
			Main.processor.getClient().sendRequestForFile(fileName, 0);
		}
		
		else if (e.getSource() == sendButton)
		{
			int returnVal = fileChooser.showOpenDialog(this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = fileChooser.getSelectedFile();
				
				int fileSize = (int) file.length();
                int nChunks = 0, read = 0, readLength = 2097152;
                System.out.println("selected file size " + fileSize);
                
                byte aChunk[];
                try
                {
                    FileInputStream fis = new FileInputStream(file);
                    FileOutputStream fos;
                    
                    File tempDir = new File("/home/alpamys/dev/soft/bittorrent/files");
                    if ( !tempDir.exists() )
                    	tempDir.mkdirs();
                
                    File fileDir = new File(tempDir.getPath() + "/" + file.getName());
                    fileDir.mkdir();
                    
                    while (fileSize > 0)
                    {
                        if (fileSize <= 2097152)
                            readLength = fileSize;
                        
                        aChunk = new byte[readLength];
                        read = fis.read(aChunk, 0, readLength);
                        
                        fileSize -= read;
                        nChunks++;
                        
                        fos = new FileOutputStream(new File( fileDir.getPath() + "/" + (nChunks - 1) ));
                        fos.write(aChunk);
                        fos.flush();
                        fos.close();
                        fos = null;
                    }
                    
                    Main.processor.getClient().sendFileInfo(file.getName(), nChunks-1);
                }
                catch (Exception ex)
                { ex.printStackTrace(); }
			}
		}
	}
}