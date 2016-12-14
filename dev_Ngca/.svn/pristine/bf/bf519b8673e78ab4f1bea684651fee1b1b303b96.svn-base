package com.bt.vosp.capability.nextgenclientauthorisation.impl.helper;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.bt.vosp.common.exception.PropertyLoadingException;

public class NGCAPreProcessorTest {
	
	@Mock
	ConfigurableListableBeanFactory arg0;

	@Test
	public void loadNGCAPropstest() throws PropertyLoadingException {
		NGCAPreProcessor ngca = new NGCAPreProcessor();
		ngca.loadNGCAProps();
	}
	@Test
	public void postProcessBeanFactoryTest()
	{
		
		
		NGCAPreProcessor ngca = new NGCAPreProcessor();
		ngca.postProcessBeanFactory(arg0);
	}

	
	@Test
	public void postProcessBeanFactoryTestException()
	{
		
		
		NGCAPreProcessor ngca = new NGCAPreProcessor();
		ngca.postProcessBeanFactory(null);
	}

}
