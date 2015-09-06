package kr.co.channelsoft.oss.deencryptexample.admin.user.mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    
    /**
     * 사용자 정보 조회
     * 
     * @param dto
     * @return
     */
    public List<Object> list(Map<String, String> dto);
    
    /**
     * 사용자 수 조회
     * 
     * @param dto
     */
    public int count(Map<String, String> dto);
    
    /**
     * 사용자 정보 조회
     * 
     * @param dto
     * @return
     */
    public Map<String, Object> select(Map<String, String> dto);
    
    /**
     * 사용자 정보 저장/수정
     * 
     * @param dto
     */
    public int merge(Map<String, String> dto);
    
    /**
     * 사용자 정보 저장
     * 
     * @param dto
     */
    public int insert(Map<String, String> dto);
    
    /**
     * 사용자 정보 수정
     * 
     * @param dto
     */
    public int update(Map<String, String> dto);
    
    /**
     * 사용자 정보 삭제
     * 
     * @param dto
     */
    public int delete(Map<String, String> dto);
    
}