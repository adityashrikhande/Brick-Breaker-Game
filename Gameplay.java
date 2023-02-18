
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements ActionListener, KeyListener {
    private boolean play = false;
    private Timer timer;
    private int score = 0;
    private int totalBricks = 21;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -2;
    private int ballYdir = -4;
    private MapGenerator map;

    public Gameplay(){
        map = new MapGenerator(3, 7);
        addKeyListener((KeyListener) this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g){
        // background color
        g.setColor(Color.BLACK);
        g.fillRect(1, 1, 692, 592);

        // top yellow borders
        g.setColor(Color.YELLOW);
        g.fillRect(0, 0, 692, 3);

        // side right border
        g.fillRect(683, 0, 3, 592);

        // side left border
        g.fillRect(0, 0, 3, 592);

        // peddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550, 100, 8);

        // bricks
        map.draw((Graphics2D) g);
        // ball
        g.setColor(Color.RED);
        g.fillOval(ballposX, ballposY, 20, 20);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score : " + score, 550, 30);

        // Game over
        if(ballposY >= 570){
            ballXdir = -2;
            ballYdir = -4;
            play = false;
            g.setColor(Color.RED);
            g.setFont(new Font("verdana", Font.BOLD, 20));
            g.drawString("Game Over : " + score, 250, 250);
            g.drawString("Press Enter To Restart ", 210, 280);

        }
        if(totalBricks <= 0){
            ballXdir = -2;
            ballYdir = -4;
            play = false;
            g.setColor(Color.GREEN);
            g.setFont(new Font("verdana", Font.BOLD, 20));
            g.drawString("You Won ! " + score, 250, 250);
            g.drawString("Press Enter To Restart ", 210, 280);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(play){
            if(ballposX < 3){
                ballXdir = -ballXdir;
            }
            if(ballposX > 663){
                ballXdir = -ballXdir;
            }
            if(ballposY < 3){
                ballYdir = -ballYdir;
            }
            Rectangle ballRect = new Rectangle(ballposX, ballposY, 20 ,20);
            Rectangle peddleRect = new Rectangle(playerX, 550, 100, 8);

            if(ballRect.intersects(peddleRect)){
                ballYdir = -ballYdir;
            }

            A:for(int i=0; i<map.map.length; i++){
                for(int j=0; j<map.map[i].length; j++){
                    if(map.map[i][j] == 1){
                        int width = map.brickWidth;
                        int height = map.brickHeight;
                        int brickXpos = 80 + j*width;
                        int brickYpos = 50 + i*height;

                        Rectangle brickRect = new Rectangle(brickXpos, brickYpos, width, height);

                        if(ballRect.intersects(brickRect)){
                            score += 5;
                            map.setBrick(0, i, j);
                            totalBricks--;

                            if(ballposX+19 <= brickXpos || ballposX + 1 >= brickXpos+width){
                                ballXdir = -ballXdir;
                            }else{
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;
        }
        repaint();
    }
    private void moveLeft(){
        play = true;
        playerX -= 40;
    }
    private void moveRight(){
        play = true;
        playerX += 40;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            if(playerX <= 0)return;
            moveLeft();
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(playerX >= 580)return;
            moveRight();
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(!play){
                score = 0;
                totalBricks = 21;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 320;
                map = new MapGenerator(3, 7);
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
