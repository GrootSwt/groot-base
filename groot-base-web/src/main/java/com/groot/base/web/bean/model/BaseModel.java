package com.groot.base.web.bean.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class BaseModel implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "Id_Generator")
    @GenericGenerator(name = "Id_Generator",strategy = "com.groot.base.web.generator.IdentifierGeneratorCustom")
    private Long id;

    @Override
    public boolean isNew() {
        return null == this.getId();
    }
}
