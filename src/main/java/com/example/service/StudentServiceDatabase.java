package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.StudentMapper;
import com.example.model.StudentModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentServiceDatabase implements StudentService
{
    @Autowired
    private StudentMapper studentMapper;
    public StudentServiceDatabase() {}
    public StudentServiceDatabase(StudentMapper studentMapper) {
    		this.studentMapper = studentMapper;
    }
    @Override
    public StudentModel selectStudent (String npm)
    {
        log.info ("Select student with npm {}", npm);
        return studentMapper.selectStudent (npm);
    }


    @Override
    public List<StudentModel> selectAllStudents ()
    {
        log.info ("Select all students");
        return studentMapper.selectAllStudents ();
    }


    @Override
    public boolean addStudent (StudentModel student)
    {
    		log.info("Student "+student.getName()+" telah ditambah");
    		return studentMapper.addStudent (student);
        
    }


    @Override
    public List<StudentModel> deleteStudent (String npm)
    {
        log.info("Student " + npm + " deleted");
        return studentMapper.deleteStudent(npm);
    }

    @Override
    public StudentModel updateStudent(StudentModel student){
        log.info("Student " + student.getNpm() + " update");
        return studentMapper.updateStudent(student);
    }

}
