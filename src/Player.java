import java.awt.*;

public class Player extends GameObject implements EntityA{

    private double velX = 0;
    private double velY = 0;

    private Textures tex;

    Game game;
    Controller c;

    public Player(double x,double y,Textures tex,Game game,Controller c){
        super(x,y);
        this.tex = tex;
        this.game = game;
        this.c = c;

    }

    public void tick(){
        x+=velX;
        y+=velY;

        if(x<=0){
            x = 0;
        }
        if(x>=1050){
            x=1050;
        }
        if(y <= 0){
            y=0;
        }
        if(y >= 1000-50){
            y=1000-50;
        }

        for(int i = 0 ; i < game.eb.size(); i++){
            EntityB tempEnt = game.eb.get(i);

            if(Physics.Collision(this,tempEnt)){
                c.removeEntity(tempEnt);
                Game.healbar -= 15;
                game.setEnemy_killed(game.getEnemy_killed() + 1);
            }
        }
    }


    public void render(Graphics g){
        g.drawImage(tex.player, (int) x,(int) y,null);
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

    public void setVelX(double velX) {
        this.velX = velX;
    }
    public void setVelY(double velY) {
        this.velY = velY;
    }
}
