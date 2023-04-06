package com.abysscat.usercenter.dal;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import lombok.extern.slf4j.Slf4j;

/**
 * tk.mybatis.generate  生成器
 * 1、修改resources目录下的jdbc.properties，设置数据库连接配置
 * 2、修改resources目录下的generatorConfig.xml，设置要生成的表配置
 */
@Slf4j
public class TkMybatisGen {

    public static void main(String[] args) {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;
        ConfigurationParser cp = new ConfigurationParser(warnings);

        try {
            Configuration config =
                    cp.parseConfiguration(getResourceAsStream("generatorConfig.xml"));
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            log.error("generate error", e);
        }

        for (String warning : warnings) {
            System.out.println(warning);
        }
    }


    private static InputStream getResourceAsStream(String path) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }


}
