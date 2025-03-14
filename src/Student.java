
public class Student {


    private String id;
    private String name;
    private Module[] modules ;

    public Student(String id, String name, Module[] modules) {
        this.id = id;
        this.name = name;
        this.modules = modules;
    }

    // Getters and setters for id, name, and modules

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Module[] getModules() {
        return modules;
    }


}
