package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (url, username, password, keysecret, userid) VALUES (#{url}, #{username}, #{password}, #{keySecret}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int saveCredential(Credential credential);

    @Select("SELECT c.* FROM CREDENTIALS c JOIN USERS u ON c.userid = u.userid WHERE u.username = #{username}")
    List<Credential> getAllCredentials(String username);

    @Delete("DELETE FROM CREDENTIALS c USING USERS u WHERE c.credentialid = #{credentialid} AND u.username = #{username}")
    int deleteCredential(Integer credentialid, String username);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password} WHERE credentialid = #{credentialid}")
    int editCredential(Credential credential);


}
