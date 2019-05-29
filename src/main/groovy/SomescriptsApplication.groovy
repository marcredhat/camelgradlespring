package com.codergists.somescripts
import java.io.File
import java.io.FileOutputStream
import java.util.Properties
import com.sap.conn.idoc.*
import com.sap.conn.jco.*
import org.springframework.boot.SpringApplication
import org.apache.camel.builder.RouteBuilder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.apache.camel.component.servlet.CamelHttpTransportServlet
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.apache.camel.model.rest.RestBindingMode
import org.apache.camel.LoggingLevel
import org.apache.camel.Exchange
import org.apache.camel.Processor
import org.springframework.context.ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext
import com.codergists.somescripts.HelloWorldService
import com.codergists.somescripts.HelloWorldServiceImpl


@SpringBootApplication
class SomescriptsApplication extends RouteBuilder { 

	static void main(String[] args) {
		SpringApplication.run(SomescriptsApplication, args)
	}

	@Bean
	public ServletRegistrationBean camelServletRegistrationBean() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new CamelHttpTransportServlet(), "/camel/*");
		registration.setName("CamelServlet");
		return registration;
	}

	@Override
    	public void configure() throws Exception {
                 ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
                 System.out.println(context.getBean("helloWorldService").sayHello());


		restConfiguration()
		.component("servlet")
		.bindingMode(RestBindingMode.json);

		rest().get("/hello")
		.to("direct:hello");
 

		from("direct:hello")
		.log(LoggingLevel.INFO, "Hello World HTTP ENDPOINT")
                 .log(LoggingLevel.INFO, context.getBean("helloWorldService").sayHello())
		.process(new Processor() {
                	@Override
                	void process(Exchange exchange) throws Exception {
		                def jsonMap = [marchello:"world1"]
                    		exchange.in.body = jsonMap
                                println(context.getBean("helloWorldService").sayHello())
                                println("Exchange body is:" +  exchange.in.body)
			}
		})
                

		 rest().get("/test")
                .to("direct:test");
                from('direct:test')
      .process { Exchange exchange -> println (exchange.in.body + "marctest") }
      .process { println (it.in.body + "marctest") } // equivalent

        from("timer:hello?period=5000").log('Marc: hello world timer fired ${exchangeProperty[CamelTimerCounter]}')
    }

}
