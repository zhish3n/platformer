import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;

/**
 * Created by zhi on 7/16/17.
 */
class Canvas extends JComponent {

    // initialize user information
    public static int userXC = 300;
    public static int userYC = 200;
    public static int userWidth = 50;
    public static int userHeight = 50;

    // initialize ground information
    public static int groundXA = 0;
    public static int groundXB = 800;
    public static int groundYA = 400;
    public static int groundYB = 400;

    // initialize gravity information
    public static int accelerationDueToGravity = 5;

    // initialize delta timing information
    public static double beatPeriod = 9;
    public static double lastTime = 0;

    public Canvas() {
        Thread animationThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    // delta timing is more accurate than Thread.sleep
                    double now = System.currentTimeMillis();
                    if (now - lastTime > beatPeriod) {
                        lastTime = now;
                        repaint();
                    }

                    /*
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        // null
                    }
                    */

                }
            }
        });
        animationThread.start();
    }

    public void paintComponent(Graphics g) {
        Graphics2D brush = (Graphics2D) g;

        // set brush color
        brush.setColor(Color.BLACK);

        // draw the ground
        brush.drawLine(groundXA, groundYA, groundXB, groundYB);

        // list of validation methods
        noContact();
        //gravity();

        // draw the user
        brush.setColor(Color.BLACK);
        brush.drawRect(userXC, userYC, userWidth, userHeight);

        // movement
        for (int i = 0 ; i < Mover.keyList.size(); i++) {
            switch (Mover.keyList.get(i)) {
                case KeyEvent.VK_W:
                    userYC -= 2;
                    break;
                case KeyEvent.VK_A:
                    userXC -= 2;
                    break;
                case KeyEvent.VK_S:
                    userYC += 2;
                    break;
                case KeyEvent.VK_D:
                    userXC += 2;
                    break;
                case KeyEvent.VK_SPACE:
                    jump();
                    break;
            }
        }
    }

    public void gravity() {
        userYC += accelerationDueToGravity;
    }

    public void jump() {

    }

    // don't allow the user to cross the ground line
    public void noContact() {
        if ((userYC > (groundYA - 50)) || (userYC > (groundYB - 50))) {
            userYC = (groundYA - 50);
        }
    }

}
