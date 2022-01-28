package com.groot.base.web.convertor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础Model和DTO转换器
 * @param <Model>   Model
 * @param <DTO> DTO
 */
public abstract class BaseConvertor<Model, DTO> {

    /**
     * DTO转换为Model
     * @param dto   DTO
     * @return  Model
     */
    public abstract Model toModel(DTO dto);

    /**
     * Model转换为DTO
     * @param model Model
     * @return  DTO
     */
    public abstract DTO toDTO(Model model);

    /**
     * DTO列表转换为DTO列表
     * @param dtoList   DTO列表
     * @return  Model列表
     */
    public List<Model> toListModel(List<DTO> dtoList) {
        List<Model> modelList = new ArrayList<>();
        dtoList.forEach(dto -> {
            modelList.add(toModel(dto));
        });
        return modelList;
    }

    /**
     * Model列表转换为DTO列表
     * @param modelList Model列表
     * @return  DTO列表
     */
    public List<DTO> toListDTO(List<Model> modelList) {
        List<DTO> dtoList = new ArrayList<>();
        modelList.forEach(model -> {
            dtoList.add(toDTO(model));
        });
        return dtoList;
    }

    /**
     * ModelPage转换为PageDTO
     * @param modelPage ModelPage
     * @return  DTOPage
     */
    public Page<DTO> toPageDTO(Page<Model> modelPage) {
        long total = modelPage.getTotalElements();
        List<Model> content = modelPage.getContent();
        List<DTO> dtoList = toListDTO(content);
        Pageable pageable = modelPage.getPageable();
        return new PageImpl<>(dtoList, pageable, total);
    }
}
