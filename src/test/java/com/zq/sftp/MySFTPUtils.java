package com.zq.sftp;

import cn.hutool.core.util.StrUtil;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:
 * @Author: zhangqi
 * @CreateTime: 2020/7/239:22
 * @Company: MGL    原文链接：https://blog.csdn.net/m0_37911860/java/article/details/88381888
 */
public class MySFTPUtils {
    /**
     * SFTP工具类
     *
     * @author Kimi Kong
     * @version 0.1 2019年 02月 20日 14:40 Kimi Kong Exp $
     */
    private static final Logger log = LoggerFactory.getLogger(MySFTPUtils.class);

    private ChannelSftp sftp = null;

    private String remotePath = "";

    /**
     * 连接服务器
     *
     * @return void
     * @throws
     * @Title: connect
     * @author Kimi Kong
     * @date 2016年8月26日下午3:43:48
     */
    public boolean connect(String host, String username, String password, String port) {
        boolean result = true;
        try {
            if (sftp != null) {
                log.info("sftp is not null");
            }
            JSch jsch = new JSch();
            jsch.getSession(username, host, Integer.parseInt(port));
            Session sshSession = jsch.getSession(username, host, Integer.parseInt(port));
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.info("SFTP连接服务器成功！");
        } catch (Exception e) {
            result = false;
            log.info("SFTP连接服务器失败！", e);
        }
        return result;
    }

    /**
     * 断开
     *
     * @return void
     * @throws
     * @Title: disconnect
     * @author Kimi Kong
     * @date 2016年8月26日下午3:44:15
     */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
            } else if (this.sftp.isClosed()) {
                log.info("sftp is closed already");
            }
        }
    }

    /**
     * 下载文件
     *
     * @param directory
     * @param downloadFile
     * @param saveFile
     * @param sftp
     * @return void
     * @throws
     * @Title: download
     * @author Kimi Kong
     * @date 2016年8月26日下午3:45:37
     */
    public void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            log.info("SFTPUtil.download异常", e);
        }
    }

    /**
     * 上传文件
     *
     * @param localFile
     * @return boolean
     * @throws
     * @Title: upload
     * @author Kimi Kong
     * @date 2016年8月26日下午4:19:24
     */
    public boolean upload(InputStream localFile, String fileName) {
        boolean flag = false;
        String remotePath = this.remotePath;
        try {
            if (StrUtil.isNotBlank(remotePath)) {
                createDir(remotePath, sftp);
                log.info("在SFTP服务器创建文件路径【" + remotePath + "】成功");
            }
        } catch (Exception e) {
            log.info("在SFTP服务器创建文件路径失败" + remotePath);
            return false;
        }
        try {
            this.sftp.put(localFile, fileName);
            flag = true;
            log.info("上传文件到SFTP服务器【" + fileName + "】成功");
        } catch (SftpException e) {
            log.info("上传文件到SFTP服务器失败");
        }
        return flag;
    }

    /**
     * 创建远程路径
     *
     * @param filepath
     * @param sftp
     * @return void
     * @throws
     * @Title: createDir
     * @author Kimi Kong
     * @date 2016年8月26日下午3:52:10
     */
    public void createDir(String filepath, ChannelSftp sftp) {
        boolean bcreated;
        boolean bparent;
        File file = new File(filepath);
        String path = file.getParent().replace("\\", "/");
        try {
            this.sftp.cd(path);
            bparent = true;
        } catch (SftpException e1) {
            bparent = false;
        }
        try {
            if (bparent) {
                try {
                    this.sftp.cd(filepath);
                    bcreated = true;
                } catch (Exception e) {
                    bcreated = false;
                }
                if (!bcreated) {
                    this.sftp.mkdir(filepath);
                    bcreated = true;
                }
            } else {
                createDir(path, sftp);
                this.sftp.cd(path);
                this.sftp.mkdir(filepath);
            }
        } catch (SftpException e) {
            log.info("mkdir failed :" + filepath);
            log.info("SFTPUtil.createDir异常", e);
        }
        try {
            this.sftp.cd(filepath);
        } catch (SftpException e) {
            log.info("SFTPUtil.createDir异常", e);
            log.info("can not cd into :" + filepath);
        }
    }

    public ChannelSftp getSftp() {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public void del(String filepath) throws IOException {
        File f = new File(filepath);//定义文件路径
        if (f.exists() && f.isDirectory()) {//判断是文件还是目录
            if (f.listFiles().length == 0) {//若目录下没有文件则直接删除
                f.delete();
            } else {//若有则把文件放进数组，并判断是否有下级目录
                File delFile[] = f.listFiles();
                int i = f.listFiles().length;
                for (int j = 0; j < i; j++) {
                    if (delFile[j].isDirectory()) {
                        del(delFile[j].getAbsolutePath());//递归调用del方法并取得子目录路径
                    }
                    delFile[j].delete();//删除文件
                }
            }
        } else if (f.exists() && !f.isDirectory()) {
            f.delete();
        }
    }
}