package edu.nmsu.nmamp.student.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan("edu.nmsu.nmamp.student")
public class WebMvcConfig extends WebMvcConfigurerAdapter implements WebMvcConfigurer {

	private static final Charset UTF8 = Charset.forName("UTF-8");

	// Config UTF-8 Encoding.
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", UTF8)));
		converters.add(stringConverter);

		// Add other converters ...
	}

	// Static Resource Config
	// equivalents for <mvc:resources/> tags
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
	}

	// Equivalent for <mvc:default-servlet-handler/> tag
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setCacheable(false);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}

	@Bean
	@Description("Thymeleaf Template Engine")
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		// templateEngine.setTemplateEngineMessageSource(messageSource());
		return templateEngine;
	}

	@Bean
	@Description("Thymeleaf View Resolver")
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setOrder(1);
		return viewResolver;
	}

}