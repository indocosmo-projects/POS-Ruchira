/**
 * 
 */
package com.indocosmo.pos.test;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class PrimaryMonitor
{
  public static void main(String[] args)
  {
    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();

    for(GraphicsDevice screenDevice : screenDevices)
    {
      System.out.println(screenDevice + ", ID=" + screenDevice.getIDstring());

      GraphicsConfiguration screenConfiguration = screenDevice.getDefaultConfiguration();
      Rectangle screenBounds = screenConfiguration.getBounds();
      System.out.println(screenBounds);

      JFrame frame = new JFrame(screenDevice.getIDstring());
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new GridLayout(0, 1));
      frame.add(new JLabel(screenDevice.getIDstring()));
      frame.add(new JLabel("" + screenBounds));
      if(screenBounds.x == 0)
      {
        frame.add(new JLabel("Primary?"));
      }
      frame.setBounds(screenBounds.x, screenBounds.y, 600, 200);
      frame.setVisible(true);
    }
  }
}