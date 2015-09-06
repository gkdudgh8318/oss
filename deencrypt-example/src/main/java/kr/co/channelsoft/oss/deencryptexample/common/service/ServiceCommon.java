package kr.co.channelsoft.oss.deencryptexample.common.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("serial")
public class ServiceCommon {
    
    @Autowired
    private MessageSource messageSource;
    
    protected Map<String, Object> getSuccessResultMap(final Map<String, Object> result) {
        
        return new HashMap<String, Object>() {{
        	putAll(result);
            put("userdata", new HashMap<String, Object>() {{
                
                
                put("resultCode", messageSource.getMessage("common.success.code", null, Locale.getDefault()));
                put("resultMsg",  messageSource.getMessage("common.success.msg", null, Locale.getDefault()));
            }});
        }};
    }
    
    protected Map<String, Object> getSuccessResultMap() {
        
        return new HashMap<String, Object>() {{
            put("userdata", new HashMap<String, Object>() {{
                
                put("resultCode", messageSource.getMessage("common.success.code", null, Locale.getDefault()));
                put("resultMsg",  messageSource.getMessage("common.success.msg", null, Locale.getDefault()));
            }});
        }};
    }
    
    protected Map<String, Object> getFailureResultMap() {
        
        return new HashMap<String, Object>() {{
            put("userdata", new HashMap<String, Object>() {{
                put("resultCode", messageSource.getMessage("common.failure.code", null, Locale.getDefault()));
                put("resultMsg",  messageSource.getMessage("common.failure.msg", null, Locale.getDefault()));
            }});
        }};
    }
    
}