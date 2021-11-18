import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu{
    private BufferedImage background = null;

    public Rectangle playButton = new Rectangle(Game.width/2 + 200,400,200,100);
    public Rectangle exitButton = new Rectangle(Game.width/2 + 200,600,200,100);

    public void render(Graphics g) throws IOException {
        Graphics2D g2d = (Graphics2D)g;

        BufferedImageLoader loader =new BufferedImageLoader();

        background = loader.loadImahe("/pic/pro1.jpg");

        g.setColor(Color.WHITE);
        g.drawImage(background,0,0,null );

        Font fnt0 = new Font("TimesRoman",Font.BOLD,50);
        g.setFont(fnt0);
        g.drawString("Start",playButton.x+48, playButton.y+60);
        g2d.draw(playButton);
        g.drawString("Exit",exitButton.x+48, exitButton.y+60);
        g2d.draw(exitButton);
    }

}
