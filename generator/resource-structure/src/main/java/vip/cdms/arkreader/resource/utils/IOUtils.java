package vip.cdms.arkreader.resource.utils;

import lombok.val;

import java.io.*;

public class IOUtils {
    public static byte[] readAllOrNull(InputStream in) {
        try {
            return readAll(in);
        } catch (IOException e) {
            return null;
        }
    }

    public static byte[] readAll(InputStream in) throws IOException {
        val buffer = new ByteArrayOutputStream();
        val chunk = new byte[8192]; // 8 KB
        int bytesRead;
        while ((bytesRead = in.read(chunk)) != -1)
            buffer.write(chunk, 0, bytesRead);
        val bytes = buffer.toByteArray();
        in.close();
        buffer.close();
        return bytes;
    }

    public static void writeToFile(byte[] bytes, File file) throws IOException {
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs();
        val stream = new FileOutputStream(file);
        stream.write(bytes);
        stream.close();
    }
}
