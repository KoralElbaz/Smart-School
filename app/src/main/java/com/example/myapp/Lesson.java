package com.example.myapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Lesson implements Serializable
{
    private String details;
    private Map<String,String> students_CourseGrade;
    private Map<String,String> teacher_LessonName;

    public Lesson(String details, Map<String,String> teacher_LessonName) {
        this.details = details;
        this.teacher_LessonName = teacher_LessonName;
    }

    public Lesson(String details, Map<String, String> students_CourseGrade, Map<String,String> teacher_LessonName) {
        this.details = details;
        this.students_CourseGrade = students_CourseGrade;
        this.teacher_LessonName = teacher_LessonName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Map<String, String> getStudents_CourseGrade() {
        return students_CourseGrade;
    }

    public void setStudentsList(Map<String, String> students_CourseGrade) {
        this.students_CourseGrade = students_CourseGrade;
    }

    public Map<String, String> getTeacher_LessonName() {
        return teacher_LessonName;
    }

    public void setTeacher_LessonName(Map<String, String> teacher_LessonName) {
        this.teacher_LessonName = teacher_LessonName;
    }
}
