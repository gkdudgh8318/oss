package kr.co.channelsoft.oss.aopdeencryptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("decryptionTargetHolder")
public class DecryptionTargetHolder {
    
    private Map<String, List<String>> targetHolder;
    
    public DecryptionTargetHolder() {
    
        // 복호화 대상 칼럼 선언 & alias 이름 선언
        targetHolder = new HashMap<String, List<String>>();
        targetHolder.put("User", Arrays.asList("PASS_WD", "REGI_NO", "CARD_NO", "ACCT_NO", "CELL_NO", "TELE_NO"));
        targetHolder.put("UserList", Arrays.asList("PASS_WD", "REGI_NO", "CARD_NO", "ACCT_NO", "CELL_NO", "TELE_NO"));
        
        /*targetHolder.put("User", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"PASS_WD", "REGI_NO", "CARD_NO", "ACCT_NO", "CELL_NO", "TELE_NO"})));
    	targetHolder.put("UserList", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"PASS_WD", "REGI_NO", "CARD_NO", "ACCT_NO", "CELL_NO", "TELE_NO"})));// 카드번호
        tagerMap.put("ASD14", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"MobileTel", "BirthDate"})));// 전화번호,생일
        tagerMap.put("ATA01", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));*/
    }
    
    public List<String> getTargetList(String key) {
        
        return new ArrayList<String>(targetHolder.get(key));
    }
    
    public boolean containsKey(Object key) {
        
        return targetHolder.containsKey(key);
    }
    
}