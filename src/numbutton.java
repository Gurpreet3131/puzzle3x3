/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
/**
 *
 * @author Dragneel
 */
public class numbutton extends JButton 
{
    int row,col,value;
    public numbutton(int r,int c,int v)
    {
        row=r; col=c; value=v;
    }
    public void setrow(int r)
    {
        row=r;
    }
    public void setcol(int c)
    {
        col=c;
    }
    public void setvalue(int val)
    {
        value=val;
    }
    public int getrow()
    {
        return row;
    }
    public int getcol()
    {
        return col;
    }
    public int getvalue()
    {
        return value;
    }
}
