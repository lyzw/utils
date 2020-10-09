import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/9/3
 * @since 1.0
 */
public class ImageDraw {

    public static void draw(String imagePath, String path, java.util.List<DrawLocation> content) {

        //读取图片文件，得到BufferedImage对象
        BufferedImage bimg;
        try {
            bimg = ImageIO.read(new FileInputStream(imagePath));

            //得到Graphics2D 对象
            Graphics2D g2d = (Graphics2D) bimg.getGraphics();
            //设置颜色和画笔粗细
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
//            g2d.setFont(new Font("TimesRoman", Font.BOLD, 12));

            content.forEach(value-> g2d.drawString(value.getContent(), value.getX(), value.getY()));
            //保存新图片
            ImageIO.write(bimg, "PNG", new FileOutputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class DrawLocation {
        int x;
        int y;
        String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public DrawLocation(String content, int x, int y) {
            this.content = content;
            this.x = x;
            this.y = y;
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
    }

    public static void main(String[] args) {

        String imagePath = "C:\\Users\\zhouwei\\Desktop\\3.png";
        String outPath = "C:\\Users\\zhouwei\\Desktop\\4.png";
        int x = 60;
        AtomicInteger index = new AtomicInteger(-1);
        int step = 29;
        NumberFormat numberFormat = new DecimalFormat("###,###.00");
        System.out.println(numberFormat.format(100000000.12));
//        java.util.List<DrawLocation> content = new ArrayList<DrawLocation>() {{
//            add(new DrawLocation("2019年9月4日", 440, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("SAAS20_TRANS_01", 210, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("上海市浦东新区XXXXXX", 210, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("二手房出租", 210, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("定金", 210, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("POS收款", 210, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("￥1,000.00", 250, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("壹仟元整", 260, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("测试员工名称", 230, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("EMP_NO_00001", 230, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("前后端联调房地产有限公司", 230, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("02750001", 255, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("861440389120474", 255, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("测试付款用户名称", 220, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("测试付款用户角色", 220, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("ZF202009031612185502083592361", 230, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("2020年9月4日 12:12:12", 230, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("2020年9月4日 12:23:12", 230, x + (index.incrementAndGet() * step)));
//             add(new DrawLocation("易办事", 210, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("9551600000202009033370140964", 255, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("/", 255, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("/", 255, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("/", 255, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("/", 255, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation("备注信息", 150, x + (index.incrementAndGet() * step)));
//        }};
//        draw(imagePath, outPath, content);
    }

}
