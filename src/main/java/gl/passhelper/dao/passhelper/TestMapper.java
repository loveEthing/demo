package gl.passhelper.dao.passhelper;

import gl.passhelper.data.DO.TestDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {
    TestDO selectById(@Param("id") Integer id);
}
