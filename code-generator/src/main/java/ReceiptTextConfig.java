import lombok.Builder;
import lombok.Data;

import java.awt.*;
import java.util.List;

/**
 * {description here}
 *
 * @author wei.zhou
 * @date 2020/9/16
 * @since 1.0
 */
@Data
@Builder
public class ReceiptTextConfig {

    Font font;

    Color color;

    List<StringDrawLocation> texts;

}
