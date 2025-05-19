package com.example.forum.repository;

import com.example.forum.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    //    ReportRepository が JpaRepository を継承しており、findAllメソッドを実行しているため、
//    こちらで特に何か記載する必要はありません。
//    JpaRepository にはCRUD操作の為の基本的なメソッドが定義されており、SQL文を打つ必要がありません
//    カスタムクエリ
    public List<Comment> findAllByOrderByUpdatedDateDesc();
}
