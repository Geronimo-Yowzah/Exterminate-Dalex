import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage image;

    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    public BufferedImage garbImage(int col,int row ,int width,int height){
        BufferedImage img = image.getSubimage((col*50)-50,(row*50)-50,width,height);
        return img;
    }


}
