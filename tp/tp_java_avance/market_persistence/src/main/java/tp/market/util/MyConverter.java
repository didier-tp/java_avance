package tp.market.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MyConverter {

    public static <S, D> D map(S source, Class<D> targetClass) {
        if(source==null || targetClass==null) return null;
        D target  = null;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties( target , source);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RuntimeException("echec MyConverter.map",e);
        }
        log.trace("MyConverter.map() called with source = "+source + " and targetClass = "+targetClass.getName());
        log.trace("MyConverter.map() result = "+target);
        return target;
    }


    public static <S,D> List<D> map(List<S> sourceList , Class<D> targetClass){
        if(sourceList==null || targetClass==null) return null;
        return  sourceList.stream()
                .map((source)->map(source,targetClass))
                .collect(Collectors.toList());
    }
}
