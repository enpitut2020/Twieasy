package com.example.twieasy

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * 暗号化・複合化ユーティリティクラス
 *
 */
class EncryptionWrapper{
    companion object EncryptionUtils {
        val HEX = "0123456789ABCDEF"
        /**
         * AES128暗号化
         * @param key キー
         * @param value 暗号化する文字列
         * @return 暗号化された文字列
         */
        fun encryptAES128(key: String, value: String): String? {
            try {
                val rawKey = getRawKey(key.toByteArray(charset("UTF-8")))
                val secretKey = SecretKeySpec(rawKey, "AES")
                val c = Cipher.getInstance("AES")
                c.init(Cipher.ENCRYPT_MODE, secretKey)
                val encryptedString = c.doFinal(value.toByteArray(charset("UTF-8")))
                return toHex(encryptedString)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * AES128複合化
         * @param key キー
         * @param value 複合化する文字列
         * @return 複合化された文字列
         * @throws RewardUdidException
         */
        fun decryptAES128(key: String, value: String): String? {
            try {
                val rawKey = getRawKey(key.toByteArray(charset("UTF-8")))
                val byteValue = toByte(value)
                val secretKey = SecretKeySpec(rawKey, "AES")
                val c = Cipher.getInstance("AES")
                c.init(Cipher.DECRYPT_MODE, secretKey)
                val decryptedString = c.doFinal(byteValue)
                return String(decryptedString)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * 指定したバイト配列を16バイトで取得する
         * @param key バイト配列
         * @return 16バイトのバイト配列
         * @throws Exception
         */
        fun getRawKey(key: ByteArray): ByteArray {
            val raw = ByteArray(16)
            for (i in raw.indices) {
                if (key.size >= i + 1) {
                    raw[i] = key[i]
                } else {
                    raw[i] = 0
                }
            }
            return raw
        }

        /**
         * 16進文字列をバイト配列に変換する
         * @param hexString 文字列
         * @return バイト配列
         */
        fun toByte(hexString: String): ByteArray {
            val len = hexString.length / 2
            val result = ByteArray(len)
            for (i in 0 until len) {
                result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).toByte()
            }
            return result
        }

        /**
         * 指定したバイト配列を16進文字列に変換する
         * @param buf バイト配列
         * @return 文字列
         */
        fun toHex(buf: ByteArray?): String {
            if (buf == null) {
                return ""
            }
            val result = StringBuffer(2 * buf.size)
            for (i in buf.indices) {
                appendHex(result, buf[i])
            }
            return result.toString()
        }

        /**
         * 指定したバイトを文字列として追加します
         * @param sb 追加先の文字列バッファ
         * @param b バイト
         */
        fun appendHex(sb: StringBuffer, b: Byte) {
            sb.append(HEX[(b.toInt() shr 4) and 0x0f]).append(HEX[b.toInt() and 0x0f])
        }
    }
}

