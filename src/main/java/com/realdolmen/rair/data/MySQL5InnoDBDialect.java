package com.realdolmen.rair.data;

import org.hibernate.MappingException;

/**
 * Modified dialect to prevent (meaningless) Hibernate error HHH000389.
 * It is 'normal' behavior for Hibernate but it clutters the output. This is because it does not check if
 * something exists before deleting it in the database.
 */
public class MySQL5InnoDBDialect extends org.hibernate.dialect.MySQL5InnoDBDialect {
//    @Override
//    protected String getDropSequenceString(String sequenceName) throws MappingException {
//        return "drop sequence if exists " + sequenceName;
//    }
//
//    @Override
//    public boolean dropConstraints() {
//        return false;
//    }
}
