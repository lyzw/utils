/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/9/4
 * @since 1.0
 */
public class StringDrawLocation {
    /**
     * x坐标的位置
     */
    int x;
    /**
     * y坐标的位置
     */
    int y;
    /**
     * 文本的内容
     */
    String content;

    /**
     * 方向
     */
    DirectionEnum direction = DirectionEnum.HORIZONTAL;

    /**
     * 是否拉伸平铺
     */
    boolean stretch = false;

    /**
     * 是否换行
     */
    boolean wrapLines = false;

    int length = 0;

    /**
     * 间距，主要是纵向的内容需要使用
     */
    int space = 0;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StringDrawLocation(String content, int x, int y) {
        this.content = content;
        this.x = x;
        this.y = y;
    }

    public StringDrawLocation(String content, int x, int y, DirectionEnum direction, int space) {
        this.x = x;
        this.y = y;
        this.content = content;
        this.direction = direction;
        this.space = space;
    }

    /**
     * @param content 文本内容
     * @param x       x坐标
     * @param y       y坐标
     * @param stretch 是否拉伸
     * @param length  长度
     */
    public StringDrawLocation(String content, int x, int y, boolean stretch, int length) {
        this.x = x;
        this.y = y;
        this.content = content;
        this.stretch = stretch;
        this.length = length;
    }

    /**
     * @param content   文本内容
     * @param x         x坐标
     * @param y         y坐标
     * @param length    长度
     * @param wrapLines 是否换行
     */
    public StringDrawLocation(String content, int x, int y, int length, boolean wrapLines) {
        this.x = x;
        this.y = y;
        this.content = content;
        this.wrapLines = wrapLines;
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

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public boolean isStretch() {
        return stretch;
    }

    public void setStretch(boolean stretch) {
        this.stretch = stretch;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isWrapLines() {
        return wrapLines;
    }

    public void setWrapLines(boolean wrapLines) {
        this.wrapLines = wrapLines;
    }
}
