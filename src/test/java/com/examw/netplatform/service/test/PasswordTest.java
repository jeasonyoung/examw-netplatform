package com.examw.netplatform.service.test;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.junit.Test;
 

public class PasswordTest {
	ObjectMapper mapper = new ObjectMapper();
	
	{
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
	}
	
	
	@Test
	public void pwdTest() throws JsonGenerationException, JsonMappingException, IOException{
		
//		User user = new User();
//		UserInfo info = new UserInfo();
//		info.setAccount("test哦");
//		info.setPassword("1234你好567890yangyong1231");
//		
//		System.out.println("加密前：" +  mapper.writeValueAsString(info));
//		
//		 PasswordAESUtils.encryptPassword(info, user);
//		
//		System.out.println("加密后：" +  mapper.writeValueAsString(user));
//		
//		user.setAccount(info.getAccount());
//		
//		System.out.println("解密前：" +  mapper.writeValueAsString(user));
//	  PasswordAESUtils.decryptPassword(user, info);
//		
//		System.out.println("解密后：" +  mapper.writeValueAsString(info));
	}
}