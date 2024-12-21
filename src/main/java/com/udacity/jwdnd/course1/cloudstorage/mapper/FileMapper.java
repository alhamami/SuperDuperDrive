package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFileByFileName(String fileName);

    @Select("SELECT f.* FROM FILES f JOIN USERS u ON f.userid = u.userid WHERE u.username = #{username}")
    List<File> getAllFiles(String username);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFileByFileId(Integer fileId);

    @Insert("INSERT INTO files (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @SelectKey(statement = "SELECT file_id_seq.last_value from superduperdrive.file_id_seq", keyProperty = "fileId", before = false, resultType = Integer.class)
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int savetFile(File file);


}
