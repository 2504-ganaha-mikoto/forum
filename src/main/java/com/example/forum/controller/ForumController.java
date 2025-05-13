package com.example.forum.controller;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ForumController {
    @Autowired //newしなくてもいい
    ReportService reportService;

    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得した値を入れる箱（contentData）をつくってサービスに渡しています
        List<ReportForm> contentData = reportService.findAllReport();
        // 画面遷移先を指定 「現在のURL」/top へ画面遷移することを指定します。
        mav.setViewName("/top");
        // 投稿データオブジェクトを先ほどのcontentDataをModelAndView型の変数mavへ格納します。
        // 保管各値がReportForm型のリストである「contentData」へ格納されます。
        mav.addObject("contents", contentData);
        /* 変数mavを戻り値として返します。 */
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") ReportForm reportForm){
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}

