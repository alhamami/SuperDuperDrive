package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.io.StringReader;
import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT f.* FROM FILES f JOIN USERS u ON f.userid = u.userid WHERE f.filename = #{fileName} AND u.username = #{username}")
    File getFileByFileName(String fileName, String username);

    @Select("SELECT f.* FROM FILES f JOIN USERS u ON f.userid = u.userid WHERE u.username = #{username}")
    List<File> getAllFiles(String username);

    @Select("SELECT f.* FROM FILES f JOIN USERS u ON f.userid = u.userid WHERE f.fileid = #{fileId} AND u.username = #{username}")
    File getFileByFileId(Integer fileId, String username);

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @SelectKey(statement = "SELECT file_id_seq.last_value from superduperdrive.file_id_seq", keyProperty = "fileId", before = false, resultType = Integer.class)
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int savetFile(File file);

    @Delete("DELETE FROM FILES f USING USERS u WHERE f.fileid = #{fileId} AND u.username = #{username}")
    int deleteFile(Integer fileId, String username);




}
