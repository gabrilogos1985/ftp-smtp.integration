package mx.uaq.facturacion.enlace.system;

/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

/**
 * Starts the Spring Context and will initialize the Spring Integration routes.
 *
 * @author Gunnar Hillert
 * @since 2.2
 *
 */
@Configuration
public class EmailSystemTest {

	public static final Logger LOGGER = Logger.getLogger(EmailSystemTest.class);

	public static final String HORIZONTAL_LINE = "\n=========================================================";

	public EmailSystemTest() {
	}

	/**
	 * Load the Spring Integration Application Context
	 *
	 * @param args
	 *            - command line arguments
	 */
	public static void main(final String... args) {

		LOGGER.info(HORIZONTAL_LINE + "\n"
				+ "\n          Welcome to Spring Integration!                 "
				+ "\n"
				+ "\n    For more information please visit:                   "
				+ "\n    http://www.springsource.org/spring-integration       "
				+ "\n" + HORIZONTAL_LINE);

		final AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:email-facturas.poller.xml",
				"classpath:facturacion-integration.xml");

		context.registerShutdownHook();

		final Scanner scanner = new Scanner(System.in);

		LOGGER.info(HORIZONTAL_LINE + "\n"
				+ "\n    Please press 'q + Enter' to quit the application.    "
				+ "\n" + HORIZONTAL_LINE);

		while (true) {

			final String input = scanner.nextLine();

			if ("q".equals(input.trim())) {
				break;
			}

		}

		LOGGER.info("Exiting application...bye.");

		System.exit(0);
	}

	@Bean(name = "uploadFacturaFtpChannel", autowire = Autowire.BY_NAME)
	public MessageChannel getObject() {
		return new DirectChannel();
	}

	@Bean(name = "facturaIdChannel", autowire = Autowire.BY_NAME)
	public MessageChannel getFacturaIdChannel() {
		return loggerMessages("FACTURAS_RECIBIDAS >>>");
	}

	@Bean(name = "discardEmailsChannel", autowire = Autowire.BY_NAME)
	public MessageChannel getDiscardEmailChannel() {
		return loggerMessages("D I S C A R D >");
	}

	private DirectChannel loggerMessages(String channel) {
		DirectChannel directChannel = new DirectChannel();
		directChannel.subscribe((message) -> {
			System.out.println(channel + ": " + message.getPayload());
		});
		return directChannel;
	}
}
