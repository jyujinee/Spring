package com.hello.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hello.beans.interceptors.CheckSessionInterceptor;

//@Configurable // Bean을 매번 생성하고 쓰임을 다하면 사라진다 (Prototype scope)
@Configuration // Bean을 한번만 생성한다. 대부분 configuration을 사용한다. (Singletone scope)
@EnableWebMvc // Spring Validator를 사용할 준비를 한다.
public class WebConfig implements WebMvcConfigurer{ 
	
	/**
    * View Resolver 설정
    */
   @Override
   public void configureViewResolvers(ViewResolverRegistry registry) {
      registry.jsp("/WEB-INF/views/",".jsp");
   }

   /**
    * Static Resource 설정.
    */
   // 우리가 css / js / img 가 있으면 static 에 넣어줬는데 
   // @Configuration가 생기면 그 경로 또한 막아버려서 그것 또한 static 에 접근하는경로를
   // 별도로 만들어준다.  우린 폴더를 만들어주지않았기 때문에 교재의 설정과는 다르게 설정해준다. 
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/js/**") // /js 시작하는 모든 것들은
       // 여기있는 위치의 파일을 가져가라 (파일명이라면 파일 명을 찾아서간다.)
       .addResourceLocations("classpath:/static/"); 
       
       registry.addResourceHandler("/css/**")
       .addResourceLocations("classpath:/static/");
   }
   
   /**
    * Interceptors를 추가
    */
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
	  registry.addInterceptor(new CheckSessionInterceptor())
	  		  .addPathPatterns("/**") // 모든 URL을 검사한다. (세션이 있는 것만)
	  		  .excludePathPatterns("/member/regist", 
	  				  				"/member/available",
	  				  				"/member/login",
	  				  				"/member/*-delete-me",
	  				  				"/board/list",
	  				  				"/css/**",
	  				  				"/js/**" ); // (세션 체크가 필요없는 URL은 제외한다.)
   }


}
