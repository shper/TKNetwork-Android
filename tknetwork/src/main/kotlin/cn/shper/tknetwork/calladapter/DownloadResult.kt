package cn.shper.tknetwork.calladapter

import okhttp3.ResponseBody
import java.io.*

/**
 * Author: Shper
 * Email: me@shper.cn
 * Version: V0.1 2022/4/25
 */
class DownloadResult {

    private var startAction: (() -> Unit)? = null
    private var progressAction: ((currentPercent: Long, currentSize: Long, totalSize: Long) -> Unit)? =
        null
    private var finishAction: ((filePath: String) -> Unit)? = null
    private var failureAction: ((code: Int?, message: String?, throwable: Throwable?) -> Unit)? =
        null
    private var saveFile: File? = null

    fun save(file: File): DownloadResult {
        this.saveFile = file

        return this
    }

    fun onStart(action: () -> Unit): DownloadResult {
        this.startAction = action

        return this
    }

    fun onProgress(action: (currentPercent: Long,
                            currentSize: Long,
                            totalSize: Long) -> Unit): DownloadResult {
        this.progressAction = action

        return this
    }

    fun onFinish(action: (filePath: String) -> Unit): DownloadResult {
        this.finishAction = action

        return this
    }

    fun onFailure(action: (code: Int?,
                           message: String?,
                           throwable: Throwable?) -> Unit): DownloadResult {
        this.failureAction = action

        return this
    }

    internal fun callStart() {
        this.startAction?.invoke()
    }

    internal fun callSuccess(body: ResponseBody) {
        if (this.saveFile == null) {
            callFailure(-1, "The save file is invalid.")
            return
        }

        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            val totalSize = body.contentLength()
            var fileSizeDownloaded: Long = 0
            val fileBytes = ByteArray(2048)

            inputStream = body.byteStream()
            outputStream = FileOutputStream(this.saveFile)

            while (true) {
                val readSize: Int = inputStream.read(fileBytes)
                if (readSize == -1) {
                    break
                }
                outputStream.write(fileBytes, 0, readSize)

                fileSizeDownloaded += readSize.toLong()
                this.progressAction?.invoke((100 * fileSizeDownloaded / totalSize),
                                            fileSizeDownloaded,
                                            totalSize)
            }
            outputStream.flush()

            this.finishAction?.invoke(this.saveFile?.absolutePath ?: "")
        } catch (e: IOException) {
            callFailure(throwable = Throwable(e.message, e.cause))
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
    }

    internal fun callFailure(code: Int? = null,
                             message: String? = null,
                             throwable: Throwable? = null) {
        this.failureAction?.invoke(code, message, throwable)
    }

}