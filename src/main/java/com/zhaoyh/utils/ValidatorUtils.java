package com.zhaoyh.utils;

import java.util.regex.Pattern;

/**
 * Created by zhaoyh on 2017/07/31.
 */
public class ValidatorUtils {

    public static Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");

    public static boolean isLegalUrl(String url) {
        if (!StringUtils.isBlank(url)) {
            return pattern.matcher(url).matches();
        }
        return false;
    }
}
