package com.taskmanager.server.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.File

@Document("TaskItem")
data class Task(

    @Id
    val id: String,

    var title: String,
    var timeStamp: String,
    var contents: Contents,
)

data class Contents(
    var textEntries: List<String>,
    var imageAttachments: List<PersistedImage>?,
    var filesAttachments: List<File>?,
)

data class PersistedImage(
    var bytes: ByteArray? = null,
    var mime: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersistedImage

        if (!bytes.contentEquals(other.bytes)) return false
        if (mime != other.mime) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bytes?.contentHashCode() ?: 0
        result = 31 * result + mime.hashCode()
        return result
    }
}