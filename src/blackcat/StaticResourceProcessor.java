package blackcat;

import java.io.IOException;

/**
 * 静态资源
 * @author: tjc
 * @Date: 2019-5-31
 */
public class StaticResourceProcessor {
    public void process(Request request, Response response) {
        try {
            response.sendStaticResource();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
