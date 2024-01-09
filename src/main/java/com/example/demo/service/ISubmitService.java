package com.example.demo.service;

import com.example.demo.common.Result;
import com.example.demo.entity.ProblemsBasic;
import com.example.demo.utils.SshUtil;
import com.example.demo.utils.sandboxUtil;
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
@Service
public interface ISubmitService {


    Result submit(String token, String problemIdentity, String code);
}
