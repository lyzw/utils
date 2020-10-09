import lombok.Builder;
import lombok.Data;

import java.awt.*;
import java.util.Collection;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/9/16
 * @since 1.0
 */
@Data
@Builder
public class ReceiptRectListConfig {
    Color color;

    java.util.List<ReceiptRectConfig> rects;

}
