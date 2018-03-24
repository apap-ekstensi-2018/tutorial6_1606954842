package com.example.service;

import com.example.dao.StudentMapper;
import com.example.model.StudentModel;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
public class StudentServiceDatabaseTest {
	private StudentService studentService = new StudentServiceDatabase();
	@Mock
	private StudentMapper studentMapper;
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		this.studentService = new StudentServiceDatabase(this.studentMapper);
	}
	@Test
	public void selectStudent() {
		// Given
		StudentModel studentModel = new StudentModel("1506737823", "Chanek", 3.5);
		StudentModel check = new StudentModel("1506737823", "Chanek", 3.5);
		BDDMockito.given(studentMapper.selectStudent("1506737823")).willReturn(studentModel);
		// When
		StudentModel test = studentService.selectStudent("1506737823");
		// Then
		assertThat(test, notNullValue()); // Check if Not Null
		assertThat(test, equalTo(check)); // Check if Same
	}
	
	@Test
	public void selectAllStudent() {
		// Given
		List<StudentModel> studentModels = new ArrayList<>();
		StudentModel studentModel = new StudentModel("1506737823", "Chanek", 3.5);
		studentModels.add(studentModel);
		List<StudentModel> checks = new ArrayList<>();
		StudentModel check = new StudentModel("1506737823", "Chanek", 3.5);
		checks.add(check);
		BDDMockito.given(studentMapper.selectAllStudents()).willReturn(studentModels);
		// When
		List<StudentModel> test = studentService.selectAllStudents();
		// Then
		assertThat(test, notNullValue()); // Check if Not Null 
		assertThat(test.isEmpty(), equalTo(false)); // Check kalo ngga kosong 
		assertThat(test.size(), equalTo(1)); // Check if Size same 
		assertThat(test, equalTo(checks)); // Check kalo konten sama
	}
	
	@Test
	public void addStudent() {
		// Given
		StudentModel studentModel = new StudentModel("1506737823", "Chanek", 3.5); 
		StudentModel check = new StudentModel("1506737823", "Chanek", 3.5);
		BDDMockito.given(studentMapper.addStudent(studentModel)).willReturn(true);
		// When
		boolean test = studentService.addStudent(studentModel);
		// Then 
		BDDMockito.then(studentMapper).should().addStudent(check); 
		assertThat(test, equalTo(true)); // Check if Same
	}
	
	@Test
	public void deleteStudent() {
		// Given
		List<StudentModel> studentModels = new ArrayList<>();
		StudentModel student = new StudentModel("1506737823", "Chanek", 3.5);
		StudentModel student1 = new StudentModel("1506737824", "Chanek1", 3.5);
		studentModels.add(student);
		studentModels.add(student1);
		
		BDDMockito.given(studentMapper.deleteStudent(Mockito.anyString())).will(new Answer<List<StudentModel>>() {
			@Override
			public List<StudentModel> answer(InvocationOnMock arg0) throws Throwable {
				String npm = arg0.getArgument(0);
				for(StudentModel i : studentModels) {
					if(i.getNpm().equals(npm)) {
						studentModels.remove(i);
						break;
					}
				}
				return studentModels;
			}
		});
		
		// When
		List<StudentModel> test = studentService.deleteStudent("1506737823");
		// Then
		assertThat(test.size(), equalTo(1));
		assertThat(test.contains(student1), equalTo(true));
		assertThat(test.contains(student), equalTo(false));
	}
	
	@Test
	public void updateStudent() {
		// Given
		StudentModel student = new StudentModel("1506737823", "Chanek", 3.5);
	
		BDDMockito.given(studentMapper.updateStudent(Mockito.any(StudentModel.class))).will(new Answer<StudentModel>() {
			@Override
			public StudentModel answer(InvocationOnMock arg0) throws Throwable {
				StudentModel studentModel = arg0.getArgument(0);
				student.setName(studentModel.getName());
				student.setGpa(studentModel.getGpa());
				return student;
			}
		});
		
		// When
		StudentModel updateStudent = new StudentModel("1506737823", "Gani Manja", 4);
		StudentModel test = studentService.updateStudent(updateStudent);
		
		// Then
		assertThat(test.getNpm(), equalTo(updateStudent.getNpm()));
		assertThat(test.getName(), equalTo(updateStudent.getName()));
		assertThat(test.getGpa(), equalTo(updateStudent.getGpa()));
	}
	
	
}