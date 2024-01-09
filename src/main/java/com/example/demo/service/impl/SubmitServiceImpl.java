package com.example.demo.service.impl;

import com.example.demo.common.Result;
import com.example.demo.entity.ProblemsBasic;
import com.example.demo.service.AscnyMethod;
import com.example.demo.service.IProblemsBasicService;
import com.example.demo.service.ISubmitService;
import com.example.demo.utils.SshUtil;
import com.example.demo.utils.sandboxUtil;
import com.example.demo.utils.tokenUtils;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@EnableAsync
@Service
public class SubmitServiceImpl implements ISubmitService {

    @Resource
    IProblemsBasicService problemsBasicService;

    @Resource
    AscnyMethod ascnyMethod;
    @Override
    public Result submit(String token, String problemIdentity, String code) {
        //获取用户信息
        String  userIdentity ="";
        try{
            userIdentity = (String) tokenUtils.parseToken(token).get("identity");
        }catch (Exception e){
            return Result.error("unauthorized Authorization");
        }
        try {
            //获取问题信息
            ProblemsBasic problemDetail = (ProblemsBasic) problemsBasicService.getProblemDetail(problemIdentity).getData();
            if(problemDetail==null)
                return Result.error("题目不存在");

            String identity = UUID.randomUUID().toString();
            Path submissionFolder = createSubmissionFolder(identity);
            writeCodeToFile(submissionFolder,code);
            String compileResult = compileJavaFile(submissionFolder);
            if(!compileResult.isEmpty()){
                return Result.error(STR."编译错误：\{compileResult}");
            }
            if (!SshUtil.UploadFile(STR."\{submissionFolder}/main.class"))
                return Result.error("上传文件失败");
            Integer status = sandboxUtil.Run(problemDetail.getTestCases(), problemDetail.getMaxRuntime());
            //提交记录和修改passNUm
            ascnyMethod.updateSubmitNum(identity, userIdentity, problemIdentity, status);
            return switch (status) {
                case 0 -> Result.error("未知错误");
                case 1 -> Result.success("答案正确");
                case 2 -> Result.error("答案错误");
                case 3 -> Result.error("时间超限");
                default -> Result.error("错误返回");
            };
        } catch (Exception e) {
            return Result.error(e.toString());
        }
    }
    private static Path createSubmissionFolder(String submissionIdentity) throws IOException {
        // 创建文件夹
        Path submissionFolder = Paths.get("./submissions", submissionIdentity);
        System.out.println(submissionFolder);
        Files.createDirectories(submissionFolder);
        return submissionFolder;
    }
    private static void writeCodeToFile(Path submissionFolder, String code) throws IOException {
        // 将 token 写入文件
        Path tokenFile = submissionFolder.resolve("main.java");
        Files.write(tokenFile, code.getBytes());
    }
    private static String compileJavaFile(Path javaFilePath) {
        try {

            // 构建javac命令
            ProcessBuilder processBuilder = new ProcessBuilder("javac","main.java");
            //设置工作目录
            processBuilder.directory(javaFilePath.toFile());
            // 启动进程并等待编译完成
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            // 检查编译结果
            if (exitCode == 0)
                return "";
            else
                return readInputStream(process.getErrorStream());
        } catch (IOException | InterruptedException e) {
            return e.toString();
        }
    }
    private static String readInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            reader.close();
            return result.toString();
        }
    }
}
