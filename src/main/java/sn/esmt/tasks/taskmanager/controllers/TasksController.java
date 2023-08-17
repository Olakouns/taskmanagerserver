package sn.esmt.tasks.taskmanager.controllers;

import org.springframework.web.bind.annotation.*;
import sn.esmt.tasks.taskmanager.dto.converters.ApiResponse;
import sn.esmt.tasks.taskmanager.entities.tksmanager.Dashboard;
import sn.esmt.tasks.taskmanager.entities.tksmanager.TaskCategory;
import sn.esmt.tasks.taskmanager.entities.tksmanager.Tasks;
import sn.esmt.tasks.taskmanager.services.TasksService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/task-manager")
public class TasksController {
    private final TasksService tasksService;

    public TasksController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping("dashboard")
    public List<Dashboard> getAllDashboards() {
        return this.tasksService.getAllDashboards();
    }

    @PostMapping("dashboard")
    public Dashboard addDashboard(@RequestBody @Valid Dashboard dashboard) {
        return this.tasksService.addDashboard(dashboard);
    }

    @PutMapping("dashboard/{dashboardId}")
    public Dashboard updateDashboard(@PathVariable UUID dashboardId, @RequestBody @Valid Dashboard dashboard) {
        return this.tasksService.updateDashboard(dashboardId, dashboard);
    }

    @DeleteMapping("dashboard/{dashboardId}")
    public ApiResponse deleteDashboard(@PathVariable UUID dashboardId) {
        return this.tasksService.deleteDashboard(dashboardId);
    }

    @GetMapping("dashboard/{dashboardId}/task-categories")
    public List<TaskCategory> getTaskCategoryByDashboard(@PathVariable UUID dashboardId) {
        return this.tasksService.getTaskCategoryByDashboard(dashboardId);
    }

    @GetMapping("dashboard/{dashboardId}/task-categories/{taskCategoryId}")
    public List<Tasks> getTasksByCategory(@PathVariable UUID dashboardId, @PathVariable UUID taskCategoryId, @RequestParam(defaultValue = "", required = false) String search) {
        return this.tasksService.getTasksByCategory(taskCategoryId, search);
    }

    @PostMapping("dashboard/{dashboardId}/task-categories")
    public TaskCategory addTaskCategory(@PathVariable UUID dashboardId, @RequestBody @Valid TaskCategory taskCategory) {
        return this.tasksService.addTaskCategory(dashboardId, taskCategory);
    }

    @PutMapping("dashboard/{dashboardId}/task-categories/{taskCategoryId}")
    public TaskCategory updateTaskCategory(@PathVariable UUID dashboardId, @PathVariable UUID taskCategoryId, @RequestBody @Valid TaskCategory taskCategory) {
        return this.tasksService.updateTaskCategory(dashboardId, taskCategoryId, taskCategory);
    }

    @DeleteMapping("dashboard/{dashboardId}/task-categories/{taskCategoryId}")
    public ApiResponse deleteTaskCategory(@PathVariable UUID dashboardId, @PathVariable UUID taskCategoryId) {
        return this.tasksService.deleteTaskCategory(dashboardId, taskCategoryId);
    }

    @PostMapping("dashboard/{dashboardId}/task-categories/{taskCategoryId}/tacks")
    public Tasks addTasks(@PathVariable UUID dashboardId, @PathVariable UUID taskCategoryId, @RequestBody @Valid Tasks tasks) {
        return this.tasksService.addTasks(taskCategoryId, tasks);
    }

    @PutMapping("dashboard/{dashboardId}/task-categories/{taskCategoryId}/tacks/{tasksId}")
    public Tasks updateTasks(@PathVariable UUID dashboardId, @PathVariable UUID taskCategoryId, @PathVariable UUID tasksId, @RequestBody @Valid Tasks tasks) {
        return this.tasksService.updateTasks(taskCategoryId, tasksId, tasks);
    }

    @DeleteMapping("dashboard/{dashboardId}/task-categories/{taskCategoryId}/tacks/{tasksId}")
    public ApiResponse deleteTasks(@PathVariable UUID dashboardId, @PathVariable UUID taskCategoryId, @PathVariable UUID tasksId) {
        return this.tasksService.deleteTasks(taskCategoryId, tasksId);
    }

    @PutMapping("dashboard/{dashboardId}/task-categories/{taskCategoryId}/tacks/{tasksId}/add-user")
    public ApiResponse addUserToTheTask(@PathVariable UUID dashboardId, @PathVariable UUID taskCategoryId, @PathVariable UUID tasksId, @RequestParam UUID profileId) {
        return this.tasksService.addUserToTheTask(taskCategoryId, profileId);
    }
}
