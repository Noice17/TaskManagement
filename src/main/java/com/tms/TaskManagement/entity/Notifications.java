package com.tms.TaskManagement.entity;


import jakarta.persistence.*;
import org.aspectj.weaver.ast.Not;

@Entity
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(nullable = false)
    @JoinColumn(name = "user_id")
    private User user;

    /*@Column(nullable = false)
    @JoinColumn(name = "team_id")
    private Team team;*/

    @Column(nullable = false)
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(nullable = false)
    private String description;

    public Notifications() {}

//    public Notifications(Long id, User user, Team team, Task task, String description) {
//        this.id = id;
//        this.user = user;
//        this.team = team;
//        this.task = task;
//        this.description = description;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    public Team getTeam() {
//        return team;
//    }
//
//    public void setTeam(Team team) {
//        this.team = team;
//    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
