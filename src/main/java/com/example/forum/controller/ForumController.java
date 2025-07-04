package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ErrorMessageForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ForumController {
    @Autowired //newしなくてもいい
    ReportService reportService;

    @Autowired
    CommentService commentService;

    @Autowired
    HttpSession session;
    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top(@RequestParam(name="start", required=false) String start,
                            @RequestParam(name="end", required=false) String end) throws ParseException {
        //日付を受け取る処理？
        ErrorMessageForm errorMessage = (ErrorMessageForm) session.getAttribute("errorMessage");  // 取得
//        CommentForm commentForm = (CommentForm) session.getAttribute("commentForm");
        session.invalidate(); // クリア
        ModelAndView mav = new ModelAndView();
        // 投稿を全件取得した値を入れる箱（contentData）をつくってサービスに渡しています
        List<ReportForm> contentData = reportService.findAllReport(start ,end);
        List<CommentForm> commentData = commentService.findAllComment();

        // 画面遷移先を指定 「現在のURL」/top へ画面遷移することを指定します。
        mav.setViewName("/top");
        // 投稿データオブジェクトを先ほどのcontentDataをModelAndView型の変数mavへ格納します。
        // 保管各値がReportForm型のリストである「contentData」へ格納されます。
        mav.addObject("contents", contentData);
        mav.addObject("comments", commentData);
        mav.addObject("commentform", new CommentForm());
        mav.addObject("errorMessage", errorMessage);
        mav.addObject("start",start);
        mav.addObject("end", end);


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
    public ModelAndView addContent(@Validated @ModelAttribute("formModel") ReportForm reportForm, BindingResult result)
                                    throws ParseException {
        if(result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
//            新規投稿の画面に戻る
            mav.setViewName("/new");
//            引数のレポートをそのまま戻す
            mav.addObject("formModel", reportForm);
            return mav;
        }
        // 投稿をテーブルに格納
        reportService.saveReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/delete/{id}")
    //    @ModelAttributeは？
    //    @PathVariableは form タグ内の action 属性に記述されている { } 内で指定されたURLパラメータを取得できる
    public ModelAndView deleteContent(@PathVariable Integer id) {
        // 投稿をテーブルに格納
        reportService.deleteReport(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 編集画面表示処理
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editContent(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        // 編集する投稿を取得
        // 格納用の空のformを準備　空ではなくすでにコメントが入っている
        ReportForm report = reportService.editReport(id);
        // 編集する投稿をセット　　　（"キー"　　　,"バリュー"）
        mav.addObject("formModel", report);
        // 画面遷移先を指定
        mav.setViewName("/edit");
        return mav;
    }

    /*
     * 編集処理
     */
    @PutMapping("/update/{id}")
    //@PathVariable はInteger型で アクションthに指定されている｛id}を取り出すことができる
    //@ModelAttributeは("formModel")のキーで登録しているフォームを受け取ることができる
    public ModelAndView updateContent (@PathVariable Integer id,
                                       @Validated @ModelAttribute("formModel") ReportForm report, BindingResult result) throws ParseException {

        if(result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
//            新規投稿の画面に戻る
            mav.setViewName("/edit");
//            引数のレポートをそのまま戻す
            mav.addObject("formModel", report);
            return mav;
        }
            // UrlParameterのidを更新するentityにセット
            report.setId(id);
            // 編集した投稿を更新
            //「report.setId(id);」で、指定された id をセットして、saveReport メソッドへ行って、
            // 投稿の更新処理を行います。
            reportService.saveReport(report);
            // rootへリダイレクト
            return new ModelAndView("redirect:/");

    }
    /*
     *コメント投稿
     */
    @PostMapping("/comment")
    public ModelAndView addContent(@Validated @ModelAttribute("commentform") CommentForm commentForm,BindingResult result ,ReportForm report
                                   ) throws ParseException {
        if(result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("redirect:/");
            ErrorMessageForm errorMessage = new ErrorMessageForm();
            errorMessage.setReportId(commentForm.getReportId());
            for (ObjectError error : result.getAllErrors()) {
                errorMessage.setErrorMessage(error.getDefaultMessage());
            }
            session.setAttribute("errorMessage", errorMessage);
            return mav;
        }
        // 返信をテーブルに格納
        commentService.saveComment(commentForm);
//        レポートIDに対応した投稿を取得//
        reportService.saveReport(report);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * コメント 編集画面表示処理
     */
    @GetMapping("/editComment/{id}")
    public ModelAndView editComment(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        // 編集する投稿を取得
        // 格納用の空のformを準備　空ではなくすでにコメントが入っている
        CommentForm comment = commentService.editComment(id);
        // 編集する投稿をセット　　　（"キー"　　　,"バリュー"）
        mav.addObject("formModel", comment);
        // 画面遷移先を指定
        mav.setViewName("/editComment");
        return mav;
    }

    /*
     * コメント編集処理
     */
    @PutMapping("/updateComment/{id}")
    //@PathVariable はInteger型で アクションthに指定されている｛id}を取り出すことができる
    //@ModelAttributeは("formModel")のキーで登録しているフォームを受け取ることができる
    public ModelAndView updateComment (@PathVariable Integer id,
            @Validated @ModelAttribute("formModel") CommentForm comment,
                                       BindingResult result) throws ParseException {
        if(result.hasErrors()) {
            ModelAndView mav = new ModelAndView();
//            新規投稿の画面に戻る
            mav.setViewName("/editComment");
//            引数のレポートをそのまま戻す
            mav.addObject("formModel", comment);
            return mav;
        }        // 編集した投稿を更新
        // 投稿の更新処理を行います。
        comment.setCommentId(id);
        commentService.saveComment(comment);
        ReportForm report = reportService.editReport(comment.getReportId());
//        レポートを更新
        reportService.saveReport(report);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     *コメント削除処理
     */
    @DeleteMapping("/deleteComment/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id) {
        // 投稿をテーブルに格納
        commentService.deleteComment(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}

