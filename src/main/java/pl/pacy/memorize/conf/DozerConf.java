package pl.pacy.memorize.conf;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DozerConf {

	@Bean(name = "org.dozer.Mapper")
	public DozerBeanMapper mapper() {
		List<String> mappingFiles = Arrays.asList(
//				"dozer-global-configuration.xml",
//				"dozer-bean-mappings.xml",
//				"more-dozer-bean-mappings.xml"
		);

		DozerBeanMapper dozerBean = new DozerBeanMapper();
		dozerBean.setMappingFiles(mappingFiles);
		return dozerBean;
	}

}
