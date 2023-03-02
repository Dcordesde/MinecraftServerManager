package de.david.serverManagerBackend;

public class ServerObject {

    // name, screenname, directory, version, type(bungee), port, date

    private String name;
    private String screenName;
    private String directory;
    private String version;
    private String type;
    private String port;
    private String date;

    public ServerObject(String name,String screenName,String directory,String version,String type,String port,String date){
        this.name = name;
        this.screenName = screenName;
        this.directory = directory;
        this.version = version;
        this.type = type;
        this.port = port;
        this.date = date;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
