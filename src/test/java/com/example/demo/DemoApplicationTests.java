package com.example.demo;

import com.example.demo.service.BlogProperties;
import com.example.demo.web.HelloController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	private static final Log log = LogFactory.getLog(DemoApplicationTests.class);

	@Test
	public void contextLoads() {
	}

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
	}

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("Hello World")));
	}

	@Autowired
	private BlogProperties blogProperties;

	@Test
	public void testBlog() throws Exception {
		Assert.assertEquals("coder", blogProperties.getName());
		Assert.assertEquals("Spring Boot 样例", blogProperties.getTitle());
		Assert.assertEquals("coder 正在写 《Spring Boot 样例》", blogProperties.getDesc());

		log.info("随机数测试输出：");
		log.info("随机字符串：" + blogProperties.getValue());
		log.info("随机int：" + blogProperties.getNumber());
		log.info("随机10以下 : " + blogProperties.getTest1());
		log.info("随机10-20 : " + blogProperties.getTest2());
	}

}
