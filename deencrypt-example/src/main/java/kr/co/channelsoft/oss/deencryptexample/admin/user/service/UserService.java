package kr.co.channelsoft.oss.deencryptexample.admin.user.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    
    /**
     * 사용자 정보 목록 조회
     * 
     * @param dto
     * @return
     * @throws Exception
     */
    public Map<String, Object> getUserList(Map<String, String> dto) throws Exception;
    
    /**
     * 사용자 정보 조회
     * 
     * @param dto
     * @return
     * @throws Exception
     */
    public Map<String, Object> getUser(Map<String, String> dto) throws Exception;
    
    /**
     * 사용자 정보 입력/수정
     * 
     * @param dto
     * @return
     * @throws Exception
     */
    public Map<String, Object> saveUser(Map<String, String> dto) throws Exception;
    
    /**
     * 사용자 정보 삭제
     * 
     * @param dto
     * @return
     * @throws Exception
     */
    public Map<String, Object> removeUser(Map<String, String> dto) throws Exception;
    
}