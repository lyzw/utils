import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.sapling.utils.common.tools.common.StringUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 付款凭证处理服务
 *
 * @author wei.zhou
 * @date 2020/9/4
 * @since 1.0
 */
@Slf4j
public class ReceiptService {

//
//    public String genAndUploadReceipt(PayTransInfoEntity entity) {
//        byte[] bytes = genReceiptPngBytes(entity);
//        String path = String.format("dataproduct/payment/%s/%s_%s.PNG", entity.getCompanyUuid(),  entity.getOrderNo(), new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//        if (bytes != null) {
//            String imageUrl = fileUploadService.upload(bytes, path);
//            log.info("upload image finished, result: {}", imageUrl);
//            return imageUrl;
//        }
//        return null;
//    }
//

    public byte[] genReceiptPngBytes(PayTransInfoEntity entity) {
        List<StringDrawLocation> content = this.genReceiptDrawContent(entity);
        return draw("收款入账凭证", content);
    }


    private List<StringDrawLocation> genReceiptDrawContent(PayTransInfoEntity entity) {
        SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sdf_yyyyMMdd_HHmmss = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        int x = 60;
        AtomicInteger index = new AtomicInteger(-1);
        int step = 29;
        String paidType = "POS_OFFLINE".equals(entity.getPaidType()) ? "POS收款" : "线上支付";
        NumberFormat numberFormat = new DecimalFormat("###,##0.00");
        BigDecimal amount = new BigDecimal(entity.getPaidAmount()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        String amountS = numberFormat.format(amount);
        // 公司名称优先使用商户名称，商户名称为空的情况下，使用上送的公司名称
        String companyName = StringUtil.isEmpty(entity.getMerchantName()) ? entity.getCompanyName() : entity.getMerchantName();
        return new ArrayList<StringDrawLocation>() {{
            add(new StringDrawLocation(sdf_yyyyMMdd.format(new Date()), 440, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getTransactionNo(), 210, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getPropertyAddress(), 210, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getTransType(), 210, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getPaidProperty(), 210, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(paidType, 210, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(amountS, 260, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(Convert.digitToChinese(amount), 260, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getEmployeeName(), 230, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getEmployeeNo(), 230, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(companyName, 230, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getPosDeviceId(), 255, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getPosMerchantId(), 255, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getPaidUserName(), 220, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getPaidUserDesc(), 220, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getOrderNo(), 230, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(sdf_yyyyMMdd_HHmmss.format(entity.getOrderTime()), 230, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(sdf_yyyyMMdd_HHmmss.format(entity.getPaidTime()), 230, x + (index.incrementAndGet() * step)));
//            add(new DrawLocation(JInwuPaidModeEnum.getPaidChannel(entity.getXrtTradeType()), 210, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getXrtTransId(), 255, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getCardNo(), 255, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getCardType(), 255, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getIssue(), 255, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getBankBillNo(), 255, x + (index.incrementAndGet() * step)));
            add(new StringDrawLocation(entity.getRemark(), 150, x + (index.incrementAndGet() * step)));
        }};
    }


    public static byte[] draw(String title, List<StringDrawLocation> content) {
        //读取图片文件，得到BufferedImage对象
        int width = 750 *3 , height = 2000 *3;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            //得到Graphics2D 对象
            Graphics2D g2d = (Graphics2D) image.getGraphics();
            //设置颜色和画笔粗细
            g2d.setBackground(Color.GRAY);
            // 写名称
            g2d.setColor(new Color(0x101D48));
            g2d.setFont(new Font("OPPOSans, OPPOSans-R", Font.BOLD, 32 * 3));
            g2d.setStroke(new BasicStroke(1));
            g2d.drawString(title, 32 * 3, 64 * 3);
            g2d.setFont(new Font("OPPOSans, OPPOSans-R", Font.BOLD, 28 * 3));
            g2d.drawString("收款项目信息", 32 * 3, 232 * 3);
            g2d.drawString("收款方信息", 32 * 3, 624 * 3);
            g2d.drawString("付款方信息", 32 * 3, 968 * 3);
            g2d.drawString("支付信息", 32 * 3, 1168 * 3);
            g2d.drawString("备注", 32 * 3, 1716 * 3);
            Rectangle2D top = new Rectangle2D.Double(30 * 3,130 * 3,540 * 3,16 * 3);
            Rectangle2D bottom = new Rectangle2D.Double(30 * 3,1360 * 3,540 * 3,16 * 3);
            g2d.setColor(new Color(0x9fa9ba));
//            g2d.setColor(Color.BLACK);
            g2d.fill(top);
            g2d.fill(bottom);


            g2d.setColor(new Color(0xf1f1f5));
//            g2d.setColor(Color.BLACK);
            g2d.fillRect(30 * 3,584 * 3,688 * 3,4 * 3);
            g2d.fillRect(30 * 3,928 * 3,688 * 3,4 * 3);
            g2d.fillRect(30 * 3,1128 * 3,688 * 3,4 * 3);
            g2d.fillRect(30 * 3,1676 * 3,688 * 3,4 * 3);
//            content.forEach(value -> g2d.drawString(StringUtil.isEmpty(value.getContent()) ? "\\" : value.getContent(), value.getX(), value.getY()));
            //保存新图片
            g2d.setFont(new Font("OPPOSans, OPPOSans-R", Font.BOLD, 24 * 3));
            g2d.setColor(new Color(0x728095));
            g2d.drawString("凭证生成时间",32 * 3, 120 * 3);
            g2d.drawString("收款编号",32 * 3, 284 * 3);
            g2d.drawString("收款项目",32 * 3, 332 * 3);
            g2d.drawString("收款性质",32 * 3, 380 * 3);
            g2d.drawString("交易方式",32 * 3, 428 * 3);
            g2d.drawString("收款金额(小写)",32 * 3, 476 * 3);
            g2d.drawString("收款金额(大写)",32 * 3, 524 * 3);
            g2d.drawString("收款人名称",32 * 3, 676 * 3);
            g2d.drawString("收款人编号",32 * 3, 724 * 3);
            g2d.drawString("收款公司名称",32 * 3, 772 * 3);
            g2d.drawString("付款人名称",32 * 3, 820 * 3);
            g2d.drawString("付款人角色",32 * 3, 868 * 3);
            g2d.drawString("收款POS机商户号",32 * 3, 868 * 3);
            g2d.drawString("收款POS机商户号",32 * 3, 868 * 3);
            g2d.drawString("收款POS机商户号",32 * 3, 868 * 3);
            ImageIO.write(image, "PNG", out);
            return out.toByteArray();
        } catch (Exception e) {
            log.error("生成支付凭证失败，{}", e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String data = "{\n" +
                "\"id\": 21,\n" +
                "\"companyUuid\": \"ORG10001\",\n" +
                "\"transactionNo\": \"SAAS20_TRANS_0001\",\n" +
                "\"companyName\": \"测试公司Uuid\",\n" +
                "\"employeeUuid\": \"EMP_00001\",\n" +
                "\"employeeName\": \"测试员工名称\",\n" +
                "\"employeeNo\": null,\n" +
                "\"paidUserName\": \"测试付款用户\",\n" +
                "\"paidUserDesc\": \"测试付款用户\",\n" +
                "\"transAmount\": 10,\n" +
                "\"payAmount\": 10,\n" +
                "\"transType\": \"order\",\n" +
                "\"propertyAddress\": \"上海市浦东新区XXXXXX\",\n" +
                "\"paidProperty\": null,\n" +
                "\"paidType\": null,\n" +
                "\"paidAmount\": 123,\n" +
                "\"remark\": \"无\",\n" +
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
        PayTransInfoEntity entity = JSONObject.parseObject(data, PayTransInfoEntity.class);
        try (OutputStream out = new FileOutputStream(new File(("C:\\Users\\zhouwei\\Desktop\\6.png")))) {
            byte[] bytes = new ReceiptService().genReceiptPngBytes(entity);
            out.write(bytes);
        }
        ;


    }
}
