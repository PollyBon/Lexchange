package ua.nure.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.nure.model.AppUser;
import ua.nure.model.Employee;
import ua.nure.model.enumerated.Country;
import ua.nure.model.enumerated.Language;
import ua.nure.model.enumerated.Role;
import ua.nure.service.AppUserService;
import ua.nure.service.EmployeeService;


@Controller
@RequestMapping("/")
public class AppController {

	@Autowired
	EmployeeService employeeService;

    @Autowired
    AppUserService appUserService;

	@Autowired
	MessageSource messageSource;


    @RequestMapping(value = { "/cabinet"}, method = RequestMethod.GET)
    public String openCabinet(ModelMap model) {
        AppUser appUser = new AppUser();
        appUser.setRole(Role.NEW);
        model.addAttribute("appUser", appUser);
        additionalAttributes(model);
        return "cabinet";
    }

    @RequestMapping(value = { "/cabinet"}, method = RequestMethod.POST)
    public String register(@Valid AppUser appUser, BindingResult result, ModelMap model) {
        additionalAttributes(model);
        if (result.hasErrors()) {
            return "cabinet";
        }

        if(!appUser.getPassword().equals(appUser.getRePassword())){
            FieldError error = new FieldError("appUser", "rePassword", messageSource.getMessage(
                    "wrong.rePassword", null, Locale.getDefault()));
            result.addError(error);
            return "cabinet";
        }

        appUserService.createUser(appUser);

        model.addAttribute("success", "Employee " + appUser.getFirstName() + " registered successfully");
        return "cabinet";
    }

    private void additionalAttributes(ModelMap model) {
        model.addAttribute("countries", Country.values());
        model.addAttribute("languages", Language.values());
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.POST)
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        System.out.println(email);
        System.out.println(password);

        session.setAttribute("appUser", "");
        return "success";
    }

	@RequestMapping(value = { "/"}, method = RequestMethod.GET)
	public String getIndex(ModelMap model) {
		return "index";
	}

	/////////////////////////////////////////////////////////////----OLD----///----CONTROLLER----////////////////////////


	/*
	 * This method will list all existing employees.
	 */
	@RequestMapping(value = {"/list" }, method = RequestMethod.GET)
	public String listEmployees(ModelMap model) {

		List<Employee> employees = employeeService.findAllEmployees();
		model.addAttribute("employees", employees);
		return "allemployees";
	}

	/*
	 * This method will provide the medium to add a new employee.
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String newEmployee(ModelMap model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		model.addAttribute("edit", false);
		return "registration";
	}

	/*
	 * This method will be called on form submission, handling POST request for
	 * saving employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String saveEmployee(@Valid Employee employee, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			return "registration";
		}

		/*
		 * Preferred way to achieve uniqueness of field [ssn] should be implementing custom @Unique annotation 
		 * and applying it on field [ssn] of Model class [Employee].
		 * 
		 * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
		 * framework as well while still using internationalized messages.
		 * 
		 */
		if(!employeeService.isEmployeeSsnUnique(employee.getId(), employee.getSsn())){
			FieldError ssnError =new FieldError("employee","ssn",messageSource.getMessage("non.unique.ssn", new String[]{employee.getSsn()}, Locale.getDefault()));
		    result.addError(ssnError);
			return "registration";
		}
		
		employeeService.saveEmployee(employee);

		model.addAttribute("success", "Employee " + employee.getName() + " registered successfully");
		return "success";
	}


	/*
	 * This method will provide the medium to update an existing employee.
	 */
	@RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.GET)
	public String editEmployee(@PathVariable String ssn, ModelMap model) {
		Employee employee = employeeService.findEmployeeBySsn(ssn);
		model.addAttribute("employee", employee);
		model.addAttribute("edit", true);
		return "registration";
	}
	
	/*
	 * This method will be called on form submission, handling POST request for
	 * updating employee in database. It also validates the user input
	 */
	@RequestMapping(value = { "/edit-{ssn}-employee" }, method = RequestMethod.POST)
	public String updateEmployee(@Valid Employee employee, BindingResult result,
			ModelMap model, @PathVariable String ssn) {

		if (result.hasErrors()) {
			return "registration";
		}

		if(!employeeService.isEmployeeSsnUnique(employee.getId(), employee.getSsn())){
			FieldError ssnError =new FieldError("employee","ssn",messageSource.getMessage("non.unique.ssn", new String[]{employee.getSsn()}, Locale.getDefault()));
		    result.addError(ssnError);
			return "registration";
		}

		employeeService.updateEmployee(employee);

		model.addAttribute("success", "Employee " + employee.getName()	+ " updated successfully");
		return "success";
	}

	
	/*
	 * This method will delete an employee by it's SSN value.
	 */
	@RequestMapping(value = { "/delete-{ssn}-employee" }, method = RequestMethod.GET)
	public String deleteEmployee(@PathVariable String ssn) {
		employeeService.deleteEmployeeBySsn(ssn);
		return "redirect:/list";
	}

}
