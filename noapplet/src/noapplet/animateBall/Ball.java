package noapplet.animateBall;
import java.awt.Graphics;

// Interface for a Ball
public interface Ball {
    void move();
    void draw(Graphics g);
    boolean isColliding(BasicBall other);
}

