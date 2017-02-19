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
public class puzz {
    int num[][]=new int[3][3];
    boolean win;
    puzz up,down,right,left,pre;
    public puzz()
    {
        up=null; down=null; right=null; left=null; pre=null;
        win=false;
    }
}
