//package com.hmdp;
//
//import com.hmdp.dto.Result;
//import com.hmdp.entity.User;
//import cn.hutool.core.thread.ThreadUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.hmdp.dto.LoginFormDTO;
//import com.hmdp.service.IUserService;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.util.Assert;
//import javax.annotation.Resource;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStreamWriter;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.stream.Collectors;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class VoucherOrderControllerTest {
//    @Resource
//    private MockMvc mockMvc;
//
//    @Resource
//    private IUserService userService;
//
//    @Resource
//    private ObjectMapper mapper;
//
//    @Test
//    @SneakyThrows
//    @DisplayName("登录1000个用户，并输出到文件中")
//    void login() {
//        List<String> phoneList = userService.lambdaQuery()
//                .select(User::getPhone)
//                .last("limit 1000")
//                .list().stream().map(User::getPhone).collect(Collectors.toList());
//        ExecutorService executorService = ThreadUtil.newExecutor(phoneList.size());
//        List<String> tokenList = new CopyOnWriteArrayList<>();
//        CountDownLatch countDownLatch = new CountDownLatch(phoneList.size());
//        phoneList.forEach(phone -> {
//            executorService.execute(() -> {
//                try {
//                    // 验证码
//                    String codeJson = mockMvc.perform(MockMvcRequestBuilders
//                                    .post("/user/code")
//                                    .queryParam("phone", phone))
//                            .andExpect(MockMvcResultMatchers.status().isOk())
//                            .andReturn().getResponse().getContentAsString();
//                    Result result = mapper.readerFor(Result.class).readValue(codeJson);
//                    Assert.isTrue(result.getSuccess(), String.format("获取“%s”手机号的验证码失败", phone));
//                    String code = result.getData().toString();
//                    LoginFormDTO formDTO = LoginFormDTO.builder().code(code).phone(phone).build();
//                    String json = mapper.writeValueAsString(formDTO);
//                    // token
//                    String tokenJson = mockMvc.perform(MockMvcRequestBuilders
//                                    .post("/user/login").content(json).contentType(MediaType.APPLICATION_JSON))
//                            .andExpect(MockMvcResultMatchers.status().isOk())
//                            .andReturn().getResponse().getContentAsString();
//                    result = mapper.readerFor(Result.class).readValue(tokenJson);
//                    Assert.isTrue(result.getSuccess(), String.format("获取“%s”手机号的token失败,json为“%s”", phone, json));
//                    String token = result.getData().toString();
//                    tokenList.add(token);
//                    countDownLatch.countDown();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        });
//        countDownLatch.await();
//        executorService.shutdown();
//        Assert.isTrue(tokenList.size() == phoneList.size());
//        writeToTxt(tokenList, "\\tokens.txt");
//        System.out.println("写入完成！");
//    }
//
//    private static void writeToTxt(List<String> list, String suffixPath) throws Exception {
//        // 1. 创建文件
//        File file;
//        file = new File("C:\\hm-shuoming\\tokens.txt");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        // 2. 输出
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
//        for (String content : list) {
//            bw.write(content);
//            bw.newLine();
//        }
//        bw.close();
//        System.out.println("写入完成！");
//    }
//}
