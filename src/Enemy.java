import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject implements EntityB{
    Random r = new Random();

    private Game game;
    private Controller c;
    private Textures tex;
    private int speed = r.nextInt(4)+2;

    public Enemy(double x,double y,Textures tex,Controller c,Game game){
        super(x,y);
        this.tex = tex;
        this.c = c;
        this.game = game;
    }

    public void tick() {
        y += speed;

        if (y > (Game.height * Game.scale)) {
            y = -10;
            Game.healbar -= 10;
            x = r.nextInt(Game.width * Game.scale);
        }

        for (int i = 0; i < game.ea.size(); i++) {
            EntityA tempEnt = game.ea.get(i);

            if (Physics.Collision(this, tempEnt)) {
                c.removeEntity(tempEnt);
                c.removeEntity(this);
                game.setEnemy_killed(game.getEnemy_killed() + 1);
                game.score++;
            }
        }
    }

    public void render(Graphics g){
        g.drawImage(tex.enemy,(int)x,(int)y,null);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x,(int)y,50,50);
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }
}
