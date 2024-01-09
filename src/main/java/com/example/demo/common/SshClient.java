package com.example.demo.common;

import com.jcraft.jsch.*;

public interface SshClient {
    static Session Init(){
        Session jschSession = null;
        try {
            JSch jsch = new JSch();
            jsch.setKnownHosts("");
            jschSession = jsch.getSession("root", "123.123.123.123", 22);
            jschSession.setConfig("StrictHostKeyChecking","no");
            jschSession.setPassword("123123123.");
            jschSession.connect(10000);
            return jschSession;
        } catch (JSchException e) {
            throw new RuntimeException(e);
        }
    }
    static ChannelSftp SftpInit() throws JSchException {
        Channel sftp = Init().openChannel("sftp");
        sftp.connect(5000);
        return  (ChannelSftp) sftp;
    }

    static ChannelExec ExecInit() throws JSchException {
        Channel exec = Init().openChannel("exec");

        return (ChannelExec) exec;
    }

    static ChannelShell ShellInit() throws JSchException {
        return (ChannelShell) Init().openChannel("shell");
    }
}
