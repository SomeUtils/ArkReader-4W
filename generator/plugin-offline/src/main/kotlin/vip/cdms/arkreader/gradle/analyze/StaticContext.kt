package vip.cdms.arkreader.gradle.analyze

import com.squareup.javapoet.ClassName
import vip.cdms.arkreader.gradle.utils.NetworkMapCache
import vip.cdms.arkreader.resource.network.Network
import java.io.File
import java.net.InetSocketAddress
import java.net.Proxy
import java.nio.file.Path

class StaticContext(
    val srcPath: Path,
    val assetsDir: File,
    val networkCacheDir: File
) {
    private val networkCache = NetworkMapCache(
        root = networkCacheDir,
        imageRoot = assetsDir
    )

    init {
        Network.cache = networkCache
//        Network.proxy = Proxy(Proxy.Type.SOCKS, InetSocketAddress("127.0.0.1", 7890))
    }

    fun getAssets(bytes: ByteArray?): String? {
        if (bytes == null) return null
        val url = networkCache.getImageUrl(bytes) ?: return null
        return networkCache.getFileName(url)
    }

    companion object {
        val EventImplClass: ClassName = ClassName.get("vip.cdms.arkreader.data.implement", "EventImpl")
        val EventStoryInfoImplClass: ClassName = ClassName.get("vip.cdms.arkreader.data.implement", "EventStoryInfoImpl")
    }
}
