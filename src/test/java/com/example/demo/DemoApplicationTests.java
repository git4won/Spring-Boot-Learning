package com.example.demo;

import com.example.demo.service.BlogProperties;
import com.example.demo.web.HelloController;
import com.example.demo.web.UserController;
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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
	private MockMvc userMvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
		userMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
	}

	@Test
	public void getHello() throws Exception {
		mvc.perform(get("/hello").accept(MediaType.APPLICATION_JSON))
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

	@Test
	public void testUserController() throws Exception {
		RequestBuilder request = null;

		// 1. get 查一下 user 列表，应该为空
		request = get("/users/");
		userMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("[]")));

		// 2. post 提交一个 user
		request = post("/users/")
				.param("id", "1")
				.param("name", "hero")
				.param("age", "20");
		userMvc.perform(request)
				.andExpect(content().string(equalTo("success")));

		// 3. get 获取 user 列表，应该要有刚才插入的数据
		request = get("/users/");
		userMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"hero\",\"age\":20}]")));

		// 4. put 修改 id 为 1 的 user
		request = put("/users/1")
				.param("name", "HERO")
				.param("age", "30");
		userMvc.perform(request)
				.andExpect(content().string(equalTo("success")));

		// 5. get 一个 id 为 1 的 user
		request = get("/users/1");
		userMvc.perform(request)
				.andExpect(content().string(equalTo("{\"id\":1,\"name\":\"HERO\",\"age\":30}")));

		// 6. del 删除 id 为 1 的 user
		request = delete("/users/1");
		userMvc.perform(request)
				.andExpect(content().string(equalTo("success")));

		// 7. get 查一下 user 列表，应该为空
		request = get("/users/");
		userMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("[]")));
	}

}
