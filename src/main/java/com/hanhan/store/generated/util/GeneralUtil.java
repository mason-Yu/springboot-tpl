/**
 * 
 */
package com.hanhan.store.generated.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vip.vjtools.vjkit.base.annotation.NotNull;
import com.vip.vjtools.vjkit.text.Charsets;
import com.vip.vjtools.vjkit.text.EncodeUtil;
import com.vip.vjtools.vjkit.text.HashUtil;
import com.google.common.hash.Hashing;

/**
 * @author JerryXia
 *
 */
public final class GeneralUtil {
    private static final Logger log = LoggerFactory.getLogger(GeneralUtil.class);

    public static String md5(File targetFile) {
        String currentFileMd5 = null;
        try {
            byte[] fileHash = HashUtil.md5File(new FileInputStream(targetFile));
            currentFileMd5 = EncodeUtil.encodeHex(fileHash).toLowerCase();
        } catch (IOException e) {
            log.error("md5File fail", e);
        }
        return currentFileMd5;
    }

    public static String md5(@NotNull String input) {
        return Hashing.md5().hashString(input, Charsets.UTF_8).toString();
    }
}
