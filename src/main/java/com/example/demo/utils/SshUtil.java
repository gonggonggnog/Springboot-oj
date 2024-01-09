package com.example.demo.utils;

import com.example.demo.common.SshClient;
import com.jcraft.jsch.*;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public interface SshUtil {

    static Boolean UploadFile(String  file) throws JSchException {
        ChannelSftp channelSftp = SshClient.SftpInit();
        try {
            channelSftp.put(file,"/root/oj/main/main.class");
            channelSftp.disconnect();
            return true;
        } catch (SftpException e) {
            return false;
        }
    }


    static String runCommand(String cmd,String input) throws JSchException, IOException {
        try {
            ChannelExec channelExec = SshClient.ExecInit();
            channelExec.setCommand(cmd);
            channelExec.setPty(true); // 设置伪终端

            channelExec.connect(5000);
            if (!channelExec.isConnected())
                return "连接失败";

            //创建输入流
            OutputStream outputStream = channelExec.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();
            outputStream.close();

            //获取输出流
            String result = IOUtils.toString(channelExec.getInputStream());

            channelExec.disconnect();
            return result;
        } catch (JSchException | IOException e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
