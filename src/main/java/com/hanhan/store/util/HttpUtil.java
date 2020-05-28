/**
 * 
 */
package com.hanhan.store.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jerryxia.devutil.http.CopiedByteHttpResponse;
import com.github.jerryxia.devutil.http.HttpHelper;
import com.google.common.io.Files;
import com.vip.vjtools.vjkit.io.FileUtil;

/**
 * @author JerryXia
 *
 */
public final class HttpUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
    
    public static boolean downFile(String fileGetUri, File file) {
        boolean downSuccessed = false;
        HttpGet httpGet = null;
        try {
            httpGet = HttpHelper.buildSimpleGet(fileGetUri, null, HttpHelper.DEFAULT_REQUEST_CONFIG);
        } catch (URISyntaxException e) {
            log.error("fileGetUri is invalid", e);
            return downSuccessed;
        }
        CopiedByteHttpResponse httpResponse = HttpHelper.expectedBytesExecuteRequest(httpGet);
        if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200) {
            try {
                if (!file.exists()) {
                    FileUtil.makesureParentDirExists(file);
                    file.createNewFile();
                }
                Files.write(httpResponse.getBody(), file);
                downSuccessed = true;
            } catch (IOException e) {
                log.error("downFile fail", e);
            }
        }
        return downSuccessed;
    }

}
