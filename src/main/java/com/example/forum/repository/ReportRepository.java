package com.example.forum.repository;

import com.example.forum.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
//    ReportRepository が JpaRepository を継承しており、findAllメソッドを実行しているため、
//    こちらで特に何か記載する必要はありません。
//    JpaRepositryにはあらかじめいくつかのメソッドが定義されており、SQL文を打つ必要がありません。
}
