package com.bt.vosp.capability.nextgenclientauthorisation.impl.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.bt.vosp.common.exception.VOSPBusinessException;
import com.bt.vosp.common.model.NGCAReqObject;
import com.bt.vosp.common.model.NGCARespObject;

import static org.mockito.Mockito.when;

public class SeverityErrorCodeMapperTest {

	@Test
	public void testMapErrors() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("200");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}
	@Test
	public void testMapErrors400() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("400");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}
	@Test
	public void testMapErrors401() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("401");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}
	@Test
	public void testMapErrors403() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("403");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}
	@Test
	public void testMapErrors500() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("500");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}
	@Test
	public void testMapErrorsDAA_1007() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("DAA_1007");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}
	@Test
	public void testMapErrorsDAA_1027() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("DAA_1027");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}
	@Test
	public void testMapErrorsDAA_1009() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("DAA_1009");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}
	@Test
	public void testMapErrorsDAA_1011() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("DAA_1011");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}
	@Test
	public void testMapErrorsDAA_1006() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("DAA_1006");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}
	@Test
	public void testMapErrorsDAA_1037() {
		
			VOSPBusinessException bizEx = new VOSPBusinessException();
			bizEx.setReturnCode("DAA_1037");
			bizEx.setReturnText("OK");
//			Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
			NGCAReqObject ngcaReqObj = new NGCAReqObject();
			NGCARespObject ngcaRespObject = new NGCARespObject();
			SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
			
		
	}@Test
	public void testMapErrors16000() {
		
		VOSPBusinessException bizEx = new VOSPBusinessException();
		bizEx.setReturnCode("16000");
		bizEx.setReturnText("OK");
//		Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
		NGCAReqObject ngcaReqObj = new NGCAReqObject();
		NGCARespObject ngcaRespObject = new NGCARespObject();
		SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
		
	
}@Test
	public void testMapErrors16004() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("16004");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}@Test
	public void testMapErrors16008() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("16008");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}@Test
	public void testMapErrors16503() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("16503");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}@Test
	public void testMapErrors10007() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("10007");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}@Test
	public void testMapErrors16501() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("16501");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}@Test
	public void testMapErrors16500() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("16500");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}@Test
	public void testMapErrors15000() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("15000");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}@Test
	public void testMapErrors20000() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("20000");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}@Test
	public void testMapErrors20508() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("20508");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}@Test
	public void testMapErrors422() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("422");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}
@Test
	public void testMapErrors503() {
	
	VOSPBusinessException bizEx = new VOSPBusinessException();
	bizEx.setReturnCode("503");
	bizEx.setReturnText("OK");
//	Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
	NGCAReqObject ngcaReqObj = new NGCAReqObject();
	NGCARespObject ngcaRespObject = new NGCARespObject();
	SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);
	

}

@Test
public void testMapErrorsStartWith506() {

VOSPBusinessException bizEx = new VOSPBusinessException();
bizEx.setReturnCode("506");
bizEx.setReturnText("OK");
//Mockito.when(bizEx.getHttpStatus()).thenReturn("200");
NGCAReqObject ngcaReqObj = new NGCAReqObject();
NGCARespObject ngcaRespObject = new NGCARespObject();
SeverityErrorCodeMapper.mapErrors(bizEx, ngcaReqObj, ngcaRespObject);


}

}
