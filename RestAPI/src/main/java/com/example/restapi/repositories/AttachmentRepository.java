package com.example.restapi.repositories;

import com.example.restapi.entites.Attachment;
import com.example.restapi.entites.Item;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {
    List<Attachment> findAll();

    @Modifying
    @Query(value = "insert into item_attachments " +
            "(item_id, item_type, item_data) values " +
            "(:itemId, :itemType, :itemData)",
            nativeQuery = true)
    Attachment create(@Param("itemId") Long itemId,
                      @Param("itemType") String itemType,
                      @Param("itemData") String itemData);

    @Query(value = "select * from item_attachments where item_id = :itemId",
            nativeQuery = true)
    List<Attachment> findByItem(@Param("itemId") Long itemId);

    @Modifying
    @Query("update Attachment set " +
            "itemType = :itemType, itemData = :itemData" +
            " where id = :id")
    void updateAttachment(@Param("itemType") String itemType,
                          @Param("itemData") String itemData,
                          @Param("id") Long id);
}
