package com.zlebank.zplatform.task.reconcile.util;
import java.io.InputStream;
import java.net.Socket;
 
public class TimeUtil {
    public static final int DEFAULT_PORT = 37;//NTP服务器端口
    public static final String DEFAULT_HOST = "time-nw.nist.gov";//NTP服务器地址
    private TimeUtil() {
    };
 
    public static long currentTimeMillis(Boolean sync) {
        if (sync != null && sync.booleanValue() != true)
            return System.currentTimeMillis();
        try {
            return syncCurrentTime();
        } catch (Exception e) {
            return System.currentTimeMillis();
        }
    }
 
    public static long syncCurrentTime()  throws Exception {
        
        return System.currentTimeMillis();
        // The time protocol sets the epoch at 1900,
        // the java Date class at 1970. This number
        // converts between them.
//        long differenceBetweenEpochs = 2208988800L;
 
        // If you'd rather not use the magic number uncomment
        // the following section which calculates it directly.
 
        /*
         * TimeZone gmt = TimeZone.getTimeZone("GMT"); Calendar epoch1900 =
         * Calendar.getInstance(gmt); epoch1900.set(1900, 01, 01, 00, 00, 00);
         * long epoch1900ms = epoch1900.getTime().getTime(); Calendar epoch1970
         * = Calendar.getInstance(gmt); epoch1970.set(1970, 01, 01, 00, 00, 00);
         * long epoch1970ms = epoch1970.getTime().getTime();
         * 
         * long differenceInMS = epoch1970ms - epoch1900ms; long
         * differenceBetweenEpochs = differenceInMS/1000;
         */
 
//        InputStream raw = null;
//        try {
//            Socket theSocket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
//            raw = theSocket.getInputStream();
// 
//            long secondsSince1900 = 0;
//            for (int i = 0; i < 4; i++) {
//                secondsSince1900 = (secondsSince1900 << 8) | raw.read();
//            }
//            if (raw != null)
//                raw.close();
//            long secondsSince1970 = secondsSince1900 - differenceBetweenEpochs;
//            long msSince1970 = secondsSince1970 * 1000;
//            return msSince1970;
//        } catch (Exception e) {
//            throw new Exception(e);
//        }
    }
}