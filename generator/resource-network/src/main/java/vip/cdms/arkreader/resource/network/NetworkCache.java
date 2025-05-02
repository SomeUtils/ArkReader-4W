package vip.cdms.arkreader.resource.network;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import java.io.*;

@RequiredArgsConstructor
public class NetworkCache {
    private final File root;

    public File getFile(String url) {
        val name = Integer.toHexString(url.hashCode());
        return new File(root, name);
    }

    public byte[] get(String url) {
        val file = getFile(url);
        if (!file.exists()) return null;

        try (val stream = new FileInputStream(file);
             val buffer = new ByteArrayOutputStream()) {
            val data = new byte[8192]; // 8 KB
            int bytesRead;
            while ((bytesRead = stream.read(data)) != -1)
                buffer.write(data, 0, bytesRead);
            return buffer.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    @SneakyThrows
    public void set(String url, byte[] content) {
        val file = getFile(url);
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs();
        val stream = new FileOutputStream(file);
        stream.write(content);
        stream.close();
    }

    public String getAsString(String url) {
        val cached = get(url);
        return cached == null ? null : new String(cached);
    }

    public void set(String url, String content) {
        set(url, content.getBytes());
    }
}
