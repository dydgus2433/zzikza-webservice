package com.zzikza.springboot.web.domain.sequence;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class CustomPrefixTableSequnceGenerator  extends TableGenerator {


    private final String PREFIX_KEY = "prefix_key";
    private String prefixKey = "";


    public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
    public static final String NUMBER_FORMAT_DEFAULT = "%d";
    private String numberFormat;


    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        prefixKey = ConfigurationHelper.getString( PREFIX_KEY, params);

        numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER,
                params, NUMBER_FORMAT_DEFAULT);
        super.configure(StandardBasicTypes.LONG, params, serviceRegistry);
    }

    @Override
    public Serializable generate(final SharedSessionContractImplementor session, final Object obj) {
        return prefixKey + String.format(numberFormat, super. generate(session, obj));
    }
}
