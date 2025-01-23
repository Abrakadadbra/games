import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    private static final double MOVE_SPEED = 250; // Скорость передвижения
    private static final double JUMP_SPEED = -550; // Скорость прыжка
    private static final double GRAVITY = 1200; // Гравитация
    private static final double MAX_FALL_SPEED = 700; // Максимальная скорость падения

    private int x, y;
    private int width, height;
    private BufferedImage image;
    private boolean leftPressed;
    private boolean rightPressed;
    private boolean jumping;
    private boolean falling;
    private boolean onGround;
    private double velocityX;
    private double velocityY;
    private Rectangle hitbox;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        width = 32;
        height = 64;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/GameImages/player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        hitbox = new Rectangle(x, y, width, height);
    }

    public void moveLeft() {
        leftPressed = true;
    }

    public void stopMovingLeft() {
        leftPressed = false;
    }

    public void moveRight() {
        rightPressed = true;
    }

    public void stopMovingRight() {
        rightPressed = false;
    }

    public void jump() {
        if (!jumping && !falling) {
            jumping = true;
            velocityY = JUMP_SPEED;
        }
    }

    public void update(double delta) {
        if (leftPressed) {
            velocityX = -MOVE_SPEED * delta;
        } else if (rightPressed) {
            velocityX = MOVE_SPEED * delta;
        } else {
            velocityX = 0;
        }

        if (!onGround) {
            velocityY += GRAVITY * delta;
        }

        if (velocityY > MAX_FALL_SPEED) {
            velocityY = MAX_FALL_SPEED;
        }

        x += velocityX;
        y += velocityY;

        checkCollisions();

        hitbox.x = (int)x;
        hitbox.y = (int)y;
    }

    private void checkCollisions() {
        // Здесь проверяются столкновения с платформами и другими объектами
    }

    public void render(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}