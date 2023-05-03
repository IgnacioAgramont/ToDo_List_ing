package bo.edu.ucb.todo.dto;

import java.util.*;

public class TaskDto {
    private Integer taskId;
    private String description;
    private Date date;

    private boolean completed;

    private List<Integer> labelIds;

    public TaskDto() {
    }


    public Integer getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getCompleted() { return this.completed; }

    public void setCompleted(boolean completed) { this.completed = completed; }

    public List<Integer> getLabelIds() {
        return this.labelIds;
    }

    public void setLabelIds(List<Integer> labelIds) {
        this.labelIds = labelIds;
    }


    @Override
    public String toString() {
        return "{" +
                " taskId='" + getTaskId() + "'" +
                ", description='" + getDescription() + "'" +
                ", date='" + getDate() + "'" +
                ", completed='" + getCompleted() + "'" +
                ", labelIds='" + getLabelIds() + "'" +
                "}";
    }


}