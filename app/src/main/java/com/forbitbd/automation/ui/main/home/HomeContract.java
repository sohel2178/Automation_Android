package com.forbitbd.automation.ui.main.home;


import com.forbitbd.automation.models.Command;
import com.forbitbd.automation.models.Device;
import com.forbitbd.automation.models.Switch;
import com.forbitbd.automation.models.User;

import java.util.List;

public interface HomeContract {

    interface Presenter{
        void openAddProject();
        void updateToken(User user);
        void requestForUserProjects(String userId);
        void sendCommand(Command command);
        void onDestroy();

        void updateDevice(Device device);
        void updateSwitch(Switch aSwitch);
        //void requestTodelete(Project project);
        void switchClick(Switch aSwitch);
    }

    interface View{
        void showAddProject();
        void addDeviceToAdapter(Device device);
        void updateDeviceToAdapter(Device device);
        void sendCommand(Command command);
        void editDeviceName(Device device);
        void startSearchUserActivity(Device device);
        void startSharedUserActivity(Device device);

//        void renderProjects(List<Project> projectList);
//        void addProject(Project project);
//        void updateProject(Project project);
//        void editProject(Project project);
//        void deleteProject(Project project);
//        void showDeleteDialog(Project project);
//        void startEmployeeActivity(Project project);
//        void startTaskActivity(Project project);
//        void startFinanceActivity(Project project);
//        void startStoreActivity(Project project);
    }
}
