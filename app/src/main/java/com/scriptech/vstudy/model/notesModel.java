package com.scriptech.vstudy.model;

import com.google.firebase.firestore.PropertyName;

public class notesModel {
    @PropertyName("notes_name")
    public String notes_name;
    @PropertyName("notes_link")
    public String notes_link;

    public String getNotesName() {
        return notes_name;
    }

    public String getNotesLink() {
        return notes_link;
    }
}
