import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Level {
    private BufferedImage platformImage;
    private Rectangle[] platforms;

    public Level() {
        try {
            platformImage = ImageIO.read(getClass().getResourceAsStream("/GameImages/platform.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        platforms = new Rectangle[]{
                new Rectangle(200, 500, 200, 50),
                new Rectangle(500, 300, 100, 30)
        };
    }

    public void update() {
        // Update level logic here
    }

    public void render(Graphics g) {
        for (Rectangle platform : platforms) {
            g.drawImage(platformImage, platform.x, platform.y, null);
        }
    }

    public Rectangle[] getPlatforms() {
        return platforms;
    }
}