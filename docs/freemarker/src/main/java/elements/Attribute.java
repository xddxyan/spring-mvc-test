package elements;

public class Attribute{
    private String type;
    private String name;
    private String nameDesc ="";

    public Attribute(String type, String name) {
        this.type = type;
        this.name = name;
    }
    public Attribute(String type, String name,String desc) {
        this.type = type;
        this.name = name;
        this.nameDesc = desc;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameDesc() {
        return nameDesc;
    }

    public void setNameDesc(String nameDesc) {
        this.nameDesc = nameDesc;
    }
}