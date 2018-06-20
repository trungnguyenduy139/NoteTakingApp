package com.example.trungnguyen.notetakingapp;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "NOTE")
public class Note {

    @NotNull
    @Property(nameInDb = "noteText")
    private String mNoteText;
    @NotNull
    @Property (nameInDb = "noteCreated")
    private String mCreateDate;
    @Id(autoincrement = true)
    private Long mid;

    public Note(String mNoteText, String mCreateDate) {
        this.mNoteText = mNoteText;
        this.mCreateDate = mCreateDate;
    }

    @Generated(hash = 1793001670)
    public Note(@NotNull String mNoteText, @NotNull String mCreateDate, Long mid) {
        this.mNoteText = mNoteText;
        this.mCreateDate = mCreateDate;
        this.mid = mid;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public String getmNoteText() {
        return mNoteText;
    }

    public void setmNoteText(String mNoteText) {
        this.mNoteText = mNoteText;
    }

    public String getmCreateDate() {
        return mCreateDate;
    }

    public void setmCreateDate(String mCreateDate) {
        this.mCreateDate = mCreateDate;
    }

    public String getMNoteText() {
        return this.mNoteText;
    }

    public void setMNoteText(String mNoteText) {
        this.mNoteText = mNoteText;
    }

    public String getMCreateDate() {
        return this.mCreateDate;
    }

    public void setMCreateDate(String mCreateDate) {
        this.mCreateDate = mCreateDate;
    }

    public Long getMid() {
        return this.mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }
}
