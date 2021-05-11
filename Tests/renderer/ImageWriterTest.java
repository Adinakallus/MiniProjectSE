package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void writeToImageTest() {
        ImageWriter imageWriter = new ImageWriter("bluepic", 800, 500);
        Color blue = new Color(0, 0, 255);
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(j, i, Color.BLACK);
                } else {
                    imageWriter.writePixel(j, i, blue);
                }
            }
        }
        imageWriter.writeToImage();
    }
}