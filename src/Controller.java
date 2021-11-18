import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Controller {

    private LinkedList<EntityA> e = new LinkedList<EntityA>();
    private LinkedList<EntityB> e1 = new LinkedList<EntityB>();
    EntityA enta;
    EntityB entb;
    private Textures tex;
    Random r = new Random();
    private Game game;

    public Controller(Textures tex,Game game){
        this.tex = tex;
        this.game = game;
    }

    public void creatEnemy(int enemy_count){
        for(int i = 0 ; i<enemy_count; i++){
            addEntity(new Enemy(r.nextInt(640),-10,tex,this,game));
        }
    }

    public void tick(){
        //A class
        for (int i = 0; i<e.size(); i++){
            enta = e.get(i);

            enta.tick();
        }
        //B class
        for (int i = 0; i<e1.size(); i++){
            entb = e1.get(i);

            entb.tick();
        }
    }

    public void render(Graphics g){
        //A class
        for (int i = 0 ; i < e.size();i++){
            enta = e.get(i);

            enta.render(g);
        }

        //B class
        for (int i = 0 ; i < e1.size();i++){
            entb = e1.get(i);

            entb.render(g);
        }


    }

    public void addEntity(EntityA block){
        e.add(block);
    }
    public void removeEntity(EntityA block){
        e.remove(block);
    }
    public void addEntity(EntityB block){
        e1.add(block);
    }
    public void removeEntity(EntityB block){
        e1.remove(block);
    }

    public LinkedList<EntityA> getEA(){
        return e;
    }

    public LinkedList<EntityB> getEB(){
        return e1;
    }
}
