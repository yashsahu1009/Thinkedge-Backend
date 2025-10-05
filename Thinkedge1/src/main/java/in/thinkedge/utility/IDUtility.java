 package in.thinkedge.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IDUtility {

    public static String generateProdId() {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String prodId = sdf.format(today);
        prodId = "P" + prodId;
        return prodId;
    }
}
