/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Nexus
 */
public class Board extends JPanel implements ActionListener{
    
    private Image apple;
    private Image dot;
    private Image head;
    
    public int dots;
    
    private final int RANDOM_POS = 49;
    private final int DOTSIZE = 10;
    private final int MAXDOTS = 2500;
    private final int x[] = new int[MAXDOTS];
    private final int y[] = new int[MAXDOTS];
    
    private int applex;
    private int appley;
    
    private Timer timer;
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    
    Board(){
        
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(500,500));
        
        setFocusable(true);
        loadImages();
        initGame();
        
    }
    
    public void loadImages(){
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/apple.png")) ;
        apple = i1.getImage();
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/dot.png")) ;
        dot = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/head.png")) ;
        head = i3.getImage();
    }
    
    public void initGame(){
        
        dots = 3;
        for(int i = 0; i < dots; i++) {
            x[i] = 80 - i * DOTSIZE;
            y[i] = 80;
        }
        
        locateApple();
        
        timer = new Timer( 140, this);
        timer.start();
    }
    
    public void locateApple(){
        
        int rnd = (int)(Math.random() * RANDOM_POS);
        applex = (rnd * DOTSIZE);
        appley = (rnd * DOTSIZE);
    }
    
    public void checkApple(){
        if((x[0] == applex) && (y[0] == appley)){ 
            dots++;
            locateApple();
        }
    }
    
    public void checkCollision(){
        
        for(int i = dots; i >= 5; i--){
            
            if(x[0] == x[i] && y[0] == y[i]){
                
                inGame = false;
            }
        }
        if(y[0] >= 500 || x[0] >= 500 || x[0] < 0 || y[0] < 0){
            
            inGame = false;
        }
        if(!inGame){
            timer.stop();
        }
    }
    public void move(){
        
        
        for(int i = dots; i >= 1; i--){
            
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if(leftDirection){
            x[0] -= DOTSIZE;
        }
        
        if(rightDirection){
            x[0] += DOTSIZE;
        }
        
        if(downDirection){
            y[0] += DOTSIZE;
        }
        
        if(upDirection){
            y[0] -= DOTSIZE;
        }
    }
    
    public void gameOver(Graphics g){
        
        String message = "GAME OVER";
        String score = "YOUR SCORE = "+ (dots - 3);
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrics  = getFontMetrics(font);
        
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(message, (500 - metrics.stringWidth(message))/2, 240);
        g.drawString(score, (500 - metrics.stringWidth(message))/2 - 10, 260);
        
    }
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple, applex, appley, this);
            
            for(int i = 0; i < dots; i++)
            {
                if(i == 0) g.drawImage(head, x[0], y[0], this);
                else g.drawImage(dot, x[i], y[i], this);
            }
            
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        draw(g);
    }
    public void actionPerformed(ActionEvent ae){
        
        if(inGame){
            checkApple();
            checkCollision();
            move();
        }
        
        repaint();
    }
    
    private class TAdapter extends KeyAdapter{
        
        public void keyPressed(KeyEvent e){
           int key = e.getKeyCode();
           
           if(key == KeyEvent.VK_LEFT && (!rightDirection))
           {
               leftDirection = true;
               upDirection = false;
               downDirection = false;
               rightDirection = false;
           }
           
           if(key == KeyEvent.VK_RIGHT && (!leftDirection))
           {
               rightDirection = true;
               leftDirection = false;
               upDirection = false;
               downDirection = false;
           }
           if(key == KeyEvent.VK_UP && (!downDirection))
           {
               upDirection = true;
               rightDirection = false;
               downDirection = false;
               leftDirection = false;
           }
           if(key == KeyEvent.VK_DOWN && (!upDirection))
           {
               downDirection = true;
               upDirection = false;
               leftDirection = false;
               rightDirection = false;
           }
        }
    }
}
