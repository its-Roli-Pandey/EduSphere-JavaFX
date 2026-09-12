package com.edusphere.model;

public class Student {
    private int id;
    private String name;
    private String email;
    private String course;

    // Constructor (नया स्टूडेंट बनाने के लिए)
    public Student(int id, String name, String email, String course) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCourse() { return course; }
}
