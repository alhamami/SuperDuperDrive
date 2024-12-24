package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class NotesPage {

    @FindBy(id="nav-notes-tab")
    private WebElement notesSection;

    @FindBy(id="buttonAddNote")
    private WebElement addNoteButton;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(xpath = "(//th[@id='createdNoteTitle'])[last()]")
    private WebElement createdNoteTitle;

    @FindBy(xpath = "(//td[@id='createdNoteDescription'])[last()]")
    private WebElement createdNoteDescription;

    @FindBy(xpath = "(//a[@id='deletedNote'])[last()]")
    private WebElement deletedNote;

    @FindBy(xpath = "(//button[@id='editedNote'])[last()]")
    private WebElement editedNote;

    @FindBy(id="note-description")
    private WebElement noteDescription;

    @FindBy(id="noteSubmit")
    private WebElement saveNoteButton;

    private final JavascriptExecutor jsExecutor;


    public NotesPage(WebDriver webDriver) {

        PageFactory.initElements(webDriver, this);

        jsExecutor = (JavascriptExecutor) webDriver;
    }

    public boolean isNoteDisplayed(String title, String description) {

        jsExecutor.executeScript("arguments[0].click();", notesSection);

        try {
            String noteTitleValue = createdNoteTitle.getAttribute("innerHTML");
            String noteDescriptionValue = createdNoteDescription.getAttribute("innerHTML");

            return noteTitleValue.equals(title) && noteDescriptionValue.equals(description);

        }catch (Exception e) {
            return false;
        }

    }

    public void createNote(String title, String description) {


        jsExecutor.executeScript("arguments[0].click();", notesSection);

        jsExecutor.executeScript("arguments[0].click();", addNoteButton);

        jsExecutor.executeScript("arguments[0].value='" + title + "';", noteTitle);

        jsExecutor.executeScript("arguments[0].value='"+ description +"';", noteDescription);

        jsExecutor.executeScript("arguments[0].click();", saveNoteButton);

    }

    public void editNote(String newTitle, String newDescription) {

        jsExecutor.executeScript("arguments[0].click();", notesSection);

        jsExecutor.executeScript("arguments[0].click();", editedNote);

        jsExecutor.executeScript("arguments[0].value='" + newTitle + "';", noteTitle);

        jsExecutor.executeScript("arguments[0].value='"+ newDescription +"';", noteDescription);

        jsExecutor.executeScript("arguments[0].click();", saveNoteButton);


    }


    public void deleteNote() {

        jsExecutor.executeScript("arguments[0].click();", notesSection);

        jsExecutor.executeScript("arguments[0].click();", deletedNote);

    }



}
