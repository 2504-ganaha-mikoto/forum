package com.example.forum.service;

import com.example.forum.controller.form.ReportForm;
import com.example.forum.repository.ReportRepository;
import com.example.forum.repository.entity.Report;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    /*
     * レコード全件取得処理
     */
    public List<ReportForm> findAllReport(String start ,String end) throws ParseException {
//        findAllで実行されている処理はSQL文の「select * from report;」のようなもの
//        日付をDate型に変換
        String strStartDay;
        String strEndDay;
        if(!StringUtils.isBlank(start)){
            strStartDay = start + " 00:00:00";
        } else {
            strStartDay = "2020-01-01 00:00:00";	//デフォルト値
        }
        if (!StringUtils.isBlank(end)) {
            strEndDay = end + " 23:59:59";

        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            strEndDay = sdf.format(date) + " 23:59:59";		//デフォルト値
        }
            //2つともDate型に変換する
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDay = sdFormat.parse(strStartDay);
            Date endDay = sdFormat.parse(strEndDay);


        //ennity型
//        ・コメントを追加すると投稿の更新日時が更新される　@PostMapping("/comment")でレポートも更新する処理をする
        List<Report> results = reportRepository.findByUpdatedDateBetweenOrderByIdDesc(startDay ,endDay );
//        setReportFormメソッドでEntity→Formに詰め直して、Controllerに戻しています。
//        これはEntityはデータアクセス時の入れ物、FormはViewへの入出力時に使用する入れ物と役割を分けているためです
        List<ReportForm> reports = setReportForm(results);
        return reports;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<ReportForm> setReportForm(List<Report> results) {
//        ビューに返すためにはフォームに移し返さないといけない
//        コントローラーは全部フォームで流してる？？
        List<ReportForm> reports = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            ReportForm report = new ReportForm();
            Report result = results.get(i);
            report.setId(result.getId());
            report.setContent(result.getContent());
            reports.add(report);
        }
        return reports;
    }
    /*
     * レコード追加　saveメソッドはテーブルに新規投稿をinsertするような処理
     */
    public void saveReport(ReportForm reqReport) {
        Report saveReport = setReportEntity(reqReport);
        //saveメソッドはテーブルに新規投稿をinsert・updateするような処理
        reportRepository.save(saveReport);
        //戻り値はなし
    }

    /*
     * レコード削除
     */
    public void deleteReport(Integer id) {
        reportRepository.deleteById(id);
        //戻り値はなし
    }

    /*
     * レコード1件取得
     */
    public ReportForm editReport(Integer id) {
        List<Report> results = new ArrayList<>();
        //reportRepository.findById(id) : Id が一致するレコードを取得する
        //Id が合致しないときは null を返したいので、.orElse(null)
//        return reportRepository.findById(id).orElseThrow(() -> new NotFoundException("不正なパラメーターです"));
        results.add((Report) reportRepository.findById(id).orElse(null));
        List<ReportForm> reports = setReportForm(results);
        return reports.get(0);
    }
    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Report setReportEntity(ReportForm reqReport) {
        Report report = new Report();
        report.setId(reqReport.getId());
        report.setContent(reqReport.getContent());
        return report;
    }


}

