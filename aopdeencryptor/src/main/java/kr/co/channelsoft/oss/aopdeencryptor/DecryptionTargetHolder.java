package kr.co.channelsoft.oss.aopdeencryptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component("decryptionTargetHolder")
@SuppressWarnings("unchecked")
public class DecryptionTargetHolder {
    
    private Map<String, List<String>> tagerMap;
    
    public DecryptionTargetHolder() {
    
        // 복호화 대상 칼럼 선언 & alias 이름 선언
        tagerMap = new HashMap<String, List<String>>();
        tagerMap.put("User", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"PASS_WD", "REGI_NO", "CARD_NO", "ACCT_NO", "CELL_NO", "TELE_NO"})));
        tagerMap.put("UserList", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"PASS_WD", "REGI_NO", "CARD_NO", "ACCT_NO", "CELL_NO", "TELE_NO"})));// 카드번호
        /*tagerMap.put("ASD14", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"MobileTel", "BirthDate"})));// 전화번호,생일
        
        tagerMap.put("CardATE01", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1"})));
        tagerMap.put("ATE01S1", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ATE01RerForm", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ATE01RerSijaForm", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("CheckATE01", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("saveCheckATE01", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("RDivATE01", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("CreditATC05", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("DetailATE01", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ATE01_2", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ATE01S2", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("AZZ01", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("AZZ01S1", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ATG03", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ABF01", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("PosIdATE01", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("CardATC05", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ATZ09", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ATZ09S1", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ACZ15", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ACZ15S2", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ComboATC05", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ATC05", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));
        tagerMap.put("ATA01", new ArrayList<String>(CollectionUtils.arrayToList(new String[] {"ValItem1", "ValItem7"})));*/
    }
    
    public List<String> getTargetList(String key) {
    
        return tagerMap.get(key);
    }
    
}