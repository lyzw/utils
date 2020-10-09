import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.sapling.utils.common.tools.common.StringUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * 付款凭证处理服务
 *
 * @author wei.zhou
 * @date 2020/9/4
 * @since 1.0
 */
@Slf4j
public class ReceiptService2 {

    private static final String TITLE = "收款入账凭证";

    private static final int power = 1;

    private static Font OPPO_SANS_R;
    private static Font OPPO_SANS_B;


    static {
        try {
            OPPO_SANS_R = Font.createFont(Font.TRUETYPE_FONT, ReceiptService2.class.getResourceAsStream("OPPOSans-R.ttf"));
            OPPO_SANS_B = Font.createFont(Font.TRUETYPE_FONT, ReceiptService2.class.getResourceAsStream("OPPOSans-B.ttf"));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public byte[] genReceiptPngBytes(PayTransInfoEntity entity) {
        return draw(entity);
    }

    static int width = 1099;
    static int height = 756;

    static int START_LOC_X1_LABEL = 138;
    static int START_LOC_X2_LABEL = width/2 + 60;
    static int START_LOC_Y_LABEL = 180;
    static int STEP_Y_LABEL_C1 = 32;

    static int START_LOC_Y_2 = 285;
    static int START_LOC_Y_3 = 440;
    static int START_LOC_Y_4 = 600;
    static int STEP_LOC_Y_2 = 27;
    static int STEP_LOC_Y_3 = 29;
    static int START_COL_1_X = width / 2 - 290;
    static int START_COL_2_X = width / 2 + 185;

    public static ReceiptTextConfig genContent(PayTransInfoEntity entity) {
        SimpleDateFormat sdf_yyyyMMdd_HHmmss = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


        NumberFormat numberFormat = new DecimalFormat("￥###,##0.00元");

        BigDecimal amount = new BigDecimal(entity.getPaidAmount()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        String amountS = numberFormat.format(amount);


        String companyName = entity.getMerchantName() == null ? entity.getCompanyName() : entity.getMerchantName();
        return ReceiptTextConfig.builder()
                .color(Color.BLACK)
                .font(OPPO_SANS_R.deriveFont(14f * power))
                .texts(Arrays.asList(
                        new StringDrawLocation(sdf_yyyyMMdd_HHmmss.format(new Date()), width - 220, 134),
                        new StringDrawLocation(companyName, START_COL_1_X, START_LOC_Y_LABEL),
                        new StringDrawLocation(entity.getPosDeviceId(), START_COL_1_X, START_LOC_Y_LABEL + STEP_Y_LABEL_C1),
                        new StringDrawLocation(entity.getPosMerchantId(), START_COL_1_X, START_LOC_Y_LABEL + STEP_Y_LABEL_C1 * 2),
                        new StringDrawLocation(entity.getPaidUserName(), START_COL_2_X, START_LOC_Y_LABEL),
                        new StringDrawLocation(entity.getPaidUserDesc(), START_COL_2_X, START_LOC_Y_LABEL + STEP_Y_LABEL_C1),

                        new StringDrawLocation(entity.getOrderNo(), START_COL_1_X, START_LOC_Y_2),
                        new StringDrawLocation(entity.getCardNo(), START_COL_2_X, START_LOC_Y_2),
                        new StringDrawLocation(sdf_yyyyMMdd_HHmmss.format(entity.getOrderTime()), START_COL_1_X, START_LOC_Y_2 + STEP_LOC_Y_2),
                        new StringDrawLocation(entity.getCardType(), START_COL_2_X, START_LOC_Y_2 + STEP_LOC_Y_2),

                        new StringDrawLocation(sdf_yyyyMMdd_HHmmss.format(entity.getPaidTime()), START_COL_1_X, START_LOC_Y_2 + STEP_LOC_Y_2 * 2),
                        new StringDrawLocation(entity.getIssue(), START_COL_2_X, START_LOC_Y_2 + STEP_LOC_Y_2 * 2),
//            new DrawLocation(JInwuPaidModeEnum.getPaidChannel(entity.getXrtTradeType()), START_COL_2_X, START_LOC_Y_2 + STEP_LOC_Y_2*3),

                        new StringDrawLocation(entity.getBankBillNo(), START_COL_2_X, START_LOC_Y_2 + STEP_LOC_Y_2 * 3),
                        new StringDrawLocation(entity.getXrtTransId(), START_COL_1_X, START_LOC_Y_2 + STEP_LOC_Y_2 * 4),

                        new StringDrawLocation(entity.getPaidProperty(), START_COL_1_X, START_LOC_Y_3),
                        new StringDrawLocation(entity.getTransType(), START_COL_1_X, START_LOC_Y_3 + STEP_LOC_Y_3),
                        new StringDrawLocation(entity.getTransactionNo(), START_COL_1_X, START_LOC_Y_3 + STEP_LOC_Y_3 * 2),
                        new StringDrawLocation(entity.getPropertyAddress(), START_COL_1_X, START_LOC_Y_3 + STEP_LOC_Y_3 * 3, 274, true),

                        new StringDrawLocation(entity.getEmployeeName(), START_COL_2_X, START_LOC_Y_3),
                        new StringDrawLocation(entity.getEmployeeNo(), START_COL_2_X, START_LOC_Y_3 + STEP_LOC_Y_3),
                        new StringDrawLocation(amountS, START_COL_2_X, START_LOC_Y_3 + STEP_LOC_Y_3 * 2),
                        new StringDrawLocation(Convert.digitToChinese(amount).replace("元","圆"), START_COL_2_X, START_LOC_Y_3 + STEP_LOC_Y_3 * 3, 274, true),

                        new StringDrawLocation(entity.getRemark(), START_LOC_X1_LABEL, START_LOC_Y_4)
                )).build();
    }

    public static byte[] draw(PayTransInfoEntity entity) {
        //读取图片文件，得到BufferedImage对象

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            BufferedImage image = new BufferedImage(width * power, height * power, BufferedImage.TYPE_INT_RGB);
            //得到Graphics2D 对象
            Graphics2D g2d = (Graphics2D) image.getGraphics();
            g2d.setStroke(new BasicStroke(power));
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width * power, height * power);
            g2d.setColor(Color.BLACK);
            drawBackGround(g2d);
            ReceiptTextConfig content = genContent(entity);
            drawText(g2d, content);
            ImageIO.write(image, "jpeg", out);
            return out.toByteArray();
        } catch (Exception e) {
            log.error("生成支付凭证失败，{}", e.getMessage(), e);
        }
        return null;
    }

    public static void drawBackGround(Graphics2D g2d) {
        drawTitle(g2d, width, height);
        drawBrLines(g2d, width, height);
        drawTableLine(g2d, width, height);
        drawLabels(g2d, width, height);
    }


    public static void drawLabels(Graphics2D g2d, int width, int height) {
        int middle = width / 2;

        ReceiptTextConfig labels = ReceiptTextConfig.builder()
                .color(new Color(0x728095))
                .font(OPPO_SANS_R.deriveFont(14f * power))
                .texts(Arrays.asList(
                        new StringDrawLocation("凭证生成时间：", width - 310, 134),
                        new StringDrawLocation("收款方", 91, START_LOC_Y_LABEL, DirectionEnum.VERTICAL, 24),
                        new StringDrawLocation("付款方", middle + 16, 180, DirectionEnum.VERTICAL, 24),

                        new StringDrawLocation("收款公司名称:", START_LOC_X1_LABEL, START_LOC_Y_LABEL, true, 116),
                        new StringDrawLocation("收款POS机设备号:", START_LOC_X1_LABEL, START_LOC_Y_LABEL + STEP_Y_LABEL_C1, true, 116),
                        new StringDrawLocation("收款POS机商户号:", START_LOC_X1_LABEL, START_LOC_Y_LABEL + STEP_Y_LABEL_C1 * 2, true, 116),
                        new StringDrawLocation("付款人名称:", START_LOC_X2_LABEL, START_LOC_Y_LABEL, true, 116),
                        new StringDrawLocation("付款人角色:", START_LOC_X2_LABEL, START_LOC_Y_LABEL + STEP_Y_LABEL_C1, true, 116),

                        new StringDrawLocation("支付信息", 91, 292, DirectionEnum.VERTICAL, 32),
                        new StringDrawLocation("支付订单编号:", START_LOC_X1_LABEL, 285, true, 116),
                        new StringDrawLocation("支付银行卡号:", START_LOC_X2_LABEL, 285, true, 116),
                        new StringDrawLocation("订单生成时间:", START_LOC_X1_LABEL, 285 + 29, true, 116),
                        new StringDrawLocation("支付银行卡类型:", START_LOC_X2_LABEL, 285 + 29, true, 116),
                        new StringDrawLocation("订单完成时间:", START_LOC_X1_LABEL, 285 + 54, true, 116),
                        new StringDrawLocation("支付银行卡发卡行:", START_LOC_X2_LABEL, 285 + 54, true, 116),
                        new StringDrawLocation("支付渠道:", START_LOC_X1_LABEL, 285 + 81, true, 116),
                        new StringDrawLocation("支付银行订单号:", START_LOC_X2_LABEL, 285 + 81, true, 116),
                        new StringDrawLocation("渠道支付流水号:", START_LOC_X1_LABEL, 285 + 108, true, 116),

                        new StringDrawLocation("收款信息", 91, 449, DirectionEnum.VERTICAL, 32),
                        new StringDrawLocation("收款性质:", START_LOC_X1_LABEL, 440, true, 116),
                        new StringDrawLocation("交易性质:", START_LOC_X1_LABEL, 440 + 29, true, 116),
                        new StringDrawLocation("收款编号:", START_LOC_X1_LABEL, 440 + 29 * 2, true, 116),
                        new StringDrawLocation("收款项目:", START_LOC_X1_LABEL, 440 + 29 * 3, true, 116),
                        new StringDrawLocation("操作人名称:", START_LOC_X2_LABEL, 440, true, 116),
                        new StringDrawLocation("操作人编号:", START_LOC_X2_LABEL, 440 + 29, true, 116),
                        new StringDrawLocation("收款金额(小写):", START_LOC_X2_LABEL, 440 + 29 * 2, true, 116),
                        new StringDrawLocation("收款金额(大写):", START_LOC_X2_LABEL, 440 + 29 * 3, true, 116),
                        new StringDrawLocation("备注", 91, 609, DirectionEnum.VERTICAL, 32),
                        new StringDrawLocation("注：《收款入帐凭证》仅供参考，如与支付渠道账户记录不一致，以账户记录为准。", 77, 712)
                ))
                .build();
        drawText(g2d, labels);
    }

    public static void drawTableLine(Graphics2D g2d, int width, int height) {
        ReceiptLineListConfig table = ReceiptLineListConfig.builder()
                .color(new Color(0x9fa9ba))
                .stroke(new BasicStroke(1 * power))
                .lines(Arrays.asList(
                        // 横线
                        new ReceiptLineConfig(75, 149, width - 75, 149),
                        new ReceiptLineConfig(75, 260, width - 75, 260),
                        new ReceiptLineConfig(75, 419, width - 75, 419),
                        new ReceiptLineConfig(75, 573, width - 75, 573),
                        new ReceiptLineConfig(75, 692, width - 75, 692),
                        // 竖线
                        // 两边的竖线
                        new ReceiptLineConfig(75, 149, 75, height - 60),
                        new ReceiptLineConfig(width - 75, 149, width - 75, height - 60),
                        // 第一排的竖线
                        new ReceiptLineConfig(120, 149, 120, height - 60),
                        new ReceiptLineConfig(549, 149, 549, 260),
                        new ReceiptLineConfig(595, 149, 595, 260)
                ))
                .build();
        drawLines(g2d, table);
    }

    public static void drawBrLines(Graphics2D g2d, int width, int height) {
        ReceiptLineListConfig brLine = ReceiptLineListConfig.builder()
                .color(new Color(0xf1f1f5))
                .stroke(new BasicStroke(4 * power))
                .lines(Arrays.asList(
                        new ReceiptLineConfig(width / 2 - 125, 78, width / 2 + 125, 78),
                        new ReceiptLineConfig(width / 2 - 125, 88, width / 2 + 125, 88)
                ))
                .build();
        drawLines(g2d, brLine);
    }

    public static void drawTitle(Graphics2D g2d, int width, int height) {
        ReceiptTextConfig title = ReceiptTextConfig.builder()
                .color(new Color(0x101D48))
                .font(OPPO_SANS_B.deriveFont(28f * power))
                .texts(Collections.singletonList(new StringDrawLocation(TITLE, width / 2 - TITLE.length() * 14, 50)))
                .build();
        drawText(g2d, title);
    }

    /**
     * 写文本内容
     *
     * @param g2d
     * @param config
     */
    public static void drawText(Graphics2D g2d, ReceiptTextConfig config) {
        if (config == null || config.getTexts() == null || config.getTexts().isEmpty()) {
            return;
        }
        if (config.getColor() != null) {
            g2d.setColor(config.getColor());
        }
        if (config.getFont() != null) {
            g2d.setFont(config.getFont());
        }
        FontMetrics m = g2d.getFontMetrics();
        config.getTexts().forEach(f -> {
            int x = f.getX();
            int y = f.getY();
            String content = StringUtil.isEmpty(f.getContent()) ? "/" : f.getContent();

            //纵向的话需要分多个
            if (DirectionEnum.VERTICAL.equals(f.direction)) {
                int space = f.getSpace();
                for (String c : content.split("|")) {
                    g2d.drawString(c, x * power, y * power);
                    y += space;
                }
                // 是否拉伸铺满长度
            } else if (f.isStretch()) {
                if (f.getLength() * power - m.stringWidth(content) < m.stringWidth("c")) {
                    g2d.drawString(content, x * power, y * power);
                } else {
                    // 拉伸需要判断各个不同的数据实际的长度
                    int step = f.getLength() / (content.length() - 1);
                    // 最后一个先放
                    String lstChar = content.substring(content.length() - 1);
                    g2d.drawString(lstChar, (x + f.getLength()) * power - m.stringWidth(lstChar), y * power);
                    for (String c : content.substring(0, content.length() - 1).split("|")) {
                        g2d.drawString(c, x * power, y * power);
                        x += Math.max(m.stringWidth(c) / power, step);
                    }
                }
                // 是否换行
            } else if (f.isWrapLines()) {
                int maxLocX = (x + f.getLength());
                int tmp = x;
                if (f.getLength() * power - m.stringWidth(content) < 0) {
                    for (String c : content.split("|")) {
                        g2d.drawString(c, tmp * power, y * power);
                        tmp += m.stringWidth(c) / power;
                        if (tmp >= maxLocX) {
                            tmp = x;
                            y += m.getHeight() / power;
                        }
                    }
                } else {
                    g2d.drawString(content, f.getX() * power, f.getY() * power);
                }
            } else {
                g2d.drawString(content, f.getX() * power, f.getY() * power);
            }
        });
    }

    /**
     * 画填充矩形
     *
     * @param g2d
     * @param config
     */
    public static void drawFillRects(Graphics2D g2d, ReceiptRectListConfig config) {
        if (config == null || config.getRects() == null || config.getRects().isEmpty()) {
            return;
        }
        if (config.getColor() != null) {
            g2d.setColor(config.getColor());
        }
        config.getRects().forEach(f -> g2d.fillRect(f.getX() * power, f.getY() * power, f.getWidth() * power, f.getLength() * power));
    }

    public static void drawLines(Graphics2D g2d, ReceiptLineListConfig config) {
        if (config == null || config.getLines() == null || config.getLines().isEmpty()) {
            return;
        }
        if (config.getColor() != null) {
            g2d.setColor(config.getColor());
        }
        if (config.getStroke() != null) {
            g2d.setStroke(config.getStroke());
        }
        config.getLines().forEach(f -> g2d.drawLine(f.getX1() * power, f.getY1() * power, f.getX2() * power, f.getY2() * power));
    }

    /**
     * 画空矩形
     *
     * @param g2d
     * @param config
     */
    public void drawRects(Graphics2D g2d, ReceiptRectListConfig config) {
        if (config == null || config.getRects() == null || config.getRects().isEmpty()) {
            return;
        }
        if (config.getColor() != null) {
            g2d.setColor(config.getColor());
        }
        config.getRects().forEach(f -> g2d.drawRect(f.getX() * power, f.getY() * power, f.getWidth() * power, f.getLength() * power));
    }

    public static void main(String[] args) throws IOException {
        String data = "{\n" +
                "\"id\": 21,\n" +
                "\"companyUuid\": \"ORG10001\",\n" +
                "\"transactionNo\": \"SAAS20_TRANS_0001\",\n" +
                "\"companyName\": \"测试公司Uuid\",\n" +
                "\"employeeUuid\": \"EMP_00001\",\n" +
                "\"employeeName\": \"测试员工名称\",\n" +
                "\"employeeNo\": \"EMP00001\",\n" +
                "\"paidUserName\": \"测试付款用户\",\n" +
                "\"paidUserDesc\": \"测试付款用户\",\n" +
                "\"transAmount\": 112311121310,\n" +
                "\"payAmount\": 112311121310,\n" +
                "\"transType\": \"二手房交易\",\n" +
                "\"propertyAddress\": \"这是一个很长的d地址，就想看看你到底换行还是不换行\",\n" +
                "\"paidProperty\": \"定金\",\n" +
                "\"paidType\": null,\n" +
                "\"paidAmount\": 112311121310,\n" +
                "\"remark\": \"这是一个很长的收款备注，看看是不是满足这个收款信息。看了下其实也不长！！！\",\n" +
                "\"sourceChannel\": \"saas20\",\n" +
                "\"orderNo\": \"e664c357e44b4a0889e67a7dbae84ac5\",\n" +
                "\"merchantId\": \"86144035734Z153\",\n" +
                "\"body\": \"收款备注\",\n" +
                "\"merchantCreateIp\": \"127.0.0.1\",\n" +
                "\"notifyUrl\": \"https://atool.dev.qiaofangyun.com/api/payment/notify\",\n" +
                "\"goodsTag\": null,\n" +
                "\"attach\": null,\n" +
                "\"productId\": null,\n" +
                "\"nonceStr\": \"1597054802488\",\n" +
                "\"qrCode\": \"https://qr.95516.com/00010048/unifiedNative?token=393256a175ccfa150cce76366034e9983\",\n" +
                "\"qrCodeUrl\": null,\n" +
                "\"xrtTransId\": \"traceno111\",\n" +
                "\"cashAmount\": null,\n" +
                "\"couponFee\": null,\n" +
                "\"feeType\": null,\n" +
                "\"bankType\": \"BANK_CARD\",\n" +
                "\"bankBillNo\": \"bankbillno333\",\n" +
                "\"retCode\": null,\n" +
                "\"retMessage\": null,\n" +
                "\"orderTime\": 1597054802000,\n" +
                "\"expireTime\": 1597056602000,\n" +
                "\"paidTime\": 1597111895000,\n" +
                "\"paidStatus\": \"SUCCESS\",\n" +
                "\"refundStatus\": null,\n" +
                "\"sendJinWuFlag\": false,\n" +
                "\"deleted\": null,\n" +
                "\"createdTime\": 1597054803000,\n" +
                "\"updatedTime\": 1598856050000,\n" +
                "\"createdUuid\": null," +
                "\"updatedUuid\": null,\n" +
                "\"posDeviceId\": \"posdev\",\n" +
                "\"posMerchantId\": \"posmerc\",\n" +
                "\"cardNo\": \"cardno222\",\n" +
                "\"cardType\": \"DEBIT\",\n" +
                "\"issue\": \"招商银行陆家嘴支行\",\n" +
                "\"xrtTradeType\": null,\n" +
                "\"paymentChannel\": null\n" +
                "}";

        String writerNames[] = ImageIO.getWriterFormatNames();
        Arrays.stream(writerNames).forEach(System.out::println);
        PayTransInfoEntity entity = JSONObject.parseObject(data, PayTransInfoEntity.class);
        try (OutputStream out = new FileOutputStream(new File(("C:\\Users\\zhouwei\\Desktop\\6.jpeg")))) {
            byte[] bytes = new ReceiptService2().genReceiptPngBytes(entity);
            out.write(bytes);
        }
        ;
    }


}
