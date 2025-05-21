package com.example.forum.service;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.repository.entity.Comment;
import com.example.forum.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;


    /*
     * レコード全件取得処理
     */
    public List<CommentForm> findAllComment() {
//        findAllで実行されている処理はSQL文の「select * from report;」のようなもの
        //ennity型
        List<Comment> results = commentRepository.findAllByOrderByUpdatedDateDesc();
//        setReportFormメソッドでEntity→Formに詰め直して、Controllerに戻しています。
//        これはEntityはデータアクセス時の入れ物、FormはViewへの入出力時に使用する入れ物と役割を分けているためです
        List<CommentForm> comments = setCommentForm(results);
        return comments;
    }
    /*
     * DBから取得したデータをformに設定
     */
    private List<CommentForm> setCommentForm(List<Comment> results) {
//        ビューに返すためにはフォームに移し返さないといけない
        List<CommentForm> comments = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            CommentForm comment = new CommentForm();
            Comment result = results.get(i);
            comment.setCommentId(result.getId());
            comment.setReportId(result.getReportId());
            comment.setCommentContent(result.getContent());
            comment.setCreatedDate(String.valueOf(result.getCreatedDate()));
            comment.setUpdatedDate(String.valueOf(result.getUpdatedDate()));
            comments.add(comment);
        }
        return comments;
    }
    /*
     * レコード追加　saveメソッドはテーブルに新規投稿をinsert・updateするような処理
     */
    public void saveComment(CommentForm reqComment) throws ParseException {
        Comment saveComment = setCommentEntity(reqComment);
        //saveメソッドはテーブルに新規投稿をinsert・updateするような処理
        commentRepository.save(saveComment);
        //戻り値はなし
    }

    /*
     * リクエストから取得した情報をEntityに設定
     */
    private Comment setCommentEntity(CommentForm reqComment) throws ParseException {
        Comment comment = new Comment();
        comment.setId(reqComment.getCommentId());
        comment.setReportId(reqComment.getReportId());
        comment.setContent(reqComment.getCommentContent());

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Createdate = sdFormat.parse(reqComment.getCreatedDate());
        comment.setCreatedDate(Createdate);

        Date updateDate = new Date();
        String update = sdFormat.format(updateDate);
        Date updatedDate = sdFormat.parse(update);
        comment.setUpdatedDate(updatedDate);
        return comment;
    }

    /*
     * レコード1件取得
     */
    public CommentForm editComment(Integer id) {
        List<Comment> results = new ArrayList<>();
        //Id が合致しないときは null を返したいので、.orElse(null)
        results.add((Comment) commentRepository.findById(id).orElse(null));
        List<CommentForm> comments = setCommentForm(results);
        return comments.get(0);
    }

    /*
     * レコード削除
     */
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
        //戻り値はなし
    }
}
