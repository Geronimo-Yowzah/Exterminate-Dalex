import java.awt.image.BufferedImage;

public class Textures {
    private SpriteSheet ss;
    public BufferedImage player,missile,enemy;

    public Textures(Game game){
        ss = new SpriteSheet(game.getSpritesheet());
        getTextures();
    }
    private void getTextures(){
        player = ss.garbImage(1,1,50,50);
        missile = ss.garbImage(2,1,50,50);
        enemy = ss.garbImage(3,1,50,50);
    }
}
