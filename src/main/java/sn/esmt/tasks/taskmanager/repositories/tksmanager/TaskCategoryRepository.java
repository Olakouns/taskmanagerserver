package sn.esmt.tasks.taskmanager.repositories.tksmanager;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.esmt.tasks.taskmanager.entities.tksmanager.TaskCategory;

import java.util.List;
import java.util.UUID;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, UUID> {
    List<TaskCategory> findByDashboardId(UUID dashboardId);

    List<TaskCategory> findByDefaultTaskCategory(boolean value);
}
