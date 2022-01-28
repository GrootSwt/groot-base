package com.groot.base.web.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;

public abstract class BaseRepository {

    @Resource
    protected EntityManager entityManager;

    protected Querydsl querydsl;

    protected JPAQueryFactory queryFactory;

    protected abstract Class<?> getModelClass();

    /**
     * 获取Querydsl和获取JPAQueryFactory
     */
    @PostConstruct
    protected void init() {
        querydsl = new Querydsl(entityManager, new PathBuilderFactory().create(getModelClass()));
        queryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * 无排序分页条件查询
     *
     * @param jpaQuery 查询语句
     * @param pageable 分页信息
     * @param <T>      类型
     * @return 分页数据
     */
    protected <T> Page<T> search(JPAQuery<T> jpaQuery, Pageable pageable) {
        long count = jpaQuery.fetchCount();
        List<T> list = jpaQuery.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
        return new PageImpl<>(list, pageable, count);
    }

    /**
     * 有排序分页条件查询
     *
     * @param jpaQuery 查询语句
     * @param pageable 分页信息
     * @param <T>      类型
     * @return 分页数据
     */
    protected <T> Page<T> search(JPAQuery<T> jpaQuery, Pageable pageable, OrderSpecifier<?>... orderSpecifier) {
        long count = jpaQuery.fetchCount();
        List<T> list = jpaQuery.offset(pageable.getOffset()).limit(pageable.getPageSize()).orderBy(orderSpecifier).fetch();
        return new PageImpl<>(list, pageable, count);
    }


}
