import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Table(name = "pay_trans_info")
@Data
public class PayTransInfoEntity {
    /**
     * 逻辑主键、自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * saas公司uuid
     */
    @Column(name = "companyUuid")
    private String companyUuid;

    /**
     * saas付款交易标号
     */
    @Column(name = "transactionNo")
    private String transactionNo;

    /**
     * 公司名称
     */
    @Column(name = "companyName")
    private String companyName;

    /**
     * 操作员uuid
     */
    @Column(name = "employeeUuid")
    private String employeeUuid;


    /**
     * 操作员姓名
     */
    @Column(name = "employeeName")
    private String employeeName;

    /**
     * 操作员员工编号
     */
    @Column(name = "employeeNo")
    private String employeeNo;

    /**
     * 付款用户名称
     */
    @Column(name = "paidUserName")
    private String paidUserName;

    /**
     * 付款用户角色
     */
    @Column(name = "paidUserDesc")
    private String paidUserDesc;

    /**
     * 交易金额--分
     */
    @Column(name = "transAmount")
    private Long transAmount;

    /**
     * 支付金额（单位为分）
     */
    @Column(name = "payAmount")
    private Long payAmount;

    /**
     * 交易性质
     */
    @Column(name = "transType")
    private String transType;

    /**
     * 产证地址
     */
    @Column(name = "propertyAddress")
    private String propertyAddress;

    /**
     * 收款性质
     */
    @Column(name = "paidProperty")
    String paidProperty;

    /**
     * 收款方式
     */
    @Column(name = "paidType")
    private String paidType;

    /**
     * 支付金额
     */
    @Column(name = "paidAmount")
    private Long paidAmount;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 提交渠道
     */
    @Column(name = "sourceChannel")
    private String sourceChannel;

    /**
     * 订单id
     */
    @Column(name = "orderNo")
    private String orderNo;

    /**
     * 商户号
     */
    @Column(name = "merchantId")
    private String merchantId;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 订单生成的机器 IP
     */
    @Column(name = "merchantCreateIp")
    private String merchantCreateIp;

    /**
     * 通知地址
     */
    @Column(name = "notifyUrl")
    private String notifyUrl;

    /**
     * 商品标记
     */
    @Column(name = "goodsTag")
    private String goodsTag;

    /**
     * 附加信息
     */
    private String attach;

    /**
     * 商品 ID
     */
    @Column(name = "productId")
    private String productId;

    /**
     * 随机字符串
     */
    @Column(name = "nonceStr")
    private String nonceStr;

    /**
     * 二维码信息
     */
    @Column(name = "qrCode")
    private String qrCode;

    /**
     * 二维码链接
     */
    @Column(name = "qrCodeUrl")
    private String qrCodeUrl;

    /**
     * 聚合支付交易编号
     */
    @Column(name = "xrtTransId")
    private String xrtTransId;

    /**
     * 现金券金额
     */
    @Column(name = "cashAmount")
    private Long cashAmount;

    /**
     * 现金券金额
     */
    @Column(name = "couponFee")
    private Long couponFee;

    /**
     * 货币种类,默认为CNY-人民币
     */
    @Column(name = "feeType")
    private String feeType;

    /**
     * 银行类型
     */
    @Column(name = "bankType")
    private String bankType;

    /**
     * 银行订单号
     */
    @Column(name = "bankBillNo")
    private String bankBillNo;

    /**
     * 第三方支付返回码
     */
    @Column(name = "retCode")
    private String retCode;

    /**
     * 第三方支付返回消息
     */
    @Column(name = "retMessage")
    private String retMessage;

    /**
     * 订单创建时间
     */
    @Column(name = "orderTime")
    private Date orderTime;

    /**
     * 订单失效时间
     */
    @Column(name = "expireTime")
    private Date expireTime;

    /**
     * 支付时间
     */
    @Column(name = "paidTime")
    private Date paidTime;

    /**
     * 支付状态
     */
    @Column(name = "paidStatus")
    private String paidStatus;

    /**
     * 退款状态
     */
    @Column(name = "refundStatus")
    private String refundStatus;

    /**
     * 金屋发送状态
     */
    @Column(name = "sendJinWuFlag")
    private Boolean sendJinWuFlag;
    /**
     * 金屋发送状态
     */
    @Column(name = "deleted")
    private Boolean deleted;

    /**
     * 创建时间
     */
    @Column(name = "createdTime")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updatedTime")
    private Date updatedTime;

    /**
     * 创建人uuid
     */
    @Column(name = "createdUuid")
    private String createdUuid;

    /**
     * 修改人uuid
     */
    @Column(name = "updatedUuid")
    private String updatedUuid;

    /**
     * pos机设备号
     */
    @Column(name = "posDeviceId")
    private String posDeviceId;

    /**
     * pos机商户号 进行收款的POS终端设备绑定的商户号
     */
    @Column(name = "posMerchantId")
    private String posMerchantId;

    /**
     * 银行卡号
     */
    @Column(name = "cardNo")
    private String cardNo;

    /**
     * 银行卡类型，如借记卡、信用卡
     */
    @Column(name = "cardType")
    private String cardType;

    /**
     * 发卡行
     */
    @Column(name = "issue")
    private String issue;


    /**
     * 交易类型，可以用于区分不同的支付方式
     */
    @Column(name = "xrtTradeType")
    private String xrtTradeType;

    /**
     * 支付渠道
     */
    @Column(name = "paymentChannel")
    private String paymentChannel;

    /**
     * 凭证Url
     */
    @Column(name = "receiptUrl")
    private String receiptUrl;

    /**
     * 源系统前端跳转地址
     */
    @Column(name = "sourceFrontendUrl")
    private String sourceFrontendUrl;

    /**
     * 源系统通知地址
     */
    @Column(name = "sourceBackNotifyUrl")
    private String sourceBackNotifyUrl;

    /**
     * 商户名称
     */
    @Column(name = "merchantName")
    private String merchantName;

    /**
     * 源系统退款通知地址
     */
    @Column(name = "sourceBackRefundNotifyUrl")
    private String sourceBackRefundNotifyUrl;

    /**
     * 源系统通知次数
     */
    @Column(name = "sourceNotifyFlag")
    private Integer sourceNotifyFlag;

}