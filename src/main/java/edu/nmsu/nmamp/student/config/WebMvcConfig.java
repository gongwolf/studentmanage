package edu.nmsu.nmamp.student.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.support.TransactionTemplate;
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

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan("edu.nmsu.nmamp.student")
@PropertySource("classpath:database-cfg.properties")
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

	@Bean(name = "dataSource")
	public DriverManagerDataSource getDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		driverManagerDataSource.setUrl("jdbc:mysql://128.123.63.83:3306/nmamp_student?autoReconnect=true&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;useSSL=false");
		driverManagerDataSource.setUsername("root");
		driverManagerDataSource.setPassword("1987114gqx");
		return driverManagerDataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() throws NamingException {
		return new JdbcTemplate(getDataSource());
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager getTransactionManager() throws NamingException {
		return new DataSourceTransactionManager(getDataSource());
	}

	@Bean(name = "transactionTemplate")
	public TransactionTemplate getTransactionTemplate() throws NamingException {
		TransactionTemplate transactionTemplate = new TransactionTemplate(getTransactionManager());
		return transactionTemplate;
	}

	@Bean(name = "objectMapper")
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	// @Autowired
	// private Environment env;
	//
	// @Bean(name = "dataSource")
	// public DataSource getDataSource() throws NamingException {
	// System.out.println(env.getProperty("ds.database-driver"));
	// System.out.println(env.getProperty("ds.url"));
	// System.out.println(env.getProperty("ds.username"));
	// System.out.println(env.getProperty("ds.password"));
	//
	// JndiTemplate jndiTemplate = new JndiTemplate();
	// try {
	// DataSource dataSource = (DataSource) jndiTemplate
	// .lookup("java:comp/env/jdbc/" + env.getProperty("mysql.dbname"));
	// return dataSource;
	// } catch (Exception e) {
	// System.err.println("read data source error");
	// }
	// return null;
	// }

	/**********************************
	 * configuration of Thymeleaf
	 **********************************/

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