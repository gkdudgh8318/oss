package kr.co.channelsoft.oss.aopdeencryptor.itfc;

public interface Cryptor {
    
    public String encrypt(String target) throws Exception;
    
    public String decrypt(String target) throws Exception;
    
}