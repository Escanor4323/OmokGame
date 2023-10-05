package noapplet;
import javax.swing.*;

public class AnimationNoApplet extends NoApplet{
    protected Timer timer;
    protected int delay;

    public void init(){
        timer = new Timer(delay, e -> repaint());
    }

    public void start(){timer.start();}

    public void stop(){timer.stop();}

}
