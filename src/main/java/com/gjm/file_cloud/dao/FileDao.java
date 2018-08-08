package com.gjm.file_cloud.dao;

import com.gjm.file_cloud.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDao extends JpaRepository<File, Long> {
}
