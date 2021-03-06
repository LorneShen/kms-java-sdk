package com.qcloud.kms;
import com.qcloud.kms.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.List ;

/**
 * @file kms_sample.java
 * @author:yorkxyzhang
 * @Description:kms sample  
 * @date :2017-2-28
 **/
public class kms_sample {
    public static void main(String[] args){
        //从腾讯云官网查询的云API密钥信息
        String secretId="";
        String secretKey="";
        String endpoint = "https://kms-gz.api.qcloud.com";
        try
        {
            KMSAccount account = new KMSAccount(endpoint,secretId, secretKey);
            String Description = "test";
            String Alias= "test-java-4";
            String KeyUsage = "ENCRYPT/DECRYPT";
            KeyMetadata meta = account.create_key(Description , Alias , KeyUsage);
            
            System.out.println("------------create the custom master key-------------");
            System.out.println("KeyId               "+meta.KeyId);
            System.out.println("CreateTime          "+Integer.toString(meta.CreateTime));
            System.out.println("Description         "+meta.KeyId);
            System.out.println("KeyState            "+meta.KeyState);
            System.out.println("KeyUsage            "+meta.KeyUsage);
            System.out.println("Alias               "+meta.Alias);
                
            // create a data key 
            String KeySpec = "AES_128";
            StringBuffer Plaintext  = new StringBuffer();
            String CiphertextBlob = account.generate_data_key(meta.KeyId, KeySpec,1024,"",Plaintext);
            System.out.println("the data key string is " + Plaintext);
            System.out.println("the encrypted data key string is "+CiphertextBlob);
            
            //encrypt the data key 
            CiphertextBlob  = account.encrypt(meta.KeyId, Plaintext.toString(),"");
            System.out.println("the encrypted data is " + CiphertextBlob);
                    
            //decrypt the encrypted data string
            String result = account.decrypt( CiphertextBlob,"");
            System.out.println("the decrypted data is " + result);
            
            //get the key attributes 
            meta = account.get_key_attributes(meta.KeyId);
                    
            System.out.println("------------create the custom master key-------------");
            System.out.println("KeyId               "+meta.KeyId);
            System.out.println("CreateTime          "+Integer.toString(meta.CreateTime));
            System.out.println("Description         "+meta.KeyId);
            System.out.println("KeyState            "+meta.KeyState);
            System.out.println("KeyUsage            "+meta.KeyUsage);
            System.out.println("Alias               "+meta.Alias);
            
            //set alias 
            Alias = "for test";
            account.set_key_attributes(meta.KeyId, Alias);
            
            //disable a custom key
            account.disable_key(meta.KeyId);
            
            //enable a custom key
            account.enable_key(meta.KeyId);
            
            //schedule delete key 
            account.schedule_key_deletion(meta.KeyId,7);
            // cancel delete key 
            account.cancel_key_deletion(meta.KeyId);
            
            
            
            //list keys         
            ArrayList<String> KeyId  = new ArrayList<String>();
            account.list_key(-1,-1,KeyId);
            for(int i = 0 ; i < KeyId.size(); ++i)
                System.out.println("the " +Integer.toString(i) + "Key id is " + KeyId.get(i));
            

        }
        catch(KMSServerException e1){
            System.out.println("Server Exception, " + e1.toString());
        }
        catch(KMSClientException e2){
            System.out.println("Client Exception, " + e2.toString());
        }
        catch (Exception e) {
                System.out.println("error..." + e.toString());
        }
        
    }
}
