package ${vo.pkg};
import org.springframework.stereotype.Service;
import com.metasoft.model.mapper.${vo.clazzDesc}Mapper;
import com.metasoft.model.metadata.${vo.clazzDesc};
import com.metasoft.model.mybatis.GenericDaoService;
@Service
public class ${vo.clazz} extends GenericDaoService<${vo.clazzDesc}, ${vo.clazzDesc}Mapper> {
    /**
    * @author 自动生成请勿修改
    */
}