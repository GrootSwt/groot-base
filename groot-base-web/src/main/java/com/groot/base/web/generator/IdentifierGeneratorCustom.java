package com.groot.base.web.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * 自定义主键生成器
 * 雪花算法
 */
public class IdentifierGeneratorCustom implements IdentifierGenerator {

    public IdentifierGeneratorCustom() {
    }


    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return IdGenerator.generateId();
    }

}
