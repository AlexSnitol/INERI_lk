package com.ineri.ineri_lk.controller;

import com.ineri.ineri_lk.model.Address;
import com.ineri.ineri_lk.model.City;
import com.ineri.ineri_lk.model.EstateObject;
import com.ineri.ineri_lk.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Slotin Alexander (@alexsnitol)
 */

@RequestMapping("/estate-objects")
@Controller
public class EstateObjectController extends AbstractController {

    @Autowired
    EstateObjectServiceImpl estateObjectService;
    @Autowired
    HouseTypeServiceImpl houseTypeService;
    @Autowired
    PropertyTypeServiceImpl propertyTypeService;
    @Autowired
    RenovationTypeServiceImpl renovationTypeService;
    @Autowired
    EstateObjectTypeServiceImpl estateObjectTypeService;
    @Autowired
    CityServiceImpl cityService;
    @Autowired
    AddressServiceImpl addressService;

    @GetMapping
    public ModelAndView getAll() {
        ModelAndView mv = new ModelAndView("test_view_estate_objects");
        mv.addObject("estateObjects", estateObjectService.getAll());
        return mv;
    }

    @GetMapping("/{id}")
    public ModelAndView getById(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("test_view_estate_object");
        mv.addObject("estateObject", estateObjectService.getById(id));
        return mv;
    }

    @GetMapping("/new")
    public ModelAndView newEstateObject(EstateObject estateObject) {
        ModelAndView mv = new ModelAndView("test_new_estate_object");

        mv.addObject("houseTypes", houseTypeService.getAll());
        mv.addObject("propertyTypes", propertyTypeService.getAll());
        mv.addObject("renovationTypes", renovationTypeService.getAll());
        mv.addObject("estateObjectTypes", estateObjectTypeService.getAll());
        mv.addObject("cities", cityService.getAll());

        return mv;
    }

    @PostMapping("/new")
    public String createEstateObject(EstateObject estateObject, @RequestParam City city, @RequestParam String fullAddress) {
        Address address = new Address(city, fullAddress);
        addressService.save(address);

        estateObject.setAddress(address);
        estateObject.setNowDateTime();
        estateObjectService.save(estateObject);

        return "redirect:/estate-objects";
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView mv = new ModelAndView("test_edit_estate_object");

        mv.addObject("estateObject", estateObjectService.getById(id));
        mv.addObject("houseTypes", houseTypeService.getAll());
        mv.addObject("propertyTypes", propertyTypeService.getAll());
        mv.addObject("renovationTypes", renovationTypeService.getAll());
        mv.addObject("estateObjectTypes", estateObjectTypeService.getAll());
        mv.addObject("cities", cityService.getAll());

        return mv;
    }

    @PostMapping("/{id}/edit")
    public String update(EstateObject estateObject, @PathVariable Long id, @RequestParam City city, @RequestParam String fullAddress) {
        Address address = new Address(city, fullAddress);
        addressService.save(address);

        estateObject.setAddress(address);
        estateObject.setNowDateTime();
        estateObjectService.save(estateObject);

        return "redirect:/estate-objects/" + id;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        estateObjectService.deleteById(id);
        return "redirect:/estate-objects";
    }

}
