package com.gjm.file_cloud.dao;

import com.gjm.file_cloud.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDao extends JpaRepository<File, Long> {
    void deleteFileByName(String name);
    Optional<File> findFileByName(String name);
}
