package pingpong;

//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import akka.actor.ActorRef;

public class GuiApp1 {
	
	private ActorRef actor;
  
  //Note: Typically the main method will be in a
  //separate class. As this is a simple one class
  //example it's all in the one class. blablabla
  public static void main(String[] args) {
      
      new GuiApp1(null);
  }

  public GuiApp1(ActorRef a)
  {
	  this.actor=a;
      JFrame guiFrame = new JFrame();
      
      //make sure the program exits when the frame closes
      guiFrame.addWindowListener(new WindowAdapter()
      {
          @Override
          public void windowClosing(WindowEvent e)
          {
        	  actor.tell(new Stop(), actor);
          }
      });
    
      guiFrame.setTitle("Example GUI");
      guiFrame.setSize(200, 100);
      guiFrame.setLocationRelativeTo(null);
      
      final JPanel listPanel = new JPanel();
      listPanel.setVisible(false);
      
      JButton vegFruitBut = new JButton( "Send ping");
      
      vegFruitBut.addActionListener(new ActionListener()
      {
          //@Override
          public void actionPerformed(ActionEvent event)
          {
        	  actor.tell(new Start(), actor);

          }
      });
      

      guiFrame.add(vegFruitBut,BorderLayout.SOUTH);
      
      //make sure the JFrame is visible
      guiFrame.setVisible(true);
      //test
  }
  
}