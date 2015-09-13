package kr.co.channelsoft.oss.aopdeencryptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import kr.co.channelsoft.oss.aopdeencryptor.annotation.Encrypt;
import kr.co.channelsoft.oss.aopdeencryptor.itfc.Cryptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DeencryptionAdvice {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final boolean isEnabledFirstParamEncryption;
    
    @Autowired
    private Cryptor cryptor;
    
    @Autowired
    private EncryptionTargetHolder encryptionTargetHolder;
    
    @Autowired
    private DecryptionTargetHolder decryptionTargetHolder;
    
    public DeencryptionAdvice() {
        isEnabledFirstParamEncryption = true;
    }
    
    public DeencryptionAdvice(boolean isEnabledFirstParamEncryption) {
        this.isEnabledFirstParamEncryption = isEnabledFirstParamEncryption;
    }
    
    @SuppressWarnings("unchecked")
    @Around("execution(* kr.co.channelsoft.oss.deencryptexample.admin.user.service.*Service.get*(..))")
    public Object aroundRetrieve(ProceedingJoinPoint pjp) throws Throwable {
        
        try {
            Object[] params = pjp.getArgs();
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            String methodName = method.getName();
            String businessName = methodName.replace("get", ""); // 메소드 이름 = 업무명
            
            if (logger.isDebugEnabled()) logger.debug("Method name : {}", methodName);
            
            // Parameter 암호화
            if (params != null && params.length > 0) {
                if (logger.isDebugEnabled()) logger.debug("Business name : {}", businessName);
                
                if (isEnabledFirstParamEncryption) {
                    if (logger.isDebugEnabled()) logger.debug("First parameter encryption is enabled.");
                    
                    encrypt(businessName, (Map<String, Object>) params[0]);
                    
                } else {
                    Annotation[][] paramAnnotations = pjp.getTarget().getClass().getMethod(methodName, method.getParameterTypes()).getParameterAnnotations();
                    
                    if (paramAnnotations.length > 0) {
                        if (logger.isDebugEnabled()) logger.debug("Parameter annotations : {}", Arrays.deepToString(paramAnnotations));
                        if (logger.isDebugEnabled()) logger.debug("Parameter values : {}", Arrays.deepToString(params));
                        
                        int paramLength = params.length;
                        for (int i = 0; i < paramLength; i++) {
                            
                            int annotationLength = paramAnnotations[i].length;
                            for (int j = 0; j < annotationLength; j++) {
                                if (paramAnnotations[i][j] instanceof Encrypt) {
                                    encrypt(businessName, (Map<String, Object>) params[i]);
                                }
                            }
                        }
                    }
                    
                }
            }
            
            Object result = pjp.proceed();
            
            // 복호화
            if (result instanceof List) {
                for (Map<String, Object> data : (List<Map<String, Object>>) result) { // 복호화 대상 list
                    decrypt(businessName, data);
                }
                
            } else if (result instanceof Map) {
                for (Map<String, Object> data : (List<Map<String, Object>>) ((Map<String, Object>) result).get("rows")) { // 복호화 대상 list
                    decrypt(businessName, data);
                }
                
            }
            
            return result;
            
        } catch (Throwable e) {
            throw e;
            
        }
    }
    
    @SuppressWarnings("unchecked")
    @Before("execution(* kr.co.channelsoft.oss.deencryptexample.admin.user.service.*Service.save*(..))")
    public void beforeSave(JoinPoint jp) throws Throwable {
    
        try {
            Object[] params = jp.getArgs();
            Method method = ((MethodSignature) jp.getSignature()).getMethod();
            String methodName = method.getName();
            String businessName = methodName.replace("save", ""); // Method name = Business name
            
            if (logger.isDebugEnabled()) logger.debug("Method name : {}", methodName);
            
            // Parameter existence check.
            if (params == null || params.length == 0) {
                if (logger.isDebugEnabled()) logger.debug("Parameters doesn't exist.");
                return;
            }
            
            // Parameter 암호화 start
            if (logger.isDebugEnabled()) logger.debug("Business name : {}", businessName);
            
            // First parameter encryption enabled check.
            if (isEnabledFirstParamEncryption) {
                if (logger.isDebugEnabled()) logger.debug("First parameter encryption is enabled.");
                
                encrypt(businessName, (Map<String, Object>) params[0]);
                
            } else {
                if (logger.isDebugEnabled()) logger.debug("First parameter encryption is disabled.");
                
                // Parameter annotation check.
                Annotation[][] paramAnnotations = method.getParameterAnnotations();
                
                if (paramAnnotations != null && paramAnnotations.length > 0) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Parameter annotations : {}", Arrays.deepToString(paramAnnotations));
                        logger.debug("Parameter values : {}", Arrays.deepToString(params));
                    }
                    
                    int encryptAnnotationCount = 0;
                    
                    int paramLength = params.length;
                    for (int i = 0; i < paramLength; i++) {
                        
                        int annotationLength = paramAnnotations[i].length;
                        for (int j = 0; j < annotationLength; j++) {
                            
                            // Parameter annotation type check.
                            if (paramAnnotations[i][j] instanceof Encrypt) {
                                encryptAnnotationCount++;
                                
                                encrypt(businessName, (Map<String, Object>) params[i]);
                            }
                        }
                    }
                    
                    if (encryptAnnotationCount == 0) {
                        if (logger.isDebugEnabled()) logger.debug("First parameter encryption is disabled but 'Encrypt' annotation doesn't exist!");
                    }
                    
                } else {
                    if (logger.isDebugEnabled()) logger.debug("First parameter encryption is disabled but parameter annotations doesn't exist!");
                    
                }
                
            }
            
        } catch (Throwable e) {
            if (logger.isWarnEnabled()) logger.warn(e.getMessage());
            
        }
    }
    
    // 암호화
    public void encrypt(String businessName, Map<String, Object> data) throws Exception {
        
        if (data == null) {
            if (logger.isDebugEnabled()) logger.debug("Encrypt target data map is null.");
            return;
        }
        if (!encryptionTargetHolder.containsKey(businessName)) {
            if (logger.isDebugEnabled()) logger.debug("Encrypt target holder doesn't contain the key : {}", businessName);
            return;
        }
        
        for (String encryptTarget : encryptionTargetHolder.getTargetList(businessName)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Encrypt target name : {}", encryptTarget);
                logger.debug("Encrypt target exists : {}", data.containsKey(encryptTarget));
            }
            
            if (data.containsKey(encryptTarget)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Encrypt target value : {}", data.get(encryptTarget));
                    logger.debug("Encrypted value : {}", cryptor.encrypt((String) data.get(encryptTarget)));
                }
                
                data.put(encryptTarget, cryptor.encrypt((String) data.get(encryptTarget)));
            }
        }
    }
    
    // 복호화
    public void decrypt(String businessName, Map<String, Object> data) throws Exception {
    	
        if (data == null) {
            if (logger.isDebugEnabled()) logger.debug("Decrypt target data map is null.");
            return;
        }
        if (!decryptionTargetHolder.containsKey(businessName)) {
            if (logger.isDebugEnabled()) logger.debug("Decrypt target holder doesn't contain the key : {}", businessName);
            return;
        }
        
        for (String decryptTarget : decryptionTargetHolder.getTargetList(businessName)) {
            if (logger.isDebugEnabled()) {
                logger.debug("Decrypt target name : {}", decryptTarget);
                logger.debug("Decrypt target exists : {}", data.containsKey(decryptTarget));
            }
            
            if (data.containsKey(decryptTarget)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Decrypt target value : {}", data.get(decryptTarget));
                    logger.debug("Decrypted value : {}", cryptor.decrypt((String) data.get(decryptTarget)));
                }
                
                data.put(decryptTarget, cryptor.decrypt((String) data.get(decryptTarget)));
            }
        }
    }
    
}