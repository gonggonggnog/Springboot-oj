package com.example.demo.utils;

import com.example.demo.common.SshClient;
import com.example.demo.entity.TestCase;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.Frame;
import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public interface sandboxUtil {
    //0-未知错误，1-答案正确，2-答案错误，3-时间超限
    static Integer Run(List<TestCase> testCases, Integer maxTime) throws JSchException, IOException {
        ChannelSftp channelSftp = SshClient.SftpInit();
        ChannelShell channelShell = SshClient.ShellInit();
        OutputStream outputStream = channelShell.getOutputStream();
        channelShell.connect(50000);
        for (TestCase testCase : testCases) {
            try {
                //推送输入文件到服务器
                String input = testCase.getInput().trim();
                channelSftp.put(IOUtils.toInputStream(input), "/root/oj/main/input.txt");
                Thread.sleep(2000);
                //执行程序
                outputStream.write("bash /root/run_docker.sh\n".getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                Thread.sleep(2000);
                //获取并对比运行结果
                InputStream output = channelSftp.get("/root/oj/main/output.txt");
                InputStream timeStream = channelSftp.get("/root/oj/main/time.txt");
                String result = IOUtils.toString(output);
                String timeString = IOUtils.toString(timeStream).trim();
                if (timeString.equals(""))
                    return 3;
                float time = Float.parseFloat(timeString);
                if (time >= maxTime)
                    return 3;
                if (!matchUtils.Match(result, testCase.getOutput()))
                    return 2;
                //删除文件
                outputStream.write("bash /root/rmFile.sh\n".getBytes());
                outputStream.flush();
                Thread.sleep(2000);

            } catch (SftpException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        channelSftp.disconnect();
        return 1;
    }
}
