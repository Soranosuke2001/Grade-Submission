package com.ltp.gradesubmission;

// Method 1: This method has bugs
// import java.util.ArrayList;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GradeController {

    // Method 1: This method has bugs (when the site is refreshed the same data is
    // added again)
    // List<Grade> studentGrades = new ArrayList<>();

    // Method 2: Alternative Solution
    List<Grade> studentGrades = new ArrayList<>();

    @GetMapping("/grades")
    public String getGrades(Model model) {
        // Method 1: This method has bugs
        // studentGrades.add(new Grade("Harry", "Potions", "C-"));
        // studentGrades.add(new Grade("Hermione", "Arithmancy", "A+"));
        // studentGrades.add(new Grade("Neville", "Charms", "A-"));

        model.addAttribute("grades", studentGrades);

        return "grades";
    }

    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {
        int index = getGradeIndex(id);
        model.addAttribute("grade", index == Constants.NOT_FOUND ? new Grade() : studentGrades.get(index));

        return "form";
    }

    @PostMapping("/handleSubmit")
    public String submitForm(Grade grade) {
        int index = getGradeIndex(grade.getId());

        if (index == Constants.NOT_FOUND) {
            studentGrades.add(grade);
        } else {
            studentGrades.set(index, grade);
        }

        return "redirect:/grades";
    }

    public Integer getGradeIndex(String id) {
        for (int i = 0; i < studentGrades.size(); i++) {
            if (studentGrades.get(i).getId().equals(id)) {
                return i;
            }
        }

        return Constants.NOT_FOUND;
    }
}
