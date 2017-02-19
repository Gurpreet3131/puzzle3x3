import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
public class puzzle extends JFrame implements ActionListener,Runnable
{
   public static JPanel matrix,select,top;
   public static numbutton m[][]=new numbutton[3][3];
   public static JButton newpuzzle,submit,solve,autosolve;
   public static JLabel countlabel,clicks;
   public static int a[][]=new int[3][3];
   public static Random randomGenerator=new Random(); 
   public static int flag,random,row,column,counter,count,countset,cheat,inversion=0;
   //countset 1 tells that puzzle is solved and do not increase the moves count
   public static String temp;
   public static Border buttonborder = new LineBorder(Color.black, 1);
   public static Thread auto;
   boolean thstart=false;
   int brow=0,bcol=0,front=-1,rear=-1,posrow,poscol;
   puzz queue[]=new puzz[1000000];
   int step[]=new int[10000]; 
   //public static puzz rt=new puzz();
   public static puzz last=new puzz();
   int direction=0; //1up, 2down, 3left, right4
   public static puzzle obj;
   public boolean wincheck()
   {
       int count=0,flag2=0;
       for(int i=0;i<3;i++)
       {
           for(int j=0;j<3;j++)
           {
                if(i==2 && j==2) break;
                count++;
                if(String.valueOf(count).compareTo(m[i][j].getText())!=0)
                {
                    flag2=1;
                }
            }
        }
        if(flag2==0) return true;
        else return false;
   }
   public boolean wina(puzz node)
   {
       int count=0,flag2=0;
       for(int i=0;i<3;i++)
       {
           for(int j=0;j<3;j++)
           {
                if(i==2 && j==2) break;
                count++;
                if(count!=node.num[i][j]) flag2=1;
            }
        }
        if(flag2==0) return true;
        else return false;
   }
   void insert(puzz item)
   {
       if(front==-1) front=0;
       rear=rear+1;
       if(rear==1000000) rear=0;
       queue[rear]=item;
      
   }
   puzz del()
   {
       puzz item=new puzz();
       if(front==-1 || front==rear+1) return null;
       item=queue[front]; front=front+1;
       if(front==1000000) front=0;
       return item;
   }
   public void down(int row,int column,puzz node)
    {
         if(node.num[row+1][column]==-1)  //down
            {
                int temp= node.num[row][column];
                node.num[row][column]=-1;
                node.num[row+1][column]=temp;
            }
    }
   public void up(int row,int column,puzz node)
    {
         if(node.num[row-1][column]==-1)  //down
            {
                int temp= node.num[row][column];
                node.num[row][column]=-1;
                node.num[row-1][column]=temp;
            }
    }
   public void left(int row,int column,puzz node)
    {
         if(node.num[row][column-1]==-1)  //down
            {
                int temp= node.num[row][column];
                node.num[row][column]=-1;
                node.num[row][column-1]=temp;
            }
    }
   public void right(int row,int column,puzz node)
    {
         if(node.num[row][column+1]==-1)  //down
            {
                int temp= node.num[row][column];
                node.num[row][column]=-1;
                node.num[row][column+1]=temp;
            }
    }
   boolean queue_empty()
   {
       if(front==-1 && front==rear+1) return true;
       else return false;
   }
   public void solving(puzz root)
   {
       puzz temp=new puzz();
       boolean wins=false;
       temp=root;
       insert(temp);
       int brow=0,bcol=0;
       for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                root.num[i][j]=a[i][j];
                if(a[i][j]==-1) 
                {
                    brow=i; bcol=j;
                }
            }
        }
       int ct=0;
       while(!queue_empty())
       {
            temp=del();
            if(temp==null)
            {
                System.out.println("null found");
                break;
            }
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    //if(temp==null) continue;
                    if(temp.num[i][j]==-1) 
                    {
                        brow=i; bcol=j;
                    }
                }
            }
            //System.out.println(temp);
            temp.up=new puzz();
            temp.down=new puzz();
            temp.left=new puzz();
            temp.right=new puzz();
            temp.up.pre=temp;
            temp.down.pre=temp;
            temp.left.pre=temp;
            temp.right.pre=temp;
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    temp.up.num[i][j]=temp.num[i][j];
                    temp.down.num[i][j]=temp.num[i][j];
                    temp.left.num[i][j]=temp.num[i][j];
                    temp.right.num[i][j]=temp.num[i][j];
                   // System.out.print(temp.num[i][j]+" ");
                }
               // System.out.println();
            }
            if(temp.pre==null)
            {
               
                if(brow-1>=0 && temp.down!=null) 
                {
                down(brow-1,bcol,temp.down);
                insert(temp.down);
                wins=wina(temp.down);
                if(wins==true) {
                    //System.out.println("WIN");
                    direction=2; 
                    last=temp.down; break;
                }
                }
            
            
            if(brow+1<=2 && temp.up!=null) 
            {
                up(brow+1,bcol,temp.up);
                insert(temp.up);
                wins=wina(temp.up); 
                if(wins==true)
                {
                    //System.out.println("WIN");
                    last=temp.up;
                    direction=1; break;
                }
            }
            
            if(bcol+1<=2 && temp.left!=null)
            {
                left(brow,bcol+1,temp.left); insert(temp.left);
                wins=wina(temp.left);
                
                if(wins==true){
                    //System.out.println("WIN");
                    last=temp.left;
                    direction=3; break;
                }
            }
            
            if(bcol-1>=0 && temp.right!=null) 
            {
                right(brow,bcol-1,temp.right); insert(temp.right);
                wins=wina(temp.right);
                if(wins==true) 
                {
                    //System.out.println("WIN");
                    last=temp.right;
                    direction=4; break;
                }
            }
            }
            else
            {
                if(temp.pre.up!=temp)
            {
                if(brow-1>=0 && temp.down!=null) 
                {
                down(brow-1,bcol,temp.down);
                insert(temp.down);
                wins=wina(temp.down);
                if(wins==true) {
                    //System.out.println("WIN");
                    direction=2; 
                    last=temp.down; break;
                }
                }
            }
            if(temp.pre.down!=temp){
            if(brow+1<=2 && temp.up!=null) 
            {
                up(brow+1,bcol,temp.up);
                insert(temp.up);
                wins=wina(temp.up); 
                if(wins==true)
                {
                    //System.out.println("WIN");
                    last=temp.up;
                    direction=1; break;
                }
            }}
            if(temp.pre.right!=temp){
            if(bcol+1<=2 && temp.left!=null)
            {
                left(brow,bcol+1,temp.left); insert(temp.left);
                wins=wina(temp.left);
                
                if(wins==true){
                    //System.out.println("WIN");
                    last=temp.left;
                    direction=3; break;
                }
            }}
            if(temp.pre.left!=temp){
            if(bcol-1>=0 && temp.right!=null) 
            {
                right(brow,bcol-1,temp.right); insert(temp.right);
                wins=wina(temp.right);
                if(wins==true) 
                {
                    //System.out.println("WIN");
                    last=temp.right;
                    direction=4; break;
                }
            }}
            }
            
            ct++;
            if(ct%100000==0)
            {
                System.out.println(ct);
                System.out.println(front+" "+rear);
                if(ct==1000000) ct=0;
            }
       }
       System.out.println("SOLUTION FOUND");
    }
   public void run()
   {
        brow=0;bcol=0; 
        int index=0;
        front=-1;rear=-1;
        puzz rt=new puzz();
        solving(rt);
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(a[i][j]==-1) 
                {
                    brow=i; bcol=j;
                }
            }
        }
        puzz temp=new puzz();
        temp=last;   
        while(temp.pre!=null)
        {
            if(temp.pre.up.equals(temp))step[index++]=1;
            else if(temp.pre.down.equals(temp)) step[index++]=2;
            else if(temp.pre.left.equals(temp)) step[index++]=3;
            else if(temp.pre.right.equals(temp)) step[index++]=4;
            temp=temp.pre;
        }
        System.out.println("SOLVING");
        for(int st=index;st>=0;st--)
        {
            for(int i=0;i<3;i++)
            {
                for(int j=0;j<3;j++)
                {
                    if(a[i][j]==-1) 
                    {
                        brow=i; bcol=j;
                    }
                }
            }
            if(step[st]==1) upswap(brow+1,bcol);
            else if(step[st]==2) downswap(brow-1,bcol);
            else if(step[st]==3) leftswap(brow,bcol+1);
            else if(step[st]==4) rightswap(brow,bcol-1);
            try{auto.sleep(500); } catch(Exception e) {}
        }       
        System.out.println("SOLVED");
        System.gc();
        try{auto.stop();} catch(Exception e){}
   }
   public puzzle()
    {
        count=0;
        matrix=new JPanel();
        matrix.setLayout(new GridLayout(3,3));
        select =new JPanel();
        select.setLayout(new FlowLayout());
        top=new JPanel();
        top.setLayout(new FlowLayout());
        clicks=new JLabel("Clicks: ");
        countlabel=new JLabel(String.valueOf(count));
        top.add(clicks);
        top.add(countlabel);
        countlabel.setForeground(Color.white);
        clicks.setForeground(Color.white);
        clicks.setFont(new Font("Arial", Font.PLAIN, 20));
        countlabel.setFont(new Font("Arial", Font.PLAIN, 20));
        
        top.setBackground(Color.black);
        add(top,BorderLayout.NORTH);
        
        newpuzzle=new JButton("New");
        submit=new JButton("Submit");
        solve=new JButton("Solve");
        autosolve=new JButton("AutoSolve");
        select.add(newpuzzle);
        select.add(submit);
        select.add(solve);
        select.add(autosolve);
        newpuzzle.addActionListener(this);
        submit.addActionListener(this);
        solve.addActionListener(this);
        autosolve.addActionListener(this);
        submit.setBackground(Color.white);
        newpuzzle.setBackground(Color.white);
        solve.setBackground(Color.white);
        autosolve.setBackground(Color.white);
        select.setBackground(Color.black);
        
        add(select,BorderLayout.SOUTH);
        
        puzzleGenerate(); 
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                   if(i==2 && j==2)
                   {
                       m[i][j]=new numbutton(i,j,-1);
                   }
                   else 
                   {
                       m[i][j]=new numbutton(i,j,a[i][j]);
                       m[i][j].setText(String.valueOf(a[i][j]));
                   }
                   m[i][j].setFont(new Font("Arial", Font.PLAIN, 30));
                   m[i][j].setBackground(Color.lightGray);
                   m[i][j].setFocusable(false);
                   m[i][j].setBorder(buttonborder);
                   
                   m[i][j].addActionListener(this);
                   matrix.add(m[i][j]);
            }
        }
        column=2;row=2;
        add(matrix);
        
        setSize(400,400);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void autosolveaction()
    {
        auto=new Thread(obj);
        //if(thstart==false) 
        auto.start();
  
    }
    public void submitaction()
    {
        
    }
    public void solveaction()
    {
         int c=0;
            
            for(int i=0;i<3;i++)
           {
               for(int j=0;j<3;j++)
               {
                   c++;
                   if(i==2 && j==2)
                   {
                       m[i][j].setText(String.valueOf(""));
                       a[i][j]=-1;
                       m[i][j].setvalue(-1);
                   }
                   else
                   {
                       m[i][j].setText(String.valueOf(c));
                       a[i][j]=c;
                       m[i][j].setvalue(a[i][j]);
                   }
               }
           }
    }
    public void newpuzzleaction()
    {
        dispose();
        obj=new puzzle();
        
        /*for(int i=0;i<1000000;i++) queue[i]=null;
        for(int i=0;i<10000;i++) step[i]=0;
        count=0;countset=0;cheat=0;
           clicks.setText("Clicks: ");
           countlabel.setText(String.valueOf(count));
           for(int i=0;i<3;i++)
           {
               for(int j=0;j<3;j++)
               {
                   a[i][j]=0;
               }
           }
           puzzleGenerate();
            
           for(int i=0;i<3;i++)
           {
               for(int j=0;j<3;j++)
               {
                   if(i==2 && j==2)
                   {
                       m[i][j].setText(String.valueOf(""));
                       m[i][j].setrow(i); m[i][j].setcol(j);
                       a[i][j]=-1; m[i][j].setvalue(a[i][j]);
                   }
                   else
                   {
                       m[i][j].setText(String.valueOf(a[i][j]));
                       m[i][j].setrow(i); m[i][j].setcol(j);
                       m[i][j].setvalue(a[i][j]);
                   }
               }
           }*/
    }
    public void actionPerformed(ActionEvent e)
    {
        if(newpuzzle==e.getSource()) newpuzzleaction();
        else if(solve==e.getSource()) solveaction();
        else if(submit==e.getSource()) submitaction();
        else if(autosolve==e.getSource()) autosolveaction();
        else 
        {
            numbutton but=(numbutton)e.getSource();
            if(but.getrow()<2) downswap(but.getrow(),but.getcol());
            if(but.getrow()>0) upswap(but.getrow(),but.getcol());
            if(but.getcol()>0) leftswap(but.getrow(),but.getcol());
            if(but.getcol()<2) rightswap(but.getrow(),but.getcol());
        }
        
    }
    public void countinc()
    {
        if(countset==0)
        {
            count++;
            countlabel.setText(String.valueOf(count));
        }
    }
    public void puzzleGenerate() //it will generate a random puzzle
    {
        inversion=0;
       
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
            {
                if(i==2 && j==2) 
                {
                    a[i][j]=-1;
                    break;
                }
                a[i][j]=0;
            }
        
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                flag=0;
                if(i==2 && j==2) break;
                random=randomGenerator.nextInt(9);
                if(random==0) 
                {
                    j--;
                    continue;
                }
                else
                {
                    for(int k=0;k<3;k++)
                    {
                        for(int l=0;l<3;l++)
                        {
                            if(random==a[k][l])
                            {
                                flag=1;
                            }
                        }
                    }
                }
                if(flag==1) j--;
                else a[i][j]=random;
            }
        } 
        int forinversion[]=new int[9],index=0;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                if(i==2 && j==2 ) break;
                forinversion[index++]=a[i][j];
            }
        }
        for(int i=0;i<7;i++)
        {
            for(int j=i+1;j<8;j++)
            {
                if(forinversion[i]>forinversion[j]) inversion++;
            }
        }
        if(inversion%2!=0) puzzleGenerate();
    }
    public void downswap(int row,int column)
    {
         if(m[row+1][column].getText()=="")  //down
            {
                temp= m[row][column].getText();
                m[row][column].setText("");
                m[row+1][column].setText(temp);
                a[row][column]=-1;
                a[row+1][column]=Integer.parseInt(temp);
                m[row][column].setvalue(-1); m[row+1][column].setvalue(a[row+1][column]);
                countinc();
            }
    }
    public void upswap(int row,int column)
    {
        if(m[row-1][column].getText()=="")  //up
            {
                temp= m[row][column].getText();
                m[row][column].setText("");
                m[row-1][column].setText(temp);
                a[row][column]=-1;
                a[row-1][column]=Integer.parseInt(temp);
                m[row][column].setvalue(-1); m[row-1][column].setvalue(a[row-1][column]);
                countinc();
            }    
    }
    public void rightswap(int row,int column)
    {
        if(m[row][column+1].getText()=="")  //right
            {
                temp= m[row][column].getText();
                m[row][column].setText("");
                m[row][column+1].setText(temp);
                a[row][column]=-1;
                a[row][column+1]=Integer.parseInt(temp);
                m[row][column].setvalue(-1); m[row][column+1].setvalue(a[row][column+1]);
                countinc();
            }
    }
    public void leftswap(int row,int column)
    {
        if(m[row][column-1].getText()=="")  //left
            {
                temp= m[row][column].getText();
                m[row][column].setText("");
                m[row][column-1].setText(temp);
                a[row][column]=-1;
                a[row][column-1]=Integer.parseInt(temp);
                m[row][column].setvalue(-1); m[row][column-1].setvalue(a[row][column-1]);
                countinc();
            }
    }
    public static void main(String...x)
    {
       obj =new puzzle();
       //auto=new Thread(obj);
    }
}
