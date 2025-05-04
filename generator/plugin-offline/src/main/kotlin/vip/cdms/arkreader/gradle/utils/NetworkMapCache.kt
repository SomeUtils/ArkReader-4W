package vip.cdms.arkreader.gradle.utils

import com.eclipsesource.json.Json
import com.eclipsesource.json.JsonObject
import vip.cdms.arkreader.resource.network.NetworkCache
import java.io.File
import java.lang.Integer.toHexString

class NetworkMapCache(root: File, private val imageRoot: File) : NetworkCache(root) {
    private val imageExtensions = listOf(
        "jpg",
        "jpeg",
        "png",
    )

    private val imageInnerMapping = mutableMapOf<String, String>()
    private val imageMapping = object : MutableMap<String, String> by imageInnerMapping {
        val file = root.resolve("image_mapping.json")

        init {
            if (file.exists()) for (member in Json.parse(file.readText()).asObject())
                this[member.name] = member.value.asString()
        }

        private fun save() {
            val jsonObject = JsonObject()
            for ((k, v) in this) jsonObject.add(k, v)
            file.writeText(jsonObject.toString())
        }

        override fun put(key: String, value: String) =
            imageInnerMapping.put(key, value).apply { save() }
    }

    fun getImageUrl(content: ByteArray) = imageMapping[content.getKey()]

    override fun getFile(url: String): File {
        if (!isImageUrl(url)) return super.getFile(url)
        return imageRoot.resolve(getFileName(url))
    }

    override fun set(url: String, content: ByteArray) {
        if (isImageUrl(url)) imageMapping[content.getKey()] = url
        super.set(url, content)
    }

    private fun ByteArray.getKey() = toHexString(contentHashCode())

    private fun isImageUrl(url: String) =
        imageExtensions.any { url.lowercase().endsWith(".$it") }
}
