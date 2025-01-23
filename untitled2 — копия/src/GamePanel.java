import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {
    private Thread gameThread;
    private boolean running;
    private BufferedImage background;
    private Player player;
    private Level level;

    public GamePanel() {
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/GameImages/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        player = new Player(100, 450);
        level = new Level();

        this.addKeyListener(new KeyInputHandler());
        this.setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        player.render(g);
        level.render(g);
    }

    public void startGame() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        while (running) {
            long now = System.nanoTime();
            double delta = (now - lastTime) / nsPerTick;

            if (delta >= 1) {
                tick(delta);
                render();
                lastTime = now;
            }
        }
    }

    private void tick(double delta) {
        player.update(delta);
        level.update();
    }

    private void render() {
        repaint();
    }

    private class KeyInputHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    player.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    player.moveRight();
                    break;
                case KeyEvent.VK_UP:
                    player.jump();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    player.stopMovingLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    player.stopMovingRight();
                    break;
            }
        }
    }
}