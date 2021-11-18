import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    public static final int width = 550;
    public static final int height = 500;
    public static final int scale = 2;
    public final String Title = "Exterminate Dalex";

    private boolean is_shooting = false;

    private boolean running = false;
    private Thread thread;

    private BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
    private BufferedImage spritesheet = null;
    private BufferedImage background = null;
    private BufferedImage background1 = null;

    private int enemy_count = 5;
    private int enemy_killed = 0;

    public int minute = 1;
    public static int second = 30;
    public static int ticks = 0;
    public int score = 0;

    private Player p;
    private Controller c;
    private Textures tex;
    private Menu menu;

    public boolean winning = false;
    public boolean gameover = false;

    public LinkedList<EntityA> ea;
    public LinkedList<EntityB> eb;

    public static int healbar = 400;

    public enum STATE{
        menu,
        game
    };

    public static STATE state = STATE.menu;


    public void init(){
        requestFocus();
        BufferedImageLoader loader =new BufferedImageLoader();
        try{
            spritesheet = loader.loadImahe("/pic/sprite2.png");
            background = loader.loadImahe("/pic/bg1.jpg");
            background1 = loader.loadImahe("/pic/bg4.jpg");
        }catch (IOException e){
            e.printStackTrace();
        }

        tex = new Textures(this);
        c = new Controller(tex,this);
        p = new Player(50,50,tex,this,c);
        menu = new Menu();

        ea = c.getEA();
        eb = c.getEB();

        this.addKeyListener(new KeyInput(this));
        this.addMouseListener(new MouseInput());
        c.creatEnemy(enemy_count);
    }

    private synchronized void start(){
        if (running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();

    }

    private synchronized void stop(){
        if(!running)
            return;

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    @Override
    public void run() {
        init();
        double tickpertime = 1000000000 / 60;
        double delta = 0;
        long now;
        long lasttime = System.nanoTime();
        long timer = 0;

        while (!running || winning == false){
            now = System.nanoTime();
            delta += (now-lasttime) / tickpertime;
            timer += now - lasttime;
            lasttime = now;

            if( delta >= 1){
                tick();
                ticks++;
                delta--;
                if(ticks == 60){
                    second--;
                }
                if (second == 0){
                    if(minute >= 1) {
                        second = 59;
                        minute--;
                    }
                }
                if (timer >= 1000000000) {
                    timer = 0; ticks = 0; // ไม่ให้เกิน60
                }
                if(minute <= 0 && second <= 0 || healbar <= 0){
                    gameover = true;
                }
                if(score == 100){
                    winning = true;
                }
            }
            try {
                render();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    private void tick(){
        if(state == STATE.game) {
            p.tick();
            c.tick();
        }

        if(enemy_killed >= enemy_count){
            enemy_count += 2;
            enemy_killed = 0;
            c.creatEnemy(enemy_count);
        }
    }

    private void render() throws IOException {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.drawImage(image,0,0,getWidth() ,getHeight(),this);



        if(state == STATE.game){
            if(score < 50) {
                g.drawImage(background, 0, 0, null);
            }
            if (score >= 50){
                g.drawImage(background1, 0, 0, null);
            }

            if(!winning && !gameover){
                p.render(g);
                c.render(g);
                g.setColor(Color.GRAY);
                g.fillRect(120,10,400,50);

                g.setColor(Color.CYAN);
                g.fillRect(120,10,healbar,50);

                g.setColor(Color.GRAY);
                g.drawRect(120,10,400,50);

                g.setFont(new Font("TimesRoman",Font.BOLD,40));
                g.drawString(" HP :", 10, 50);
                g.drawString("TIME : " + minute + " : " + second, 10, 100);
                g.drawString("SCORE : " + score, 10, 150);
            }
            if(gameover == true){
                g.setFont(new Font("TimesRoman",Font.BOLD,100));
                g.drawString("GAME OVER!!" ,250, 500);
            }
            if(winning == true){
                g.setFont(new Font("TimesRoman",Font.BOLD,100));
                g.drawString("YOU WON!!" ,250, 500);
            }

        }
        else if (state == STATE.menu){
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }


    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();


        if(state == STATE.game) {
            if (key == KeyEvent.VK_D) {
                p.setVelX(6);
            } else if (key == KeyEvent.VK_A) {
                p.setVelX(-6);
            } else if (key == KeyEvent.VK_S) {
                p.setVelY(6);
            } else if (key == KeyEvent.VK_W) {
                p.setVelY(-6);
            } else if (key == KeyEvent.VK_SPACE && !is_shooting) {
                is_shooting = true;
                c.addEntity(new Bullet(p.getX(), p.getY(), tex, this));
            }
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_D){
            p.setVelX(0);
        }else if(key == KeyEvent.VK_A){
            p.setVelX(0);
        }else if(key == KeyEvent.VK_S){
            p.setVelY(0);
        }else if(key == KeyEvent.VK_W){
            p.setVelY(0);
        }else if(key == KeyEvent.VK_SPACE){
            is_shooting = false;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();

        game.setPreferredSize(new Dimension(width*scale,height*scale));
        game.setMaximumSize(new Dimension(width*scale,height*scale));
        game.setMinimumSize(new Dimension(width*scale,height*scale));

        JFrame frame = new JFrame(game.Title);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

    public BufferedImage getSpritesheet(){
        return spritesheet;
    }

    public void setEnemy_count(int enemy_count) {
        this.enemy_count = enemy_count;
    }

    public void setEnemy_killed(int enemy_killed) {
        this.enemy_killed = enemy_killed;
    }

    public int getEnemy_count() {
        return enemy_count;
    }

    public int getEnemy_killed() {
        return enemy_killed;
    }
}
