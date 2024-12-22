package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int saveNote(Note note);

    @Select("SELECT n.* FROM NOTES n JOIN USERS u ON n.userid = u.userid WHERE u.username = #{username}")
    List<Note> getAllNotes(String username);

    @Delete("DELETE FROM NOTES n USING USERS u WHERE n.noteid = #{noteid} AND u.username = #{username}")
    int deleteNote(Integer noteid, String username);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteid}")
    int editNote(Note note);
}
