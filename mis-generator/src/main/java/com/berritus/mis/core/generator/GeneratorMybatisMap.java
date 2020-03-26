package com.berritus.mis.core.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright:
 * @Description:
 * @Author: Qin Guihe
 * @Create: 2020-01-16
 */
public class GeneratorMybatisMap {
	private static final Logger logger = LoggerFactory.getLogger(GeneratorMybatisMap.class);

	public void generator() throws Exception{

		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("F:\\spring-proj\\comproj\\mis-generator\\src\\main\\resources\\generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);

	}

	public static void main(String[] args) throws Exception {
		try {
			GeneratorMybatisMap generatorSqlmap = new GeneratorMybatisMap();
			generatorSqlmap.generator();
		} catch (Exception e) {
			logger.error("error ", e);
		}
	}
}
