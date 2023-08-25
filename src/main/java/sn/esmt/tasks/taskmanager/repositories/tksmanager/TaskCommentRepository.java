package sn.esmt.tasks.taskmanager.repositories.tksmanager;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.tasks.taskmanager.entities.tksmanager.TaskCategory;
import sn.esmt.tasks.taskmanager.entities.tksmanager.TaskComment;


public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
}
