import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.applet.*;
import java.net.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

    private Timer timer, restartTimer, alienTimer;
    private Craft craft;
    private ArrayList aliens;
    private Alien alien;
    private boolean ingame, splash;
    private int B_WIDTH;
    private int B_HEIGHT;
    Toolkit kit = Toolkit.getDefaultToolkit();;
    Image bg, bg2, bg3,logo, btnGame, btnHelp, btnUser;
    AudioClip myClip, myClip2, myClip3, audioClip3;
	URL codebase, codebase2, codebase3;
	int score, level, rectLength, life;
	private String player;

    private int[][] pos = { 
        {2380, 29}, {2500, 59}, {1380, 89},
        {780, 109}, {580, 139}, {680, 239}, 
        {790, 259}, {760, 50}, {790, 150},
        {980, 209}, {560, 45}, {510, 70},
        {930, 159}, {590, 80}, {530, 60},
        {940, 59}, {990, 30}, {920, 200},
        {900, 259}, {660, 50}, {540, 90},
        {810, 220}, {860, 20}, {740, 180},
        {820, 128}, {490, 170}, {700, 30}
        
     };

    public Board(String playerIn) {
		player = playerIn;
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = false;
        splash = true;
		bg = kit.getImage("pictures/spacebg.gif");
		bg2 = kit.getImage("pictures/spacebg2.gif");
		bg3 = kit.getImage("pictures/spacebg3.gif");
		btnUser = kit.getImage("pictures/user.png");
		logo = kit.getImage("pictures/intro.png");
		btnGame = kit.getImage("pictures/game.png");
		btnHelp = kit.getImage("pictures/help.png");
		
		
        setSize(500, 500);
		score = 0;
		level = 1;
		rectLength = 200;
		
        craft = new Craft();
        
        try {
            codebase = new URL("file:" + System.getProperty("user.dir") + "/media/spacemusic.au");
            codebase2 = new URL("file:" + System.getProperty("user.dir") + "/media/die.wav");
            codebase3 = new URL("file:" + System.getProperty("user.dir") + "/media/eerie.wav");
            
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
		AudioClip audioClip = Applet.newAudioClip(codebase);
		AudioClip audioClip2 = Applet.newAudioClip(codebase2);
		AudioClip audioClip3 = Applet.newAudioClip(codebase3);
		
		myClip = audioClip;
		myClip2 = audioClip2;
		myClip3 = audioClip3;
		
		myClip.loop();

        initAliens();

        timer = new Timer(5, this);
        alienTimer = new Timer((10 - level) * 1000,new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				initAliens();
				
				
			}    
			});
		restartTimer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRestart();
		}	});
        timer.start();
        alienTimer.start();
        restartTimer.start();
    }

    public void addNotify() {
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();   
    }

    public void initAliens() {
        aliens = new ArrayList();

        for (int i=0; i< pos.length; i++ ) {
            aliens.add(new Alien(pos[i][0], pos[i][1]));
            }
        }
		

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if (ingame) {

            
            switch (level) {
				case 1:
					g2d.drawImage(bg, 0,0,this);
					break;
				case 2:
					g2d.drawImage(bg3, 0,0,this);
					break;
				case 3:
					g2d.drawImage(bg2, 0,0,this);
					break;
				default:
					g2d.drawImage(bg, 0,0,this);
					
			}

            if (craft.isVisible())
                g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(),
                              this);

            ArrayList ms = craft.getMissiles();

            for (int i = 0; i < ms.size(); i++) {
                Missile m = (Missile)ms.get(i);
                g2d.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }

            for (int i = 0; i < aliens.size(); i++) {
                Alien a = (Alien)aliens.get(i);
                if (a.isVisible())
                    g2d.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }

            g2d.setColor(Color.WHITE);
            g2d.drawString("present score: " + score, 5, 15);
            
            g2d.setColor(Color.YELLOW);
            g2d.drawString("current player: " + player, 150, 15);

			g2d.setColor(Color.WHITE);
            g2d.drawString("current level: " + level, 350, 15);
			
			g2d.setColor(Color.WHITE);
			life = (int) (rectLength * 100)/200;
            g2d.drawString(String.valueOf(life) + " %", 400, 410);
            
			g2d.setColor(Color.RED);
            g2d.fillRect(200,400, rectLength, 10);
            

        } else if(splash) {
        
			g2d.drawImage(logo, (B_WIDTH - logo.getWidth(null)) / 2, 100,this);
				String msg = "Press ENTER to start game";
				Font small = new Font("Helvetica", Font.BOLD, 18);
				FontMetrics metr = this.getFontMetrics(small);

				g.setColor(Color.GRAY);
				g.setFont(small);
				g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
                         350);
        }
        
        else {
			myClip.stop();
			myClip2.play();
            String msg = "Game Over";
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = this.getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
                         B_HEIGHT / 2);
            timer.stop();
            alienTimer.stop();
						
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void actionPerformed(ActionEvent e) {

        if (aliens.size()==0) {
            ingame = false;
        }

        ArrayList ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) {
            Missile m = (Missile) ms.get(i);
            if (m.isVisible()) 
				m.move(4);
             
            else  ms.remove(i);
        }

        for (int i = 0; i < aliens.size(); i++) {
            Alien a = (Alien) aliens.get(i);
            if (a.isVisible()) 
                a.move(level);
            else aliens.remove(i);
        }
        
        checkLifeLineExhausted();
		checkLevel();
        craft.move();
        checkCollisions();
        repaint();  
    }
    
    public void checkRestart(){
		if (score == -1) {
			score = 0;
			craft.setVisible(true);
			splash = false;
            ingame = true;
			timer.start();
        alienTimer.start();
        myClip.loop();
		initAliens();	
		}
	}
	
	public void checkLevel(){
		if (score < 2000) {
			level = 1;
		} else if (score > 2000 && score < 4500) {
			level = 2;
		} else {
			level = 3;
		}
	}
	
	public void checkLifeLineExhausted(){
				if (rectLength == 0) {
				craft.setVisible(false);
                ingame = false;
                }
           }

    public void checkCollisions() {

        Rectangle r3 = craft.getBounds();

        for (int j = 0; j<aliens.size(); j++) {
            Alien a = (Alien) aliens.get(j);
            Rectangle r2 = a.getBounds();

            if (r3.intersects(r2)) {
                rectLength -= 20;
                myClip2.play();
                a.setVisible(false);
            }
        }
 
        ArrayList ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) {
            Missile m = (Missile) ms.get(i);

            Rectangle r1 = m.getBounds();

            for (int j = 0; j<aliens.size(); j++) {
                Alien a = (Alien) aliens.get(j);
                Rectangle r2 = a.getBounds();

                if (r1.intersects(r2)) {
					score += 10;
                    m.setVisible(false);
                    a.setVisible(false);
                }
            }
        }
    }


    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
            
            int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER) {
            score = -1;
            level = 1;
            
			}
        
        }
    }
}
