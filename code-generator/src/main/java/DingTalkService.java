import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/9/16
 * @since 1.0
 */
public class DingTalkService {


    public static OapiRobotSendResponse sendMarkdownMessage(String url, String secret, String title, String content)
            throws NoSuchAlgorithmException, InvalidKeyException, ApiException {
        DingTalkClient client = new DefaultDingTalkClient(genSignedUrl(url, secret));
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll("true");
        request.setAt(at);
        request.setMsgtype("markdown");
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText(content);
        request.setMarkdown(markdown);
        return client.execute(request);
    }

    public static String genSignedUrl(String url, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Long timestamp = System.currentTimeMillis();
        String sign = genSign(timestamp, secret);
        return String.format("%s&timestamp=%s&sign=%s", url, timestamp.toString(), sign);
    }

    public static String genSign(Long timestamp, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        String sign = new cn.hutool.core.net.URLEncoder().encode(new String(Base64.encodeBase64(signData)), StandardCharsets.UTF_8);
        System.out.println(sign);
        return sign;
    }


    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, ApiException {
        System.out.println(genSign(System.currentTimeMillis(), "SEC905de40dd973ea9c6c61fe1d46c6e3cad1a1c5434535edd7ff0b5062b1cfab30"));
        System.out.println(genSignedUrl("https://oapi.dingtalk.com/robot/send?access_token=dd3260e8006f6d5475e90a51c8501536ae5d92d650c6f21fa49d405b100527be", "SEC905de40dd973ea9c6c61fe1d46c6e3cad1a1c5434535edd7ff0b5062b1cfab30"));
        System.out.println(sendMarkdownMessage("https://oapi.dingtalk.com/robot/send?access_token=dd3260e8006f6d5475e90a51c8501536ae5d92d650c6f21fa49d405b100527be",
                "SEC905de40dd973ea9c6c61fe1d46c6e3cad1a1c5434535edd7ff0b5062b1cfab30", "系统支付通知失败告警ce",
                "### 系统支付通知失败告警\n\n\n\n" +
                        "**源系统：**jeditransaction\n\n" +
                        "**源系统名称：**前后端联调\n\n" +
                        "**公司Uuid：**qianhouduanliandiaoy_saas2_99dcd576f\n\n" +
                        "**收款编号：**SK699119061089425\n\n" +
                        "**交易流水号：**ZF202009141801264301674171175\n\n" +
                        "**失败总次数：**180\n\n" +
                        "**通知地址：**\n\n" +
                        "> https://jedi.dev.qiaofangyun.com/qianhouduanliandiaoy_saas2_99dcd576f/transaction/collectMoney/confirm/5a2a7fbc80fe497191dcca348543e2e2/8bbd7323e3c64a32b426f93b50f84826"));
    }
}
