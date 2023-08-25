package sn.esmt.tasks.taskmanager.services;

import sn.esmt.tasks.taskmanager.dto.converters.ApiResponse;
import sn.esmt.tasks.taskmanager.entities.tksmanager.Dashboard;
import sn.esmt.tasks.taskmanager.entities.tksmanager.TaskCategory;
import sn.esmt.tasks.taskmanager.entities.tksmanager.TaskComment;
import sn.esmt.tasks.taskmanager.entities.tksmanager.Tasks;

import java.util.List;
import java.util.UUID;

public interface TasksService {
    List<Dashboard> getAllDashboards();

    Dashboard addDashboard(Dashboard dashboard);
    Dashboard getDashboard(UUID dashboardId);

    Dashboard updateDashboard(UUID dashboardId, Dashboard dashboard);

    ApiResponse deleteDashboard(UUID dashboardId);

    List<TaskCategory> getTaskCategoryByDashboard(UUID dashboardId);

    List<Tasks> getTasksByCategory(UUID taskCategoryId, String search);

    TaskCategory addTaskCategory(UUID dashboardId, TaskCategory taskCategory);

    TaskCategory updateTaskCategory(UUID dashboardId, UUID taskCategoryId, TaskCategory taskCategory);

    ApiResponse deleteTaskCategory(UUID dashboardId, UUID taskCategoryId);

    Tasks addTasks(UUID taskCategoryId, Tasks tasks);

    Tasks updateTasks(UUID taskCategoryId, UUID tasksId, Tasks tasks);

    ApiResponse deleteTasks(UUID taskCategoryId, UUID tasksId);

    ApiResponse addUserToTheTask(UUID taskId, UUID profileId);

    ApiResponse removeUserFromTask(UUID taskId, UUID profileId);

    ApiResponse attachFileToTheTask(UUID taskId, long mediaFileId);

    ApiResponse removeFileFromTask(UUID taskId, long mediaFileId);


    TaskComment addCommentToTheTasks(UUID taskId, TaskComment taskComment);

    ApiResponse removeCommentFromTask(UUID taskId, long taskCommentId);
}
