package com.hiwotab.employeedirectory.controller;

import com.hiwotab.employeedirectory.models.Department;
import com.hiwotab.employeedirectory.models.Employee;
import com.hiwotab.employeedirectory.repositories.DepartmentRepo;
import com.hiwotab.employeedirectory.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    EmployeeRepo employeeRepo;
    @RequestMapping("/")
    public String showHomePage() {
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/load")
    public @ResponseBody String LoadDB() {
        Department department = new Department();
        department.setDepName("Operations");
        departmentRepo.save(department);
        department = new Department();
        department.setDepName("Accounting");
        departmentRepo.save(department);
        department = new Department();
        department.setDepName("Human Resources");
        departmentRepo.save(department);
        return "Loaded!";
    }

     @RequestMapping("/homePage")
    public String showHomePages() {
        return "index";
    }

    @GetMapping("/addEmployee/{id}")
    public String addEmployeeInfo(@PathVariable("id") long id, Model model) {

        Employee employee=new Employee();
        employee.setDepartment(departmentRepo.findOne(id));
        model.addAttribute("newEmp", employee);
        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployeeInfos(@Valid @ModelAttribute("newEmp") Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            return "addEmployee";
        }

        Department department=departmentRepo.findOne(employee.getDepartment().getId());
        if(department.getDepHName()==0) {
            employeeRepo.save(employee);
            department.setDepHName(employee.getId());
            departmentRepo.save(department);
        }
        else

            employeeRepo.save(employee);

        return "dispEmpInfo";

    }

    @GetMapping("/addDepartment")
    public String addDepartmentInfo(Model model) {
        model.addAttribute("newDep", new Department());
        return "addDepartment";
    }

    @PostMapping("/addDepartment")
    public String addDepartemntInfo(@Valid @ModelAttribute("newDep") Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addDepartment";
        }
        departmentRepo.save(department);

        model.addAttribute("emp", employeeRepo.findOne(department.getId()));
        return "dispDepInfo";
    }

    @GetMapping("/Depupdate/{id}")
    public String updateDep(@PathVariable("id") long id, Model model) {
        model.addAttribute("newDep", departmentRepo.findOne(id));
        return "updateDep";
    }



    @RequestMapping("/Empupdate/{id}")
    public String updateEmp(@PathVariable("id") long id, Model model) {
        model.addAttribute("newEmp", employeeRepo.findOne(id));
        return "addEmployee";
    }
    @RequestMapping("/Empdelete/{id}")
    public String delEmployee(@PathVariable("id") long id){
        employeeRepo.delete(id);
        return "redirect:/listEmp";
    }
    @RequestMapping("/listDep")
    public String listAllDep(Model model) {
        model.addAttribute("department", departmentRepo.findAll());

        return "listDep";
    }

    @RequestMapping("/listEmp")
    public String listAllEmp(Model model) {
        model.addAttribute("employee", employeeRepo.findAll());
        return "listEmp";
    }
    @RequestMapping("/Depdetail/{id}")
    public String showDepartment(@PathVariable("id") long id, Model model) {
        Department departmentD=departmentRepo.findOne(id);
        model.addAttribute("departmentD", departmentD);
        model.addAttribute("listEmp",employeeRepo.findByDepartment(departmentD));
        return "dispDepDetail";
    }

    @RequestMapping("/Empdetail/{id}")
    public String showEmployee(@PathVariable("id") long id, Model model) {
        model.addAttribute("listEmp", employeeRepo.findOne(id));
        return "dispEmpDetail";
    }



}
