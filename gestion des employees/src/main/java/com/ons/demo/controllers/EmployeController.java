package com.ons.demo.controllers;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ons.demo.entities.Employe;
import com.ons.demo.services.EmployeService;

@Controller
public class EmployeController {
	
	@Autowired
	EmployeService employeService;
	
	@RequestMapping("/showCreate")
	public String showCreate(ModelMap modelMap)
	{
	modelMap.addAttribute("employe", new Employe());
	modelMap.addAttribute("mode", "new");
	return "formEmploye";
	}
	
	@RequestMapping("/saveEmploye")
	public String saveProduit(@Valid Employe employe,BindingResult bindingResult)
	{
	if (bindingResult.hasErrors()) return "formEmploye";
	employeService.saveEmploye(employe);
	return "formEmploye";
	}
	
	@RequestMapping("/ListeEmployees")
	public String listeEmployees(ModelMap modelMap,@RequestParam (name="page",defaultValue = "0") int page,
			@RequestParam (name="size", defaultValue = "3") int size)
	{
		Page<Employe> emp = employeService.getAllEmployeesParPage(page, size);
		modelMap.addAttribute("employees", emp);
		 modelMap.addAttribute("pages", new int[emp.getTotalPages()]);
		modelMap.addAttribute("currentPage", page);
		modelMap.addAttribute("size", size);
		return "listeEmployees";

	}
	@RequestMapping("/supprimerEmploye")
	public String supprimerEmploye(@RequestParam("id") Long id,
	 ModelMap modelMap,@RequestParam (name="page",defaultValue = "0") int page,
	 @RequestParam (name="size", defaultValue = "2") int size)
	{ 
	employeService.deleteEmployeById(id);
	Page<Employe> emp = employeService.getAllEmployeesParPage(page, size);
			modelMap.addAttribute("employees", emp);
			modelMap.addAttribute("pages", new int[emp.getTotalPages()]);
			modelMap.addAttribute("currentPage", page);
			modelMap.addAttribute("size", size);

	return "listeEmployees";
	}
	@RequestMapping("/modifierEmploye")
	public String editerEmploye(@RequestParam("id") Long id,ModelMap modelMap)
	{
	Employe e= employeService.getEmploye(id);
	modelMap.addAttribute("employe", e);
	return "editerEmploye";
	}

	
	@RequestMapping("/modifierEmploye")
	public String upadteEmploye(@RequestParam("id") Long id,ModelMap modelMap)
	{
	Employe e= employeService.getEmploye(id);
	modelMap.addAttribute("employe", e);
	modelMap.addAttribute("mode", "edit");
	return "formEmploye";
	}



}
