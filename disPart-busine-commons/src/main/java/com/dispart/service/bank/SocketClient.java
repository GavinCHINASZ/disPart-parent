package com.dispart.service.bank;

import com.dispart.vo.BankNacosConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

@Service("socketClient")
public class SocketClient {

    private final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    @Autowired
    private BankNacosConfig config;

    public String send(String reqXml) {

        logger.debug("发送的请求报文是:{}", reqXml);

        logger.info("获取服务器地址:{},端口:{}", config.getIp(), config.getPort());

        try {
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(config.getIp(), config.getPort());
            socket.connect(socketAddress, 5000);
            socket.setSoTimeout(config.getTimeout());

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(reqXml.getBytes(config.getCharset()));
            outputStream.flush();
            socket.shutdownOutput();

            InputStream inputStream = socket.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] tmp = new byte[1024];

            int len;
            while((len = inputStream.read(tmp)) >= 0) {
                baos.write(tmp,0, len);
            }

            String respXml = baos.toString(config.getCharset());

            logger.debug("接收到网银的响应报文:{}", respXml);

            return respXml;

        } catch (Exception e) {
            logger.error("发送网银交易出现异常, 发送的请求报文：{}", reqXml, e);
            throw new RuntimeException("发送网银交易出现异常");
        }

    }
}
