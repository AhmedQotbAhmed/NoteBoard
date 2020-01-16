package com.example.noteapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
// annotation -->@name
// dao   "interFace: بنحط فيه الحاجات اللي بينعملها زي النسيرت و ريموف و الكلام ده     "
// @Dao   "annotation to make a room knowing your operations"
                        // interFace  in --> @insert  'Serialize'  "and take a obj from my class"
                        // interFace in  --> @Update    "and take a obj from my class"
                        // interFace in  --> @Delete    "and take a obj from my class"
// interFace  in -->  @query ("Select * from task ''name of entity ")   'List<class> select( ) { //codes}'

//


@Entity (tableName = "NoteEntity") // name your Entity
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID") //  علشان لما اجي بعد كدا و اغير اسم الفريبول اللي تحت ده هيحصل انه مش هيعرف يااكسيس الداتا الاديمه لانه مش عارف الاسم بتاعها علشان كدا بعمل كولوم اينفوا
    private int id;

    @ColumnInfo(name = "task")
    private String task;

    @ColumnInfo(name = "desc")
    private String desc;

    @ColumnInfo(name = "finish_by")
    private String finishBy;

    @ColumnInfo(name = "finished")
    private boolean finished;


    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFinishBy() {
        return finishBy;
    }

    public void setFinishBy(String finishBy) {
        this.finishBy = finishBy;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}


/// proguard sto protect your application