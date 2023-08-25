package sn.esmt.tasks.taskmanager.storages;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@ConfigurationProperties("storage")
@Component
public class StorageProperties {

    private String location;

    public StorageProperties() {
		this.location = System.getProperty("user.home") + File.separator + "TaskManager";
		File dir = new File(this.location + File.separator + "images");
		if (!dir.exists())
			if(!dir.mkdirs()){
				System.err.println("no create "+dir.getAbsolutePath());
			}
	}

	public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
