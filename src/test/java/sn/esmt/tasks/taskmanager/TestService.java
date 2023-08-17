package sn.esmt.tasks.taskmanager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sn.esmt.tasks.taskmanager.dto.converters.ApiResponse;
import sn.esmt.tasks.taskmanager.dto.converters.LoginRequest;
import sn.esmt.tasks.taskmanager.entities.tksmanager.Dashboard;
import sn.esmt.tasks.taskmanager.entities.tksmanager.TaskCategory;
import sn.esmt.tasks.taskmanager.entities.tksmanager.Tasks;
import sn.esmt.tasks.taskmanager.services.AuthService;
import sn.esmt.tasks.taskmanager.services.TasksService;

import java.util.Date;
import java.util.List;


@SpringBootTest
@ActiveProfiles("test")
public class TestService {
    @Autowired
    private TasksService tasksService;
    @Autowired
    private AuthService authService;

    @Value("${default.login.username}")
    private String username;
    @Value("${default.login.password}")
    private String password;


    @BeforeEach
    public void setUp() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setUsername(username);
        authService.authenticateUser(loginRequest);
    }

    @Test
    public void getUserDashboard() {
        List<Dashboard> dashboardList = this.tasksService.getAllDashboards();
        Assertions.assertThat(dashboardList.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void addDashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setBordName("Spring boot application");
        dashboard.setDescriptions("Some description");
        Dashboard dashboardB = tasksService.addDashboard(dashboard);

        Assertions.assertThat(dashboardB).isNotNull();
        Assertions.assertThat(dashboardB.getId()).isNotNull();
        Assertions.assertThat(dashboardB.getBordName()).isEqualTo(dashboard.getBordName());
        Assertions.assertThat(dashboardB.getDescriptions()).isEqualTo(dashboard.getDescriptions());

        List<TaskCategory> taskCategories = tasksService.getTaskCategoryByDashboard(dashboard.getId());
        Assertions.assertThat(taskCategories.size()).isGreaterThan(0);
    }

    @Test
    public void updateDashboard() {
        Dashboard dashboardB = new Dashboard();
        dashboardB.setBordName("Financial App");
        dashboardB.setDescriptions("Some description");
        dashboardB = tasksService.addDashboard(dashboardB);

        Assertions.assertThat(dashboardB.getId()).isNotNull();

        dashboardB.setBordName(dashboardB.getBordName() + "Update");

        Dashboard dashboardDB = this.tasksService.updateDashboard(dashboardB.getId(), dashboardB);
        Assertions.assertThat(dashboardDB.getBordName()).isEqualTo(dashboardB.getBordName());
    }

    @Test
    public void deleteDashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setBordName("Docker App");
        dashboard.setDescriptions("Some description");
        dashboard = tasksService.addDashboard(dashboard);
        Assertions.assertThat(dashboard.getId()).isNotNull();

        ApiResponse apiResponse = this.tasksService.deleteDashboard(dashboard.getId());
        Assertions.assertThat(apiResponse).isNotNull();
        Assertions.assertThat(apiResponse.getSuccess()).isEqualTo(true);
    }

    @Test
    public void getTaskCategoryByDashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setBordName("Docker App 2");
        dashboard.setDescriptions("Some description");
        dashboard = tasksService.addDashboard(dashboard);
        Assertions.assertThat(dashboard.getId()).isNotNull();

        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setDashboard(dashboard);
        taskCategory.setName("TaskCategory");
        taskCategory.setIndexColor("#000000");

        taskCategory = tasksService.addTaskCategory(dashboard.getId(), taskCategory);

        Assertions.assertThat(taskCategory.getId()).isNotNull();
        Assertions.assertThat(taskCategory.isDefaultTaskCategory()).isEqualTo(false);
        Assertions.assertThat(taskCategory.getDashboard().getId()).isEqualTo(dashboard.getId());


        List<TaskCategory> taskCategories = tasksService.getTaskCategoryByDashboard(dashboard.getId());
        Assertions.assertThat(taskCategories.size()).isGreaterThan(0);
    }

    @Test
    public void getTasksByCategory() {
        Dashboard dashboard = new Dashboard();
        dashboard.setBordName("Docker App 3");
        dashboard.setDescriptions("Some description");
        dashboard = tasksService.addDashboard(dashboard);
        Assertions.assertThat(dashboard.getId()).isNotNull();

        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setDashboard(dashboard);
        taskCategory.setName("TaskCategory");
        taskCategory.setIndexColor("#000000");

        taskCategory = tasksService.addTaskCategory(dashboard.getId(), taskCategory);

        Assertions.assertThat(taskCategory.getId()).isNotNull();

        Tasks tasks = new Tasks();
        tasks.setTitle("My tasks");
        tasks.setTags(List.of("Design", "UI/UX"));
        tasks.setBadgeColor(List.of("#000000", "#ffffff"));
        tasks.setDeadline(new Date());
        tasks.setDashboard(dashboard);
        tasks.setTaskCategory(taskCategory);

        tasks = tasksService.addTasks(taskCategory.getId(), tasks);

        Assertions.assertThat(tasks.getId()).isNotNull();
        Assertions.assertThat(tasks.getCreatedBy()).isNotNull();

        List<Tasks> tasksList = tasksService.getTasksByCategory(taskCategory.getId(), null);
        Assertions.assertThat(tasksList.size()).isEqualTo(1);

        Tasks tasks2 = new Tasks();
        tasks2.setTitle("My Apps Tasks");
        tasks2.setTags(List.of("Design", "UI/UX"));
        tasks2.setBadgeColor(List.of("#000000", "#ffffff"));
        tasks2.setDeadline(new Date());
        tasks2.setDashboard(dashboard);
        tasks2.setTaskCategory(taskCategory);

        tasks2 = tasksService.addTasks(taskCategory.getId(), tasks2);

        Assertions.assertThat(tasks2.getId()).isNotNull();
        Assertions.assertThat(tasks2.getCreatedBy()).isNotNull();

        List<Tasks> tasksList2 = tasksService.getTasksByCategory(taskCategory.getId(), "My Apps Tasks");
        Assertions.assertThat(tasksList2.size()).isEqualTo(1);


        List<Tasks> tasksList3 = tasksService.getTasksByCategory(taskCategory.getId(), "");
        Assertions.assertThat(tasksList3.size()).isEqualTo(2);

    }

    @Test
    public void addTaskCategory() {
        Dashboard dashboard = new Dashboard();
        dashboard.setBordName("Add tasks category dash");
        dashboard.setDescriptions("Some description");
        dashboard = tasksService.addDashboard(dashboard);
        Assertions.assertThat(dashboard.getId()).isNotNull();

        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setDashboard(dashboard);
        taskCategory.setName("TaskCategory");
        taskCategory.setIndexColor("#000000");

        taskCategory = tasksService.addTaskCategory(dashboard.getId(), taskCategory);
        Assertions.assertThat(taskCategory.getId()).isNotNull();
        Assertions.assertThat(taskCategory.getName()).isEqualTo("TaskCategory");
    }

    @Test
    public void updateTaskCategory() {
        Dashboard dashboard = new Dashboard();
        dashboard.setBordName("Update tasks category dash");
        dashboard.setDescriptions("Some description");
        dashboard = tasksService.addDashboard(dashboard);
        Assertions.assertThat(dashboard.getId()).isNotNull();

        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setDashboard(dashboard);
        taskCategory.setName("TaskCategory");
        taskCategory.setIndexColor("#000000");

        taskCategory = tasksService.addTaskCategory(dashboard.getId(), taskCategory);
        Assertions.assertThat(taskCategory.getId()).isNotNull();

        taskCategory.setName("Update Task");
        taskCategory = tasksService.updateTaskCategory(dashboard.getId(), taskCategory.getId(), taskCategory);

        Assertions.assertThat(taskCategory.getId()).isNotNull();
        Assertions.assertThat(taskCategory.getName()).isEqualTo("Update Task");
    }

    @Test
    public void deleteTaskCategory() {
        Dashboard dashboard = new Dashboard();
        dashboard.setBordName("Delete tasks dash");
        dashboard.setDescriptions("Some description");
        dashboard = tasksService.addDashboard(dashboard);
        Assertions.assertThat(dashboard.getId()).isNotNull();

        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setDashboard(dashboard);
        taskCategory.setName("TaskCategory");
        taskCategory.setIndexColor("#000000");

        taskCategory = tasksService.addTaskCategory(dashboard.getId(), taskCategory);
        Assertions.assertThat(taskCategory.getId()).isNotNull();

        ApiResponse apiResponse = tasksService.deleteTaskCategory(dashboard.getId(), taskCategory.getId());
        Assertions.assertThat(apiResponse.getSuccess()).isEqualTo(true);

    }

    @Test
    public void addTasks() {
        Dashboard dashboard = new Dashboard();
        dashboard.setBordName("Add tasks dash");
        dashboard.setDescriptions("Some description");
        dashboard = tasksService.addDashboard(dashboard);
        Assertions.assertThat(dashboard.getId()).isNotNull();

        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setDashboard(dashboard);
        taskCategory.setName("TaskCategory");
        taskCategory.setIndexColor("#000000");

        taskCategory = tasksService.addTaskCategory(dashboard.getId(), taskCategory);

        Assertions.assertThat(taskCategory.getId()).isNotNull();

        Tasks tasks = new Tasks();
        tasks.setTitle("My tasks");
        tasks.setTags(List.of("Design", "UI/UX"));
        tasks.setBadgeColor(List.of("#000000", "#ffffff"));
        tasks.setDeadline(new Date());
        tasks.setDashboard(dashboard);
        tasks.setTaskCategory(taskCategory);

        tasks = tasksService.addTasks(taskCategory.getId(), tasks);

        Assertions.assertThat(tasks.getId()).isNotNull();
        Assertions.assertThat(tasks.getCreatedBy()).isNotNull();
    }

    @Test
    public void updateTasks() {
        Dashboard dashboard = new Dashboard();
        dashboard.setBordName("Update tasks dash");
        dashboard.setDescriptions("Some description");
        dashboard = tasksService.addDashboard(dashboard);
        Assertions.assertThat(dashboard.getId()).isNotNull();

        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setDashboard(dashboard);
        taskCategory.setName("TaskCategory");
        taskCategory.setIndexColor("#000000");

        taskCategory = tasksService.addTaskCategory(dashboard.getId(), taskCategory);

        Assertions.assertThat(taskCategory.getId()).isNotNull();

        Tasks tasks = new Tasks();
        tasks.setTitle("My tasks");
        tasks.setTags(List.of("Design", "UI/UX"));
        tasks.setBadgeColor(List.of("#000000", "#ffffff"));
        tasks.setDeadline(new Date());
        tasks.setDashboard(dashboard);
        tasks.setTaskCategory(taskCategory);

        tasks = tasksService.addTasks(taskCategory.getId(), tasks);

        Assertions.assertThat(tasks.getId()).isNotNull();
        Assertions.assertThat(tasks.getCreatedBy()).isNotNull();

        tasks.setTitle("Update task");

        tasks = tasksService.updateTasks(taskCategory.getId(), tasks.getId(), tasks);
        Assertions.assertThat(tasks.getTitle()).isEqualTo("Update task");


        TaskCategory taskCategory2 = new TaskCategory();
        taskCategory2.setDashboard(dashboard);
        taskCategory2.setName("TaskCategory2");
        taskCategory2.setIndexColor("#ffffff");

        taskCategory2 = tasksService.addTaskCategory(dashboard.getId(), taskCategory2);

        Assertions.assertThat(taskCategory2.getId()).isNotNull();


        tasks = tasksService.updateTasks(taskCategory2.getId(), tasks.getId(), tasks);
        Assertions.assertThat(tasks.getTaskCategory().getId()).isNotNull();
        Assertions.assertThat(tasks.getTaskCategory().getId()).isEqualTo(taskCategory2.getId());

    }

    @Test
    public void deleteTasks() {
        Dashboard dashboard = new Dashboard();
        dashboard.setBordName("delete tasks dash");
        dashboard.setDescriptions("Some description");
        dashboard = tasksService.addDashboard(dashboard);
        Assertions.assertThat(dashboard.getId()).isNotNull();

        TaskCategory taskCategory = new TaskCategory();
        taskCategory.setDashboard(dashboard);
        taskCategory.setName("TaskCategory");
        taskCategory.setIndexColor("#000000");

        taskCategory = tasksService.addTaskCategory(dashboard.getId(), taskCategory);

        Assertions.assertThat(taskCategory.getId()).isNotNull();

        Tasks tasks = new Tasks();
        tasks.setTitle("My tasks");
        tasks.setTags(List.of("Design", "UI/UX"));
        tasks.setBadgeColor(List.of("#000000", "#ffffff"));
        tasks.setDeadline(new Date());
        tasks.setDashboard(dashboard);
        tasks.setTaskCategory(taskCategory);

        tasks = tasksService.addTasks(taskCategory.getId(), tasks);

        Assertions.assertThat(tasks.getId()).isNotNull();
        Assertions.assertThat(tasks.getCreatedBy()).isNotNull();

        ApiResponse apiResponse = tasksService.deleteTasks(taskCategory.getId(), tasks.getId());

        Assertions.assertThat(apiResponse.getSuccess()).isEqualTo(true);
    }

    @Test
    public void addUserToTheTask() {
        // TODO: 8/16/2023 addUserToTheTask test
    }

    @Test
    public void removeUserFromTask() {
        // TODO: 8/16/2023 removeUserFromTask test

    }

    @Test
    public void attachFileToTheTask() {
        // TODO: 8/16/2023 attachFileToTheTask test
    }

    @Test
    public void removeFileFromTask() {
        // TODO: 8/16/2023 removeFileFromTask test

    }
}
