package net.in.dayan.springdemo.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @SequenceGenerator(name = "todoSeq", sequenceName="todo_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todoSeq")
    private Integer id;

    private String title;

    private Integer status;

    private Date dt;

    @Transient
    private MultipartFile pic;

    @Transient
    private boolean hasPic = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getTitle() {
        return title;
    }

    public MultipartFile getPic() {
        return pic;
    }

    public void setPic(MultipartFile pic) {
        this.pic = pic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatusStr() {
        return status == null || status == 0? "Open": "Close";
    }

    public void setHasPic(boolean b) {
        this.hasPic = b;
    }

    public boolean hasPic() {
        return this.hasPic;
    }

    static class Option {
        public Integer id;
        public String name;
        public Option(Integer id, String name) {
            this.name = name;
            this.id = id;
        }
    }

    public static Option[] statusOptions = new Option[]{new Option(0, "Open"), new Option(1, "Close")};
}
