import java.awt.Image;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Missile {

    private int x, y;
    private Image image;
    boolean visible;
    private int width, height;

    private final int BOARD_WIDTH = 500;
    private final int BOARD_HEIGHT = 500;
    private final int MISSILE_SPEED = 2;

    public Missile(int x, int y) {

        ImageIcon ii =
            new ImageIcon(this.getClass().getResource("pictures/missile.gif"));
        image = ii.getImage();
        visible = true;
        width = image.getWidth(null);
        height = image.getHeight(null);
        this.x = x;
        this.y = y;
    }


    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void move(int dir) {
        switch (dir) {
        case 1:
        //direction is up
        y -= MISSILE_SPEED;
        if (y < 0)
            visible = false;
            break;
         case 2:
        //direction is down
        y += MISSILE_SPEED;
        if (y > BOARD_HEIGHT)
            visible = false;
            break;
         case 3:
        //direction is left
        x -= MISSILE_SPEED;
        if (x < 0)
            visible = false;
            break;
         case 4:
        //direction is up
        x += MISSILE_SPEED;
        if (x > BOARD_HEIGHT)
            visible = false;
            break;
        }
    }
}
