package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {

    private final UserService userService;
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(UserService userService, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public boolean saveCredential(Credential credential, String username){

        SecureRandom randomSecret = new SecureRandom();
        byte[] keySecretByte = new byte[16];

        User user = userService.getUserByUsername(username);
        credential.setUserid(user.getUserid());

        randomSecret.nextBytes(keySecretByte);
        String keySecret = Base64.getEncoder().encodeToString(keySecretByte);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), keySecret);

        credential.setPassword(encryptedPassword);
        credential.setKeySecret(keySecret);
        int savedCredential = credentialMapper.saveCredential(credential);

        return savedCredential > 0;

    }

    public List<Credential> getAllCredentials(String username){

        return credentialMapper.getAllCredentials(username);
    }

    public List<Credential> getAllDecryptedCredentials(String username){

        List<Credential> decryptedCredentials = new ArrayList<>();
        List<Credential> credentials = credentialMapper.getAllCredentials(username);

        for (Credential credential : credentials) {
            if(credential != null){
                if(credential.getPassword() != null && credential.getKeySecret() != null){
                    String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKeySecret());
                    credential.setPassword(decryptedPassword);
                    decryptedCredentials.add(credential);
                }

            }

        }

        return decryptedCredentials;
    }

    public boolean deleteCredential(Integer credentialid, String username){

        int deletedCredential = credentialMapper.deleteCredential(credentialid, username);

        return deletedCredential > 0;
    }

    public boolean editCredential(Credential credential){

        Credential cred = credentialMapper.getCredential(credential.getUsername());

        credential.setUserid(cred.getUserid());


        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), cred.getKeySecret());

        credential.setPassword(encryptedPassword);
        credential.setKeySecret(cred.getKeySecret());

        int editedCredential = credentialMapper.editCredential(credential);

        return editedCredential > 0;
    }


}
