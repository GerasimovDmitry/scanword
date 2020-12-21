package com.scanword.backend.repository;

import com.scanword.backend.entity.Dictionary;
import com.scanword.backend.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MediaRepository extends JpaRepository<Media, UUID> {
    @Query(value = "SELECT * FROM media WHERE is_image = true", nativeQuery = true)
    List<Media> getAllImages();

    @Query(value = "SELECT * FROM media WHERE is_image = false", nativeQuery = true)
    List<Media> getAllSounds();
}
