package com.example.cuiduo.wifitesting.Client

import com.example.cuiduo.wifitesting.Handler.HeartBeatHandler
import com.example.cuiduo.wifitesting.Handler.MessageHandler
import com.example.cuiduo.wifitesting.R
import com.example.cuiduo.wifitesting.UtilClass.Util
import com.example.cuiduo.wifitesting.codec.TextCodecFactory
import org.apache.mina.core.session.IdleStatus
import org.apache.mina.core.session.IoSession
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.filter.keepalive.KeepAliveFilter
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler
import org.apache.mina.filter.ssl.SslFilter
import org.apache.mina.transport.socket.nio.NioSocketConnector
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.net.InetSocketAddress
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

/**
 * Created by cuiduo on 2017/7/22.
 */
//客户端代码！
class Client(val macAddress:String) : Thread() {
    //    private val logger = LoggerFactory.getLogger(javaClass.simpleName)
//    public val macAddress = Util.getRandomMacAddress()
    //延迟加载(Lazy) lazy()是一个函数, 接受一个Lambda表达式作为参数, 返回一个Lazy类型的实例,这个实例可以作为一个委托, 实现延迟加载属性(lazy property): 第一次调用 get() 时, 将会执行 lazy() 函数受到的Lambda 表达式,然后会记住这次执行的结果, 以后所有对 get() 的调用都只会简单地返回以前记住的结果.
    private val connector: NioSocketConnector by lazy { NioSocketConnector() }
    private val clientListener: ClientListener by lazy { ClientListener() }
    private val codeFilter: ProtocolCodecFilter by lazy { ProtocolCodecFilter(TextCodecFactory()) }
    private val heartFilter: KeepAliveFilter by lazy {
        val keepAliveFilter = KeepAliveFilter(HeartBeatHandler(macAddress), IdleStatus.BOTH_IDLE, KeepAliveRequestTimeoutHandler.CLOSE)
        keepAliveFilter.isForwardEvent = true
        keepAliveFilter.requestTimeout = 60
        keepAliveFilter.requestInterval = 30
        return@lazy keepAliveFilter
    }

    private val sslContext: SSLContext by lazy { SSLContext.getInstance(Util.CLIENT_AGREEMENT) }

    private val sslFilter: SslFilter by lazy {
        val file: File = File("/Users/cuiduo/purifierdevice.keystore")
        var str: String = R.raw.purifierdevice.toString()
        val stringStream2: InputStream = ByteArrayInputStream(R.raw.purifierdevice.toString().toByteArray())
        val stringStream = file.inputStream()
        val ks: KeyStore = KeyStore.getInstance("JKS")
        ks.load(stringStream, "123321".toCharArray())
        val trustManager: TrustManagerFactory = TrustManagerFactory.getInstance("SunX509")
        trustManager.init(ks)
        sslContext.init(null, trustManager.trustManagers, null)
        SslFilter(sslContext).apply { isUseClientMode = true }
    }
    private var session: IoSession? = null


    override fun run() {
        super.run()
        connector.run {
            //123开头的是测试环境的
            System.out.println("123" + InetSocketAddress("123.56.157.215", 2001).hostName)
            //101开头的是商用环境的
            //setDefaultRemoteAddress(InetSocketAddress("101.200.30.189", 2001))
            setDefaultRemoteAddress(InetSocketAddress("123.56.157.215", 2001))
            handler = MessageHandler(macAddress)
            addListener(clientListener)
        }
        val filterChain = connector.filterChain
        filterChain.addLast("sslFilter", sslFilter)
        filterChain.addLast("codec", codeFilter)
        filterChain.addLast("heart", heartFilter)

        createSession()
    }

    private fun createSession() {

        println("macAddress = $macAddress")
        if (isSessionAvailable()) {
            System.out.println("createSession : session is already available")
        }
        try {
            val future = connector.connect()
            //Wait for the connection attempt to be finished.// 等待连接创建完成
            future.awaitUninterruptibly()
            // 获得session
            session = future.session
            System.out.println(session.toString())
        } catch (e: Exception) {
            System.out.println("createSession : ${e.printStackTrace()}")
        }
    }

    fun isSessionAvailable(): Boolean = !(session == null || !session!!.isConnected || session!!.isClosing)
   /* fun sendMessage(request: String) {
        if (!isSessionAvailable()) {
            System.out.println("sendMessage : session is not available")
        }
        try {
            session?.write(request)
        } catch (e: Exception) {
            System.out.println("sendMessage : ${e.printStackTrace()}")
        }
    }*/

}