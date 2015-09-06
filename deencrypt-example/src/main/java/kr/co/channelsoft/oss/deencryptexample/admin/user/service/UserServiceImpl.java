package kr.co.channelsoft.oss.deencryptexample.admin.user.service;

import java.util.HashMap;
import java.util.Map;

import kr.co.channelsoft.oss.aopdeencryptor.annotation.Encrypt;
import kr.co.channelsoft.oss.deencryptexample.admin.user.mapper.UserMapper;
import kr.co.channelsoft.oss.deencryptexample.common.service.ServiceCommon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@SuppressWarnings("serial")
public class UserServiceImpl extends ServiceCommon implements UserService {
    
    @Autowired
    private UserMapper mapper;
    
    /*
     * (non-Javadoc)
     * @see kr.co.channelsoft.oss.deencryptexample.admin.user.service.UserService#getUserList(java.util.Map)
     */
    public Map<String, Object> getUserList(final Map<String, String> dto) throws Exception {
        
        return getSuccessResultMap(new HashMap<String, Object>() {{
                
            put("rows", mapper.list(dto));
                
        }});
    }
    
    /*
     * (non-Javadoc)
     * @see kr.co.channelsoft.oss.deencryptexample.admin.user.service.UserService#getUser(java.util.Map)
     */
    public Map<String, Object> getUser(final Map<String, String> dto) throws Exception {
        
        return getSuccessResultMap(new HashMap<String, Object>() {{
                
            put("user", mapper.select(dto));
                
        }});
    }
    
    /*
     * (non-Javadoc)
     * @see kr.co.channelsoft.oss.deencryptexample.admin.user.service.UserService#saveUser(java.util.Map)
     */
    public Map<String, Object> saveUser(@Encrypt Map<String, String> dto) throws Exception {
    
        if (CollectionUtils.isEmpty(dto) || !dto.containsKey("MODE") || !"M".equals(dto.get("MODE"))) {
            return getFailureResultMap();
        }
        
        mapper.merge(dto);
        
        return getSuccessResultMap();
    }
    
    /*
     * (non-Javadoc)
     * @see kr.co.channelsoft.oss.deencryptexample.admin.user.service.UserService#removeUser(java.util.Map)
     */
    public Map<String, Object> removeUser(Map<String, String> dto) throws Exception {
    
        if (CollectionUtils.isEmpty(dto) || !dto.containsKey("MODE") || !"D".equals(dto.get("MODE"))) {
            return getFailureResultMap();
        }
        
        mapper.delete(dto);
        
        return getSuccessResultMap();
    }
    
}