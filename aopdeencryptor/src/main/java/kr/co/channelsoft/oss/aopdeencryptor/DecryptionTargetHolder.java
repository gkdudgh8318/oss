package kr.co.channelsoft.oss.aopdeencryptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("decryptionTargetHolder")
public class DecryptionTargetHolder {
    
    private final Map<String, List<String>> targetHolder;
    
    public DecryptionTargetHolder() {
    
        // 복호화 대상 정의
        targetHolder = new HashMap<String, List<String>>();
        targetHolder.put("User", Arrays.asList("PASS_WD", "REGI_NO", "CARD_NO", "ACCT_NO", "CELL_NO", "TELE_NO"));
        targetHolder.put("UserList", Arrays.asList("PASS_WD", "REGI_NO", "CARD_NO", "ACCT_NO", "CELL_NO", "TELE_NO"));
/*
        tagerMap.put("key1", Arrays.asList("item11", "item12")); // 카드번호
        tagerMap.put("key2", Arrays.asList("item21", "item22")); // 전화번호, 생일
        tagerMap.put("key3", Arrays.asList("item31", "item32"));
*/
    }
    
    public List<String> getTargetList(String key) {
        
        return new ArrayList<String>(targetHolder.get(key));
    }
    
    public boolean containsKey(Object key) {
        
        return targetHolder.containsKey(key);
    }
    
}