package com.techacademy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.EmployeeService;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // 日報一覧画面
    @GetMapping
    public String list(Model model, @AuthenticationPrincipal UserDetail userDetail) {
        List<Report> reportList;

        if (userDetail.getEmployee().getRole() == Employee.Role.ADMIN) {
            reportList=reportService.findAll();
        } else {
            reportList=reportService.findByEmployee(userDetail.getEmployee());
        }
        model.addAttribute("listSize",reportList.size());
        model.addAttribute("reportList",reportList);
        return "reports/list";
    }

    // 日報詳細画面
    @GetMapping(value = "/{id}/")
    public String detail(@PathVariable Integer id, Model model) {

       model.addAttribute("report", reportService.findById(id));
        return "reports/detail";
    }

    // 日報新規登録画面
        @GetMapping(value = "/add")
        public String create(@ModelAttribute Report report, @AuthenticationPrincipal UserDetail userDetail, Model model) {
            // employee/regist.htmlに画面遷移
            model.addAttribute("employeeName", userDetail.getEmployee().getName());
        return "reports/new";
    }

    // 日報新規登録処理
        @PostMapping(value = "/add")
        public String add(@Validated Report report, BindingResult res, @AuthenticationPrincipal UserDetail userDetail, Model model) {
            if(res.hasErrors()) {
                // エラーあり
                return create(report, userDetail, model);
            }
            // employee登録
            reportService.saveReport(report, userDetail);
            // 一覧画面にリダイレクト
            return "redirect:/reports";
        }

    // 日報削除処理
    @PostMapping(value = "/{code}/delete")
    public String delete(@PathVariable String code, @AuthenticationPrincipal UserDetail userDetail, Model model) {

//        ErrorKinds result = reportService.delete(code, userDetail);
//
//        if (ErrorMessage.contains(result)) {
//            model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
//            model.addAttribute("report", reportService.findByCode(code));
//            return detail(code, model);
//        }

        return "redirect:/reports";
    }

    // 日報更新処理
    @PostMapping(value = "/{code}/update")
    public String edit(@PathVariable String code, @AuthenticationPrincipal UserDetail userDetail,
            Model model/* ,Report report */) {
//        if(code==null) {
//            model.addAttribute("report",report);
//        }
//        else {
//        model.addAttribute("employee", reportService.findByCode(code));
//        }
        return "reports/update";
    }

    @PostMapping(value = "/update")
    public String update(@AuthenticationPrincipal UserDetail userDetail,
            /* @Validated Report report, */ BindingResult res, Model model) {
        // 入力チェック
//        if (res.hasErrors()) {
//            return edit(null,userDetail,model,report);
//        }
//             // パスワードが空白だった場合
//         ErrorKinds result = null;
//
//        if ("".equals(report.getPassword())) {
//            // 空白の場合
//            result = reportService.updateWithoutPassword(report);
//
//        } else {
//            result = reportService.update(report);
//        }
//        if (ErrorMessage.contains(result)) {
//            model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
//            return edit(null,userDetail,model,report);
//        }
        return "redirect:/reports";
    }
}
