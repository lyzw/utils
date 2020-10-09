import lombok.Builder;
import lombok.Data;

import java.awt.*;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/9/16
 * @since 1.0
 */
@Data
@Builder
public class ReceiptLineListConfig {

    Color color;

    Stroke stroke;

    java.util.List<ReceiptLineConfig> lines;

}
