/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 *
 * @author Sid
 */
public class DesignDefault {
    
    /********************************
     * 
     * COLORS
     * 
     ********************************/
    private Color bgColor = new Color(160,160,160);
    public Color getBGColor()
    { return bgColor; }
    public void setBGColor(Color c)
    { bgColor = c; }
    
    private  Color panelBGColor = new Color(128,128,128);
    public Color getPanelBGColor()
    { return panelBGColor; }
    public void setPanelBGColor(Color c)
    { panelBGColor = c; }
    
    private Color dialogBGColor = new Color(112,112,112);
    public Color getDialogBGColor()
    { return dialogBGColor; }
    public void setDialogBGColor(Color c)
    { dialogBGColor = c; }
    
    private  Color listSelectionColor = new Color(0,240,240);
    public Color getListSelectionColor()
    { return listSelectionColor; }
    public void setListSelectionColor(Color c)
    { listSelectionColor = c; }
    
    private  Color textColor = new Color(255,255,255);
    public Color getTextColor()
    { return textColor; }
    public void setTextColor(Color c)
    { textColor = c; }
    
    
    /**************************************
     * 
     * DIMENSIONS
     * 
     **************************************/
    private Dimension frameDimension = new Dimension(960,680);
    public Dimension getFrameDimension()
    { return frameDimension; }
    public void setFrameDimension(Dimension d)
    { frameDimension = d; }

    private  Dimension panelDimension = new Dimension(760,620);
    public Dimension getPanelDimension()
    { return panelDimension; }
    public void setPanelDimension(Dimension d)
    { panelDimension = d; }
    
    private Dimension sidePanelDimension = new Dimension(160,80);
    public Dimension getSidePanelDimension()
    { return sidePanelDimension; }
    public void setSidePanelDimension(Dimension d)
    { sidePanelDimension = d; }
    
    private  Dimension smallButtonDimension = new Dimension(25,25);
    public Dimension getSmallButtonDimension()
    { return smallButtonDimension; }
    public void setSmallButtonDimension(Dimension d)
    { smallButtonDimension = d; }
    
    private  Dimension bigButtonDimension = new Dimension(100,25);
    public Dimension getBigButtonDimension()
    { return bigButtonDimension; }
    public void setBigButtonDimension(Dimension d)
    { bigButtonDimension = d; }
    
    
    /********************************************
     * 
     * FONTS
     * 
     ********************************************/
    private Font smallText = new Font("Candara", 0, 10);
    public Font getSmallText()
    { return smallText; }
    public void setSmallText(Font f)
    { smallText = f; }
    
    private Font standardText = new Font("Candara", 0, 12);
    public Font getStandardText()
    { return standardText; }
    public void setStandardText(Font f)
    { standardText = f; }
    
    private Font headerText = new Font("Candara", Font.BOLD, 18);
    public Font getHeaderText()
    { return headerText; }
    public void setHeaderText(Font f)
    { headerText = f; }
    
    public DesignDefault()
    {
        
    }
    
    public static class Main
    {
        public static DesignDefault instance = new DesignDefault();
    }
    
    public static  DesignDefault getInstance()
    {
        return Main.instance;
    }
    
    public void resetDefault()
    {
         bgColor = new Color(200,200,200);
         panelBGColor = new Color(120,120,120);
         listSelectionColor = new Color(0,240,240);
         textColor = new Color(255,255,255);
         frameDimension = new Dimension(960,680);
         panelDimension = new Dimension(760,620);
         sidePanelDimension = new Dimension(160,80);
         smallButtonDimension = new Dimension(25,25);
         bigButtonDimension = new Dimension(100,25);
         smallText = new Font("Candara", 0, 10);
         standardText = new Font("Candara", 0, 12);
         headerText = new Font("Candara", Font.BOLD, 18);
    }
}
