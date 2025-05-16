package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
//    ReportRepository が JpaRepository を継承しており、findAllメソッドを実行しているため、
//    こちらで特に何か記載する必要はありません。
//    JpaRepository にはCRUD操作の為の基本的なメソッドが定義されており、SQL文を打つ必要がありません
//    カスタムクエリ(共有　ばいんどへんすうみたいになっている)

public List<Report> findByCreatedDateBetweenOrderByIdDesc(Date start, Date end);
}
