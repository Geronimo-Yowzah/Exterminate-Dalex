import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        //playButton
        if(mx >= Game.width/2 + 200 && mx<= Game.width/2 + 400){
            if(my >= 400 && my <= 500){
                Game.state = Game.state.game;
            }
        }

        //ExitButton
        if(mx >= Game.width/2 + 200 && mx<= Game.width/2 + 600){
            if(my >= 600 && my <= 700){
                System.exit(1);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
