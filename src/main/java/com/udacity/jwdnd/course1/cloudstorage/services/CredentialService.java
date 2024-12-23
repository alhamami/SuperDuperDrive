package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


        User user = userService.getUserByUsername(username);
        credential.setUserid(user.getUserid());

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), generateKey(user.getUserid().toString()));
        credential.setPassword(encryptedPassword);
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
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), generateKey(credential.getUserid().toString()));
            credential.setPassword(decryptedPassword);
            decryptedCredentials.add(credential);
        }

        return decryptedCredentials;
    }

    public boolean deleteCredential(Integer credentialid, String username){

        int deletedCredential = credentialMapper.deleteCredential(credentialid, username);

        return deletedCredential > 0;
    }

    public boolean editCredential(Credential credential){

        int editedCredential = credentialMapper.editCredential(credential);

        return editedCredential > 0;
    }

    private String generateKey(String userid){

        String generatedKey = "";

        if (userid.length() < 16) {

            StringBuilder keyBuffer = new StringBuilder(userid);

            while(keyBuffer.length() < 16) {
                keyBuffer.append("0");
            }
            generatedKey = keyBuffer.toString();
        }else{
            generatedKey = userid.substring(0, 16);
        }

        return generatedKey;

    }
}
