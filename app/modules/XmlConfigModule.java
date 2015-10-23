package modules;

import org.mybatis.guice.XMLMyBatisModule;

/**
 * Guice bind interface to its implements and deposit to guice.
 *
 * <p>
 *
 * @author Howen Xiong
 */
public class XmlConfigModule extends XMLMyBatisModule {

		/**
		 * guice initialize for load mybatis configuration file.
		 */
		@Override
		protected void initialize() {
				setEnvironmentId("development");
				setClassPathResource("mybatis-config.xml");
		}
}
