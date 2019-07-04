package com.lv.cloud.utils;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class HessionSerializeUtil {
    private HessionSerializeUtil() {
    }

    public static byte[] serialize(Object obj) {
        if(obj == null) {
            return null;
        } else {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Hessian2Output ho = new Hessian2Output(os);

            try {
                ho.writeObject(obj);
                ho.flush();
                byte[] var3 = os.toByteArray();
                return var3;
            } catch (IOException var13) {
                var13.printStackTrace();
            } finally {
                try {
                    ho.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }

            }

            return null;
        }
    }

    public static Object deserialize(byte[] by) {
        if(by == null) {
            return null;
        } else {
            ByteArrayInputStream is = new ByteArrayInputStream(by);
            Hessian2Input hi = new Hessian2Input(is);

            try {
                Object var3 = hi.readObject();
                return var3;
            } catch (IOException var13) {
                var13.printStackTrace();
            } finally {
                try {
                    hi.close();
                } catch (IOException var12) {
                    var12.printStackTrace();
                }

            }

            return null;
        }
    }
}
