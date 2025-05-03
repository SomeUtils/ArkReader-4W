package vip.cdms.arkreader.resource.network;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import vip.cdms.arkreader.resource.utils.IOUtils;

import java.io.*;

@RequiredArgsConstructor
public class NetworkCache {
    private final File root;

    public File getFile(String url) {
        return new File(root, getFileName(url));
    }
    public String getFileName(String url) {
        return Integer.toHexString(url.hashCode());
    }

    @SneakyThrows
    public byte[] get(String url) {
        val file = getFile(url);
        if (file == null || !file.exists()) return null;
        return IOUtils.readAllOrNull(new FileInputStream(file));
    }

    @SneakyThrows
    public void set(String url, byte[] content) {
        val file = getFile(url);
        if (file == null) return;
        IOUtils.writeToFile(content, file);
    }

    public String getAsString(String url) {
        val cached = get(url);
        return cached == null ? null : new String(cached);
    }

    public void set(String url, String content) {
        set(url, content.getBytes());
    }
}
