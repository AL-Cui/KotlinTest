package com.example.cuiduo.wifitesting.Handler

import com.example.cuiduo.wifitesting.MachineControl.Binding
import com.example.cuiduo.wifitesting.MachineControl.ControlClass
import com.example.cuiduo.wifitesting.MachineControl.UpdateStatus
import com.example.cuiduo.wifitesting.UtilClass.Util
import org.apache.mina.core.service.IoHandlerAdapter
import org.apache.mina.core.session.IoSession
import org.dom4j.Document
import org.dom4j.DocumentHelper
import org.dom4j.Element


/**
 * Created by cuiduo on 2017/7/22.
 */
//此类是用以处理消息的也可说是个消息处理器，当客户端有消息传给server端的时候，
//或者server端传递给Client端的时候（Client端也会有个消息处理器）都会通过消
// 息处理器进行处理。
class MessageHandler(val macAddress: String) : IoHandlerAdapter() {
    //对于非空类型的属性是必须初始化的。如果我们希望延迟进行初始化，就可以使用lateinit关键字了。
    lateinit var arrayResult: Array<String>
    var boxId = ""
    var passString = ""
    // 当一个新客户端连接后触发此方法.
    override fun sessionCreated(session: IoSession?) {
        super.sessionCreated(session)
        System.out.println("sessionCreated（新客户端连接）")
    }

    override fun exceptionCaught(session: IoSession?, cause: Throwable) {
//        super.exceptionCaught(session, cause)
        System.out.println("exceptionCaught: ${cause.printStackTrace()}")
    }

    // 当客户端发送的消息到达时:
    override fun messageReceived(session: IoSession?, message: Any?) {
//        super.messageReceived(session, message
        try {
            // 消息内容
            val text = message.toString()
            // 获取服务端发过来的消息内容
            System.out.println("received message $text")
            if (Util.isNotBlank(text)) {
                // 解析XML文件
                readXML(text, session)
            } else {
                // 返回错误信息
                session?.write("error heartbeat")
            }
        } catch (e: Exception) {
            System.out.println(e.printStackTrace().toString())
        }

    }

    // 当信息已经传送给客户端后触发此方法.
    override fun messageSent(session: IoSession?, message: Any?) {
//        super.messageSent(session, message)
        val msg = message.toString()
        System.out.println("sent message $msg")
    }

    override fun sessionClosed(session: IoSession?) {
        super.sessionClosed(session)
    }

    fun readXML(contents: String, session: IoSession?) {
        try {
            var readDoc: Document = DocumentHelper.parseText(contents)
            val rootNode: Element = readDoc.rootElement
            if (Util.NODE_TCP_MSG.equals(rootNode.name)) {
                if (Util.MSG_VALUE_HEARTBEATRES.equals(rootNode.elementTextTrim(Util.NODE_MSG))) {   //收到心跳包回复，什么也不做

                } else if (Util.MSG_VALUE_CTRLNOTIFY.equals(rootNode.elementTextTrim(Util.NODE_MSG))) {
                    //收到服务器发过来的指令
                    println("收到的服务器指令 = ${rootNode.elementTextTrim(Util.NODE_CMD)}")
                    when (rootNode.elementTextTrim(Util.NODE_CMD)) {
                        Util.CMD_VALUE -> {
                            println("收到绑定指令")
                            //绑定指令，取得boxID，pass
                            arrayResult = Binding().boxIDCreateFirstRequest(macAddress)
                            boxId = arrayResult[0]
                            passString = arrayResult[1]
                            //上报状态
//                            UpdateStatus.CreateRequest(macAddress, boxId, passString)
                        }
                        Util.CMD_VALUE_BOXDELETE -> {
                            println("收到解绑指令")
                            //删除boxid,待完善
                        }
                        Util.CMD_VALUE_CHECKCMD -> {
                            println("收到控制指令")
                            //去HMS去控制指令，并上报结果，再把状态给回去,待完善
                            ControlClass.CreateRequest(macAddress,boxId,passString)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            System.out.println(e.toString())
        }
    }
}