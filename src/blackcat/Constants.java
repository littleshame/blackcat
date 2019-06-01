package blackcat;

import java.io.File;

/**
 * 全局变量
 * @author: tjc
 * @Date: 2019-5-31
 */
public class Constants {

    /** WEB_ROOT is the directory where our HTML and other files reside.
     *  For this package, WEB_ROOT is the "webroot" directory under the working
     *  directory.
     *  The working directory is the location in the file system
     *  from where the java command was invoked.
     */
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator  + "webroot";

    // shutdown command
    public static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

}