package kr.co.channelsoft.oss.deencryptexample.admin.user.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("userMapper")
@SuppressWarnings("unchecked")
public class UserMapperImpl extends EgovAbstractMapper implements UserMapper {
    
    /*
     * (non-Javadoc)
     * @see kr.co.channelsoft.oss.deencryptexample.admin.user.mapper.UserMapper#list(java.util.Map)
     */
    public List<Object> list(Map<String, String> dto) {
    
        return (List<Object>) selectList("kr.co.channelsoft.oss.deencryptexample.admin.user.list", dto);
    }
    
    /*
     * (non-Javadoc)
     * @see kr.co.channelsoft.oss.deencryptexample.admin.user.mapper.UserMapper#count(java.util.Map)
     */
    public int count(Map<String, String> dto) {
    
        return (Integer) selectOne("kr.co.channelsoft.oss.deencryptexample.admin.user.count", dto);
    }
    
    /*
     * (non-Javadoc)
     * @see kr.co.channelsoft.oss.deencryptexample.admin.user.mapper.UserMapper#merge(java.util.Map)
     */
    public int merge(Map<String, String> dto) {
    
        return update("kr.co.channelsoft.oss.deencryptexample.admin.user.merge", dto);
    };
    
    /*
     * (non-Javadoc)
     * @see kr.co.channelsoft.oss.deencryptexample.admin.user.mapper.UserMapper#insert(java.util.Map)
     */
    public int insert(Map<String, String> dto) {
    
        return insert("kr.co.channelsoft.oss.deencryptexample.admin.user.insert", dto);
    }
    
    /*
     * (non-Javadoc)
     * @see kr.co.channelsoft.oss.deencryptexample.admin.user.mapper.UserMapper#update(java.util.Map)
     */
    public int update(Map<String, String> dto) {
    
        return update("kr.co.channelsoft.oss.deencryptexample.admin.user.update", dto);
    }
    
    /*
     * (non-Javadoc)
     * @see kr.co.channelsoft.oss.deencryptexample.admin.user.mapper.UserMapper#delete(java.util.Map)
     */
    public int delete(Map<String, String> dto) {
    
        return delete("kr.co.channelsoft.oss.deencryptexample.admin.user.delete", dto);
    }
    
}