import lombok.Builder;
import lombok.Data;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/9/16
 * @since 1.0
 */
public class ReceiptRectConfig {

    int x;

    int y;

    int width;

    int length;

    public ReceiptRectConfig(int x, int y, int width, int length) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
