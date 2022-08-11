package com.bjpowernode.front;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

@SpringBootTest
class MicrWebApplicationTests {

	@Test
	void contextLoads() {
		boolean flag = false;

			flag = Pattern.matches("^1[1-9]\\d{9}$", "13700000000");

		System.out.println(flag);

	}

}
